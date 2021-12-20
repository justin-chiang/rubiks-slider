package main;

import gamelogic.*;
import java.awt.Dimension;
import javax.swing.JFrame;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;



/** The main class for the project. Runs everything.
 * 
 * @author Calix Tang
 *
 */
public class Slider {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/* Carrom
		Menu menu = new Menu(750, 750);
		PApplet.runSketch(new String[]{"Menu"}, menu);
		
		PSurfaceAWT menusurf = (PSurfaceAWT) menu.getSurface();
		PSurfaceAWT.SmoothCanvas menucanvas = (PSurfaceAWT.SmoothCanvas) menusurf.getNative();
		JFrame menuwindow = (JFrame)menucanvas.getFrame();
		
		//window is 750x750 permanently
		menuwindow.setSize(750, 776);
		menuwindow.setMinimumSize(new Dimension(750,776));
		menuwindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		menuwindow.setResizable(false);//a stretch is to change this
				
		//make window visible
		menuwindow.setVisible(true);
		menucanvas.requestFocus();
		 */
		
		GameBoard board = new GameBoard(1000, 800, 0, 5, 5);
		PApplet.runSketch(new String[] {"Game"}, board);
	}

}
