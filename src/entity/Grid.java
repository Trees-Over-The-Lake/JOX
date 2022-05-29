package entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Grid extends Entity{

	int square_size;
	int stroke;
	
	public Grid(int x,int y, int width, int stroke) {
		super(x, y, width, width);
		this.stroke      = stroke;
		this.square_size = width / 3 - stroke;
	}
	
	public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setStroke(new BasicStroke(stroke));
		g2.setColor(Color.red);
		
		g2.drawLine(x,y + square_size,x + width,y + square_size);
		g2.drawLine(x,y + square_size*2 + stroke*2,x + width,y + square_size*2 + stroke*2);
		
		g2.drawLine(x+square_size,y,x + square_size,y + height);
		g2.drawLine(x+ square_size*2 + stroke*2 ,y,x + square_size*2 + stroke*2,y +height);
	}

}
