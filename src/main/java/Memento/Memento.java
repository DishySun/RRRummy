package Memento;

import java.util.ArrayList;

import rrrummy.Meld;
import rrrummy.Tile;

public class Memento {
	private ArrayList<Meld> table;
	private ArrayList<Tile> playerHand;
	 
	public Memento(Originator originator) {
		this.table = originator.getTable();
		this.playerHand = originator.getPlayerHand();
	}
 
	public ArrayList<Meld> getTable(){
		return table;
	}
	
	public ArrayList<Tile> getPlayerHand() {
		return playerHand;
	}
 
	public void setState(ArrayList<Meld> t, ArrayList<Tile> p) {
		this.table = t;
		this.playerHand =p;
	}
}
