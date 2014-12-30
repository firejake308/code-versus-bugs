package clientfiles;

import java.awt.geom.Arc2D;
import java.util.Random;

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
	
	private Arc2D workingScan;
	
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
	 * Moves the scanner forward
	 */
	public void scan(double elapsedTime)
	{
		scanDegree += elapsedTime * 0.7;
		if (Game.fastForward)
			scanDegree += elapsedTime * 0.7;
		
		//save current, working arc
		if(!infected)
		{
			workingScan = scan;
		}
		//disable an infected tower
		if(scanDegree > 359 && scanDegree < 361 && infected)
		{
			scan = new Arc2D.Double();
			System.out.println("disabled scanner");
		}
		else if(scanDegree > 719 && infected)
		{
			scan = workingScan;
			scanDegree = 0;
			System.out.println("re-enabled scanner");
		}
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
				case 1:					Upgrades.upgradesInfo.setText("Stronger Scan: $500\nScans deal more damage at a time");
										break;
				case 2:					Upgrades.upgradesInfo.setText("Legendary Scan: $1000\nScans deal an immense amount of damage at a time");
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
				case 1:					Upgrades.upgradesInfo.setText(" Backup Tower: $500\nAllows the tower to be cured");
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Quarantine Worms: $750\nQuarantines worms, disabling their attacks");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
		
		else if (upgradePath == 3)
		{
			switch (upgradesInPath3)
			{
				case 1:					Upgrades.upgradesInfo.setText(" Wider Range: $400\nIncreases the angle of the scan arc.");
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Extreme Range: $600\nGreatly increases the angle of the scan arc");
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