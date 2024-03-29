package utils;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

/**
 * Represents a PApplet Button!
 * 
 * @author Justin Chiang
 *
 */
public class Button {

	private double x;
	private double y;
	private double width;
	private double height;
	private int round;
	private float[] defaultColor;
	private float[] hoverColor;
	private boolean hovered;
	private String text;
	private boolean st;
	private PFont font;
	private boolean visible;

	/**
	 * 
	 * @param xVal x coordinate location of the button's center
	 * @param yVal y coordinate location of the button's center
	 * @param w  width of the button
	 * @param h  height of the button
	 * @param dC Color of button when button it is not being hovered over
	 * @param hC Color of button when button it is being hovered over
	 * @param t  the text to be displayed on this Button
	 * @param r  the roundness of the button
	 */
	public Button(double xVal, double yVal, double w, double h, float[] dC, float[] hC, String t, int r) {
		x = xVal;
		y = yVal;
		width = w;
		height = h;
		defaultColor = dC;
		hoverColor = hC;
		hovered = false;
		text = t;
		round = r;
		st = false;
		font = null;
		visible = false;
	}

	/**
	 * 
	 * @param marker the PApplet used to draw this Button
	 */
	public void draw(PApplet marker) {
		marker.pushStyle();
		
		visible = true;
		
		if(!st) {
		if (hovered)
			marker.fill(defaultColor[0], defaultColor[1], defaultColor[2]);
		else
			marker.fill(hoverColor[0], hoverColor[1], hoverColor[2]);
		} else {
			marker.fill(60);
		}
		marker.rectMode(PConstants.CENTER);
		marker.rect((float) x, (float) y, (float) width, (float) height, round);
		if(!st)
			marker.fill(255);
		else
			marker.fill(150);
		if (this.font != null) {
			marker.textFont(font);
		}
		marker.textSize((float) height - 10);
		marker.text(text, (float) (x - marker.textWidth(text) / 2), (float) (y) + marker.textAscent() / 2 - 5);
		if (st) {
			int offset = 13;
//			marker.stroke(255, 0, 0);
//			marker.line((float) x - (float) width / 2 + offset, (float) y - (float) height / 2, (float) x + (float) width / 2 - offset,
//					(float) y + (float) height / 2);
//			marker.line((float) x - (float) width / 2 + offset, (float) y + (float) height / 2, (float) x + (float) width / 2 - offset,
//					(float) y - (float) height / 2);
			
		}
		marker.popStyle();
	}

	/**
	 * 
	 * @param h the boolean that regarding whether there is a cursor on this Button or not
	 */
	public void setHover(boolean h) {
		hovered = h;
	}

	/**
	 * 
	 * @return a Rectangle2D.Double object that bounds this Button
	 */
	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double(x - width / 2, y - height / 2, width, height);
	}
	
	/** Returns whether the mouse is within the bounds of the button.
	 * 
	 * @param mouseX X coordinate of the mouse
	 * @param mouseY Y coordinate of the mouse
	 * @return a boolean representing whether the mouse is within the bounds of the button.
	 */
	public boolean onButton(float mouseX, float mouseY) {
		return (mouseX <= x + width/2 && mouseX >= x - width/2 && mouseY <= y+height/2 && mouseY >= y-height/2);
	}
	
	/*
	 * 
	 * @return a boolean whether this button has a strike through or not
	 */
	public boolean getST() {
		return st;
	}

	/** Adds a strike through is there isn't one and removes it if there is already one
	 * 
	 */
	public void toggleST() {
		st = !st;
	}
	
	public void setST(boolean s) {
		st = s;
	}
	
	/**
	 * 
	 * @param f the PFont that the text of this Button should have
	 */
	public void setFont(PFont f) {
		font = f;
	}
	
	/**
	 * 
	 * @param b the boolean to set the visibility of this button to
	 */
	public void setVisible(boolean b) {
		visible = b;
	}
	
	/**
	 * Returns the "visibility" of this button
	 * @return the visibility of the button
	 */
	public boolean getVisible() {
		return visible;
	}

}
