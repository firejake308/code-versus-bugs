package clientfiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import clientfiles.Malware.State;

/**
 * This will control and define the towers in the game
 * 
 * @Author Patrick Kenney
 * 9/30/14
 * 
 * UPDATE LOG:
 * 
 * 11/25/2014 - 1:48PM - Patrick Kenney
 * 		tower deleting now available
 * 11/26/2014 - Patrick Kenney
 * 		1st: new targeting system implemented (distance based)
 * 		2nd: began working on upgrade system
 * 		3rd: buttons are now in arrays to make methods more reliable
 * 11/27/14:
 * 		moved upgrades to own class
 * 		changed sprites[]to static
 * 11/29/14:
 * 		gave towers health
 * 		infected towers now have broken virus finders
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
public abstract class Tower implements ActionListener, Serializable
{	
	private static final long serialVersionUID = 1L;
	public static Tower[] allTowers = new Tower[100];
	protected static JButton[] sprites = new JButton[100];
	protected  int[] costsOfUpgrades = new int[27];
	public ImageIcon icon;
	private static Random generator = new Random();
	public boolean backedUp = false;
	
	protected int id;
	protected int x;
	protected int y;
	protected int diameterOfTower;
	protected double range;  //upgrade-able
	protected double rangeOfSplash;
	public int timer = 0;  
	public int timerReset;   //upgrade-able
	protected TowerType type;
	protected double damage;
	protected int level = 1;
	protected int health;
	protected int maxHealth;
	private boolean infected = false;
	public boolean isConnected = false;
	private boolean spawnedWorm = false;
	private static int healthToSet = 50;
	
	protected Ellipse2D rangeIndicator;
	protected Arc2D scan;
	protected BufferedImage healthBar;
	public boolean rangeOn = true;
	protected double angleOfArrow;
	protected double scanDegree;
	
	//upgrades
	protected int projectileDurability;
	protected boolean splashEffect;
	protected boolean lethalRandoms = false;
	protected boolean buffed;
	
	protected int cost = 0;
	protected int realValue = 0;
	
	// for displaying upgrades per tower in upgrade panel
	protected int upgradesInPath1 = 1; 
	protected int upgradesInPath2 = 1;
	protected int upgradesInPath3 = 1;
	
	// counts kills, shots fired, hits, and accuracy respectively
	protected int kills;
	protected double shotsFired;
	protected double hits;
	protected double accuracy;
	
	public Tower(ImageIcon icon, int idToSet)
	{
		kills = 0;
		shotsFired = 0;
		hits = 0;
		
		accuracy = 1;
		maxHealth = healthToSet;
		health = maxHealth;
		buffed = false;
		
		id = idToSet;
		
		System.out.println("The id is: " + id);
		
		this.icon = icon;
		
		sprites[id] = new JButton(icon);
		sprites[id].setBounds(getX(), getY(), (int) (Game.xScale*icon.getIconWidth()), (int) (Game.yScale*icon.getIconHeight()));
		sprites[id].addActionListener(this);
		
		rangeIndicator = new Ellipse2D.Double(getCenterX()-range, getCenterY()-range, range*2, range*2);
		
		Game.gamePanel.addToLayeredPane(sprites[id], 0);
	}
	public int getRadius()
	{
		return diameterOfTower / 2;
	}
	
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public double getRange()
	{
		return range;
	}
	public void setX(int xToSet)
	{
		x=xToSet;
		if(this instanceof Scanner)
			scan = new Arc2D.Double(x, y, range, range, 0, ((Scanner)this).getArcAngle(), Arc2D.PIE);
	}
	public void setY(int yToSet)
	{
		y=yToSet;
		if(this instanceof Scanner)
			scan = new Arc2D.Double(x, y, range, range, 0, ((Scanner)this).getArcAngle(), Arc2D.PIE);
	}
	
	public void setIcon(ImageIcon i)
	{
		sprites[id].setIcon(i);
		icon = i;
	}
	
	/**returns the x of the center of the tower
	 * 
	 * @return
	 */
	public int getCenterX()
	{
		return (int) (x + Game.xScale * icon.getIconWidth() / 2);
	}
	/**returns the y of the center of the tower
	 * 
	 * @return
	 */
	public int getCenterY()
	{
		return y + (int) (Game.yScale * icon.getIconHeight() / 2);
	}
	/**sets the x-coordinate of the center of the tower
	 * 
	 * @param xToSet
	 */
	public void setCenterX(int xToSet)
	{
		x = xToSet - (int) (Game.xScale * icon.getIconWidth() / 2);
	}
	/**sets the y-coordinate of the center of the tower
	 * 
	 * @param yToSet
	 */
	public void setCenterY(int yToSet)
	{
		y = yToSet - (int) (Game.yScale * icon.getIconHeight() / 2);
	}
	/**
	 * Returns the location of the tower on the game panel. Use this for drawing.
	 * 
	 * @deprecated
	 * @return
	 */
	public int getScreenX()
	{
		return x;
	}
	/**
	 * Returns the location of the tower on the game panel. Use this for drawing.
	 * 
	 * @deprecated
	 * @return
	 */
	public int getScreenY()
	{
		return y;
	}
	public TowerType getType()
	{
		return type;
	}
	public static void increaseHealth(int increase)
	{
		healthToSet += increase;
	}
	public static void resetHealth()
	{
		healthToSet = 0;
	}
	public static int getHealthToSet() {
		return healthToSet;
	}
	public static void setHealthToSet(int healthToSet) {
		Tower.healthToSet = healthToSet;
	}
	public void dealDamage(int damage)
	{
		health -= damage;
		//change image for disc throwers
		if(this instanceof DiscThrower)
		{
			if(health < 0)
			{
				//or, just infect it
				infected = true;
				health = 100;
				setIcon(new ImageIcon(MyImages.dt5));
			}
			else if(health <= maxHealth/5)
			{
				setIcon(new ImageIcon(MyImages.dt4));
			}
			else if(health <= 2*maxHealth/5)
			{
				setIcon(new ImageIcon(MyImages.dt3));
			}
			else if(health <= 3*maxHealth/5)
			{
				setIcon(new ImageIcon(MyImages.dt2));
			}
			else if(health <= 4*maxHealth/5)
			{
				setIcon(new ImageIcon(MyImages.dt1));
			}
		}
		else if(this instanceof Scanner)
		{
			if(health < 0)
			{
				//kill a worm-ed tower
				//Upgrades.displayedUpgradeID = id;
				//Upgrades.deleteTower();
				
				//or, just infect it
				infected = true;
				health = 100;
				setIcon(new ImageIcon(MyImages.scanner5));
			}
			else if(health <= maxHealth / 5)
			{
				setIcon(new ImageIcon(MyImages.scanner4));
			}
			else if(health <= 2 * maxHealth / 5)
			{
				setIcon(new ImageIcon(MyImages.scanner3));
			}
			else if(health <= 3 * maxHealth / 5)
			{
				setIcon(new ImageIcon(MyImages.scanner2));
			}
			else if(health <= 4 * maxHealth / 5)
			{
				System.out.println("DAMAGED");
				setIcon(new ImageIcon(MyImages.scanner1));
			}
		}
		else if(this instanceof Encrypter)
		{
			if(health < 0)
			{
				//or, just infect it
				infected = true;
				health = 100;
				setIcon(new ImageIcon(MyImages.encrypter5));
			}
			else if(health <= maxHealth / 5)
			{
				setIcon(new ImageIcon(MyImages.encrypter4));
			}
			else if(health <= 2 * maxHealth / 5)
			{
				setIcon(new ImageIcon(MyImages.encrypter3));
			}
			else if(health <= 3 * maxHealth / 5)
			{
				setIcon(new ImageIcon(MyImages.encrypter2));
			}
			else if(health <= 4 * maxHealth / 5)
			{
				setIcon(new ImageIcon(MyImages.encrypter1));
			}
		}
		else if(this instanceof NumberGenerator)
		{
			if(health < 0)
			{
				infected = true;
				health = 100;
				healthBar = MyImages.healthBar5;
			}
			else if(health <= maxHealth / 5)
			{
				healthBar = MyImages.healthBar4;
			}
			else if(health <= 2 * maxHealth / 5)
			{
				healthBar = MyImages.healthBar3;
			}
			else if(health <= 3 * maxHealth / 5)
			{
				healthBar = MyImages.healthBar2;
			}
			else if(health <= 4 * maxHealth / 5)
			{
				healthBar = MyImages.healthBar1;
			}
		}
		//for non-imaged towers, just infect
		else if(health < 0)
		{
			infected = true;
			health = 100;
			System.out.println(getClass().getName()+" doesn't have an image.");
			
			if(health < 0)
			{
				healthBar = MyImages.healthBar0;
			}
		}
	}
	public boolean isInfected()
	{
		return infected;
	}
	/**
	 * Creates a projectile to attack opponents
	 * 
	 * @param target the malware to direct the cd at
	 * @param toerType the type of the attacking tower
	 */
	public void attack(Malware target, TowerType towerType)
	{
		if (towerType == TowerType.DISC_THROWER || towerType == TowerType.NUMBER_GENERATOR || towerType == TowerType.ANTIVIRUS_SOFTWARE)
		{
			double xOfTower = getCenterX();
			double yOfTower = getCenterY();
			double xOfVirus = target.getCenterX();
			double yOfVirus = target.getCenterY();
			
			double slope = (yOfVirus - yOfTower) / (xOfVirus - xOfTower);
			//absolute value of arctan, so angle is always positive
			double angle = Math.abs(Math.atan(slope));
			
			double a = Math.sin(angle);
			
			double b = Math.cos(angle);
			
			int quadrant = 0;
			
			
			if (xOfVirus >= xOfTower && yOfVirus <= yOfTower)
			{
				quadrant = 1;
				angleOfArrow=Math.PI-angle;
			}
			else if (xOfVirus <= xOfTower && yOfVirus <= yOfTower)
			{
				quadrant = 2;
				angleOfArrow = angle;
			}
			else if (xOfVirus <= xOfTower && yOfVirus >= yOfTower)
			{
				quadrant = 3;
				angleOfArrow = -angle;
			}
			else if (xOfVirus >= xOfTower && yOfVirus >= yOfTower)
			{
				quadrant = 4;
				angleOfArrow = Math.PI + angle;
			}
			
			//add a new projectile to list 
			Projectile.allProjectiles.add(new Projectile(a, b, quadrant, xOfTower, yOfTower, towerType, id, damage));
			
			// increase shots fired of tower
			shotsFired++;
			
			// re-draw tower panel with updated statistics
			if (id == Upgrades.displayedUpgradeID)
				Upgrades.updateStatistics();
			//return statement seems kind of unnecessary
			return;
		}
	}
	
	public static Malware findTarget(Tower target)
	{
		//if infected, only 2% chance of looking for viruses
		if(target.infected)
		{
			int r = generator.nextInt(50);
			if(r != 0)
			{
				return null;
			}
		}
		
		double xOfVirus = 0;
		double yOfVirus = 0;
		int xOfTower = target.getCenterX();
		int yOfTower = target.getCenterY();
		int i = 0;
		double range = target.getRange();
		double distanceFromTower = 1000;
		double virusesDistance = 0;
		boolean specialEffects = false;
		boolean targetFound = false;
		boolean validTower = true;
		boolean commTowerSelected = false;
		Malware virusToAttack = null;
		
		if (target.type == TowerType.NUMBER_GENERATOR)
			specialEffects = true;
		
		if (specialEffects)
		{
			while (i < Malware.numMalwares)
			{
				//necessary to prevent null pointer exceptions
				if(Malware.allMalware[i] == null)
					break;
				
				xOfVirus = Malware.allMalware[i].getCenterX();
				yOfVirus = Malware.allMalware[i].getCenterY();
				
				distanceFromTower = Math.sqrt(Math.pow(xOfVirus - xOfTower, 2) + Math.pow(yOfVirus - yOfTower, 2));
				
				if (Malware.allMalware[i].getRelativeDistance() >= virusesDistance && Malware.allMalware[i].state != State.FROZEN && Malware.allMalware[i].state != State.INVISIBLE && distanceFromTower <= range && yOfVirus >= 0)
				{
					virusToAttack = Malware.allMalware[i];
					virusesDistance = virusToAttack.getRelativeDistance();
					targetFound = true;
				}
				
				i++;
			}
		}
		
		i = 0;
		
		if (!targetFound)
		{
			while (i < Malware.numMalwares)
			{
				//necessary to prevent null pointer exceptions
				if(Malware.allMalware[i] == null)
					break;
				
				xOfVirus = Malware.allMalware[i].getCenterX();
				yOfVirus = Malware.allMalware[i].getCenterY();
				
				distanceFromTower = Math.sqrt(Math.pow(xOfVirus - xOfTower, 2) + Math.pow(yOfVirus - yOfTower, 2));
				
				if (Malware.allMalware[i].state != State.INVISIBLE  && Malware.allMalware[i].getRelativeDistance() >= virusesDistance && distanceFromTower <= range && yOfVirus >= 0)
				{
					virusToAttack = Malware.allMalware[i];
					virusesDistance = virusToAttack.getRelativeDistance();
					targetFound = true;
				}
				
				i++;
			}
		}
		
		i = 0;
		
		if (target.type == TowerType.COMMUNICATIONS_TOWER || target.type == TowerType.FIREWALL || target.type == TowerType.ENCRYPTER)
			validTower = false;
		
		// check range of comm towers
		if (specialEffects)
		{
			for (int t = 0; t < GamePanel.numTowers; t++)
			{
				if (Tower.allTowers[t] == null)
					break;
				if (Tower.allTowers[t].type == TowerType.COMMUNICATIONS_TOWER)
					commTowerSelected = true;
				
				while (i < Malware.numMalwares && commTowerSelected && validTower)
				{
					//necessary to prevent null pointer exceptions
					if(Malware.allMalware[i] == null)
						break;
					
					xOfVirus = Malware.allMalware[i].getCenterX();
					yOfVirus = Malware.allMalware[i].getCenterY();
					xOfTower = Tower.allTowers[t].getCenterX();
					yOfTower = Tower.allTowers[t].getCenterY();
					
					distanceFromTower = Math.sqrt(Math.pow(xOfVirus - xOfTower, 2) + Math.pow(yOfVirus - yOfTower, 2));
					
					if (Malware.allMalware[i].getRelativeDistance() >= virusesDistance && Malware.allMalware[i].state != State.FROZEN 
							&& Malware.allMalware[i].state != State.INVISIBLE && distanceFromTower <= Tower.allTowers[t].range && yOfVirus >= 0 
							&& ((CommunicationsTower)Tower.allTowers[t]).shareRange)
					{
						virusToAttack = Malware.allMalware[i];
						virusesDistance = virusToAttack.getRelativeDistance();
						targetFound = true;
					}
					
					i++;
				}
			}
		}
		
		i = 0;
		
		// check range of comm towers
		for (int t = 0; t < GamePanel.numTowers; t++)
		{
			if (Tower.allTowers[t] == null)
				break;
			if (Tower.allTowers[t].type == TowerType.COMMUNICATIONS_TOWER)
				commTowerSelected = true;
			
			while (i < Malware.numMalwares && commTowerSelected && validTower)
			{
				//necessary to prevent null pointer exceptions
				if(Malware.allMalware[i] == null)
					break;
				
				xOfVirus = Malware.allMalware[i].getCenterX();
				yOfVirus = Malware.allMalware[i].getCenterY();
				xOfTower = Tower.allTowers[t].getCenterX();
				yOfTower = Tower.allTowers[t].getCenterY();
				
				distanceFromTower = Math.sqrt(Math.pow(xOfVirus - xOfTower, 2) + Math.pow(yOfVirus - yOfTower, 2));
				
				if (Malware.allMalware[i].getRelativeDistance() >= virusesDistance && Malware.allMalware[i].state != State.INVISIBLE && distanceFromTower <= Tower.allTowers[t].range && yOfVirus >= 0 && ((CommunicationsTower)Tower.allTowers[t]).shareRange)
				{
					virusToAttack = Malware.allMalware[i];
					virusesDistance = virusToAttack.getRelativeDistance();
					targetFound = true;
				}
				
				i++;
			}
		}
		
		if (targetFound)
		{
			return virusToAttack;
		}
		else
		{
			return null;
		}
	}
	
	public void drawTower(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		
		//draw range indicator if necessary
		if(rangeOn)
		{
			g2d.setColor(new Color(0, 0, 0, 50));
			g2d.fill(rangeIndicator);
		}
		
		//reset image
		AffineTransform at = new AffineTransform();
		at.scale(Game.xScale, Game.yScale);
		at.translate(getX()/Game.xScale, getY()/Game.yScale);
		g2d.drawImage(icon.getImage(), at, null);
		sprites[id].setIcon(icon);
		sprites[id].setBounds(getX(), getY(), (int) (Game.xScale*icon.getIconWidth()), (int) (Game.yScale*icon.getIconHeight()));
		
		//rotate arrow for disc throwers
		if(this instanceof DiscThrower)
		{
			AffineTransform op = new AffineTransform();
			op.translate(getCenterX()-MyImages.redArrow.getWidth()/2,
						 getCenterY()-MyImages.redArrow.getHeight()/2);
			op.rotate(angleOfArrow, getCenterX() - getX(), getCenterY() - getY());
			op.translate(Math.cos(angleOfArrow), Math.sin(angleOfArrow));
			g2d.drawImage(MyImages.redArrow, op, null);
		}
		//rotate scanner for scanners
		else if(this instanceof Scanner)
		{
			scan.setAngleStart(scanDegree);
			scan.setFrame(getCenterX()-range, getCenterY()-range, range*2, range*2);
			g2d.setColor(new Color(131, 252, 201, 150));
			g2d.fill(scan);
		}
		//set background transparent for communications tower
		else if(this instanceof CommunicationsTower)
		{
			sprites[id].setBackground(new Color(0,0,0,0));
		}
		//draw health bar for num gens and avs's
		else if(this instanceof NumberGenerator || this instanceof AntiVirusSoftware)
		{
			AffineTransform op = new AffineTransform();
			op.scale(Game.xScale, Game.yScale);
			op.translate(getX()/Game.xScale, (getY() + sprites[id].getHeight())/Game.yScale);
			g2d.drawImage(healthBar, op, null);
		}
	}
	
	public abstract void addUpgradeOptions(int idOfTower);
	public abstract void displayUpgradeInfo(int upgradePath);
	
	public int getCostOfUpgrade(int path)
	{
		switch (path)
		{
			case 1:				return costsOfUpgrades[upgradesInPath1 - 1];
			case 2:				return costsOfUpgrades[upgradesInPath2 + 8];
			case 3:				return costsOfUpgrades[upgradesInPath3 + 17];
			default:			return 0;
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton temp = (JButton) e.getSource();
		
		for (int i = 0; i < sprites.length; i++)
		{
			if (temp == sprites[i])
			{
				if (Upgrades.displayedUpgradeID != i)
				{
					if (Upgrades.upgradesActive)
					{
						//hide upgrade panel and range indicator
						Tower.allTowers[Upgrades.displayedUpgradeID].rangeOn = false;
						Upgrades.removeUpgradePanel();
					}
					//show panel and range indicator
					Upgrades.showUpgradePanel(i);
					rangeOn = true;
					rangeIndicator = new Ellipse2D.Double(getCenterX()-range, getCenterY()-range, range*2, range*2);
					
					//special case for tutorial slide 9
					//user was asked to select a tower to bring up upgrade panel
					if(Game.tutorialSlide == 9)
						Game.gamePanel.nextSlide();
				}
				else if (Upgrades.displayedUpgradeID == i)
				{
					//hide upgrade panel and range indicator
					Upgrades.removeUpgradePanel();
					rangeOn = false;
				}
			}
		}
	}
	
	public boolean hasSpawnedWorm()
	{
		return spawnedWorm;
	}
	
	public void setSpawnedWorm()
	{
		spawnedWorm = true;
	}
	public void setInfected(boolean b) 
	{
		infected = b;
	}
}
