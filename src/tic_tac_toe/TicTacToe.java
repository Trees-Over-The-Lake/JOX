package tic_tac_toe; 

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Grid;
import input.MouseInput;

public class TicTacToe {

	BufferedImage background;
	Grid gameGrid;
	
	Player currPlayer = Player.Circle;
	
	public TicTacToe() {
		
		try { this.background = ImageIO.read(new File("res/paper_texture.jpg")); } 
		catch (IOException e) { e.printStackTrace(); }
		
		int gameGridWidth = MainGameLoop.get_width() * MainGameLoop.get_scale() - 100;
		
		int gameGridX = (int)(MainGameLoop.get_width() * MainGameLoop.get_scale() * 0.1);
		int gameGridY = (int)(MainGameLoop.get_height() * MainGameLoop.get_scale() * 0.1);
		
		this.gameGrid = new Grid(gameGridX,gameGridY,gameGridWidth);
	}
	
	public void tick() {
		
		int mx = MouseInput.get_x();
		int my = MouseInput.get_y();
		
		if (MouseInput.is_right_button_clicked() && this.gameGrid.isColidding(MouseInput.get_mouse_entity())) {
			
			System.out.println(mx + " " + my);
			
			boolean board_was_marked = this.gameGrid.mark_board(currPlayer,mx,my);
			
			if (!board_was_marked) 
				return;
			
			if (currPlayer == Player.Circle) 
				currPlayer = Player.Cross;
			else 
				currPlayer = Player.Circle;
		
			
		}
	}
	
	public void render(Graphics g) {
		
		g.drawImage(background, 0,0,null);
		gameGrid.render(g);
	}
}
