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

import javax.swing.*;

public class GamePanel extends JPanel
{
	private static int mouseX;
	private static int mouseY;
	private static int mapX=0;
	private static int mapY=0;
	public static int numTowers=0;
	public LevelManager lvlManager;
	
	public boolean mouseInUpgradePanel = false;
	
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
	
	static private final long serialVersionUID = 1;
	
	JLayeredPane layeredPane;
	
	public GamePanel()
	{
		this.setFocusable(true);
		lvlManager = new StoryManager();
		
		DrawingPanel drawingPanel = new DrawingPanel();
		drawingPanel.setBounds(0,0,Game.screenSize.width -115, Game.screenSize.height -115);
		
		//layered pane stuff
		layeredPane = new JLayeredPane();
		layeredPane.setSize(Game.screenSize.width -115, Game.screenSize.height -115);
		layeredPane.add(drawingPanel, JLayeredPane.DEFAULT_LAYER);
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
            				Upgrades.removeUpgradePanel();
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
	                    
	                    currDT.setCenterX(e.getX()-mapX);
	                    currDT.setCenterY(e.getY()-mapY);
            			
            			//reset cursor to default and tower to place to none
                        setCursor(Cursor.getDefaultCursor());
                        ShopPanel.towerToPlace = TowerType.NONE;
            			//System.out.println("cursor set to normal");
                        break;
            		case NUMBER_GENERATOR:
	            		// create a new number generator in the static array
	                    Tower.allTowers[numTowers] = new NumberGenerator(e.getX(), e.getY(), numTowers);
	                    
	                    //use currDT as shortcut reference for current disc thrower
	                    Tower currT = Tower.allTowers[numTowers];
	                    numTowers++; //now there's 1 more tower
	                    
	                    currT.setCenterX(e.getX()-mapX);
	                    currT.setCenterY(e.getY()-mapY);
	                        
            			//reset cursor to default and tower to place to none
                        setCursor(Cursor.getDefaultCursor());
                        ShopPanel.towerToPlace = TowerType.NONE;
            			break;
            		case NONE:
            		default:
            			                        	
            	}
            }
            //when the mouse enters the game panel, change the cursor to towerToPlace
            public void mouseEntered(MouseEvent e)
            {
            	switch(ShopPanel.towerToPlace)
            	{
            		case DISC_THROWER:
            			setCursor(discThrowerCursor);
                    	//System.out.println("cursor set to dt");
                    	break;
            		case NUMBER_GENERATOR:
            			setCursor(numberGeneratorCursor);
            			//System.out.println("cursor set to ng");
            			break;
            		case NONE:
            		default:
            			setCursor(Cursor.getDefaultCursor());
            			//System.out.println("cursor set to normal");
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
        		}
        		if(e.getKeyCode()==KeyEvent.VK_N)
        		{
        			TowerType towerToPlace = TowerType.NUMBER_GENERATOR;
        			ShopPanel.validateBuy(towerToPlace);
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
	
	public static int getMapX()
	{
		return mapX;
	}
	
	public static int getMapY()
	{
		return mapY;
	}
	public static int getMouseX()
	{
		return mouseX;
	}
	public static int getMouseY()
	{
		return mouseY;
	}
	
	public void setCursorIcon()
	{
		switch (ShopPanel.towerToPlace)
    	{
    		case DISC_THROWER:			if (!ShopPanel.checkPlacement())
    										setCursor(invalidDiscThrowerCursor);
					            		else
											setCursor(discThrowerCursor);
    									break;
    									
    		case NUMBER_GENERATOR:		if (!ShopPanel.checkPlacement())
											setCursor(invalidNumberGeneratorCursor);
    									else
    										setCursor(numberGeneratorCursor);
										break;
										
			default:					setCursor(Cursor.getDefaultCursor());
										break;
    	}
	}
	
	public void addToLayeredPane(Component c, Integer i)
	{
		layeredPane.add(c, i);
	}
	
	private class DrawingPanel extends JPanel
	{
		public DrawingPanel()
		{
			setBackground(Color.GREEN);
			setOpaque(false);
			/*
			//tutorial things
    		JLabel tutorialBox;
    		int w = Game.screenSize.width - 110;
    		int h = Game.screenSize.height- 110;
    		Font consolas = new Font("Monospaced", Font.PLAIN, h/20);
    		switch(Game.tutorialSlide)
    		{
    			case 0:
    				break;
    			case 1:
    				tutorialBox = new JLabel("Welcome to Code Versus Bugs!");
    				tutorialBox.setBounds(900,400, w, h / 10);
    				System.out.println(tutorialBox.getLocation());
    				tutorialBox.setFont(consolas);
    				tutorialBox.setBackground(new Color(143, 211, 247, 175));
    				tutorialBox.setOpaque(true);
    				this.add(tutorialBox);
    				System.out.println(tutorialBox.getLocation());
    		}*/
		}
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
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
			for(int p = 0; p < Projectile.allProjectiles.length; p++)
			{
				Projectile curr = Projectile.allProjectiles[p];
				if(curr == null)
					break;
				else
					curr.drawProjectile(g);
			}
		}
	}
}
