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
	private BufferedImage[] digitImages;
	private String text;
	
	/**
	 * Creates a digital display
	 * @param numDigits the number of digits to display
	 */
	public DigitalDisplay(int numDigits, String text) 
	{
		super();
		
		//int[] defaultDisplay = {8,8,8,8,8};
		setDisplay(new int[numDigits]);
		this.text = text;
		
		setVisible(true);
		setOpaque(true);
		setSize(new Dimension(305,  100));
	}
	
	public void setDisplay(int[] d)
	{
		digits = d;
		digitImages = new BufferedImage[d.length];
	}
	
	public void setDisplay(int num)
	{
		int display[] = new int[digits.length];
		int i = display.length-1;
		
		while (i >=0)
		{
			display[i] = num % 10;
			num /= 10;
			i--;
		}
		
		setDisplay(display);
	}
	public void paintComponent(Graphics g)
	{
		//forcibly draw background
		//g.setColor(Color.black);
		//g.fillRect(0, 0, getWidth(), getHeight());
		
		//paint each digit
		for(int i = 0; i < digits.length; i++)
		{
			switch(digits[i])
			{
				case 0:
					digitImages[i] = MyImages.r0;
					break;
				case 1:
					digitImages[i] = MyImages.r1;
					break;
				case 2:
					digitImages[i] = MyImages.r2;
					break;
				case 3:
					digitImages[i] = MyImages.r3;
					break;
				case 4:
					digitImages[i] = MyImages.r4;
					break;
				case 5:
					digitImages[i] = MyImages.r5;
					break;
				case 6:
					digitImages[i] = MyImages.r6;
					break;
				case 7:
					digitImages[i] = MyImages.r7;
					break;
				case 8:
					digitImages[i] = MyImages.r8;
					break;
				case 9:
					digitImages[i] = MyImages.r9;
					break;
			}
			
			//now draw images
			Graphics2D g2d = (Graphics2D) g;
			AffineTransform at = new AffineTransform();
			at.translate((i+1)*50, 20);
			g2d.drawImage(digitImages[i], at, null);
		}
		
		//draw text
		g.setFont(new Font("Monospaced", Font.BOLD, 25));
		g.setColor(new Color(153, 217, 234));
		g.drawString(text, getWidth()-20*text.length(), getHeight()-10);
	}
}
