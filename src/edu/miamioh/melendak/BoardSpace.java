/**
 * 
 */
package edu.miamioh.melendak;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * @author alyssakirstine
 * Class: CSE 271
 * Instructor: Dr. Stephan
 * Date: May 4th, 2017
 * File name: BoardSpace.java
 * Description: A class for a board space to be put on the board of the n-queens problem.
 * 				Also accounts for which spaces have queens and which do not.
 */
public class BoardSpace extends JComponent {
	// Declaring instance variables
	private Color color;
	static int RECT_HEIGHT = 60;
	static int RECT_WIDTH = 60;
	static int xComponent = 0;
	static int yComponent = 0;
	private BufferedImage img = null;
	private boolean hasQueen;
	private int col, row;

	/**
	 * A constructor for an empty board space
	 */
	public BoardSpace () {
		hasQueen = false;
	} // end BoardSpace constructor
	
	/**
	 * A constructor for an empty board space with a specified color
	 * @param color
	 */
	public BoardSpace (Color color) {
		this.color = color;
		hasQueen = false;
	} // end BoardSpace constructor
	
	/**
	 * Paints a board space and sets it to the specified color
	 */
	public void paintComponent(Graphics g) {
		g.setColor(color);
		g.fillRect(xComponent,yComponent,RECT_WIDTH,RECT_HEIGHT);
		g.drawImage(img, xComponent, yComponent, null);
	} // end paintComponent method

	/**
	 * Returns the color of the board space
	 * @return BoardSpace color
	 */
	public Color getColor() {
		return color;
	} // end getColor method
	
	/**
	 * Adds a queen image to the board space
	 * @throws IOException
	 */
	public void setImage() throws IOException {
		img = ImageIO.read(new File("queen.png"));
		hasQueen = true;
		repaint();
	} // end setImage method
	
	/**
	 * Adds a queen image (based on parameter) to the board space
	 * @param state Choose "unsafe" or "win"
	 * @throws IOException
	 */
	public void setImage(String state) throws IOException {
		if (state.equals("unsafe")) {
			img = ImageIO.read(new File("unsafeQueen.png"));
			repaint();
		}
		else if (state.equals("win")) {
			img = ImageIO.read(new File("winningQueen.png"));
			repaint();
		}
	} // end setImage method
	
	/**
	 * Removes the queen image from the board space
	 */
	public void removeImage() {
		img = null;
		hasQueen = false;
		repaint();
	} // end removeImage method
	
	/**
	 * Checks whether a board space has a queen
	 * @return True if queen, false if no queen
	 */
	public boolean hasQueen() {
		return hasQueen;
	} // end hasQueen method
	
	/**
	 * Sets the column of the board space
	 * @param col BoardSpace column
	 */
	public void setCol(int col) {
		this.col = col;
	} // end setCol method
	
	/**
	 * Sets the row of the board space
	 * @param row BoardSpace row
	 */
	public void setRow(int row) {
		this.row = row;
	} // end setRow method
	
	/**
	 * Returns the column of the board space
	 * @return BoardSpace column
	 */
	public int getCol() { 
		return this.col;
	} // end getCol method
	
	/**
	 * Returns the row of the board space
	 * @return BoardSpace row
	 */
	public int getRow() {
		return this.row;
	} // end getRow method
	
	/**
	 * Checks whether this queen attacks another. (Code from Dr. Stephan's lecture slides)
	 * @param queen Queen to be compared
	 * @return True if attack, false if no attack
	 */
	public boolean attacks(BoardSpace other) {
		return this.row == other.getRow() ||
				this.col == other.getCol() ||
				Math.abs(this.row - other.getRow()) == Math.abs(this.col - other.getCol());
	} // end examine method
	
	/**
	 * Overrides the default toString method and returns
	 * a string representation of a boardSpace
	 */
	@Override
	public String toString() {
		return "(Column: " + col + ", Row: " + row + ")";
	} // end toString method
	
} // end class