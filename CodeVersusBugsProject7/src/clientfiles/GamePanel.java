/**GamePanel.java
 * Contains the code for the main game panel
 * Draws up to 1 DiscThrower at a point that is clicked.
 * 
 * UPDATE LOG:
 * 10/6/14 	- moved map2.png to resources folder
 * 10/13/14 - now creates a single virus that moves with the timer
 * 11/2/14:	now creates 2 viruses, both of which function normally
 * 11/8/14: now uses the setCenterX/Y methods to streamline tower placement
 * 11/12/14:
 * 		lvlManager variable is now a LevelManager rather than a StoryManager
 * 11/15/14:
 * 		now uses ShopPanel.towerToPlace to place towers
 * 		cursor becomes a mini-discthrower when buy disc thrower button is pressed
 * 11/16/14:
 * 		can no longer buy a disc thrower without sufficient resources
 * 11/23/14:
 * 		added hotkeys for buying towers
 * 11/24/14:
 * 		increased scroll speed (2-5)
 * 11/25/2014 - Patrick Kenney
 * 		made it so that if you move over an invalid area, the cursor turns red
 * 11/29/2014:
 * 		scrolling removed
 * 12/7/14:
 * 		tower range indicator disappears when user clicks on map
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
package clientfiles;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.geom.Ellipse2D.Double;
import java.util.Iterator;
import java.util.ListIterator;

import javax.swing.*;

public class GamePanel extends JPanel
{
	private static int mouseX;
	private static int mouseY;
	public static int numTowers=0;
	public LevelManager lvlManager;
	
	public static boolean mouseInUpgradePanel = false;
	public Rectangle[] path;
	
	//cursors
	private Image dtSprite = DiscThrower.icon.getImage();
	private Point dtHotspot = new Point(15, 15);
	private Cursor discThrowerCursor = Toolkit.getDefaultToolkit().createCustomCursor(dtSprite,dtHotspot,"Disc Thrower");
	private Image ngSprite = NumberGenerator.icon.getImage();
	private Point ngHotspot = new Point(15, 15);
	private Cursor numberGeneratorCursor = Toolkit.getDefaultToolkit().createCustomCursor(ngSprite, ngHotspot, "Number Generator");
	private Image ngInvalidSprite = NumberGenerator.invalidIcon.getImage();
	private Cursor invalidNumberGeneratorCursor = Toolkit.getDefaultToolkit().createCustomCursor(ngInvalidSprite, ngHotspot, "Number Generator");
	private Image dtInvalidSprite = DiscThrower.invalidIcon.getImage();
	private Cursor invalidDiscThrowerCursor = Toolkit.getDefaultToolkit().createCustomCursor(dtInvalidSprite, dtHotspot, "Number Generator");
	private Image scSprite = Scanner.icon.getImage();
	private Image scInvalidSprite = Scanner.invalidIcon.getImage();
	private Point scHotspot = new Point(15, 15);
	private Cursor scannerCursor = Toolkit.getDefaultToolkit().createCustomCursor(scSprite,scHotspot,"Scanner");
	private Cursor invalidScannerCursor = Toolkit.getDefaultToolkit().createCustomCursor(scInvalidSprite, scHotspot, "Scanner");
	private Image fwSprite = FireWall.normalIcon.getImage();
	private Image fwInvalidSprite = FireWall.invalidIcon.getImage();
	private Point fwHotspot = new Point(15, 15);
	private Cursor firewallCursor = Toolkit.getDefaultToolkit().createCustomCursor(fwSprite,fwHotspot,"Firewall");
	private Cursor invalidFirewallCursor = Toolkit.getDefaultToolkit().createCustomCursor(fwInvalidSprite, fwHotspot, "Firewall");
	
	
	static private final long serialVersionUID = 1;
	
	private JLabel tutorial;
	private JLayeredPane layeredPane;
	private Ellipse2D.Double tempRangeIndicator;
	public static boolean rangeOn;
	
	public GamePanel()
	{
		this.setFocusable(true);
		lvlManager = new StoryManager();
		
		DrawingPanel drawingPanel = new DrawingPanel();
		drawingPanel.setBounds(0,0,Game.screenSize.width -115, Game.screenSize.height -115);
		
		//shortcuts used in bounds calculation of tutorial
		int w = Game.screenSize.width - 110;
		int h = Game.screenSize.height - 110;
		
		//initialize the tutorial
		tutorial = new JLabel("Welcome to Code Versus Bugs! ... (Click to continue)");
		tutorial.setBounds(0, 9*h/10, w, h/10);
		tutorial.setBackground(new Color(158, 216, 255, 175));
		tutorial.setOpaque(true);
		tutorial.setFont(new Font("Monospaced", Font.PLAIN, w/40));
		tutorial.setHorizontalAlignment(SwingConstants.CENTER);
		tutorial.setVerticalAlignment(SwingConstants.CENTER);
		
		tutorial.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				tutorial.setBackground(new Color(158,216,255,255));
			}
			public void mouseExited(MouseEvent e)
			{
				tutorial.setBackground(new Color(158, 216, 255, 175));
			}
			public void mousePressed(MouseEvent e)
			{
				//if a special action from the user is required to move on to the
				//next slide, don't do anything
				switch(Game.tutorialSlide)
				{
					case 7:
					case 8:
					case 9:
					case 13:
					case 17:
					case 28:
						return;
				}
				
				//if it's any other level, move on to the next slide
				nextSlide();
				
				/*Notes:
				 * Slide 7 requires the user to buy a disc thrower
				 * Slide 8 requires placing a tower
				 * Slide 9 requires the user to click on the tower
				 * Slide 13 requires upgrading a tower's range
				 * Slide 17 requires starting the first round
				 * Slide 28 is invisible, and will only move on when the tech panel appears
				 */
			}
		});
		
		//layered pane stuff
		layeredPane = new JLayeredPane();
		layeredPane.setSize(Game.screenSize.width -115, Game.screenSize.height -115);
		layeredPane.add(drawingPanel, JLayeredPane.DEFAULT_LAYER);
		layeredPane.add(tutorial, JLayeredPane.DRAG_LAYER);
		add(layeredPane);
		
		addMouseListener(new MouseAdapter()
		{
            public void mousePressed(MouseEvent e) 
            {
            	if(!mouseInUpgradePanel)
            	{
            		for (int i = 0; i < Tower.allTowers.length; i++)
            		{
            			if (Tower.allTowers[i] == null)
            				break;
            			else if (i == Upgrades.displayedUpgradeID)
            			{
            				Upgrades.removeUpgradePanel();
            			}
            			//hide range indicator for all towers
        				Tower.allTowers[i].rangeOn = false;	
            		}
            	}
            	
            	requestFocusInWindow();
            	
            	if (ShopPanel.towerToPlace == TowerType.NONE)
            	{
            		System.out.println("You must select a type first!");
            		return;
            	}
            	
            	// check if valid placement
            	if (!ShopPanel.checkPlacement())
            	{
            		ShopPanel.changeInfo("Invalid Location!", true); 
            		return;
            	}
            	
            	switch(ShopPanel.towerToPlace)
            	{
            		case DISC_THROWER:
	            		// create a new discthrower in the static array
	                    Tower.allTowers[numTowers] = new DiscThrower(e.getX(), e.getY(), numTowers);
	                    
	                    //use currDT as shortcut reference for current disc thrower
	                    DiscThrower currDT = (DiscThrower) Tower.allTowers[numTowers];
	                    numTowers++; //now there's 1 more tower
	                    
	                    currDT.setCenterX(e.getX());
	                    currDT.setCenterY(e.getY());
            			
            			//reset cursor to default and tower to place to none
                        setCursor(Cursor.getDefaultCursor());
                        rangeOn = false;
                        ShopPanel.towerToPlace = TowerType.NONE;
                        
                        //special case for tutorial slide 8
                        if(Game.tutorialSlide == 8)
                        	nextSlide();
                        break;
            		case NUMBER_GENERATOR:
	            		// create a new number generator in the static array
	                    Tower.allTowers[numTowers] = new NumberGenerator(e.getX(), e.getY(), numTowers);
	                    
	                    //use currDT as shortcut reference for current disc thrower
	                    Tower currT = Tower.allTowers[numTowers];
	                    numTowers++; //now there's 1 more tower
	                    
	                    currT.setCenterX(e.getX());
	                    currT.setCenterY(e.getY());
	                        
            			//reset cursor to default and tower to place to none
                        setCursor(Cursor.getDefaultCursor());
                        rangeOn = false;
                        ShopPanel.towerToPlace = TowerType.NONE;
            			break;
            		case SCANNER:
            			// create a new number generator in the static array
	                    Tower.allTowers[numTowers] = new Scanner(e.getX(), e.getY(), numTowers);
	                    
	                    //use currDT as shortcut reference for current disc thrower
	                    Tower currST = Tower.allTowers[numTowers];
	                    numTowers++; //now there's 1 more tower
	                    
	                    currST.setCenterX(e.getX());
	                    currST.setCenterY(e.getY());
	                        
            			//reset cursor to default and tower to place to none
                        setCursor(Cursor.getDefaultCursor());
                        rangeOn = false;
                        ShopPanel.towerToPlace = TowerType.NONE;
            			break;
            		case FIREWALL:
            			// create a new number generator in the static array
	                    Tower.allTowers[numTowers] = new FireWall(e.getX(), e.getY(), numTowers);
	                    
	                    //use currDT as shortcut reference for current disc thrower
	                    Tower currFT = Tower.allTowers[numTowers];
	                    numTowers++; //now there's 1 more tower
	                    
	                    currFT.setCenterX(e.getX());
	                    currFT.setCenterY(e.getY());
	                        
            			//reset cursor to default and tower to place to none
                        setCursor(Cursor.getDefaultCursor());
                        rangeOn = false;
                        ShopPanel.towerToPlace = TowerType.NONE;
            			break;
            			
            		case NONE:
            		default:
            			rangeOn = false;                   	
            	}
            }
            //when the mouse enters the game panel, change the cursor to towerToPlace
            public void mouseEntered(MouseEvent e)
            {
            	switch(ShopPanel.towerToPlace)
            	{
            		case DISC_THROWER:
            			setCursor(discThrowerCursor);
                    	break;
            		case NUMBER_GENERATOR:
            			setCursor(numberGeneratorCursor);
            			break;
            		case SCANNER:
            			setCursor(scannerCursor);
            			break;
            		case FIREWALL:
            			setCursor(firewallCursor);
            			break;
            		case NONE:
            		default:
            			setCursor(Cursor.getDefaultCursor());
            			rangeOn = false;
            			break;
            			
            	}
            }
        });
		
        addMouseMotionListener(new MouseAdapter() 
        {
            public void mouseMoved(MouseEvent e) 
            {
                //if the mouse is moved, reset mouseX & mouseY variables
            	setMouseXY(e.getX(),e.getY());
            	setCursorIcon();
            	
            	//turn mouseOnTrack on or off
            	for(int trackPart = 0; trackPart < path.length; trackPart++)
            	{
            		if(path[trackPart].contains(e.getPoint()))
            		{
            			ShopPanel.mouseOnTrack = true;
            			return;
            		}
            	}
            	//if no track part contains mouse position
            	ShopPanel.mouseOnTrack = false;
            }
        });
        
        addKeyListener(new KeyListener()
        {
        	public void keyPressed(KeyEvent e)
        	{
        		/*map movement
        		if(e.getKeyCode()==KeyEvent.VK_LEFT && mapX<0)
        			mapX+=5;
        		if(e.getKeyCode()==KeyEvent.VK_RIGHT && mapX>-454)
        			mapX-=5;
        		if(e.getKeyCode()==KeyEvent.VK_UP && mapY<0)
        			mapY+=5;
        		if(e.getKeyCode()==KeyEvent.VK_DOWN && mapY>-159)
        			mapY-=5;
        		*/
        		
        		// for hot keys
        		if(e.getKeyCode()==KeyEvent.VK_D)
        		{
        			TowerType towerToPlace = TowerType.DISC_THROWER;
        			ShopPanel.validateBuy(towerToPlace);
        			//for faster feedback to user, reset cursor to new towerToPlace
        			setCursorIcon();
        		}
        		if(e.getKeyCode()==KeyEvent.VK_N)
        		{
        			TowerType towerToPlace = TowerType.NUMBER_GENERATOR;
        			ShopPanel.validateBuy(towerToPlace);
        			//for faster feedback to user, reset cursor to new towerToPlace
        			setCursorIcon();
        		}
        		if(e.getKeyCode()==KeyEvent.VK_S)
        		{
        			TowerType towerToPlace = TowerType.SCANNER;
        			ShopPanel.validateBuy(towerToPlace);
        			//for faster feedback to user, reset cursor to new towerToPlace
        			setCursorIcon();
        		}
        		if(e.getKeyCode()==KeyEvent.VK_ESCAPE || e.getKeyCode()==KeyEvent.VK_SPACE)
        		{
        			Game.pauseListener();
        		}
        	}
        	public void keyReleased(KeyEvent e){}
        	public void keyTyped(KeyEvent e){}
        });
	}
	public void setMouseXY(int x, int y)
	{
		mouseX=x;
		mouseY=y;
	}
	public static int getMouseX()
	{
		return mouseX;
	}
	public static int getMouseY()
	{
		return mouseY;
	}
	/**
	 * Updates the cursor based on the current towerToPlace
	 * 
	 */
	public void setCursorIcon()
	{
		//set correct cursor and draw range
		switch (ShopPanel.towerToPlace)
    	{
    		case DISC_THROWER:			if (!ShopPanel.checkPlacement())
    										setCursor(invalidDiscThrowerCursor);
    									else
					            			setCursor(discThrowerCursor);
					            		rangeOn = true;
					            		tempRangeIndicator = new Ellipse2D.Double(getMouseX()-DiscThrower.rangeToSet, getMouseY()-DiscThrower.rangeToSet, 
					            					DiscThrower.rangeToSet*2, DiscThrower.rangeToSet*2);
    									break;
    									
    		case NUMBER_GENERATOR:		if (!ShopPanel.checkPlacement())
											setCursor(invalidNumberGeneratorCursor);
    									else
    										setCursor(numberGeneratorCursor);
    									rangeOn = true;
    									tempRangeIndicator = new Ellipse2D.Double(getMouseX()-NumberGenerator.rangeToSet, getMouseY()-NumberGenerator.rangeToSet, 
    											NumberGenerator.rangeToSet*2, NumberGenerator.rangeToSet*2);
    		case SCANNER:				if (!ShopPanel.checkPlacement())
											setCursor(invalidScannerCursor);
										else
											setCursor(scannerCursor);
										rangeOn = true;
					            		tempRangeIndicator = new Ellipse2D.Double(getMouseX()-Scanner.rangeToSet, getMouseY()-Scanner.rangeToSet, 
					            					Scanner.rangeToSet*2, Scanner.rangeToSet*2);
					            		break;
    		case FIREWALL:				if (!ShopPanel.checkPlacement())
											setCursor(invalidFirewallCursor);
										else
											setCursor(firewallCursor);
							    		break;
							    		
			default:					setCursor(Cursor.getDefaultCursor());
										break;
    	}
	}
	
	public void addToLayeredPane(Component c, Integer i)
	{
		layeredPane.add(c, i);
	}
	public void removeFromLayeredPane(Component c)
	{
		layeredPane.remove(c);
	}
	/**
	 * Moves to next slide of tutorial.
	 */
	public void nextSlide()
	{
		//shortcuts used in bounds calculation of tutorial
		int w = Game.screenSize.width - 110;
		int h = Game.screenSize.height - 110;
		
		//move on to next slide of tutorial
		Game.tutorialSlide++;
		
		switch(Game.tutorialSlide)
		{
			case 2:
				tutorial.setText("In this game, you play as a computer virus tracker...");
				break;
			case 3:
				tutorial.setText("who moves around the network, placing disc throwers...");
				break;
			case 4:
				tutorial.setText("that throw CDs at viruses to kill them. Along the...");
				break;
			case 5:
				tutorial.setText("way, you'll meet more advanced malware and use more...");
				break;
			case 6:
				tutorial.setText("powerful towers, but I'm getting ahead of myself.");
				break;
			case 7:
				tutorial.setIcon(new ImageIcon(MyImages.redArrow));
				tutorial.setHorizontalTextPosition(SwingConstants.RIGHT);
				tutorial.setHorizontalAlignment(SwingConstants.LEFT);
				tutorial.setText("First, click this button to buy a disc thrower.");
				tutorial.setBounds(0, 70, 4*w/5, h/10);
				break;
			case 8:
				tutorial.setText("Now place it over here.");
				tutorial.setBounds(17*w/32, 13*h/20, 7*w/16, h/10);
				break;
			case 9:
				tutorial.setText("Now click on the tower.");
				tutorial.setBounds(17*w/32, 13*h/20, 7*w/16, h/10);
				break;
			case 10:
				tutorial.setText("Excellent! You've brought up the upgrade panel!...");
				tutorial.setIcon(null);
				tutorial.setHorizontalAlignment(SwingConstants.CENTER);
				tutorial.setBounds(0, 8*h/10, w, h/10);
				break;
			case 11:
				tutorial.setText("Here you can see your tower's stats, delete it...");
				break;
			case 12:
				tutorial.setText("for a partial refund, and upgrade your tower,...");
				break;
			case 13:
				tutorial.setText("for a fee. Buy the Wider Range upgrade now.");
				break;
			case 14:
				tutorial.setText("Great! Whenever you want, click anywhere on...");
				break;
			case 15:
				tutorial.setText("the map to hide the upgrade panel. Now...");
				break;
			case 16:
				tutorial.setText("you're all set. Press the play button...");
				break;
			case 17:
				tutorial.setText("in the top left corner to begin the round.");
				break;
			case 18:
				tutorial.setIcon(new ImageIcon(MyImages.redArrow));
				tutorial.setText("These guys are malware minions.");
				tutorial.setBounds(9*w/21, 15, 11*w/21, h/10);
				break;
			case 19:
				tutorial.setText("They are little snippets of...");
				break;
			case 20:
				tutorial.setText("executable code that will...");
				break;
			case 21:
				tutorial.setText("crash your CPU if they get to it.");
				tutorial.setBounds(9*w/21, 15, 12*w/21, h/10);
				break;
			case 22:
				tutorial.setText("Your tower's CDs contain the...");
				break;
			case 23:
				tutorial.setText("code that'll remove these guys...");
				break;
			case 24:
				tutorial.setText("from the network. Okay, looks...");
				break;
			case 25:
				tutorial.setText("like you're all set. ...");
				break;
			case 26:
				tutorial.setText("I'll check back on you at the...");
				break;
			case 27:
				tutorial.setText("end of this round. Good luck!...");
				break;
			case 28:
				tutorial.setVisible(false);
				break;
			case 29:
				break;
		}
	}
	/**
	 * Turns off the tutorial.
	 */
	public void disableTutorial()
	{
		Game.tutorial = false;
		tutorial.setVisible(false);
	}
	
	private class DrawingPanel extends JPanel
	{
		public DrawingPanel()
		{
			setBackground(Color.GREEN);
			setOpaque(false);
		}
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			//draw path
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.YELLOW);
			for(int trackPart = 0; trackPart < path.length; trackPart++)
			{
				g2d.fill(path[trackPart]);
			}
			
			//draw all viruses
			for(int v=0; v<lvlManager.getMalwaresThisLevel(); v++)
			{
				if(Malware.allMalware[v] == null)
					break;
				else
					Malware.allMalware[v].drawVirus(g);
			}
			
			//draw all towers
			for(int i = 0; i < numTowers; i++)
			{
				Tower curr = Tower.allTowers[i];
				if(curr != null)
					curr.drawTower(g);
				else
					break;
			}
			
			//draw all projectiles
			ListIterator<Projectile> iterator = Projectile.allProjectiles.listIterator();
			while(iterator.hasNext())
			{
				iterator.next().drawProjectile(g);
			}
			
			//draw temporary range indicator
			if(rangeOn)
			{
				g2d.setColor(new Color(0, 0, 0, 50));
				g2d.fill(tempRangeIndicator);
				//System.out.println("drew range circle at "+tempRangeIndicator.getBounds());
			}
			
			//draw map images
			//modem
			AffineTransform at = new AffineTransform();
			at.scale(Game.scaleOfSprites, Game.scaleOfSprites);
			at.translate(((Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 84) - Game.scaleOfSprites*MyImages.modem.getWidth()/2)/Game.scaleOfSprites, 
					 ((Game.heightOfGamePanel * .4) + Game.heightOfGamePanel / 2- Game.widthOfGamePanel / 42 - Game.scaleOfSprites*MyImages.modem.getHeight()/2)/Game.scaleOfSprites);
			g2d.drawImage(MyImages.modem, at, null);
			//cpu
			at = new AffineTransform();
			at.scale(Game.scaleOfSprites, Game.scaleOfSprites);
			at.translate(((Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) + Game.widthOfGamePanel / 3 - (Game.widthOfGamePanel / 84) - MyImages.cpu.getWidth()/2)/Game.scaleOfSprites, 
					(Game.heightOfGamePanel/6 - MyImages.cpu.getHeight()/2)/Game.scaleOfSprites);
			g2d.drawImage(MyImages.cpu, at, null);
		}
	}
}
