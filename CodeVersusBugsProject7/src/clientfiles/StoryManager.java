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

import javax.swing.ImageIcon;

public class StoryManager implements LevelManager
{
	public int malwaresThisLevel = 15;
	
	private int minions = 15;
	private int rushMinions = 0;
	private int tankMinions = 0;
	private int worms = 0;
	private int trojans = 0;
	
	// malwares per level
	private int malwaresForLevel1 = 15;
	private int malwaresForLevel2 = 30;
	private int malwaresForLevel3 = 50;
	private int malwaresForLevel4 = 80;
	private int malwaresForLevel5 = 140;
	private int malwaresForLevel6 = 200;
	private int malwaresForLevel7 = 270;
	private int malwaresForLevel8 = 450;
	private int malwaresForLevel9 = 700;
	private int malwaresForLevel10 = 10;
	private int malwaresForLevel11 = 280;
	private int malwaresForLevel12 = 455;
	private int malwaresForLevel13 = 615;
	private int malwaresForLevel14 = 625;
	private int malwaresForLevel15 = 60;
	private int malwaresForLevel16 = 140;
	private int malwaresForLevel17 = 310;
	private int malwaresForLevel18 = 440;
	private int malwaresForLevel19 = 615;
	private int malwaresForLevel20 = 535;
	
	public StoryManager()  
	{
		
	}
	
	// set number of malwares per type that are in the level
	public void setMalwaresForLevel(int numOfMinions, int numOfFastMinions, int numOfSlowMinions, int numOfWorms, int numOfTrojans)
	{
		minions = numOfMinions;
		rushMinions = numOfFastMinions;
		tankMinions = numOfSlowMinions;
		worms = numOfWorms;
		trojans = numOfTrojans;
	}
	
	public int getMalwaresThisLevel()
	{
		return malwaresThisLevel;
	}
	
	/**
	 * Creates the malwares for story mode levels.
	 * 
	 * @param malwaresToAddThisLevel the total number of malwares in this level.
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
		
		startY = Game.heightOfGamePanel + 10;
		// i is the number of trojans per route, as worms is / 5 (5 = routes)
		for (int i = 0; i < trojans; i++)
		{
			Malware.allMalware[v] = new Trojan(Malware.NORMAL, startY);
			v++;
			startY += 70;
		}
	}
	
	/**
	 * Takes the user to the next level.
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
				setMalwaresForLevel(15, 0, 0, 0, 0);
				addMalwares();
				malwaresThisLevel = malwaresForLevel1;
				break;
			case 2:
				setMalwaresForLevel(30, 0, 0, 0, 0);
				addMalwares();
				malwaresThisLevel = malwaresForLevel2;
				break;
			case 3:	
				setMalwaresForLevel(50, 0, 0, 0, 0);
				addMalwares();
				malwaresThisLevel = malwaresForLevel3;
				break;
			case 4:	
				setMalwaresForLevel(80, 00, 00, 0, 0);
				addMalwares();
				malwaresThisLevel = malwaresForLevel4;
				break;
			case 5:	
				setMalwaresForLevel(100, 20, 20, 0, 0);
				addMalwares();
				malwaresThisLevel = malwaresForLevel5;
				break;
			case 6:
				setMalwaresForLevel(120, 40, 40, 0, 0);
				addMalwares();
				malwaresThisLevel = malwaresForLevel6;
				break;
			case 7:
				setMalwaresForLevel(150, 60, 60, 0, 0);
				addMalwares();
				malwaresThisLevel = malwaresForLevel7;
				break;
			case 8:
				setMalwaresForLevel(250, 100, 100, 0, 0);
				addMalwares();
				malwaresThisLevel = malwaresForLevel8;
				break;
			case 9:
				setMalwaresForLevel(400, 150, 150, 0, 0);
				addMalwares();
				malwaresThisLevel = malwaresForLevel9;
				break;
			case 10:
				setMalwaresForLevel(0, 0, 0, 10, 0);
				addMalwares();
				malwaresThisLevel = malwaresForLevel10;
				break;
			case 11:
				setMalwaresForLevel(150, 60, 60, 10, 0);
				addMalwares();
				malwaresThisLevel = malwaresForLevel11;
				break;
			case 12:
				setMalwaresForLevel(200, 120, 120, 15, 0);
				addMalwares();
				malwaresThisLevel = malwaresForLevel12;
				break;
			case 13:
				setMalwaresForLevel(250, 175, 175, 15, 0);
				addMalwares();
				malwaresThisLevel = malwaresForLevel13;
				break;
			case 14:
				setMalwaresForLevel(300, 250, 60, 15, 0);
				addMalwares();
				malwaresThisLevel = malwaresForLevel14;
				break;
			case 15:
				setMalwaresForLevel(10, 10, 10, 40, 0);
				addMalwares();
				malwaresThisLevel = malwaresForLevel15;
				break;
			case 16:
				setMalwaresForLevel(40, 30, 30, 40, 0);
				addMalwares();
				malwaresThisLevel = malwaresForLevel16;
				break;
			case 17:
				setMalwaresForLevel(100, 80, 80, 50, 0);
				addMalwares();
				malwaresThisLevel = malwaresForLevel17;
				break;
			case 18:
				setMalwaresForLevel(150, 120, 120, 50, 0);
				addMalwares();
				malwaresThisLevel = malwaresForLevel18;
				break;
			case 19:
				setMalwaresForLevel(175, 150, 150, 60, 0);
				addMalwares();
				malwaresThisLevel = malwaresForLevel19;
				break;
			case 20:
				setMalwaresForLevel(0, 0, 0, 0, 10);
				addMalwares();
				malwaresThisLevel = malwaresForLevel20;
				break;
			case 21:
				Game.gameState = Game.OVER;
		}
	}
}
