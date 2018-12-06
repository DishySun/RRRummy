package Memento;

import java.util.ArrayList;

import players.Hand;
import rrrummy.Meld;
import rrrummy.Tile;

public class Memento {
	private ArrayList<Meld> table;
	private Hand playerHand;
	 
	public Memento(Originator originator) {
		this.table = originator.getTable();
		this.playerHand = originator.getPlayerHand();
	}
 
	public ArrayList<Meld> getTable(){
		return table;
	}
	
	public Hand getPlayerHand() {
		return playerHand;
	}
 
	public void setState(ArrayList<Meld> t, Hand p) {
		this.table = t;
		this.playerHand =p;
	}
}
