
package clientfiles;

import java.awt.*;
import java.awt.event.*;

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
public class TechPanel extends JPanel implements ActionListener
{
	//width and height of the panel for compatibility across multiple devices
	int width;
	int height; 
	
	//buttons for the upgrades
	private JButton damage;
	private JButton speed;
	private JButton lives;
	private JButton money;
	
	//number of points spent in each area
	private int attackPoints;
	private int defensePoints;
	private int supportPoints;
	
	//number of points spent on each upgrade
	private int damagePoints;
	private int speedPoints;
	private int livesPoints;
	private int moneyPoints;
	
	public TechPanel()
	{
		attackPoints = 0;
		defensePoints = 0;
		supportPoints = 0;
		
		damagePoints = 0;
		livesPoints = 0;
		moneyPoints = 0;
	}
	/**all GUI initializing goes in this pseudo-constructor
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
		speed= new JButton("<html><div style = \"text-align:center\">Speed<br>"+damagePoints+"/5"+"</html>");
		lives = new JButton("<html><div style = \"text-align:center\">Lives<br>"+livesPoints+"/5"+"</html>");
		money = new JButton("<html><div style = \"text-align:center\">Money<br>"+moneyPoints+"/5"+"</html>");
		
		//set bounds using fractions of width and height*
		damage.setBounds(width/6-50, 25, 100, height/15);
		speed.setBounds(width/6-50, height/15+35, 100, height/15);
		lives.setBounds(width/2-50, 25, 100, height/15);
		money.setBounds(5*width/6-50, 25,100, height/15);
		
		//add action listeners
		damage.addActionListener(this);
		speed.addActionListener(this);
		lives.addActionListener(this);
		money.addActionListener(this);
		
		//add buttons to panel
		add(damage);
		add(speed);
		add(lives);
		add(money);
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
			if(speedPoints < 5)
				speed.setEnabled(true);
			if(livesPoints < 5)
				lives.setEnabled(true);
			if(moneyPoints < 5)
				money.setEnabled(true);
			
			//special case for tutorial slide 28
			//force user to slide 29 once tech panel appears for the first time
			if(Game.tutorialSlide <= 28)
				Game.tutorialSlide = 29;
		}
		else if(aFlag == false)
			//set invisible like normal
			super.setVisible(false);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton clicked = (JButton) e.getSource();
		
		if(clicked == damage)
		{
			damagePoints++;
			attackPoints++;
			
			//increase damage of pre-existing towers by 5
			for(int t = 0; t < Tower.allTowers.length; t++)
			{
				Tower curr = Tower.allTowers[t];
				if(curr == null)
					break;
				else if(curr.getType() == TowerType.DISC_THROWER || curr.getType() == TowerType.SCANNER)
				{
					curr.damage += 5; //only increases damage on dt's and scanners by 5
				}
			}
			
			//update damage for future towers
			DiscThrower.increaseDamage(5);
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
				else if(curr.getType() == TowerType.DISC_THROWER || curr.getType() == TowerType.SCANNER)
				{
					curr.timerReset -= 3; //only decreases timer reset on dt's and scanners by 3
				}
			}
			
			DiscThrower.speedToSet -= 3;
			NumberGenerator.speedToSet -= 3;
		}
		
		else if(clicked == lives)
		{
			livesPoints++;
			defensePoints++;
			
			//add 20 lives
			Game.lives += 20;
			Game.gf.life.setText("Bytes Remaining: " + Game.lives);
		}
		
		else if(clicked == money)
		{
			moneyPoints++;
			supportPoints++;
			
			Game.addMoney(25);
		}
		
		//update point values
		damage.setText("<html><div style = \"text-align:center\">Damage<br>"+damagePoints+"/5"+"</html>");
		speed.setText("<html><div style = \"text-align:center\">Speed<br>"+speedPoints+"/5"+"</html>");
		lives.setText("<html><div style = \"text-align:center\">Lives<br>"+livesPoints+"/5"+"</html>");
		money.setText("<html><div style = \"text-align:center\">Money<br>"+moneyPoints+"/5"+"</html>");
		
		//disable all buttons after a point is spent
		damage.setEnabled(false);
		speed.setEnabled(false);
		lives.setEnabled(false);
		money.setEnabled(false);
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
	}
}
