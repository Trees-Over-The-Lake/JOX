package tic_tac_toe; 

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Grid;
import input.MouseInput;
import network.Network;

public class TicTacToe {

	BufferedImage background;
	Grid gameGrid;
	
	PlayerType currPlayer = PlayerType.Circle;
	
	Font labelFont;
	FontMetrics metrics;
	int labelX;
	int labelY;
	
	Network connection = new Network();
	
	public TicTacToe() {
		
		
		labelFont = new Font("Century", Font.BOLD, 24);
		
		try { this.background = ImageIO.read(new File("res/paper_texture.jpg")); } 
		catch (IOException e) { e.printStackTrace(); }
		
		int gameGridWidth = MainGameLoop.get_width() * MainGameLoop.get_scale() - 100;
		
		int gameGridX = (int)(MainGameLoop.get_width() * MainGameLoop.get_scale() * 0.1);
		int gameGridY = (int)(MainGameLoop.get_height() * MainGameLoop.get_scale() * 0.1) + 30;
		
		this.gameGrid = new Grid(gameGridX,gameGridY,gameGridWidth);
	}
	
	public void tick() {
		
		int mx = MouseInput.get_x();
		int my = MouseInput.get_y();
		
		if (MouseInput.is_right_button_clicked() && this.gameGrid.isColidding(MouseInput.get_mouse_entity())) {
			
			boolean board_was_marked = this.gameGrid.mark_board(currPlayer,mx,my);
			
			if (!board_was_marked) 
				return;
			
			if (currPlayer == PlayerType.Circle) 
				currPlayer = PlayerType.Cross;
			else 
				currPlayer = PlayerType.Circle;
			
		} 
		
		if (gameGrid.winner != PlayerType.None) {
			System.out.println("We have a Winner! " + gameGrid.winner);
		}
		
		if (gameGrid.game_ended_with_tie()) {
			System.out.println("Game ended with a tie");
		}
	}

	public void render(Graphics g) {
		
		g.drawImage(background, 0,0,null);
		gameGrid.render(g);
		render_current_player_text(g);
		
	}
	
	public void render_current_player_text(Graphics g) {
		
		
		String label = "Current Player: " + currPlayer;
		
		g.setFont(labelFont);
		metrics = g.getFontMetrics(labelFont);
		int x = (MainGameLoop.get_width() * MainGameLoop.get_scale() - metrics.stringWidth(label)) / 2;
	    int y = (int)((MainGameLoop.get_height()- metrics.getHeight()) * 0.05 + metrics.getAscent());
		g.drawString(label,x, y);
	}
}
