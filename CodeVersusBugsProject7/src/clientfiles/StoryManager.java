/**LevelManager.java
 * This class creates a LevelManager object, which generates a group of Virus objects.
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
 */
package clientfiles;

public class StoryManager implements LevelManager
{
	//current level
	public static int lvl;
	public int malwaresThisLevel = 15;
	
	// malwares per level
	private int malwaresForLevel1 = 15;
	private int malwaresForLevel2 = 30;
	private int malwaresForLevel3 = 60;
	private int malwaresForLevel4 = 5;
	private int malwaresForLevel5 = 55;
	private int malwaresForLevel6 = 120;
	
	public StoryManager()
	{
		lvl = 1;
	}
	
	public int getMalwaresThisLevel()
	{
		return malwaresThisLevel;
	}
	
	/**creates the malwares for story mode levels
	 * 
	 */
	public void addMalwares(int malwaresToAddThisLevel)
	{
		// v is the counter for malwares input
		int v = 0;
		int startY = -75;
		switch(lvl)
		{
		//all minions only levels
		case 1:
		case 2:
		case 3:
			// i is the number of malwares per route, as malwares to add is / 5 (5 = routes)
			for (int i = 0; i < malwaresToAddThisLevel / 5; i++)
			{
				// l == lane
				for(int l = 1; l < 6; l++)
				{
					Malware.allMalware[v] = new Minion(l, startY);
					v++;
				}
				startY -= 70;
			}
			break;
			//worms only levels
		case 4:
			// i is the number of malwares per route, as malwares to add is / 5 (5 = routes)
			for (int i = 0; i < malwaresToAddThisLevel / 5; i++)
			{
				// l == lane
				for(int l = 1; l < 6; l++)
				{
					Malware.allMalware[v] = new Worm(l, startY);
					v++;
				}
				startY -= 70;
			}
			break;
		//mostly minions followed by 5 worms
		case 5:
		case 6:
			//initialize all but 5 of malwares in this level to minions
			for (int i = 0; i < (malwaresToAddThisLevel - 5) / 5; i++)
			{
				// l == lane
				for(int l = 1; l < 6; l++)
				{
					Malware.allMalware[v] = new Minion(l, startY);
					v++;
				}
				startY -= 70;
			}
			//last 5 malwares should be worms
			for(int l = 1; l <= 5; l++)
			{
				Malware.allMalware[v] = new Worm(l, startY);
				v++;
			}
			break;
		}
	}
	
	public void closelvl()
	{	
		switch(lvl)
		{
			case 1:	
				addMalwares(malwaresForLevel1);
				malwaresThisLevel = malwaresForLevel1;
				break;
			case 2: 
				addMalwares(malwaresForLevel2);
				malwaresThisLevel = malwaresForLevel2;
				break;
			case 3:	
				addMalwares(malwaresForLevel3);
				malwaresThisLevel = malwaresForLevel3;
				break;
			case 4:	
				addMalwares(malwaresForLevel4);
				malwaresThisLevel = malwaresForLevel4;
				break;
			case 5:	
				addMalwares(malwaresForLevel5);
				malwaresThisLevel = malwaresForLevel5;
				break;
			case 6:
				addMalwares(malwaresForLevel6);
				malwaresThisLevel = malwaresForLevel6;
			case 7:
				Game.gameState = Game.OVER;
		}
	}
}
