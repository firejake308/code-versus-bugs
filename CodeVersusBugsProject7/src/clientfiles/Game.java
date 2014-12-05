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
 * 		gave the frame an icon
 * 11/15/14:
 * 		you can now pause the game using the gameState variable
 * 11/24/14:
 * 		fixed bug where 2 game frames were being created instead of 1
 * 11/25/14:
 * 		now repaints while paused
 * 11/27/14:
 * 		[BALANCE] starting money increased ($100 -> $150)
 */

package clientfiles;

import java.awt.*;

import javax.swing.*;

import clientfiles.Malware.State;

public class Game extends JFrame implements Runnable
{
	public static final int START = 0;
	public static final int PLAYING = 1;
	public static final int PAUSED = 2;
	public static final int OVER = 3;
	
	public static int gameState = Game.START;
	public static int level = 1;
	public static int fps=60;
	public static boolean gamePlaying = true;
	public static boolean endOfRound = true;
	public static double numFramesPassed = 0;
	public static int money = 150;
	public static int lives = 50;
	private static final long serialVersionUID = 1L;
	public static boolean tutorial = true;
	public static int tutorialSlide = 1;
	
	public static GameFrame gf;
	public static StartMenu startMenu;

	//4 areas of screen
	static public JPanel infoPanel;
	static public GamePanel gamePanel;
	static public TechPanel techPanel;
	static public ShopPanel shopPanel;
	static public JButton pauseButton;
	
	public static int widthOfGamePanel;
	public static int heightOfGamePanel;
	
	// track
	static public JPanel track;
	
	public static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static DisplayMode dm = device.getDisplayMode();
	
	public static void initializeGame()
	{

		try 
		{
			gf.setUndecorated(true);
		    gf.setBounds(0,0,dm.getWidth(),dm.getHeight());
			gf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			gf.setIconImage(MyImages.miniMinion);
			gf.setVisible(true);
			gf.setResizable(false);
		}
		catch(Exception e)
		{
			System.out.println("couldn't enter full screen");
		}
		gamePanel.requestFocusInWindow();
		System.out.println(gamePanel.isFocusOwner());
		
		Projectile.initializeProjectiles();
		Upgrades.initializeUpgrades();
		
		startMenu.setVisible(false);
		
		System.out.println("Game inititalized.");
	}
	
	public void run()
	{
		//each time the game is run, process movements and render
		processMovements(numFramesPassed);
		renderGameState();
	}
	
	public void renderGameState()
	{
		//now works with repaint because game loop doesn't hog EDT
		gamePanel.repaint();
	}
	
	/**handles all movements that have occurred in the past frame
	 * 
	 * @param frames
	 */
	public void processMovements(double frames)
	{
		//move all of the viruses
		for(int v=0; v<gamePanel.lvlManager.getMalwaresThisLevel(); v++)
		{
			if(Malware.allMalware[v]==null)
				break;
			else
				Malware.allMalware[v].moveVirus(frames);
		}
		
		//check if viruses have moved into range of a tower
		Malware target;
		
		for(int t=0; t < GamePanel.numTowers; t++)
		{
			Tower tower = Tower.allTowers[t];
			if(tower == null)
				break;
			else
			{
				target = Tower.findTarget(tower);
				
				//if there is a virus to attack and timer is at 0,
				//then attack and reset timer
				if(target != null && tower.timer == 0)
				{
					//debug code:      System.out.println("tried to attack "+target);
					//System.out.println("Attacked: " + target);
					tower.attack(target, tower.getType());
					tower.timer = tower.timerReset;
				}
				//if timer has been reset, decrement timer once per frame
				else if(tower.timer>0)
					tower.timer--;
			}
		}
		
		//check if any towers are in range of worms
		Tower targetTower;
		for(int m = 0; m < Malware.allMalware.length; m++)
		{
			if(Malware.allMalware[m] instanceof Worm)
			{
				Worm worm = (Worm) Malware.allMalware[m];
				targetTower = Worm.findTarget(worm);
				if(worm.timer==0 && targetTower != null)
				{
					worm.attack(targetTower);
					worm.timer = worm.TIMER_RESET;
				}
				else if(worm.timer > 0)
				{
					worm.timer--;
				}
				else if(targetTower == null && worm.state == State.ATTACKING)
				{
					worm.state = State.NORMAL;
				}
			}
		}
		
		//move any projectiles that exist
		for(int p=0; p<Projectile.allProjectiles.length; p++)
		{
			if(Projectile.allProjectiles[p] == null)
				break;
			else
			{
				Projectile.allProjectiles[p].moveProjectile(frames);
			}
		}
		
		if(ShopPanel.timer > 0)
		{
			ShopPanel.timer--;
		}
		else if (ShopPanel.warned)
		{
			ShopPanel.info.setText("");
			ShopPanel.info.setBackground(Color.white);
			ShopPanel.warned = false;
		}
	}
	
	/**removes cost from player's balance and returns true if the player as enough money to make 
	 * the purchase
	 * @param cost
	 * @return
	 */
	public static boolean makePurchase(int cost)
	{
		if(money >= cost)
		{
			money -= cost;
			gf.moneyLabel.setText("$" + money + " money");
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static int getMoney() 
	{
		return money;
	}

	/**adds income to the player's balance
	 * 
	 * @param income
	 */
	public static void addMoney(int income)
	{
		money += income;
		gf.moneyLabel.setText("$" + money + " money");
	}
}
