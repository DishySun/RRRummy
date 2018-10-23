package rrrummy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Hand {
	private ArrayList<Tile> hand;
	
	public Hand() {
		hand = new ArrayList<Tile>();
	}
	
	public Tile getTile(int i){return hand.get(i);}
	public void add(Tile t) {hand.add(t);}
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
		//TODO: need to be implemented
		//use "boolean Tile.isGreaterThan(Tile)"
		Collections.sort(hand, new Comparator<Tile>(){

			@Override
			public int compare(Tile t1, Tile t2) {
				// TODO Auto-generated method stub
				if (t1.isGreaterThan(t2)) {
					return 1;
				}else return -1;
			}
		
	});
}

	public void sortByNum() {
		// TODO Auto-generated method stub
		Collections.sort(hand, new Comparator<Tile>(){

			@Override
			public int compare(Tile t1, Tile t2) {
				// TODO Auto-generated method stub
				if (t1.isEqualThan(t2)) {
					return 1;
				}else return -1;
			}
		});
	}
}