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
	private Color epic = new Color(34, 157, 68);

	//stuff in info panel
	public DigitalDisplay moneyDisplay;
	public DigitalDisplay life;
	public DigitalDisplay fpsDisplay;
	public DigitalDisplay levelCounter;
	
	//the constructor instantiates the panels and sets the layout
	public GameFrame()
	{
		super("Code Versus Bugs In Development");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		//initialize the 4 JPanels
		Game.infoPanel = new JPanel(new GridLayout(1,4));
		Game.gamePanel = new GamePanel();
		Game.techPanel = new TechPanel();
		Game.shopPanel = new ShopPanel();
		
		//initialize the control buttons
		Game.pauseButton = new JButton(PauseButtonListener.sprite);
		Game.fastForwardButton = new JButton(new ImageIcon(MyImages.fastForwardOff));
		Game.saveButton = new JButton(new ImageIcon(MyImages.save));
		Game.loadButton = new JButton(new ImageIcon(MyImages.load));
		Game.helpButton = new JButton(new ImageIcon(MyImages.info));
		Game.restartButton = new JButton(new ImageIcon(MyImages.restart));
		Game.quitButton = new JButton(new ImageIcon(MyImages.quit));
		
		//set layout for game
		Container mainWindow = getContentPane();
		mainWindow.setLayout(null);
		mainWindow.setBackground(epic);
		
		mainWindow.add(Game.pauseButton);
		Game.pauseButton.setBounds(5,5,100,100);
		
		//add listener for pause button
		Game.pauseButton.addActionListener(this);
		
		//6 small buttons
		Game.fastForwardButton.setBounds(105, 5, 30, 30);
		Game.fastForwardButton.addActionListener(this);
		mainWindow.add(Game.fastForwardButton);
		
		Game.saveButton.setBounds(105, 40, 30, 30);
		Game.saveButton.addActionListener(this);
		mainWindow.add(Game.saveButton);
		
		Game.loadButton.setBounds(105, 75, 30, 30);
		Game.loadButton.addActionListener(this);
		mainWindow.add(Game.loadButton);
		
		Game.helpButton.setBounds(140, 5, 30, 30);
		Game.helpButton.addActionListener(this);
		mainWindow.add(Game.helpButton);
		
		Game.restartButton.setBounds(140, 40, 30, 30);
		Game.restartButton.addActionListener(this);
		mainWindow.add(Game.restartButton);
		
		Game.quitButton.setBounds(140, 75, 30, 30);
		Game.quitButton.addActionListener(this);
		mainWindow.add(Game.quitButton);
		
		//initialize money label and fps counter
		moneyDisplay = new DigitalDisplay(5, "money");
		fpsDisplay = new DigitalDisplay(5, "fps");
		life = new DigitalDisplay(5, "bytes");
		levelCounter = new DigitalDisplay(5, "level");
		
		//fix up font size
		moneyDisplay.setDisplay(Game.getMoney());
		life.setDisplay(Game.lives);
		levelCounter.setDisplay(Game.level);
		
		//add info displays to info panel
		Game.infoPanel.add(life);
		Game.infoPanel.add(moneyDisplay);
		Game.infoPanel.add(fpsDisplay);
		Game.infoPanel.add(levelCounter);
		
		mainWindow.add(Game.infoPanel);
		Game.infoPanel.setBounds(170,5,screenSize.width-170,100);
		
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
		Game.xScale = (Game.widthOfGamePanel / 1256.0);
		Game.yScale = (Game.heightOfGamePanel / 658.0);
		System.out.println(Game.yScale);
		
		//tower range initialization
		DiscThrower.rangeToSet = (int) (Game.widthOfGamePanel * .09);
		NumberGenerator.rangeToSet = (int) (Game.widthOfGamePanel * .1);
		Scanner.rangeToSet = (int) (Game.widthOfGamePanel * .075);
		CommunicationsTower.rangeToSet = (int) (Game.widthOfGamePanel * .075);
		
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
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//responses to the buttons are outsourced to Game
		if (e.getSource() == Game.pauseButton)
			Game.pauseListener();
		else if (e.getSource() == Game.fastForwardButton)
			Game.fastForwardListener();
		else if(e.getSource() == Game.saveButton)
			Game.saveGame();
		else if(e.getSource() == Game.loadButton)
			Game.loadGame();
		else if(e.getSource() == Game.helpButton)
			Game.openHelp();
		else if(e.getSource() == Game.restartButton)
			Game.restart();
		else if(e.getSource() == Game.quitButton)
			Game.quit();
	}
	
}
