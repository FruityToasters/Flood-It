import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

@SuppressWarnings("serial")
public class CellFrame extends JFrame{
	
	static Cell label = new Cell();
	//Cell that the LabelArray will originate from
	static int intArraysize;
	//holds the size of the array
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
	
}
