package clientfiles;

import java.awt.Point;

import javax.swing.ImageIcon;

import clientfiles.Malware.State;

public class CommunicationsTower extends Tower
{
	public static int cost = 1000;
	
	public static ImageIcon icon = new ImageIcon(MyImages.commTower);
	public static ImageIcon invalidIcon = new ImageIcon(MyImages.invalidCommTower);
	
	public static int damageToSet = 0;
	public static int speedToSet = 0;
	public static int rangeToSet = (int) ((Game.screenSize.width - 100) * .05);
	public static int healthToSet = 0;   // immortal
	
	public static boolean star = false;
	public static boolean mesh = false;
	public static boolean uploadingTower = false;
	public static int numOfPacketsToHub = 0;
	public static int numOfPacketsToTower = 0;
	public static int timerResetHub = 150;
	public static int timerHub = 0;
	public static int timerResetTower = 150;
	public static int timerTower = 0;
	public static Tower connectingTower = null;
	public static Tower towerHub = null;
	
	// tracks unlocked upgrades, first 0 = dt, 1 = ng, 2 = sc, second 0 = 1st path, 1 = 2nd path, 2 = 3rd path
	public static int [][] upgradesUnlocked = new int[3][3];
	public static Tower [] towersConnected = new Tower[100];
	public static Tower [] bestConnectedTowers = new Tower [3];
	public static int totalTowersConnected = 0;
	
	public boolean shareRange = false;
	public boolean informationHub = false;
	public double damageToAdd = 10;
	public double speedToAdd = -5;
	public double rangeToAdd = (int) ((Game.screenSize.width - 100) * .05);
	
	public CommunicationsTower(int xToSet, int yToSet, int idToSet)
	{
		super(icon, idToSet);

		// to be edited later
		int [] costsOfUpgradesGoBetween = {400, 500, 600, 1000000, 0, 0, 0, 0, 0, 750, 1000, 10000000, 0, 0, 0, 0, 0, 0, 500, 800, 1500, 10000000, 0, 0, 0, 0, 0};
		costsOfUpgrades = costsOfUpgradesGoBetween;
		
		//cost = 50;
		projectileDurability = 0;
		rangeOfSplash = 0;
		splashEffect = false;
		
		diameterOfTower = icon.getIconWidth();
		
		x=0;
		y=0;
		
		range = rangeToSet;
		System.out.println("range = "+rangeToSet);
		timerReset = speedToSet;
		
		type = TowerType.COMMUNICATIONS_TOWER;
		damage = damageToSet;
		
		angleOfArrow = 0;
		
		realValue += cost;
		
		//make user pay for towers
		Game.makePurchase(cost);
	}

	public void addUpgradeOptions(int idOfTower)
	{
		switch (allTowers[idOfTower].upgradesInPath1)
		{
			case 1:					Upgrades.upgradePath1.setText("Damage Support");
									break;
			case 2:					Upgrades.upgradePath1.setText("Range Support");
									break;
			case 3:					Upgrades.upgradePath1.setText("Legendary Damage");
									break;
			case 4:					Upgrades.upgradePath1.setText("Path Closed");
									break;
			default:				break;
		}
		
		switch (allTowers[idOfTower].upgradesInPath2)
		{
			case 1:					Upgrades.upgradePath2.setText("Fire Speed");
									break;
			case 2:					Upgrades.upgradePath2.setText("Share Range");
									break;
			case 3:					Upgrades.upgradePath2.setText("Path Closed");
									break;
			default:				break;
		}
		
		switch (allTowers[idOfTower].upgradesInPath3)
		{
			case 1:					Upgrades.upgradePath3.setText("Wider Range");
									break;
			case 2:					Upgrades.upgradePath3.setText("Extreme Range");
									break;
			case 3:					Upgrades.upgradePath3.setText("Information Hub");
									break;
			case 4:					Upgrades.upgradePath3.setText("Path Closed");
									break;
			default:				break;
		}
	}
	
	public void displayUpgradeInfo(int upgradePath)
	{
		if (upgradePath == 1)
		{
			switch (upgradesInPath1)
			{
				case 1:					Upgrades.upgradesInfo.setText(" Damage Support:\n $400\n Tower boosts other's\n damage more");
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Range Support:\n $500\n Tower boosts other's\n range more");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Legendary Damage:\n $600\n Tower boosts other's\n damage even more");
										break;
				case 4:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
		
		else if (upgradePath == 2)
		{
			switch (upgradesInPath2)
			{
				case 1:					Upgrades.upgradesInfo.setText(" Fire Speed:\n $750\n Increases nearby tower's\n attack speed more");
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Share Range:\n $1000\n Towers can shoot into\n this tower's range");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
		
		else if (upgradePath == 3)
		{
			switch (upgradesInPath3)
			{
				case 1:					Upgrades.upgradesInfo.setText(" Wider Range:\n $500\n Increases tower range");
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Extreme Range:\n $800\n Greatly increases tower range");
										break;
				case 3:					Upgrades.upgradesInfo.setText(" Information Hub:\n $1500\n Allows connected towers to\n share upgrades");
										break;
				case 4:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
	}
	
	/**
	 * Adds upgrades to nearby towers
	 */
	public void upgradeTowers()
	{
		// for use in loop
		boolean invalidTower = false;
		double xOfTower;
		double yOfTower;
		double xOfTarget;
		double yOfTarget;
		double distance;
		
		// gives upgrades to towers
		for(int t=0; t < GamePanel.numTowers; t++)
		{
			// test if it's null, if it is exit loop
			if (Tower.allTowers[t] == null)
				break;;
			// test if it's an invalid tower
			if (Tower.allTowers[t].type == TowerType.COMMUNICATIONS_TOWER || Tower.allTowers[t].type == TowerType.FIREWALL || Tower.allTowers[t].type == TowerType.ENCRYPTER)
				invalidTower = true;
			// test if tower's already been buffed
			if (Tower.allTowers[t].buffed)
				break;
			
			// test if tower is within range
			if (!invalidTower)
			{
				// check if tower is within range
				xOfTower = getCenterX();
				yOfTower = getCenterY();
				xOfTarget = Tower.allTowers[t].getCenterX();
				yOfTarget = Tower.allTowers[t].getCenterY();
				distance = Math.sqrt(Math.pow(xOfTarget - xOfTower, 2) + Math.pow(yOfTarget - yOfTower, 2));
				
				// if within range add buffs
				if (distance <= range)
				{
					if (Tower.allTowers[t] instanceof DiscThrower || Tower.allTowers[t] instanceof NumberGenerator)
					{
						Tower.allTowers[t].range += rangeToAdd;
						Tower.allTowers[t].timerReset += speedToAdd;
						Tower.allTowers[t].damage += damageToAdd;
					}
					else if (Tower.allTowers[t] instanceof Scanner)
					{
						Tower.allTowers[t].range += rangeToAdd / 2;
						Tower.allTowers[t].damage += damageToAdd / 20;
					}
				}
			}
			
			invalidTower = false;
		}
	}
	
	public void removeUpgrades()
	{
		// for use in loop
		boolean invalidTower = false;
		double xOfTower;
		double yOfTower;
		double xOfTarget;
		double yOfTarget;
		double distance;
		
		//gives buffs to nearby towers
		for(int t=0; t < GamePanel.numTowers; t++)
		{
			// test if it's null, if it is exit loop
			if (Tower.allTowers[t] == null)
				break;
			// test if it's an invalid tower
			if (Tower.allTowers[t].type == TowerType.COMMUNICATIONS_TOWER || Tower.allTowers[t].type == TowerType.FIREWALL || Tower.allTowers[t].type == TowerType.ENCRYPTER)
				invalidTower = true;
			// test if tower is within range
			if (!invalidTower)
			{
				// check if tower is within range
				xOfTower = getCenterX();
				yOfTower = getCenterY();
				xOfTarget = Tower.allTowers[t].getCenterX();
				yOfTarget = Tower.allTowers[t].getCenterY();
				
				distance = Math.sqrt(Math.pow(xOfTarget - xOfTower, 2) + Math.pow(yOfTarget - yOfTower, 2));
				
				// if within range add buffs
				if (distance <= range)
				{
					if (Tower.allTowers[t] instanceof DiscThrower || Tower.allTowers[t] instanceof NumberGenerator)
					{
						Tower.allTowers[t].range -= rangeToAdd;
						Tower.allTowers[t].timerReset -= speedToAdd;
						Tower.allTowers[t].damage -= damageToAdd;
					}
					else if (Tower.allTowers[t] instanceof Scanner)
					{
						Tower.allTowers[t].range -= rangeToAdd / 2;
						Tower.allTowers[t].damage = damageToAdd / 20;
					}
				}
			}
		}
	}
	
	/**
	 * Adds upgrades to a tower if it is added to the game later
	 * 
	 * @param tower
	 */
	public void upgradeTower(Tower tower)
	{
		// for use in loop
		boolean invalidTower = false;
		double xOfTower;
		double yOfTower;
		double xOfTarget;
		double yOfTarget;
		double distance;
		
		// test if it's an invalid tower
		if (tower.type == TowerType.COMMUNICATIONS_TOWER || tower.type == TowerType.FIREWALL || tower.type == TowerType.ENCRYPTER)
			invalidTower = true;
		// test if tower is within range
		if (!invalidTower)
		{
			// check if tower is within range
			xOfTower = getCenterX();
			yOfTower = getCenterY();
			xOfTarget = tower.getCenterX();
			yOfTarget = tower.getCenterY();
			distance = Math.sqrt(Math.pow(xOfTarget - xOfTower, 2) + Math.pow(yOfTarget - yOfTower, 2));
			
			// if within range add buffs
			if (distance <= range)
			{
				if (tower instanceof DiscThrower || tower instanceof NumberGenerator)
				{
					tower.range += rangeToAdd;
					tower.timerReset += speedToAdd;
					tower.damage += damageToAdd;
				}
				else if (tower instanceof Scanner)
				{
					tower.range += rangeToAdd / 2;
					tower.damage += damageToAdd / 20;
				}
			}
		}
	}
	
	public static void connectTower(Tower tower)
	{
		boolean uploadTower = false;
		
		if (tower.type == TowerType.DISC_THROWER)
		{
			if (tower.upgradesInPath1 >= upgradesUnlocked[0][0])
			{
				upgradesUnlocked[0][0] = tower.upgradesInPath1;
			}
			else
				uploadTower = true;
			if (tower.upgradesInPath2 >= upgradesUnlocked[0][1])
			{
				upgradesUnlocked[0][1] = tower.upgradesInPath2;
			}
			else
				uploadTower = true;
			if (tower.upgradesInPath3 >= upgradesUnlocked[0][2])
			{
				upgradesUnlocked[0][2] = tower.upgradesInPath3;
			}
			else
				uploadTower = true;
		}
		if (tower.type == TowerType.NUMBER_GENERATOR)
		{
			if (tower.upgradesInPath1 >= upgradesUnlocked[1][0])
			{
				upgradesUnlocked[1][0] = tower.upgradesInPath1;
			}
			else
				uploadTower = true;
			if (tower.upgradesInPath2 >= upgradesUnlocked[1][1])
			{
				upgradesUnlocked[1][1] = tower.upgradesInPath2;
			}
			else
				uploadTower = true;
			if (tower.upgradesInPath3 >= upgradesUnlocked[1][2])
			{
				upgradesUnlocked[1][2] = tower.upgradesInPath3;
			}
			else
				uploadTower = true;
		}
		if (tower.type == TowerType.SCANNER)
		{
			if (tower.upgradesInPath1 >= upgradesUnlocked[2][0])
			{
				upgradesUnlocked[2][0] = tower.upgradesInPath1;
			}
			else
				uploadTower = true;
			if (tower.upgradesInPath2 >= upgradesUnlocked[2][1])
			{
				upgradesUnlocked[2][1] = tower.upgradesInPath2;
			}
			else
				uploadTower = true;
			if (tower.upgradesInPath3 >= upgradesUnlocked[2][2])
			{
				upgradesUnlocked[2][2] = tower.upgradesInPath3;
			}
			else
				uploadTower = true;
		}
		
		if (!uploadTower)
		{
			if (tower instanceof DiscThrower)
				bestConnectedTowers[0] = tower;
			if (tower instanceof NumberGenerator)
				bestConnectedTowers[1] = tower;
			if (tower instanceof Scanner)
				bestConnectedTowers[2] = tower;
			
			towersConnected[totalTowersConnected] = tower;
			totalTowersConnected++;
			updateConnectedTowers(tower);
			return;
		}
		uploadingTower = true;
		connectingTower = tower;
		
		if (star)
		{
			if (tower instanceof DiscThrower)
				numOfPacketsToHub = Math.abs((bestConnectedTowers[0].realValue - tower.realValue) / 25);
			if (tower instanceof NumberGenerator)
				numOfPacketsToHub = Math.abs((bestConnectedTowers[1].realValue - tower.realValue) / 25);
			if (tower instanceof Scanner)
				numOfPacketsToHub = Math.abs((bestConnectedTowers[2].realValue - tower.realValue) / 25);
		}
		else if (mesh)
		{
			if (tower instanceof DiscThrower)
				numOfPacketsToTower = Math.abs((bestConnectedTowers[0].realValue - tower.realValue) / 25);
			if (tower instanceof NumberGenerator)
				numOfPacketsToTower = Math.abs((bestConnectedTowers[1].realValue - tower.realValue) / 25);
			if (tower instanceof Scanner)
				numOfPacketsToTower = Math.abs((bestConnectedTowers[2].realValue - tower.realValue) / 25);
		}
	}
	
	public static void handlePackets(boolean leavingFromHub)
	{
		// handle invalid exceptions
		if (connectingTower == null)
			return;
		if (leavingFromHub && numOfPacketsToTower == 0)
			return;
		if (mesh && numOfPacketsToTower == 0)
			return;
		if (mesh && leavingFromHub)
			return;
		if (!mesh && !leavingFromHub && numOfPacketsToHub == 0)
			return;
		
		Point origin = null;
		Point destination = null;
		double xOfTarget = 0;
		double yOfTarget = 0;
		int xOfTower = 0;
		int yOfTower = 0;
		double distanceFromOrigin = 10000;
		double targetsDistance = 10000;
		boolean commTowerSelected = false;
		Tower originTower = null;
		BonusFile packet = null;
		
		// going to tower
		if (leavingFromHub || mesh)
		{
			xOfTarget = connectingTower.getCenterX();
			yOfTarget = connectingTower.getCenterY();
		}
		
		if (towerHub == null && star)
		{
			for (int i = 0; i < Tower.allTowers.length; i++)
			{
				if (Tower.allTowers[i] == null)
					break;
				if (Tower.allTowers[i] instanceof CommunicationsTower)
				{
					if (((CommunicationsTower)Tower.allTowers[i]).informationHub)
					{
						xOfTower = Tower.allTowers[i].getCenterX();
						yOfTower = Tower.allTowers[i].getCenterY();
						commTowerSelected = true;
					}
				}
				
				distanceFromOrigin = Math.sqrt(Math.pow(xOfTarget - xOfTower, 2) + Math.pow(yOfTarget - yOfTower, 2));
				
				if (distanceFromOrigin < targetsDistance && commTowerSelected)
				{
					towerHub = Tower.allTowers[i];
					targetsDistance = distanceFromOrigin;
				}
				
				commTowerSelected = false;
			}
		}
		
		if (towerHub == null && star)
			return;
		
		// for going to hub
		if (!leavingFromHub && star)
		{
			xOfTarget = towerHub.getCenterX();
			yOfTarget = towerHub.getCenterY();
		}
		
		// for going to hub from tower
		if (leavingFromHub && star)
		{
			origin = new Point(towerHub.getCenterX(), towerHub.getCenterY());
			destination = new Point(connectingTower.getCenterX(), connectingTower.getCenterY());
		}
		
		// for mesh or going to hub
		else
		{
			for (int i = 0; i < towersConnected.length; i++)
			{
				if (towersConnected[i] == null)
					break;
				if (towersConnected[i].type == connectingTower.type)
				{
					xOfTower = towersConnected[i].getCenterX();
					yOfTower = towersConnected[i].getCenterY();
				}
				
				distanceFromOrigin = Math.sqrt(Math.pow(xOfTarget - xOfTower, 2) + Math.pow(yOfTarget - yOfTower, 2));
				
				if (distanceFromOrigin < targetsDistance)
				{
					originTower = towersConnected[i];
					targetsDistance = distanceFromOrigin;
				}
			}
			
			origin = new Point(originTower.getCenterX(), originTower.getCenterY());
			if (mesh)
				destination = new Point(connectingTower.getCenterX(), connectingTower.getCenterY());
			else
				destination = new Point(towerHub.getCenterX(), towerHub.getCenterY());
		}
		
		System.out.println(origin + " " + destination);
		if (origin != null && destination != null)
		{
			System.out.println(origin + " " + destination);
			if (mesh || leavingFromHub)
				packet = new BonusFile(origin, destination, false);
			else if (star && !leavingFromHub)
				packet = new BonusFile(origin, destination, true);
		}
	}
	
	/**
	 * Updates the upgrades of already connected towers
	 * @param tower
	 */
	public static void updateConnectedTowers(Tower tower)
	{
		TowerType typeOfUpgradedTower = tower.type;
		int upgradedTowerID = -1;
		
		for (int i = 0; i < towersConnected.length; i++)
		{
			if (towersConnected[i] == null)
				break;
			if (towersConnected[i].type == typeOfUpgradedTower && tower != towersConnected[i])
			{
				upgradeConnectedTowers(tower, towersConnected[i], false);
			}
		}
		
		switch (typeOfUpgradedTower)
		{
			case DISC_THROWER:					upgradedTowerID = 0;
												break;
			case NUMBER_GENERATOR:				upgradedTowerID = 1;
												break;
			case SCANNER:						upgradedTowerID = 2;
												break;
			default:							return;
		}
		
		if (tower.upgradesInPath1 > upgradesUnlocked[upgradedTowerID][0])
			upgradesUnlocked[upgradedTowerID][0] = tower.upgradesInPath1;
		if (tower.upgradesInPath2 > upgradesUnlocked[upgradedTowerID][1])
			upgradesUnlocked[upgradedTowerID][1] = tower.upgradesInPath2;
		if (tower.upgradesInPath3 > upgradesUnlocked[upgradedTowerID][2])
			upgradesUnlocked[upgradedTowerID][2] = tower.upgradesInPath3;
	}
	
	/**
	 * upgrade connected tower if one has been upgraded or upgrade a new tower if it is added
	 * 
	 * @param betterTower
	 * @param worseTower
	 * @param addingNewTower
	 */
	public static void upgradeConnectedTowers(Tower betterTower, Tower worseTower, boolean addingNewTower)
	{
		while(betterTower.upgradesInPath1 > worseTower.upgradesInPath1)
		{
			Upgrades.getUpgradeID(worseTower, 1, true);
		}
		while(betterTower.upgradesInPath2 > worseTower.upgradesInPath2)
		{
			Upgrades.getUpgradeID(worseTower, 2, true);
		}
		while(betterTower.upgradesInPath3 > worseTower.upgradesInPath3)
		{
			Upgrades.getUpgradeID(worseTower, 3, true);
		}
		
		if (addingNewTower)
		{
			connectingTower = null;
			towerHub = null;
		}
	}
}
