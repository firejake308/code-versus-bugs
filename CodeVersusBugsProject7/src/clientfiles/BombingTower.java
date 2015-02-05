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
	public static ImageIcon icon = new ImageIcon(MyImages.random);
	public static ImageIcon invalidIcon = new ImageIcon(MyImages.invalidRandom);
	public static int cost = 750;
	public static int speedToSet = 140;
	public static int rangeToSet = (int) (Game.widthOfGamePanel * .6);
	public static double damageToSet = 30.0;
	
	public BombingTower(int xToSet, int yToSet, int idToSet)
	{
		super(icon, idToSet);
		
		// to be edited later
		int [] costsOfUpgradesGoBetween = {1000, 600, 10000000, 0, 0, 0, 0, 0, 0, 300, 600, 10000000, 0, 0, 0, 0, 0, 0, 400, 600, 10000000, 0, 0, 0, 0, 0, 0};
		costsOfUpgrades = costsOfUpgradesGoBetween;
		
		cost = 60;
		projectileDurability = 1;
		rangeOfSplash = .5; 
		splashEffect = false;
		
		diameterOfTower = 50;
		
		x=0;
		y=0;
		range = rangeToSet;
		timerReset = speedToSet;
		
		type = TowerType.BOMBINGTOWER;
		damage = damageToSet;
		
		realValue += cost;
		
		healthBar = MyImages.healthBar0;
		
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
			case 1:					Upgrades.upgradePath1.setText("Network Disruptor");
									break;
			case 2:					Upgrades.upgradePath1.setText("Disruptor Range");
									break;
			case 3:					Upgrades.upgradePath1.setText("Path Closed");
									break;
			default:				break;
		}
		
		switch (allTowers[idOfTower].upgradesInPath2)
		{
			case 1:					Upgrades.upgradePath2.setText("Lethal Numbers");
									break;
			case 2:					Upgrades.upgradePath2.setText("Killer Numbers");
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
			case 3:					Upgrades.upgradePath3.setText("Path Closed");
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
				case 1:					Upgrades.upgradesInfo.setText("Network Disruptor: $1000\nDisables all viruses within range of the disruption");
										break;
				case 2:					Upgrades.upgradesInfo.setText("Disruptor Range: $600\nIncreases range of disruption");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
		
		else if (upgradePath == 2)
		{
			switch (upgradesInPath2)
			{
				case 1:					Upgrades.upgradesInfo.setText("Lethal Numbers: $300\nNumbers now deal a small amount of damage");
										break;
				case 2:					Upgrades.upgradesInfo.setText("Killer Numbers: $600\nNumbers now deal more damage");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
		
		else if (upgradePath == 3)
		{
			switch (upgradesInPath3)
			{
				case 1:					Upgrades.upgradesInfo.setText("Wider Range: $400\nIncreases tower range");
										break;
				case 2:					Upgrades.upgradesInfo.setText("Extreme Range: $750\nIncreases tower range");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
	}
}
