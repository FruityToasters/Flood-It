
public class Player {
	String name;
	int score;
	String difficulty;
	
	public Player() {
		
	}
	
	public String toString() {
		return (this.name + "," + this.score + "," + this.difficulty);
	}
	
	public static Player parsePlayer(String s) {
		Player p = new Player();
					String[] playerSplit = s.split(",");
					p.name = playerSplit[0];
					p.score = Integer.parseInt(playerSplit[1]);
					p.difficulty = playerSplit[2];
		return p;
	}
	
	public boolean equals(Player p) {
		return (p.name.equals(this.name) && p.difficulty.equals(this.difficulty));
	}

}
