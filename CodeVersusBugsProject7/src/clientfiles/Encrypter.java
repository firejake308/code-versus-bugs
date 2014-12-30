package clientfiles;

import java.util.ListIterator;

import javax.swing.ImageIcon;

public class Encrypter extends Tower 
{
	public static int cost = 750;
	public static ImageIcon icon = new ImageIcon(MyImages.encrypter0);
	public static ImageIcon invalidIcon = new ImageIcon(MyImages.invalidEncrypter);
	public static int damageToSet = 0;
	public static int speedToSet = 0;
	public static int rangeToSet = 0;
	public static int healthToSet = 50;
	
	public Encrypter(int xToSet, int yToSet, int idToSet) 
	{
		super(icon, idToSet);
		
		projectileDurability = 0;
		rangeOfSplash = 0;
		splashEffect = false;
		
		diameterOfTower = 50;
		
		x = 0;
		y = 0;
		
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
			case 1:					Upgrades.upgradePath1.setText("De-encrypt Trojans\n(WIP)");
									break;
			case 2:					Upgrades.upgradePath1.setText("Path Closed");
									break;
			case 3:					Upgrades.upgradePath1.setText("Path Closed");
									break;
			case 4:					Upgrades.upgradePath1.setText("Path Closed");
									break;
			default:				break;
		}
	}

	@Override
	public void displayUpgradeInfo(int upgradePath) 
	{
		if (upgradePath == 1)
		{
			switch (upgradesInPath1)
			{
				case 1:					Upgrades.upgradesInfo.setText("De-encrypt Trojans: $100\nDe-ecnrypts Trojans, like scanners, and makes "
						+ "them visible to your other towers.");
										break;
				case 2:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
				case 3:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
				case 4:					Upgrades.upgradesInfo.setText("Path Closed");
										break;
			}
		}
	}
	
	public void encryptAll()
	{
		ListIterator<BonusFile> iter = BonusFile.allFiles.listIterator();
		while(iter.hasNext())
		{
			BonusFile file = iter.next();
			
			double dist = Math.sqrt(Math.pow(file.getX() - getX(), 2) + Math.pow(file.getY() - getY(), 2));
			
			if(dist < 20)
			{
				file.encrypt();
			}
		}
	}
}
