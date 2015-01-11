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

import javax.swing.*;

import clientfiles.Malware.State;

public class Game extends JFrame implements Runnable
{
	public static final int START = 0;
	public static final int PLAYING = 1;
	public static final int PAUSED = 2;
	public static final int OVER = 3;
	public static int gameState = Game.START;
	public static int fps = 60;
	public static boolean gamePlaying = true;
	public static boolean endOfRound = true;
	public static double numFramesPassed = 0;
	
	//common debugging parameters
	private static int money = 20000;
	public static int lives = 5000;
	public static int level = 1;
	
	public static boolean tutorial = true;
	public static int tutorialSlide = 1;
	public static boolean livesTutorialPlayed = false;
	public static int savedSlide = 1;
	public static boolean freeplay = false;
	
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
	
	static public JButton pauseButton;
	static public JButton fastForwardButton;
	static public JButton saveButton;
	static public JButton loadButton;
	
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
		
		gf.moneyLabel.setDisplay(money);
	}
	public static int getMoney() 
	{
		return money;
	}
	public static void saveGame()
	{
		//reset miscellaneous static variables
		Tower.resetHealth();
		try
		{
			//record all static integer variables
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
			fileOutput.write(NumberGenerator.damageToSet);
			fileOutput.write(NumberGenerator.speedToSet);
			fileOutput.write((int)(Scanner.damageToSet * 1000));
			fileOutput.write(Scanner.arcAngleToSet);
			fileOutput.write(Tower.getHealthToSet());
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
			objOutput.writeObject(Tower.allTowers);
			objOutput.writeObject(Tower.sprites);
			objOutput.writeObject(techPanel.getPointValues());
			objOutput.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
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
			NumberGenerator.damageToSet = fileInput.read();
			NumberGenerator.speedToSet = fileInput.read();
			Scanner.damageToSet = fileInput.read() / 1000;
			Scanner.arcAngleToSet = fileInput.read();
			Tower.setHealthToSet(fileInput.read());
			level = fileInput.read();
			lives = fileInput.read();
			money = fileInput.read();
			tutorialSlide = fileInput.read();
			GamePanel.numTowers = fileInput.read();
			Malware.numMalwares = fileInput.read();
			fileInput.close();
			
			//read objects
			ObjectInputStream objInput = new ObjectInputStream(new FileInputStream("objs.txt"));
			Tower.allTowers = (Tower[]) objInput.readObject();
			Tower.sprites = (JButton[]) objInput.readObject();
			techPanel.setPointValues((int[])objInput.readObject());
			objInput.close();
			
			//turn off fast-forward for smoother transition
			fastForward = false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//update info panel
		gf.life.setText("Bytes Remaining: " + Game.lives);
		gf.moneyLabel.setDisplay(money);
		gf.levelCounter.setText("Level: " + Game.level);
		
		//re-add towers to gamepanel
		for(int t=0; t<Tower.sprites.length; t++)
		{
			if(Tower.sprites[t] == null)
				break;
			else
				gamePanel.add(Tower.sprites[t]);
		}
	}
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
	
	public static void pauseListener()
	{
		//if it's already playing, then pause it
		if(Game.gameState == Game.PLAYING)
		{
			Game.pauseButton.setText("");
			Game.pauseButton.setIcon(PauseButtonListener.sprite);
			
			Game.gameState = Game.PAUSED;
			
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
				//reset static Game variables
				Game.gameState = Game.PAUSED;
				Game.level = 1;
				Game.lives = 5000;
				Game.money = 750;
				Game.tutorialSlide = 1;
				Game.endOfRound = true;
				
				//reset game
				Game.gf.setVisible(false);
				System.out.println(gf);
				System.out.println(gamePanel);
				System.out.println(shopPanel);
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
				DiscThrower.damageToSet = 25;
				DiscThrower.speedToSet = 30;
				NumberGenerator.damageToSet = 0;
				NumberGenerator.speedToSet = 80;
				Scanner.damageToSet = 0.35;
				Scanner.arcAngleToSet = 90;
				Tower.resetHealth();
			}
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
					Game.gamePanel.disableTutorial();
				else
					return;
			}
			
			//doesn't move on to next level unless it is end of round, 
			//changed in Virus when game is paused remotely
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
				if(Game.tutorialSlide == 17 || Game.tutorialSlide == 406)
					Game.gamePanel.nextSlide();
			}
			
			Game.pauseButton.setIcon(PauseButtonListener.pausedSprite);
			Game.gameState = Game.PLAYING;
			
			//special case for minion types tutorial
			if(level == 5 && Game.tutorial && Game.tutorialSlide <= 30)
				Game.gamePanel.nextSlide();
			//move onto files tutorial
			if(level == 20 && Game.tutorial && Game.tutorialSlide <= 72)
				Game.gamePanel.nextSlide();
			//move onto viruses tutorial
			if(level == 21 && Game.tutorial && Game.tutorialSlide <= 92)
				Game.gamePanel.nextSlide();
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
				Malware.allMalware[v].moveVirus(malwareSpeed*frames);
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
				
				if(bot.timer <= 0)
				{
					bot.spawnWave();
					bot.timer = bot.TIMER_RESET;
					System.out.println("massive wave of minions spawned");
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
}
