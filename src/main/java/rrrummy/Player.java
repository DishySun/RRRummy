package rrrummy;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

public class Player {
	private Hand hand;
	private int score;
	private String name;
	public Player(String n) {
		name = n;
		hand = new Hand();
		score = 0;
	}
	public void draw(Tile tile) {
		// TODO Auto-generated method stub
		hand.add(tile);
	}
	public int handSize() {
		return hand.size();
		// TODO Auto-generated method stub
	}
	public Tile getHand(int i) {
		// TODO Auto-generated method stub
		return hand.getTile(i);
	}
	public Tile play(int i) {
		// TODO Auto-generated method stub
		return hand.remove(i);
	}
	
	public String toString() {
		String str = "";
		str += "Player: " + name + " \n";
		str += "Hand:  ";
		for(int i=0; i<hand.size();i++) {
			str += hand.getTile(i) + " ";
		}
		return str;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}
	
}
