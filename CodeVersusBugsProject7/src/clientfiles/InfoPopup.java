package clientfiles;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * A pop up with some info. Based in AWT, not Swing.
 * 
 * @author Adel Hassan
 *
 */
public class InfoPopup
{
	private String title;
	private String info1;
	private String info2;
	private String info3;
	private int x;
	private int y;
	private boolean visible;
	private Rectangle okBounds;
	
	public InfoPopup(int x, int y)
	{
		title = "";
		info1 = "";
		info2 = "";
		info3 = "";
		visible = false;
		this.x = x;
		this.y = y;
		okBounds = new Rectangle(0, 0, 0, 0);
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setInfo1(String info1) {
		this.info1 = info1;
	}

	public void setInfo2(String info2) {
		this.info2 = info2;
	}

	public void setInfo3(String info3) {
		this.info3 = info3;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible()
	{
		return visible;
	}
	
	public Rectangle getOKBounds()
	{
		return okBounds;
	}

	public void draw(Graphics g)
	{	
		//freeze strings
		String info1 = this.info1;
		String info2 = this.info2;
		String info3 = this.info3;
		
		//border
		int width = Math.min(300, 15 * Math.max(Math.max(info1.length(), info2.length()), info3.length()));
		int height = 20 * (3 + info1.length()/30 + info2.length()/30 + info3.length()/30) + 60;
		g.setColor(Color.GRAY);
		g.fillRect(x, y, width, height);
		g.setColor(Color.BLACK);
		g.fillRect(x+5, y+5, width-10, height-10);
		
		//info
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.BOLD, 20));
		g.drawString(title, x+10, y+25);
		g.setFont(new Font("Monospaced", Font.PLAIN, 15));
		
		int yToSet = y + 45;
		try{
			while(info1.length() > 30)
			{
				//draw first part
				g.drawString(info1.substring(0, 30), x+10, yToSet);
				info1 = info1.substring(30, info1.length());
				yToSet += 20;
			}
			g.drawString(info1.substring(0, info1.length()), x+10, yToSet);
			yToSet += 20;
			
			while(info2.length() > 30)
			{
				//draw first part
				g.drawString(info2.substring(0, 30), x+10, yToSet);
				info2 = info2.substring(30, info2.length());
				yToSet += 20;
			}
			g.drawString(info2.substring(0, info2.length()), x+10, yToSet);
			yToSet += 20;
			
			while(info3.length() > 30)
			{
				//draw first part
				g.drawString(info3.substring(0, 30), x+10, yToSet);
				info3 = info3.substring(30, info3.length());
				yToSet += 20;
			}
			g.drawString(info3.substring(0, info3.length()), x+10, yToSet);
		}catch(Exception e){
			System.err.println("error");
			e.printStackTrace();
			System.exit(1);
		}
		
		//ok button
		g.setColor(Color.GRAY);
		g.fillRect(x+10, y + height-25, 50, 20);
		okBounds = new Rectangle(x+10, y + height - 25, 50, 20);
		g.setColor(Color.BLACK);
		g.drawString("OK", x+25, y + height-10);
	}
}