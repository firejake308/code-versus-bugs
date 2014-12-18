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

public class StoryManager implements LevelManager
{
	public int malwaresThisLevel = 15;
	
	private int minions = 15;
	private int rushMinions = 0;
	private int tankMinions = 0;
	private int worms = 0;
	
	// malwares per level
	private int malwaresForLevel1 = 15;
	private int malwaresForLevel2 = 30;
	private int malwaresForLevel3 = 60;
	private int malwaresForLevel4 = 5;
	private int malwaresForLevel5 = 55;
	private int malwaresForLevel6 = 120;
	private int malwaresForLevel7 = 25;
	
	public StoryManager()  
	{
		
	}
	
	// set number of malwares per type that are in the level
	public void setMalwaresForLevel(int numOfMinions, int numOfFastMinions, int numOfSlowMinions, int numOfWorms)
	{
		minions = numOfMinions;
		rushMinions = numOfFastMinions;
		tankMinions = numOfSlowMinions;
		worms = numOfWorms;
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
				Malware.allMalware[v] = new Minion(l, startY);
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
				//Malware.allMalware[v] = new Minion(l, startY);
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
				//Malware.allMalware[v] = new Minion(l, startY);
				v++;
			}
			startY -= 70;
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
	}
	
	/**
	 * Takes the user to the next level.
	 */
	public void nextlvl()
	{	
		switch(Game.level)
		{
			case 1:	
				setMalwaresForLevel(15, 0, 0, 0);
				addMalwares();
				malwaresThisLevel = malwaresForLevel1;
				break;
			case 2:
				setMalwaresForLevel(30, 0, 0, 0);
				addMalwares();
				malwaresThisLevel = malwaresForLevel2;
				break;
			case 3:	
				setMalwaresForLevel(60, 0, 0, 0);
				addMalwares();
				malwaresThisLevel = malwaresForLevel3;
				break;
			case 4:	
				setMalwaresForLevel(0, 0, 0, 5);
				addMalwares();
				malwaresThisLevel = malwaresForLevel4;
				break;
			case 5:	
				setMalwaresForLevel(50, 0, 0, 5);
				addMalwares();
				malwaresThisLevel = malwaresForLevel5;
				break;
			case 6:setMalwaresForLevel(115, 0, 0, 5);
				addMalwares();
				malwaresThisLevel = malwaresForLevel6;
				break;
			case 7:
				setMalwaresForLevel(0, 0, 0, 25);
				addMalwares();
				malwaresThisLevel = malwaresForLevel7;
				break;
			case 8:
				Game.gameState = Game.OVER;
		}
	}
}
