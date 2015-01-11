package clientfiles;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import clientfiles.Malware.State;
/**
 * Creates slow, tanky worms that attack your towers
 * @author Ahmadul
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
public class Worm extends Malware
{
	//static variables for images
	public static Image sprite = new ImageIcon(MyImages.wormHead).getImage();
	public static Image sprite2 = new ImageIcon(MyImages.wormBody).getImage();
	//shortcut variable to width of game panel
	private static int w = Game.widthOfGamePanel;
	
	//instance variables that weren't inherited from Malware
	private double x2;
	private double y2;
	private double distance2;
	private double x3;
	private double y3;
	private double distance3;
	private double x4;
	private double y4;
	private double distance4;
	private int range;
	private int damage;
	private int lane;
	public int timer=0;
	public final int TIMER_RESET = 60;
	
	public Worm(int lane, int y) 
	{
		//create a worm in specified lane at specified y value
		super(0, lane, y);
		
		offensive = true;
		
		//set secondary positions
		x2 = x;
		y2 = y - sprite.getHeight(null);
		distance2 = distance - sprite.getHeight(null);
		x3 = x;
		y3 = y- 2*sprite.getHeight(null);
		distance3 = distance - 2*sprite.getHeight(null);
		x4 = x;
		y4 = y - 3*sprite.getHeight(null);
		distance4 = distance - 3*sprite.getHeight(null);
		
		//initialize instance variables
		health = 1400;
		reward = 5;
		range = (int) (Game.widthOfGamePanel * .03);
		damage = 10;
		speed = (int) (w * 0.020);
		this.lane = lane;
	}
	
	private double getX2() 
	{
		return x2;
	}

	private void setX2(double x2) 
	{
		this.x2 = x2;
	}

	private double getY2()
	{
		return y2;
	}

	private void setY2(double y2)
	{
		this.y2 = y2;
	}

	private double getDistance2() 
	{
		return distance2;
	}

	private void setDistance2(double distance2)
	{
		this.distance2 = distance2;
	}

	private double getX3() 
	{
		return x3;
	}

	private void setX3(double x3) 
	{
		this.x3 = x3;
	}

	private double getY3() 
	{
		return y3;
	}

	private void setY3(double y3) 
	{
		this.y3 = y3;
	}

	private double getDistance3() 
	{
		return distance3;
	}

	private void setDistance3(double distance3) 
	{
		this.distance3 = distance3;
	}

	private double getX4()
	{
		return x4;
	}

	private void setX4(double x4) 
	{
		this.x4 = x4;
	}

	private double getY4()
	{
		return y4;
	}

	private void setY4(double y4)
	{
		this.y4 = y4;
	}

	private double getDistance4() 
	{
		return distance4;
	}

	private void setDistance4(double distance4)
	{
		this.distance4 = distance4;
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
		else if(state == State.ATTACKING)
		{
			manipulator = 0;
		}
		// to be changed later
		//reset
		else
		{
			manipulator = 1;
			elapsedTime = 0;
			state = State.NORMAL;
		}
		
		//move down for first stretch
		if(getDistance()<path[0]) //if distance traveled is less than 2st item in array, then move down
		{
			setY(getY()+directions[0]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance(getDistance()+speed*frames/60*manipulator * Game.speedModifier);
			
			//special case for worms tutorial
			if(getDistance() > path[0]/2)
			{
				if(Game.tutorial && Game.tutorialSlide <= 50 && this instanceof Worm)
					Game.gamePanel.nextSlide();
			}
		}
		//move right
		else if(getDistance()<path[1])
		{
			setX(getX()+directions[1]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance(getDistance()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move down again
		else if(getDistance()<path[2])
		{
			setY(getY()+directions[2]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance(getDistance()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move right
		else if(getDistance()<path[3])
		{
			setX(getX()+directions[3]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance(getDistance()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move up
		else if(getDistance()<path[4])
		{
			setY(getY()+directions[4]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance(getDistance()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move right
		else if(getDistance()<path[5])
		{
			setX(getX()+directions[5]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance(getDistance()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move down again
		else if(getDistance()<path[6])
		{
			setY(getY()+directions[6]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance(getDistance()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move left
		else if(getDistance()<path[7])
		{
			setX(getX()+directions[7]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance(getDistance()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move down again
		else if(getDistance()<path[8])
		{
			setY(getY()+directions[8]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance(getDistance()+speed*frames/60*manipulator * Game.speedModifier);
		}
		
		/********************now for x2, y2 and distance2**************************/	
		
		//move down for first stretch
		if(getDistance2()<path[0]) //if distance traveled is less than 2st item in array, then move down
		{
			setY2(getY2()+directions[0]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance2(getDistance2()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move right
		else if(getDistance2()<path[1])
		{
			setX2(getX2()+directions[1]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance2(getDistance2()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move down again
		else if(getDistance2()<path[2])
		{
			setY2(getY2()+directions[2]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance2(getDistance2()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move right
		else if(getDistance2()<path[3])
		{
			setX2(getX2()+directions[3]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance2(getDistance2()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move up
		else if(getDistance2()<path[4])
		{
			setY2(getY2()+directions[4]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance2(getDistance2()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move right
		else if(getDistance2()<path[5])
		{
			setX2(getX2()+directions[5]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance2(getDistance2()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move down again
		else if(getDistance2()<path[6])
		{
			setY2(getY2()+directions[6]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance2(getDistance2()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move left
		else if(getDistance2()<path[7])
		{
			setX2(getX2()+directions[7]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance2(getDistance2()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move down again
		else if(getDistance2()<path[8])
		{
			setY2(getY2()+directions[8]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance2(getDistance2()+speed*frames/60*manipulator * Game.speedModifier);
		}
		
		/************************now for x3, y3, distance3*******************************/
		
		//move down for first stretch
		if(getDistance3()<path[0]) //if distance traveled is less than 2st item in array, then move down
		{
			setY3(getY3()+directions[0]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance3(getDistance3()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move right
		else if(getDistance3()<path[1])
		{
			setX3(getX3()+directions[1]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance3(getDistance3()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move down again
		else if(getDistance3()<path[2])
		{
			setY3(getY3()+directions[2]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance3(getDistance3()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move right
		else if(getDistance3()<path[3])
		{
			setX3(getX3()+directions[3]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance3(getDistance3()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move up
		else if(getDistance3()<path[4])
		{
			setY3(getY3()+directions[4]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance3(getDistance3()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move right
		else if(getDistance3()<path[5])
		{
			setX3(getX3()+directions[5]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance3(getDistance3()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move down again
		else if(getDistance3()<path[6])
		{
			setY3(getY3()+directions[6]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance3(getDistance3()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move left
		else if(getDistance3()<path[7])
		{
			setX3(getX3()+directions[7]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance3(getDistance3()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move down again
		else if(getDistance3()<path[8])
		{
			setY3(getY3()+directions[8]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance3(getDistance3()+speed*frames/60*manipulator * Game.speedModifier);
		}
		
		/***********************now for x4, y4, distance4*****************/
		
		//move down for first stretch
		if(getDistance4()<path[0]) //if distance traveled is less than 2st item in array, then move down
		{
			setY4(getY4()+directions[0]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance4(getDistance4()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move right
		else if(getDistance4()<path[1])
		{
			setX4(getX4()+directions[1]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance4(getDistance4()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move down again
		else if(getDistance4()<path[2])
		{
			setY4(getY4()+directions[2]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance4(getDistance4()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move right
		else if(getDistance4()<path[3])
		{
			setX4(getX4()+directions[3]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance4(getDistance4()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move up
		else if(getDistance4()<path[4])
		{
			setY4(getY4()+directions[4]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance4(getDistance4()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move right
		else if(getDistance4()<path[5])
		{
			setX4(getX4()+directions[5]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance4(getDistance4()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move down again
		else if(getDistance4()<path[6])
		{
			setY4(getY4()+directions[6]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance4(getDistance4()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move left
		else if(getDistance4()<path[7])
		{
			setX4(getX4()+directions[7]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance4(getDistance4()+speed*frames/60*manipulator * Game.speedModifier);
		}
		//move down again
		else if(getDistance4()<path[8])
		{
			setY4(getY4()+directions[8]*speed*frames/60*manipulator * Game.speedModifier);
			setDistance4(getDistance4()+speed*frames/60*manipulator * Game.speedModifier);
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
	public void attack(Tower target)
	{
		//calculate the angle at which the projectile should move
		double slope = (double)(target.getCenterY() - this.getCenterY()) / (target.getCenterX() - this.getCenterX());
		//absolute value of arctan, so angle is always positive
		double angle = Math.abs(Math.atan(slope));
		double a = Math.sin(angle);
		double b = Math.cos(angle);
		
		int quadrant = 0;
		if (target.getCenterX() >= this.getCenterX() && target.getCenterY() <= this.getCenterY())
			quadrant = 1;
		else if (target.getCenterX() <= this.getCenterX() && target.getCenterY() <= this.getCenterY())
			quadrant = 2;
		else if (target.getCenterX() <= this.getCenterX() && target.getCenterY() >= this.getCenterY())
			quadrant = 3;
		else if (target.getCenterX() >= this.getCenterX() && target.getCenterY() >= this.getCenterY())
			quadrant = 4;
		
		//add a new projectile to list 
		Projectile.allProjectiles.add(new Projectile(a, b, quadrant, getCenterX(), getCenterY(), id, damage));
	}
	
	public static Tower findTarget(Worm worm)
	{
		double xOfTower = 0;
		double yOfTower = 0;
		int xOfWorm = worm.getCenterX();
		int yOfWorm = worm.getCenterY();
		int i = 0;
		double range = worm.range;
		double distanceFromWorm = 1000;
		Tower towerToAttack = null;
		
		i = 0;
		boolean attackingFireWall = false;
		
		while (i < Tower.allTowers.length)
		{
			//necessary to prevent null pointer exceptions
			if(Tower.allTowers[i] == null)
				break;
			
			if (Tower.allTowers[i] instanceof FireWall)
				attackingFireWall = true;
			
			//compare tower and worm x to get distance between them
			xOfTower = Tower.allTowers[i].getCenterX();
			yOfTower = Tower.allTowers[i].getCenterY();
			distanceFromWorm = Math.sqrt(Math.pow(xOfWorm - xOfTower, 2) + Math.pow(yOfWorm - yOfTower, 2));
			
			//if tower is close enough and not infected, stop moving and attack it
			if(distanceFromWorm < range && !Tower.allTowers[i].isInfected() && !attackingFireWall)
			{
				towerToAttack = Tower.allTowers[i];
				worm.state = State.ATTACKING;
			}
			i++;
		}
		
		return towerToAttack;
	}
	
	@Override
	public void drawVirus(Graphics g)
	{
		if(health>0)
		{
			g.drawImage(sprite, (int)getX(), (int)getY(), null);
		}
		if(health>150)
		{
			g.drawImage(sprite2, (int)getX2(), (int)getY2(), null);
		}
		if(health>300)
		{
			g.drawImage(sprite2, (int)getX3(), (int)getY3(), null);
		}
		if(health>450)
		{
			g.drawImage(sprite2, (int)getX4(), (int)getY4(), null);

		}
	}
}