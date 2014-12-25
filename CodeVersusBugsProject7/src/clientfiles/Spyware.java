package clientfiles;

import java.awt.image.BufferedImage;
import java.util.ListIterator;

public class Spyware extends Malware 
{
	public static BufferedImage sprite = MyImages.spy;
	
	private boolean hasData;
	private int lane;
	
	public Spyware(int lane, int y) 
	{
		super(Malware.NORMAL, lane, y);
		
		//initialize instance variables
		health = 200;
		reward = 15;
		speed = (int) (w * 0.030);
		hasData = false;
		this.lane = lane;
	}
	
	@Override
	public void moveVirus(double frames)
	{
		if(!hasData)
		{
			super.moveVirus(frames);
		
			//only difference is that we need to check for collison wth exe files
			ListIterator<BonusFile> iter = BonusFile.allFiles.listIterator();
			while(iter.hasNext())
			{
				BonusFile file = iter.next();
				//only bother to check if the file is a data
				if(file.getType() == BonusFile.DATA)
				{
					double dist = Math.sqrt(Math.pow(file.getX() - x, 2) + Math.pow(file.getY() - y, 2));
				
					//if distance is less than geometric mean of sprite's dimensions
					if(dist < Math.sqrt(sprite.getHeight() * sprite.getWidth()))
					{
						file.addToRecycleBin();
						
						hasData = true;
					}
				}
			}
		}
		//otherwise, if we have already acquired the data, run back
		else
		{
			System.out.println("moving back");
			//if virus makes it across the map, then despawn virus
			if(getDistance()<0)
			{
				System.out.println("a virus made it across");
				
				Game.makePurchase((int)(Game.money*0.95));
				
				if (Game.lives <= 0)
				{
					Game.gameState = Game.OVER;
				}
				System.out.println(this.toString());
				despawn();
			}
			else if(getDistance()<path[0])
			{
				setY(getY()-directions[0]*speed*frames/60*manipulator * Game.speedModifier);
				setDistance(getDistance()-speed*frames/60*manipulator * Game.speedModifier);
			}
			else if(getDistance()<path[1])
			{
				setX(getX()-directions[1]*speed*frames/60*manipulator * Game.speedModifier);
				setDistance(getDistance()-speed*frames/60*manipulator * Game.speedModifier);
			}
			else if(getDistance()<path[2])
			{
				setY(getY()-directions[2]*speed*frames/60*manipulator * Game.speedModifier);
				setDistance(getDistance()-speed*frames/60*manipulator * Game.speedModifier);
			}
			else if(getDistance()<path[3])
			{
				setX(getX()-directions[3]*speed*frames/60*manipulator * Game.speedModifier);
				setDistance(getDistance()-speed*frames/60*manipulator * Game.speedModifier);
			}
			else if(getDistance()<path[4])
			{
				setY(getY()-directions[4]*speed*frames/60*manipulator * Game.speedModifier);
				setDistance(getDistance()-speed*frames/60*manipulator * Game.speedModifier);
			}
			else if(getDistance()<path[5])
			{
				setX(getX()-directions[5]*speed*frames/60*manipulator * Game.speedModifier);
				setDistance(getDistance()-speed*frames/60*manipulator * Game.speedModifier);
				System.out.println("lane 4/5 going sideways to left");
			}
			else if(getDistance()<path[6])
			{
				setY(getY()-directions[6]*speed*frames/60*manipulator * Game.speedModifier);
				setDistance(getDistance()-speed*frames/60*manipulator * Game.speedModifier);
				System.out.println("lane 4/5 on file stream, going back");
			}
			else if(getDistance()<path[7])
			{
				setX(getX()-directions[7]*speed*frames/60*manipulator * Game.speedModifier);
				setDistance(getDistance()-speed*frames/60*manipulator * Game.speedModifier);
				System.out.println("going sideways to left");
			}
			else if(getDistance()<path[8])
			{
				setY(getY()-directions[8]*speed*frames/60*manipulator * Game.speedModifier);
				setDistance(getDistance()-speed*frames/60*manipulator * Game.speedModifier);
				System.out.println("on file stream, going back");
			}
		}
	}
}
