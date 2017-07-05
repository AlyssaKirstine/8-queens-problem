/**
 * 
 */
package edu.miamioh.melendak;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author alyssakirstine
 * Class: CSE 271
 * Instructor: Dr. Stephan
 * Date: May 4th, 2017
 * File name: EightQueensFrame.java
 * Description: This class creates chess board and GUI that allows
 * 				a user to try the n-queens problem.
 */
public class EightQueensFrame extends JFrame {
	// Declaring instance variables
	
	// Panels
	private JPanel boardPanel;
	private JPanel buttonPanel;
	private JPanel textPanel;
	
	// for board
	private Color brown = new Color(80, 19, 0);
	private Color gold = new Color(225, 182, 121);
	private int cols, rows;
	private int numOfQueens;
	private ArrayList<BoardSpace> queens;
	private BoardSpace testSpace = new BoardSpace(Color.black);
	
	// Listeners
	private MouseListener listener = new QueenListener();
	private ActionListener buttonListener;
	
	// for buttonPanel
	JButton checkSolutionButton, tipButton;
	
	/**
	 * Constructs a game of the n-queens problem
	 */
	public EightQueensFrame() {
		// Initialize button listener
		buttonListener = new ButtonListener();
		
		// Setting up the oh so fun game
		constructTextPanel();
		constructBoard();
		constructButtonPanel();	
		addActionListeners();
		addPanels();
	} // end constructor
	
	/**
	 * Constructs and adds text to a text panel
	 */
	public void constructTextPanel() {
		textPanel = new JPanel();
		JLabel text = new JLabel("Can you solve the 8-queens problem?");
		textPanel.add(text);
	} // end constructTextPanel
	
	/**
	 * Constructs and adds boardSpaces to a board
	 */
	public void constructBoard() {
		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(8,8));
		cols = 8;
		rows = 8;
		boolean brownColor = true;
		
		// Goes through add adds alternating-colored board spaces to our board panel
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				BoardSpace space;
				if (brownColor) {
					space = new BoardSpace(brown);
					brownColor = false;
				}
				else {
					space = new BoardSpace(gold);
					brownColor = true;
				}
				space.setCol(j + 1);
				space.setRow(i + 1);
				space.addMouseListener(listener);
				boardPanel.add(space);
			}
			if (brownColor) {
				brownColor = false;
			}
			else {
				brownColor = true;
			}
		} // end for loop
		queens = new ArrayList<BoardSpace>();
		boardPanel.setVisible(true);
	} // end constructBoard method
	
	/**
	 * Constructs and adds buttons to a button panel
	 */
	public void constructButtonPanel() {
		buttonPanel = new JPanel();
		checkSolutionButton = new JButton("Check Solution");
		tipButton = new JButton("Tip");
		buttonPanel.add(checkSolutionButton);
		buttonPanel.add(tipButton);
		buttonPanel.setVisible(true);
	} // end constructButtonPanel method
	
	/**
	 * Adds action listeners to the two buttons
	 */
	public void addActionListeners() {
		checkSolutionButton.addActionListener(buttonListener);
		tipButton.addActionListener(buttonListener);
	} // end addActionListeners method
	
	/**
	 * Adds panels to the board
	 */
	public void addPanels() {
		add(textPanel,BorderLayout.NORTH);
		add(boardPanel,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);
	} // end addPanels method
	
	/**
	 * A mouse listener class for a board space.
	 * @author alyssakirstine
	 */
	class QueenListener implements MouseListener {
		/**
		 *  For when a boardSpace is clicked. Adds or removes a queen
		 *  depending on the circumstances.
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() instanceof BoardSpace) {
				BoardSpace space = (BoardSpace) e.getSource();
				try {
					if (space.hasQueen()) {
						space.removeImage();
						tipButton.setVisible(true);
						// remove queen from array
						queens.remove(space);
						numOfQueens--;
					}
					else {
						space.setImage();
						// add queen to array
						queens.add(space);
						numOfQueens++;
						// tip button hides when eight queens are on the board
						if (eightOrMoreQueens())
							tipButton.setVisible(false);
					}
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		} // end mouseClicked method
		// Do-nothing methods
		@Override 
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
	} // end class
	
	/**
	 * An action listener class for the check solution and tip button
	 * @author alyssakirstine
	 *
	 */
	class ButtonListener implements ActionListener {
		/**
		 * Activates when one of the buttons is clicked
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// Part for check solution button
			if (e.getSource().equals(checkSolutionButton)) {
				JFrame frame = new JFrame();
				try {
					if (examine().equals("Abandon")) {
						JOptionPane.showMessageDialog(frame, "One or more queens attacks each other! Try again.");
					}
					else if (examine().equals("Accept"))
						JOptionPane.showMessageDialog(frame, "You win!");
					else {
						JOptionPane.showMessageDialog(frame, "Your queens are safe.");
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} // end part for check solution button
			
			// Part for tip button
			if (e.getSource().equals(tipButton)) {
				JFrame frame = new JFrame();
				if (testSpace.equals(findSafeSpace())) {
					JOptionPane.showMessageDialog(frame, "Sorry! There are no safe spots left on the board.");
				}
				else {
					JOptionPane.showMessageDialog(frame, "You can place a queen at " + findSafeSpace().toString());
				}
			} // end part for tip button
			
		} // end actionPerformed method
		
	} // end ButtonListener class
	
	/**
	 * Checks whether there are more than eight queens on the board
	 * @return True if there are eight or more queens. Otherwise, false.
	 */
	public boolean eightOrMoreQueens() {
		if (numOfQueens >= 8) {
			return true;
		}
		else {
			return false;
		}
	} // end eightOrMoreQueens method
	
	/**
	 * Examines a partial solution (code from Dr. Stephan's lecture slides)
	 * @return A string representation of the user's progress
	 * @throws IOException 
	 */
	public String examine() throws IOException {
		boolean error = false;
		// Checks whether two queens attack each other
		for (int i = 0; i < queens.size(); i++) {
			for (int j = i + 1; j < queens.size(); j++) {
				if (queens.get(i).attacks(queens.get(j))) {
					queens.get(i).setImage("unsafe");
					queens.get(j).setImage("unsafe");
					error = true;
				}
			}
		}
		// For when two queens do attack each other
		if (error) {
			return "Abandon";
		}
		// For when the user has won
		if (queens.size() == 8) {
			for (int i = 0; i < queens.size(); i++) {
				queens.get(i).setImage("win");
			}
			return "Accept";
		}
		// For when the user is safe
		else {
			return "Safe";
		}
	} // end examine method

	/**
	 * This method finds any safe spaces on the board. If there is one, it returns it.
	 * Otherwise, it returns testSpace, which is an instance variable.
	 * @return A safe board space (or if no safe spaces, returns testSpace)
	 */
	public BoardSpace findSafeSpace() {
		BoardSpace other = new BoardSpace();
		// Accounts for no queens on board yet
		if (queens.size() == 0) {
			other.setCol(1);
			other.setRow(1);
			return other;
		}
		// Compares a boardSpace to all of the spaces with a queen
		for (int i = 1; i <= cols; i++) {
			for (int j = 1; j <= rows; j++) {
				other.setCol(i);
				other.setRow(j);
				boolean attacks = false;
				for (BoardSpace s : queens) {
					if (s.attacks(other)) {
						attacks = true;
					}
				}
				// Returns a safe board space (if there is one)
				if (!attacks) {
					return other;
				}
			}
		}
		return this.testSpace;
	} // end findSafeSpace method
	
} // end class