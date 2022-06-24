package entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import tic_tac_toe.PlayerType;

public class Grid extends Entity{
	
	private final static int GRID_SIZE = 3;
	
	private final static Color DEFAULT_SERVER_COLOR = Color.blue;
	private final static Color DEFAULT_CLIENT_COLOR = Color.red;
	
	private final static int DEFAULT_STROKE = 10;
	
	int square_size = 10;
	int stroke;
	
	Player board[];

	Color board_color = Color.red;
	
	public PlayerType winner = PlayerType.None;
	
	private int marked_squares = 0;
	
	private static final int[][] WINS = new int[][] { { 0, 1, 2 }, 
													{ 3, 4, 5 },
													{ 6, 7, 8 }, 
													{ 0, 3, 6 }, 
													{ 1, 4, 7 }, 
													{ 2, 5, 8 }, 
													{ 0, 4, 8 }, 
													{ 2, 4, 6 } };
													
	public int last_marked_board_index = -1;
	
	public Grid(int x,int y, int width) {
		super(x, y, width, width);
		this.stroke      = DEFAULT_STROKE;
		this.square_size = width / GRID_SIZE - stroke;
		this.board = new Player[GRID_SIZE * GRID_SIZE];
	}
	
	public boolean mark_board_with_positions(PlayerType currPlayer, int x,int y) {
		
		boolean board_marked = false;
		
		int x_index = calculate_board_index(this.x,x);
		int y_index = calculate_board_index(this.y,y);
		
		if (x_index == -1 || y_index == -1)
			return board_marked;
		
		if (this.board[x_index + (y_index * GRID_SIZE)] != null) {
			System.out.println("Posicao vazia");
			return board_marked;
		}
		
		board_marked = true;
		
		int player_x = calculate_board_position(this.x,x_index);
		int player_y = calculate_board_position(this.y,y_index);
		
		if ( currPlayer == PlayerType.Circle )
			this.board[x_index + (y_index * GRID_SIZE)] = new Circle(player_x, player_y, DEFAULT_SERVER_COLOR);
		else
			this.board[x_index + (y_index * GRID_SIZE)] = new Cross(player_x, player_y, DEFAULT_CLIENT_COLOR);
		
		marked_squares++;
		
		last_marked_board_index = x_index + (y_index * GRID_SIZE);
		
		winner = check_for_board_winner(PlayerType.Circle);
		
		if (winner == PlayerType.None)
			winner = check_for_board_winner(PlayerType.Cross);
		
		for(int i = 0 ; i < GRID_SIZE; i++) {
			for (int j = 0 ; j < GRID_SIZE; j++) {
				if (this.board[j + (i * GRID_SIZE)] == null) {
					System.out.print(" ");
				} else if (this.board[i + (j * GRID_SIZE)] instanceof Circle) {
					System.out.print("O");
				} else {
					System.out.print("X");
				}
			}
			System.out.println("");
		}
		
		return board_marked;
	}
	
	public boolean mark_board_with_index(PlayerType currPlayer, int board_index) {
		
		int board_x_index = board_index % GRID_SIZE;
		int board_y_index = board_index / GRID_SIZE;
		
		int player_x = calculate_board_position(this.x,board_x_index);
		int player_y = calculate_board_position(this.y,board_y_index);
		
		if ( currPlayer == PlayerType.Circle )
			this.board[board_x_index + (board_y_index * GRID_SIZE)] = new Circle(player_x, player_y, DEFAULT_SERVER_COLOR);
		else
			this.board[board_x_index + (board_y_index * GRID_SIZE)] = new Cross(player_x, player_y, DEFAULT_CLIENT_COLOR);
		
		marked_squares++;
		
		winner = check_for_board_winner(PlayerType.Circle);
		
		if (winner == PlayerType.None)
			winner = check_for_board_winner(PlayerType.Cross);
		
		last_marked_board_index = board_index;
		
		for(int i = 0 ; i < GRID_SIZE; i++) {
			for (int j = 0 ; j < GRID_SIZE; j++) {
				if (this.board[j + (i * GRID_SIZE)] == null) {
					System.out.print(" ");
				} else if (this.board[i + (j * GRID_SIZE)] instanceof Circle) {
					System.out.print("O");
				} else {
					System.out.print("X");
				}
			}
			System.out.println("");
		}
		
		return true;
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
	
	private PlayerType check_for_board_winner(PlayerType currPlayer) {
		
		for (int[] position : WINS) {
			
			if (board[position[0]] == null ||
				board[position[1]] == null || 
				board[position[2]] == null) {
				continue;
			}
			
			if (board[position[0]].playerEnum == currPlayer &&
				board[position[1]].playerEnum == currPlayer &&
				board[position[2]].playerEnum == currPlayer) {
				return currPlayer;
			}
		}
		
		return PlayerType.None;
	}
	
	private int calculate_board_position(int axis, int axis_index) {
		return axis + (axis_index*stroke + axis_index*square_size);
	}
	
	public boolean game_ended_with_tie() {
		return winner == PlayerType.None && marked_squares == GRID_SIZE * GRID_SIZE;
	}

	public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setStroke(new BasicStroke(stroke));
		g2.setColor(Color.red);
		
		g2.drawLine(x,y + square_size,x + width,y + square_size);
		g2.drawLine(x,y + square_size*2 + stroke*2,x + width,y + square_size*2 + stroke*2);
		
		g2.drawLine(x+square_size,y,x + square_size,y + height);
		g2.drawLine(x+ square_size*2 + stroke*2 ,y,x + square_size*2 + stroke*2,y +height);
		
		for (int i = 0 ; i < board.length; i++) {
			
			Entity currPlayer = board[i];
			if ( currPlayer != null ) {
				currPlayer.render(g);
			}
		}
	}

}
