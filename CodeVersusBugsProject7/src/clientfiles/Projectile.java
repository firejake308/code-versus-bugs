package clientfiles;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

/**Projectile.java
 * A CD that flies toward a targeted virus
 * 
 * @author Patrick Kenney
 * 
 * UPDATE LOG
 * 11/15/14:
 * 		experimented with setting projectile's speed in pixels per second. Does not work.
 * 		[BALANCE] reduced speed (10 -> 5)
 * 		[BALANCE] reduced damage (100 -> 50)
 * 11/26/2014:
 * 		added multiple uses to projectiles, known bugs: does not always kill 2 but always hits 1
 * 		added splash effect ability
 * 11/28/2014:
 * 		fixed targeting system with multiple kills(dts)
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

public class Projectile
{
	public static Projectile[] allProjectiles = new Projectile[1000];
	
	private double x;
	private double y;
	
	private int id;
	private int idOfTower;
	
	private int quadrant;
	private int uses;
	private boolean splashEffect = false;
	
	private double a;
	private double b;
	private double speed;
	private double damage;
	private double manipulatorForVirus;
	
	private Image sprite;
	static Random rand = new Random(31415);
	
	// so you don't double hit viruses
	Malware[] virusesHit = new Malware[15];
	private int numOfVirusesHit = 0;
	boolean virusHit = false;
	
	// so you get maximum splash
	boolean splashEffectActivated = false;
	private double splashRange;
	
	private final int TOWER = -45;
	private final int WORM = 342;
	private int owner;
	
	public Projectile(double aToSet, double bToSet, int quadrantToSet, double xToSet, double yToSet, TowerType towerType, int location, int towerID, double damageToSet)
	{
		owner = TOWER;
		
		idOfTower = towerID;
		
		if (/*towerType==TowerType.DISC_THROWER*/Tower.allTowers[idOfTower] instanceof DiscThrower)
		{
			sprite = MyImages.cd;
			speed = 5;
			
			// affects speed of virus
			manipulatorForVirus = 1;
		}
		else if(/*towerType == TowerType.NUMBER_GENERATOR*/Tower.allTowers[idOfTower] instanceof NumberGenerator)
		{
			if (Tower.allTowers[idOfTower].lethalRandoms)
			{
				ImageIcon[] randoms = {
						new ImageIcon(MyImages.r0lethal), new ImageIcon(MyImages.r1lethal),
						new ImageIcon(MyImages.r2lethal), new ImageIcon(MyImages.r3lethal),
						new ImageIcon(MyImages.r4lethal), new ImageIcon(MyImages.r5lethal),
						new ImageIcon(MyImages.r6lethal), new ImageIcon(MyImages.r7lethal),
						new ImageIcon(MyImages.r8lethal), new ImageIcon(MyImages.r9lethal)};
				int r = rand.nextInt(randoms.length);
				sprite = randoms[r].getImage();
			}
			else
			{
				ImageIcon[] randoms = {
						new ImageIcon(MyImages.r0), new ImageIcon(MyImages.r1),
						new ImageIcon(MyImages.r2), new ImageIcon(MyImages.r3),
						new ImageIcon(MyImages.r4), new ImageIcon(MyImages.r5),
						new ImageIcon(MyImages.r6), new ImageIcon(MyImages.r7),
						new ImageIcon(MyImages.r8), new ImageIcon(MyImages.r9)};
				int r = rand.nextInt(randoms.length);
				sprite = randoms[r].getImage();
			}
			
			speed=3;
			
			splashRange = Tower.allTowers[idOfTower].rangeOfSplash;
			
			//change icon of the number generator
			int r1 = NumberGenerator.rand.nextInt(NumberGenerator.icons.length);
			Tower.sprites[idOfTower].setIcon(NumberGenerator.icons[r1]);
			
			// affects speed of virus
			manipulatorForVirus = 0;
		}
		
		a = aToSet;
		b = bToSet;
		quadrant = quadrantToSet;
		x = xToSet - sprite.getWidth(null)/2;
		y = yToSet - sprite.getHeight(null)/2;
		id = location;
		damage = damageToSet;
		uses = Tower.allTowers[idOfTower].projectileDurability;
		splashEffect = Tower.allTowers[idOfTower].splashEffect;
	}
	/**ONLY FOR USE WITH WORMS TO ATTACK TOWERS
	 * 
	 * @param aToSet
	 * @param bToSet
	 * @param quadrantToSet
	 * @param xToSet
	 * @param yToSet
	 * @param location
	 * @param damageToSet
	 */
	public Projectile(double aToSet, double bToSet, int quadrantToSet, double xToSet, double yToSet, int location, double damageToSet)
	{
		owner = WORM;
		
		ImageIcon[] randoms = {
				new ImageIcon(MyImages.r0), new ImageIcon(MyImages.r1),
				new ImageIcon(MyImages.r2), new ImageIcon(MyImages.r3),
				new ImageIcon(MyImages.r4), new ImageIcon(MyImages.r5),
				new ImageIcon(MyImages.r6), new ImageIcon(MyImages.r7),
				new ImageIcon(MyImages.r8), new ImageIcon(MyImages.r9)};
		int r = rand.nextInt(randoms.length);
		sprite = randoms[r].getImage();
		
		speed=3;
		
		a = aToSet;
		b = bToSet;
		quadrant = quadrantToSet;
		x = xToSet - sprite.getWidth(null)/2;
		y = yToSet - sprite.getHeight(null)/2;
		id = location;
		damage = damageToSet;
		
		uses = 1;
		splashEffect = false;
		splashRange = 0;
	}
	/**returns the x coordinate for drawing purposes*/
	public double getX()
	{
		return x;
	}
	/**returns the y coordinate for drawing purposes*/
	public double getY()
	{
		return y;
	}
	/**returns the x coordinate for math purposes*/
	public double getCenterX()
	{
		return x + sprite.getWidth(null)/2;
	}
	/**returns the y coordinate for math purposes*/
	public double getCenterY()
	{
		return y + sprite.getHeight(null)/2;
	}
	public static void initializeProjectiles()
	{
		for (int i = 0; i < allProjectiles.length; i++)
			allProjectiles[i] = null;
	}
	// I removed parameter p since this is an object method
	public void moveProjectile(double elapsedTime)
	{
		double oldX = getX();
		double oldY = getY();
		
		//debug code, to be removed
		//System.out.println("old x "+oldX);
		//System.out.println("a: "+a+", b: "+b);
		
		double yOfNewLocation = 0;
		double xOfNewLocation = 0;
		
		if (quadrant == 1)
		{
			xOfNewLocation = oldX + (b * elapsedTime * speed);
			yOfNewLocation = oldY - (a * elapsedTime * speed);
		}
		
		else if (quadrant == 2)
		{
			xOfNewLocation = oldX - (b * elapsedTime * speed);
			yOfNewLocation = oldY - (a * elapsedTime * speed);
		}
		
		else if (quadrant == 3)
		{
			xOfNewLocation = oldX - (b * elapsedTime * speed);
			yOfNewLocation = oldY + (a * elapsedTime * speed);
		}
		
		else if (quadrant == 4)
		{
			xOfNewLocation = oldX + (b * elapsedTime * speed);
			yOfNewLocation = oldY + (a * elapsedTime * speed);
		}
		
		//System.out.println(xOfNewLocation + ", " + yOfNewLocation);
		
		// currently useless
		//double distance = Math.sqrt(Math.pow(xOfNewLocation - oldX, 2) + Math.pow(yOfNewLocation - oldY, 2));
		
		//System.out.println(distance + " units traveled this frame.");
		
		x = xOfNewLocation;
		y = yOfNewLocation;
		
		if(owner == TOWER)
		{
			//loop through all viruses and check for collision
			for(int v = 0; v < Malware.allMalware.length; v++)
			{
				if(Malware.allMalware[v] == null)
					break;
				
				virusHit = false; // resets variable
				double xOfVirus = Malware.allMalware[v].getCenterX();
				double yOfVirus = Malware.allMalware[v].getCenterY();
				
				for (int i = 0; i < numOfVirusesHit; i++)
				{
					if (virusesHit[i] == Malware.allMalware[v])
					{
						virusHit = true;
						//System.out.println(Virus.allViruses[v] + "Virus already hit!");
					}
				}
				
				double distFromVirus  = Math.sqrt(Math.pow(xOfVirus - getCenterX(), 2) + Math.pow(yOfVirus - getCenterY(), 2));
				
				if (splashEffectActivated && distFromVirus <= Malware.allMalware[v].sprite.getHeight(null) * splashRange)
				{
					//System.out.println(Virus.allViruses[v] + "Virus hit!");
					
					uses--;
					
					Malware.allMalware[v].dealDamage((int)damage, manipulatorForVirus, idOfTower);
				}
				
				else if(distFromVirus <= Malware.allMalware[v].sprite.getHeight(null) / 2 && !virusHit)
				{
					if (splashEffect)
					{
						// activates splash effect
						splashEffectActivated = true;
						
						// resets loop
						v = 0;
					}
					else
					{	
						uses--;
						
						virusesHit[numOfVirusesHit] = Malware.allMalware[v];
						numOfVirusesHit++;
						
						//System.out.println(Virus.allViruses[v] + "Virus hit!");
						
						Malware.allMalware[v].dealDamage((int)damage, manipulatorForVirus, idOfTower);
						
						if (uses <= 0)
							this.deleteProjectile();
						break;
					}
				}
			}
		}
		else if(owner == WORM)
		{
			//loop through all towers and check for collision
			for (int t = 0; t < Tower.allTowers.length; t++)
			{
				Tower current = Tower.allTowers[t];
				if(current == null)
					break;
				
				//if it hits the tower, deal damage to it and decrement uses
				double distFromTower = Math.sqrt(Math.pow(current.getX() - getCenterX(), 2) + Math.pow(current.getY()- getCenterY(), 2));
				
				if(distFromTower < Tower.sprites[t].getWidth())
				{
					current.dealDamage((int)damage);
					deleteProjectile();
					//debug code, to be removed`
					//System.out.println("hit a tower!\t now have "+uses+" uses remaining");
				}
			}
		}
		//delete used projectiles
		if (uses <= 0)
			this.deleteProjectile();
		
		//also delete projectile if it goes off the screen
		if(x<-50 || x>1500 || y<-50 || y>800)
		{
			this.deleteProjectile();
			//System.out.println("A projectile went out of bounds");
		}
		else if (x == 0 && y == 0)
		{
			this.deleteProjectile();
			System.out.println("A projectile in the top left corner was deleted");
		}
	}
	
	public void drawProjectile(Graphics g)
	{
		g.drawImage(sprite, (int)x + GamePanel.getMapX(), (int)y + GamePanel.getMapY(), null);
	}
	
	public void deleteProjectile()
	{
		//save the dead projectile's ID and kill it off
		int deadProjectileID=id;
		
		//move all other projectiles in array
		for(int v=deadProjectileID; v<allProjectiles.length; v++)
		{
			allProjectiles[v] = allProjectiles[v+1];
			
			//break once reaches the nulls
			if(allProjectiles[v]==null)
				break;
			
			//change the projectile's id to match its new location in the array
			allProjectiles[v].id = v;
		}
	}
}

