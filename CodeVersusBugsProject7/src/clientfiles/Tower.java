package clientfiles;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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
 */
public abstract class Tower implements ActionListener
{	
	public static Tower[] allTowers = new Tower[100];
	protected static JButton[] sprites = new JButton[100];
	protected  int[] costsOfUpgrades = new int[27];
	private static Random generator = new Random();
	
	protected int id;
	protected int x;
	protected int y;
	protected int diameterOfTower;
	protected double range;  //upgrade-able
	protected double rangeOfSplash;
	public int timer = 0;  
	public int timerReset;   //upgrade-able
	//private int attackSpeed; // work on this later
	protected TowerType type;
	protected double damage;
	protected int level = 1;
	protected int health = 50;
	private boolean infected = false;
	
	//upgrades
	protected int projectileDurability;
	protected boolean splashEffect; //TODO add animation
	protected boolean lethalRandoms = false;
	
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
	
	/** Upgrades Ideas:
	 * Individual vs Universal  				<---let's start with individual 
	 * Attack       		<---i think we should do attack 1st
	 * bottom panel vs shop panel				<---???you choose here
	 * panel(must be individual) vs tree(must be universal)	<---we don't actually have to have different visuals, tree == paused like state?
	 * 											here, let's just start
	 * 											ok so lets get the pause menu
	 * stats						<---start w/ stats. access the tree?
	 * ok, so stats on tree and individual upgrades on panel
	 * abilities/special upgrades vs stats
	 */
	
	public Tower(ImageIcon icon, int idToSet)
	{
		kills = 0;
		shotsFired = 0;
		hits = 0;
		
		accuracy = 1;
		
		id = idToSet;
		
		System.out.println("The id is: " + id);
		
		sprites[id] = new JButton(icon);
		sprites[id].setBounds(getX(), getY(), 50, 50);
		sprites[id].addActionListener(this);
		
		Game.gamePanel.add(sprites[id]);
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
	}
	public void setY(int yToSet)
	{
		y=yToSet;
	}
	
	/**returns the x of the center of the tower
	 * 
	 * @return
	 */
	public int getCenterX()
	{
		return x + sprites[id].getWidth() / 2;
	}
	/**returns the y of the center of the tower
	 * 
	 * @return
	 */
	public int getCenterY()
	{
		return y + sprites[id].getHeight() / 2;
	}
	/**sets the x-coordinate of the center of the tower
	 * 
	 * @param xToSet
	 */
	public void setCenterX(int xToSet)
	{
		x=xToSet-25;
	}
	/**sets the y-coordinate of the center of the tower
	 * 
	 * @param yToSet
	 */
	public void setCenterY(int yToSet)
	{
		y=yToSet-25;
	}
	/**Returns the location of the tower on the game panel. Use this for drawing.
	 * 
	 * @return
	 */
	public int getScreenX()
	{
		return x + GamePanel.getMapX();
	}
	/**Returns the location of the tower on the game panel. Use this for drawing.
	 * 
	 * @return
	 */
	public int getScreenY()
	{
		return y + GamePanel.getMapY();
	}
	public TowerType getType()
	{
		return type;
	}
	
	public void dealDamage(int damage)
	{
		health -= damage;
		if(health<=0)
		{
			//kill a worm-ed tower
			//Upgrades.displayedUpgradeID = id;
			//Upgrades.deleteTower();
			
			//or, just infect it
			infected = true;
			health = 100;
		}
	}
	public boolean isInfected()
	{
		return infected;
	}
	/**
	 * creates a projectile to attack opponents
	 */
	public void attack(Malware target, TowerType towerType)
	{
		if (towerType == TowerType.DISC_THROWER || towerType == TowerType.NUMBER_GENERATOR)
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
				quadrant = 1;
			else if (xOfVirus <= xOfTower && yOfVirus <= yOfTower)
				quadrant = 2;
			else if (xOfVirus <= xOfTower && yOfVirus >= yOfTower)
				quadrant = 3;
			else if (xOfVirus >= xOfTower && yOfVirus >= yOfTower)
				quadrant = 4;
			
			//go through allProjectiles until you hit a null
			//and create a projectile at that location in the array
			for (int i = 0; i < Projectile.allProjectiles.length; i++)
			{
				if (Projectile.allProjectiles[i] == null)
				{
					Projectile.allProjectiles[i] = new Projectile(a, b, quadrant, xOfTower, yOfTower, towerType, i, id, damage);
					
					// increase shots fired of tower
					shotsFired++;
					
					// re-draw tower panel with updated statistics
					if (id == Upgrades.displayedUpgradeID)
						Upgrades.updateStatistics();
					break;
				}
			}
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
		Malware virusToAttack = null;
		
		if (target.type == TowerType.NUMBER_GENERATOR)
			specialEffects = true;
		
		if (specialEffects)
		{
			while (i < Game.gamePanel.lvlManager.getMalwaresThisLevel())
			{
				//necessary to prevent null pointer exceptions
				if(Malware.allMalware[i] == null)
					break;
				
				xOfVirus = Malware.allMalware[i].getCenterX();
				yOfVirus = Malware.allMalware[i].getCenterY();
				
				distanceFromTower = Math.sqrt(Math.pow(xOfVirus - xOfTower, 2) + Math.pow(yOfVirus - yOfTower, 2));
				
				if (Malware.allMalware[i].getRelativeDistance() >= virusesDistance && Malware.allMalware[i].state != State.FROZEN && distanceFromTower <= range && yOfVirus >= 0)
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
			while (i < Game.gamePanel.lvlManager.getMalwaresThisLevel())
			{
				//necessary to prevent null pointer exceptions
				if(Malware.allMalware[i] == null)
					break;
				
				xOfVirus = Malware.allMalware[i].getCenterX();
				yOfVirus = Malware.allMalware[i].getCenterY();
				
				distanceFromTower = Math.sqrt(Math.pow(xOfVirus - xOfTower, 2) + Math.pow(yOfVirus - yOfTower, 2));
				//System.out.println(distanceFromTower + " " + Virus.allViruses[i]);
				
				if (Malware.allMalware[i].getRelativeDistance() >= virusesDistance && distanceFromTower <= range && yOfVirus >= 0)
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
		g.setColor(Color.BLACK);
		g.drawOval((int)(getCenterX()-range)+GamePanel.getMapX(), (int)(getCenterY()-range)+GamePanel.getMapY(), (int)range*2,(int) range*2);
		sprites[id].setBounds(getScreenX(), getScreenY(), sprites[id].getWidth(), sprites[id].getHeight());
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
					Upgrades.showUpgradePanel(i);
				}
				else if (Upgrades.displayedUpgradeID == i)
					Upgrades.removeUpgradePanel();
			}
		}
	}
}
