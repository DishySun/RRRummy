package rrrummy;
import java.util.ArrayList;

public class Hand {
	private ArrayList<Tile> hand;
	
	public Hand() {
		hand = new ArrayList<Tile>();
	}
	
	public ArrayList<Tile> getHand(){return hand;}
	public void add(Tile t) {hand.add(t);}
	public Tile remove(int i) {return hand.remove(i);}
	public int size() {return hand.size();}
	public Tile remove() {return hand.remove(size()-1);}
	public void sort() {
		//TODO: need to be implemented
		//use "boolean Tile.isGreaterThan(Tile)"
		
	}
}
