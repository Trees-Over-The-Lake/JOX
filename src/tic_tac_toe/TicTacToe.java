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
import network.GameClient;
import network.GameServer;

public class TicTacToe {

	BufferedImage background;
	Grid gameGrid;
	
	PlayerType yourPlayer  = PlayerType.Circle;
	PlayerType enemyPlayer = PlayerType.Cross;
	
	Font labelFont;
	FontMetrics metrics;
	int labelX;
	int labelY;
	
	boolean yourTurn = false;
	
	GameClient client;
	
	public TicTacToe() {
		
		try {
			client = new GameClient();
			client.startServerCommunication();
			client.server_sended_message.connect(this, "process_server_input");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		labelFont = new Font("Century", Font.BOLD, 24);
		
		try { this.background = ImageIO.read(getClass().getResource("/paper_texture.jpg")); } 
		catch (IOException e) { e.printStackTrace(); }
		
		int gameGridWidth = MainGameLoop.get_width() * MainGameLoop.get_scale() - 100;
		
		int gameGridX = (int)(MainGameLoop.get_width() * MainGameLoop.get_scale() * 0.1);
		int gameGridY = (int)(MainGameLoop.get_height() * MainGameLoop.get_scale() * 0.1) + 30;
		
		this.gameGrid = new Grid(gameGridX,gameGridY,gameGridWidth);
		
	}
	
	public void tick() {
		
		if (yourTurn) 
			playerTurn();
		
		if (gameGrid.winner != PlayerType.None) {
			System.out.println("We have a Winner! " + gameGrid.winner);
		}
		
		if (gameGrid.game_ended_with_tie()) {
			System.out.println("Game ended with a tie");
		}
	}
	
	public boolean playerTurn() {
		
		boolean player_played = false;
		
		int mx = MouseInput.get_x();
		int my = MouseInput.get_y();
		
		if (MouseInput.is_right_button_clicked() && this.gameGrid.isColidding(MouseInput.get_mouse_entity())) {
			
			boolean board_was_marked = this.gameGrid.mark_board_with_positions(yourPlayer,mx,my);
			
			player_played = board_was_marked;
			
			if (!player_played) 
				return player_played;

			String board_index = "" + gameGrid.last_marked_board_index;
			
			client.sendData(board_index);
			gameGrid.last_marked_board_index = -1;
			
			yourTurn = false;
		} 
		
		return player_played;
	}
	
	public void enemyTurn(int board_index) {
		
		int response = board_index;
		
		if (response == -1) {
			return;
		}

		gameGrid.mark_board_with_index(enemyPlayer, response);
			
		yourTurn = true;
	}
	
	public void process_server_input(String msg) {
		
		System.out.println("Mensagem recebida do servidor: " + msg);
		
		if(msg.contains(GameServer.START_GAME)) {
			if (msg.contains(GameServer.FIRST_TURN)) {
				yourTurn = true;
			}
			else {
				yourPlayer  = PlayerType.Cross;
				enemyPlayer = PlayerType.Circle;
			}
			
		} else if(msg.contains(GameServer.SERVER_CLOSED)) {
			System.err.println("[ERROR!]: Server closed abruptly");
			System.exit(1);
		}
		else {
			enemyTurn(Integer.parseInt(msg));
		}
	}

	public void render(Graphics g) {
		
		//g.drawImage(background, 0,0,null);
		gameGrid.render(g);
		render_current_player_text(g);
		
	}
	
	public void render_current_player_text(Graphics g) {
		
		String label = "Current Player: " + (yourTurn ? yourPlayer : enemyPlayer) ;
		
		g.setFont(labelFont);
		metrics = g.getFontMetrics(labelFont);
		int x = (MainGameLoop.get_width() * MainGameLoop.get_scale() - metrics.stringWidth(label)) / 2;
	    int y = (int)((MainGameLoop.get_height()- metrics.getHeight()) * 0.05 + metrics.getAscent());
		g.drawString(label,x, y);
	}
}
