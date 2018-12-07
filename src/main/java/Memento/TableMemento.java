package Memento;

import java.util.ArrayList;

import rrrummy.Meld;
import rrrummy.Table;

public class TableMemento {
	private final ArrayList<Meld> melds;
	
	public TableMemento(ArrayList<Meld> t) {
		this.melds = t;
	}
	
	public ArrayList<Meld> getSaveData() {
		return melds;
	}
}
