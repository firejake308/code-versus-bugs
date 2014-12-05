package clientfiles;

public class StartMenuCreator implements Runnable 
{

	public StartMenuCreator() 
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() 
	{
		Game.startMenu = new StartMenu();
		System.out.println("start menu created");
	}

}
