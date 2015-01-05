
package clientfiles;

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

import javax.swing.*;
/**TechPanel.java
 * This class handles universal upgrades in-between rounds.
 * @author Adel Hassan
 * 
 * UPDATE LOG
 * 11/25/14:
 * 		class created
 * 11/29/14:
 * 		upgrading damage now affects future towers
 * 
 * ----------------------------------------------------------------------
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
public class TechPanel extends JPanel implements ActionListener, Serializable
{
	//width and height of the panel for compatibility across multiple devices
	int width;
	int height; 
	
	//buttons for the upgrades
	private JButton damage;
	private JButton speed;
	private JButton range;
	private JButton lives;
	private JButton buffer;
	private JButton router;
	private JButton slow;
	private JButton money;
	private JButton moneyMult;
	
	//text for tutorial
	private JTextArea tutorial;
	
	//number of points spent in each area
	private int attackPoints;
	private int defensePoints;
	private int supportPoints;
	public int pointsToSpend;
	
	//number of points spent on each upgrade
	private int damagePoints;
	private int speedPoints;
	private int rangePoints;
	private int bufferPoints;
	private int livesPoints;
	private int routerPoints;
	private int slowPoints;
	private int moneyPoints;
	private int moneyMultPoints;
	
	public TechPanel()
	{
		attackPoints = 0;
		defensePoints = 0;
		supportPoints = 0;
		pointsToSpend = 0;
		
		damagePoints = 0;
		speedPoints = 0;
		rangePoints = 0;
		bufferPoints = 0;
		livesPoints = 0;
		routerPoints = 0;
		slowPoints = 0;
		moneyPoints = 0;
		moneyMultPoints = 0;
		
		//enable escape to bring up quit dialog
		addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        		{
        			Game.pauseButton.setText("");
        			Game.pauseButton.setIcon(PauseButtonListener.sprite);
        			
        			Game.gameState = Game.PAUSED;
        			
        			//show dialog to quit or resume
        			Object[] options = {"Resume", "Quit"};
        			int choice = JOptionPane.showOptionDialog(Game.gf.getContentPane(), "Game Paused", "Pause", JOptionPane.DEFAULT_OPTION, 
        					JOptionPane.PLAIN_MESSAGE, PauseButtonListener.sprite, options, options[0]);
        			
        			if(choice == 0)
        			{
        				//if escaped between rounds, go back to pause
        				if (Game.endOfRound == true)
            			{
        					Game.pauseButton.setIcon(PauseButtonListener.sprite);
                			Game.gameState = Game.PAUSED;
            			}
        				//otherwise, go back to playing
        				else
        				{
        					Game.pauseButton.setIcon(PauseButtonListener.pausedSprite);
            				Game.gameState = Game.PLAYING;
        				}
        			}
        			else if(choice == 1)
        			{
        				System.exit(0);
        			}
        		}
			}
		});
	}
	/**
	 * All GUI initializing goes in this pseudo-constructor
	 * 
	 */
	public void initializeTechPanel()
	{
		//print width and height for debugging
		width = getWidth();
		height = getHeight();
		System.out.println("(w, h): "+width+" "+height);
		
		//we want to place our own buttons as we please
		setLayout(null);
		
		//initialize the buttons
		damage = new JButton("<html><div style = \"text-align:center\">Damage<br>"+damagePoints+"/5"+"</html>");
		speed = new JButton("<html><div style = \"text-align:center\">Attack Speed<br>"+speedPoints+"/5"+"</html>");
		range = new JButton("<html><div style = \"text-align:center\">Tower Range<br>"+rangePoints+"/5"+"</html>");
		buffer = new JButton("<html><div style = \"text-align:center\">Buffer Upgrade<br>"+bufferPoints+"/5"+"</html>");
		lives = new JButton("<html><div style = \"text-align:center\">Lives<br>"+livesPoints+"/5"+"</html>");
		router = new JButton("<html><div style = \"text-align:center\">Router<br>"+routerPoints+"/5"+"</html>");
		slow = new JButton("<html><div style = \"text-align:center\">Slow Malware<br>"+slowPoints+"/5"+"</html>");
		money = new JButton("<html><div style = \"text-align:center\">Money<br>"+moneyPoints+"/5"+"</html>");
		moneyMult = new JButton("<html><div style = \"text-align:center\">Money Multiplier<br>"+moneyMultPoints+"/5"+"</html>");
		
		//set bounds using fractions of width and height*
		damage.setBounds(width/6-80, 25, 160, 50);
		speed.setBounds(width/6-80, 50 + 35, 160, 50);
		range.setBounds(width/6-80, 2*50 + 45, 160, 50);
		
		buffer.setBounds(width/2-80, 25, 160, 50);
		lives.setBounds(width/2-80, 50 + 35, 160, 50);
		router.setBounds(width/2-80, 2*50 + 45, 160, 50);
		
		slow.setBounds(5*width/6-80, 25, 160, 50);
		money.setBounds(5*width/6-80, 50 + 35, 160, 50);
		moneyMult.setBounds(5*width/6-80, 2*50 + 45, 160, 50);
		
		//set images
		Image buttonOpen = MyImages.buttonOpen;
		Image buttonClosed = MyImages.buttonClosed;
		damage.setIcon(new ImageIcon(buttonOpen));
		speed.setIcon(new ImageIcon(buttonOpen));
		range.setIcon(new ImageIcon(buttonOpen));
		buffer.setIcon(new ImageIcon(buttonOpen));
		lives.setIcon(new ImageIcon(buttonOpen));
		router.setIcon(new ImageIcon(buttonOpen));
		slow.setIcon(new ImageIcon(buttonOpen));
		money.setIcon(new ImageIcon(buttonOpen));
		moneyMult.setIcon(new ImageIcon(buttonOpen));
		
		damage.setRolloverIcon(new ImageIcon(buttonClosed));
		speed.setRolloverIcon(new ImageIcon(buttonClosed));
		range.setRolloverIcon(new ImageIcon(buttonClosed));
		lives.setRolloverIcon(new ImageIcon(buttonClosed));
		buffer.setRolloverIcon(new ImageIcon(buttonClosed));
		router.setRolloverIcon(new ImageIcon(buttonClosed));
		slow.setRolloverIcon(new ImageIcon(buttonClosed));
		money.setRolloverIcon(new ImageIcon(buttonClosed));
		moneyMult.setRolloverIcon(new ImageIcon(buttonClosed));
		
		damage.setBorder(null);
		speed.setBorder(null);
		range.setBorder(null);
		lives.setBorder(null);
		buffer.setBorder(null);
		router.setBorder(null);
		slow.setBorder(null);
		money.setBorder(null);
		moneyMult.setBorder(null);
		
		damage.setHorizontalTextPosition(SwingConstants.CENTER);
		speed.setHorizontalTextPosition(SwingConstants.CENTER);
		range.setHorizontalTextPosition(SwingConstants.CENTER);
		lives.setHorizontalTextPosition(SwingConstants.CENTER);
		buffer.setHorizontalTextPosition(SwingConstants.CENTER);
		router.setHorizontalTextPosition(SwingConstants.CENTER);
		slow.setHorizontalTextPosition(SwingConstants.CENTER);
		money.setHorizontalTextPosition(SwingConstants.CENTER);
		moneyMult.setHorizontalTextPosition(SwingConstants.CENTER);
		
		damage.setVerticalTextPosition(SwingConstants.CENTER);
		speed.setVerticalTextPosition(SwingConstants.CENTER);
		range.setVerticalTextPosition(SwingConstants.CENTER);
		lives.setVerticalTextPosition(SwingConstants.CENTER);
		buffer.setVerticalTextPosition(SwingConstants.CENTER);
		router.setVerticalTextPosition(SwingConstants.CENTER);
		slow.setVerticalTextPosition(SwingConstants.CENTER);
		money.setVerticalTextPosition(SwingConstants.CENTER);
		moneyMult.setVerticalTextPosition(SwingConstants.CENTER);
		
		damage.setForeground(new Color(153, 217, 234));
		speed.setForeground(new Color(153, 217, 234));
		range.setForeground(new Color(153, 217, 234));
		lives.setForeground(new Color(153, 217, 234));
		buffer.setForeground(new Color(153, 217, 234));
		router.setForeground(new Color(153, 217, 234));
		slow.setForeground(new Color(153, 217, 234));
		money.setForeground(new Color(153, 217, 234));
		moneyMult.setForeground(new Color(153, 217, 234));
		
		damage.setFont(new Font("Monospaced", Font.PLAIN, 15));
		speed.setFont(new Font("Monospaced", Font.PLAIN, 15));
		range.setFont(new Font("Monospaced", Font.PLAIN, 15));
		lives.setFont(new Font("Monospaced", Font.PLAIN, 15));
		buffer.setFont(new Font("Monospaced", Font.PLAIN, 15));
		router.setFont(new Font("Monospaced", Font.PLAIN, 15));
		slow.setFont(new Font("Monospaced", Font.PLAIN, 15));
		money.setFont(new Font("Monospaced", Font.PLAIN, 15));
		moneyMult.setFont(new Font("Monospaced", Font.PLAIN, 15));
		
		//set descriptions
		damage.setToolTipText("Increases the damage on all Disc Throwers and Scanners. Disc Throwers get 5 extra damage, "
				+ "and Scanners get 0.5 damage per tick.");
		speed.setToolTipText("Increases the attack speed of Disc Throwers and Number Generators.");
		range.setToolTipText("Increases the range of your towers by 10%.");
		buffer.setToolTipText("Increases the size of your towers' buffers, making them more resistant to worm attacks."
				+ " This allows each tower to take 1 more hit.");
		lives.setToolTipText("Boost your uncorrupted data by 1000 bytes.");
		router.setToolTipText("Upgrade your modem to a router so that it can connect to the decoy CPU. Malwares that hit the decoy CPU"
				+ " do not corrupt any important data.");
		slow.setToolTipText("Slow down all future viruses by 3%.");
		money.setToolTipText("Boost your money by $100");
		moneyMult.setToolTipText("Earn 50% more money per kill.");
		
		//add action listeners
		damage.addActionListener(this);
		speed.addActionListener(this);
		range.addActionListener(this);
		
		buffer.addActionListener(this);
		lives.addActionListener(this);
		router.addActionListener(this);
		
		slow.addActionListener(this);
		money.addActionListener(this);
		moneyMult.addActionListener(this);
		
		//add buttons to panel
		add(damage);
		add(speed);
		add(range);
		
		add(buffer);
		add(lives);
		add(router);
		
		add(slow);
		add(money);
		add(moneyMult);
		
		//start all of the non-primary buttons out as disabled
		speed.setEnabled(false);
		range.setEnabled(false);
		lives.setEnabled(false);
		router.setEnabled(false);
		money.setEnabled(false);
		moneyMult.setEnabled(false);
		
		//tutorialstuff
		tutorial = new JTextArea();
		tutorial.setBounds(0, height/2, width, height/4);
		tutorial.setEditable(false);
		tutorial.setLineWrap(true);
		tutorial.setWrapStyleWord(true);
		tutorial.setText("Welcome to the Hardware Store! Here we sell all kinds of "
				+ "stats upgrades for your towers. At the end of each round, "
				+ "you can get 1 upgrade for free! As you spend more points in an "
				+ "area, you'll unlock more upgrades in that field. Go ahead and "
				+ "spend a point to move on to the next round.");
		tutorial.setBackground(new Color(158, 216, 255, 175));
		tutorial.setOpaque(true);
		tutorial.setFont(new Font("Monospaced", Font.PLAIN, 20));
		
		if(Game.tutorial)
			add(tutorial);
	}
	
	@Override
	public void setVisible(boolean aFlag)
	{
		if(aFlag == true)
		{
			//set visible like normal
			super.setVisible(true);
			
			//make the buttons clickable again if user can spend more points on them
				if(damagePoints < 5)
					damage.setEnabled(true);
				if(attackPoints >= 3 && speedPoints < 5)
					speed.setEnabled(true);
				if(attackPoints >= 3 && rangePoints < 5)
					range.setEnabled(true);
				
				if(bufferPoints < 5)
					buffer.setEnabled(true);
				if(defensePoints >= 3 && livesPoints < 5)
					lives.setEnabled(true);
				if(defensePoints >= 6 && routerPoints < 5)
					router.setEnabled(true);
				
				if(slowPoints < 5)
					slow.setEnabled(true);
				if(supportPoints > 3 && moneyPoints < 5)
					money.setEnabled(true);
				if(supportPoints > 10 && moneyMultPoints < 1)
					moneyMult.setEnabled(true);
			
			//special case for tutorial slide 28
			//force user to slide 29 once tech panel appears for the first time
			if(Game.tutorialSlide <= 28)
				Game.tutorialSlide = 29;
		}
		else if(aFlag == false)
		{
			//set invisible like normal
			super.setVisible(false);
			
			//also, enable save/load
			Game.saveButton.setEnabled(true);
			Game.loadButton.setEnabled(true);
			
			if(Game.tutorial && Game.level == 2)
			{
				Game.savedSlide = Game.tutorialSlide;
				Game.tutorialSlide = 399;
				Game.gamePanel.nextSlide();
			}
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton clicked = (JButton) e.getSource();
		pointsToSpend--;
		
		if(clicked == damage)
		{
			damagePoints++;
			attackPoints++;
			
			//special case for tutorial slide 29
			if(Game.tutorialSlide == 29 && Game.tutorial)
				Game.gamePanel.nextSlide();
			
			//increase damage of pre-existing towers
			for(int t = 0; t < Tower.allTowers.length; t++)
			{
				Tower curr = Tower.allTowers[t];
				if(curr == null)
					break;
				else if(curr.getType() == TowerType.DISC_THROWER)
				{
					curr.damage += 5;
				}
				else if(curr.getType() == TowerType.NUMBER_GENERATOR)
				{
					curr.damage += 1;
				}
				else if(curr.getType() == TowerType.SCANNER)
				{
					curr.damage += .5;
				}
			}
			
			//update damage for future towers
			DiscThrower.increaseDamage(5);
			NumberGenerator.increaseDamage(1);
		}
		
		else if(clicked == speed)
		{
			speedPoints++;
			attackPoints++;
			
			//reduce timer reset on all pre-existing towers by 3/60ths of a second
			for(int t=0; t < Tower.allTowers.length; t++)
			{
				Tower curr = Tower.allTowers[t];
				if(curr == null)
					break;
				else if(curr.getType() == TowerType.DISC_THROWER || curr.getType() == TowerType.NUMBER_GENERATOR)
				{
					curr.timerReset -= 3; //only decreases timer reset on dt's and num gens by 3
				}
			}
			
			DiscThrower.speedToSet -= 3;
			NumberGenerator.speedToSet -= 3;
		}
		else if(clicked == range)
		{
			rangePoints++;
			attackPoints++;
			
			//reduce timer reset on all pre-existing towers by 3/60ths of a second
			for(int t=0; t < Tower.allTowers.length; t++)
			{
				Tower curr = Tower.allTowers[t];
				if(curr == null)
					break;
				else if(curr instanceof DiscThrower || curr instanceof NumberGenerator || curr instanceof Scanner)
				{
					curr.range *= 1.1;
				}
			}
			
			DiscThrower.rangeToSet *= 1.1;
			NumberGenerator.rangeToSet *= 1.1;
			Scanner.rangeToSet *= 1.1;
		}
		else if (clicked == buffer)
		{
			//update point values
			bufferPoints++;
			defensePoints++;
			
			//special case for tutorial slide 29
			if(Game.tutorialSlide == 29 && Game.tutorial)
				Game.gamePanel.nextSlide();
			
			//increase health of existing towers
			for(int t=0; t < Tower.allTowers.length; t++)
			{
				Tower curr = Tower.allTowers[t];
				if(curr == null)
					break;
				else if(curr.getType() != TowerType.FIREWALL)
				{
					curr.health += 10;
				}
			}
			
			//increase health of all future towers
			Tower.increaseHealth(10);
		}
		else if(clicked == lives)
		{
			livesPoints++;
			defensePoints++;
			
			//add 1000 bytes
			Game.lives += 1000;
			Game.gf.life.setText("Bytes Remaining: " + Game.lives);
		}
		else if(clicked == router)
		{
			//update point values
			routerPoints++;
			defensePoints++;
			
			//turn on router
			Malware.routerOn = true;
			//image is changed in game panel
		}
		else if(clicked == slow)
		{
			//update point values
			slowPoints++;
			supportPoints++;
			
			//special case for tutorial slide 29
			if(Game.tutorialSlide == 29 && Game.tutorial)
				Game.gamePanel.nextSlide();
			
			//unleash the slow
			Game.malwareSpeed -= 0.03;
		}
		else if(clicked == money)
		{
			moneyPoints++;
			supportPoints++;
			
			Game.addMoney(100);
		}
		else if(clicked == moneyMult)
		{
			moneyMultPoints++;
			supportPoints++;
			
			Game.moneyMultiplier = 1.5;
		}
		
		//update point values
		damage.setText("<html><div style = \"text-align:center\">Damage<br>"+damagePoints+"/5"+"</html>");
		speed.setText("<html><div style = \"text-align:center\">Attack Speed<br>"+speedPoints+"/5"+"</html>");
		range.setText("<html><div style = \"text-align:center\">Tower Range<br>"+rangePoints+"/5"+"</html>");
		
		buffer.setText("<html><div style = \"text-align:center\">Buffer Upgrade<br>"+bufferPoints+"/5"+"</html>");
		lives.setText("<html><div style = \"text-align:center\">Lives<br>"+livesPoints+"/5"+"</html>");
		router.setText("<html><div style = \"text-align:center\">Router<br>"+routerPoints+"/5"+"</html>");
		
		slow.setText("<html><div style = \"text-align:center\">Slow Malware<br>"+slowPoints+"/5"+"</html>");
		money.setText("<html><div style = \"text-align:center\">Money<br>"+moneyPoints+"/5"+"</html>");
		moneyMult.setText("<html><div style = \"text-align:center\">Money Multiplier<br>"+moneyMultPoints+"/5"+"</html>");
		
		//disable all buttons after a point is spent
		if(pointsToSpend == 0)
		{
			damage.setEnabled(false);
			speed.setEnabled(false);
			range.setEnabled(false);
			
			buffer.setEnabled(false);
			lives.setEnabled(false);
			router.setEnabled(false);
			
			slow.setEnabled(false);
			money.setEnabled(false);
			moneyMult.setEnabled(false);
		}
		
		setVisible(false);
		Game.gamePanel.setVisible(true);
	}
	public void disableTutorial()
	{
		tutorial.setVisible(false);
	}
	public void paintComponent(Graphics g)
	{
		//attack
		g.setColor(Color.red);
		g.fillRect(0, 0, width/3, height);
		//defense
		g.setColor(Color.blue);
		g.fillRect(width/3, 0, width/3, height);
		//utility
		g.setColor(new Color(73, 227, 122));
		g.fillRect(2*width/3, 0, width/3, height);
		
		//strings
		g.setColor(Color.black);
		g.setFont(new Font("myfont", Font.BOLD, 20));
		g.drawString("ATTACK", 10, height-20);
		g.drawString("DEFENSE", width/3+10, height-20);
		g.drawString("SUPPORT", 2*width/3+10, height-20);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
	}
}
