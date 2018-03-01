/* 6/5/2017
 * Color domination game (based on flood-it)
 * Game where you click on a color and the top right cell changes to that color then you click on another color and the top right cell and all the cells that are touching that cell that have the same color change to the coor that you clicked on and so on...
 */
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class CellGameLogic extends CellFrame implements MouseListener {
	public static Leaderboard l;
	public static Player p;
	private static final long serialVersionUID = 8319933255121639927L;
	ArrayList<Cell> activeCells = new ArrayList<Cell>();
	public static String difficulty;
	//ArrayList that holds the active cells. Active cells are the cells that ones that get added to the ones that change colours on click
	static JFrame frame;
	static int intTurn; 
	static boolean gameOver = false;
	//Holds the number of turns that user took
	Random rnd = new Random();
	//Random number generator
	Timer t = new Timer();
	//Timer
	public CellGameLogic(String name) { //Constructor
		super(name, difficulty);
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

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException{
		//System.out.println(l.medPlayers);
		Scanner scan = new Scanner(System.in);
		out("Would you like easy, medium or hard?");
		difficulty = "";
		do  {
			difficulty = scan.nextLine();
		} while (!(difficulty.equalsIgnoreCase("easy") || difficulty.equalsIgnoreCase("medium") || difficulty.equalsIgnoreCase("hard")));
		//user sets their level of difficulty, which is the size of the board
		if (difficulty.equalsIgnoreCase("Easy")) {
			setBoardSize(10);
		} else if (difficulty.equalsIgnoreCase("Medium")) {
			setBoardSize(20);
		} else if (difficulty.equalsIgnoreCase("Hard")) {
			setBoardSize(30);
		}
		difficulty = difficulty.toLowerCase();
		//setBoardSize(boardSize);
		LabelArray = Cell.toLabelArray(intArraysize,  intArraysize, difficulty);
		//sets label array size to what the user chose
		intTurn = 0;
		//turns start at 0
		frame = new CellGameLogic("Color Domination Game");
		frame.setSize(LabelArray[0].length * (LabelArray[0][0].getWidth()) + LabelArray[0][0].getWidth(), 
				LabelArray[1].length * (LabelArray[0][0].getHeight()) + LabelArray[0][0].getHeight() + 20);
		//sets the size of the frame proportional to the array of labels
		frame.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e){
		if (gameOver == false) {
			intTurn += 1;
			//each time a label is clicked, turns increase by 1
			setActiveColors((Cell) e.getSource());
			// al the active cells get the color of the cell that is clicked
			addCellNeighbors();
		}
		if (checkWin()) {
			gameOver = true;
			//if user wins:
			JOptionPane.showMessageDialog(null, "YOU DID IT. Your score : " + (intTurn) 
					+ "\n(The lower the better!)");
			try {
				gameOver();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			t.scheduleAtFixedRate(colourFlash, 0, 50);
			
			
		}
	}
	ArrayList<Boolean> colourBooleans = new ArrayList<Boolean>();
	ArrayList<Cell> winCells = new ArrayList<Cell>();
	public TimerTask colourFlash = new TimerTask() {
		//timer task that makes all the cells ranomly change to a bright color
		int intTime = 10000;
        public void run() {
            for (Cell c: activeCells) {
            	Color color = winColor;
            	getColourBooleans(color);
            	LabelArray[0][0].setBackground(cancerColors[rnd.nextInt(cancerColors.length)]);
          	  	c.setBackground(cancerColors[rnd.nextInt(cancerColors.length)]);
          	  	setWinColours(color);
          	  	
          	  	if (colourBooleans.contains(false) == false) {
          	  		cancerColors[cancerColors.length - 1] = Color.green;
          	  	}
          	  	
          	  	for (int i = 0; i < LabelArray[0].length; i++) {
          	  		
          	  	}
            }
            intTime -= 1;
            if (intTime == 0) {
          	  return;
            }
        }
	};
	
	public static void gameOver() throws IOException{
		l = new Leaderboard();
		p = new Player();
		p.score = intTurn;
		System.out.println("Please enter your name:");
		Scanner scan = new Scanner(System.in);
		String name = scan.nextLine();
		scan.close();
		p.name = name;
		p.difficulty = difficulty;
		
		l.loadBoard();
		l.add(p);
		l.saveBoard();
		l.printBoard();
	}
	
	public void getColourBooleans(Color c) {
		colourBooleans.clear();
		winCells.clear();
		
		for (int i = 4; i <= 6; i++) {
			winCells.add(LabelArray[4][i]);
			if (LabelArray[4][i].getBackground() == c) {
				colourBooleans.add(true); 
			} else {
				colourBooleans.add(false);
			}
		}
		
		for (int i = 6; i <= 8; i++) {
			winCells.add(LabelArray[5][i]);
			if (LabelArray[5][i].getBackground() == c) {
				colourBooleans.add(true);
			} else {
				colourBooleans.add(false);
			}
		}
		
		for (int i = 4; i <= 6; i++) {
			winCells.add(LabelArray[6][i]);
			if (LabelArray[6][i].getBackground() == c) {
				colourBooleans.add(true); 
			} else {
				colourBooleans.add(false);
			}
		}
		
		for (int i = 5; i <= 8; i++) {
			winCells.add(LabelArray[8][i]);
			if (LabelArray[8][i].getBackground() == c) {
				colourBooleans.add(true); 
			} else {
				colourBooleans.add(false);
			}
		}
		
		winCells.add(LabelArray[9][5]);
		if (LabelArray[9][5].getBackground() == c) {
			colourBooleans.add(true);
		} else {
			colourBooleans.add(false);
		}
		
		winCells.add(LabelArray[9][8]);
		if (LabelArray[9][8].getBackground() == c) {
			colourBooleans.add(true);
		} else {
			colourBooleans.add(false);
		}
		
		for (int i = 5; i <= 8; i++) {
			winCells.add(LabelArray[10][i]);
			if (LabelArray[10][i].getBackground() == c) {
				colourBooleans.add(true); 
			} else {
				colourBooleans.add(false);
			}
		}
		
		for (int i = 5; i <= 8; i++) {
			winCells.add(LabelArray[12][i]);
			if (LabelArray[12][i].getBackground() == c) {
				colourBooleans.add(true); 
			} else {
				colourBooleans.add(false);
			}
		}
		
		winCells.add(LabelArray[13][8]);
		if (LabelArray[13][8].getBackground() == c) {
			colourBooleans.add(true);
		} else {
			colourBooleans.add(false);
		}
		
		for (int i = 5; i <= 8; i++) {
			winCells.add(LabelArray[14][i]);
			if (LabelArray[14][i].getBackground() == c) {
				colourBooleans.add(true); 
			} else {
				colourBooleans.add(false);
			}
		}
		
		for (int i = 10; i <= 13; i++) {
			winCells.add(LabelArray[4][i]);
			if (LabelArray[4][i].getBackground() == c) {
				colourBooleans.add(true);
			} else {
				colourBooleans.add(false);
			}
		}
		
		winCells.add(LabelArray[5][14]);
		if (LabelArray[5][14].getBackground() == c) {
			colourBooleans.add(true);
		} else {
			colourBooleans.add(false);
		}
		
	
		winCells.add(LabelArray[6][13]);
		if (LabelArray[6][13].getBackground() == c) {
			colourBooleans.add(true);
		} else {
			colourBooleans.add(false);
		}
		
		
		winCells.add(LabelArray[7][14]);
		if (LabelArray[7][14].getBackground() == c) {
			colourBooleans.add(true);
		} else {
			colourBooleans.add(false);
		}
		
		for (int i = 10; i <= 13; i++) {
			winCells.add(LabelArray[8][i]);
			if (LabelArray[8][i].getBackground() == c) {
				colourBooleans.add(true);
			} else {
				colourBooleans.add(false);
			}
		}
		
		for (int i = 12; i <= 14; i++) {
			winCells.add(LabelArray[10][i]);
			if (LabelArray[10][i].getBackground() == c) {
				colourBooleans.add(true);			
			} else {
				colourBooleans.add(false);
			}
		}
		
		winCells.add(LabelArray[10][10]);
		if (LabelArray[10][10].getBackground() == c) {
			colourBooleans.add(true);
		} else {
			colourBooleans.add(false);
		}
		
		for (int i = 11; i <= 14; i++) {
			winCells.add(LabelArray[12][i]);
			if (LabelArray[12][i].getBackground() == c) {
				colourBooleans.add(true);
			} else {
				colourBooleans.add(false);
			}
		}
		
		winCells.add(LabelArray[13][11]);
		if (LabelArray[13][11].getBackground() == c) {
			colourBooleans.add(true);
		} else {
			colourBooleans.add(false);
		}
		
		for (int i = 11; i <= 14; i++) {
			winCells.add(LabelArray[14][i]);
			if (LabelArray[14][i].getBackground() == c) {
				colourBooleans.add(true);
			} else {
				colourBooleans.add(false);
			}
		}
	}
	
	public void setWinColours(Color c) {
		for (int i = 0; i < winCells.size(); i++) {
			if (colourBooleans.get(i) == true) {
				winCells.get(i).setBackground(c);
			}
		}
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
