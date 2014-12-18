package clientfiles;

public interface LevelManager 
{
	/**spawn a number of viruses by initializing x number of viruses in the allMalware array
	 * 
	 */
	
	
	// extra methods removed to get rid of errors, replaced by:
	public void addMalwares();
	
	public void setMalwaresForLevel(int numOfMinions, int numOfFastMinions, int numOfSlowMinions, int numOfWorms);
	
	public void nextlvl();
	
	public int getMalwaresThisLevel();
	
}
