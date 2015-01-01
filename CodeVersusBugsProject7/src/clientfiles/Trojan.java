package clientfiles;

import java.awt.Image;

public class Trojan extends Malware 
{
	//shortcut variable to width of game panel
	private static int w = Game.widthOfGamePanel;
	public static Image sprite = MyImages.secureFile;
	
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
		health = 800;
		reward = 10;
		speed = (int) (w * 0.0075);
		state = State.INVISIBLE;
		
		//System.out.println("x "+x+" y "+y);
	}
	
	@Override
	public void moveVirus(double frames)
	{
		//keep going up until hits cpu
		if(getY() > Game.gamePanel.path[1].getY())
		{
			setY(getY()-speed*frames/60*manipulator * Game.speedModifier);
			setDistance(getDistance()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//if virus makes it across the map, then despawn virus
		else
		{
			System.out.println("a virus made it across");
					
			Game.lives-=health;
			Game.gf.life.setText("Bytes Remaining: " + Game.lives);
					
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
