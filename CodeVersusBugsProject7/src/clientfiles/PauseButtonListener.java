package clientfiles;
import java.awt.Image;
/*PauseButtonListener.java
 * This is the listener for the pause button of the game
 */
import java.awt.event.*;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class PauseButtonListener implements Runnable
{
	private GameFrame theGUI;
	public static ImageIcon sprite = new ImageIcon(MyImages.play);
	public static ImageIcon pausedSprite = new ImageIcon(MyImages.pause);
	
	public void run()
	{
		CodeVersusBugs.game.renderGameState();
	}
}
