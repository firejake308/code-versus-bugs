package clientfiles;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class ShopPanel extends JPanel implements ActionListener
{
	public static TowerType towerToPlace = TowerType.NONE;
	private JButton buyDiscThrower;
	private JButton buyNumberGenerator;
	private JButton buyScanner;
	
	public static JLabel info = new JLabel("");
	public static boolean warned = false;
	public static boolean mouseOnTrack = false;
	
	private Icon dtImage = DiscThrower.icon;
	private Icon ngImage = NumberGenerator.icon;
	//private Icon scImage;
	
	public static int timer=0;
	
	public ShopPanel()
	{
		buyDiscThrower = new JButton(dtImage);
		buyNumberGenerator = new JButton(ngImage);
		buyScanner = new JButton();
		
		info.setBorder(BorderFactory.createLineBorder(Color.black));
		info.setOpaque(true);
		info.setBounds(5, 10, 75, 55);
		//info.setEditable(false);
		buyDiscThrower.setBounds(20, 75, 50, 50);
		buyNumberGenerator.setBounds(20, 150, 50, 50);
		
		buyDiscThrower.addActionListener(this);
		buyNumberGenerator.addActionListener(this);
		
		setLayout(null);
		add(info);
		add(buyDiscThrower);
		add(buyNumberGenerator);
		
		buyDiscThrower.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				if (!warned)
				{
            		info.setText("$"+DiscThrower.cost);
            		if (Game.money < DiscThrower.cost)
            			info.setBackground(Color.red);
            		else
                		info.setBackground(Color.white);
            	}
			}
		});
		
		buyNumberGenerator.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				if (!warned)
				{
            		info.setText("$60");
            		if (Game.money < NumberGenerator.cost)
            			info.setBackground(Color.red);
            		else
            			info.setBackground(Color.white);
            	}
			}
		});
	}
	
	public static void changeInfo(String text, boolean warning)
	{
		// sets text
		info.setText("<html><div style=\"text-align: center;\">"+text+"</html>");
		
		// if it is a warning message set background red
		if (warning)
		{	
			warned = true;
			info.setBackground(Color.red);
		}
		timer = 120;
		
	}
	
	public static boolean checkPlacement()
	{
		int x = GamePanel.getMouseX();
		int y = GamePanel.getMouseY();
		int offset = 0;
		int centerOfOtherTowerX;
		int centerOfOtherTowerY;
		int radiusOfOtherTower;
		
		if (mouseOnTrack)
			return false;
		
		/* 
		 * Reason for iterations:
		 * 
		 * 0 - because tower could be twice the size of the track?
		 * 1 - testing 1 side
		 * 2 - testing the other side
		*/
		for (int i = 0; i < 3; i++)
		{
			// 1st iteration
			if(i == 0)
				offset = 0;
			
			// second iteration
			if(i == 1 && towerToPlace == TowerType.DISC_THROWER)
				offset = 25;
			else if(i == 1 && towerToPlace == TowerType.NUMBER_GENERATOR)
				offset = 25;
			
			// 3rd iteration
			else if(i == 2 && towerToPlace == TowerType.DISC_THROWER)
				offset = -25;
			else if(i == 2 && towerToPlace == TowerType.NUMBER_GENERATOR)
				offset = -25;
			
			// vertical top far left
			if(y < Game.heightOfGamePanel / 4 && x + offset < Game.widthOfGamePanel / 14 + Game.widthOfGamePanel / 42 && x + offset > Game.widthOfGamePanel / 14)
			{
				return false;
			}
			
			// vertical top 2nd to left
			else if(y < Game.heightOfGamePanel / 4 && x + offset < Game.widthOfGamePanel / 4 + Game.widthOfGamePanel / 42 && x + offset > Game.widthOfGamePanel / 4)
			{
				return false;
			}
			
			// vertical top 3rd left
			else if(y < Game.heightOfGamePanel / 4 && x + offset < (int) (Game.widthOfGamePanel * .4) + Game.widthOfGamePanel / 42 && x + offset > (int) (Game.widthOfGamePanel * .4))
			{
				return false;
			}
			
			// horizontal, connects top 3, left side
			else if(y + offset < Game.heightOfGamePanel / 4 - (Game.widthOfGamePanel / 42) + Game.widthOfGamePanel / 42 &&  y + offset > Game.heightOfGamePanel / 4 - (Game.widthOfGamePanel / 42) && x < Game.widthOfGamePanel / 14 + (int) (Game.widthOfGamePanel * .35) && x > Game.widthOfGamePanel / 14)
			{
				return false;
			}
			
			// vertical, stems from above, left side
			else if(y < Game.heightOfGamePanel / 4 - (Game.widthOfGamePanel / 42) + (int) (Game.heightOfGamePanel * .4) &&  y > Game.heightOfGamePanel / 4 - (Game.widthOfGamePanel / 42) && x + offset < Game.widthOfGamePanel / 5 + Game.widthOfGamePanel / 42 && x + offset > Game.widthOfGamePanel / 5)
			{
				return false;
			}
			
			// horizontal, connects to above, left side
			else if(y + offset < (Game.heightOfGamePanel / 4) + (int) (Game.heightOfGamePanel * .4) - (2 * Game.widthOfGamePanel / 42) + Game.widthOfGamePanel / 42 &&  y + offset > (Game.heightOfGamePanel / 4) + (int) (Game.heightOfGamePanel * .4) - (2 * Game.widthOfGamePanel / 42) && x < Game.widthOfGamePanel / 5 + Game.widthOfGamePanel / 5 && x > Game.widthOfGamePanel / 5)
			{
				return false;
			}
			
			// vertical, connects left, left side
			else if(y < (int) (Game.heightOfGamePanel * .4) + (Game.heightOfGamePanel / 4) + (int) (Game.heightOfGamePanel * .4) - (2 * Game.widthOfGamePanel / 42) - (int) (Game.heightOfGamePanel * .4) + Game.widthOfGamePanel / 42 &&  y > (int) (Game.heightOfGamePanel * .4) && x + offset < (int) (Game.widthOfGamePanel * .4) + Game.widthOfGamePanel / 42 && x + offset > (int) (Game.widthOfGamePanel * .4))
			{
				return false;
			}
			
			// horizontal, connects left, left side
			else if(y + offset < (int) (Game.heightOfGamePanel * .4) + Game.widthOfGamePanel / 42 &&  y + offset > (int) (Game.heightOfGamePanel * .4) && x < (int) (Game.widthOfGamePanel * .4) + Game.widthOfGamePanel / 5 && x > (int) (Game.widthOfGamePanel * .4))
			{
				return false;
			}
			
			// vertical, connects left and right (midway), both sides
			else if(y < (int) (Game.heightOfGamePanel * .4) + Game.heightOfGamePanel / 2 &&  y > (int) (Game.heightOfGamePanel * .4) && x + offset < (int) (Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 42) + Game.widthOfGamePanel / 42 && x + offset > (int) (Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 42))
			{
				return false;
			}
			
			// horizontal, connects right, both sides
			else if(y + offset < (int) (Game.heightOfGamePanel * .4 + Game.heightOfGamePanel / 2) - (Game.widthOfGamePanel / 42) + Game.widthOfGamePanel / 42 &&  y + offset > (int) (Game.heightOfGamePanel * .4 + Game.heightOfGamePanel / 2) - (Game.widthOfGamePanel / 42) && x < (int) (Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 42) - (int) (Game.widthOfGamePanel * .3) + (int) (Game.widthOfGamePanel * .3) + Game.widthOfGamePanel / 42 && x > (int) (Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 42) - (int) (Game.widthOfGamePanel * .3))
			{
				return false;
			}
			
			// vertical, connects right, last piece
			else if(y > (int) (Game.heightOfGamePanel * .4 + Game.heightOfGamePanel / 2) - (Game.widthOfGamePanel / 42) && x + offset < (int) (Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 42) - (int) (Game.widthOfGamePanel * .3) + Game.widthOfGamePanel / 42 && x + offset > (int) (Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 42) - (int) (Game.widthOfGamePanel * .3))
			{
				return false;
			}
			
			// vertical, 4th from left, top(right)
			else if(y < (int) (Game.heightOfGamePanel * .4) && x + offset < (int) (Game.widthOfGamePanel * .65) + Game.widthOfGamePanel / 42 && x + offset > (int) (Game.widthOfGamePanel * .65))
			{
				return false;
			}
			
			// vertical, 5th from left(last), top right
			else if(y < (int) (Game.heightOfGamePanel * .4) && x + offset < (int) (Game.widthOfGamePanel * .85) + Game.widthOfGamePanel / 42 && x + offset > (int) (Game.widthOfGamePanel * .85))
			{
				return false;
			}
			
			// horizontal, connects top 2 on right side, right side
			else if(y + offset < (int) (Game.heightOfGamePanel * .4) - (Game.widthOfGamePanel / 42) + Game.widthOfGamePanel / 42 &&  y + offset > (int) (Game.heightOfGamePanel * .4) - (Game.widthOfGamePanel / 42) && x < (int) (Game.widthOfGamePanel * .65) + Game.widthOfGamePanel / 5 && x > (int) (Game.widthOfGamePanel * .65))
			{
				return false;
			}
			
			// vertical, connects top, right side
			else if(y < (int) (Game.heightOfGamePanel * .4) - (Game.widthOfGamePanel / 42) + (int) (Game.heightOfGamePanel * .3) &&  y > (int) (Game.heightOfGamePanel * .4) - (Game.widthOfGamePanel / 42) && x + offset < (int) (Game.widthOfGamePanel * .8) + Game.widthOfGamePanel / 42 && x + offset > (int) (Game.widthOfGamePanel * .8))
			{
				return false;
			}
			
			// horizontal, connects right, last on right side
			else if(y + offset < (int) (Game.heightOfGamePanel * .4) + (int) (Game.heightOfGamePanel * .3) - (Game.widthOfGamePanel / 42) + Game.widthOfGamePanel / 42 &&  y + offset > (int) (Game.heightOfGamePanel * .4) + (int) (Game.heightOfGamePanel * .3) - (Game.widthOfGamePanel / 42) && x < (int) (Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 42) + (int) (Game.widthOfGamePanel * .8) - ((int) (Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 42) + (Game.widthOfGamePanel / 42)) + (2 * Game.widthOfGamePanel / 42) && x > (int) (Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 42))
			{
				return false;
			}
			
			/**CORNERS**/
			
			// check corner of 1st on top left
			else if(y < Game.heightOfGamePanel / 4 + offset && y > Game.heightOfGamePanel / 4 - Game.widthOfGamePanel / 42 + offset && x < Game.widthOfGamePanel / 14 + Game.widthOfGamePanel / 42 - offset && x > Game.widthOfGamePanel / 14 - offset)
			{
				return false;
			}
			
			// check corner of 3rd on top left
			else if(y < Game.heightOfGamePanel / 4 + offset && y > Game.heightOfGamePanel / 4 - Game.widthOfGamePanel / 42 + offset && x < (int) (Game.widthOfGamePanel * .4) + Game.widthOfGamePanel / 42 + offset && x > (int) (Game.widthOfGamePanel * .4) + offset)
			{
				return false;
			}
			
			/* check corner of 2 vertical on left side (not top row)
			else if(y < Game.heightOfGamePanel / 4 - (Game.widthOfGamePanel / 42) + (int) (Game.heightOfGamePanel * .4) + Game.widthOfGamePanel / 42 + offset && y > Game.heightOfGamePanel / 4 - (Game.widthOfGamePanel / 42) + (int) (Game.heightOfGamePanel * .4) + offset && x < Game.widthOfGamePanel / 5 - offset && x > Game.widthOfGamePanel / 5 - Game.widthOfGamePanel / 42 - offset)
			{
				return false;
			}
			
			// check corner opposite of above (left side)
			else if(y < Game.heightOfGamePanel / 4 - (Game.widthOfGamePanel / 42) + (int) (Game.heightOfGamePanel * .4) + Game.widthOfGamePanel / 42 + offset && y > Game.heightOfGamePanel / 4 - (Game.widthOfGamePanel / 42) + (int) (Game.heightOfGamePanel * .4) + offset && x < (int) (Game.widthOfGamePanel * .4) + Game.widthOfGamePanel / 42 + Game.widthOfGamePanel / 42 && x > (int) (Game.widthOfGamePanel * .4) + Game.widthOfGamePanel / 42)
			{
				return false;
			}
			
			// check corner above line converging both sides, left corner
			else if(y < (int) (Game.heightOfGamePanel * .4) + Game.widthOfGamePanel / 42 + offset && y > (int) (Game.heightOfGamePanel * .4) + offset && x < (int) (Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) + offset && x > (int) (Game.widthOfGamePanel * .4) + (Game.widthOfGamePanel / 5) - (Game.widthOfGamePanel / 42) + offset)
			{
				return false;
			}
			
			// check corner above line converging both sides, right corner
			else if(y < (int) (Game.heightOfGamePanel * .4) + Game.widthOfGamePanel / 42 + offset && y > (int) (Game.heightOfGamePanel * .4) + offset && x < (int) (Game.widthOfGamePanel * .4) + Game.widthOfGamePanel / 5 + Game.widthOfGamePanel / 42 + offset && x > (int) (Game.widthOfGamePanel * .4) + Game.widthOfGamePanel / 5 + offset)
			{
				return false;
			}
			
			// check corner directly below line converging both sides, both
			else if(y < 699 + offset && y > 667 + offset && x < 736 + offset && x > 707 + offset)
			{
				return false;
			}
			
			// check last corner
			else if(y < 699 - offset && y > 667 - offset && x < 337 - offset && x > 304 - offset)
			{
				return false;
			}
			
			// check corner connecting 4th on left side, top
			else if(y < 332 + offset && y > 300 + offset && x < 919 - offset && x > 885 - offset)
			{
				return false;
			}
			
			// check corner connecting last column to left on top
			else if(y < 332 + offset && y > 300 + offset && x < 1161 + offset && x > 1129 + offset)
			{
				return false;
			}
			
			// last corner on right side before merging
			else if(y < 524 + offset && y > 492 + offset && x < 1098 + offset && x > 1066 + offset)
			{
				return false;
			}*/
			
			/**For checking proximity to other towers*/
			for (int j = 0; j < Tower.allTowers.length; j++)
			{
				if (Tower.allTowers[j] == null)
					break;
				
				// get centers of other towers
				centerOfOtherTowerX = Tower.allTowers[j].getCenterX();
				centerOfOtherTowerY = Tower.allTowers[j].getCenterY();
				radiusOfOtherTower = Tower.allTowers[j].getRadius();
				
				// check top left and bottom right corners
				if (x + offset >= centerOfOtherTowerX - radiusOfOtherTower && x + offset <= centerOfOtherTowerX + radiusOfOtherTower && y + offset >= centerOfOtherTowerY - radiusOfOtherTower && y + offset <= centerOfOtherTowerY + radiusOfOtherTower)
					return false;
				// check top right and bottom left corners
				else if (x + offset >= centerOfOtherTowerX - radiusOfOtherTower && x + offset <= centerOfOtherTowerX + radiusOfOtherTower && y - offset >= centerOfOtherTowerY - radiusOfOtherTower && y - offset <= centerOfOtherTowerY + radiusOfOtherTower)
					return false;
			}
		}
		
		return true;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton temp = (JButton) e.getSource();
		TowerType towerType = TowerType.NONE;
		
		if (temp == buyDiscThrower)
		{
			towerType = TowerType.DISC_THROWER;
			if (towerToPlace == TowerType.DISC_THROWER)
			{
				towerToPlace = TowerType.NONE;
				return;
			}
			
			changeInfo("Disc Thrower Selected",false);
		}
		else if(temp == buyNumberGenerator)
		{
			towerType = TowerType.NUMBER_GENERATOR;
			if (towerToPlace == TowerType.NUMBER_GENERATOR)
			{
				towerToPlace = TowerType.NONE;
				return;
			}
			
			changeInfo(" Number Generator Selected",false);
		}
		else if (temp == buyScanner)
		{
			towerType = TowerType.SCANNER;
			if (towerToPlace == TowerType.SCANNER)
			{
				towerToPlace = TowerType.NONE;
				return;
			}
			
			changeInfo(" Scanner Selected",false);
		}
		
		validateBuy(towerType);
	}
	
	public static void validateBuy(TowerType type)
	{
		if (type == TowerType.DISC_THROWER)
		{
			if(Game.money >= DiscThrower.cost)
			{
				towerToPlace = TowerType.DISC_THROWER;
			}
			else
				changeInfo("Need $!", true);
		}
		
		else if(type == TowerType.NUMBER_GENERATOR)
		{
			if(Game.money >= NumberGenerator.cost)
			{
				towerToPlace = TowerType.NUMBER_GENERATOR;
			}
			else
				changeInfo("Need $!", true);
		}
		
		else if(type == TowerType.SCANNER)
		{}
	}
	
	public void paintComponent(Graphics g)
	{
		
	}
}
