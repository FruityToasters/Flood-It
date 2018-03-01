import java.util.ArrayList;

public class Player {
	Deck deck;
	int num;
	String ape;
	boolean canGo;
	String name;
	
	public Player() {
		this.deck = new Deck(3); 
		this.num = 0;
		this.ape = "";
		this.canGo = true;
		this.name = "Player " + this.num;
	}
	
	public Player(int n) {
		this.deck = new Deck(3); 
		this.num = n;
		this.ape = "";
		this.canGo = true;
		this.name = "Player " + this.num;
	}
	
	public void printApe() {
		System.out.println(this.name + ", you have: " + this.ape.toUpperCase());
	}
	
	

}
