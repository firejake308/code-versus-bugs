package clientfiles;

import java.awt.image.BufferedImage;
import java.util.ListIterator;

public class Virus extends Malware 
{
	public static BufferedImage sprite = MyImages.virus;
	
	private int lane;
	public boolean canReplicate;
	
	public Virus(int lane, int y) 
	{
		super(0, lane, y);
		
		//initialize instance variables
		health = 100;
		reward = 10;
		speed = (int) (w * 0.030);
		this.lane = lane;
		canReplicate = true;
	}
	
	private Virus(Virus parent)
	{
		this(parent.lane, (int) (parent.y));
		
		//for lanes 1,2, and 3
		if(parent.path != lane4 || parent.path != lane5)
		{
			//if on horizontal stretch, offest child virus x
			if(parent.getDistance() < parent.path[parent.path.length-1])
			{
				setX(parent.x - 50);
			}
			//else if on vertical stretch, offset child virus y
			else 
			{
				setX(parent.x);
				setY(parent.y + 50);
			}
		}
		//for paths 4 and 5, basically same thing except for index of path part
		//that we are comparing against
		else
		{
			//if on horizontal stretch, offest child virus x
			if(parent.getDistance() < parent.path[parent.path.length-3])
			{
				setX(parent.x - 50);
			}
			//else if on vertical stretch, offset child virus y
			else 
			{
				setX(parent.x);
				setY(parent.y + 50);
			}
		}
		setDistance(parent.distance - 50);
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
			if(file.getType() == BonusFile.EXE)
			{
				double dist = Math.sqrt(Math.pow(file.getX() - x, 2) + Math.pow(file.getY() - y, 2));
				
				//if distance is less than geometric mean of sprite's dimensions
				if(dist < Math.sqrt(sprite.getHeight() * sprite.getWidth()))
				{
					file.addToRecycleBin();
					
					//replicate the virus
					if(canReplicate)
					{
						Malware.allMalware[numMalwares] = new Virus(this);
						System.out.println("virus replicated, and now there are "+numMalwares);
						canReplicate = false;
					}
				}
			}
		}
	}
}
