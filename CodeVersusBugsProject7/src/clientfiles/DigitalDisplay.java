package clientfiles;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.*;

@SuppressWarnings("serial")
public class DigitalDisplay extends JPanel
{
	private static final BufferedImage dim = MyImages.dim;
	private static final BufferedImage glow = MyImages.glow;
	
	private int[] digits;
	private BufferedImage leftTop;
	private BufferedImage leftBot;
	private BufferedImage rightTop;
	private BufferedImage rightBot;
	private BufferedImage top;
	private BufferedImage bot;
	private BufferedImage mid;
	
	public DigitalDisplay(int numDigits) 
	{
		super();
		
		int[] defaultDisplay = {8,8,8,8,8};
		setDisplay(defaultDisplay);
		
		setBackground(new Color(255, 255, 255));
		setVisible(true);
		setOpaque(true);
		setSize(new Dimension(305,  100));
		System.out.println("size = "+getSize());
		
		addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				System.out.println("mouse moved");
			}
		});
	}
	
	public void setDisplay(int[] d)
	{
		digits = d;
	}
	
	public void paintComponent(Graphics g)
	{
		for(int i = 0; i < digits.length; i++)
		{
			//paint 1st digit
			switch(digits[i])
			{
				case 0:
					leftTop = glow;
					leftBot = glow;
					rightTop = glow;
					rightBot = glow;
					top = glow;
					bot = glow;
					mid = dim;
					break;
				case 1:
					leftTop = dim;
					leftBot = dim;
					rightTop = glow;
					rightBot = glow;
					top = dim;
					mid = dim;
					bot = dim;
					break;
				case 2:
					leftTop = dim;
					leftBot = glow;
					rightTop = glow;
					rightBot = dim;
					top = glow;
					mid = glow;
					bot = glow;
					break;
				case 3:
					leftTop = dim;
					leftBot = dim;
					rightTop = glow;
					rightBot = glow;
					top = glow;
					mid = glow;
					bot = glow;
					break;
				case 4:
					leftTop = glow;
					leftBot = dim;
					rightTop = glow;
					rightBot = glow;
					top = dim;
					mid = glow;
					bot = dim;
					break;
				case 5:
					leftTop = glow;
					leftBot = dim;
					rightTop = dim;
					rightBot = glow;
					top = glow;
					mid = glow;
					bot = glow;
					break;
				case 6:
					leftTop = glow;
					leftBot = glow;
					rightTop = dim;
					rightBot = glow;
					top = glow;
					mid = glow;
					bot = glow;
					break;
				case 7:
					leftTop = dim;
					leftBot = dim;
					rightTop = glow;
					rightBot = glow;
					top = glow;
					mid = dim;
					bot = dim;
					break;
				case 8:
					leftTop = glow;
					leftBot = glow;
					rightTop = glow;
					rightBot = glow;
					top = glow;
					mid = glow;
					bot = glow;
					break;
				case 9:
					leftTop = glow;
					leftBot = dim;
					rightTop = glow;
					rightBot = glow;
					top = glow;
					mid = glow;
					bot = glow;
					break;
			}
			
			//now draw images
			Graphics2D g2d = (Graphics2D) g;
			AffineTransform at = new AffineTransform();
			at.translate((i+1)*30, 10);
			g2d.drawImage(leftTop, at, null);
			
			at = new AffineTransform();
			at.translate((i+1)*30, 30);
			g2d.drawImage(leftBot, at, null);
			
			at = new AffineTransform();
			at.translate(i*30, 30);
			at.rotate(-Math.PI/2, i*30, 30);
			g2d.drawImage(top, at, null);
		}
	}
}
