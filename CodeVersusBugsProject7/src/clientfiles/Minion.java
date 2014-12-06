/**Minion.java
 * Handles minion-specific stuff.
 * 
 * UPDATE LOG
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