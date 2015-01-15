package clientfiles;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javafx.scene.control.ScrollBar;

import javax.swing.*;

/**
 * Creates a help menu in order to avoid the need for a tutorial
 * 
 * @author Patrick
 *
 */

public class Help extends JFrame
{
	private JTextArea helpMenu;
	private JScrollPane scroll;
	
	/**
	 * Makes a help menu.
	 */
	public Help()
	{	
		//get content pane and let me put stuff where I want
		Container contentPane = getContentPane();
		contentPane.setLayout(null);
		contentPane.setBackground(Color.BLACK);
		
		helpMenu = new JTextArea("HELP:\n\n\n"
								+ "Malwares:\n\n"
								+ "Minions:\n"
								+ "Minions are low level malware that persist throughout the game. There are 3 versions: regular/blue (weakest), small/green (fast), large/red (slow and more health)\n\n"
								+ "Viruses:\n"
								+ "Viruses are a later game malware that replicate when they hit a packet or file. There are 3 versions of viruses, like above.\n\n"
								+ "Trojans:\n"
								+ "Trojans come from the bottom file path and are invisible to anti-malware devices until they are either scanned or they run into an upgraded encryptor\n\n"
								+ "Spywares:\n"
								+ "Spywares are late game and attempt to steal money (10%) by retrievng a file and returning back to the start. If they don't find a file, they'll be happy to damage the CPU instead.\n\n"
								+ "Worms:\n"
								+ "Worms are late game malware that have a lot of health aand can fire back, causing infections. Infected devices are far less effective. Its orange \"tail\" indicates its health.\n\n"
								+ "Bots:\n"
								+ "Bots are end game malwares that have lots of health, move quickly and spawn malwares whenever hit by projectiles\n\n\n\n"
								+ "Anti-Malware Devices:\n\n"
								+ "Disc Thrower:\n"
								+ "Disc Throwers are early game towers that are played throughout the game; hey throw discs with code to destroy malwares, upgrade-able\n\n"
								+ "Number Generator:\n"
								+ "Number Generators are also early game and are quite effective; they freeze malware and can be upgraded to deal damage and do splash effects\n\n"
								+ "Scanner:\n"
								+ "Scanners ");
		helpMenu.setEditable(false);
		helpMenu.setLineWrap(true);
		helpMenu.setWrapStyleWord(true);
		helpMenu.setBackground(Color.black);
		helpMenu.setForeground(new Color(0, 162, 232));
		helpMenu.setFont(new Font("Monospaced", Font.PLAIN, 15));
		helpMenu.setCaretPosition(0);
		helpMenu.setBounds(50, 50, 400, 400);
		
		//scrollbar stuff
		scroll = new JScrollPane(helpMenu);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(50, 50, 400, 400);
		contentPane.add(scroll);
		
		//format the frame
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setTitle("Help");
		setSize(500, 500);
		setLocationRelativeTo(Game.gf);
		setAlwaysOnTop(true);
		setResizable(false);
	}
}
