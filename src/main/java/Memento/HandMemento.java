package Memento;

import java.util.ArrayList;

import players.Hand;
import rrrummy.Meld;
import rrrummy.Tile;

public class HandMemento {
	private final ArrayList<Tile> hand;
	public HandMemento(Hand h) {
		hand = new ArrayList<Tile>();
		for(Tile t : h.getHandList()) {
			Tile tempTile = new Tile(t);
			hand.add(tempTile);
		}
	}
 
	public ArrayList<Tile> getSaveData() {
		return hand;
	}
}
