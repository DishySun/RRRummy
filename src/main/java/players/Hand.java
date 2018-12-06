package players;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import rrrummy.AbleToAddBothSideException;
import rrrummy.InvalidTileException;
import rrrummy.Meld;
import rrrummy.Tile;

public class Hand {
	private ArrayList<Tile> hand;
	
	public Hand(ArrayList<Tile> initHand) {
		hand = initHand;
	}
	
	//copy constructor
	public Hand(Hand h) {
		//this.hand = h.hand;
		hand = new ArrayList<Tile>();
		System.out.println("System:"+h);
		System.out.println(h.hand);
		for(Tile t : h.hand)
			hand.add(t);
	}
	
	public Tile getTile(int i){return hand.get(i);}
	public void add(Tile t) {
		hand.add(t);
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
	public ArrayList<Integer> sort() {
		ArrayList<Integer> sortedIndex = new ArrayList<Integer>();
		ArrayList<Tile> temp = new ArrayList<Tile>();
		temp.addAll(hand);
		Collections.sort(hand, new Comparator<Tile>(){

			@Override
			public int compare(Tile t1, Tile t2) {
					if (t1.isGreaterThan(t2)) {
					return 1;
				}else return -1;
			}
		
		});
		for (Tile t: hand) {
			sortedIndex.add(temp.indexOf(t));
		}
		return sortedIndex;
	}
	// hand color order after sorted: BLUE, RED, GREEN, ORANGE,JOKER
	public boolean contaions(Tile t) {
		return hand.contains(t);
	}
	public int indexOf(Tile t) {
		return hand.indexOf(t);
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
	
	public int handIndexOf(Tile tile) {
		sort();
		return hand.indexOf(tile);
	}
}