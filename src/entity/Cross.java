package entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import tic_tac_toe.PlayerType;

public class Cross extends Player{

	private final static int DEFAULT_RADIUS = 150;
	private final static int DEFAULT_STROKE = 10;
	
	Color color;
	int stroke_size;

	public Cross(int x, int y, Color color) {
		super(x, y, DEFAULT_RADIUS, DEFAULT_RADIUS);
		this.stroke_size = DEFAULT_STROKE;
		this.color = color;
		playerEnum = PlayerType.Cross;
	}
	
	public Cross(int x,int y, int width, Color color) {
		super(x, y, width, width);
		this.stroke_size = DEFAULT_STROKE;
		this.color = color;
	}
	
	public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(stroke_size));
		g2.setColor(color);
		g2.drawLine(x, y, x + width, y + height);
		g2.drawLine(x, y + height, x+width, y);
	}
}
