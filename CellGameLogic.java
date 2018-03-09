/* 6/5/2017
 * Fardeen Haque, Eric Yang and Misha Tsirlin
 * Color domination game (based on flood-it)
 * Game where you click on a color and the top right cell changes to that color then you click on another color and the top right cell and all the cells that are touching that cell that have the same color change to the coor that you clicked on and so on...
 */
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.awt.*;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CellGameLogic extends CellFrame implements MouseListener {
	public static Leaderboard l;
	public static Player p;
	private static final long serialVersionUID = 8319933255121639927L;

	public static String difficulty;
	//ArrayList that holds the active cells. Active cells are the cells that ones that get added to the ones that change colours on click
	static JFrame frame;
	static int intTurn; 
	static boolean gameOver = false;
	static //Holds the number of turns that user took
	Random rnd = new Random();
	//Random number generator
	static Timer t = new Timer();
	//Timer
	public CellGameLogic(String name) { //Constructor
		super(name);
		//Loop adds mouselisteners to each cell in LabelArray
		for (int i = 0; i < LabelArray[0].length; i++) {
			for( int j = 0; j < LabelArray[1].length; j++) {
				LabelArray[i][j].addMouseListener(this);
			}
		}
		LabelArray[0][0].isActive = true;
		//sets the top right cell active right away
		addCellNeighbors();
		//if any other cells around top right cell are the same color, they change color
		
	}

	public static void main(String[] args) throws IOException{
		
		JOptionPane.showMessageDialog(null, "Welcome to the game of flood-it, where you can have fun with colours. \n"
				+ "before you begin, chose a difficulty. Have fun!", "", JOptionPane.PLAIN_MESSAGE);
		//System.out.println(l.medPlayers);
		//out("Would you like easy, medium or hard?");
		String[] difficulties = {"Easy", "Medium", "Hard"};
		JComboBox<String> box = new JComboBox<String>(difficulties);
		difficulty = "";
		try {
			do  {
				difficulty = (String) JOptionPane.showInputDialog(null, "Choose your difficulty:", "", JOptionPane.PLAIN_MESSAGE, null, difficulties, "Easy");
			} while (!(difficulty.equalsIgnoreCase("easy") || difficulty.equalsIgnoreCase("medium") || difficulty.equalsIgnoreCase("hard")) || difficulty == null);
		//user sets their level of difficulty, which is the size of the board
		} catch (Exception ex) {
			System.exit(0);
		}
		setBoardSize(20);
		difficulty = difficulty.toLowerCase();
		//setBoardSize(boardSize);
		LabelArray = label.toLabelArray(intArraysize,  intArraysize);
		//sets label array size to what the user chose
		colourBoard(difficulty);
		intTurn = 0;
		//turns start at 0
		frame = new CellGameLogic("Flood-it!");
		frame.setSize(LabelArray[0].length * (LabelArray[0][0].getWidth()) + LabelArray[0][0].getWidth(), 
				LabelArray[1].length * (LabelArray[0][0].getHeight()) + LabelArray[0][0].getHeight() + 20);
		//sets the size of the frame proportional to the array of labels
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void colourBoard(String s) {
		int rndIndex = 0;
		Color[] easyColours = {new Color(226, 43, 43), new Color(249, 152, 32), 
				new Color(104, 18, 150)
				};
		Color[] medColours = {new Color(226, 43, 43), new Color(249, 152, 32), 
				new Color(104, 18, 150), new Color(216, 47, 157), new Color(60, 128, 216)};
		
		Color[] hardColours = {new Color(226, 43, 43), new Color(249, 152, 32), 
				new Color(247, 233, 76), new Color(43, 229, 83), new Color(60, 128, 216), new Color(104, 18, 150), new Color(216, 47, 157)};
		
		
		for (Cell[] c : LabelArray) {
			for (Cell cell : c) {
				rnd = new Random();
				if (s.equalsIgnoreCase("easy")) {
					rndIndex = rnd.nextInt(easyColours.length);
					cell.setBackground(easyColours[rndIndex]);
				} else if (s.equalsIgnoreCase("medium")) {
					rndIndex = rnd.nextInt(medColours.length);
					cell.setBackground(medColours[rndIndex]);
				} else if (s.equalsIgnoreCase("hard")) {
					rndIndex = rnd.nextInt(hardColours.length);
					cell.setBackground(hardColours[rndIndex]);
				}
				
				}
			}
		}
	
	static YouWinThread winThread = new YouWinThread(t);
	
	@Override
	public void mouseClicked(MouseEvent e){
		
		if (gameOver == false) {
			if (!(((Cell) e.getSource()).getBackground().equals(LabelArray[0][0].getBackground()))) {
				intTurn += 1;
				//each time a label is clicked, turns increase by 1
				setActiveColors((Cell) e.getSource());
				//all the active cells get the color of the cell that is clicked
				addCellNeighbors();
			}
			
		}
		if (checkWin()) {
			setActiveColors((Cell) e.getSource());
			addCellNeighbors();
			gameOver = true;
			//(new Thread(new CellFrame(this.getName()))).start();
			
			winThread.task = colourFlash;
			winThread.start();
			
			
			
			
		}
	}

	public static void gameOver() throws IOException{
		l = new Leaderboard();
		p = new Player();
		p.score = intTurn;
		p.difficulty = difficulty;
		p.name = "";
		p.getStars();
		JOptionPane.showMessageDialog(null, "YOU DID IT. Your score : " + (intTurn) 
				+ "\n(The lower the better!)\n" + p.stars, "Congratulations", JOptionPane.PLAIN_MESSAGE);
	
		String name;
		
		do {
		name = JOptionPane.showInputDialog(null, "", "Please enter your name:", JOptionPane.PLAIN_MESSAGE);
		} while (name == null || name.length() < 1);
		
		p.name = name;

		t.cancel();
		t.purge();
	
		winThread.interrupt();
		
		l.loadBoard();
		l.add(p);
		l.saveBoard();
		//l.sortList();
		frame.setVisible(false);
		JFrame f = new JFrame("Leaderboard");
		JPanel panel = new JPanel();
		int labelCount = 0;
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		for (JLabel jl : l.printBoard()) {
			jl.setLocation(0, labelCount);
			jl.setVisible(true);
			panel.add(jl);
			labelCount += 20;
		}
		
		f.getContentPane().add(panel);
		f.setSize(l.size * 5, labelCount + 20);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.toFront();
		f.setResizable(false);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//l.printBoard();
	}

	public void setActiveColors(Cell curLabel) {
		//sets the background color of all active cells to the backcolor of the cell given as a parameter
		Color currentColor = ((JLabel) curLabel).getBackground();
		for (int i = 0; i < LabelArray[0].length; i++) {
			for (int j = 0; j < LabelArray[1].length; j++) {
				if (LabelArray[i][j].isActive == true) {
					LabelArray[i][j].setBackground(currentColor);
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean checkWin() {
		//checks if the user won
		Color c = LabelArray[0][0].getBackground();
		for (int i = 0; i < LabelArray[0].length; i++) {
			for (int j = 0; j < LabelArray[1].length; j++) {
				if (LabelArray[i][j].getBackground() != c) {
					//if one of the cells has a different background from any of the other ones, the user has not won
					return false;
				}
			}
		}
		return true;
	}
	
	public static void setBoardSize(int size) {
		//sets the size of the array
		if (size < 5) {
			//if the user enters a number below 5, size gets set to 5
			size = 5;
		} else if (size > 30) {
			//if user enter a # over 30, size gets set to 30
			size = 30;
		}
		
		intArraysize = size;
	}
	
	public static void out(Object o) {
		//simplifies System.out.println (so that I can write out("String"); rather than System.out.println("String');
		System.out.println(o);
	}
	
	public void addCellNeighbors() {
		//sets the neighbors of all active cells (if neighbor has the same color)
		for (int i = 0; i < LabelArray[0].length; i++) {
			for (int j = 0; j < LabelArray[1].length; j++) {
				if (LabelArray[i][j].isActive == true) {
					if (i < LabelArray[0].length - 1) {
						//checks the neighbor to the right of active cell, as long as the active cell is not on the righmost column
						if (LabelArray[i+1][j].isActive == false && LabelArray[i+1][j].getBackground() == LabelArray[i][j].getBackground()) {
							//sets said neighbour to active if the color matches
							LabelArray[i+1][j].isActive = true;
							activeCells.add(LabelArray[i+1][j]);
						}
					}
					if (i > 0) {
						//checks the neighbour to the left of the active cell, as long as the active cell is not on the leftmost column
						if (LabelArray[i-1][j].isActive == false && LabelArray[i-1][j].getBackground() == LabelArray[i][j].getBackground()) {
							//sets said neighbour to active if the color matches
							LabelArray[i-1][j].isActive = true;
							activeCells.add(LabelArray[i-1][j]);
						}
					}
					if (j < LabelArray[1].length - 1) {
						//checks the neighbour to the borrom of the active cell, as long as the active cell is not on the bottom row
						if (LabelArray[i][j+1].isActive == false && LabelArray[i][j+1].getBackground() == LabelArray[i][j].getBackground()) {
							//sets said neighbour to active if the color matches
							LabelArray[i][j+1].isActive = true;
							activeCells.add(LabelArray[i][j+1]);
						}
					}
					if (j > 0) {
						//checks the neighbour to the top of the active cell, as long as the active cell is not on the top row
						if (LabelArray[i][j-1].isActive == false && LabelArray[i][j-1].getBackground() == LabelArray[i][j].getBackground()) {
							//sets said neighbor to active if the color matches
							LabelArray[i][j-1].isActive = true;
							activeCells.add(LabelArray[i][j-1]);
						}
					}
				}
			}
		}
	}
	
}

/* What was tested:
 * LabelArray - make sure the array of cells works and is shown and every cell in it is clickable
 * setBoardSize - make sure the size stays within 5 and 30 and the label array gets set to the same size
 * AddCellNeighbors - make sure each cell neighbour gets added and no other cells get added
 * setActiveColor - make sure all the active cells are the same color
 * checkWin - make sure user wins under right condition
 * intTurn - make sure it increases each time
 * Cell.toLabelAray - make sure it generates an array and doesn't cause an error and that the array size is correct
 * ColorArray - made sure all the colors are nice to look at
 */
