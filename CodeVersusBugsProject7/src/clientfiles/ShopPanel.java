package clientfiles;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
/**ShopPanel.java
 * Handles the shop panel, which holds buttons for buying towers and an info label.
 * Additionally, this class checks the placement of new towers for collision with other towers
 * and the path.
 * @author Ahmadul
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
public class ShopPanel extends JPanel implements ActionListener
{
	public static TowerType towerToPlace = TowerType.NONE;
	private JButton buyDiscThrower;
	private JButton buyNumberGenerator;
	private JButton buyScanner;
	private JButton buyFireWall;
	private JButton buyEncrypter;
	private JButton buyCommunicationsTower;
	private JButton buyAVS;
	private JButton openTyper;
	
	private TypingWindow typer;
	
	public static JLabel info = new JLabel("");
	public static boolean warned = false;
	public static boolean mouseOnTrack = false;
	
	private Icon dtImage = DiscThrower.icon;
	private Icon ngImage = NumberGenerator.icon;
	private Icon scImage = Scanner.icon;
	private Icon fwImage = new ImageIcon(MyImages.firewallShopImage);
	private Icon enImage = Encrypter.icon;
	private Icon ctImage = CommunicationsTower.icon;
	private Icon avsImage = FastTower.icon;
	
	public static int timer=0;
	
	public ShopPanel()
	{
		buyDiscThrower = new JButton(dtImage);
		buyNumberGenerator = new JButton(ngImage);
		buyScanner = new JButton(scImage);
		buyFireWall = new JButton(fwImage);
		buyEncrypter = new JButton(enImage);
		buyCommunicationsTower = new JButton(ctImage);
		buyAVS = new JButton(avsImage);
		openTyper = new JButton(new ImageIcon(MyImages.openTyper));
		typer = new TypingWindow();
		
		//locations and sizes of components are subject to change
		info.setBorder(BorderFactory.createLineBorder(Color.black));
		info.setOpaque(true);
		info.setBounds(5, 10, 75, 55);
		buyDiscThrower.setBounds(20, 75, 51, 63);
		buyNumberGenerator.setBounds(20, 150, 51, 50);
		buyScanner.setBounds(20, 225, 51, 63);
		buyFireWall.setBounds(20, 305, 51, 50);
		buyEncrypter.setBounds(20, 370, 51, 63);
		buyCommunicationsTower.setBounds(20, 440, 51, 50);
		buyAVS.setBounds(20, 500, 51, 50);
		openTyper.setBounds(20, 570, 50, 50);
		
		//makes button background transparent
		buyCommunicationsTower.setBackground(new Color(0,0,0,0));
		//scale image of avs
		Image scaledAVS = MyImages.antiVirusSoftware.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		buyAVS.setIcon(new ImageIcon(scaledAVS));
		
		buyDiscThrower.addActionListener(this);
		buyNumberGenerator.addActionListener(this);
		buyScanner.addActionListener(this);
		buyFireWall.addActionListener(this);
		buyEncrypter.addActionListener(this);
		buyCommunicationsTower.addActionListener(this);
		buyAVS.addActionListener(this);
		openTyper.addActionListener(this);
		
		//set tool tip texts
		buyDiscThrower.setToolTipText("Buy a Disc Thrower");
		buyNumberGenerator.setToolTipText("Buy a Number Generator");
		buyScanner.setToolTipText("Buy a Scanner");
		buyFireWall.setToolTipText("Buy a Firewall");
		buyEncrypter.setToolTipText("Buy an Encryptor");
		buyCommunicationsTower.setToolTipText("Buy a Communications Tower");
		buyAVS.setToolTipText("Buy a FAST");
		openTyper.setToolTipText("Write some code to make some money on the side");
		
		//add all buttons to panel
		setLayout(null);
		add(info);
		add(buyDiscThrower);
		add(buyNumberGenerator);
		add(buyScanner);
		add(buyFireWall);
		add(buyEncrypter);
		add(buyCommunicationsTower);
		add(buyAVS);
		add(openTyper);
		
		buyDiscThrower.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				if (!warned)
				{
            		if (Game.getMoney() < DiscThrower.cost)
            			changeInfo("$"+DiscThrower.cost, true);
            		else
            			changeInfo("$"+DiscThrower.cost, false);
            	}
			}
		});
		
		buyNumberGenerator.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				if (!warned)
				{
            		if (Game.getMoney() < NumberGenerator.cost)
            			changeInfo("$600", true);
            		else
            			changeInfo("$600", false);
            	}
			}
		});
		
		buyScanner.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				if (!warned)
				{
            		if (Game.getMoney() < Scanner.cost)
            			changeInfo("$1000", true);
            		else
            			changeInfo("$1000", false);
            	}
			}
		});
		
		buyFireWall.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				if (!warned)
				{
            		if (Game.getMoney() < FireWall.cost)
            			changeInfo("$200", true);
            		else
            			changeInfo("$200", false);
            	}
			}
		});
		
		buyEncrypter.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				if (!warned)
				{
            		if (Game.getMoney() < Encrypter.cost)
            			changeInfo("$"+Encrypter.cost, true);
            		else
            			changeInfo("$"+Encrypter.cost, false);
            	}
			}
		});
		
		buyCommunicationsTower.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				if (!warned)
				{
            		if (Game.getMoney() < CommunicationsTower.cost)
            			changeInfo("$"+CommunicationsTower.cost, true);
            		else
            			changeInfo("$"+CommunicationsTower.cost, false);
            	}
			}
		});
		
		buyAVS.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				if (!warned)
				{
            		if (Game.getMoney() < FastTower.cost)
            			changeInfo("$"+FastTower.cost, true);
            		else
            			changeInfo("$"+FastTower.cost, false);
            	}
			}
		});
	}
	public static void changeInfo(String text, boolean warning)
	{
		// sets text
		info.setText("<html><div style=\"text-align: center;\">"+text+"</html>");
		
		// if it is a warning message set background red
		if (warning)
		{	
			warned = true;
			info.setBackground(Color.red);
		}
		//reset timer so that text fades after 2 seconds
		timer = 60;
	}
	
	public static boolean checkPlacement()
	{
		int x = GamePanel.getMouseX();
		int y = GamePanel.getMouseY();
		int offset = 0;
		Rectangle proposedTower = new Rectangle();
		int centerOfOtherTowerX;
		int centerOfOtherTowerY;
		int radiusOfOtherTower;
		
		if (mouseOnTrack && towerToPlace != TowerType.FIREWALL && towerToPlace != TowerType.ENCRYPTER)
		{
			return false;
		}
		else if (mouseOnTrack && (towerToPlace == TowerType.FIREWALL || towerToPlace == TowerType.ENCRYPTER))
		{
			boolean comparingToFireWall = false;
			
			/**For checking proximity to other towers*/
			for (int j = 0; j < Tower.allTowers.length; j++)
			{
				if (Tower.allTowers[j] == null)
					break;
				
				// get centers of other towers
				centerOfOtherTowerX = Tower.allTowers[j].getCenterX();
				centerOfOtherTowerY = Tower.allTowers[j].getCenterY();
				radiusOfOtherTower = Tower.allTowers[j].getRadius();
				
				if (Tower.allTowers[j] instanceof FireWall)
					comparingToFireWall = true;
				
				// check top left and bottom right corners
				if (comparingToFireWall && x + offset >= centerOfOtherTowerX - radiusOfOtherTower && x + offset <= centerOfOtherTowerX + radiusOfOtherTower && y + offset >= centerOfOtherTowerY - radiusOfOtherTower && y + offset <= centerOfOtherTowerY + radiusOfOtherTower)
					return false;
				// check top right and bottom left corners
				else if (comparingToFireWall && x + offset >= centerOfOtherTowerX - radiusOfOtherTower && x + offset <= centerOfOtherTowerX + radiusOfOtherTower && y - offset >= centerOfOtherTowerY - radiusOfOtherTower && y - offset <= centerOfOtherTowerY + radiusOfOtherTower)
					return false;
			}
			return true;
		}
		else if (!mouseOnTrack && (towerToPlace == TowerType.FIREWALL || towerToPlace == TowerType.ENCRYPTER))
			return false;
		
		//simplified bounds checking against the track
		//first, create a rectangle for the proposed tower
		if(towerToPlace == TowerType.DISC_THROWER)
		{
			proposedTower = new Rectangle(x-DiscThrower.icon.getIconWidth()/2, y-DiscThrower.icon.getIconHeight()/2, 
					DiscThrower.icon.getIconWidth(), DiscThrower.icon.getIconHeight());
		}
		else if(towerToPlace == TowerType.NUMBER_GENERATOR)
		{
			proposedTower = new Rectangle(x-NumberGenerator.icon.getIconWidth()/2, y-NumberGenerator.icon.getIconHeight()/2, 
					NumberGenerator.icon.getIconWidth(), NumberGenerator.icon.getIconHeight());
		}
		else if(towerToPlace == TowerType.SCANNER)
		{
			proposedTower = new Rectangle(x-Scanner.icon.getIconWidth()/2, y-Scanner.icon.getIconHeight()/2, 
					Scanner.icon.getIconWidth(), Scanner.icon.getIconHeight());
		}
		else if(towerToPlace == TowerType.COMMUNICATIONS_TOWER)
		{
			proposedTower = new Rectangle(x-CommunicationsTower.icon.getIconWidth()/2, y-CommunicationsTower.icon.getIconHeight()/2, 
					CommunicationsTower.icon.getIconWidth(), CommunicationsTower.icon.getIconHeight());
		}
		else if(towerToPlace == TowerType.FAST_TOWER)
		{
			proposedTower = new Rectangle(x-FastTower.icon.getIconWidth()/2, y-FastTower.icon.getIconHeight()/2, 
					FastTower.icon.getIconWidth(), FastTower.icon.getIconHeight());
		}
		//next, loop through all path parts and check for intersection with proposed tower
		for(int pathPart = 0; pathPart < Game.gamePanel.path.length; pathPart++)
		{
			if(Game.gamePanel.path[pathPart].intersects(proposedTower))
				return false;
		}
		
		//loop through all towers and check for intersection with proposed tower
		for(int t = 0; t < Tower.allTowers.length; t++)
		{
			if(Tower.allTowers[t] == null)
			{
				break;
			}
			else
			{
				Rectangle curr = new Rectangle(Tower.allTowers[t].getX(), Tower.allTowers[t].getY(), 
						Tower.sprites[t].getWidth(), Tower.sprites[t].getHeight());
				if(curr.intersects(proposedTower))
					return false;
			}
		}
		
		return true;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton temp = (JButton) e.getSource();
		TowerType towerType = TowerType.NONE;
		
		if (temp == buyDiscThrower)
		{
			towerType = TowerType.DISC_THROWER;
			if (towerToPlace == TowerType.DISC_THROWER)
			{
				towerToPlace = TowerType.NONE;
				changeInfo("Tower Deselected", false);
				return;
			}
			//warn user before buying if tutorial on
			if(Game.tutorial && Game.tutorialSlide < 7)
			{
				Object[] options = {"Oops. I'll go back.", "Stop bothering me!"};
				int choice = JOptionPane.showOptionDialog(Game.gf, "Are you sure you want to buy a Disc Thrower?", 
						"WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, 0);
				if(choice == 1)
					Game.gamePanel.disableTutorial();
				else if(choice == 0)
					return;
			}
			//special case if user is on tutorial slide 6
			if(Game.tutorialSlide == 7)
				Game.gamePanel.nextSlide();
			
			changeInfo("Disc Thrower Selected",false);
		}
		else if(temp == buyNumberGenerator)
		{
			towerType = TowerType.NUMBER_GENERATOR;
			if (towerToPlace == TowerType.NUMBER_GENERATOR)
			{
				towerToPlace = TowerType.NONE;
				changeInfo("Tower Deselected", false);
				return;
			}
			//warn user before buying if tutorial on
			if(Game.tutorial && Game.tutorialSlide <= 7)
			{
				Object[] options = {"Oops. I'll go back.", "Stop bothering me!"};
				int choice = JOptionPane.showOptionDialog(Game.gf, "Are you sure you want to buy a Number Generator?", 
						"WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, 0);
				if(choice == 1)
					Game.gamePanel.disableTutorial();
				else if(choice == 0)
					return;
			}
			//specialcase for tutorial slide 105
			if(Game.tutorial && Game.tutorialSlide == 105)
				Game.gamePanel.nextSlide();
			
			changeInfo("Number Generator Selected",false);
		}
		else if (temp == buyScanner)
		{
			towerType = TowerType.SCANNER;
			
			if (towerToPlace == TowerType.SCANNER)
			{
				towerToPlace = TowerType.NONE;
				changeInfo("Tower Deselected", false);
				return;
			}
			//warn user before buying if tutorial on
			if(Game.tutorial && Game.tutorialSlide <= 7)
			{
				Object[] options = {"Oops. I'll go back.", "Stop bothering me!"};
				int choice = JOptionPane.showOptionDialog(Game.gf, "Are you sure you want to buy a Scanner?", 
						"WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, 0);
				if(choice == 1)
					Game.gamePanel.disableTutorial();
				else if(choice == 0)
					return;
			}
			if(Game.tutorial && Game.tutorialSlide == 88)
				Game.gamePanel.nextSlide();
			changeInfo("Scanner Selected",false);
		}
		else if (temp == buyFireWall)
		{
			towerType = TowerType.FIREWALL;
			
			if (towerToPlace == TowerType.FIREWALL)
			{
				towerToPlace = TowerType.NONE;
				changeInfo("Tower Deselected", false);
				return;
			}
			//warn user before buying if tutorial on
			if(Game.tutorial && Game.tutorialSlide <= 7)
			{
				Object[] options = {"Oops. I'll go back.", "Stop bothering me!"};
				int choice = JOptionPane.showOptionDialog(Game.gf, "Are you sure you want to buy a Firewall?", 
						"WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, 0);
				if(choice == 1)
					Game.gamePanel.disableTutorial();
				else if(choice == 0)
					return;
			}
			
			//move to slide 511
			if(Game.tutorial && Game.tutorialSlide == 510)
				Game.gamePanel.nextSlide();
			
			changeInfo("Firewall Selected",false);
		}
		else if (temp == buyEncrypter)
		{
			towerType = TowerType.ENCRYPTER;
			
			//reset if already selected
			if (towerToPlace == TowerType.ENCRYPTER)
			{
				towerToPlace = TowerType.NONE;
				changeInfo("Tower Deselected", false);
				return;
			}
			//warn user before buying if tutorial on
			if(Game.tutorial && Game.tutorialSlide <= 7)
			{
				Object[] options = {"Oops. I'll go back.", "Stop bothering me!"};
				int choice = JOptionPane.showOptionDialog(Game.gf, "Are you sure you want to buy an Encrypter?", 
						"WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, 0);
				if(choice == 1)
					Game.gamePanel.disableTutorial();
				else if(choice == 0)
					return;
			}
			//special case for tutorial slide 117
			if(Game.tutorial && Game.tutorialSlide == 117)
				Game.gamePanel.nextSlide();
			
			changeInfo("Encrypter Selected",false);
		}
		else if (temp == buyCommunicationsTower)
		{
			towerType = TowerType.COMMUNICATIONS_TOWER;
			
			//reset if already selected
			if (towerToPlace == TowerType.COMMUNICATIONS_TOWER)
			{
				towerToPlace = TowerType.NONE;
				changeInfo("Tower Deselected", false);
				return;
			}
			//warn user before buying if tutorial on
			if(Game.tutorial && Game.tutorialSlide <= 7)
			{
				Object[] options = {"Oops. I'll go back.", "Stop bothering me!"};
				int choice = JOptionPane.showOptionDialog(Game.gf, "Are you sure you want to buy an Encrypter?", 
						"WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, 0);
				if(choice == 1)
					Game.gamePanel.disableTutorial();
				else if(choice == 0)
					return;
			}
			
			changeInfo("Comm Tower Selected",false);
		}
		else if (temp == buyAVS)
		{
			towerType = TowerType.FAST_TOWER;
			
			//reset if already selected
			if (towerToPlace == TowerType.FAST_TOWER)
			{
				towerToPlace = TowerType.NONE;
				changeInfo("Tower Deselected", false);
				return;
			}
			//warn user before buying if tutorial on
			if(Game.tutorial && Game.tutorialSlide <= 7)
			{
				Object[] options = {"Oops. I'll go back.", "Stop bothering me!"};
				int choice = JOptionPane.showOptionDialog(Game.gf, "Are you sure you want to buy a Futuristic Advanced Shield Thrower?", 
						"WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, 0);
				if(choice == 1)
					Game.gamePanel.disableTutorial();
				else if(choice == 0)
					return;
			}
			
			changeInfo("FAST Selected",false);
		}
		else if(temp == openTyper)
		{
			typer.setVisible(true);
		}
		
		validateBuy(towerType);
	}
	
	public static void validateBuy(TowerType type)
	{
		if (type == TowerType.DISC_THROWER)
		{
			if(Game.getMoney() >= DiscThrower.cost)
			{
				towerToPlace = TowerType.DISC_THROWER;
			}
			else
				changeInfo("Not Enough Money", true);
		}
		
		else if(type == TowerType.NUMBER_GENERATOR)
		{
			if(Game.getMoney() >= NumberGenerator.cost)
			{
				towerToPlace = TowerType.NUMBER_GENERATOR;
			}
			else
				changeInfo("Not Enough Money!", true);
		}
		
		else if(type == TowerType.SCANNER)
		{
			if(Game.getMoney() >= Scanner.cost)
			{
				towerToPlace = TowerType.SCANNER;
			}
			else
				changeInfo("Not Enough Money!", true);
		}
		
		else if(type == TowerType.FIREWALL)
		{
			if(Game.getMoney() >= FireWall.cost)
			{
				towerToPlace = TowerType.FIREWALL;
			}
			else
				changeInfo("Not Enough Money!", true);
		}
		else if(type == TowerType.ENCRYPTER)
		{
			if(Game.getMoney() >= Encrypter.cost)
			{
				towerToPlace = TowerType.ENCRYPTER;
			}
			else
				changeInfo("Not Enough Money!", true);
		}
		else if(type == TowerType.COMMUNICATIONS_TOWER)
		{
			if(Game.getMoney() >= CommunicationsTower.cost)
			{
				towerToPlace = TowerType.COMMUNICATIONS_TOWER;
			}
			else
				changeInfo("Not Enough Money!", true);
		}
		else if(type == TowerType.FAST_TOWER)
		{
			if(Game.getMoney() >= CommunicationsTower.cost)
			{
				towerToPlace = TowerType.FAST_TOWER;
			}
			else
				changeInfo("Not Enough Money!", true);
		}
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		//draw num gen health bar
		AffineTransform op = new AffineTransform();
		op.translate(20, 150 + ngImage.getIconHeight());
		g2d.drawImage(MyImages.healthBar0, op, null);
		
		//draw FAST health bar
		op = new AffineTransform();
		op.translate(20, 550);
		g2d.drawImage(MyImages.healthBar0, op, null);
	}
}
