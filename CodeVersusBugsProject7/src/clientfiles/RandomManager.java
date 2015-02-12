package clientfiles;

import java.util.Random;

import javax.swing.ImageIcon;

/**
 * 
 * @author Ahmadul
 *
 */
public class RandomManager implements LevelManager 
{
	private static Random generator = new Random();
	
	public int malwaresThisLevel = 0;
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
	 * Creates the malwares for story mode levels.
	 * 
	 */
	public void addMalwares()
	{
		// v is the counter for malwares input
		int v = 0;
		int startY = -75;
		
		//make normal minions
		int minionsSoFar = 0;
		while(minionsSoFar < minions)
		{
			Malware.allMalware[v] = new Minion(Minion.NORMAL, generator.nextInt(5)+1, startY);
			minionsSoFar++;
			v++;
			startY -= generator.nextInt(70) + 5;
		}
		
		//make tank minions
		startY = -75;
		int tanksSoFar = 0;
		while(tanksSoFar < tankMinions)
		{
			Malware.allMalware[v] = new Minion(Minion.TANK, generator.nextInt(5)+1, startY);
			tanksSoFar++;
			v++;
			startY -= generator.nextInt(70) + 5;
		}
		
		//make rush minions
		startY = -75;
		int rushesSoFar = 0;
		while(rushesSoFar < rushMinions)
		{
			Malware.allMalware[v] = new Minion(Minion.RUSH, generator.nextInt(5)+1, startY);
			rushesSoFar++;
			v++;
			startY -= generator.nextInt(10) + 5;
		}
		
		//make normal viruses
		int virusesSoFar = 0;
		while(virusesSoFar < viruses)
		{
			Malware.allMalware[v] = new Virus(Malware.NORMAL, generator.nextInt(5)+1, startY);
			virusesSoFar++;
			v++;
			startY -= generator.nextInt(50) + 5;
		}
		
		//make tank viruses
		startY = -75;
		int tankVsSoFar = 0;
		while(tankVsSoFar < viruses)
		{
			Malware.allMalware[v] = new Virus(Malware.TANK, generator.nextInt(5)+1, startY);
			tankVsSoFar++;
			v++;
			startY -= generator.nextInt(50) + 5;
		}
		
		//make rush viruses
		startY = -75;
		int rushVsSoFar = 0;
		while(rushVsSoFar < viruses)
		{
			Malware.allMalware[v] = new Virus(Malware.RUSH, generator.nextInt(5)+1, startY);
			rushVsSoFar++;
			v++;
			startY -= generator.nextInt(50) + 5;
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

	@Override
	public void setMalwaresForLevel(int numOfMinions, int numOfFastMinions, int numOfSlowMinions, int numOfWorms,
			int numOfTrojans, int numOfViruses, int numOfVirusesF, int numOfVirusesT, int numOfSpyware, int numOfBots) 
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

	@Override
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
		
		if(Game.level < 10)
		{
			malwaresThisLevel = (int)(15 + 25 * Math.pow(Game.level-1, 1.2));
			
			//decide number of each type of minions
			int m1 = generator.nextInt(malwaresThisLevel/2);
			int m2 = generator.nextInt(malwaresThisLevel - m1);
			int m3 = malwaresThisLevel - (m1 + m2);
			
			//set malwares for level and add
			setMalwaresForLevel(m1, m2, m3, 0, 0, 0, 0, 0, 0, 0);
			setFilesForLevel(0);
			addMalwares();
		}
		else if(Game.level < 20)
		{
			//decide on total number of malwares through formula
			malwaresThisLevel = (int)(270 + 25 * Math.pow(Game.level-1, 1.2));
			
			//decide number of each type of malware
			int m1 = generator.nextInt(malwaresThisLevel/6);
			int m2 = generator.nextInt(malwaresThisLevel/3 - m1);
			int m3 = generator.nextInt(malwaresThisLevel/2 - m1 - m2);
			int v1 = generator.nextInt(2*malwaresThisLevel/3 - m1 - m2 - m3);
			int v2 = generator.nextInt(5*malwaresThisLevel/6 - m1 - m2 - m3 - v1);
			int v3 = malwaresThisLevel - (m1 + m2 + m3 + v1 + v2);
			
			//set malwares for level and add
			setMalwaresForLevel(m1, m2, m3, 0, 0, v1, v2, v3, 0, 0);
			setFilesForLevel(10);
			addMalwares();
		}
		else if(Game.level < 40)
		{
			//decide on total number of malwares through formula
			malwaresThisLevel = (int)(500 + 35 * Math.pow(Game.level-1, 1.2));
			
			//arrays for different types of malware
			int[] m = new int[8];
			
			m[0] = generator.nextInt(malwaresThisLevel/8);
			m[1] = generator.nextInt(malwaresThisLevel/4 - m[0]);
			m[2] = generator.nextInt(3*malwaresThisLevel/8 - m[0] - m[1]);
			m[3] = generator.nextInt(malwaresThisLevel/2 - m[0] - m[1] - m[2]);
			m[4] = generator.nextInt(5*malwaresThisLevel/8 - m[0] - m[1] - m[2] - m[3]);
			m[5] = generator.nextInt(3*malwaresThisLevel/4 - m[0] - m[1] - m[2] - m[3] - m[4]);
			m[6] = generator.nextInt(7*malwaresThisLevel/8 - m[0] - m[1] - m[2] - m[3] - m[4] - m[5]);
			m[7] = malwaresThisLevel - (m[0] + m[1] + m[2] + m[3] + m[4] + m[5] + m[6]);
			
			//set malwares for level and add
			if(generator.nextBoolean())
				setMalwaresForLevel(m[7], m[6], m[5], m[4], m[3], m[2], m[1], m[0], 0, 0);
			else
				setMalwaresForLevel(m[6], m[7], m[0], m[4], m[5], m[3], m[1], m[2], 0, 0);
			setFilesForLevel(10);
			addMalwares();
		}
		else
		{
			//decide on total number of malwares through formula
			malwaresThisLevel = (int)(750 + 35 * Math.pow(Game.level-1, 1.2));
			
			//array for different types of malware
			int[] m = new int[9];
			
			m[0] = generator.nextInt(malwaresThisLevel/9);
			m[1] = generator.nextInt(2*malwaresThisLevel/9 - m[0]);
			m[2] = generator.nextInt(3*malwaresThisLevel/9 - m[0] - m[1]);
			m[3] = generator.nextInt(4*malwaresThisLevel/9 - m[0] - m[1] - m[2]);
			m[4] = generator.nextInt(5*malwaresThisLevel/9 - m[0] - m[1] - m[2] - m[3]);
			m[5] = generator.nextInt(6*malwaresThisLevel/9 - m[0] - m[1] - m[2] - m[3] - m[4]);
			m[6] = generator.nextInt(7*malwaresThisLevel/9 - m[0] - m[1] - m[2] - m[3] - m[4] - m[5]);
			m[7] = generator.nextInt(7*malwaresThisLevel/9 - m[0] - m[1] - m[2] - m[3] - m[4] - m[5] - m[6]);
			m[8] = malwaresThisLevel - (m[0] + m[1] + m[2] + m[3] + m[4] + m[5] + m[6] - m[7]);
			
			//account for bots now
			int bots = Game.level - 40;
			if(Game.level > 50)
				bots *= 1.5;
			malwaresThisLevel += bots;
			
			//set malwares for level and add
			if(generator.nextBoolean())
				setMalwaresForLevel(m[7], m[6], m[5], m[4], m[3], m[2], m[1], m[0], m[8], bots);
			else
				setMalwaresForLevel(m[6], m[4], m[0], m[8], m[5], m[3], m[1], m[2], m[7], bots);
			setFilesForLevel(10);
			addMalwares();
		}
	}
	
	/**
	 * @deprecated
	 */
	@Override
	public int getMalwaresThisLevel() {
		return malwaresThisLevel;
	}

}
