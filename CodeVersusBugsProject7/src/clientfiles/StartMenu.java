package clientfiles;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
/**StartMenu.java
 * Creates a frame for the start menu.
 * 
 * @author Ahmadul
 * 
 * -----------------------------------------------------------------------
 * 
 * Copyright 2014 Adel Hassan and Patrick Kenney
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class StartMenu extends JFrame implements Runnable, ActionListener, ItemListener
{
	//front page stuff
	private JLabel title;
	private JButton start;
	private JButton credits;
	private JButton options;
	private JButton instructions;
	private JButton exit;
	private JLabel deco1;
	private JLabel deco2;
	
	//inside pages stuff
	private JTextArea creditsText = new JTextArea("");
	private JScrollPane scroll = new JScrollPane(creditsText);
	private JCheckBox tutorial = new JCheckBox("Tutorial", true);
	private JRadioButton storyMode = new JRadioButton("Story Mode", true);
	private JRadioButton freeplayMode = new JRadioButton("Freeplay", false);
	private ButtonGroup playMode = new ButtonGroup();
	private JCheckBox sound = new JCheckBox("Sound", true);
	private JTextArea instructionsText = new JTextArea();
	private JScrollPane instructionsScroll = new JScrollPane(instructionsText);
	private JButton back = new JButton(new ImageIcon(MyImages.backOpen));
	private Container c;
	
	public StartMenu()
	{
		c = getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(0,0,0));
		
		setSize(400,500);
		setIconImage(MyImages.miniMinion);
		setLocationRelativeTo(null);
		
		title = new JLabel("Code Versus Bugs");
		title.setForeground(new Color(0, 162, 232));
		title.setFont(new Font("Monospaced", Font.BOLD, 30));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setBounds(0,50,400,50);
		c.add(title);
		
		deco1 = new JLabel(new ImageIcon(MyImages.minion));
		deco1.setBounds(10, 390, 70, 70);
		c.add(deco1);
		
		deco2 = new JLabel(new ImageIcon(MyImages.minion));
		deco2.setBounds(310, 390, 70, 70);
		c.add(deco2);
		
		start = new JButton(new ImageIcon(MyImages.startOpen));
		start.setBounds(120,100,160,50);
		start.addActionListener(this);
		start.setRolloverIcon(new ImageIcon(MyImages.startClosed));
		start.setBorder(null);
		c.add(start);
		
		credits = new JButton(new ImageIcon(MyImages.creditsOpen));
		credits.setBounds(120, 160, 160, 50);
		credits.addActionListener(this);
		credits.setRolloverIcon(new ImageIcon(MyImages.creditsClosed));
		credits.setBorder(null);
		c.add(credits);
		
		options = new JButton(new ImageIcon(MyImages.optionsOpen));
		options.setBounds(120, 220, 160, 50);
		options.addActionListener(this);
		options.setRolloverIcon(new ImageIcon(MyImages.optionsClosed));
		options.setBorder(null);
		c.add(options);
		
		instructions = new JButton(new ImageIcon(MyImages.directionsOpen));
		instructions.setBounds(120, 280, 160, 50);
		instructions.addActionListener(this);
		instructions.setRolloverIcon(new ImageIcon(MyImages.directionsClosed));
		instructions.setBorder(null);
		c.add(instructions);
		
		exit = new JButton(new ImageIcon(MyImages.exitOpen));
		exit.setBounds(120, 340, 160, 50);
		exit.addActionListener(this);
		exit.setRolloverIcon(new ImageIcon(MyImages.exitClosed));
		exit.setBorder(null);
		c.add(exit);
		
		setVisible(true);
	}
	
	public void run()
	{
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton temp = (JButton) e.getSource();
		
		if (temp == start)
		{
			// changed to paused so it's more fluid
			Game.gameState = Game.PAUSED;
			Game.initializeGame();
			setVisible(false);
		}
		
		else if (temp == credits)
		{
			start.setVisible(false);
			credits.setVisible(false);
			options.setVisible(false);
			instructions.setVisible(false);
			exit.setVisible(false);
			title.setVisible(false);
			
			scroll.setVisible(true);
	        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	        scroll.setBounds(40, 50, 320, 325);
	        
			creditsText.setVisible(true);
			creditsText.setFont(new Font("Monospaced", Font.BOLD, 15));
	        creditsText.setText("             Credits: \n\n"
					  		  + "            Developers:\n\n"
					  		  + "            Adel hassan\n"
					  		  + "          Patrick Kenney\n\n"
					  		  + "            Directors:\n\n"
					  		  + "            Adel hassan\n"
					  		  + "          Patrick Kenney\n\n"
					  		  + "            Programmers:\n\n"
					  		  + "            Adel hassan\n"
					  		  + "          Patrick Kenney\n\n"
					  		  + "           Visual Design:\n\n"
					  		  + "            Adel hassan\n"
					  		  + "          Patrick Kenney\n\n"
					  		  + "            Specialists:\n\n"
					  		  + "            Adel hassan\n"
					  		  + "          Patrick Kenney\n\n"
					  		  + "          Creative Design:\n\n"
					  		  + "            Adel hassan\n"
					  		  + "          Patrick Kenney\n\n"
					  		  + "             Writers:\n\n"
					  		  + "            Adel hassan\n"
					  		  + "          Patrick Kenney\n\n");
	
	        creditsText.setEditable(false);
	        creditsText.setBounds(40, 50, 320, 325);
	        creditsText.setBackground(Color.black);
		    creditsText.setForeground(new Color(0, 162, 232));
	        
			c.add(scroll);
			
			//turn on the back button
			back.setBounds(120, 400, 160, 50);
			back.addActionListener(this);
			back.setVisible(true);
			back.setRolloverIcon(new ImageIcon(MyImages.backClosed));
			back.setBorder(null);
			c.add(back);
		}
		
		else if (temp == options)
		{
			//remove all buttons from screen
			start.setVisible(false);
			credits.setVisible(false);
			options.setVisible(false);
			instructions.setVisible(false);
			exit.setVisible(false);
			title.setVisible(false);
			
			//make the tutorial check box visible
			tutorial.setBounds(120, 50, 160, 50);
			tutorial.setBackground(Color.black);
			tutorial.setForeground(new Color(0, 162, 232));
			tutorial.setFont(new Font("Monospaced", Font.BOLD, 20));
			tutorial.setFocusable(false);
			tutorial.addItemListener(this);
			tutorial.setVisible(true);
			c.add(tutorial);
			
			//make mode options visible
			storyMode.setBounds(60, 100, 160, 50);
			storyMode.setBackground(Color.black);
			storyMode.setForeground(new Color(0, 162, 232));
			storyMode.setFont(new Font("Monospaced", Font.BOLD, 20));
			storyMode.setFocusable(false);
			storyMode.addItemListener(this);
			storyMode.setVisible(true);
			c.add(storyMode);
			
			//sound check box
			sound.setBounds(120, 150, 160, 50);
			sound.setBackground(Color.black);
			sound.setForeground(new Color(0, 162, 232));
			sound.setFont(new Font("Monospaced", Font.BOLD, 20));
			sound.setFocusable(false);
			sound.addItemListener(this);
			sound.setVisible(true);
			c.add(sound);
			
			freeplayMode.setBounds(220, 100, 160, 50);
			freeplayMode.setBackground(Color.black);
			freeplayMode.setForeground(new Color(0, 162, 232));
			freeplayMode.setFont(new Font("Monospaced", Font.BOLD, 20));
			freeplayMode.setFocusable(false);
			freeplayMode.addItemListener(this);
			freeplayMode.setVisible(true);
			c.add(freeplayMode);
			
			playMode.add(storyMode);
			playMode.add(freeplayMode);
			
			//turn on the back button
			back.setBounds(120, 400, 160, 50);
			back.addActionListener(this);
			back.setVisible(true);
			back.setRolloverIcon(new ImageIcon(MyImages.backClosed));
			back.setBorder(null);
			c.add(back);
		}
		
		else if (temp == instructions)
		{
			//remove all buttons from screen
			start.setVisible(false);
			credits.setVisible(false);
			options.setVisible(false);
			instructions.setVisible(false);
			exit.setVisible(false);
			title.setVisible(false);
			
			//make the instructions visible
			instructionsText.setVisible(true);
			instructionsText.setFont(new Font("Monospaced", Font.BOLD, 15));
			instructionsText.setText("Welcome to Code Versus Bugs! In this game, you play as a computer virus tracker who fights"
					+ " malicious malware by placing epic towers.\n\n"
					+ "CONTROLS\n\n"
					+ "This game is played primarily with the mouse, but there are optional hotkeys you can use to speed"
					+ " things up. They are:\n"
					+ "ESCAPE:\tBrings up a dialog to quit the game.\n"
					+ "SPACE:\tWorks the same as\n\tclicking the \n\tpause/play button.\n"
					+ "D:\tPlaces a Disc Thrower\n\ttower, if you have the\n\tnecessary funds.\n"
					+ "N:\tPlaces a Number Generator\n\ttower, if you have\n\tthe necessary funds.\n"
					+ "S:\tPlaces a Scanner tower, \n\tif you have the \n\tnecessary funds.\n\n"
					+ "GAMEPLAY\n\n"
					+ "Code Versus Bugs is a tower defense-style game. Malwares come in from the top of the map,"
					+ " then follow the track to the CPU. As a cybersecurity expert, your job is to place towers"
					+ " to prevent the malwares from getting to the CPU and corrupting your data. You may see the"
					+ " amount of data remaining in the CPU at any time in the top of the screen, where there is a"
					+ " statisticlabeled \"Bytes Remaining.\" Any malware that reaches the CPU will corrupt as many"
					+ " bytes as it has remaining health. Once there is no more uncorrupted data in the CPU, you"
					+ " lose the game.\n\n"
					+ "IN-GAME TUTORIAL\n\n"
					+ "There is an in-game tutorial that will guide you through your first playthrough of Code"
					+ " Versus Bugs. You may turn off the tutorial by going to Options from the start menu and"
					+ " deselecting the checkbox labeled \"Tutorial.\" The checkbox is selected by default. If you"
					+ " do wish to keep the in-game tutorial, several transluscent blue boxes will appear to"
					+ " inform and guide you. You may click the box to move on to the next tutorial slide.\n\n"
					+ "FREEPLAY MODE\n\n"
					+ "There is an option to play in either story mode or freeplay mode. The default is story mode,"
					+ " since this is recommended for beginners. Story mode features carefully structured levels and"
					+ " designed to teach new players about the game. Freeplay mode is designed for experienced"
					+ " players, since it features more hectic, randomly generated levels that get progressively"
					+ " harder. There is no tutorial for freeplay mode, so only veterans should venture into it.\n\n"
					+ "CONTROL BUTTONS\n\n"
					+ "There are several buttons in the top left corner of the screen. The largest of these is the play/pause"
					+ " button, which freezes the malwares but allows you to place towers or plan out the death of"
					+ " the malwares. This button is also used to start each round.\n"
					+ "The other ubttons, in order from top to bottom and left to right, are: Fast Forward, Save, Load,"
					+ " Help, Restart, and Quit.\n\n"
					+ "THE HELP MENU\n\n"
					+ "Pressing the button with the i in a circle will bring up a help menu. The help menu contains detailed"
					+ " information about gameplay, malware types, and tower types. Pulling up the help menu is a great way"
					+ " to learn more about malwares during a slow round since doing so doesn't pause the game."
					);
			instructionsText.setEditable(false);
		    instructionsText.setBounds(40, 50, 320, 325);
		    instructionsText.setLineWrap(true);
		    instructionsText.setWrapStyleWord(true);
		    instructionsText.setCaretPosition(0);
		    instructionsText.setBackground(Color.black);
		    instructionsText.setForeground(new Color(0, 162, 232));
		    
		    instructionsScroll.setVisible(true);
		    instructionsScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	        instructionsScroll.setBounds(40, 50, 320, 325);
			c.add(instructionsScroll);
			
			//turn on the back button
			back.setBounds(120, 400, 160, 50);
			back.addActionListener(this);
			back.setVisible(true);
			back.setRolloverIcon(new ImageIcon(MyImages.backClosed));
			back.setBorder(null);
			c.add(back);
		}
		
		else if (temp == exit)
		{
			Game.gameState = Game.OVER;
			setVisible(false);
			System.exit(0);
		}
		
		else if (temp == back)
		{
			//turn on inner page components
			creditsText.setVisible(false);
			instructionsText.setVisible(false);
			back.setVisible(false);
			scroll.setVisible(false);
			instructionsScroll.setVisible(false);
			tutorial.setVisible(false);
			storyMode.setVisible(false);
			freeplayMode.setVisible(false);
			sound.setVisible(false);
			
			//turn on main page components
			title.setVisible(true);
			start.setVisible(true);
			credits.setVisible(true);
			options.setVisible(true);
			instructions.setVisible(true);
			exit.setVisible(true);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		int state = e.getStateChange();
		
		//turn tutorial on/off
		if(e.getSource() == tutorial)
		{
			if(state == ItemEvent.DESELECTED)
			{
				Game.tutorialSlide = 0;
				Game.gamePanel.disableTutorial();
				Game.techPanel.disableTutorial();
			}
			else if(state == ItemEvent.SELECTED)
			{
				Game.gamePanel.enableTutorial();
				Game.techPanel.enableTutorial();
			}
		}
		
		//switch between story and freeplay
		if(e.getSource() == storyMode)
		{
			//enter story mode
			if(e.getStateChange() == ItemEvent.SELECTED)
			{
				System.out.println("enabled story mode");
				Game.gamePanel.enterStoryMode();
			}
			//enter free play mode
			else
			{
				System.out.println("enabled freeplay mode");
				Game.gamePanel.enterFreeplay();
				//tutorial doesn't work well with freeplay
				Game.gamePanel.disableTutorial();
				Game.techPanel.disableTutorial();
				tutorial.setSelected(false);
			}
		}
		
		//sound on/off
		if(e.getSource() == sound)
		{
			if(e.getStateChange() == ItemEvent.SELECTED)
				Game.soundOn = true;
			else
				Game.soundOn = false;
		}
	}
}
