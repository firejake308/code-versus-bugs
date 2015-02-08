/**Game.java
 * This is the class that handles background events
 * and creates the start screen.
 * 
 * UPDATE LOG
 * 11/1/14:	
 * 		made Game a Runnable so that it can run on EDT
 * 		removed game loop so that it wouldn't hog EDT
 * 		commented out most println()s
 * 11/8/14:
 * 		gave the frame an normalIcon
 * 11/15/14:
 * 		you can now pause the game using the gameState variable
 * 11/24/14:
 * 		fixed bug where 2 game frames were being created instead of 1
 * 11/25/14:
 * 		now repaints while paused
 * 11/27/14:
 * 		[BALANCE] starting money increased ($100 -> $150)
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
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.sound.sampled.*;
import javax.swing.*;

import clientfiles.Malware.State;

public class Game extends JFrame implements Runnable
{
	public static final int START = 0;
	public static final int PLAYING = 1;
	public static final int PAUSED = 2;
	public static final int OVER = 3;
	public static final int WON = 4;
	public static int gameState = Game.START;
	public static int fps = 60;
	public static boolean gamePlaying = true;
	public static boolean endOfRound = true;
	public static double numFramesPassed = 0;
	
	//common debugging parameters
	private static int money = 7500;
	public static int lives = 5000;
	public static int level = 10;
	
	public static boolean tutorial = true;
	public static int tutorialSlide = 1;
	public static boolean livesTutorialPlayed = false;
	public static int savedSlide = 1;
	public static boolean freeplay = false;
	public static boolean soundOn = true;
	
	private static final long serialVersionUID = 1L;
	public static GameFrame gf;
	public static StartMenu startMenu;
	
	// for use with fast forwarding
	public static double speedModifier = 1.0;
	public static boolean fastForward = false;
	
	//4 areas of screen
	static public JPanel infoPanel;
	static public GamePanel gamePanel;
	static public TechPanel techPanel;
	static public ShopPanel shopPanel;
	
	//control buttons in top left
	static public JButton pauseButton;
	static public JButton fastForwardButton;
	static public JButton saveButton;
	static public JButton loadButton;
	static public JButton helpButton;
	static public JButton restartButton;
	static public JButton quitButton;
	
	public static int widthOfGamePanel;
	public static int heightOfGamePanel;
	
	public static double xScale;
	public static double yScale;
	public static double malwareSpeed = 1.0;
	public static double moneyMultiplier = 1.0;
	
	// track
	static public JPanel track;
	
	public static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static DisplayMode dm = device.getDisplayMode();
	
	/**
	 * Adds income to the player's balance.
	 * 
	 * @param income
	 */
	public static void addMoney(int income)
	{
		if(income > 0)
			money += Math.floor(moneyMultiplier * income);
		else
			money += income;
		
		gf.moneyDisplay.setDisplay(money);
	}
	/**
	 * Gets the layer's current balance.
	 * 
	 * @return money
	 */
	public static int getMoney() 
	{
		return money;
	}
	/**
	 * Saves the current game.
	 */
	public static void saveGame()
	{
		//reset miscellaneous static variables
		try
		{
			//record all static integer and boolean variables
			File staticInts = new File("staticInts.txt");
			FileOutputStream fileOutput = new FileOutputStream(staticInts);
			if(Malware.routerOn)
				fileOutput.write(1);
			else
				fileOutput.write(0);
			if(Game.freeplay)
				fileOutput.write(1);
			else
				fileOutput.write(0);
			fileOutput.write(DiscThrower.damageToSet);
			fileOutput.write(DiscThrower.speedToSet);
			fileOutput.write(NumberGenerator.speedToSet);
			fileOutput.write((int)(Scanner.damageToSet * 1000));
			fileOutput.write(Scanner.arcAngleToSet);
			fileOutput.write(Tower.getHealthToSet());
			if(CommunicationsTower.star)
				fileOutput.write(1);
			else
				fileOutput.write(0);
			if(CommunicationsTower.mesh)
				fileOutput.write(1);
			else
				fileOutput.write(0);
			if(CommunicationsTower.uploadingTower)
				fileOutput.write(1);
			else
				fileOutput.write(0);
			fileOutput.write(CommunicationsTower.numOfPacketsToHub);
			fileOutput.write(CommunicationsTower.numOfPacketsToTower);
			fileOutput.write(CommunicationsTower.timerResetHub);
			fileOutput.write(CommunicationsTower.timerHub);
			fileOutput.write(CommunicationsTower.timerResetTower);
			fileOutput.write(CommunicationsTower.timerTower);
			
			fileOutput.write(level);
			fileOutput.write(lives);
			fileOutput.write(money);
			fileOutput.write(tutorialSlide);
			fileOutput.write(GamePanel.numTowers);
			fileOutput.write(Malware.numMalwares);
			fileOutput.close();
			
			//write all objects
			File objs = new File("objs.txt");
			ObjectOutputStream objOutput = new ObjectOutputStream(new FileOutputStream(objs));
			objOutput.writeDouble(NumberGenerator.damageToSet);
			objOutput.writeObject(Tower.allTowers);
			objOutput.writeObject(Tower.sprites);
			objOutput.writeObject(techPanel.getPointValues());
			objOutput.writeObject(CommunicationsTower.connectingTower);
			objOutput.writeObject(CommunicationsTower.towerHub);
			objOutput.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Loads a saved game.
	 */
	public static void loadGame()
	{
		try
		{
			//read integers and booleans
			FileInputStream fileInput = new FileInputStream("staticInts.txt");
			int router = fileInput.read();
			if(router == 1)
				Malware.routerOn = true;
			else
				Malware.routerOn = false;
			//turn on freeplay if necessary
			if(fileInput.read() == 1)
				gamePanel.enterFreeplay();
			else
				gamePanel.enterStoryMode();
			DiscThrower.damageToSet = fileInput.read();
			DiscThrower.speedToSet = fileInput.read();
			NumberGenerator.speedToSet = fileInput.read();
			Scanner.damageToSet = fileInput.read() / 1000.0;
			Scanner.arcAngleToSet = fileInput.read();
			Tower.setHealthToSet(fileInput.read());
			
			//set comm tower static variables
			if(fileInput.read() == 1)
				CommunicationsTower.star = true;
			else
				CommunicationsTower.star = false;
			if(fileInput.read() == 1)
				CommunicationsTower.mesh = true;
			else
				CommunicationsTower.mesh = false;
			if(fileInput.read() == 1)
				CommunicationsTower.uploadingTower = true;
			else
				CommunicationsTower.uploadingTower = false;
			CommunicationsTower.numOfPacketsToHub=fileInput.read();
			CommunicationsTower.numOfPacketsToTower=fileInput.read();
			CommunicationsTower.timerResetHub=fileInput.read();
			CommunicationsTower.timerHub=fileInput.read();
			CommunicationsTower.timerResetTower=fileInput.read();
			CommunicationsTower.timerTower=fileInput.read();
			
			level = fileInput.read();
			lives = fileInput.read();
			money = fileInput.read();
			tutorialSlide = fileInput.read();
			GamePanel.numTowers = fileInput.read();
			Malware.numMalwares = fileInput.read();
			fileInput.close();
			
			//read objects
			ObjectInputStream objInput = new ObjectInputStream(new FileInputStream("objs.txt"));
			NumberGenerator.damageToSet = objInput.read();
			Tower.allTowers = (Tower[]) objInput.readObject();
			Tower.sprites = (JButton[]) objInput.readObject();
			techPanel.setPointValues((int[])objInput.readObject());
			CommunicationsTower.connectingTower = (Tower) objInput.readObject();
			CommunicationsTower.towerHub = (Tower) objInput.readObject();
			objInput.close();
			
			//turn off fast-forward for smoother transition
			fastForward = false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//update info panel
		gf.life.setDisplay(Game.lives);
		gf.moneyDisplay.setDisplay(money);
		gf.levelCounter.setDisplay(Game.level);
		
		//re-add towers to gamepanel
		for(int t=0; t<Tower.sprites.length; t++)
		{
			if(Tower.sprites[t] == null)
				break;
			else
				gamePanel.add(Tower.sprites[t]);
		}
	}
	/**
	 * Opens the help menu.
	 */
	public static void openHelp()
	{
		Help help = new Help();
		help.setVisible(true);
		help.setFocusable(true);
	}
	/**
	 * Restarts the game. First brings up a dialog for confirmation.
	 */
	public static void restart()
	{
		//propmt user if he/she to confirm
		int choice = JOptionPane.showConfirmDialog(Game.gf.getContentPane(), "Are you sure you want to restart?", "Restart", 
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		
		//resume game if user dosn't actually want to restart
		if(choice == JOptionPane.NO_OPTION)
		{
			return;
		}
		
		//reset static Game variables
		Game.gameState = Game.PAUSED;
		Game.level = 1;
		Game.lives = 5000;
		Game.money = 750;
		Game.tutorialSlide = 1;
		Game.endOfRound = true;
		
		//reset game
		Game.gf.setVisible(false);
		Game.gf = new GameFrame();
		Game.initializeGame();
		
		//reset static collections
		Tower.allTowers = new Tower[1000];
		GamePanel.numTowers = 0;
		Tower.sprites = new JButton[1000];
		Malware.allMalware = new Malware[10000];
		Malware.numMalwares = 0;
		BonusFile.allFiles = new ArrayList<BonusFile>();
		Projectile.allProjectiles = new ArrayList<Projectile>();
		
		//reset miscellaneous static variables
		Malware.routerOn = false;
		DiscThrower.damageToSet = 35;
		DiscThrower.speedToSet = 60;
		NumberGenerator.damageToSet = 0.0;
		NumberGenerator.speedToSet = 120;
		Scanner.damageToSet = 0.25;
		Scanner.arcAngleToSet = 90;
		Tower.resetHealth();
		CommunicationsTower.star = false;
		CommunicationsTower.mesh = false;
		CommunicationsTower.uploadingTower = false;
		CommunicationsTower.numOfPacketsToHub = 0;
		CommunicationsTower.numOfPacketsToTower = 0;
		CommunicationsTower.timerResetHub = 150;
		CommunicationsTower.timerHub = 0;
		CommunicationsTower.timerResetTower = 150;
		CommunicationsTower.timerTower = 0;
		CommunicationsTower.connectingTower = null;
		CommunicationsTower.towerHub = null;
		
		//turn on freeplay if was playing freeplay earlier
		if(freeplay)
			gamePanel.enterFreeplay();
		else
			gamePanel.enterStoryMode();
	}
	/**
	 * Quits the game. First brings up a dialog for confirmation. Returns without doing anything if the user presses no.
	 */
	public static void quit()
	{
		//ask user to confirm quitting
		int choice = JOptionPane.showConfirmDialog(gf, "Are you sure you want to quit?", "Quit Game", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		
		//force quit game if yes
		if(choice == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	/**
	 * Initializes many game variables.
	 */
	public static void initializeGame()
	{
		try 
		{
			gf.setUndecorated(true);
		    gf.setBounds(0,0,dm.getWidth(),dm.getHeight());
			gf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ArrayList<BufferedImage> icons = new ArrayList<BufferedImage>();
			icons.add(MyImages.miniMinion);
			icons.add(MyImages.miniMinion2);
			icons.add(MyImages.miniMinion3);
			gf.setIconImages(icons);
			gf.setVisible(true);
			gf.setResizable(false);
		}
		catch(Exception e)
		{
			System.out.println("couldn't enter full screen");
		}
		gamePanel.requestFocusInWindow();
		System.out.println(gamePanel.isFocusOwner());
		Upgrades.initializeUpgrades();
		
		startMenu.setVisible(false);
		
		// if tutorial is on, set up game accordingly
		if (tutorial)
		{
			//just an idea
		}
		
		System.out.println("Game inititalized.");
	}
	/**
	 * Removes cost from player's balance and returns true if the player as enough money to make 
	 * the purchase.
	 * 
	 * @param cost the cost of the purchase
	 * @return if user has enough money to make the purchase
	 */
	public static boolean makePurchase(int cost)
	{
		if(money >= cost)
		{
			addMoney(-cost);
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * Enacts the appropriate reaction once the pause/play button is pressed.
	 */
	public static void pauseListener()
	{
		//if it's already playing, then pause it
		if(Game.gameState == Game.PLAYING)
		{
			Game.pauseButton.setText("");
			Game.pauseButton.setIcon(PauseButtonListener.sprite);
			
			Game.gameState = Game.PAUSED;
			/*legacy code
			//show dialog to quit or resume
			Object[] options = {"Resume", "Quit", "Restart"};
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
			else if(choice == 2)
			{
				
			}*/
		}
		else if(Game.gameState == Game.PAUSED)
		{
			//if tutorial is on and user clicks prematurely
			if(Game.tutorialSlide < 17 && Game.tutorial)
			{
				Object[] options = {"Oops. I'll go back.", "Stop bothering me!"};
				int choice = JOptionPane.showOptionDialog(Game.gf, "Are you sure you're ready to start the round?", 
						"WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, 0);
				if(choice == 1)
					Game.gamePanel.disableTutorial();
				return;
			}
			else if(Game.tutorialSlide == 29 && Game.tutorial)
			{
				Object[] options = {"Oops. I'll go back.", "Stop bothering me!"};
				int choice = JOptionPane.showOptionDialog(Game.gf, "Are you sure you want to start without upgrading your hardware?", 
						"WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, 0);
				if(choice == 1)
				{
					Game.gamePanel.disableTutorial();
					Game.techPanel.disableTutorial();
				}
				else
					return;
			}
			
			//doesn't move on to next level unless it is end of round, which is
			//changed in Malware.java when game is paused remotely
			if (Game.endOfRound == true)
			{
				Game.endOfRound = false;
				Game.gamePanel.lvlManager.nextlvl();
				//if it hasn't been done already, switch tech panel to game panel
				if(Game.techPanel.isShowing())
				{	
					Game.gamePanel.setVisible(true);
					Game.techPanel.setVisible(false);
				}
				
				//disable save/load once round starts
				Game.saveButton.setEnabled(false);
				Game.loadButton.setEnabled(false);
				
				//special case for tutorial slide 17 and 406
				if(Game.tutorialSlide == 17)
					Game.gamePanel.nextSlide();
			}
			
			Game.pauseButton.setIcon(PauseButtonListener.pausedSprite);
			Game.gameState = Game.PLAYING;
		}
	}
	
	public static void fastForwardListener()
	{
		if (!fastForward)
		{
			fastForwardButton.setIcon(new ImageIcon(MyImages.fastForwardOn));
			fastForward = true;
			speedModifier = 2.0;
		}
		
		else if (fastForward)
		{
			fastForwardButton.setIcon(new ImageIcon(MyImages.fastForwardOff));
			fastForward = false;
			speedModifier = 1.0;
		}
	}
	
	/**
	 * Handles all movements that have occurred in the past frame.
	 * 
	 * @param frames
	 */
	public void processMovements(double frames)
	{
		//move all of the viruses
		for(int v=0; v<Malware.numMalwares; v++)
		{
			if(Malware.allMalware[v]==null)
				break;
			else
				Malware.allMalware[v].move(malwareSpeed*frames);
		}
		
		//check if viruses have moved into range of a tower
		Malware target;
		
		for(int t=0; t < GamePanel.numTowers; t++)
		{
			Tower tower = Tower.allTowers[t];
			if(tower == null)
				break;
			else if (tower instanceof Scanner)
			{
				((Scanner) tower).scan(frames);
				
				for (int m = 0; m < Malware.numMalwares; m++)
				{
					if(Malware.allMalware[m] == null)
						break;
					else
					{
						Point malware = new Point(Malware.allMalware[m].getCenterX(), Malware.allMalware[m].getCenterY());
						
						if(tower.scan.contains(malware) && ((Scanner) tower).tickCounter >= 1)
						{
							Malware.allMalware[m].dealDamage(tower.damage, 1, t);
							((Scanner) tower).tickCounter = 0;
							
							if (Malware.allMalware[m] instanceof Worm && ((Scanner)tower).disableWorms)
							{
								Malware.allMalware[m].state = State.BENIGN;
							}
							if (Malware.allMalware[m] instanceof Trojan && Malware.allMalware[m].state == State.INVISIBLE)
							{
								((Trojan)Malware.allMalware[m]).makeVisible();
							}
						}
						
						((Scanner) tower).tickCounter++;
						if (fastForward)
							((Scanner) tower).tickCounter++;
					}
				}
			}
			else if (tower instanceof FireWall)
			{
				((FireWall) tower).dealDamage();
			}
			else if (tower instanceof Encrypter)
			{
				((Encrypter) tower).encryptAll();
			}
			else
			{
				target = Tower.findTarget(tower);
				
				//if there is a virus to attack and timer is at 0,
				//then attack and reset timer
				if(target != null && tower.timer <= 0)
				{
					tower.attack(target, tower.getType());
					tower.timer = tower.timerReset;
				}
				//if timer has been reset, decrement timer once (or twice if ff on) per frame
				else if(tower.timer>0)
					tower.timer-=speedModifier;
				if (fastForward)
					tower.timer-=speedModifier;
			}
		}
		
		//check if any towers are in range of worms
		Tower targetTower;
		for(int m = 0; m < Malware.allMalware.length; m++)
		{
			if(Malware.allMalware[m] instanceof Worm)
			{
				Worm worm = (Worm) Malware.allMalware[m];
				if (worm.state != State.BENIGN)
					targetTower = Worm.findTarget(worm);
				else
					targetTower = null;
				
				if(worm.timer==0 && targetTower != null)
				{
					worm.attack(targetTower);
					worm.timer = worm.TIMER_RESET;
				}
				else if(worm.timer > 0)
				{
					worm.timer-=speedModifier;
				}
				else if(targetTower == null && worm.state == State.ATTACKING)
				{
					worm.state = State.NORMAL;
				}
			}
		}
		
		//spawn minions around bots
		for(int m = 0; m < Malware.allMalware.length; m++)
		{
			if(Malware.allMalware[m] instanceof Bot)
			{
				Bot bot = (Bot) Malware.allMalware[m];
				
				if(bot.timer <= 0 && bot.getY() >= 0)
				{
					bot.spawnWave();
					bot.timer = bot.TIMER_RESET;
					//System.out.println("massive wave of minions spawned");
				}
				else if(bot.timer > 0)
				{
					bot.timer -= speedModifier;
				}
			}
		}
		
		//move any projectiles that exist
		for(Projectile p:Projectile.allProjectiles)
		{
			if(p != null)
			{
				p.moveProjectile(frames);
			}
		}
		//recycle any dead projectiles
		ListIterator<Projectile> iterator = Projectile.recycleBin.listIterator();
		while(iterator.hasNext())
		{
			Projectile curr = iterator.next();
			Projectile.allProjectiles.remove(curr);
			iterator.remove();
		}
		
		//fade away shop panel
		if(ShopPanel.timer > 0)
		{
			ShopPanel.timer--;
		}
		else
		{
			ShopPanel.info.setText("");
			ShopPanel.info.setBackground(Color.white);
			ShopPanel.warned = false;
		}
		
		
		
		// call handlePackets on comm towers to allow for more to move
		if (CommunicationsTower.timerResetHub <= CommunicationsTower.timerHub && !CommunicationsTower.mesh)
		{
			CommunicationsTower.handlePackets(true);
			CommunicationsTower.timerHub = 0;
		}
		else
		{
			CommunicationsTower.timerHub++;
			if (fastForward)
				CommunicationsTower.timerHub++;
		}
		
		if (CommunicationsTower.timerResetTower <= CommunicationsTower.timerTower)
		{
			CommunicationsTower.handlePackets(false);
			CommunicationsTower.timerTower = 0;
		}
		else
		{
			CommunicationsTower.timerTower++;
			if (fastForward)
				CommunicationsTower.timerTower++;
		}
		
		
		//move any files that exist
		for(BonusFile p:BonusFile.allFiles)
		{
			if(p != null)
			{
				p.move(frames * Game.speedModifier);
			}
		}
		BonusFile.emptyBin();
	}

	public void renderGameState()
	{
		//now works with repaint because game loop doesn't hog EDT
		gamePanel.repaint();
	}
	
	public void run()
	{
		//just to be sure, try to disable the start menu
		if(startMenu.isShowing())
			startMenu.setVisible(false);
		
		//each time the game is run, process movements and render
		processMovements(numFramesPassed);
		renderGameState();
	}
	
	/**
	 * Plays a sound.
	 * @param fileName name of a file in clientfiles.resources including suffix
	 */
	public static void playSound(String fileName)
	{
		if(soundOn)
			new Thread(new Runnable()
			{
				public void run()
				{
					try{
						AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("resources/"+fileName));
						AudioFormat format = audioStream.getFormat();
						DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
						SourceDataLine enterKeypress = (SourceDataLine) AudioSystem.getLine(info);
						
						enterKeypress.open(format);
						enterKeypress.start();
						
						int BUFFER_SIZE = 4096;
						 
						byte[] bytesBuffer = new byte[BUFFER_SIZE];
						int bytesRead = -1;
						 
						while ((bytesRead = audioStream.read(bytesBuffer)) != -1) {
						    enterKeypress.write(bytesBuffer, 0, bytesRead);
						}
						
						enterKeypress.drain();
						enterKeypress.close();
						audioStream.close();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}).start();
	}
}
