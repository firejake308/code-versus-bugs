package clientfiles;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
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
	private JLabel title = new JLabel("Code Versus Bugs");
	private JButton start = new JButton("NEW GAME");
	private JButton credits = new JButton("CREDITS");
	private JButton options = new JButton("OPTIONS");
	private JButton exit = new JButton("EXIT");
	private JTextArea creditsText = new JTextArea("");
	private JScrollPane scroll = new JScrollPane(creditsText);
	private JCheckBox tutorial = new JCheckBox("Tutorial", true);
	private JButton back = new JButton("Back");
	private Container c;
	
	public StartMenu() 
	{
		c = getContentPane();
		c.setLayout(null);
		
		setSize(400,400);
		setIconImage(MyImages.miniMinion);
		setLocationRelativeTo(null);
		
		title.setBounds(145,50,110,50);
		c.add(title);
		
		start.setBounds(140,100,120,50);
		start.addActionListener(this);
		c.add(start);
		
		credits.setBounds(140, 160, 120, 50);
		credits.addActionListener(this);
		c.add(credits);
		
		options.setBounds(140,220,120,50);
		options.addActionListener(this);
		c.add(options);
		
		exit.setBounds(140, 280, 120, 50);
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
			setVisible(false);
			Game.initializeGame();
		}
		
		else if (temp == credits)
		{
			start.setVisible(false);
			credits.setVisible(false);
			options.setVisible(false);
			exit.setVisible(false);
			title.setVisible(false);
			
	        //scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	        //scroll.setBounds(140, 50, 20, 200);
	        
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
	        creditsText.setBounds(140, 50, 120, 200);
	        //creditsText.add(new Scrollbar(Scrollbar.VERTICAL));
	        
	        //c.add(scroll);
			c.add(creditsText);
			
			back.setBounds(140, 275, 120, 50);
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
			exit.setVisible(false);
			title.setVisible(false);
			
			//make the tutorial visible
			tutorial.setBounds(140, 50, 120, 200);
			tutorial.addItemListener(this);
			tutorial.setVisible(true);
			c.add(tutorial);
			
			//turn on the back button
			back.setBounds(140, 275, 120, 50);
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
			back.setVisible(false);
			scroll.setVisible(false);
			tutorial.setVisible(false);
			
			title.setVisible(true);
			start.setVisible(true);
			credits.setVisible(true);
			options.setVisible(true);
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
		}
		else if(state == ItemEvent.SELECTED)
			Game.tutorial = true;
	}
}
