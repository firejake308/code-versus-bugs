package clientfiles;

import javax.swing.ImageIcon;

public class CommunicationsTower extends Tower
{
	public static int cost = 1000;
	
	public static ImageIcon icon = new ImageIcon(MyImages.commTower);
	public static ImageIcon invalidIcon = new ImageIcon(MyImages.invalidCommTower);
	
	public static int damageToSet = 0;
	public static int speedToSet = 0;
	public static int rangeToSet = (int) (Game.widthOfGamePanel * .10);
	public static int healthToSet = 0;   // immortal
	
	public boolean shareRange = false;
	public boolean informationHub = false;
	public double damageToAdd = 10;
	public double speedToAdd = -5;
	public double rangeToAdd = (int) ((Game.screenSize.width - 100) * .02);
	
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
				case 1:					Upgrades.upgradesInfo.setText(" Damage Support: $400\n Tower boosts other's damage more");
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Range Support: $500\n Tower boosts other's range more");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Legendary Damage: $600\n Tower boosts other's damage even more");
										break;
				case 4:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
		
		else if (upgradePath == 2)
		{
			switch (upgradesInPath2)
			{
				case 1:					Upgrades.upgradesInfo.setText(" Fire Speed: $750\n Increases nearby tower's attack speed more");
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Share Range: $1000\n Towers can shoot into this tower's range");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
		
		else if (upgradePath == 3)
		{
			switch (upgradesInPath3)
			{
				case 1:					Upgrades.upgradesInfo.setText(" Wider Range: $500\n Increases tower range");
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Extreme Range: $800\n Greatly increases tower range");
										break;
				case 3:					Upgrades.upgradesInfo.setText(" Information Hub: $1500\n Allows connected towers to share upgrades");
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
}
