/**StoryManager.java
 * This class creates a StoryManager object, which generates a group of Virus objects
 * according to a pre-defined order.
 * 
 * UPDATE LOG
 * 10/13/2014 - class created
 * 11/2/14:	
 * 		now creates an army of 10 marching malwares upon constructor call
 * 11/12/14:
 * 		now implements LevelManager
 * 		now creates 5 malwares on each of 2 lanes
 * 11/15/14:
 * 		now creates 3 malwares on each of 5 lanes
 * 		added level 2, which begins immediately after all level 1 malwares die
 * 11/24/14:
 * 		now creates malwares more efficiently and indexed properly for targeting
 * 
 * TODO
 * write AI to initialize rounds with a seed value for harder or lower difficulties?
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

import java.util.Random;

import javax.swing.ImageIcon;

public class StoryManager implements LevelManager
{
	public int malwaresThisLevel = 15;
	private int filesThisLevel = 0;
	
	private int minions = 15;
	private int rushMinions = 0;
	private int tankMinions = 0;
	private int worms = 0;
	private int trojans = 0;
	private int viruses = 0;
	private int rushViruses = 0;
	private int tankViruses = 0;
	private int spywares = 0;
	private int bots;
	
	/**
	 * Sets number of malwares per type that are in the level.
	 */
	public void setMalwaresForLevel(int numOfMinions, int numOfFastMinions, int numOfSlowMinions, int numOfWorms, int numOfTrojans, int numOfViruses, int numOfVirusesF, int numOfVirusesT, int numOfSpyware, int numOfBots)
	{
		minions = numOfMinions;
		rushMinions = numOfFastMinions;
		tankMinions = numOfSlowMinions;
		worms = numOfWorms;
		trojans = numOfTrojans;
		viruses = numOfViruses;
		rushViruses = numOfVirusesF;
		tankViruses = numOfVirusesT;
		
		spywares = numOfSpyware;
		bots = numOfBots;
	}
	
	@Override
	public void setFilesForLevel(int numFiles) 
	{
		filesThisLevel = numFiles;
	}
	/**
	 * @deprecated
	 */
	public int getMalwaresThisLevel()
	{
		return malwaresThisLevel;
	}
	
	/**
	 * Creates the malwares for story mode levels.
	 * 
	 */
	public void addMalwares()
	{
		// v is the counter for malwares input
		int v = 0;
		int startY = -75;
		
		// i is the number of minions per route, as minions to add is / 5 (5 = routes)
		for (int i = 0; i < minions / 5; i++)
		{
			// l == lane
			for(int l = 1; l < 6; l++)
			{
				Malware.allMalware[v] = new Minion(Minion.NORMAL, l, startY);
				v++;
			}
			startY -= 70;
		}
		
		// i is the number of tank minions per route, as tankMinions is / 5 (5 = routes)
		for (int i = 0; i < tankMinions / 5; i++)
		{
			// l == lane
			for(int l = 1; l < 6; l++)
			{
				Malware.allMalware[v] = new Minion(Minion.TANK, l, startY);
				v++;
			}
			startY -= 70;
		}
		
		// i is the number of rush minions per route, as rushMinions is / 5 (5 = routes)
		for (int i = 0; i < rushMinions / 5; i++)
		{
			// l == lane
			for(int l = 1; l < 6; l++)
			{
				Malware.allMalware[v] = new Minion(Minion.RUSH, l, startY);
				v++;
			}
			startY -= 10;
		}
		
		// i is the number of worms per route, as worms is / 5 (5 = routes)
		for (int i = 0; i < worms / 5; i++)
		{
			// l == lane
			for(int l = 1; l < 6; l++)
			{
				Malware.allMalware[v] = new Worm(l, startY);
				v++;
			}
			startY -= 70;
		}
		
		//make viruses, where i is number of viruses per route
		for(int i = 0; i < viruses / 5; i++)
		{
			for(int l = 1; l <= 5; l++)
			{
				Malware.allMalware[v] = new Virus(Malware.NORMAL, l, startY);
				v++;
			}
			startY -= 70;
		}
		
		//make bots, where i is number of bots per route
		for(int i = 0; i < bots / 5; i++)
		{
			for(int l = 1; l <= 5; l++)
			{
				Malware.allMalware[v] = new Bot(l, startY);
				v++;
			}
			startY -= 70;
		}
		
		//make viruses, where i is number of viruses per route
		for(int i = 0; i < tankViruses / 5; i++)
		{
			for(int l = 1; l <= 5; l++)
			{
				Malware.allMalware[v] = new Virus(Malware.TANK, l, startY);
				v++;
			}
			startY -= 70;
		}
				
		//make viruses, where i is number of viruses per route
		for(int i = 0; i < rushViruses / 5; i++)
		{
			for(int l = 1; l <= 5; l++)
			{
				Malware.allMalware[v] = new Virus(Malware.RUSH, l, startY);
				v++;
			}
			startY -= 70;
		}
		
		//make spywares, where i is number of spywares per route
		for(int i = 0; i < spywares / 5; i++)
		{
			for(int l = 1; l <= 5; l++)
			{
				Malware.allMalware[v] = new Spyware(l, startY);
				v++;
			}
			startY -= 70;
		}
		
		//make files and Trojans if necessary
		startY = Game.heightOfGamePanel + 10;
		int filesSoFar = 0;
		int trojansSoFar = 0;
		while (filesSoFar + trojansSoFar < filesThisLevel + trojans)
		{
			Random gen = new Random();
			int random = gen.nextInt(4);
			
			//50% probability of making a file
			if(filesSoFar < filesThisLevel)
			{
				if(random < 2)
				{
					if(random == 0)
						BonusFile.allFiles.add(new BonusFile(startY, BonusFile.DATA));
					else if(random == 1)
						BonusFile.allFiles.add(new BonusFile(startY, BonusFile.EXE));
					startY += 100;
					filesSoFar++;
				}
			}
			//50% probability of making a Trojan
			else if(trojansSoFar < trojans)
			{
				Malware.allMalware[v] = new Trojan(Malware.NORMAL, startY);
				startY += 100;
				v++;
				trojansSoFar++;
			}
			
		}
	}
	
	/**
	 * Takes the user to the next level.
	 * 
	 */
	public void nextlvl()
	{	
		// reset kills available per round for FireWalls
		for (int t = 0; t < GamePanel.numTowers; t++)
		{
			if (Tower.allTowers[t] == null)
				break;
			if (Tower.allTowers[t] instanceof FireWall)
			{
				((FireWall) Tower.allTowers[t]).killsLeft = ((FireWall) Tower.allTowers[t]).killsPerRound;
				Tower.allTowers[t].icon = new ImageIcon(MyImages.firewall);
			}
		}
		//delete all projectiles
		Projectile.clearProjectiles();
		
		switch(Game.level)
		{
			case 1:	//TODO reset
				setMalwaresForLevel(10, 0, 0, 0, 0, 0, 0, 0, 0, 0);
				setFilesForLevel(0);
				addMalwares();
				break;
			case 2:
				setMalwaresForLevel(15, 0, 0, 0, 0, 0, 0, 0, 0, 0);
				setFilesForLevel(0);
				addMalwares();
				break;
			case 3:	
				setMalwaresForLevel(25, 0, 0, 0, 0, 0, 0, 0, 0, 0);
				setFilesForLevel(0);
				addMalwares();
				break;
			case 4:	
				setMalwaresForLevel(25, 0, 0, 0, 0, 0, 0, 0, 0, 0);
				setFilesForLevel(0);
				addMalwares();
				break;
			case 5:	
				setMalwaresForLevel(30, 0, 0, 0, 0, 0, 0, 0, 0, 0);
				setFilesForLevel(0);
				addMalwares();
				break;
			case 6:
				setMalwaresForLevel(40, 0, 0, 0, 0, 0, 0, 0, 0, 0);
				setFilesForLevel(0);
				addMalwares();
				break;
			case 7:
				setMalwaresForLevel(50, 0, 0, 0, 0, 0, 0, 0, 0, 0);
				setFilesForLevel(0);
				addMalwares();
				break;
			case 8:
				setMalwaresForLevel(60, 0, 0, 0, 0, 0, 0, 0, 0, 0);
				setFilesForLevel(0);
				addMalwares();
				break;
			case 9:
				setMalwaresForLevel(75, 0, 0, 0, 0, 0, 0, 0, 0, 0);
				setFilesForLevel(0);
				addMalwares();
				break;
			case 10:
				setMalwaresForLevel(75, 0, 0, 0, 0, 0, 0, 0, 0, 0);
				setFilesForLevel(5);
				addMalwares();
				break;
			case 11:
				setMalwaresForLevel(80, 0, 0, 0, 0, 0, 0, 0, 0, 0);
				setFilesForLevel(5);
				addMalwares();
				break;
			case 12:
				setMalwaresForLevel(0, 10, 10, 0, 0, 0, 0, 0, 0, 0);
				setFilesForLevel(5);
				addMalwares();
				break;
			case 13:
				setMalwaresForLevel(50, 25, 0, 0, 0, 0, 0, 0, 0, 0);
				setFilesForLevel(5);
				addMalwares();
				break;
			case 14:
				setMalwaresForLevel(50, 0, 25, 0, 0, 0, 0, 0, 0, 0);
				setFilesForLevel(5);
				addMalwares();
				break;
			case 15:
				setMalwaresForLevel(50, 40, 40, 0, 0, 0, 0, 0, 0, 0);
				setFilesForLevel(6);
				addMalwares();
				break;
			case 16:
				setMalwaresForLevel(75, 50, 50, 0, 0, 0, 0, 0, 0, 0);
				setFilesForLevel(6);
				addMalwares();
				break;
			case 17:
				setMalwaresForLevel(100, 60, 60, 0, 0, 0, 0, 0, 0, 0);
				setFilesForLevel(7);
				addMalwares();
				break;
			case 18:
				setMalwaresForLevel(100, 100, 50, 0, 0, 0, 0, 0, 0, 0);
				setFilesForLevel(8);
				addMalwares();
				break;
			case 19:
				setMalwaresForLevel(100, 50, 100, 0, 0, 0, 0, 0, 0, 0);
				setFilesForLevel(9);
				addMalwares();
				break;
			case 20:
				setMalwaresForLevel(0, 0, 0, 0, 0, 25, 0, 0, 0, 0);
				setFilesForLevel(10);
				addMalwares();
				break;
			case 21:
				setMalwaresForLevel(25, 25, 25, 0, 0, 25, 25, 25, 0, 0);
				setFilesForLevel(8);
				addMalwares();
				break;
			case 22:
				setMalwaresForLevel(0, 50, 0, 0, 0, 50, 50, 50, 0, 0);
				setFilesForLevel(8);
				addMalwares();
				break;
			case 23:
				setMalwaresForLevel(0, 50, 50, 0, 0, 0, 75, 75, 0, 0);
				setFilesForLevel(8);
				addMalwares();
				break;
			case 24:
				setMalwaresForLevel(0, 100, 0, 0, 0, 75, 0, 50, 0, 0);
				setFilesForLevel(8);
				addMalwares();
			case 25:
				setMalwaresForLevel(0, 0, 0, 0, 5, 0, 0, 0, 5, 0);
				setFilesForLevel(8);
				addMalwares();
				break;
			case 26:
				setMalwaresForLevel(0, 25, 25, 0, 5, 0, 50, 25, 10, 0);
				setFilesForLevel(8);
				addMalwares();
				break;
			case 27:
				setMalwaresForLevel(0, 50, 25, 0, 10, 0, 75, 50, 15, 0);
				setFilesForLevel(8);
				addMalwares();
				break;
			case 28:
				setMalwaresForLevel(10, 25, 50, 0, 15, 10, 50, 75, 20, 0);
				setFilesForLevel(8);
				addMalwares();
			case 29:
				setMalwaresForLevel(25, 75, 75, 0, 15, 25, 100, 100, 25, 0);
				setFilesForLevel(8);
				addMalwares();
				break;
			case 30:
				setMalwaresForLevel(10, 0, 0, 10, 0, 10, 0, 0, 0, 0);
				setFilesForLevel(8);
				addMalwares();
				break;
			case 31:
				setMalwaresForLevel(25, 25, 25, 15, 25, 25, 25, 25, 30, 0);
				setFilesForLevel(8);
				addMalwares();
				break;
			case 32:
				setMalwaresForLevel(0, 50, 50, 20, 20, 0, 50, 50, 35, 0);
				setFilesForLevel(8);
				addMalwares();
			case 33:
				setMalwaresForLevel(0, 50, 50, 25, 20, 25, 75, 75, 40, 0);
				setFilesForLevel(8);
				addMalwares();
				break;
			case 34:
				setMalwaresForLevel(0, 100, 25, 25, 30, 0, 75, 25, 45, 0);
				setFilesForLevel(8);
				addMalwares();
				break;
			case 35:
				setMalwaresForLevel(0, 75, 75, 30, 30, 25, 125, 125, 50, 0);
				setFilesForLevel(8);
				addMalwares();
			case 36:
				setMalwaresForLevel(10, 75, 100, 35, 40, 10, 50, 100, 55, 0);
				setFilesForLevel(8);
				addMalwares();
				break;
			case 37:
				setMalwaresForLevel(20, 125, 50, 40, 45, 20, 150, 50, 60, 0);
				setFilesForLevel(8);
				addMalwares();
				break;
			case 38:
				setMalwaresForLevel(25, 100, 100, 45, 50, 10, 125, 100, 65, 0);
				setFilesForLevel(8);
				addMalwares();
			case 39:
				setMalwaresForLevel(20, 100, 150, 50, 50, 20, 150, 100, 70, 0);
				setFilesForLevel(8);
				addMalwares();
				break;
			case 40:
				setMalwaresForLevel(0, 0, 0, 0, 0, 0, 0, 0, 0, 5);
				setFilesForLevel(8);
				addMalwares();
				break;
				
			case 41:
				setMalwaresForLevel(5, 25, 25, 5, 5, 5, 25, 25, 75, 5);
				setFilesForLevel(8);
				addMalwares();
				break;
			case 42:
				setMalwaresForLevel(0, 75, 50, 20, 20, 0, 75, 75, 80, 0);
				setFilesForLevel(8);
				addMalwares();
			case 43:
				setMalwaresForLevel(0, 100, 75, 30, 20, 25, 100, 75, 85, 10);
				setFilesForLevel(8);
				addMalwares();
				break;
			case 44:
				setMalwaresForLevel(0, 175, 50, 40, 30, 0, 100, 100, 90, 10);
				setFilesForLevel(8);
				addMalwares();
				break;
			case 45:
				setMalwaresForLevel(0, 75, 150, 50, 40, 25, 125, 125, 95, 0);
				setFilesForLevel(8);
				addMalwares();
			case 46:
				setMalwaresForLevel(0, 100, 100, 60, 50, 0, 150, 150, 100, 15);
				setFilesForLevel(8);
				addMalwares();
				break;
			case 47:
				setMalwaresForLevel(0, 150, 75, 75, 75, 0, 250, 75, 105, 15);
				setFilesForLevel(8);
				addMalwares();
				break;
			case 48:
				setMalwaresForLevel(0, 150, 150, 90, 90, 0, 200, 200, 110, 20);
				setFilesForLevel(8);
				addMalwares();
			case 49:
				setMalwaresForLevel(0, 300, 200, 100, 100, 0, 300, 400, 115, 20);
				setFilesForLevel(8);
				addMalwares();
				break;
			case 50:
				setMalwaresForLevel(100, 600, 500, 150, 150, 100, 600, 500, 125, 25);
				setFilesForLevel(8);
				addMalwares();
				break;
			default:
				Game.gameState = Game.OVER;
		}
	}
}