package entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Cross extends Entity{

	public Cross(int x,int y,int width, int height) {
		super(x,y,width, height);
		// TODO Auto-generated constructor stub
	}
	
	public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(10));
		g2.setColor(Color.red);
		g2.drawLine(x, y, x + width, y + height);
		g2.drawLine(x, y + height, x+width, y);
	}
}
