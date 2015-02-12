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
								+ "Scanners are antivirus scanners that constantly rotate. Scanners can quarantine threats like worms, making them benign. They deal area-of-effect damage.\n\n"
								+ "Firewall:\n"
								+ "Firewalls can destroy simple malwares like viruses and minions in one hit, but they can only kill about 10 per round.\n\n"
								+ "Encryptors:\n"
								+ "Encryptors can encrypt files and can reveal Trojans, but they can only encrypt about 10 per round.\n\n"
								+ "Communications Tower:\n"
								+ "Communications towers are support systems that buff nearby systems and can pass upgrades if they are upgraded to an information hub\n\n"
								+ "FAST\n"
								+ "Futuristic Advanced Shield Throwers are advanced anti-virus softwares that are extremely powerful\n\n\n\n"
								+ "In-Game Content:\n\n"
								+ "CPU:\n"
								+ "Malwares will attempt to reach this location in order to damage your computer, causing a loss of bytes until system collapse at 0\n\n"
								+ "Decoy CPU:\n"
								+ "Some malwares will get routed to this CPU when you install a router from the hardware store. No bytes are lost upon being reached.\n\n"
								+ "Modem:\n"
								+ "Modem is short for MOdulator-DEModulator. All this means is that it downloads data from the Internet and turns it into stuff your computer can understand. In this case, that data is malware.If you add a router (available in the hardware store) some of the malware will be routed to the decoy CPU.");
		
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
