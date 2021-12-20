package gamelogic;

import gameobjects.*;
import main.Menu;
import utils.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.event.MouseEvent;


/**This class represents the entire logic of the game while the game is being played.
 * 
 * @author Justin Chiang, Akshat Jain, Calix Tang
 *
 */
public class GameBoard extends PApplet{
	//Constants
	public static final float FPS = 120;
	public static final int[] DEFAULT_BOARD_SIZE = new int[] {3,3};
	public static final float DEFAULT_TILE_WIDTH = 75;
	
	//Class Fields
	
	//window fields - better to not go below (500,500) 
	private int width;
	private int height;

	//0 - unselected, 1 - time-based, 2 - fewest moves, 3 - practice, 4 - solved
	private int gameMode;
	private boolean play;
	
	private int movesUsed;
	private int moveMax;
	private float timeUsed;
	private float timeMax;
	
	
	private String numToMove;
	
	private Menu menu;
	
	private Board board;
	private Board goalBoard;
	
	private Button resetButton;
	private Button menuButton;
	private ArrayList<Button> buttons;
	//Constructors 
	
	
	/** A no-args constructor. 
	 * 
	 */
	public GameBoard() {
		this.width = 800; 
		this.height = 800;
		this.gameMode = 0;
		this.movesUsed = 0;
		this.timeUsed = 0;
		this.numToMove = "0";
	}
	
	public GameBoard(int width, int height) {
		this.width = width;
		this.height = height;
		//Since we don't have a control/start loop yet, I'm making it automatically start the puzzle.
		this.gameMode = 1;
		this.play = false;
		this.movesUsed = 0;
		this.timeUsed = 0;
		this.numToMove = "0";
		this.menu = new Menu(width, height);
		this.board = null;
		this.goalBoard = null;
		this.resetButton = new Button(width * 0.5f, height * 0.99f, 200, 50, new float[] {180, 180, 180}, new float[] {230, 230, 230}, "Reset", 7);
		this.menuButton = new Button(width * 0.85f, height * 0.90f, 200, 50, new float[] {180, 180, 180}, new float[] {230, 230, 230}, "Menu", 7);
		this.buttons = new ArrayList<Button>();
		this.buttons.add(this.resetButton);
		this.buttons.add(this.menuButton);
	}
	
	public GameBoard(int width, int height, int gameMode, int rows, int cols) {
		this.width = width;
		this.height = height;
		this.gameMode = gameMode;
		this.play = false;
		this.movesUsed = 0;
		this.timeUsed = 0;
		this.numToMove = "0";
		this.menu = new Menu(width, height);
		this.board = null;
		this.goalBoard = null;
		this.resetButton = new Button(width * 0.5f, height * 0.90f, 200, 50, new float[] {180, 180, 180}, new float[] {230, 230, 230}, "Reset", 7);
		this.menuButton = new Button(width * 0.85f, height * 0.90f, 200, 50, new float[] {180, 180, 180}, new float[] {230, 230, 230}, "Menu", 7);
		this.buttons = new ArrayList<Button>();
		this.buttons.add(this.resetButton);
		this.buttons.add(this.menuButton);
	}
	
	private void initCommon() {
	}
	
	/** Papplet will run this method upon instantiation. Do not touch.
	 * 
	 */
	public void settings() {
		size(this.width, this.height);
	}
	
	/** Change things for instantiation here.
	 * 
	 */
	public void setup() {
		frameRate(FPS);
	}
	
	
	/** This method draws all the objects within the game. 
	 * 
	 */
	public void draw() {
		pushStyle();
		pushMatrix();
		
		background(43,45,47); //charcoal gray background
		
		if(!play) {
			this.resetButton.setVisible(false);
			this.menuButton.setVisible(false);
			
			this.menu.draw(this);
		}
		else if(play) {
			this.menu.getStartButton().setVisible(false);
			this.menu.getInstructionsButton().setVisible(false);
			this.menu.getQuitButton().setVisible(false);
			this.menu.getTimeButton().setVisible(false);
			this.menu.getMoveButton().setVisible(false);
			this.menu.getThreeByThree().setVisible(false);
			this.menu.getFourByFour().setVisible(false);
			this.menu.getFiveByFive().setVisible(false);
			this.menu.getBackButton().setVisible(false);
			
			this.board.draw(this);
			this.goalBoard.draw(this);
			
			for (Button b : buttons) {
				b.draw(this);
			}
			textAlign(CENTER);
			if (gameMode > 0 && gameMode < 4) {
				this.timeUsed += (1/FPS)*2;
				if (this.board.equals(this.goalBoard)) {
					this.gameMode = 4;
					System.out.println("asdf");
				}
			}
			
			fill(200,200,200);
			textSize(28);
			if(gameMode == 1) {
				text("Time Used: " + (int)(this.timeUsed) + "s", width*0.70f, height * 0.15f);
			}
			else if(gameMode == 2) {
				text("Moves Used: " + this.movesUsed, width * 0.70f, height * 0.15f);
			}
			
			if (gameMode == 4) {
				System.out.println("Done");
				text("Congrats!", width * 0.75f, height * .25f);
			}
		}
		
		popMatrix();
		popStyle();
	}
	
	/** The default Processing method for detecting a single key press.
	 * 
	 */
	public void keyPressed() {
		
		if (keyCode >= 37 && keyCode <= 40 ) { //Left Up Right Down corresponds to 37 38 39 40
			if (this.gameMode > 0 && this.gameMode < 4) {
				boolean moved = this.board.move(keyCode - 37, Integer.parseInt(numToMove)-1);
				this.numToMove = "0";
				if (moved) {this.movesUsed++;}
				
			} 
		}
		
		if (Character.isDigit(key)) {
			this.numToMove += key;
		}
		
	}
	
	public void mousePressed() {
		
		if (resetButton.onButton(mouseX,mouseY) && resetButton.getVisible()) {
			this.movesUsed = 0;
			this.timeUsed = 0;
			this.board = new Board(board.getCoords()[0], board.getCoords()[1], board.getSize()[0], board.getSize()[0], board.getTileWidth());
			this.goalBoard = new Board(goalBoard.getCoords()[0], goalBoard.getCoords()[1], goalBoard.getSize()[0], goalBoard.getSize()[0], goalBoard.getTileWidth());
			this.goalBoard.setTileColors(board.getTiles());
			this.goalBoard.shuffleBoard();
			while(this.board.equals(this.goalBoard)) {
				this.goalBoard.shuffleBoard();
			}
		}
		
		if(menuButton.onButton(mouseX, mouseY) && menuButton.getVisible()){
			this.play = false;
			this.menu.setMenuLevel(1);
		}
		
		if(menu.getStartButton().onButton(mouseX, mouseY) && menu.getStartButton().getVisible()) {
			menu.increaseMenuLevel(0);
		}
		
		if(menu.getInstructionsButton().onButton(mouseX, mouseY) && menu.getInstructionsButton().getVisible()) {
			menu.increaseMenuLevel(1);
		}
		
		if(menu.getQuitButton().onButton(mouseX, mouseY) && menu.getQuitButton().getVisible()) {
			exit();
		}
		
		if(menu.getTimeButton().onButton(mouseX, mouseY) && menu.getTimeButton().getVisible()) {
			this.gameMode = 1;
			menu.increaseMenuLevel(2);
		}
		
		if(menu.getMoveButton().onButton(mouseX, mouseY) && menu.getMoveButton().getVisible()) {
			this.gameMode = 2;
			menu.increaseMenuLevel(2);			
		}
		
		if(menu.getThreeByThree().onButton(mouseX, mouseY) && menu.getThreeByThree().getVisible()) {
			this.play = true;
			this.movesUsed = 0;
			this.timeUsed = 0;
			this.numToMove = "0";
			this.menu = new Menu(width, height);
			this.board = new Board(width * 0.5f, height * 0.6f, 3, 3, DEFAULT_TILE_WIDTH);
			this.goalBoard = new Board(width * 0.2f, height * 0.15f, 3, 3, DEFAULT_TILE_WIDTH*0.4f);
			this.goalBoard.setTileColors(board.getTiles());
			this.goalBoard.shuffleBoard();
			while(this.board.equals(this.goalBoard)) {
				this.goalBoard.shuffleBoard();
			}
		}
		
		if(menu.getFourByFour().onButton(mouseX, mouseY) && menu.getFourByFour().getVisible()) {
			this.play = true;
			this.movesUsed = 0;
			this.timeUsed = 0;
			this.numToMove = "0";
			this.board = new Board(width * 0.5f, height * 0.6f, 4, 4, DEFAULT_TILE_WIDTH);
			this.goalBoard = new Board(width * 0.2f, height * 0.15f, 4, 4, DEFAULT_TILE_WIDTH*0.4f);
			this.goalBoard.setTileColors(board.getTiles());
			this.goalBoard.shuffleBoard();
			while(this.board.equals(this.goalBoard)) {
				this.goalBoard.shuffleBoard();
			}
		}
		
		if(menu.getFiveByFive().onButton(mouseX, mouseY) && menu.getFiveByFive().getVisible()) {
			this.play = true;
			this.movesUsed = 0;
			this.timeUsed = 0;
			this.numToMove = "0";
			this.board = new Board(width * 0.5f, height * 0.6f, 5, 5, DEFAULT_TILE_WIDTH);
			this.goalBoard = new Board(width * 0.2f, height * 0.15f, 5, 5, DEFAULT_TILE_WIDTH*0.4f);
			this.goalBoard.setTileColors(board.getTiles());
			this.goalBoard.shuffleBoard();
			while(this.board.equals(this.goalBoard)) {
				this.goalBoard.shuffleBoard();
			}
		}
		
		if(menu.getBackButton().onButton(mouseX, mouseY) && menu.getBackButton().getVisible()) {
			menu.decreaseMenuLevel();
		}
		
	}
}
