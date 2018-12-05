package Memento;

import java.util.ArrayList;

import players.Player;
import rrrummy.Meld;
import rrrummy.Tile;

public class Originator {
	private ArrayList<Meld> table;
	private ArrayList<Tile> playerHand;
	
	/*
	 * Create a new Memento
	 * @return
	 */
	
	public Memento createMemento() {
		return new Memento(this);
	}
	
	/*
	 * restore state
	 * @param o
	 */
	
	public void restoreMemento(Memento o) {
		this.table = o.getTable();
		this.playerHand = o.getPlayerHand();
	}
	
	public ArrayList<Meld> getTable(){
		return table;
	}
	
	public ArrayList<Tile> getPlayerHand() {
		return playerHand;
	}
 
	public void setState(ArrayList<Meld> tab, ArrayList<Tile> handt) {
		table = new ArrayList<Meld>();
		for(Meld meld : tab) {
			Meld tempM = new Meld(meld);
			table.add(tempM);
		}
		playerHand = new ArrayList<Tile>();
		for(Tile h : handt) {
			Tile tempT = new Tile(h);
			playerHand.add(tempT);
		}
	}
}
