package clientfiles;

import javax.swing.ImageIcon;

/**
 * Created:		12/21/2014
 * 
 * @author Patrick
 *
 * This will be the brains for the fire walls
 */

public class FireWall extends Tower
{	
	public static int cost = 50;
	public static ImageIcon icon = new ImageIcon(MyImages.firewall);
	public static ImageIcon invalidIcon = new ImageIcon(MyImages.invalidDT);
	public static int damageToSet = 100;
	public static int speedToSet = 0;
	public static int rangeToSet = 0;
	public int killsLeft;
	public int killsPerRound = 10;
	
	private Malware[] malwaresHit = new Malware[1000];
	
	public FireWall(int xToSet, int yToSet, int idToSet) 
	{
		super(icon, idToSet);
		
		// to be edited later
		int [] costsOfUpgradesGoBetween = {50, 200, 250, 1000000, 0, 0, 0, 0, 0, 40, 100, 10000000, 0, 0, 0, 0, 0, 0, 30, 60, 30, 10000000, 0, 0, 0, 0, 0};
		costsOfUpgrades = costsOfUpgradesGoBetween;
		
		//cost = 50;
		projectileDurability = 0;
		rangeOfSplash = 0;
		splashEffect = false;
		
		diameterOfTower = 50;
		
		x=0;
		y=0;
		
		killsLeft = killsPerRound;
		
		range = rangeToSet;
		timerReset = speedToSet;
		
		type = TowerType.FIREWALL;
		damage = damageToSet;
		
		angleOfArrow = 0;
		
		realValue += cost;
		
		//make user pay for towers
		Game.makePurchase(cost);
	}
	
	public static void increaseDamage(int increase)
	{
		damageToSet += increase;
	}
	
	public void addUpgradeOptions(int idOfTower)
	{
		switch (allTowers[idOfTower].upgradesInPath1)
		{
			case 1:					Upgrades.upgradePath1.setText("Harder Discs");
									break;
			case 2:					Upgrades.upgradePath1.setText("Impervious Discs");
									break;
			case 3:					Upgrades.upgradePath1.setText("Quicker Firing");
									break;
			case 4:					Upgrades.upgradePath1.setText("Path Closed");
									break;
			default:				break;
		}
		
		switch (allTowers[idOfTower].upgradesInPath2)
		{
			case 1:					Upgrades.upgradePath2.setText("Powerful Discs");
									break;
			case 2:					Upgrades.upgradePath2.setText("Stronger Discs");
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
			case 3:					Upgrades.upgradePath3.setText("Cure Tower");
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
				case 1:					Upgrades.upgradesInfo.setText(" Harder Discs:\n   $50\n Discs can attack 2\n viruses before being\n  destroyed");
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Impervious Discs:\n   $200\n Discs can attack 3\n viruses before being\n  destroyed");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Quicker Firing:\n    $250\n Attack speed\n increase");
										break;
				case 4:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
		
		else if (upgradePath == 2)
		{
			switch (upgradesInPath2)
			{
				case 1:					Upgrades.upgradesInfo.setText(" More Powerful Discs:\n    $40\n Discs are more\n powerful");
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Stronger Discs:\n    $100\n Discs are even\n more pawerful");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
		
		else if (upgradePath == 3)
		{
			switch (upgradesInPath3)
			{
				case 1:					Upgrades.upgradesInfo.setText(" Wider Range:\n     $30\n Increases tower\n range");
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Extreme Range:\n     $60\n Greatly increases\n tower range");
										break;
				case 3:					Upgrades.upgradesInfo.setText(" Cure tower:\n     $30\n Cures tower if it is\n infected by a worm");
										break;
				case 4:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
	}
	
	public void dealDamage()
	{
		boolean virusHit = false;
		
		if (killsLeft == 0)
			return;
		
		for (int m = 0; m < Malware.numMalwares; m++)
		{
			if (Malware.allMalware[m] == null)
				return;
			
			virusHit = false; // resets variable
			double xOfVirus = Malware.allMalware[m].getCenterX();
			double yOfVirus = Malware.allMalware[m].getCenterY();
			
			for (int i = 0; i < killsPerRound; i++)
			{
				if (malwaresHit[i] == null)
					break;
				if (malwaresHit[i] == Malware.allMalware[m])
					virusHit = true;
			}
			
			double distFromMalware  = Math.sqrt(Math.pow(xOfVirus - getCenterX(), 2) + Math.pow(yOfVirus - getCenterY(), 2));
			
			if(distFromMalware <= Malware.allMalware[m].sprite.getHeight(null) / 2 && !virusHit)
			{
				Malware type = Malware.allMalware[m];
				
				if (type instanceof Minion)
				{
					type.dealDamage(1000, 0, id);
					killsLeft--;
				}
				else
				{
					type.dealDamage(150, 1, id);
					killsLeft--;
					
					for (int i = 0; i < killsPerRound; i++)
					{
						if (malwaresHit[i] == null)
						{
							malwaresHit[i] = type;
							break;
						}
					}
				}
			}
		}
	}
	
}
