package clientfiles;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.ListIterator;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class Virus extends Malware 
{
	public static BufferedImage sprite = MyImages.virus;
	public static BufferedImage tankSprite = MyImages.tankVirus;
	public static BufferedImage rushSprite = MyImages.rushVirus;
	
	private int lane;
	public boolean canReplicate;
	
	public Virus(int type, int lane, int y) 
	{
		super(type, lane, y);
		
		//initialize instance variables
		if(type == NORMAL)
		{
			health = 100;
			reward = 10;
			speed = (int) (w * 0.030);
		}
		else if(type == RUSH)
		{
			health = 90;
			reward = 11;
			speed = (int) (w * 0.035);
		}
		else if(type == TANK)
		{
			health = 150;
			reward = 15;
			speed = (int) (w * 0.025);
		}
		this.lane = lane;
		canReplicate = false;
	}
	
	private Virus(Virus parent)
	{
		this(parent.type, parent.lane, (int) (parent.y));
		
		canReplicate = false;
	}
	
	@Override
	public void moveVirus(double frames)
	{
		super.moveVirus(frames);
		
		//only difference is that we need to check for collison wth exe files
		ListIterator<BonusFile> iter = BonusFile.allFiles.listIterator();
		while(iter.hasNext())
		{
			BonusFile file = iter.next();
			//only bother to check if the file is an exe
			if((file.getType() == BonusFile.EXE || file.getType() == BonusFile.PACKET) && !file.isEncrypted())
			{
				double dist = Math.sqrt(Math.pow(file.getX() - x, 2) + Math.pow(file.getY() - y, 2));
				
				//if distance is less than geometric mean of sprite's dimensions
				if(dist < Math.sqrt(sprite.getHeight() * sprite.getWidth()))
				{
					file.addToRecycleBin();
					System.out.println("file consumed");
					//replicate the virus
					if(canReplicate)
					{
						switch(type)
						{
							case TANK:
								replicate(1);
								break;
							case NORMAL:
								replicate(2);
								break;
							case RUSH:
								replicate(4);
								break;
						}
						System.out.println("virus replicated, and now there are "+numMalwares);
						canReplicate = false;
					}
					break;
				}
			}
		}
	}
	/**
	 * Makes copies of a virus
	 * 
	 * @param copies the number of copies to make
	 */
	public void replicate(int copies)
	{
		int frontInterval = Math.min(50, (int)((path[pathPart] - distance)/copies));
		int backInterval;
		try
		{
			backInterval = Math.min(50, (int)((distance - path[pathPart-1])/copies));
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			backInterval = 50;
		}
		
		System.out.println("copies/2 "+copies/2);
		
		//if the path is vertical
		if(pathPart % 2 == 0)
		{
			//if going down
			if(directions[pathPart] == 1)
			{
				//spawn viruses behind
				for(int c = 1; c <= Math.ceil(copies / 2.0); c++)
				{
					Malware.allMalware[numMalwares] = new Virus(type, lane, (int)(y - c * backInterval));
					Malware.allMalware[numMalwares-1].setCenterX(getCenterX());
					Malware.allMalware[numMalwares-1].setDistance(getDistance() - c * backInterval);
				}
				//spawn viruses behind
				for(int c = 1; c <= copies / 2; c++)
				{
					Malware.allMalware[numMalwares] = new Virus(type, lane, (int)(y + c * frontInterval));
					Malware.allMalware[numMalwares-1].setCenterX(getCenterX());
					Malware.allMalware[numMalwares-1].setDistance(getDistance() + c * frontInterval);
				}
			}
			//if going up, switch sign on distance
			else
			{
				//spawn viruses behind
				for(int c = 1; c <= Math.ceil(copies / 2.0); c++)
				{
					Malware.allMalware[numMalwares] = new Virus(type, lane, (int)(y + c * backInterval));
					Malware.allMalware[numMalwares-1].setCenterX(getCenterX());
					Malware.allMalware[numMalwares-1].setDistance(getDistance() - c * backInterval);	
					System.out.println(c);
				}
				//spawn viruses in front
				for(int c = 1; c <= copies / 2; c++)
				{
					Malware.allMalware[numMalwares] = new Virus(type, lane, (int)(y - c * frontInterval));
					Malware.allMalware[numMalwares-1].setCenterX(getCenterX());
					Malware.allMalware[numMalwares-1].setDistance(getDistance() + c * frontInterval);
				}
			}
		}
				//if on horizontal part of track
				else
				{
					//if going forward
					if(directions[pathPart] == 1)
					{
						//spawn copies behind, at least 1
						for(int c = 1; c <= Math.ceil(copies / 2.0); c++)
						{
							Malware.allMalware[numMalwares] = new Virus(this);
							Malware.allMalware[numMalwares-1].setCenterX(getCenterX() - c * backInterval);
							Malware.allMalware[numMalwares-1].setDistance(getDistance() - c * backInterval);
						}
						//spawn copies in front, may be 0
						for(int c = 1; c <= copies / 2; c++)
						{
							Malware.allMalware[numMalwares] = new Virus(this);
							Malware.allMalware[numMalwares-1].setCenterX(getCenterX() + c * frontInterval);
							Malware.allMalware[numMalwares-1].setDistance(getDistance() + c * frontInterval);
						}
					}
					//if going left, switch sign on distance
					else
					{
						//spawn copies behind, at least 1
						for(int c = 1; c <= Math.ceil(copies / 2.0); c++)
						{
							Malware.allMalware[numMalwares] = new Virus(this);
							Malware.allMalware[numMalwares-1].setCenterX(getCenterX() - c * backInterval);
							Malware.allMalware[numMalwares-1].setDistance(getDistance() + c * backInterval);
						}
						//spawn copies in front, may be 0
						for(int c = 1; c <= copies / 2; c++)
						{
							Malware.allMalware[numMalwares] = new Virus(this);
							Malware.allMalware[numMalwares-1].setCenterX(getCenterX() + c * frontInterval);
							Malware.allMalware[numMalwares-1].setDistance(getDistance() - c * frontInterval);
						}
					}
				}	
	}
}
