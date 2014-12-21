package clientfiles;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.*;

/**NumberGenerator.java
 * Creates random number generator towers.
 * 
 * @author Patrick
 * 
 * UPDATE LOG
 *	11/26/2014
 *		set damage to 0 since it now has a splash effect
 * 11/27/14:
 * 		re-colored with all-new red theme
 * 
 *  ----------------------------------------------------------------------
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

public class NumberGenerator extends Tower
{
	public static ImageIcon icon = new ImageIcon(MyImages.random);
	public static ImageIcon invalidIcon = new ImageIcon(MyImages.invalidRandom);
	public static ImageIcon lethalIcon = new ImageIcon(MyImages.randomlethal);
	public static int cost = 600;
	static Random rand = new Random(31415);
	public static ImageIcon[] icons = {new ImageIcon(MyImages.random0), new ImageIcon(MyImages.random1),
										new ImageIcon(MyImages.random2), new ImageIcon(MyImages.random3),
										new ImageIcon(MyImages.random4), new ImageIcon(MyImages.random5),
										new ImageIcon(MyImages.random6), new ImageIcon(MyImages.random7),
										new ImageIcon(MyImages.random8), new ImageIcon(MyImages.random9)};
	public static int speedToSet = 80;
	public static int rangeToSet = (int) (Game.widthOfGamePanel * .15);
	
	public NumberGenerator(int xToSet, int yToSet, int idToSet) 
	{
		super(icon, idToSet);
		
		// to be edited later
		int [] costsOfUpgradesGoBetween = {75, 50, 10000000, 0, 0, 0, 0, 0, 0, 60, 10000000, 0, 0, 0, 0, 0, 0, 0, 50, 10000000, 0, 0, 0, 0, 0, 0, 0};
		costsOfUpgrades = costsOfUpgradesGoBetween;
		
		cost = 60;
		projectileDurability = 1;
		rangeOfSplash = 0; 
		splashEffect = false;
		
		diameterOfTower = 50;
		
		x=0;
		y=0;
		range = rangeToSet;
		timerReset = speedToSet;
		
		type = TowerType.NUMBER_GENERATOR;
		damage = 0;
		
		realValue += cost;
		
		//make user pay for towers
		Game.money -= cost;
		Game.gf.moneyLabel.setText("$"+Game.money+" money");
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
			case 2:					Upgrades.upgradePath2.setText("Path Closed");
									break;
			default:				break;
		}
		
		switch (allTowers[idOfTower].upgradesInPath3)
		{
			case 1:					Upgrades.upgradePath3.setText("Wider Range");
									break;
			case 2:					Upgrades.upgradePath3.setText("Path Closed");
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
				case 1:					Upgrades.upgradesInfo.setText("Network Disruptor:\n  $75\n Disables all viruses\n within range of\n the disruption");
										break;
				case 2:					Upgrades.upgradesInfo.setText("Disruptor Range:\n $50\n Increses range of\n disruption");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
		
		else if (upgradePath == 2)
		{
			switch (upgradesInPath2)
			{
				case 1:					Upgrades.upgradesInfo.setText("Lethal Numbers:\n $60\n Numbers now deal\n damage");
										break;
				case 2:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
		
		else if (upgradePath == 3)
		{
			switch (upgradesInPath3)
			{
				case 1:					Upgrades.upgradesInfo.setText("Wider Range:\n   $50\n Increases tower\n range");
										break;
				case 2:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
	}
}
