package entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import tic_tac_toe.Player;

public class Grid extends Entity{
	
	private final static int GRID_SIZE = 3;
	
	private final static Color DEFAULT_SERVER_COLOR = Color.blue;
	private final static Color DEFAULT_CLIENT_COLOR = Color.red;
	
	private final static int DEFAULT_STROKE = 10;
	
	int square_size = 10;
	int stroke;
	
	Entity board[];

	Color board_color = Color.red;
	
	public Grid(int x,int y, int width) {
		super(x, y, width, width);
		this.stroke      = DEFAULT_STROKE;
		this.square_size = width / GRID_SIZE - stroke;
		this.board = new Entity[GRID_SIZE * GRID_SIZE];
	}
	
	public boolean mark_board(Player currPlayer, int x,int y) {
		
		boolean board_marked = false;
		
		int x_index = calculate_board_index(this.x,x);
		int y_index = calculate_board_index(this.y,y);
		
		if (x_index == -1 || y_index == -1)
			return board_marked;
		
		if (this.board[x_index + (y_index * GRID_SIZE)] != null)
			return board_marked;
		
		board_marked = true;
		
		int player_x = calculate_board_position(this.x,x_index);
		int player_y = calculate_board_position(this.y,y_index);
		
		if ( currPlayer == Player.Circle )
			this.board[x_index + (y_index * GRID_SIZE)] = new Circle(player_x, player_y, DEFAULT_SERVER_COLOR);
		else
			this.board[x_index + (y_index * GRID_SIZE)] = new Cross(player_x, player_y, DEFAULT_CLIENT_COLOR);
		
		return board_marked;
	}
	
	private int calculate_board_index(int offset, int axis) {
		int index = -1;
		
		if ( axis > offset && axis < square_size + offset)
			index = 0;
		else if ( axis > square_size + stroke + offset && axis < (square_size*2 + stroke*2) + offset )
			index = 1;
		else if ( axis > (square_size*2 + stroke*2) + offset )
			index = 2;
		
		return index;
	}
	
	private int calculate_board_position(int axis, int axis_index) {
		return axis + (axis_index*stroke + axis_index*square_size);
	}
	
	public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setStroke(new BasicStroke(stroke));
		g2.setColor(Color.red);
		
		g2.drawLine(x,y + square_size,x + width,y + square_size);
		g2.drawLine(x,y + square_size*2 + stroke*2,x + width,y + square_size*2 + stroke*2);
		
		g2.drawLine(x+square_size,y,x + square_size,y + height);
		g2.drawLine(x+ square_size*2 + stroke*2 ,y,x + square_size*2 + stroke*2,y +height);
		
		for (Entity player : this.board) {
			if ( player != null )
				player.render(g);
		}
	}

}
