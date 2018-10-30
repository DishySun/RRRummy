package players;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import rrrummy.Tile;

public class Hand {
	private ArrayList<Tile> hand;
	
	public Hand(ArrayList<Tile> initHand) {
		hand = initHand;
		this.sort();
	}
	
	public Tile getTile(int i){return hand.get(i);}
	public void add(Tile t) {
		hand.add(t);
		this.sort();
	}
	public Tile remove(int i) {return hand.remove(i);}
	public int size() {return hand.size();}
	public Tile remove() {return hand.remove(size()-1);}
	public String toString() {
		String aString = "";
		for (int i = 0; i < hand.size(); i++) {
			aString+= hand.get(i).toString();
			if (i != hand.size()-1) aString+=", ";
		}
		return aString;
	}
	public void sort() {

		Collections.sort(hand, new Comparator<Tile>(){

			@Override
			public int compare(Tile t1, Tile t2) {
					if (t1.isGreaterThan(t2)) {
					return 1;
				}else return -1;
			}
		
		});
	}
}