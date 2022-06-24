package tic_tac_toe; 

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import entity.Grid;
import helpers.Timer;
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
	boolean gameBegin = false;
	boolean gameEnded = false;
	Timer gameEndedTimer;
	
	GameClient client;
	
	int enemyResponse = -1;
	boolean enemyResponded = false;
	
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
		
		if (gameGrid.winner != PlayerType.None) {
			
			gameEnded = true;
			if (gameEndedTimer == null) {
				gameEndedTimer = new Timer(60);
			} else {
				
				if(gameEndedTimer.is_stopped()) {
					

					 JOptionPane.showMessageDialog(MainGameLoop.frame,
		                   "We have a winner: " + gameGrid.winner  , 
		                   gameGrid.winner == yourPlayer ? "You Win" : "You Lose", 
		                   JOptionPane.INFORMATION_MESSAGE);
					 
					 System.exit(0);
				} else {
					gameEndedTimer.tick();
				}
			}
			
		}
		
		if (gameGrid.game_ended_with_tie()) {
			
			gameEnded = true;
			if (gameEndedTimer == null) {
				gameEndedTimer = new Timer(30);
			} else {
				
				if(gameEndedTimer.is_stopped()) {
					

					JOptionPane.showMessageDialog(MainGameLoop.frame,
			                   "Game ended with a tie", 
			                   "Game finished", 
			                   JOptionPane.INFORMATION_MESSAGE);
						 
						 System.exit(0);
				} else {
					gameEndedTimer.tick();
				}
			}
			 
		}	
	
		if (!gameEnded) {
			if (yourTurn) 
				playerTurn();
			
			else if (enemyResponded) {
				enemyTurn(enemyResponse);
				enemyResponded = false;
				enemyResponse = -1;
			}
		}
	}
	
	public boolean playerTurn() {
		
		boolean player_played = false;
		
		int mx = MouseInput.get_x();
		int my = MouseInput.get_y();
		
		if (MouseInput.is_right_button_clicked() && this.gameGrid.isColidding(MouseInput.get_mouse_entity())) {
			
			boolean board_was_marked = this.gameGrid.mark_board_with_positions(yourPlayer,mx,my);
			
			player_played = board_was_marked;
			
			System.out.println("Player " +  player_played);
			
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
			
			gameBegin = true;
			
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
			enemyResponded = true;
			enemyResponse = Integer.parseInt(msg);
		}
	}

	public void render(Graphics g) {
	
		g.drawImage(background, 0,0,null);
		render_current_player_text(g);
		gameGrid.render(g);
		
	}
	
	public void render_current_player_text(Graphics g) {
		
		String label = new String();
		
		if (!gameBegin)
			label = "Waiting for second player...";
		else 
			label = "Current Player: " + (yourTurn ? "You" : "Opponent") ;
		
		g.setFont(labelFont);
		metrics = g.getFontMetrics(labelFont);
		int x = (MainGameLoop.get_width() * MainGameLoop.get_scale() - metrics.stringWidth(label)) / 2;
	    int y = (int)((MainGameLoop.get_height()- metrics.getHeight()) * 0.05 + metrics.getAscent());
		g.drawString(label,x, y);
	}
}
