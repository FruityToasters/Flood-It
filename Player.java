
public class Player {
	String name;
	int score;
	String difficulty;
	String stars;
	
	public Player() {
		
	}
	
	public void getStars() {
		if (this.difficulty.equalsIgnoreCase("easy")) {
			if (this.score <= 17) {
				this.stars = "Three stars! ***";
			} else if (this.score > 17 && this.score <= 20) {
				this.stars = "Two stars! **";
			} else {
				this.stars = "Only one star *";
			}
		} else if (this.difficulty.equalsIgnoreCase("medium")) {
			if (this.score <= 34) {
				this.stars = "Three stars! ***";
			} else if (this.score > 34 && this.score <= 38) {
				this.stars = "Two stars! **";
			} else {
				this.stars = "Only one star *";
			}
		} else {
			if (this.score <= 49) {
				this.stars = "Three stars! ***";
			} else if (this.score > 49 && this.score <= 53) {
				this.stars = "Two stars! **";
			} else {
				this.stars = "Only one star *";
			}
		}
	}
	
	public String toString() {
		return (this.name + ", " + this.score + ", " + this.difficulty);
	}
	
	public static Player parsePlayer(String s) {
		Player p = new Player();
		String d = s.replaceAll(" ", "");
					String[] playerSplit = d.split(",");
					p.name = playerSplit[0];
					p.score = Integer.parseInt(playerSplit[1]);
					p.difficulty = playerSplit[2];
		return p;
	}
	
	public boolean equals(Player p) {
		return (p.name.equals(this.name) && p.difficulty.equals(this.difficulty));
	}

}
