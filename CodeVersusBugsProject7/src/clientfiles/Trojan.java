package clientfiles;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class Trojan extends Malware 
{
	//shortcut variable to width of game panel
	private static int w = Game.widthOfGamePanel;
	public static BufferedImage sprite = MyImages.secureFile;
	
	/**
	 * Creates a Trojan.
	 * @param type NORMAL, RUSH, or TANK
	 * @param y the y value to create the trojan at
	 */
	public Trojan(int type, int y) 
	{
		super(type, 0, y);
		
		setCenterX((int) ((Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 84) + Game.widthOfGamePanel / 3));
		
		//initialize instance variables
		maxHealth = 1000;
		health = 1000;
		reward = 5;
		speed = (int) (w * 0.008);
		state = State.INVISIBLE;
		
		//System.out.println("x "+x+" y "+y);
	}
	
	@Override
	public void move(double frames)
	{
		//keep going up until hits cpu
		if(getY() > Game.gamePanel.path[1].getY())
		{
			//
			if(getY() < Game.gamePanel.getHeight() && firstTrojan)
			{
				Game.playSound("danger.wav");
				firstTrojan = false;
				Game.gameState = Game.PAUSED;
				Game.gamePanel.infoPopup = new InfoPopup((int)x-200, (int)y-200);
				Game.gamePanel.infoPopup.setVisible(true);
	        	Game.gamePanel.infoPopup.setTitle("Trojan");
	        	Game.gamePanel.infoPopup.setInfo1("Health: " + this.maxHealth);
	        	Game.gamePanel.infoPopup.setInfo2("Ability: Sneaks in to the system through the backdoor.");
	        	Game.gamePanel.infoPopup.setInfo3("Fact: Trojans may look legitimate, but they're actually malicious.");
			}
			setY(getY()-speed*frames/60*manipulator * Game.speedModifier);
			setDistance(getDistance()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//if virus makes it across the map, then despawn virus
		else
		{
			System.out.println("a virus made it across");
					
			Game.lives-=health;
			Game.gf.life.setDisplay(Game.lives);
					
			if (Game.lives <= 0)
			{
				Game.gameState = Game.OVER;
			}
			System.out.println(this.toString());
			despawn();
		}
	}
	
	public void makeVisible()
	{
		state = State.NORMAL;
		super.sprite = MyImages.trojan;
	}
}
