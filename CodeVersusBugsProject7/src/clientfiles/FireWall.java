package clientfiles;

import javax.swing.ImageIcon;

/**
 * Created:		12/21/2014
 * 
 * @author Patrick
 *
 * This will be the brains for the fire walls
 */

public class FireWall extends Tower
{	
	public static int cost = 200;
	public static ImageIcon normalIcon = new ImageIcon(MyImages.firewall);
	public static ImageIcon invalidIcon = new ImageIcon(MyImages.invalidDT);
	public static int damageToSet = 100;
	public static int speedToSet = 0;
	public static int rangeToSet = 0;
	public int killsLeft;
	public int killsPerRound = 10;
	public boolean regenerate = false;
	public int timeSinceLastRegen = 0;
	public int regenerationInterval = 0;
	
	private Malware[] malwaresHit = new Malware[1000];
	
	public FireWall(int xToSet, int yToSet, int idToSet) 
	{
		super(normalIcon, idToSet);
		
		// to be edited later
		int [] costsOfUpgradesGoBetween = {150, 250, 400, 1000000, 0, 0, 0, 0, 0, 150, 300, 10000000, 0, 0, 0, 0, 0, 0, 500, 400, 10000000, 10000000, 0, 0, 0, 0, 0};
		costsOfUpgrades = costsOfUpgradesGoBetween;
		
		projectileDurability = 0;
		rangeOfSplash = 0;
		splashEffect = false;
		
		diameterOfTower = 50;
		
		x=0;
		y=0;
		
		killsLeft = killsPerRound;
		
		range = rangeToSet;
		timerReset = speedToSet;
		
		type = TowerType.FIREWALL;
		damage = damageToSet;
		
		realValue += cost;
		
		damage = 150;
		
		//make user pay for towers
		Game.makePurchase(cost);
	}
	
	public static void increaseDamage(int increase)
	{
		damageToSet += increase;
	}
	
	public void addUpgradeOptions(int idOfTower)
	{
		switch (allTowers[idOfTower].upgradesInPath1)
		{
			case 1:					Upgrades.upgradePath1.setText("Stronger Wall");
									break;
			case 2:					Upgrades.upgradePath1.setText("Extreme Wall");
									break;
			case 3:					Upgrades.upgradePath1.setText("Invincible Wall");
									break;
			case 4:					Upgrades.upgradePath1.setText("Path Closed");
									break;
			default:				break;
		}
		
		switch (allTowers[idOfTower].upgradesInPath2)
		{
			case 1:					Upgrades.upgradePath2.setText("Dangerous Wall");
									break;
			case 2:					Upgrades.upgradePath2.setText("Killer Wall");
									break;
			case 3:					Upgrades.upgradePath2.setText("Path Closed");
									break;
			default:				break;
		}
		
		switch (allTowers[idOfTower].upgradesInPath3)
		{
			case 1:					Upgrades.upgradePath3.setText("Regenerate Wall");
									break;
			case 2:					Upgrades.upgradePath3.setText("Faster Rejuvination");
									break;
			case 3:					Upgrades.upgradePath3.setText("Path Closed");
									break;
			case 4:					Upgrades.upgradePath3.setText("Path Closed");
									break;
			default:				break;
		}
	}
	
	public void displayUpgradeInfo(int upgradePath)
	{
		if (upgradePath == 1)
		{
			switch (upgradesInPath1)
			{
				case 1:					Upgrades.upgradesInfo.setText(" Stronger Wall:\n   $150\n Wall can block 20\n viruses before being\n  destroyed");
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Extreme Wall:\n   $250\n Wall can block 25\n viruses before being\n  destroyed");
										break;
				case 3:					Upgrades.upgradesInfo.setText(" Invincible Wall:\n    $400\n Wall can block 40\n viruses before being\n  destroyed");
										break;
				case 4:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
		
		else if (upgradePath == 2)
		{
			switch (upgradesInPath2)
			{
				case 1:					Upgrades.upgradesInfo.setText(" Dangerous Wall:\n    $150\n Wall deals more damage\n to worms and trojans");
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Killer Wall:\n    $300\n Wall deals even more damage\n to worms and trojans");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
		
		else if (upgradePath == 3)
		{
			switch (upgradesInPath3)
			{
				case 1:					Upgrades.upgradesInfo.setText(" Regenerate Wall:\n     $500\n Allows the wall to rebuild\n overtime, can attack more");
										break;
				case 2:					Upgrades.upgradesInfo.setText(" Faster Rejuvination:\n     $400\n Increases regeneration\n speeds");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
				case 4:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
	}
	
	public void dealDamage()
	{
		boolean virusHit = false;
		
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
		
		for (int m = 0; m < Malware.numMalwares; m++)
		{
			if (Malware.allMalware[m] == null)
				return;
			
			virusHit = false; // resets variable
			double xOfVirus = Malware.allMalware[m].getCenterX();
			double yOfVirus = Malware.allMalware[m].getCenterY();
			
			for (int i = 0; i < 1000; i++)
			{
				if (malwaresHit[i] == null)
					break;
				if (malwaresHit[i] == Malware.allMalware[m])
					virusHit = true;
			}
			
			double distFromMalware  = Math.sqrt(Math.pow(xOfVirus - getCenterX(), 2) + Math.pow(yOfVirus - getCenterY(), 2));
			
			if(distFromMalware <= Malware.allMalware[m].sprite.getHeight(null) / 2 && !virusHit)
			{
				Malware type = Malware.allMalware[m];
				
				if (type instanceof Minion)
				{
					type.dealDamage(1000, 0, id);
					killsLeft--;
				}
				else
				{
					type.dealDamage(damage, 1, id);
					killsLeft--;
					
					for (int i = 0; i < killsPerRound; i++)
					{
						if (malwaresHit[i] == null)
						{
							malwaresHit[i] = type;
							break;
						}
					}
				}
				
				if(killsLeft <= 0)
				{
					icon = new ImageIcon(MyImages.firewallBroken);
					return;
				}
			}
		}
	}
}
