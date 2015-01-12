package clientfiles;

import javafx.scene.control.ScrollBar;

import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * Creates a help menu in order to avoid the need for a tutorial
 * 
 * @author Patrick
 *
 */

class Help extends JFrame
{
	public static JTextField helpMenu;
	public static ScrollBar scroll;
	
	public Help()
	{
		// 
		helpMenu = new JTextField("HELP:\n\n\n"
								+ "Malwares:\n\n"
								+ "Minions:\n"
								+ "Minions are low level malware taht persist throughout the game, there are 3 versions: regular (weakest), small (fast), large (slow and more health)\n\n"
								+ "Viruses:\n"
								+ "Viruses are a later game malware that replicate when they hit a packet or file, there are 3 versions like above\n\n"
								+ "Trojans:\n"
								+ "Trojans come from the bottom file path and are invisible to anti-malware devices until");
	}
}
