package clientfiles;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
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
 * 1/11/15:
 * 		New Tower upgrades added
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
	public static JButton   connect				= new JButton();
	public static JPanel    upgrade             = new JPanel();
	public static int       displayedUpgradeID  = 1000;
	
	public static JButton   upgradePath1   		= new JButton();
	public static JButton   upgradePath2   		= new JButton();
	public static JButton   upgradePath3 	    = new JButton();
	
	public static JTextArea statistics   		= new JTextArea();
	public static JTextArea upgradesInfo 		= new JTextArea();
	private static JScrollPane infoScroll 		= new JScrollPane(upgradesInfo);
	
	public static TowerType typeOfTower  		= TowerType.NONE;
	public static boolean	upgradesActive		= false;
	
	public static void initializeUpgrades()
	{
		int height = Game.gamePanel.getHeight() / 10;
		int width = Game.gamePanel.getWidth();
		
		//format the upgrades panel
		upgrade.setBounds(0, height * 9, width, height);
		upgrade.setBackground(Color.CYAN);
		upgrade.setVisible(true);
		upgrade.setLayout(null);
		
		//set bounds of upgrade buttons
		upgradePath1.setBounds(width/10 + 100, 10, 150, height-20);
		upgradePath2.setBounds(width/10 + 255, 10, 150, height-20);
		upgradePath3.setBounds(width/10 + 410, 10, 150, height-20);
		
		//snazzify the buttons
		Image buttonOpen = MyImages.buttonOpen.getScaledInstance(150, height-20, Image.SCALE_SMOOTH);
		Image buttonClosed = MyImages.buttonClosed.getScaledInstance(150, height-20, Image.SCALE_SMOOTH);
		
		upgradePath1.setIcon(new ImageIcon(buttonOpen));
		upgradePath2.setIcon(new ImageIcon(buttonOpen));
		upgradePath3.setIcon(new ImageIcon(buttonOpen));
		
		upgradePath1.setRolloverIcon(new ImageIcon(buttonClosed));
		upgradePath2.setRolloverIcon(new ImageIcon(buttonClosed));
		upgradePath3.setRolloverIcon(new ImageIcon(buttonClosed));
		
		upgradePath1.setBorder(null);
		upgradePath2.setBorder(null);
		upgradePath3.setBorder(null);
		
		upgradePath1.setFont(new Font("Monospaced", Font.PLAIN, 16));
		upgradePath2.setFont(new Font("Monospaced", Font.PLAIN, 16));
		upgradePath3.setFont(new Font("Monospaced", Font.PLAIN, 16));
		
		upgradePath1.setForeground(new Color(0, 162, 232));
		upgradePath2.setForeground(new Color(0, 162, 232));
		upgradePath3.setForeground(new Color(0, 162, 232));
		
		upgradePath1.setHorizontalTextPosition(SwingConstants.CENTER);
		upgradePath1.setVerticalTextPosition(SwingConstants.CENTER);
		upgradePath2.setHorizontalTextPosition(SwingConstants.CENTER);
		upgradePath2.setVerticalTextPosition(SwingConstants.CENTER);
		upgradePath3.setHorizontalTextPosition(SwingConstants.CENTER);
		upgradePath3.setVerticalTextPosition(SwingConstants.CENTER);
		
		//format JLabels
		statistics.setBounds(5, 5, 100, 80);
		statistics.setBackground(Color.CYAN);
		
		upgradesInfo.setBounds((int) (width / 1.5) - 75, 10, 160, height - 20);
		upgradesInfo.setBackground(Color.CYAN);
		upgradesInfo.setLineWrap(true);
		upgradesInfo.setWrapStyleWord(true);
		upgradesInfo.setCaretPosition(0);
		
		infoScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		infoScroll.setPreferredSize(upgradesInfo.getSize());
		infoScroll.setBounds((int) (width / 1.5) - 75, 10, 160, height - 20);
		infoScroll.setBorder(null);
		
		//format side buttons
		Image deleteImg = MyImages.delete.getScaledInstance(100, (int)(height/3), Image.SCALE_SMOOTH);
		deleteTower.setBounds(105+(width/10-25)/2-37, 3, 100, (int)(height * .3));
		deleteTower.setIcon(new ImageIcon(deleteImg));
		
		Image cureImg = MyImages.cure.getScaledInstance(100, (int)(height/3), Image.SCALE_SMOOTH);
		cureTower.setBounds(105+(width/10-25)/2-37, 3 + (int)(height * .3), 100, height/3);
		cureTower.setIcon(new ImageIcon(cureImg));
		
		Image connectImg = MyImages.connect.getScaledInstance(100, (int)(height/3), Image.SCALE_SMOOTH);
		connect.setBounds(105+(width/10-25)/2-37, 2 + 2*(int)(height * .3), 100, height/3);
		connect.setIcon(new ImageIcon(connectImg));
		
		System.out.println(connect.getSize());
		
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
		    	getUpgradeID(Tower.allTowers[displayedUpgradeID], 1, false);
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
		    	getUpgradeID(Tower.allTowers[displayedUpgradeID], 2, false);
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
		    	getUpgradeID(Tower.allTowers[displayedUpgradeID], 3, false);
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
		
		connect.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				showConnectInfo();
			}
		});
		
		connect.addActionListener(new ActionListener()
		{
			
		    public void actionPerformed(ActionEvent e)
		    {
		    	connectTower();
		    }
		});
	}
	
	public static void showUpgradePanel(int id)
	{
		typeOfTower = Tower.allTowers[id].type;
		System.out.println(typeOfTower);
		
		displayedUpgradeID = id;
		
		updateStatistics();
		upgrade.add(deleteTower);
		upgrade.remove(cureTower);
		upgrade.remove(connect);
		//only add cure tower for kill-able towers
		if(typeOfTower != TowerType.FIREWALL && typeOfTower != TowerType.COMMUNICATIONS_TOWER)
		{
			upgrade.add(cureTower);
			
			//encrypters get to cure, but not connect
			if(typeOfTower != TowerType.ENCRYPTER)
				upgrade.add(connect);
			
			//temporarily disable connect if there is another tower uploading
			if(CommunicationsTower.uploadingTower)
				connect.setEnabled(false);
			else
				connect.setEnabled(true);
		}
		
		if (typeOfTower == TowerType.DISC_THROWER)
			DiscThrower.allTowers[displayedUpgradeID].addUpgradeOptions(displayedUpgradeID);
		else if (typeOfTower == TowerType.NUMBER_GENERATOR)
			NumberGenerator.allTowers[displayedUpgradeID].addUpgradeOptions(displayedUpgradeID);
		else if (typeOfTower == TowerType.SCANNER)
			Scanner.allTowers[displayedUpgradeID].addUpgradeOptions(displayedUpgradeID);
		else if (typeOfTower == TowerType.FIREWALL)
			Scanner.allTowers[displayedUpgradeID].addUpgradeOptions(displayedUpgradeID);
		else if (typeOfTower == TowerType.ENCRYPTER)
			Encrypter.allTowers[displayedUpgradeID].addUpgradeOptions(displayedUpgradeID);
		else if (typeOfTower == TowerType.COMMUNICATIONS_TOWER)
			CommunicationsTower.allTowers[displayedUpgradeID].addUpgradeOptions(displayedUpgradeID);
		else if (typeOfTower == TowerType.ANTIVIRUS_SOFTWARE)
			AntiVirusSoftware.allTowers[displayedUpgradeID].addUpgradeOptions(displayedUpgradeID);
		
		//wrap text on upgrade buttons
		upgradePath1.setText("<html><center>"+upgradePath1.getText()+"</center></html>");
		upgradePath2.setText("<html><center>"+upgradePath2.getText()+"</center></html>");
		upgradePath3.setText("<html><center>"+upgradePath3.getText()+"</center></html>");
		
		upgrade.add(upgradePath1);
		upgrade.add(upgradePath2);
		upgrade.add(upgradePath3);
		//upgrade.add(upgradesInfo);
		upgrade.add(infoScroll);
		Game.gamePanel.addToLayeredPane(upgrade, JLayeredPane.DRAG_LAYER);
		
		//move tutorial out of the way
		Game.gamePanel.moveTutorial();
		upgradesActive = true;
	}
	
	public static void removeUpgradePanel()
	{	
		if (GamePanel.numTowers < displayedUpgradeID)
			displayedUpgradeID = GamePanel.numTowers--;
		
		Game.gamePanel.remove(statistics);
		Game.gamePanel.remove(deleteTower);
		Game.gamePanel.remove(cureTower);
		Game.gamePanel.remove(connect);
		Game.gamePanel.remove(upgradePath1);
		Game.gamePanel.remove(upgradePath2);
		Game.gamePanel.remove(upgradePath3); 
		Game.gamePanel.remove(upgradesInfo);
		
		Game.gamePanel.removeFromLayeredPane(upgrade);
		displayedUpgradeID = 98;
		upgradesInfo.setText("");
		upgradesActive = false;
	}
	
	public static void updateStatistics()
	{
		//Tower.allTowers[displayedUpgradeID].accuracy = Tower.allTowers[displayedUpgradeID].hits / Tower.allTowers[displayedUpgradeID].shotsFired;
		
		statistics.setText("    Kills: " + Tower.allTowers[displayedUpgradeID].kills + "\n   Shots Fired: " 
										 + Tower.allTowers[displayedUpgradeID].shotsFired + "\n   Damage: "
										 + Tower.allTowers[displayedUpgradeID].damage);
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
			Tower.allTowers[displayedUpgradeID].setInfected(false);
			Tower.allTowers[displayedUpgradeID].health = 50;
			
			//reset image
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
	
	public static void connectTower()
	{
		boolean activeCommTower = false;
		
		//search for an active info hub
		for (int i = 0; i < GamePanel.numTowers; i++)
		{
			if (Tower.allTowers[i] == null)
				return;
			if (Tower.allTowers[i] instanceof CommunicationsTower)
			{
				if (((CommunicationsTower) Tower.allTowers[i]).informationHub)
					activeCommTower = true;
			}
		}
		
		//connect tower if have money, there is an info hub, and the tower isn't already connected
		if (!Tower.allTowers[displayedUpgradeID].isConnected && activeCommTower && Game.makePurchase(500))
		{	
			Tower.allTowers[displayedUpgradeID].isConnected = true;
			CommunicationsTower.connectTower(Tower.allTowers[displayedUpgradeID]);
		}
	}
	
	public static void showConnectInfo()
	{
		upgradesInfo.setText(" Connect:\n$500\n\n Requires a communications tower with the information hub upgrade");
	}
	
	public static void getUpgradeID(Tower tower, int upgradePath, boolean hubUpdating)
	{
		int upgradeID = 0;
		/**Explanation for upgradeID in upgradeTower(int upgradeID)**/
		
		//warn user if in tutorial
		if(Game.tutorial && Game.tutorialSlide < 13 && !hubUpdating)
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
			case ANTIVIRUS_SOFTWARE:		upgradeID += 100;
			case COMMUNICATIONS_TOWER:		upgradeID += 100;
			case ENCRYPTER:					upgradeID += 100;
			case FIREWALL:					upgradeID += 100;
			case SCANNER:					upgradeID += 100;
			case NUMBER_GENERATOR:			upgradeID += 100;
			case DISC_THROWER:				upgradeID += 100;
											break;
		}
		
		if (upgradePath == 1)
		{
			upgradeID += 10;
			
			upgradeID += tower.upgradesInPath1;
			
			if (hubUpdating)
				Game.addMoney(tower.getCostOfUpgrade(1));
			if (Game.makePurchase(tower.getCostOfUpgrade(1)))
			{
				tower.realValue += tower.getCostOfUpgrade(1);
				tower.upgradesInPath1++;
				tower.displayUpgradeInfo(1);
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
			
			upgradeID += tower.upgradesInPath2;
			
			if (hubUpdating)
				Game.addMoney(tower.getCostOfUpgrade(2));
			if (Game.makePurchase(tower.getCostOfUpgrade(2)))
			{
				tower.realValue += tower.getCostOfUpgrade(2);
				tower.upgradesInPath2++;
				tower.displayUpgradeInfo(2);
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
			
			upgradeID += tower.upgradesInPath3;
			
			if (hubUpdating)
				Game.addMoney(tower.getCostOfUpgrade(3));
			if (Game.makePurchase(tower.getCostOfUpgrade(3)))
			{
				tower.realValue += tower.getCostOfUpgrade(3);
				tower.upgradesInPath3++;
				tower.displayUpgradeInfo(3);
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
		upgradeTower(upgradeID, tower);
	}
	
	public static void upgradeTower(int upgradeID, Tower tower)
	{
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
											tower.timerReset -= 15;
											System.out.println("attack speed++");
											break;
											
			case 121:						//increase damage
											tower.damage += 10;
											System.out.println("damage++");
											break;
			case 122:						tower.damage += 15;
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
											tower.rangeOfSplash = 1.1;
											System.out.println("splash effect = true");
											break;
										
			case 212:						tower.rangeOfSplash = 1.3;
											System.out.println("disruptor range enhanced");
											break;
										
			case 221:						tower.damage += 5;
											tower.lethalRandoms = true;
											Tower.sprites[displayedUpgradeID].setIcon(NumberGenerator.lethalIcon);
											System.out.println("killer numbers");
											break;
			case 222:						tower.damage += 10;
											break;
											
			case 231:						tower.range += tower.range / 4;
											System.out.println("wider range");
											
											//update range indicator
											tower.rangeIndicator = new Ellipse2D.Double(tower.getCenterX()-tower.range, 
												tower.getCenterY()-tower.range, tower.range*2, tower.range*2);
											break;
			case 232:						tower.range += tower.range / 4;
											System.out.println("wider range");
											
											//update range indicator
											tower.rangeIndicator = new Ellipse2D.Double(tower.getCenterX()-tower.range, 
												tower.getCenterY()-tower.range, tower.range*2, tower.range*2);
											break;
											
			case 311:						Tower.allTowers[displayedUpgradeID].damage += .05;
											System.out.println("damage++");
											break;
			case 312:						Tower.allTowers[displayedUpgradeID].damage += .1;
											System.out.println("damage++");
											break;
			case 321:						Tower.allTowers[displayedUpgradeID].backedUp = true;
											System.out.println("Backups Added");
											break;
			case 322:						((Scanner)Tower.allTowers[displayedUpgradeID]).disableWorms = true;
											System.out.println("disable worms");
											break;
			
			case 331:						((Scanner)Tower.allTowers[displayedUpgradeID]).increaseArcAngle(20);
											System.out.println("arc angle++");
											
											//update scanner range
											tower.scan = new Arc2D.Double(tower.getX(), tower.getY(), tower.getRange(), tower.getRange(), 0, ((Scanner)tower).getArcAngle(), Arc2D.PIE);
											break;
			case 332:						((Scanner)Tower.allTowers[displayedUpgradeID]).increaseArcAngle(25);
											System.out.println("arc angle++");
											
											//update scanner range
											tower.scan = new Arc2D.Double(tower.getX(), tower.getY(), tower.getRange(), tower.getRange(), 0, ((Scanner)tower).getArcAngle(), Arc2D.PIE);
											break;
											
											
			case 411:						((FireWall)Tower.allTowers[displayedUpgradeID]).killsPerRound = 20;
											break;
			case 412:						((FireWall)Tower.allTowers[displayedUpgradeID]).killsPerRound = 25;
											break;
			case 413:						((FireWall)Tower.allTowers[displayedUpgradeID]).killsPerRound = 30;
											break;
			case 421:						((FireWall)Tower.allTowers[displayedUpgradeID]).damage = 400;
											break;
			case 422:						((FireWall)Tower.allTowers[displayedUpgradeID]).damage = 500;
											break;
			case 431:						((FireWall)Tower.allTowers[displayedUpgradeID]).regenerate = true;
											((FireWall)Tower.allTowers[displayedUpgradeID]).regenerationInterval = 600;
											break;
			case 432:						((FireWall)Tower.allTowers[displayedUpgradeID]).regenerationInterval = 480;
											break;
											
											
			case 511:						((Encrypter)Tower.allTowers[displayedUpgradeID]).killsPerRound = 20;
											break;
			case 512:						((Encrypter)Tower.allTowers[displayedUpgradeID]).killsPerRound = 25;
											break;
			case 521:						((Encrypter)Tower.allTowers[displayedUpgradeID]).deencrypter = true;
											break;
			case 531:						((Encrypter)Tower.allTowers[displayedUpgradeID]).regenerate = true;
											((Encrypter)Tower.allTowers[displayedUpgradeID]).regenerationInterval = 600;
											break;
			case 532:						((Encrypter)Tower.allTowers[displayedUpgradeID]).regenerationInterval = 480;
											
			
			case 611:						((CommunicationsTower) Tower.allTowers[displayedUpgradeID]).removeUpgrades();
											((CommunicationsTower) Tower.allTowers[displayedUpgradeID]).damageToAdd += 5;
											((CommunicationsTower) Tower.allTowers[displayedUpgradeID]).upgradeTowers();
											break;
			case 612:						((CommunicationsTower) Tower.allTowers[displayedUpgradeID]).removeUpgrades();
											((CommunicationsTower) Tower.allTowers[displayedUpgradeID]).rangeToAdd += Game.widthOfGamePanel * .01;
											((CommunicationsTower) Tower.allTowers[displayedUpgradeID]).upgradeTowers();
											break;
			case 613:						((CommunicationsTower) Tower.allTowers[displayedUpgradeID]).removeUpgrades();
											((CommunicationsTower) Tower.allTowers[displayedUpgradeID]).damageToAdd += 5;
											((CommunicationsTower) Tower.allTowers[displayedUpgradeID]).upgradeTowers();
											break;
			case 621:						((CommunicationsTower) Tower.allTowers[displayedUpgradeID]).removeUpgrades();
											((CommunicationsTower) Tower.allTowers[displayedUpgradeID]).speedToAdd -= 5;
											((CommunicationsTower) Tower.allTowers[displayedUpgradeID]).upgradeTowers();
											break;
			case 622:						((CommunicationsTower) Tower.allTowers[displayedUpgradeID]).shareRange = true;
											break;
			case 631:						Tower.allTowers[displayedUpgradeID].range += Game.widthOfGamePanel * .01;

											//update range indicator
											tower.rangeIndicator = new Ellipse2D.Double(tower.getCenterX()-tower.range, 
												tower.getCenterY()-tower.range, tower.range*2, tower.range*2);
											break;
			case 632:						Tower.allTowers[displayedUpgradeID].range += Game.widthOfGamePanel * .02;

											//update range indicator
											tower.rangeIndicator = new Ellipse2D.Double(tower.getCenterX()-tower.range, 
												tower.getCenterY()-tower.range, tower.range*2, tower.range*2);
											break;
			case 633:						((CommunicationsTower) Tower.allTowers[displayedUpgradeID]).informationHub = true;
											
											if (!CommunicationsTower.mesh && !CommunicationsTower.star)
											{
												Object[] options = {"Star", "Mesh"};
												int choice = JOptionPane.showOptionDialog(Game.gf, "What type of hub would you like?", 
														"WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, 0);
												if(choice == 0)
													CommunicationsTower.star = true;
												else
													CommunicationsTower.mesh = true;
											}
											break;
											
											
			case 711:						Tower.allTowers[displayedUpgradeID].projectileDurability = 2;
											break;
			case 712:						Tower.allTowers[displayedUpgradeID].projectileDurability = 3;
											break;
			case 713:						Tower.allTowers[displayedUpgradeID].timerReset -= 5;
											break;
											
			case 721:						Tower.allTowers[displayedUpgradeID].damage += 5;
											break;
			case 722:						Tower.allTowers[displayedUpgradeID].damage += 10;
											break;
			case 723:						Tower.allTowers[displayedUpgradeID].damage += 10;
											break;
											
			case 731:						Tower.allTowers[displayedUpgradeID].range += Game.heightOfGamePanel * .1;
			
											//update range indicator
											tower.rangeIndicator = new Ellipse2D.Double(tower.getCenterX()-tower.range, 
												tower.getCenterY()-tower.range, tower.range*2, tower.range*2);
											break;
			case 732:						Tower.allTowers[displayedUpgradeID].range += Game.heightOfGamePanel * .1;
			
											//update range indicator
											tower.rangeIndicator = new Ellipse2D.Double(tower.getCenterX()-tower.range, 
												tower.getCenterY()-tower.range, tower.range*2, tower.range*2);
											break;
											
			case 733:						tower.backedUp = true;
											break;
		}
		// update all uploaded towers to hubs
		if (tower.isConnected)
			CommunicationsTower.updateConnectedTowers(tower);
		
		//update stats box
		updateStatistics();
		
		//update upgrade buttons
		tower.addUpgradeOptions(displayedUpgradeID);
	}
}
