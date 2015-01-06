package clientfiles;

import java.util.ListIterator;

import javax.swing.ImageIcon;

import clientfiles.Malware.State;

public class Encrypter extends Tower 
{
	public static int cost = 750;
	public static ImageIcon icon = new ImageIcon(MyImages.encrypter0);
	public static ImageIcon invalidIcon = new ImageIcon(MyImages.invalidEncrypter);
	public static int damageToSet = 0;
	public static int speedToSet = 0;
	public static int rangeToSet = 0;
	public int killsLeft;
	public int killsPerRound;
	public boolean regenerate;
	public int timeSinceLastRegen;
	public int regenerationInterval;
	public boolean deencrypter;
	
	public Encrypter(int xToSet, int yToSet, int idToSet) 
	{
		super(icon, idToSet);
		
		projectileDurability = 0;
		rangeOfSplash = 0;
		splashEffect = false;
		
		diameterOfTower = 50;
		
		x = 0;
		y = 0;
		
		killsPerRound = 10;
		killsLeft = killsPerRound;
		regenerate = false;
		timeSinceLastRegen = 0;
		regenerationInterval = 0;
		deencrypter = false;
		
		range = rangeToSet;
		timerReset = speedToSet;
		
		type = TowerType.ENCRYPTER;
		damage = damageToSet;
		
		realValue += cost;
		
		//make user pay for towers
		Game.makePurchase(cost);
	}

	@Override
	public void addUpgradeOptions(int idOfTower) 
	{
		switch (upgradesInPath1)
		{
			case 1:					Upgrades.upgradePath1.setText("Increased Efficiency");
									break;
			case 2:					Upgrades.upgradePath1.setText("Extreme Efficiency");
									break;
			case 3:					Upgrades.upgradePath1.setText("Path Closed");
									break;
			case 4:					Upgrades.upgradePath1.setText("Path Closed");
									break;
			default:				break;
		}
		switch(upgradesInPath2)
		{
			case 1:					Upgrades.upgradePath2.setText("De-encrypt Trojans");
									break;
			case 2:					
			case 3:					Upgrades.upgradePath2.setText("Path Closed");
									break;
		}
		switch(upgradesInPath3)
		{
			case 1:					Upgrades.upgradePath3.setText("Regenerate");
									break;
			case 2:					Upgrades.upgradePath3.setText("Faster Rejuvination");
									break;
		}
	}

	@Override
	public void displayUpgradeInfo(int upgradePath) 
	{
		if (upgradePath == 1)
		{
			switch (upgradesInPath1)
			{
				case 1:					Upgrades.upgradesInfo.setText("Increased Efficiency: $150\nEncrypter can encrypt 20 files before overflowing");
										break;
				case 2:					Upgrades.upgradesInfo.setText("Extreme efficiency: $250\nEncrypter can encrypt an impressive 30 files before"
						+ " overflowing.");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
				case 4:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
		else if(upgradePath == 2)
		{
			switch(upgradesInPath2)
			{
				case 1:
					Upgrades.upgradesInfo.setText("De-encrypt Trojans: $100\nDe-ecnrypts Trojans, like scanners, and makes "
							+ "them visible to your other towers");
					break;
				case 2:
				case 3:
					Upgrades.upgradesInfo.setText("Path Closed");
					break;
			}
		}
		else if(upgradePath == 3)
		{
			switch(upgradesInPath3)
			{
			case 1:
				Upgrades.upgradesInfo.setText("Regenerate: $500\nAllows the encrypter to recharge overtime, can attack more");
				break;
			case 2:
				Upgrades.upgradesInfo.setText("Faster Rejuvination: $400\nIncreases regeneration speeds");
				break;
			case 3:
			case 4:
				Upgrades.upgradesInfo.setText("Path Closed");
				break;
			}
		}
	}
	
	public void encryptAll()
	{
		//regeneration stuff
		if (regenerate && timeSinceLastRegen > 0 && killsLeft < killsPerRound)
		{
			timeSinceLastRegen--;
		}
		else if (regenerate && timeSinceLastRegen <= 0 && killsLeft < killsPerRound)
		{
			if (killsLeft == 0)
				icon = new ImageIcon(MyImages.firewall);
			killsLeft++;
			
			timeSinceLastRegen = regenerationInterval;
		}
		if (killsLeft == 0)
			return;
		
		//if it can encrypt, do so
		ListIterator<BonusFile> iter = BonusFile.allFiles.listIterator();
		while(iter.hasNext())
		{
			BonusFile file = iter.next();
			
			double dist = Math.sqrt(Math.pow(file.getX() - getX(), 2) + Math.pow(file.getY() - getY(), 2));
			
			if(dist < 20)
			{
				file.encrypt();
				killsLeft--;
			}
		}
		
		//if it can de-encrypt a Trojan, do that
		if(deencrypter)
		{
			for(int m = 0; m < Malware.allMalware.length; m++)
			{
				if(Malware.allMalware[m] == null)
					break;
				else if(Malware.allMalware[m] instanceof Trojan && Malware.allMalware[m].state == State.INVISIBLE)
				{
					Malware.allMalware[m].state = State.NORMAL;
					killsLeft--;
					break;
				}	
			}
		}
	}
}
