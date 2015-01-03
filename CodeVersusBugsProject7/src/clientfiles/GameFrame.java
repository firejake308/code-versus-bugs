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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
	public JLabel levelCounter;
	
	//the constructor instantiates the panels and sets the layout
	public GameFrame()
	{
		super("Code Versus Bugs In Development");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		Game.infoPanel = new JPanel(new GridLayout(1,4));
		Game.gamePanel = new GamePanel();
		Game.techPanel = new TechPanel();
		Game.shopPanel = new ShopPanel();
		Game.pauseButton = new JButton(PauseButtonListener.sprite);
		Game.fastForwardButton = new JButton(new ImageIcon(MyImages.fastForwardOff));
		
		//set layout for game
		Container mainWindow = getContentPane();
		mainWindow.setLayout(null);
		mainWindow.setBackground(epic);
		
		mainWindow.add(Game.pauseButton);
		Game.pauseButton.setBounds(5,5,100,100);
		
		//add listener for pause button
		Game.pauseButton.addActionListener(this);
		
		mainWindow.add(Game.infoPanel);
		
		Game.fastForwardButton.setBounds(105, 5, 30, 30);
		Game.fastForwardButton.addActionListener(this);
		mainWindow.add(Game.fastForwardButton);
		
		Game.infoPanel.setBounds(145,5,screenSize.width-145,50);
		
		//initialize money label and fps counter
		moneyLabel = new JLabel("$" + Game.getMoney() + " money");
		fpsCounter = new JLabel(Game.fps+" fps");
		life = new JLabel("Bytes Remaining: " + Game.lives);
		levelCounter = new JLabel("Level: " + Game.level);
		
		//fix up font size
		moneyLabel.setFont(new Font("Monospaced", Font.PLAIN, screenSize.width / (4*11)));
		fpsCounter.setFont(new Font("Monospaced", Font.PLAIN, screenSize.width / (4*11)));
		life.setFont(new Font("Monospaced", Font.PLAIN, screenSize.width / (4*14)));
		levelCounter.setFont(new Font("Monospaced", Font.PLAIN, screenSize.width / (4*11)));
		
		//add fpsCounter to info panel
		Game.infoPanel.add(life);
		Game.infoPanel.add(moneyLabel);
		Game.infoPanel.add(fpsCounter);
		Game.infoPanel.add(levelCounter);
		
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
		
		Game.widthOfGamePanel = Game.gamePanel.getWidth();
		Game.heightOfGamePanel = Game.gamePanel.getHeight();
		Game.scaleOfSprites = (Game.widthOfGamePanel / 50) / 26.44;	
		
		DiscThrower.rangeToSet = (int) (Game.widthOfGamePanel * .12);
		NumberGenerator.rangeToSet = (int) (Game.widthOfGamePanel * .15);
		Scanner.rangeToSet = (int) (Game.widthOfGamePanel * .10);
		CommunicationsTower.rangeToSet = (int) (Game.widthOfGamePanel * .10);
		
		//make path parts
		Game.gamePanel.path = new Rectangle[18];
		
		//new parts added in modem update
		Game.gamePanel.path[0] = new Rectangle();
		Game.gamePanel.path[0].setBounds((int) (Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 42),
				(int) (Game.heightOfGamePanel * .4 + Game.heightOfGamePanel / 2) - (Game.widthOfGamePanel / 42),
				Game.widthOfGamePanel/3,
				Game.widthOfGamePanel/42);
		//new parts added in modem update
		Game.gamePanel.path[1] = new Rectangle();
		Game.gamePanel.path[1].setBounds((int) ((Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 42) + Game.widthOfGamePanel / 3),
				Game.heightOfGamePanel/6,
				Game.widthOfGamePanel/42,
				(int) (Game.heightOfGamePanel * .4 + Game.heightOfGamePanel / 2) - Game.heightOfGamePanel / 6);
		//new parts added in modem update
		Game.gamePanel.path[2] = new Rectangle();
		Game.gamePanel.path[2].setBounds((int) ((Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 42) + Game.widthOfGamePanel / 3),
				(int) (Game.heightOfGamePanel * .4 + Game.heightOfGamePanel / 2) - (Game.widthOfGamePanel / 42),
				Game.widthOfGamePanel / 42,
				(int)(Game.heightOfGamePanel * .15));
		//top left entrance
		Game.gamePanel.path[3] = new Rectangle();
		Game.gamePanel.path[3].setBounds(Game.widthOfGamePanel / 14, 0, Game.widthOfGamePanel / 42, Game.heightOfGamePanel / 4);
		//2nd entrance from left
		Game.gamePanel.path[4] = new Rectangle();
		Game.gamePanel.path[4].setBounds(Game.widthOfGamePanel / 4, 0, Game.widthOfGamePanel / 42, Game.heightOfGamePanel / 4);
		//3rd entrance from left
		Game.gamePanel.path[5] = new Rectangle();
		Game.gamePanel.path[5].setBounds((int) (Game.widthOfGamePanel * .4), 0, Game.widthOfGamePanel / 42, Game.heightOfGamePanel / 4);
		//4th entrance from left
		Game.gamePanel.path[6] = new Rectangle();
		Game.gamePanel.path[6].setBounds((int) (Game.widthOfGamePanel * .65), 0, Game.widthOfGamePanel / 42, (int) (Game.heightOfGamePanel * .4));
		//rightmost entrance
		Game.gamePanel.path[7] = new Rectangle();
		Game.gamePanel.path[7].setBounds((int) (Game.widthOfGamePanel * .85), 0, Game.widthOfGamePanel / 42, (int) (Game.heightOfGamePanel * .4));
		//connector for 3 left entrances
		Game.gamePanel.path[8] = new Rectangle();
		Game.gamePanel.path[8].setBounds(Game.widthOfGamePanel / 14, Game.heightOfGamePanel / 4 - (Game.widthOfGamePanel / 42), (int) (Game.widthOfGamePanel * .35), Game.widthOfGamePanel / 42);
		//connector for 3 right entrances
		Game.gamePanel.path[9] = new Rectangle();
		Game.gamePanel.path[9].setBounds((int) (Game.widthOfGamePanel * .65), (int) (Game.heightOfGamePanel * .4) - (Game.widthOfGamePanel / 42), Game.widthOfGamePanel / 5, Game.widthOfGamePanel / 42);
		//goes down from left connector
		Game.gamePanel.path[10] = new Rectangle();
		Game.gamePanel.path[10].setBounds(Game.widthOfGamePanel / 5, Game.heightOfGamePanel / 4 - (Game.widthOfGamePanel / 42), Game.widthOfGamePanel / 42, (int) (Game.heightOfGamePanel * .4));
		//goes right from Game.gamePanel.path[10]
		Game.gamePanel.path[11] = new Rectangle();
		Game.gamePanel.path[11].setBounds(Game.widthOfGamePanel / 5, (Game.heightOfGamePanel / 4) + (int) (Game.heightOfGamePanel * .4) - (2 * Game.widthOfGamePanel / 42), Game.widthOfGamePanel / 5, Game.widthOfGamePanel / 42);
		//goes up from Game.gamePanel.path[11]
		Game.gamePanel.path[12] = new Rectangle();
		Game.gamePanel.path[12].setBounds((int) (Game.widthOfGamePanel * .4), (int) (Game.heightOfGamePanel * .4), Game.widthOfGamePanel / 42, (Game.heightOfGamePanel / 4)- (Game.widthOfGamePanel / 42));
		//goes right from Game.gamePanel.path[12] to make top of u-turn
		Game.gamePanel.path[13] = new Rectangle();
		Game.gamePanel.path[13].setBounds((int) (Game.widthOfGamePanel * .4), (int) (Game.heightOfGamePanel * .4), Game.widthOfGamePanel / 5, Game.widthOfGamePanel / 42);
		//long downward track that combines both sides
		Game.gamePanel.path[14] = new Rectangle();
		Game.gamePanel.path[14].setBounds((int) (Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 42), (int) (Game.heightOfGamePanel * .4), Game.widthOfGamePanel / 42, Game.heightOfGamePanel / 2);
		//goes down from connector of top right entrances
		Game.gamePanel.path[15] = new Rectangle();
		Game.gamePanel.path[15].setBounds((int) (Game.widthOfGamePanel * .8), (int) (Game.heightOfGamePanel * .4) - (Game.widthOfGamePanel / 42), Game.widthOfGamePanel / 42, (int) (Game.heightOfGamePanel * .3));
		//goes left from Game.gamePanel.path[15] to join with down connector
		Game.gamePanel.path[16] = new Rectangle();
		Game.gamePanel.path[16].setBounds((int) (Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 42), (int) (Game.heightOfGamePanel * .4) + (int) (Game.heightOfGamePanel * .3) - (Game.widthOfGamePanel / 42),
				(int) (Game.widthOfGamePanel * .8) - ((int) (Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5)) + (2 * Game.widthOfGamePanel / 42), Game.widthOfGamePanel / 42);
		//goes left from point of connection of sides, last track on dead-end path
		Game.gamePanel.path[17] = new Rectangle();
		Game.gamePanel.path[17].setBounds((int) (Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 42) - (int) (Game.widthOfGamePanel * .3), 
				(int) (Game.heightOfGamePanel * .4 + Game.heightOfGamePanel / 2) - (Game.widthOfGamePanel / 42),
				Game.widthOfGamePanel /3, 
				Game.widthOfGamePanel / 42);
		/*addKeyListener(new KeyAdapter()
		{
			//pause the game whenerver escape is pressed
			public void keyTyped(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					Game.pauseButton.setText("");
					Game.pauseButton.setIcon(PauseButtonListener.sprite);
					
					Game.gameState = Game.PAUSED;
					
					//show dialog to quit or resume
					Object[] options = {"Resume", "Quit"};
					int choice = JOptionPane.showOptionDialog(Game.gf.getContentPane(), "Game Paused", "Pause", JOptionPane.DEFAULT_OPTION, 
							JOptionPane.PLAIN_MESSAGE, PauseButtonListener.sprite, options, options[0]);
					
					if(choice == 0)
					{
						Game.pauseButton.setIcon(PauseButtonListener.pausedSprite);
						Game.gameState = Game.PLAYING;
					}
					else if(choice == 1)
					{
						System.exit(0);
					}
				}
			}
		});*/
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == Game.pauseButton)
			Game.pauseListener();
		if (e.getSource() == Game.fastForwardButton)
			Game.fastForwardListener();
	}
	
}
