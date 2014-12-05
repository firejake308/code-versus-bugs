/**Virus.java
 * Handles movement and drawing of viruses.
 * 
 * UPDATE LOG
 * 11/1/14:	
 * 		speed is now in pixels per second
 * 		x, y, and distance are now doubles for more fine tuning
 * 		made checkpoint variables for future use in implementing different paths
 * 11/2/14:	
 * 		drawVirus and moveVirus are no longer static for more logical code
 * 11/12/14:
 * 		2 paths now exist for viruses to follow
 * 		implemented directions and path arrays in moveVirus()
 * 11/15/14:
 * 		added lanes 3,4 and 5 
 * 		[BALANCE] reduced income from viruses ($10 -> $5)
 * 11/23/14:
 * 		added freezing ability and framework for future abilities
 * 11/27/14:
 * 		[BALANCE] increased speed of virus (20 -> 35)
 * TODO
 * see if we are done with this class for now
 */
package clientfiles;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

public class Minion extends Malware
{
	
	public static Image sprite = MyImages.minion;
	private int w = Game.widthOfGamePanel;
	public int speed = (int) (w * 0.025);
	
	/**creates a virus in the lane specified, at the chosen y value. 
	 * Lanes are numbered from left to right.
	 * 
	 * @param y
	 */
	public Minion(int lane, int y)
	{
		super(lane, y);
		
		//initialize instance variables
		health = 100;
		reward = 5;
	}
	
	/**call this method on a virus object to move the virus
	 * where frames is the interpolation
	 * 
	 * @param frames
	 */
	@Override
	public void moveVirus(double frames)
	{
		// timer for frozen viruses
		if (state == State.FROZEN && elapsedTime < 5)
		{
			elapsedTime += frames/60;
		}
		// to be changed later
		else // reseting
		{
			manipulator = 1;
			elapsedTime = 0;
			state = State.NORMAL;
		}
		
		//move down for first stretch
		if(getDistance()<path[0]) //if distance traveled is less than 2st item in array, then move down
		{
			setY(getY()+directions[0]*speed*frames/60*manipulator);
			setDistance(getDistance()+speed*frames/60*manipulator);
		}
		//move right
		else if(getDistance()<path[1])
		{
			setX(getX()+directions[1]*speed*frames/60*manipulator);
			setDistance(getDistance()+speed*frames/60*manipulator);
		}
		//move down again
		else if(getDistance()<path[2])
		{
			setY(getY()+directions[2]*speed*frames/60*manipulator);
			setDistance(getDistance()+speed*frames/60*manipulator);
		}
		//move right
		else if(getDistance()<path[3])
		{
			setX(getX()+directions[3]*speed*frames/60*manipulator);
			setDistance(getDistance()+speed*frames/60*manipulator);
		}
		//move up
		else if(getDistance()<path[4])
		{
			setY(getY()+directions[4]*speed*frames/60*manipulator);
			setDistance(getDistance()+speed*frames/60*manipulator);
		}
		//move right
		else if(getDistance()<path[5])
		{
			setX(getX()+directions[5]*speed*frames/60*manipulator);
			setDistance(getDistance()+speed*frames/60*manipulator);
		}
		//move down again
		else if(getDistance()<path[6])
		{
			setY(getY()+directions[6]*speed*frames/60*manipulator);
			setDistance(getDistance()+speed*frames/60*manipulator);
		}
		//move left
		else if(getDistance()<path[7])
		{
			setX(getX()+directions[7]*speed*frames/60*manipulator);
			setDistance(getDistance()+speed*frames/60*manipulator);
		}
		//move down again
		else if(getDistance()<path[8])
		{
			setY(getY()+directions[8]*speed*frames/60*manipulator);
			setDistance(getDistance()+speed*frames/60*manipulator);
		}
		//if virus makes it across the map, then despawn virus
		else
		{
			System.out.println("a virus made it across");
			
			Game.lives--;
			Game.gf.life.setText("Lives: " + Game.lives + ".....Round: " + Game.level);
			
			if (Game.lives <= 0)
			{
				Game.gameState = Game.OVER;
			}
			System.out.println(this.toString());
			despawn();
		}
	}
	
}