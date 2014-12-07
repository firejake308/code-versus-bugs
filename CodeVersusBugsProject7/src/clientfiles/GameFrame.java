package clientfiles;
/**GameFrame.java
 * This class extends JFrame to create the window that you see during gameplay.
 * The frame contains 4 panels arranged in a grid bag layout.
 * The grid bag layout can be modified but is very time-consuming.
 * 
 * @author Adel Hassan
 * 9/28/2014 - Began working on constructor and layouts (Adel)
 * 10/1/2014 - Colored panels to debug
 * 10/3/2014 - Implemented GamePanel class to show mouse coordinates
 * 10/14/2014 - Moved the main method to a new CodeVersusBugs class.
 * 10/19/2014 - Moved panels into Game class
 * 11/2/2014 - 	Removed money variable as it is no longer useful here
 * 				Added fps counter to info panel
 * 				Info panel also shows placeholder for money
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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import javax.swing.*;

public class GameFrame extends JFrame implements ActionListener
{
	
	static private final long serialVersionUID = 1;
	private Color epic = new Color(100,179,132);

	//stuff in info panel
	public JLabel moneyLabel;
	public JLabel life;
	public JLabel fpsCounter;
	
	//the constructor instantiates the panels and sets the layout
	public GameFrame()
	{
		super("Code Versus Bugs In Development");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		Game.infoPanel = new JPanel(new GridLayout(1,3));
		Game.gamePanel = new GamePanel();
		Game.techPanel = new TechPanel();
		Game.shopPanel = new ShopPanel();
		Game.pauseButton = new JButton(PauseButtonListener.sprite);
		
		//set layout for game
		Container mainWindow = getContentPane();
		//GridBagLayout layout = new GridBagLayout();
		//GridBagConstraints constraints = new GridBagConstraints();
		mainWindow.setLayout(null);
		mainWindow.setBackground(epic);
		
		mainWindow.add(Game.pauseButton);
		Game.pauseButton.setBounds(5,5,100,100);
		
		//add listener for pause button
		Game.pauseButton.addActionListener(this);
		
		mainWindow.add(Game.infoPanel);
		Game.infoPanel.setBounds(110,5,screenSize.width-110,50);
		
		//initialize money label and fps counter
		moneyLabel = new JLabel("$" + Game.money + " money");
		fpsCounter = new JLabel(Game.fps+" fps");
		life = new JLabel("Lives: " + Game.lives + ".....Round: " + Game.level);
		
		//fix up font size
		//moneyLabel.setFont(new Font("Monospaced", Font.PLAIN, 50));
		//fpsCounter.setFont(new Font("Monospaced", Font.PLAIN, 50));
		//life.setFont(new Font("Monospaced", Font.PLAIN, 50));
		
		//add fpsCounter to info panel
		Game.infoPanel.add(life);
		Game.infoPanel.add(moneyLabel);
		Game.infoPanel.add(fpsCounter);
		
		mainWindow.add(Game.shopPanel);
		Game.shopPanel.setBounds(5,110,100,screenSize.height-110);
		
		mainWindow.add(Game.gamePanel);
		Game.gamePanel.setBounds(110,110,screenSize.width-110,screenSize.height-110);
		
		mainWindow.add(Game.techPanel);
		Game.techPanel.setBounds(110, 110, screenSize.width-110,screenSize.height-110);
		Game.techPanel.initializeTechPanel();
		
		//debug code
		//Game.techPanel.setVisible(true);
		//Game.gamePanel.setVisible(false);
		
		//these are just here to make sure that
		Game.gamePanel.setLayout(null);
		Game.gamePanel.setBackground(epic);
		Game.infoPanel.setBackground(epic);
		//Game.shopPanel.setBackground(Color.CYAN);
		
		Game.widthOfGamePanel = Game.gamePanel.getWidth();
		Game.heightOfGamePanel = Game.gamePanel.getHeight();
		Game.scaleOfSprites = (Game.widthOfGamePanel / 50) / 26.44;	
		
		int lowest = JLayeredPane.FRAME_CONTENT_LAYER;
		
		// for top left entrance
		Game.track = new JPanel();
		Game.track.setBounds(Game.widthOfGamePanel / 14, 0, Game.widthOfGamePanel / 42, Game.heightOfGamePanel / 4);
		Game.track.setBackground(Color.YELLOW);
		Game.gamePanel.addToLayeredPane(Game.track, lowest);
		
		Game.track.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = true;
				Game.gamePanel.setCursorIcon();
			}
			
			public void mouseExited(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = false;
				Game.gamePanel.setCursorIcon();
			}
		});
		
		// for 2nd to left entrance
		Game.track = new JPanel();
		Game.track.setBounds(Game.widthOfGamePanel / 4, 0, Game.widthOfGamePanel / 42, Game.heightOfGamePanel / 4);
		Game.track.setBackground(Color.YELLOW);
		Game.gamePanel.addToLayeredPane(Game.track, lowest);
		
		Game.track.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = true;
				Game.gamePanel.setCursorIcon();
			}
			
			public void mouseExited(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = false;
				Game.gamePanel.setCursorIcon();
			}
		});
		
		// for 3rd to left entrance
		Game.track = new JPanel();
		Game.track.setBounds((int) (Game.widthOfGamePanel * .4), 0, Game.widthOfGamePanel / 42, Game.heightOfGamePanel / 4);
		Game.track.setBackground(Color.YELLOW);
		Game.gamePanel.addToLayeredPane(Game.track, lowest);
		
		// for 4th to left entrance
		Game.track = new JPanel();
		Game.track.setBounds((int) (Game.widthOfGamePanel * .65), 0, Game.widthOfGamePanel / 42, (int) (Game.heightOfGamePanel * .4));
		Game.track.setBackground(Color.YELLOW);
		Game.gamePanel.addToLayeredPane(Game.track, lowest);
		
		Game.track.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = true;
				Game.gamePanel.setCursorIcon();
			}
			
			public void mouseExited(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = false;
				Game.gamePanel.setCursorIcon();
			}
		});
		
		// for last to left entrance
		Game.track = new JPanel();
		Game.track.setBounds((int) (Game.widthOfGamePanel * .85), 0, Game.widthOfGamePanel / 42, (int) (Game.heightOfGamePanel * .4));
		Game.track.setBackground(Color.YELLOW);
		Game.gamePanel.addToLayeredPane(Game.track, lowest);
		
		Game.track.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = true;
				Game.gamePanel.setCursorIcon();
			}
			
			public void mouseExited(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = false;
				Game.gamePanel.setCursorIcon();
			}
		});
		
		// for connector (top left entrances)
		Game.track = new JPanel();
		Game.track.setBounds(Game.widthOfGamePanel / 14, Game.heightOfGamePanel / 4 - (Game.widthOfGamePanel / 42), (int) (Game.widthOfGamePanel * .35), Game.widthOfGamePanel / 42);
		Game.track.setBackground(Color.YELLOW);
		Game.gamePanel.addToLayeredPane(Game.track, lowest);
		
		Game.track.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = true;
				Game.gamePanel.setCursorIcon();
			}
			
			public void mouseExited(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = false;
				Game.gamePanel.setCursorIcon();
			}
		});
		
		// for connector, top right entrances
		Game.track = new JPanel();
		Game.track.setBounds((int) (Game.widthOfGamePanel * .65), (int) (Game.heightOfGamePanel * .4) - (Game.widthOfGamePanel / 42), Game.widthOfGamePanel / 5, Game.widthOfGamePanel / 42);
		Game.track.setBackground(Color.YELLOW);
		Game.gamePanel.addToLayeredPane(Game.track, lowest);
		
		Game.track.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = true;
				Game.gamePanel.setCursorIcon();
			}
			
			public void mouseExited(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = false;
				Game.gamePanel.setCursorIcon();
			}
		});
		
		// for track that connects from top 3 connector on left
		Game.track = new JPanel();
		Game.track.setBounds(Game.widthOfGamePanel / 5, Game.heightOfGamePanel / 4 - (Game.widthOfGamePanel / 42), Game.widthOfGamePanel / 42, (int) (Game.heightOfGamePanel * .4));
		Game.track.setBackground(Color.YELLOW);
		Game.gamePanel.addToLayeredPane(Game.track, lowest);
		
		Game.track.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = true;
				Game.gamePanel.setCursorIcon();
			}
			
			public void mouseExited(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = false;
				Game.gamePanel.setCursorIcon();
			}
		});
		
		// goes right from the one above on left side(lowest)
		Game.track = new JPanel();
		Game.track.setBounds(Game.widthOfGamePanel / 5, (Game.heightOfGamePanel / 4) + (int) (Game.heightOfGamePanel * .4) - (2 * Game.widthOfGamePanel / 42), Game.widthOfGamePanel / 5, Game.widthOfGamePanel / 42);
		Game.track.setBackground(Color.YELLOW);
		Game.gamePanel.addToLayeredPane(Game.track, lowest);
		
		Game.track.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = true;
				Game.gamePanel.setCursorIcon();
			}
			
			public void mouseExited(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = false;
				Game.gamePanel.setCursorIcon();
			}
		});
		
		// goes up from the one above, only track of this type, on left
		Game.track = new JPanel();
		Game.track.setBounds((int) (Game.widthOfGamePanel * .4), (int) (Game.heightOfGamePanel * .4), Game.widthOfGamePanel / 42,
				(Game.heightOfGamePanel / 4) + (int) (Game.heightOfGamePanel * .4) - (2 * Game.widthOfGamePanel / 42) - (int) (Game.heightOfGamePanel * .4) + Game.widthOfGamePanel / 42);
		Game.track.setBackground(Color.YELLOW);
		Game.gamePanel.addToLayeredPane(Game.track, lowest);
		
		Game.track.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = true;
				Game.gamePanel.setCursorIcon();
			}
			
			public void mouseExited(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = false;
				Game.gamePanel.setCursorIcon();
			}
		});
		
		// goes right from one above on left side, middle height
		Game.track = new JPanel();
		Game.track.setBounds((int) (Game.widthOfGamePanel * .4), (int) (Game.heightOfGamePanel * .4), Game.widthOfGamePanel / 5, Game.widthOfGamePanel / 42);
		Game.track.setBackground(Color.YELLOW);
		Game.gamePanel.addToLayeredPane(Game.track, lowest);
		
		Game.track.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = true;
				Game.gamePanel.setCursorIcon();
			}
			
			public void mouseExited(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = false;
				Game.gamePanel.setCursorIcon();
			}
		});
		
		// long downward track that combines both sides
		Game.track = new JPanel();
		Game.track.setBounds((int) (Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 42), (int) (Game.heightOfGamePanel * .4), Game.widthOfGamePanel / 42, Game.heightOfGamePanel / 2);
		Game.track.setBackground(Color.YELLOW);
		Game.gamePanel.addToLayeredPane(Game.track, lowest);
		
		Game.track.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = true;
				Game.gamePanel.setCursorIcon();
			}
			
			public void mouseExited(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = false;
				Game.gamePanel.setCursorIcon();
			}
		});
		
		// goes down from connector of top right entrances
		Game.track = new JPanel();
		Game.track.setBounds((int) (Game.widthOfGamePanel * .8), (int) (Game.heightOfGamePanel * .4) - (Game.widthOfGamePanel / 42), Game.widthOfGamePanel / 42, (int) (Game.heightOfGamePanel * .3));
		Game.track.setBackground(Color.YELLOW);
		Game.gamePanel.addToLayeredPane(Game.track, lowest);
		
		Game.track.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = true;
				Game.gamePanel.setCursorIcon();
			}
			
			public void mouseExited(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = false;
				Game.gamePanel.setCursorIcon();
			}
		});
		
		// goes left from 1 above, meets other side
		Game.track = new JPanel();
		Game.track.setBounds((int) (Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 42), (int) (Game.heightOfGamePanel * .4) + (int) (Game.heightOfGamePanel * .3) - (Game.widthOfGamePanel / 42),
				(int) (Game.widthOfGamePanel * .8) - ((int) (Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 42) + (Game.widthOfGamePanel / 42)) + (2 * Game.widthOfGamePanel / 42), Game.widthOfGamePanel / 42);
		Game.track.setBackground(Color.YELLOW);
		Game.gamePanel.addToLayeredPane(Game.track, lowest);
		
		Game.track.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = true;
				Game.gamePanel.setCursorIcon();
			}
			
			public void mouseExited(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = false;
				Game.gamePanel.setCursorIcon();
			}
		});
		
		// goes left from point of connection of sides, 2nd to last track
		Game.track = new JPanel();
		Game.track.setBounds((int) (Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 42) - (int) (Game.widthOfGamePanel * .3), (int) (Game.heightOfGamePanel * .4 + Game.heightOfGamePanel / 2) - (Game.widthOfGamePanel / 42),
				(int) (Game.widthOfGamePanel * .3) + Game.widthOfGamePanel / 42, Game.widthOfGamePanel / 42);
		Game.track.setBackground(Color.YELLOW);
		Game.gamePanel.addToLayeredPane(Game.track, lowest);
		
		Game.track.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = true;
				Game.gamePanel.setCursorIcon();
			}
			
			public void mouseExited(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = false;
				Game.gamePanel.setCursorIcon();
			}
		});
		
		// last segment of track
		Game.track = new JPanel();
		Game.track.setBounds((int) (Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 42) - (int) (Game.widthOfGamePanel * .3), (int) (Game.heightOfGamePanel * .4 + Game.heightOfGamePanel / 2) - (Game.widthOfGamePanel / 42),
				Game.widthOfGamePanel / 42, (int) (Game.heightOfGamePanel * .15));
		Game.track.setBackground(Color.YELLOW);
		Game.gamePanel.addToLayeredPane(Game.track, lowest);
		
		Game.track.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = true;
				Game.gamePanel.setCursorIcon();
			}
			
			public void mouseExited(MouseEvent e)
			{
				ShopPanel.mouseOnTrack = false;
				Game.gamePanel.setCursorIcon();
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Game.pauseListener();
	}
	
}
