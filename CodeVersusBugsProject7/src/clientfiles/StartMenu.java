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
	private JLabel title;
	private JButton start;
	private JButton credits;
	private JButton options;
	private JButton instructions;
	private JButton exit;
	
	private JTextArea creditsText = new JTextArea("");
	private JScrollPane scroll = new JScrollPane(creditsText);
	private JCheckBox tutorial = new JCheckBox("Tutorial", true);
	private JTextArea instructionsText = new JTextArea();
	private JScrollPane instructionsScroll = new JScrollPane(instructionsText);
	private JButton back = new JButton("Back");
	private Container c;
	
	public StartMenu()
	{
		c = getContentPane();
		c.setLayout(null);
		
		setSize(400,500);
		setIconImage(MyImages.miniMinion);
		setLocationRelativeTo(null);
		
		title = new JLabel("Code Versus Bugs");
		title.setFont(new Font("Monospaced", Font.BOLD, 30));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setBounds(0,50,400,50);
		c.add(title);
		
		start = new JButton("NEW GAME");
		start.setFont(new Font("Monospaced", Font.PLAIN, 20));
		start.setBounds(120,100,160,50);
		start.addActionListener(this);
		c.add(start);
		
		credits = new JButton("CREDITS");
		credits.setFont(new Font("Monospaced", Font.PLAIN, 20));
		credits.setBounds(120, 160, 160, 50);
		credits.addActionListener(this);
		c.add(credits);
		
		options = new JButton("OPTIONS");
		options.setFont(new Font("Monospaced", Font.PLAIN, 20));
		options.setBounds(120, 220, 160, 50);
		options.addActionListener(this);
		c.add(options);
		
		instructions = new JButton("INSTRUCTIONS");
		instructions.setFont(new Font("Monospaced", Font.PLAIN, 20));
		instructions.setBounds(120, 280, 160, 50);
		instructions.addActionListener(this);
		c.add(instructions);
		
		exit = new JButton("EXIT");
		exit.setFont(new Font("Monospaced", Font.PLAIN, 20));
		exit.setBounds(120, 340, 160, 50);
		exit.addActionListener(this);
		c.add(exit);
		
		setVisible(true);
		setLocationRelativeTo(null);
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
			
	        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	        scroll.setBounds(120, 50, 160, 200);
	        
			creditsText.setVisible(true);
	        creditsText.setText("             Credits: \n\n"
					  		  + "          Developers:\n\n"
					  		  + "          Adel hassan\n"
					  		  + "        Patrick Kenney\n\n"
					  		  + "          Directors:\n\n"
					  		  + "         Adel hassan\n"
					  		  + "       Patrick Kenney\n\n"
					  		  + "         Programmers:\n\n"
					  		  + "         Adel hassan\n"
					  		  + "       Patrick Kenney\n\n"
					  		  + "          Visual Design:\n\n"
					  		  + "         Adel hassan\n"
					  		  + "       Patrick Kenney\n\n");
	
	        creditsText.setEditable(false);
	        creditsText.setBounds(120, 50, 160, 200);
	        
			c.add(scroll);
			
			back.setBounds(120, 275, 160, 50);
			back.addActionListener(this);
			back.setVisible(true);
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
			tutorial.setBounds(120, 50, 160, 200);
			tutorial.addItemListener(this);
			tutorial.setVisible(true);
			c.add(tutorial);
			
			//turn on the back button
			back.setBounds(120, 275, 160, 50);
			back.addActionListener(this);
			back.setVisible(true);
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
			instructionsText.setText("Welcome to Code Versus Bugs! In this game, you play as a computer virus tracker who fights"
					+ " malicious malware by placing epic towers.\n\n"
					+ "CONTROLS\n\n"
					+ "This game is played primarily with the mouse, but there are optional hotkeys you can use to speed"
					+ " things up. They are:\n"
					+ "ESCAPE:\tBrings up a dialog to quit the game.\n"
					+ "SPACE:\tWorks the same as clicking the \n\tpause/play button.\n"
					+ "D:\tPlaces a Disc Thrower tower, if you \n\thave the necessary funds.\n"
					+ "N:\tPlaces a Number Generator tower, if \n\tyou have the necessary funds.\n"
					+ "S:\tPlaces a Scanner tower, if you have the \tnecessary funds.\n\n"
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
					+ " inform and guide you. You may click a check box to move on to the next tutorial slide."
					);
			instructionsText.setEditable(false);
		    instructionsText.setBounds(40, 50, 320, 325);
		    instructionsText.setLineWrap(true);
		    instructionsText.setWrapStyleWord(true);
		    
		    instructionsScroll.setVisible(true);
		    instructionsScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	        instructionsScroll.setBounds(40, 50, 320, 325);
			c.add(instructionsScroll);
			
			//turn on the back button
			back.setBounds(120, 400, 160, 50);
			back.addActionListener(this);
			back.setVisible(true);
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
			creditsText.setVisible(false);
			instructionsText.setVisible(false);
			back.setVisible(false);
			scroll.setVisible(false);
			instructionsScroll.setVisible(false);
			tutorial.setVisible(false);
			
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
		if(state == ItemEvent.DESELECTED)
		{
			Game.tutorial = false;
			Game.tutorialSlide = 0;
			Game.gamePanel.disableTutorial();
		}
		else if(state == ItemEvent.SELECTED)
			Game.tutorial = true;
	}
}
