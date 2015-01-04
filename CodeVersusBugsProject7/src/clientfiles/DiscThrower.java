package clientfiles;
/**DiscThrower.java
 * This class extends Tower to create a class that creates DiscThrower objects
 * Contains methods to set and get x and y and also draw the tower
 * The image is currently a primitive sprite
 * 
 * UDPATE LOG
 * 11/8/14:
 * 		added primitive sprite
 * 11/16/2014:
 * 		[BALANCE] increased tower cost (20 -> 50)
 * 		added a static int icon that stores the DiscThrower image
 * 
 * TODO add rotating arrow
 * 
 * ----------------------------------------------------------------------
 * 
 * Copyright 2014 Adel Hassan and Patrick Kenney
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import javax.swing.ImageIcon;

public class DiscThrower extends Tower
{
	public static int cost = 500;
	public static ImageIcon icon = new ImageIcon(MyImages.dt0);
	public static ImageIcon invalidIcon = new ImageIcon(MyImages.invalidDT);
	public static int damageToSet = 25;
	public static int speedToSet = 30;
	public static int rangeToSet = (int) (Game.widthOfGamePanel * .12);
	
	public DiscThrower(int xToSet, int yToSet, int idToSet) 
	{
		super(icon, idToSet);
		
		// to be edited later
		int [] costsOfUpgradesGoBetween = {300, 600, 500, 1000000, 0, 0, 0, 0, 0, 300, 500, 10000000, 0, 0, 0, 0, 0, 0, 200, 400, 500, 10000000, 0, 0, 0, 0, 0};
		costsOfUpgrades = costsOfUpgradesGoBetween;
		
		//cost = 50;
		projectileDurability = 1;
		rangeOfSplash = 0;
		splashEffect = false;
		
		diameterOfTower = icon.getIconWidth();
		
		x=0;
		y=0;
		
		range = rangeToSet;
		timerReset = speedToSet;
		
		type = TowerType.DISC_THROWER;
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
			case 3:					Upgrades.upgradePath3.setText("Backup Tower");
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
				case 1:					Upgrades.upgradesInfo.setText("Harder Discs: $300\nDiscs can attack 2 viruses before being destroyed");
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Impervious Discs: $600\nDiscs can attack 3 viruses before being  destroyed");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Quicker Firing: $500\nAttack speed increase");
										break;
				case 4:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
		
		else if (upgradePath == 2)
		{
			switch (upgradesInPath2)
			{
				case 1:					Upgrades.upgradesInfo.setText(" More Powerful Discs: $300\n Discs are more powerful");
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Stronger Discs: $500\n Discs are even more powerful");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
		
		else if (upgradePath == 3)
		{
			switch (upgradesInPath3)
			{
				case 1:					Upgrades.upgradesInfo.setText(" Wider Range: $200\n Increases tower range");
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Extreme Range: $400\n Greatly increases tower range");
										break;
				case 3:					Upgrades.upgradesInfo.setText(" Backup Tower: $500\n Allows the tower to be cured");
										break;
				case 4:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
	}
}
 