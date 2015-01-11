package clientfiles;

import java.awt.image.BufferedImage;

public class Bot extends Malware 
{
	public static BufferedImage sprite = MyImages.bot;
	
	private int lane;
	public int timer=0;
	public final int TIMER_RESET = 600;
	
	public Bot(int lane, int y) 
	{
		super(NORMAL, lane, y);
		
		//initialize instance variables
		health = 2500;
		reward = 10;
		speed = (int) (w * 0.030);
		this.lane = lane;
	}
	
	/**
	 * Spawns a wave of malwares around the bot.
	 */
	public void spawnWave()
	{
		int frontInterval = Math.min(50, (int)((path[pathPart] - distance)/3));
		int backInterval;
		try
		{
			backInterval = Math.min(50, (int)((distance - path[pathPart-1])/3));
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			backInterval = 50;
		}
		
		//if the path is vertical
		if(pathPart % 2 == 0)
		{
			//if going down
			if(directions[pathPart] == 1)
			{
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y - backInterval));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX());
				Malware.allMalware[numMalwares-1].setDistance(getDistance() - backInterval);
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y - 2 * backInterval));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX());
				Malware.allMalware[numMalwares-1].setDistance(getDistance() - 2 * backInterval);
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y - 3 * backInterval));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX());
				Malware.allMalware[numMalwares-1].setDistance(getDistance() - 3 * backInterval);
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y + frontInterval));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX());
				Malware.allMalware[numMalwares-1].setDistance(getDistance() + frontInterval);
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y + 2 * frontInterval));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX());
				Malware.allMalware[numMalwares-1].setDistance(getDistance() + 2 * frontInterval);
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y + 3 * frontInterval));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX());
				Malware.allMalware[numMalwares-1].setDistance(getDistance() + 3 * frontInterval);
			}
			//if going up, switch sign on distance
			else
			{
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y + backInterval));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX());
				Malware.allMalware[numMalwares-1].setDistance(getDistance() - backInterval);
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y + 2 * backInterval));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX());
				Malware.allMalware[numMalwares-1].setDistance(getDistance() - 2 * backInterval);
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y + 3 * backInterval));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX());
				Malware.allMalware[numMalwares-1].setDistance(getDistance() - 3 * backInterval);
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y - frontInterval));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX());
				Malware.allMalware[numMalwares-1].setDistance(getDistance() + frontInterval);
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y - 2 * frontInterval));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX());
				Malware.allMalware[numMalwares-1].setDistance(getDistance() + 2 * frontInterval);
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y - 3 * frontInterval));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX());
				Malware.allMalware[numMalwares-1].setDistance(getDistance() + 3 * frontInterval);
			}
		}
		//if on horizontal part of track
		else
		{
			if(directions[pathPart] == 1)
			{
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX() - backInterval);
				Malware.allMalware[numMalwares-1].setDistance(getDistance() - backInterval);
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX() - 2 * backInterval);
				Malware.allMalware[numMalwares-1].setDistance(getDistance() - 2 * backInterval);
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX() - 3 * backInterval);
				Malware.allMalware[numMalwares-1].setDistance(getDistance() - 3 * backInterval);
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX() + frontInterval);
				Malware.allMalware[numMalwares-1].setDistance(getDistance() + frontInterval);
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX() + 2 * frontInterval);
				Malware.allMalware[numMalwares-1].setDistance(getDistance() + 2 * frontInterval);
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX() + 3 * frontInterval);
				Malware.allMalware[numMalwares-1].setDistance(getDistance() + 3 * frontInterval);
			}
			else
			{
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX() + backInterval);
				Malware.allMalware[numMalwares-1].setDistance(getDistance() - backInterval);
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX() + 2 * backInterval);
				Malware.allMalware[numMalwares-1].setDistance(getDistance() - 2 * backInterval);
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX() + 3 * backInterval);
				Malware.allMalware[numMalwares-1].setDistance(getDistance() - 3 * backInterval);
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX() - frontInterval);
				Malware.allMalware[numMalwares-1].setDistance(getDistance() + frontInterval);
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX() - 2 * frontInterval);
				Malware.allMalware[numMalwares-1].setDistance(getDistance() + 2 * frontInterval);
				Malware.allMalware[numMalwares] = new Minion(RUSH, lane, (int)(y));
				Malware.allMalware[numMalwares-1].setCenterX(getCenterX() - 3 * frontInterval);
				Malware.allMalware[numMalwares-1].setDistance(getDistance() + 3 * frontInterval);
			}
		}
	}
}
