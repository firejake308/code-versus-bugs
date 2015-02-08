/* Copyright 2014 Adel Hassan and Patrick Kenney
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
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ListIterator;
/**
 * A file that carries information across the map.
 * 
 * @author Adel Hassan and Patrick Kenney
 *
 */
public class BonusFile 
{
	public static final int EXE = 0;
	public static final int DATA = 1;
	public static final int PACKET = 2;
	public static final Point CPU = new Point((int) ((Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 84) + Game.widthOfGamePanel / 3), 
			(int) Game.gamePanel.path[1].getY());
	public static ArrayList<BonusFile> allFiles = new ArrayList<BonusFile>();
	public static ArrayList<BonusFile> recycleBin = new ArrayList<BonusFile>();
	private static boolean firstFile = true;
	
	private double x;
	private double y;
	private double speed;
	private int reward;
	private BufferedImage sprite;
	private int type;
	private boolean encrypted;
	private boolean travelingToHub = false;
	private Point destination;
	private Point origin;
	private double distance;
	private double TOTAL_DISTANCE;
	
	/**
	 * Creates a file for use with comm towers.
	 * 
	 * @param orig the point at which to spawn the file
	 * @param dest the point which the file is traveling to
	 * @param toHub whether or not the file is headed to an info hub
	 */
	public BonusFile(Point orig, Point dest, boolean toHub)
	{
		speed = Game.widthOfGamePanel  * 0.02;
		this.type = PACKET;
		sprite = MyImages.packetFile;
		
		//initialize x, y,origin, and destination
		setCenterX((int) ((Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 84) + Game.widthOfGamePanel / 3));
		setOrigin(orig.x, orig.y);
		destination = dest;
		distance = 0;
		TOTAL_DISTANCE = origin.distance(destination);
		
		ListIterator<BonusFile> iterator = allFiles.listIterator();
		iterator.add(this);
	}
	
	/**
	 * Creates a file that carries important info.
	 * 
	 * @param type DATA or EXE
	 * @param orig the starting point of the file
	 * @param dest the destination of the file
	 * @deprecated
	 */
	public BonusFile(int type, Point orig, Point dest)
	{
		//initialize instance variables
		reward = 10;
		speed = Game.widthOfGamePanel  * 0.0075;
		encrypted = false;
		
		if(type == EXE)
		{
			this.type = type;
			sprite = MyImages.exeFile;
		}
		else if(type == DATA)
		{
			this.type = type;
			sprite = MyImages.dataFile;
		}
		else if(type == PACKET)
		{
			this.type = type;
			sprite = MyImages.packetFile;
		}
		
		//initialize x, y,origin, and destination
		setCenterX((int) ((Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 84) + Game.widthOfGamePanel / 3));
		setOrigin((int) ((Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 84) + Game.widthOfGamePanel / 3), orig.y + sprite.getHeight()/2);
		destination = dest;
		distance = 0;
		TOTAL_DISTANCE = origin.distance(destination);
		
		ListIterator<BonusFile> iterator = allFiles.listIterator();
		iterator.add(this);
	}
	/**
	 * Creates a file on the "file stream" part of the map, headed toward the CPU by default.
	 * 
	 * @param y the y to start the file at
	 * @param type the type of the file (DATA or EXE)
	 */
	public BonusFile(int y, int type) 
	{
		//initialize instance variables
		reward = 10;
		speed = Game.widthOfGamePanel  * 0.0075;
		encrypted = false;
		
		if(type == EXE)
		{
			this.type = type;
			sprite = MyImages.exeFile;
		}
		else if(type == DATA)
		{
			this.type = type;
			sprite = MyImages.dataFile;
		}
		setCenterX((int) ((Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 84) + Game.widthOfGamePanel / 3));
		setY(y);
		setOrigin((int) ((Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 84) + Game.widthOfGamePanel / 3), y + sprite.getHeight()/2);
		destination = CPU;
		distance = 0;
		TOTAL_DISTANCE = y - CPU.y;
		
		ListIterator<BonusFile> iterator = allFiles.listIterator();
		iterator.add(this);
	}
	public double getX() 
	{
		return x;
	}

	public double getY() 
	{
		return y;
	}
	private void setY(double y) 
	{
		this.y = y;
	}
	private void setCenterX(int xToSet) 
	{
		x = xToSet - sprite.getWidth()/2;
	}
	private void setCenterY(int yToSet) 
	{
		y = yToSet - sprite.getHeight()/2;
	}
	public void setOrigin(int x, int y)
	{
		setCenterX(x);
		setCenterY(y);
		origin = new Point(x,y);
	}
	public int getType()
	{
		return type;
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(sprite, (int)x, (int)y, null);
	}
	
	public void move(double frames)
	{
		if(distance > 50 && Game.tutorial && firstFile)
		{
			firstFile = false;
			Game.gameState = Game.PAUSED;
			Game.gamePanel.infoPopup = new InfoPopup((int)x-200, (int)y-200);
			Game.gamePanel.infoPopup.setVisible(true);
	    	Game.gamePanel.infoPopup.setTitle("File");
	    	Game.gamePanel.infoPopup.setInfo1("Health: None");
	    	Game.gamePanel.infoPopup.setInfo2("Ability: Gives 10 bytes if it makes it to the CPU safely.");
	    	Game.gamePanel.infoPopup.setInfo3("Fact: In this game, all data files hold your credit card number.");
		}
		//keep going up until hits destination
		if(distance < TOTAL_DISTANCE)
		{
			double slope;
			double angle;
			double xDist;
			double yDist;
			
			try
			{
				slope = (double)(destination.y - origin.y) / (destination.x - origin.x);
				angle = Math.abs(Math.atan(slope));
			}
			//if going straight up or down, angle is 90
			catch(ArithmeticException e)
			{
				angle = Math.PI / 2;
			}
			yDist = Math.sin(angle);
			xDist = Math.cos(angle);
			//debug
			//System.out.println("moving "+xDist+" on x-axis and "+yDist+" on y");
			
			if(destination.x >= origin.x && destination.y >= origin.y)
			{
				setY(y+yDist*speed*frames/60);
				setX(x+xDist*speed*frames/60);
				distance += speed*frames/60;
			}
			else if(destination.x >= origin.x && destination.y <= origin.y)
			{
				setY(y-yDist*speed*frames/60);
				setX(x+xDist*speed*frames/60);
				distance += speed*frames/60;
			}
			else if(destination.x <= origin.x && destination.y >= origin.y)
			{
				setY(y+yDist*speed*frames/60);
				setX(x-xDist*speed*frames/60);
				distance += speed*frames/60;
			}
			else if(destination.x <= origin.x && destination.y <= origin.y)
			{
				setY(y-yDist*speed*frames/60);
				setX(x-xDist*speed*frames/60);
				distance += speed*frames/60;
			}
		}
		//if virus makes it to destination, then despawn virus
		else
		{
			//add lives for CPU files
			if(destination.equals(CPU))
			{
				//debug
				//System.out.println("a file made it across");
				
				Game.lives+=reward;
				Game.gf.life.setDisplay(Game.lives);
				
				addToRecycleBin();
			}
			
			// if dealing with a packet
			else if (type == 2)
			{
				if (travelingToHub)
				{
					CommunicationsTower.numOfPacketsToHub--;
					CommunicationsTower.numOfPacketsToTower++;
				}
				else
				{
					CommunicationsTower.numOfPacketsToTower--;
					
					if (CommunicationsTower.numOfPacketsToTower == 0)
					{
						int towerTypeOfConnecting = -1;
						
						CommunicationsTower.towersConnected[CommunicationsTower.totalTowersConnected] = CommunicationsTower.connectingTower;
						CommunicationsTower.totalTowersConnected++;
						CommunicationsTower.uploadingTower = false;
						
						if (CommunicationsTower.connectingTower instanceof DiscThrower)
							towerTypeOfConnecting = 0;
						else if (CommunicationsTower.connectingTower instanceof NumberGenerator)
							towerTypeOfConnecting = 1;
						else if (CommunicationsTower.connectingTower instanceof Scanner)
							towerTypeOfConnecting = 2;
						
						// update upgrades for the tower
						CommunicationsTower.updateConnectedTowers(CommunicationsTower.connectingTower);
						CommunicationsTower.upgradeConnectedTowers(CommunicationsTower.bestConnectedTowers[towerTypeOfConnecting], CommunicationsTower.connectingTower, true);
					}
					
					addToRecycleBin();
				}
			}
			//for files headed to other places
			else
			{
				addToRecycleBin();
			}
		}
	}
	
	private void setX(double x) {
		this.x = x;
	}
	public void encrypt()
	{
		encrypted = true;
		sprite = MyImages.secureFile;
	}
	
	public boolean isEncrypted()
	{
		return encrypted;
	}
	
	public void addToRecycleBin()
	{
		recycleBin.add(this);
	}
	
	public static void emptyBin()
	{
		ListIterator<BonusFile> iterator = recycleBin.listIterator();
		while(iterator.hasNext())
		{
			BonusFile curr = iterator.next();
			allFiles.remove(curr);
			iterator.remove();
		}
		
		//allow viruses to replicate again
		for(Malware m : Malware.allMalware)
		{
			if(m instanceof Virus && !((Virus) m).canReplicate)
			{
				((Virus)m).canReplicate = true;
				
				//debug
				//System.out.println("virus can replicate again.");
			}
			else if(m == null)
				break;
		}
	}
}
