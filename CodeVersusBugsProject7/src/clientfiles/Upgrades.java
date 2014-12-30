package clientfiles;

import java.awt.Color;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;

import javax.swing.*;
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
 * 12/16/2014:
 * 		More upgrades added, a cure button is added
 * 12/20/2014:
 * 		Scanner upgrades added (first ones)
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
	public static JButton	cureTower			= new JButton();
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
		
		deleteTower.setBounds(105+(width/10-25)/2-37, 10, 100, 25);
		deleteTower.setText("Delete");
		
		cureTower.setBounds(105+(width/10-25)/2-37, 35, 100, 25);
		cureTower.setText("Cure Tower");
		
		upgrade.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				GamePanel.mouseInUpgradePanel = true;
			}
			
			public void mouseExited(MouseEvent e)
			{
				GamePanel.mouseInUpgradePanel = false;
			}
		});
		
		upgradePath1.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	getUpgradeID(1);
		    	upgradesInfo.setBackground(Color.CYAN);
				Tower.allTowers[displayedUpgradeID].displayUpgradeInfo(1);
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
		    	upgradesInfo.setBackground(Color.CYAN);
				Tower.allTowers[displayedUpgradeID].displayUpgradeInfo(2);
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
		
		cureTower.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				showCureInfo();
			}
		});
		
		cureTower.addActionListener(new ActionListener()
		{
			
		    public void actionPerformed(ActionEvent e)
		    {
		    	cureTower();
		    }
		});
	}
	
	public static void showUpgradePanel(int id)
	{
		typeOfTower = Tower.allTowers[id].type;
		
		displayedUpgradeID = id;
		
		updateStatistics();
		upgrade.add(deleteTower);
		upgrade.add(cureTower);
		
		if (typeOfTower == TowerType.DISC_THROWER)
			DiscThrower.allTowers[displayedUpgradeID].addUpgradeOptions(displayedUpgradeID);
		else if (typeOfTower == TowerType.NUMBER_GENERATOR)
			NumberGenerator.allTowers[displayedUpgradeID].addUpgradeOptions(displayedUpgradeID);
		else if (typeOfTower == TowerType.SCANNER)
			Scanner.allTowers[displayedUpgradeID].addUpgradeOptions(displayedUpgradeID);
		else if (typeOfTower == TowerType.FIREWALL)
			Scanner.allTowers[displayedUpgradeID].addUpgradeOptions(displayedUpgradeID);
		
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
		//Tower.allTowers[displayedUpgradeID].accuracy = Tower.allTowers[displayedUpgradeID].hits / Tower.allTowers[displayedUpgradeID].shotsFired;
		
		statistics.setText("    Kills: " + Tower.allTowers[displayedUpgradeID].kills + "\n   Shots Fired: " 
										 + Tower.allTowers[displayedUpgradeID].shotsFired /* + "\n   Hits: " + Tower.allTowers[displayedUpgradeID].hits 
										 + "\n   Accuracy: " + Tower.allTowers[displayedUpgradeID].accuracy*/);
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
	
	public static void cureTower()
	{
		if (Tower.allTowers[displayedUpgradeID].isInfected() && Tower.allTowers[displayedUpgradeID].backedUp && Game.makePurchase(50))
		{
			Tower.allTowers[displayedUpgradeID].infected = false;
			Tower.allTowers[displayedUpgradeID].health = 50;
			if(Tower.allTowers[displayedUpgradeID] instanceof DiscThrower)
				Tower.allTowers[displayedUpgradeID].setIcon(new ImageIcon(MyImages.dt0));
			else if(Tower.allTowers[displayedUpgradeID] instanceof Scanner)
				Tower.allTowers[displayedUpgradeID].setIcon(new ImageIcon(MyImages.scanner0));
			else if(Tower.allTowers[displayedUpgradeID] instanceof Encrypter)
				Tower.allTowers[displayedUpgradeID].setIcon(new ImageIcon(MyImages.encrypter0));
		}
	}
	
	public static void showCureInfo()
	{
		upgradesInfo.setText(" Cure Tower:\n $50\n Requires Back Up\n Cures infected towers");
	}

	public static void getUpgradeID(int upgradePath)
	{
		int upgradeID = 0;
		/**Explanation for upgradeID in upgradeTower(int upgradeID)**/
		
		//warn user if in tutorial
		if(Game.tutorial && Game.tutorialSlide < 13)
		{
			Object[] options = {"Oops. I'll go back.", "Stop bothering me!"};
			int choice = JOptionPane.showOptionDialog(Game.gf, "You're not supposed to upgrade that yet!", 
					"WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, 0);
			if(choice == 0)
				return;
			else if(choice == 1)
				Game.gamePanel.disableTutorial();
		}
		
		System.out.println("CLICK REGISTERED");
			
		// intentionally no breaks, therefore it adds 100 each time
		switch(typeOfTower)
		{
			case NONE:						return;
			case COMMUNICATIONS_TOWER:		upgradeID += 100;
			case FIREWALL:					upgradeID += 100;
			case SCANNER:					upgradeID += 100;
			case NUMBER_GENERATOR:			upgradeID += 100;
			case DISC_THROWER:				upgradeID += 100;
											break;
		}
		
		if (upgradePath == 1)
		{
			upgradeID += 10;
			
			upgradeID += Tower.allTowers[displayedUpgradeID].upgradesInPath1;
			
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
		Tower tower = Tower.allTowers[displayedUpgradeID];
		
		/**UPGARADE ID == (TowerType + upgrade path + upgrade number)**/
		switch (upgradeID) 
		{
			case 111:						//Harder Discs
											tower.projectileDurability = 2;
											System.out.println("projectile durability++");
											break;
			case 112:						//Even Harder Discs?
											tower.projectileDurability = 3;
											System.out.println("projectile durability++");
											break;
			case 113:						//Faster attack speed?,etc.
											tower.timerReset = 24;
											System.out.println("attack speed++");
											break;
											
			case 121:						//increase damage
											tower.damage += 15;
											System.out.println("damage++");
											break;
			case 122:						tower.damage += 10;
											System.out.println("damage++");
											break;
											
			case 131:						tower.range += tower.range / 6;
											System.out.println("range++");
											
											//special case for tutorial slide 13
											if(Game.tutorialSlide == 13)
												Game.gamePanel.nextSlide();
											
											//update range indicator
											tower.rangeIndicator = new Ellipse2D.Double(tower.getCenterX()-tower.range, 
												tower.getCenterY()-tower.range, tower.range*2, tower.range*2);
											break;
										
			case 132:						tower.range += tower.range / 6;
											System.out.println("range++");
											
											//update range indicator
											tower.rangeIndicator = new Ellipse2D.Double(tower.getCenterX()-tower.range, 
												tower.getCenterY()-tower.range, tower.range*2, tower.range*2);
											break;
											
			case 133:						tower.backedUp = true;
											System.out.println("Backups Added");
											break;
											
			case 211:						tower.splashEffect = true;
											tower.rangeOfSplash = 1.2;
											System.out.println("splash effect = true");
											break;
										
			case 212:						tower.rangeOfSplash = 1.5;
											System.out.println("disruptor range enhanced");
											break;
										
			case 221:						tower.damage = 5;
											tower.lethalRandoms = true;
											Tower.sprites[displayedUpgradeID].setIcon(NumberGenerator.lethalIcon);
											System.out.println("killer numbers");
											break;
											
			case 231:						tower.range += tower.range / 4;
											System.out.println("wider range");
											
											//update range indicator
											tower.rangeIndicator = new Ellipse2D.Double(tower.getCenterX()-tower.range, 
												tower.getCenterY()-tower.range, tower.range*2, tower.range*2);
											break;
											
			case 311:						Tower.allTowers[displayedUpgradeID].damage = .45;
											System.out.println("damage++");
											break;
			case 312:						Tower.allTowers[displayedUpgradeID].damage = .55;
											System.out.println("damage++");
											break;
			case 321:						Tower.allTowers[displayedUpgradeID].backedUp = true;
											System.out.println("Backups Added");
											break;
			case 322:						((Scanner)Tower.allTowers[displayedUpgradeID]).disableWorms = true;
											System.out.println("disable worms");
											break;
			
			case 331:						((Scanner)Tower.allTowers[displayedUpgradeID]).increaseArcAngle(15);
											System.out.println("range++");
											
											//update scanner range
											tower.scan = new Arc2D.Double(tower.getX(), tower.getY(), tower.getRange(), tower.getRange(), 0, ((Scanner)tower).getArcAngle(), Arc2D.PIE);
											break;
			case 332:						((Scanner)Tower.allTowers[displayedUpgradeID]).increaseArcAngle(15);
											System.out.println("range++");
											
											//update scanner range
											tower.scan = new Arc2D.Double(tower.getX(), tower.getY(), tower.getRange(), tower.getRange(), 0, ((Scanner)tower).getArcAngle(), Arc2D.PIE);
											break;
											
			case 411:						((FireWall)Tower.allTowers[displayedUpgradeID]).killsPerRound = 20;
											break;
			case 412:						((FireWall)Tower.allTowers[displayedUpgradeID]).killsPerRound = 25;
											break;
			case 413:						((FireWall)Tower.allTowers[displayedUpgradeID]).killsPerRound = 40;
											break;
			case 421:						((FireWall)Tower.allTowers[displayedUpgradeID]).damage = 200;
											break;
			case 422:						((FireWall)Tower.allTowers[displayedUpgradeID]).damage = 300;
											break;
			case 431:						((FireWall)Tower.allTowers[displayedUpgradeID]).regenerate = true;
											((FireWall)Tower.allTowers[displayedUpgradeID]).regenerationInterval = 300;
											break;
			case 432:						((FireWall)Tower.allTowers[displayedUpgradeID]).regenerationInterval = 240;
											break;
			}
		
		Tower.allTowers[displayedUpgradeID].addUpgradeOptions(displayedUpgradeID);
	}
}
