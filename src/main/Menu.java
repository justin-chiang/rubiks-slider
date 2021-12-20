package main;
import processing.core.*;
import utils.*;
import java.util.*;

public class Menu {
	
	private int width;
	private int height;
	private int menuLevel;
	
	private boolean clickStart;
	private boolean clickInstructions;

	private Button start;
	private Button instructions;
	private Button quit;
	
	private Button time;
	private Button move;
	
	private Button threeByThree;
	private Button fourByFour;
	private Button fiveByFive;
	
	private Button back;

	public Menu(int w, int h) {
		this.width = w;
		this.height = h;
		this.menuLevel = 1;
		this.clickStart = false;
		this.clickInstructions = false;
		this.start = new Button(w * 0.5f, h * 0.5f, 300, 50, new float[] {180, 180, 180}, new float[] {230, 230, 230}, "Start", 7);
		this.instructions = new Button(w * 0.5f, h * 0.6f, 300, 50, new float[] {180, 180, 180}, new float[] {230, 230, 230}, "Instructions", 7);
		this.quit = new Button(w * 0.5f, h * 0.7f, 300, 50, new float[] {180, 180, 180}, new float[] {230, 230, 230}, "Quit", 7);
		this.time = new Button(w * 0.5f, h * 0.5f, 300, 50, new float[] {180, 180, 180}, new float[] {230, 230, 230}, "Time-Based", 7);
		this.move = new Button(w * 0.5f, h * 0.6f, 300, 50, new float[] {180, 180, 180}, new float[] {230, 230, 230}, "Move-Based", 7);
		this.threeByThree = new Button(w * 0.5f, h * 0.5f, 300, 50, new float[] {180, 180, 180}, new float[] {230, 230, 230}, "3 x 3", 7);
		this.fourByFour = new Button(w * 0.5f, h * 0.6f, 300, 50, new float[] {180, 180, 180}, new float[] {230, 230, 230}, "4 x 4", 7);
		this.fiveByFive = new Button(w * 0.5f, h * 0.7f, 300, 50, new float[] {180, 180, 180}, new float[] {230, 230, 230}, "5 x 5", 7);
		this.back = new Button(w * 0.5f, h * 0.9f, 300, 50, new float[] {180, 180, 180}, new float[] {230, 230, 230}, "Back", 7);
	}
	
	public void draw(PApplet p) {
		p.pushStyle();
		p.pushMatrix();
		
		p.textAlign(PApplet.CENTER);
		p.textSize(56);
		p.text("Rubik's Slider", width * 0.5f, height * 0.2f);
		
		if(menuLevel == 1) {
			p.textAlign(PApplet.LEFT);
			
			time.setVisible(false);
			move.setVisible(false);
			
			threeByThree.setVisible(false);
			fourByFour.setVisible(false);
			fiveByFive.setVisible(false);
			
			back.setVisible(false);
			
			start.draw(p);
			instructions.draw(p);
			quit.draw(p);
		}
		else if(menuLevel == 2 && clickStart) {
			p.textAlign(PApplet.LEFT);
			
			start.setVisible(false);
			instructions.setVisible(false);
			quit.setVisible(false);
			
			threeByThree.setVisible(false);
			fourByFour.setVisible(false);
			fiveByFive.setVisible(false);
			
			time.draw(p);
			move.draw(p);
			back.draw(p);
		}
		else if(menuLevel == 2 && clickInstructions) {
			p.textAlign(PApplet.LEFT);
			
			start.setVisible(false);
			instructions.setVisible(false);
			quit.setVisible(false);
			
			time.setVisible(false);
			move.setVisible(false);
			
			threeByThree.setVisible(false);
			fourByFour.setVisible(false);
			fiveByFive.setVisible(false);
			
			System.out.println("test");
			back.draw(p);
		}
		else if(menuLevel == 3) {
			p.textAlign(PApplet.LEFT);
			
			start.setVisible(false);
			instructions.setVisible(false);
			quit.setVisible(false);
			
			time.setVisible(false);
			move.setVisible(false);
			
			threeByThree.draw(p);
			fourByFour.draw(p);
			fiveByFive.draw(p);
			back.draw(p);
		}
		
		p.popMatrix();
		p.popStyle();
	}
	
	public Button getStartButton() {
		return start;
	}
	
	public Button getInstructionsButton() {
		return instructions;
	}
	
	public Button getQuitButton() {
		return quit;
	}
	
	public Button getTimeButton() {
		return time;
	}
	
	public Button getMoveButton() {
		return move;
	}
	
	public Button getThreeByThree() {
		return threeByThree;
	}
	
	public Button getFourByFour() {
		return fourByFour;
	}
	
	public Button getFiveByFive() {
		return fiveByFive;
	}
	
	public Button getBackButton() {
		return back;
	}
	
	public void setMenuLevel(int x) {
		this.menuLevel = x;
	}
	
	public void increaseMenuLevel(int x) {
		if(x == 0) {
			clickStart = true;
		}
		else if(x == 1) {
			clickInstructions = true;
		}
		menuLevel++;
	}
	
	public void decreaseMenuLevel() {
		if(menuLevel == 2) {
			if(clickStart && !clickInstructions) {
				clickStart = false;
			}
			else if(!clickStart && clickInstructions) {
				clickInstructions = false;
			}
		}
		menuLevel--;
	}
}
