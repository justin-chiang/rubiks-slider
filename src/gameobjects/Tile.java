package gameobjects;

import processing.core.*;

/** Represents one tile of the puzzle.
 * 
 * @author Justin Chiang, Akshat Jain, Calix Tang
 *
 */
public class Tile {
	
	//These represent the array coordinates of the tile.
	private int i;
	private int j;
	//These represent the PApplet coordinates and size of the tile.
	private float x;
	private float y;
	private float width;
	private float height;
	private boolean nullTile;
	
	
	//Represents the color value of the tile.
	private float[] color;
	
	public Tile() {
		this.i = 0;
		this.j = 0;
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
		this.nullTile = false;
	}
	
	public Tile(int i, int j, float x, float y, float width, float height, float[] color) {
		this.i = i;
		this.j = j;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.nullTile = false;
	}
	
	public int[] getIndices() {
		return new int[] {this.i, this.j};
	}
	public void setIndices(int i, int j) {
		this.i = i;
		this.j = j;
	}
	
	public float[] getCoords() {
		return new float[] {this.x, this.y};
	}
	public void setCoords(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public boolean getNull() {
		return this.nullTile;
	}
	public void setNull(boolean nullness) {
		this.nullTile = nullness;
	}
	public float[] getColor() {
		return this.color;
	}
	public void setcolor(float[] color) {
		this.color = color;
	}
	
	/** Draws this Tile in a PApplet p.
	 * 
	 * @param p - the PApplet.
	 */
	public void draw(PApplet p) {
		if (this.nullTile) {
			return;
		}
		p.pushStyle();
		p.pushMatrix();
		
		p.rectMode(PConstants.CENTER);
		p.fill(color[0], color[1], color[2]);
		p.stroke(color[0] * 0.6f, color[1] * 0.6f, color[2] * 0.6f);
		p.strokeWeight(2);
		p.rect(this.x, this.y, this.width * 0.97f , this.height * 0.97f , 3);
		
		p.popMatrix();
		p.popStyle();
	}
}
