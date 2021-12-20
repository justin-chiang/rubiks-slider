package gameobjects;
import processing.core.*;
import utils.*;
import java.util.*;

public class Board {
	
	public static final float[] BOARD_COLOR = {};
	
	//class Fields
	private int rows;
	private int cols;
	//The top-left corner's coordinates.
	private float x;
	private float y;
	//width and height should not be declared but calculated based off of the row and column dimensions.
	private float width;
	private float height;
	//tiles will be square anyways.
	private float tileWidth;
	
	
	private Tile[][] tiles;
	//these are the indices of the null tile. 
	private int nullI;
	private int nullJ;
	
	/**No-args constructor - don't know why you would want to use this.
	 * 
	 */
	public Board() {
	}

	public Board(int rows, int cols, float tileWidth) {
		//dangerous
		this.x = 0;
		this.y = 0;
		
		this.rows = rows;
		this.cols = cols;
		this.tileWidth = tileWidth;
		this.width = cols * tileWidth;
		this.height = rows * tileWidth;
		
		generateBoard(this.rows, this.cols, this.tileWidth);
	}
	
	public Board(float x, float y, int rows, int cols, float tileWidth) {
		this.x = x;
		this.y = y;
		this.rows = rows;
		this.cols = cols;
		this.tileWidth = tileWidth;
		this.width = cols * tileWidth;
		this.height = rows * tileWidth;
		
		generateBoard(this.rows, this.cols, this.tileWidth);
	}
	
	public Tile[][] getTiles() {
		return this.tiles;
	}
	public void setTiles(Tile[][] newTiles) {
		this.tiles = newTiles;
	}
	
	public float[] getCoords() {
		return new float[] {this.x, this.y};
	}
	
	public int[] getSize() {
		return new int[] {this.rows, this.cols};
	}
	
	public float getTileWidth() {
		return this.tileWidth;
	}
	
	/** Generates a random arrangement of a board.
	 * 
	 * @param rows - the number of rows the board has.
	 * @param cols - the number of columns the board has.
	 * @param tileWidth - how wide each tile is.
	 * @return A 2D array representing an arrangement of the tiles on the board.
	 */
	public void generateBoard(int rows, int cols, float tileWidth) {
		float[][] colors = {Constants.RED, Constants.ORANGE, Constants.YELLOW, Constants.WHITE, Constants.GREEN, Constants.BLUE};
		Tile[][] board = new Tile[rows][cols]; 
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				board[i][j] = new Tile(i, j, this.x - ((cols/2f - j) * tileWidth) + tileWidth/2, this.y - ((rows/2f - i) * tileWidth) + tileWidth/2, tileWidth, tileWidth, colors[(int)(6*Math.random())]);
			}
		}
		
		//set a random tile to empty.
		this.nullI = (int)(Math.random()*rows);
		this.nullJ = (int)(Math.random()*cols);
		board[this.nullI][this.nullJ].setNull(true);
		board[this.nullI][this.nullJ].setcolor(new float[]{-1,-1,-1});
		this.tiles = board;
	}
	
	/** Shuffles the tiles of this Board.
	 * 
	 * @param solvedBoard the board in a solved state.
	 */
	public void shuffleBoard() {
		int moves = (int)(Math.random() * 50) + 50; // [50, 99]
		for (int i = 0; i < moves; i++) {
			int move = (int)(Math.random() * 4);
			int length = 0;
			if (move % 2 == 0) { //l/r
				length = this.cols;
			} else { //u/d
				length = this.rows;
			}
			this.move(move, (int)(Math.random() * length));
		}
	}
	
	/** Checks the dimensions and tile colors of each board.
	 * 
	 * @param that the other Board to check equivalence with
	 * @return a boolean representing whether these two boards are equal to each other or not.
	 */
	public boolean equals(Board that) {
		if (this.rows == that.rows && this.cols == that.cols) {
			if (this.nullI == that.nullI && this.nullJ == that.nullJ) {
				for (int i = 0; i < this.rows; i++) {
					for (int j = 0; j < this.cols; j++) {
						if (!this.tiles[i][j].getColor().equals(that.tiles[i][j].getColor())) {
							return false;
						}
					}
				}
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/** Sets the tile colors of this board according to the tiles of another board.
	 * 
	 * @pre requires that srcTiles and this board's tiles be of the same dimensions.
	 * @param srcTiles the source 2-D Tile array to copy from.
	 */
	public void setTileColors(Tile[][] srcTiles) {
		for (int i = 0; i < srcTiles.length; i++) {
			for (int j = 0; j < srcTiles[0].length; j++) {
				this.tiles[i][j].setcolor(srcTiles[i][j].getColor());
				if (this.tiles[i][j].getNull()) {this.tiles[i][j].setNull(false);}
				if (srcTiles[i][j].getNull()) {this.tiles[i][j].setNull(true); this.nullI = i; this.nullJ = j;}
			}
		}
	}
	
	public boolean move(int move, int numToMove) {
		//move: 0 = LEFT, 1 = UP, 2 = RIGHT, 3 = DOWN

		Tile tempTile = this.tiles[this.nullI][this.nullJ];
		float[] tempCoords = tempTile.getCoords();
		if (move == 0) {
			if (this.nullJ == this.cols-1) { return false; }
			this.tiles[this.nullI][this.nullJ].setCoords(this.tiles[this.nullI][this.nullJ+1].getCoords()[0],this.tiles[this.nullI][this.nullJ+1].getCoords()[1]);
			this.tiles[this.nullI][this.nullJ].setIndices(this.nullI,this.nullJ+1);
			this.tiles[this.nullI][this.nullJ+1].setCoords(tempCoords[0], tempCoords[1]);
			this.tiles[this.nullI][this.nullJ+1].setIndices(this.nullI,this.nullJ);
			this.tiles[this.nullI][this.nullJ] = this.tiles[this.nullI][this.nullJ+1];
			this.tiles[this.nullI][this.nullJ+1] = tempTile;
			this.nullJ += 1;
		} else if (move == 1) {
			if (this.nullI == this.rows-1) { return false; }
			this.tiles[this.nullI][this.nullJ].setCoords(this.tiles[this.nullI+1][this.nullJ].getCoords()[0],this.tiles[this.nullI+1][this.nullJ].getCoords()[1]);
			this.tiles[this.nullI][this.nullJ].setIndices(this.nullI+1,this.nullJ);
			this.tiles[this.nullI+1][this.nullJ].setCoords(tempCoords[0], tempCoords[1]);
			this.tiles[this.nullI+1][this.nullJ].setIndices(this.nullI,this.nullJ);
			this.tiles[this.nullI][this.nullJ] = this.tiles[this.nullI+1][this.nullJ];
			this.tiles[this.nullI+1][this.nullJ] = tempTile;
			this.nullI += 1;
		} else if (move == 2) {
			if (this.nullJ == 0) { return false; }
			this.tiles[this.nullI][this.nullJ].setCoords(this.tiles[this.nullI][this.nullJ-1].getCoords()[0],this.tiles[this.nullI][this.nullJ-1].getCoords()[1]);
			this.tiles[this.nullI][this.nullJ].setIndices(this.nullI,this.nullJ-1);
			this.tiles[this.nullI][this.nullJ-1].setCoords(tempCoords[0], tempCoords[1]);
			this.tiles[this.nullI][this.nullJ-1].setIndices(this.nullI,this.nullJ);
			this.tiles[this.nullI][this.nullJ] = this.tiles[this.nullI][this.nullJ-1];
			this.tiles[this.nullI][this.nullJ-1] = tempTile;
			this.nullJ -= 1;
		} else if (move == 3) {
			if (this.nullI == 0) { return false; }

			this.tiles[this.nullI][this.nullJ].setCoords(this.tiles[this.nullI-1][this.nullJ].getCoords()[0],this.tiles[this.nullI-1][this.nullJ].getCoords()[1]);
			this.tiles[this.nullI][this.nullJ].setIndices(this.nullI-1,this.nullJ);
			this.tiles[this.nullI-1][this.nullJ].setCoords(tempCoords[0], tempCoords[1]);
			this.tiles[this.nullI-1][this.nullJ].setIndices(this.nullI,this.nullJ);
			this.tiles[this.nullI][this.nullJ] = this.tiles[this.nullI-1][this.nullJ];
			this.tiles[this.nullI-1][this.nullJ] = tempTile;
			this.nullI -= 1;
		}
		
		//recursion
		if (numToMove > 0) {
			return this.move(move, numToMove-1);
		} else {
			return true;
		}
	}
	
	
	/** This method draws the Board's borders and all of its tiles. 
	 * 
	 */
	public void draw(PApplet p) {
		p.pushStyle();
		p.pushMatrix();
		
		p.rectMode(PConstants.CENTER);

		p.stroke(164-50,116-50,73-50);
		p.strokeWeight(5);
		p.fill(164,116,73);
		//draw the board
		p.rect(this.x, this.y, this.width + 20, this.height + 20, 7);
		
		//draw each tile
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				tiles[i][j].draw(p);
			}
		}
		
		p.popMatrix();
		p.popStyle();
	}
	
	
}
