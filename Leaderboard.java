import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import org.apache.commons.io.*;
public class Leaderboard {
	List<Player> players;
	File ldb = new File("P:/ICS3U/ColorDomination/ldb.txt");
	public Leaderboard() throws IOException {
		players = new ArrayList<Player>();
	}
	
	public void add(Player p) {
		players.add(p);
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
		
		
		for (int i = 0; i < players.size(); i++) {
			System.out.println(players.get(i));
		}
		
	}
	
	public void saveBoard() throws IOException {
		FileUtils.deleteQuietly(ldb);
		FileUtils.writeLines(new File("P:/ICS3U/ColorDomination/ldb.txt"), players);
		for (Player p : players) {
			System.out.println(p);
		}
	}
	
	public void sortList() {
		int n = 0;
		int index = -1;
		List<Player> newList = new ArrayList<Player>();
		for (int i = 0; i < players.size(); i++) {
			for (int j = 0; j < players.size(); j++) {
				if (players.get(j).score > n && players.get(j).score != 0) {
					n = players.get(j).score;
					index = j;
				}
			}
			newList.add(players.get(index));
			players.get(index).score = 0;
		}
		
		players.clear();
		for (Player p : newList) {
			players.add(p);
		}
	}

}
