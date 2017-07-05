/**
 * 
 */
package edu.miamioh.melendak;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author alyssakirstine
 * Class: CSE 271
 * Instructor: Dr. Stephan
 * Date: May 4th, 2017
 * File name: nQueensProblem.java
 * Description: This class calls on a graphical application
 * 				that produces a game of the n-queens problem.
 */
public class nQueensProblem {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Constructing a Frame Object
		JFrame frame = new EightQueensFrame();
		frame.setSize(480, 550);
		frame.setTitle("The n-queens problem");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	} // end main method

} // end class
