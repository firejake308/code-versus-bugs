package clientfiles;

import java.awt.geom.Arc2D;

import javax.swing.ImageIcon;
/**
 * The purpose of this class is to initialize the tower "Scanner"
 * 
 * UPDATE LOG:
 * 	12/20/2014:
 * 		created
 */

public class Scanner extends Tower
{
	public static int cost = 1000;
	
	public static ImageIcon icon = new ImageIcon(MyImages.scanner0);
	public static ImageIcon invalidIcon = new ImageIcon(MyImages.invalidScanner);
	
	public static double damageToSet = .35;
	public static int rangeToSet = (int) (Game.widthOfGamePanel * .10);
	public static int arcAngleToSet = 90;
	
	public int tickCounter = 0;
	private int arcAngle;
	public boolean disableWorms = false;
	
	public Scanner(int xToSet, int yToSet, int idToSet)
	{
		super(icon, idToSet);
		
		// to be edited later
		int [] costsOfUpgradesGoBetween = {500, 1000, 1000000, 1000000, 0, 0, 0, 0, 0, 500, 750, 10000000, 0, 0, 0, 0, 0, 0, 400, 600, 10000000, 10000000, 0, 0, 0, 0, 0};
		costsOfUpgrades = costsOfUpgradesGoBetween;
		
		//cost = 50;
		projectileDurability = 0;
		rangeOfSplash = 0;
		splashEffect = false;
		
		diameterOfTower = 50;
		health = 100;
		
		x=0;
		y=0;
		
		range = rangeToSet;
		arcAngle = arcAngleToSet;
		
		type = TowerType.SCANNER;
		damage = damageToSet;
		
		realValue += cost;
		
		scan = new Arc2D.Double(x, y, range, range, 0, getArcAngle(), Arc2D.PIE);
		
		//make user pay for towers
		Game.makePurchase(cost);
	}
	
	/**
	 * The purpose of this method is to attack malwares with a special attack only the Scanner uses
	 */
	public void scan(double elapsedTime)
	{
		scanDegree += elapsedTime * 0.7;
		if (Game.fastForward)
			scanDegree += elapsedTime * 0.7;
	}
	
	public static void increaseDamage(int increase)
	{
		damageToSet += increase;
	}
	
	public void addUpgradeOptions(int idOfTower)
	{
		switch (allTowers[idOfTower].upgradesInPath1)
		{
			case 1:					Upgrades.upgradePath1.setText("Stronger Scan");
									break;
			case 2:					Upgrades.upgradePath1.setText("Legendary Scan");
									break;
			case 3:					Upgrades.upgradePath1.setText("Path Closed");
									break;
			case 4:					Upgrades.upgradePath1.setText("Path Closed");
									break;
			default:				break;
		}
		
		switch (allTowers[idOfTower].upgradesInPath2)
		{
			case 1:					Upgrades.upgradePath2.setText("Backup Tower");
									break;
			case 2:					Upgrades.upgradePath2.setText("Disarm Worms");
									break;
			case 3:					Upgrades.upgradePath2.setText("Path Closed");
									break;
			default:				break;
		}
		
		switch (allTowers[idOfTower].upgradesInPath3)
		{
			case 1:					Upgrades.upgradePath3.setText("Wider Arc");
									break;
			case 2:					Upgrades.upgradePath3.setText("Extreme Arc");
									break;
			case 3:					Upgrades.upgradePath3.setText("Path Closed");
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
				case 1:					Upgrades.upgradesInfo.setText(" Stronger Scan:\n   $500\n Scans deal more\n damage at a time");
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Legendary Scan:\n   $1000\n Scans deal an\n immense amount of\n damage at a time");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
				case 4:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
		
		else if (upgradePath == 2)
		{
			switch (upgradesInPath2)
			{
				case 1:					Upgrades.upgradesInfo.setText(" Backup Tower:\n    $500\n Allows the tower to\n be cured");
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Quarantine Worms:\n    $750\n Quarantines worms,\n disabling their attacks");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
		
		else if (upgradePath == 3)
		{
			switch (upgradesInPath3)
			{
				case 1:					Upgrades.upgradesInfo.setText(" Wider Range:\n     $400\n Increases the angle\n of the scan arc.");
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Extreme Range:\n     $600\n Greatly increases\n the angle of the scan arc");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
				case 4:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
	}

	public int getArcAngle() 
	{
		return arcAngle;
	}

	public void increaseArcAngle(int increase) 
	{
		this.arcAngle += increase;
	}
}