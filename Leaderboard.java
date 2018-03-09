import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import javax.swing.JLabel;

import org.apache.commons.io.*;
public class Leaderboard {
	List<Player> players;
	//P:/ICS3U/ColorDomination/ldb.txt
	//File ldb = new File("C:/Users/"+ System.getProperty("user.name") + "/Documents/ldb.txt");
	File ldb = new File("ldb.txt");
	int size;
	public Leaderboard() throws IOException {
		players = new ArrayList<Player>();
		
		ldb.createNewFile();
	}
	
	public void add(Player p) {
		int index = -1;
		if (players.size() == 0) {
			players.add(p);
		} else {
			//short n = Short.MAX_VALUE;
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).score > p.score) {
					index = i;
					break;
				}
			}
			
			if (index == - 1) {
				players.add(p);
			} else {
				players.add(index, p);
			}
			
			if (p.toString().length() > size) {
				size = p.toString().length();
			}
			
			
		}
		
	}
	
	public void loadBoard() throws IOException {
		List<String> temp = FileUtils.readLines(ldb, Charset.defaultCharset());
		if (temp.size() > 0) {
			for (String s : temp) {
				players.add(Player.parsePlayer(s));
			}
		}
	}
	
	public ArrayList<JLabel> printBoard() {
		//String leaderboard = "";
		ArrayList<JLabel> labels = new ArrayList<JLabel>();
		int count = 1;
		labels.add(new JLabel( "Leaderboard:"));
		labels.add(new JLabel("=============="));
		labels.add(new JLabel(" "));
		labels.add(new JLabel("Hard mode:"));
			for (Player p : players) {
				if (p.difficulty.equalsIgnoreCase("Hard") && count <= 10) {
					labels.add(new JLabel((count++) + ". " + p.toString().substring(0, p.toString().length() - 6)));
				}
			}
			count = 1;
			labels.add(new JLabel(" "));
		labels.add(new JLabel("Medium mode: "));
			for (Player p : players) {
				if (p.difficulty.equalsIgnoreCase("Medium") && count <= 10) {
					labels.add(new JLabel((count++) + ". " + p.toString().substring(0, p.toString().length() - 8)));
			}
		}
			count = 1;
			labels.add(new JLabel(" "));
		labels.add(new JLabel("\nEasy mode:"));
		for (Player p : players) {
			if (p.difficulty.equalsIgnoreCase("Easy") && count <= 10) {
				labels.add(new JLabel((count++) + ". " + p.toString().substring(0, p.toString().length() - 6) + "\n"));
			}
		}
		
		return labels;
		
	}
	
	public void saveBoard() throws IOException {
		FileUtils.deleteQuietly(ldb);
		FileUtils.writeLines(new File("ldb.txt"), players);
	}

}
