import java.awt.*;
import java.util.*;
import javax.swing.JLabel;

public class Cell extends JLabel {
	//Cell class for each individual cell that is a part of LabelArray
	private static final long serialVersionUID = -3776485612595454858L;
	//no clue what the serialVersionUID is, Java gave me a warning to put it there.
	static Color[] hardColorArray = {new Color(145, 249, 97), new Color(6, 173, 134), 
			new Color(106, 69, 109), new Color(237, 0, 71), new Color(229, 128, 84), new Color(247, 233, 76)};
	
	static Color[] medColorArray = {new Color(145, 249, 97), new Color(6, 173, 134), 
	new Color(106, 69, 109), new Color(237, 0, 71)};
	
	static Color[] colorArray = {new Color(145, 249, 97), new Color(6, 173, 134), 
			new Color(106, 69, 109)};
	//Array of colors that holds the colors to be used for the cells
	int rndIndex;
	//int that holds a random index of the color Array
	Color c2;
	//color that the cells background color is going to be set to
	Random rnd;
	//random number generator
	final Dimension dimSize = new Dimension(20, 20);
	//size of cell
	public boolean isActive;
	//whether or not the cell is active
	public Cell() {
		rnd = new Random();
		
		c2 = colorArray[rndIndex];
		
		this.setBackground(c2);
		//sets the background of a cell to one of the colors in colorArray
		this.setSize(dimSize);
		//sets the size of the cell to the dimension dimSize
		this.setMinimumSize(dimSize);
		this.setPreferredSize(dimSize);
		this.setMaximumSize(dimSize);
		isActive = false;
		//initially all the cells are inactive
	}
	public static Cell[][] toLabelArray(Cell [][] c, int dim1Length, int dim2Length, String dif) {
		//takes a cell and makes a whole array of them
		//Cell[][] c = new Cell [dim1Length][dim2Length];
		int rndIndex;
		Random rnd;
		Color c2;
		
		if (dif.equalsIgnoreCase("Easy")) {
			rndIndex = rnd.nextInt(colorArray.length);
			c2 = colorArray[rndIndex];
		} else if (dif.equalsIgnoreCase("Medium")) {
			rndIndex = rnd.nextInt(medColorArray.length);
			c2 = medColorArray[rndIndex];
		} else if (dif.equalsIgnoreCase("Hard")) {
			rndIndex = rnd.nextInt(hardColorArray.length);
			c2 = hardColorArray[rndIndex];
		}
		
		this.setBackground(c2);
		for (int i = 0; i < c[0].length; i++) {
			for (int j = 0; j < c[1].length; j++) {
				c[i][j] = new Cell(dif);
			}
		}
		return c;
		
	}

}
