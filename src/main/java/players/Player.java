package players;

import java.util.ArrayList;

import game.View;
import rrrummy.Tile;

public class Player {
	private String name;
	protected Hand hand;
	private int playerId;
	protected static int idTracker = 0;
	
	public Player(String name) {
		this.name = name;
		playerId = idTracker++;
	}
	
	public String getName() {return name;}
	public int getId() {return playerId;}
	
	public void initHand(ArrayList<Tile> arr) {
		hand = new Hand(arr);
		hand.sort();
	}
	
	public void draw(Tile t) {
		hand.add(t);
		hand.sort();
	}
	
	public Tile play(int index) {
		if (index >= handSize() || index < 0) return null;
		return hand.remove(index);
	}
	
	public Tile getTile(int i) {return hand.getTile(i);}
	public void printHand() {
		System.out.println(name+"'s hand: "+hand);
	}
	public String getCommandString(View v) {
		return v.getCommand();
	}
	public int handSize() {return hand.size();}
	public Tile getHand(int i) {
		if (i >= handSize() || i < 0) return null;
		return hand.getTile(i);
	}
}
