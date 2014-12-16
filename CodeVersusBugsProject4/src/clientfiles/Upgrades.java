package clientfiles;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
/**
 * Purpose: To deal with upgrades, all the upgrades and visuals
 * 
 * 
 * Adding Upgrades:
 * 		Add in Upgrades.upgradeTower()
 * 		Add price in TowerClass.constructor
 * 		Add text in TowerClass.addUpgradeOption(int idOfTower)
 * 		Add info in TowerClass.displayUpgradeInfo(int ugradePath)
 * 
 * 11/26/2014-created
 * 11/27/2014-functional
 * 11/27/14:
 * 		deleting towers fixed - Adel
 * 12/6/14:
 * 		fixed overlapping delete button
 * 
 * @author Patrick Kenney
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
public abstract class Upgrades
{
	public static JButton   deleteTower         = new JButton();
	public static JPanel    upgrade             = new JPanel();
	public static int       displayedUpgradeID  = 1000;
	
	public static JButton   upgradePath1   		= new JButton();
	public static JButton   upgradePath2   		= new JButton();
	public static JButton   upgradePath3 	    = new JButton();
	
	public static JTextArea statistics   		= new JTextArea();
	public static JTextArea upgradesInfo 		= new JTextArea();
	
	public static TowerType typeOfTower  		= TowerType.NONE;
	
	public static void initializeUpgrades()
	{
		int height = Game.gamePanel.getHeight() / 10;
		int width = Game.gamePanel.getWidth();
		
		//TODO check setBounds stuff
		upgrade.setBounds(0, height * 9, width, width / 10);
		upgrade.setBackground(Color.CYAN);
		upgrade.setVisible(true);
		upgrade.setLayout(null);
		
		upgradePath1.setBounds(width/10 + 80, 10, 150, height-20);
		upgradePath2.setBounds(width/10 + 235, 10, 150, height-20);
		upgradePath3.setBounds(width/10 + 390, 10, 150, height-20);
		
		statistics.setBounds(5, 5, 100, 80);
		statistics.setBackground(Color.CYAN);
		upgradesInfo.setBounds((int) (width / 1.5) - 75, 10, 150, height - 20);
		upgradesInfo.setBackground(Color.CYAN);
		
		deleteTower.setBounds(105+(width/10-25)/2-37, 10, 75, 25);
		deleteTower.setText("Delete");
		
		upgrade.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				Game.gamePanel.mouseInUpgradePanel = true;
			}
			
			public void mouseExited(MouseEvent e)
			{
				Game.gamePanel.mouseInUpgradePanel = false;
			}
		});
		
		upgradePath1.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	getUpgradeID(1);
		    }
		});
		
		upgradePath1.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				upgradesInfo.setBackground(Color.CYAN);
				Tower.allTowers[displayedUpgradeID].displayUpgradeInfo(1);
			}
		});
		
		upgradePath2.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	getUpgradeID(2);
		    }
		});
		
		upgradePath2.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				upgradesInfo.setBackground(Color.CYAN);
				Tower.allTowers[displayedUpgradeID].displayUpgradeInfo(2);
			}
		});
		
		upgradePath3.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	getUpgradeID(3);
		    }
		});
		
		upgradePath3.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				upgradesInfo.setBackground(Color.CYAN);
				Tower.allTowers[displayedUpgradeID].displayUpgradeInfo(3);
			}
		});
		
		deleteTower.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	deleteTower();
		    }
		});
	}
	
	public static void showUpgradePanel(int id)
	{
		typeOfTower = Tower.allTowers[id].type;
		displayedUpgradeID = id;
		System.out.println("Dispalyed upgrade id == " + displayedUpgradeID);
		
		updateStatistics();
		upgrade.add(deleteTower);
		
		if (typeOfTower == TowerType.DISC_THROWER)
			DiscThrower.allTowers[displayedUpgradeID].addUpgradeOptions(displayedUpgradeID);
		else if (typeOfTower == TowerType.NUMBER_GENERATOR)
			NumberGenerator.allTowers[displayedUpgradeID].addUpgradeOptions(displayedUpgradeID);
		
		upgrade.add(upgradePath1);
		upgrade.add(upgradePath2);
		upgrade.add(upgradePath3);
		upgrade.add(upgradesInfo);
		Game.gamePanel.addToLayeredPane(upgrade, JLayeredPane.DEFAULT_LAYER + 1);
	}
	
	public static void removeUpgradePanel()
	{	
		if (GamePanel.numTowers < displayedUpgradeID)
			displayedUpgradeID = GamePanel.numTowers--;
		
		Game.gamePanel.remove(statistics);
		Game.gamePanel.remove(deleteTower);
		Game.gamePanel.remove(upgradePath1);
		Game.gamePanel.remove(upgradePath2);
		Game.gamePanel.remove(upgradePath3); 
		Game.gamePanel.remove(upgradesInfo);
		
		Game.gamePanel.removeFromLayeredPane(upgrade);
		displayedUpgradeID = 98;
		upgradesInfo.setText("");
	}
	
	public static void updateStatistics()
	{
		Tower.allTowers[displayedUpgradeID].accuracy = Tower.allTowers[displayedUpgradeID].hits / Tower.allTowers[displayedUpgradeID].shotsFired;
		
		statistics.setText("    Kills: " + Tower.allTowers[displayedUpgradeID].kills + "\n   Shots Fired: " 
										+ Tower.allTowers[displayedUpgradeID].shotsFired + "\n   Hits: " + Tower.allTowers[displayedUpgradeID].hits 
										+ "\n   Accuracy: " + Tower.allTowers[displayedUpgradeID].accuracy);
		upgrade.add(statistics);
	}
	
	
	public static void deleteTower()
	{
		int towerToBeDeletedID = displayedUpgradeID;
		
		System.out.println(displayedUpgradeID);
		
		GamePanel.numTowers--;
		
		Game.gamePanel.removeFromLayeredPane(Tower.sprites[towerToBeDeletedID]);
		
		// reimburse 60% of cost
		Game.addMoney((int) (Tower.allTowers[towerToBeDeletedID].realValue * 0.6));
		
		removeUpgradePanel();
		
		//move all sprites in array
		for(int t = towerToBeDeletedID; t < Tower.allTowers.length; t++)
		{
			Tower.sprites[t] = Tower.sprites[t + 1];
			
			//break once reaches the nulls
			if(Tower.sprites[t] == null)
			{
				System.out.println(towerToBeDeletedID + "= null");
				break;
			}
		}
		
		//move all other towers in array
		for(int t = towerToBeDeletedID; t < Tower.allTowers.length; t++)
		{
			Tower.allTowers[t] = Tower.allTowers[t + 1];
			
			//break once reaches the nulls
			if(Tower.allTowers[t] == null)
			{
				System.out.println(towerToBeDeletedID + "= null");
				break;
			}
			
			Tower.allTowers[t].id = t;
		}
		displayedUpgradeID = 98;
	}

	public static void getUpgradeID(int upgradePath)
	{
		int upgradeID = 0;
		/**Explanation for upgradeID in upgradeTower(int upgradeID)**/
		
		System.out.println("CLICK REGISTERED");
			
		// intentionally no breaks, therefore it adds 100 each time
		switch(typeOfTower)
		{
			case NONE:						return;
			case SCANNER:					upgradeID += 100;
			case NUMBER_GENERATOR:			upgradeID += 100;
			case DISC_THROWER:				upgradeID += 100;
											break;
		}
		
		if (upgradePath == 1)
		{
			upgradeID += 10;
			
			upgradeID += Tower.allTowers[displayedUpgradeID].upgradesInPath1;
			
			//warn user if in tutorial
			if(Game.tutorial && Game.tutorialSlide <= 13)
			{
				Object[] options = {"Oops. I'll go back.", "Stop bothering me!"};
				int choice = JOptionPane.showOptionDialog(Game.gf, "That's the wrong upgrade!", 
						"WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, 0);
				if(choice == 0)
					return;
				else if(choice == 1)
					Game.gamePanel.disableTutorial();
			}
			
			if (Game.makePurchase(Tower.allTowers[displayedUpgradeID].getCostOfUpgrade(1)))
			{
				Tower.allTowers[displayedUpgradeID].realValue += Tower.allTowers[displayedUpgradeID].getCostOfUpgrade(1);
				Tower.allTowers[displayedUpgradeID].upgradesInPath1++;
			}
			else
			{
				upgradesInfo.setBackground(Color.RED);
				return;
			}
		}
				
		else if (upgradePath == 2)
		{
			upgradeID += 20;
			
			upgradeID += Tower.allTowers[displayedUpgradeID].upgradesInPath2;
			
			//warn user if in tutorial
			if(Game.tutorial && Game.tutorialSlide <= 13)
			{
				Object[] options = {"Oops. I'll go back.", "Stop bothering me!"};
				int choice = JOptionPane.showOptionDialog(Game.gf, "That's the wrong upgrade!", 
						"WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, 0);
				if(choice == 0)
					return;
				else if(choice == 1)
					Game.gamePanel.disableTutorial();
			}
			
			if (Game.makePurchase(Tower.allTowers[displayedUpgradeID].getCostOfUpgrade(2)))
			{
				Tower.allTowers[displayedUpgradeID].realValue += Tower.allTowers[displayedUpgradeID].getCostOfUpgrade(2);
				Tower.allTowers[displayedUpgradeID].upgradesInPath2++;
			}
			else
			{
				upgradesInfo.setBackground(Color.RED);
				return;
			}
		}
		else if (upgradePath == 3)
		{
			upgradeID += 30;
			
			upgradeID += Tower.allTowers[displayedUpgradeID].upgradesInPath3;
			
			if (Game.makePurchase(Tower.allTowers[displayedUpgradeID].getCostOfUpgrade(3)))
			{
				Tower.allTowers[displayedUpgradeID].realValue += Tower.allTowers[displayedUpgradeID].getCostOfUpgrade(3);
				Tower.allTowers[displayedUpgradeID].upgradesInPath3++;
			}
			else
			{
				upgradesInfo.setBackground(Color.RED);
				return;
			}
		}
		else
		{
			System.out.println("Error with getting the upgrade ID (button not registered illegal reference)");
			return;
		}
		
		//System.out.println("Upgrade ID == " + upgradeID);
		upgradeTower(upgradeID);
	}
	
	public static void upgradeTower(int upgradeID)
	{
		/**UPGARADE ID == (TowerType + upgrade path + upgrade number)**/
		switch (upgradeID) 
		{
			case 111:						Tower.allTowers[displayedUpgradeID].projectileDurability = 2;
											System.out.println("projectile durability++");
											break;
											
			case 121:						Tower.allTowers[displayedUpgradeID].damage = 40;
											System.out.println("damage++");
											break;
											
			case 131:						Tower.allTowers[displayedUpgradeID].range += Tower.allTowers[displayedUpgradeID].range / 6;
											System.out.println("range++");
											
											//special case for tutorial slide 13
											if(Game.tutorialSlide == 13)
												Game.gamePanel.nextSlide();
											
											//update range indicator
											Tower tower = Tower.allTowers[displayedUpgradeID];
											tower.rangeIndicator = new Ellipse2D.Double(tower.getCenterX()-tower.range, 
													tower.getCenterY()-tower.range, tower.range*2, tower.range*2);
											break;
										
											
											
			case 211:						Tower.allTowers[displayedUpgradeID].splashEffect = true;
											Tower.allTowers[displayedUpgradeID].rangeOfSplash = 1.2;
											System.out.println("splash effect = true");
											break;
										
			case 212:						Tower.allTowers[displayedUpgradeID].rangeOfSplash = 1.5;
											System.out.println("disruptor range enhanced");
											break;
										
			case 221:						Tower.allTowers[displayedUpgradeID].damage = 5;
											Tower.allTowers[displayedUpgradeID].lethalRandoms = true;
											Tower.sprites[displayedUpgradeID].setIcon(NumberGenerator.lethalIcon);
											System.out.println("killer numbers");
											break;
											
			case 231:						Tower.allTowers[displayedUpgradeID].range += Tower.allTowers[displayedUpgradeID].range / 4;
											System.out.println("wider range");
											
											//update range indicator
											Tower tower1 = Tower.allTowers[displayedUpgradeID];
											tower1.rangeIndicator = new Ellipse2D.Double(tower1.getCenterX()-tower1.range, 
											tower1.getCenterY()-tower1.range, tower1.range*2, tower1.range*2);
											break;
		}
		
		Tower.allTowers[displayedUpgradeID].addUpgradeOptions(displayedUpgradeID);
	}
}
