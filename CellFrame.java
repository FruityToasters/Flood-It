import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

@SuppressWarnings("serial")
public class CellFrame extends JFrame{
	Timer t = new Timer();
	static Cell label = new Cell(); 
	//Cell that the LabelArray will originate from
	static int intArraysize;
	//holds the size of the array
	ArrayList<Cell> activeCells = new ArrayList<Cell>();
	public static Cell[][] LabelArray;
	//Array of labels (the actual board that is used to be played on
	static JPanel mypanel = new JPanel();
	//panl that holds the labelArray
	static Color winColor = Color.black;
	static Color[] cancerColors = {Color.blue, Color.magenta, Color.green, Color.orange,
			Color.red, Color.yellow, Color.cyan, Color.blue, Color.magenta, Color.green, Color.orange,
			Color.red, Color.yellow, Color.cyan, Color.white,
			Color.magenta, Color.green, Color.orange, Color.yellow, Color.cyan, Color.black};
	//Bright colors that will flash at the end
	public CellFrame(String name) {
		//the JFrame (only the GUI)
		super(name);
		setBackground(Color.gray);
		//setLocation(100, 100);
		mypanel.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		//setUndecorated(true);
		//make sure the JPanel does not have a layout manager
		//mypanel.setAlignmentX(CENTER_ALIGNMENT);
		//mypanel.setAlignmentY(CENTER_ALIGNMENT);
		for (int i = 0; i < LabelArray[0].length; i++) {
			for (int j = 0; j < LabelArray[1].length; j++) {
				LabelArray[i][j].setVisible(true);
				LabelArray[i][j].setOpaque(true);
				LabelArray[i][j].setLocation(i * LabelArray[i][j].getHeight(), j * LabelArray[i][j].getWidth());
				mypanel.add(LabelArray[i][j]);
				//adds the label array to the JPanel as well as sets the location of each label on the JPanel
			}
		}
		getContentPane().add(mypanel);
		//adds the JPanel to the JFrame
		setSize(LabelArray[0][0].getWidth() * 20, LabelArray[0][0].getHeight() * 20);
		//sets the size of the frame proportionate to the size of the array
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	ArrayList<Boolean> colourBooleans = new ArrayList<Boolean>();
	ArrayList<Cell> winCells = new ArrayList<Cell>();
	
	TimerTask colourFlash = new TimerTask() {
		//timer task that makes all the cells ranomly change to a bright color
		int intTime = 10000;
        public void run() {
        	Random rnd = new Random();
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

	
}
