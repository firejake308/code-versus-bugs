package clientfiles;

import javax.swing.*;

/**
 * Purpose: code for a new tower that will deal a splash effect, hitting multiple malwares
 * 
 * UPDATE LOG:
 *  2/2/2015:
 *   Fabrication
 * 
 * @author Patrick
 * 
 */

public class BombingTower extends Tower
{
	// TODO
	public static ImageIcon icon = new ImageIcon(MyImages.bt0);
	public static ImageIcon invalidIcon = new ImageIcon(MyImages.invalidDT);
	public static int cost = 750;
	public static int speedToSet = 140;
	public static int rangeToSet =  (int) ((Game.screenSize.width - 100) * .07);
	public static double damageToSet = 35.0;
	
	public BombingTower(int xToSet, int yToSet, int idToSet)
	{
		super(icon, idToSet);
		
		// to be edited later
		int [] costsOfUpgradesGoBetween = {750, 1000, 1250, 100000000, 0, 0, 0, 0, 0, 400, 600, 750, 100000000, 0, 0, 0, 0, 0, 400, 600, 800, 10000000, 0, 0, 0, 0, 0};
		costsOfUpgrades = costsOfUpgradesGoBetween;
		
		cost = 750;
		projectileDurability = 1;
		rangeOfSplash = 1;
		splashEffect = true;
		
		diameterOfTower = 50;
		
		x=0;
		y=0;
		range = rangeToSet;
		timerReset = speedToSet;
		
		type = TowerType.BOMBINGTOWER;
		damage = damageToSet;
		
		realValue += cost;
		
		healthBar = MyImages.healthBar0;
		angleOfArrow = 0;
		
		//make user pay for towers
		Game.makePurchase(cost);
	}
	
	public static void increaseDamage(double increase)
	{
		damageToSet += increase;
	}
	
	public void addUpgradeOptions(int idOfTower)
	{		
		switch (allTowers[idOfTower].upgradesInPath1)
		{
			case 1:					Upgrades.upgradePath1.setText("Wider Blast Radius");
									break;
			case 2:					Upgrades.upgradePath1.setText("Extreme Blast Radius");
									break;
			case 3:					Upgrades.upgradePath1.setText("Leegendary Blast Radius");
									break;
			case 4:					Upgrades.upgradePath1.setText("Path Closed");
									break;
			default:				break;
		}
		
		switch (allTowers[idOfTower].upgradesInPath2)
		{
			case 1:					Upgrades.upgradePath2.setText("More Powerful CDs");
									break;
			case 2:					Upgrades.upgradePath2.setText("Killer CDs");
									break;
			case 3:					Upgrades.upgradePath2.setText("Legendary CDs");
									break;
			default:				break;
		}
		
		switch (allTowers[idOfTower].upgradesInPath3)
		{
			case 1:					Upgrades.upgradePath3.setText("Wider Range");
									break;
			case 2:					Upgrades.upgradePath3.setText("Extreme Range");
									break;
			case 3:					Upgrades.upgradePath3.setText("Legendary Range");
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
				case 1:					Upgrades.upgradesInfo.setText("Wider Blast Radius: $750\nDisables all viruses within range of the disruption");
										break;
				case 2:					Upgrades.upgradesInfo.setText("Extreme Blast Radius: $1000\nIncreases range of disruption");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Legendary Blast Radius: $1250\nIncreases range of disruption");
										break;
				case 4:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
		
		else if (upgradePath == 2)
		{
			switch (upgradesInPath2)
			{
				case 1:					Upgrades.upgradesInfo.setText("More Powerful CDs: $400\nNumbers now deal a small amount of damage");
										break;
				case 2:					Upgrades.upgradesInfo.setText("Killer CDs: $600\nNumbers now deal more damage");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Legendary CDs: $750\nNumbers now deal more damage");
										break;
				case 4:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
		
		else if (upgradePath == 3)
		{
			switch (upgradesInPath3)
			{
				case 1:					Upgrades.upgradesInfo.setText("Wider Range: $400\nIncreases tower range");
										break;
				case 2:					Upgrades.upgradesInfo.setText("Extreme Range: $600\nIncreases tower range");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Legendary Range: $800\nIncreases tower range");
										break;
				case 4:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
	}
}
