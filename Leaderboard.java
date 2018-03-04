import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import org.apache.commons.io.*;
public class Leaderboard {
	List<Player> players;
	//P:/ICS3U/ColorDomination/ldb.txt
	File ldb = new File("C:/Users/"+ System.getProperty("user.name") + "/Documents/ldb.txt");
	public Leaderboard() throws IOException {
		players = new ArrayList<Player>();
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
			
			players.add(index, p);
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
	
	public void printBoard() {
		System.out.println("Leaderboard:\n==================");
		
		System.out.println("Hard mode: ");
			for (Player p : players) {
				if (p.difficulty.equalsIgnoreCase("Hard")) {
					System.out.println(p.toString().substring(0, p.toString().length() - 5));
				}
			}
		System.out.println("\nMedium mode: ");
			for (Player p : players) {
				if (p.difficulty.equalsIgnoreCase("Medium")) {
					System.out.println(p.toString().substring(0, p.toString().length() - 7));
			}
		}
		System.out.println("\nEasy mode");
		for (Player p : players) {
			if (p.difficulty.equalsIgnoreCase("Easy")) {
				System.out.println(p.toString().substring(0, p.toString().length() - 5));
			}
		}
		
	}
	
	public void saveBoard() throws IOException {
		FileUtils.deleteQuietly(ldb);
		FileUtils.writeLines(new File("C:/Users/"+ System.getProperty("user.name") + "/Documents/ldb.txt"), players);
	}

}
