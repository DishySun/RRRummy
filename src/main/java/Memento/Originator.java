package Memento;

import java.util.ArrayList;

import players.Hand;
import players.Player;
import rrrummy.Meld;
import rrrummy.Tile;

public class Originator {
	private ArrayList<Meld> table;
	private Hand playerHand;
	
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
	
	public Hand getPlayerHand() {
		return playerHand;
	}
 
	public void setState(ArrayList<Meld> tab, Hand handt) {
		table = new ArrayList<Meld>();
		for(Meld meld : tab) {
			Meld tempM = new Meld(meld);
			table.add(tempM);
		}
		playerHand = new Hand(handt);
		
	}
}
