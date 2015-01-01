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
	private int spywares = 0;
	private int bots;
	
	/**
	 * @deprecated
	 */
	public StoryManager()  
	{
		
	}
	
	/**
	 * Sets number of malwares per type that are in the level.
	 *  
	 * @param numOfMinions
	 * @param numOfFastMinions
	 * @param numOfSlowMinions
	 * @param numOfWorms
	 * @param numOfTrojans
	 */
	public void setMalwaresForLevel(int numOfMinions, int numOfFastMinions, int numOfSlowMinions, int numOfWorms, int numOfTrojans, int numOfViruses, int numOfSpyware, int numOfBots)
	{
		minions = numOfMinions;
		rushMinions = numOfFastMinions;
		tankMinions = numOfSlowMinions;
		worms = numOfWorms;
		trojans = numOfTrojans;
		viruses = numOfViruses;
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
				Malware.allMalware[v] = new Virus(l, startY);
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
		
		//make files and Trojans if necessary
		startY = Game.heightOfGamePanel + 10;
		int filesSoFar = 0;
		int trojansSoFar = 0;
		for (int i = 0; i < filesThisLevel + trojans; i ++)
		{
			Random gen = new Random();
			int random = gen.nextInt(4);
			
			//50% probability of making a file
			if(random < 2 && filesSoFar < filesThisLevel)
			{
				if(random == 0)
					BonusFile.allFiles.add(new BonusFile(startY, BonusFile.DATA));
				else if(random == 1)
					BonusFile.allFiles.add(new BonusFile(startY, BonusFile.EXE));
				startY += 100;
				filesSoFar++;
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
		
		switch(Game.level)
		{
			case 1:	
				setMalwaresForLevel(15, 0, 0, 0, 0, 0, 0, 0);
				addMalwares();
				break;
			case 2:
				setMalwaresForLevel(30, 0, 0, 0, 0, 0, 0, 0);
				addMalwares();
				break;
			case 3:	
				setMalwaresForLevel(50, 0, 0, 0, 0, 0, 0, 0);
				addMalwares();
				break;
			case 4:	
				setMalwaresForLevel(80, 00, 00, 0, 0, 0, 0, 0);
				addMalwares();
				break;
			case 5:	
				setMalwaresForLevel(100, 20, 20, 0, 0, 0, 0, 0);
				addMalwares();
				//special case for tutorial slide 30
				if(Game.tutorial && Game.tutorialSlide <= 30)
					Game.gamePanel.nextSlide();
				break;
			case 6:
				setMalwaresForLevel(120, 40, 40, 0, 0, 0, 0, 0);
				addMalwares();
				break;
			case 7:
				setMalwaresForLevel(150, 60, 60, 0, 0, 0, 0, 0);
				addMalwares();
				break;
			case 8:
				setMalwaresForLevel(250, 100, 100, 0, 0, 0, 0, 0);
				addMalwares();
				break;
			case 9:
				setMalwaresForLevel(400, 150, 150, 0, 0, 0, 0, 0);
				addMalwares();
				break;
			case 10:
				setMalwaresForLevel(0, 0, 0, 10, 0, 0, 0, 0);
				addMalwares();
				break;
			case 11:
				setMalwaresForLevel(150, 60, 60, 10, 0, 0, 0, 0);
				addMalwares();
				break;
			case 12:
				setMalwaresForLevel(200, 120, 120, 15, 0, 0, 0, 0);
				addMalwares();
				break;
			case 13:
				setMalwaresForLevel(250, 175, 175, 15, 0, 0, 0, 0);
				addMalwares();
				break;
			case 14:
				setMalwaresForLevel(300, 250, 60, 15, 0, 0, 0, 0);
				addMalwares();
				break;
			case 15:
				setMalwaresForLevel(10, 10, 10, 40, 0, 0, 0, 0);
				addMalwares();
				break;
			case 16:
				setMalwaresForLevel(40, 30, 30, 40, 0, 0, 0, 0);
				addMalwares();
				break;
			case 17:
				setMalwaresForLevel(100, 80, 80, 50, 0, 0, 0, 0);
				addMalwares();
				break;
			case 18:
				setMalwaresForLevel(150, 120, 120, 50, 0, 0, 0, 0);
				addMalwares();
				break;
			case 19:
				setMalwaresForLevel(175, 150, 150, 60, 0, 0, 0, 0);
				addMalwares();
				break;
			case 20:
				setMalwaresForLevel(0, 0, 0, 0, 10, 0, 0, 0);
				setFilesForLevel(4);
				addMalwares();
				//move onto files tutorial
				if(Game.tutorial && Game.tutorialSlide <= 72)
					Game.gamePanel.nextSlide();
				break;
			case 21:
				setMalwaresForLevel(0, 0, 0, 0, 0, 15, 0, 0);
				setFilesForLevel(6);
				addMalwares();
				break;
			case 22:
				setMalwaresForLevel(0, 0, 0, 0, 0, 0, 15, 0);
				setFilesForLevel(6);
				addMalwares();
				break;
			case 23:
				setMalwaresForLevel(0, 0, 0, 0, 0, 15, 15, 0);
				setFilesForLevel(6);
				addMalwares();
				break;
			case 24:
				setMalwaresForLevel(0, 0, 0, 0, 0, 0, 0, 5);
				setFilesForLevel(6);
				addMalwares();
			default:
				Game.gameState = Game.OVER;
		}
	}
}
