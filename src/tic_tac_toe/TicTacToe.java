package tic_tac_toe; 

import java.awt.Color;
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
	
	public TicTacToe() {
		
		try {
			this.background = ImageIO.read(new File("res/paper_texture.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int gameGridWidth = MainGameLoop.get_width() * MainGameLoop.get_scale() - 100;
		
		this.gameGrid = new Grid(50,80,gameGridWidth, 10);
	}
	
	public void tick() {
		System.out.println(MouseInput.data());
	}
	
	public void render(Graphics g) {
		
		g.drawImage(background, 0,0,null);
		gameGrid.render(g);
	}
}
