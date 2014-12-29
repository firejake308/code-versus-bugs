package clientfiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ListIterator;

public class BonusFile 
{
	public static final int EXE = 0;
	public static final int DATA = 1;
	public static ArrayList<BonusFile> allFiles = new ArrayList<BonusFile>();
	public static ArrayList<BonusFile> recycleBin = new ArrayList<BonusFile>();
	
	private int x;
	private double y;
	private double speed;
	private int reward;
	private BufferedImage sprite;
	private int type;
	private boolean encrypted;
	
	public BonusFile(int y, int type) 
	{
		//initialize instance variables
		reward = 10;
		speed = Game.widthOfGamePanel  * 0.01;
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
		
		ListIterator<BonusFile> iterator = allFiles.listIterator();
		iterator.add(this);
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	private void setY(double y) {
		this.y = y;
	}
	private void setCenterX(int xToSet) 
	{
		x = xToSet - sprite.getWidth()/2;
	}
	public int getType()
	{
		return type;
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(sprite, x, (int)y, null);
	}
	
	public void move(double frames)
	{
		//keep going up until hits cpu
		if(getY() > Game.gamePanel.path[1].getY())
		{
			setY(getY()-speed*frames/60 * Game.speedModifier);
			//debug
			//System.out.println((speed*frames/60 * Game.speedModifier));
		}
		//if virus makes it across the map, then despawn virus
		else
		{
			System.out.println("a file made it across");
					
			Game.lives+=reward;
			Game.gf.life.setText("Bytes Remaining: " + Game.lives);
			
			addToRecycleBin();
		}
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
				System.out.println("virus can replicate again.");
			}
			else if(m == null)
				break;
		}
	}
}
