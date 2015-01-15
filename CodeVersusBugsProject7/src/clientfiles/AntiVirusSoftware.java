/*Copyright 2014 Adel Hassan and Patrick Kenney
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
package clientfiles;


import javax.swing.ImageIcon;
/**
 * An advanced tower that is very expensive, but also has very high stats.
 * 
 * @author Adel Hassan and Patrick Kenney
 */
public class AntiVirusSoftware extends Tower
{
	private static final long serialVersionUID = 1L;
	public static int cost = 3000;
	public static ImageIcon icon = new ImageIcon(MyImages.antiVirusSoftware);
	public static ImageIcon invalidIcon = new ImageIcon(MyImages.invalidAntiVirusSoftware);
	public static int damageToSet = 50;
	public static int speedToSet = 20;
	public static int rangeToSet = (int) ((Game.screenSize.height - 100) * .3);
	
	public AntiVirusSoftware(int xToSet, int yToSet, int idToSet)
	{
		super(icon, idToSet);
		
		// to be edited later
		int [] costsOfUpgradesGoBetween = {1000, 2000, 3000, 1000000, 0, 0, 0, 0, 0, 1000, 2000, 3000, 10000000, 0, 0, 0, 0, 0, 1500, 3000, 2000, 10000000, 0, 0, 0, 0, 0};
		costsOfUpgrades = costsOfUpgradesGoBetween;
		
		//cost = 50;
		projectileDurability = 1;
		rangeOfSplash = 0;
		splashEffect = false;
		
		diameterOfTower = icon.getIconWidth();
		
		x=xToSet;
		y=yToSet;
		
		range = rangeToSet;
		timerReset = speedToSet;
		
		type = TowerType.ANTIVIRUS_SOFTWARE;
		damage = damageToSet;
		
		realValue += cost;
		
		healthBar = MyImages.healthBar0;
		
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
			case 1:					Upgrades.upgradePath1.setText("Harder Shields");
									break;
			case 2:					Upgrades.upgradePath1.setText("Impervious Shields");
									break;
			case 3:					Upgrades.upgradePath1.setText("Quicker Firing");
									break;
			case 4:					Upgrades.upgradePath1.setText("Path Closed");
									break;
			default:				break;
		}
		
		switch (allTowers[idOfTower].upgradesInPath2)
		{
			case 1:					Upgrades.upgradePath2.setText("Powerful Shields");
									break;
			case 2:					Upgrades.upgradePath2.setText("Stronger Shields");
									break;
			case 3:					Upgrades.upgradePath2.setText("Killer Shields");
									break;
			case 4:					Upgrades.upgradePath2.setText("Path Closed");
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
				case 1:					Upgrades.upgradesInfo.setText("Harder Shields: $1000\nDiscs can attack 2 viruses before being destroyed"); // TODO
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Impervious Shields: $2000\nDiscs can attack 3 viruses before being  destroyed");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Quicker Firing: $3000\nAttack speed increase");
										break;
				case 4:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
		
		else if (upgradePath == 2)
		{
			switch (upgradesInPath2)
			{
				case 1:					Upgrades.upgradesInfo.setText(" More Powerful Shields: $1000\n Discs are more powerful");
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Stronger Shields: $2000\n Discs are even more powerful");
										break;
				case 3:					Upgrades.upgradesInfo.setText(" Killerr Shields: $3000\n Discs are even more powerful");
										break;
				case 4:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
		
		else if (upgradePath == 3)
		{
			switch (upgradesInPath3)
			{
				case 1:					Upgrades.upgradesInfo.setText(" Wider Range: $1500\n Increases tower range");
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Extreme Range: $3000\n Greatly increases tower range");
										break;
				case 3:					Upgrades.upgradesInfo.setText(" Backup Tower: $2000\n Allows the tower to be cured");
										break;
				case 4:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
	}
}
 