package players;

import java.util.ArrayList;

import rrrummy.Tile;

public class Player {
	private String name;
	protected Hand hand;
	private int playerId;
	static private int idTracker = 0;
	
	public Player(String name) {
		this.name = name;
		playerId = idTracker++;
	}
	
	public String getName() {return name;}
	public int getId() {return playerId;}
	
	public void initHand(ArrayList<Tile> arr) {
		hand = new Hand(arr);
	}
	
	public void draw(Tile t) {
		hand.add(t);
	}
	
	public Tile play(int index) {
		return hand.remove(index);
	}
	
	public Tile getTile(int i) {return hand.getTile(i);}
	public void printHand() {
		System.out.println(name+"'s hand: "+hand);
	}
	/*public String getCommand(View v) {
	 * TODO: need to be implemented
		return v.getCommand();
	}*/
}
