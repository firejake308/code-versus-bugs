package clientfiles;

public interface LevelManager 
{
	// extra methods removed to get rid of errors, replaced by:
	public void addMalwares();
	
	public void setMalwaresForLevel(int numOfMinions, int numOfFastMinions, int numOfSlowMinions, int numOfWorms, int numOfTrojans, int numOfViruses, int numOfVirusesF, int numOfVirusesT, int numOfSpyware, int numOfBots);
	
	public void setFilesForLevel(int numFiles);
	
	public void nextlvl();
	
	public int getMalwaresThisLevel();
	
}
