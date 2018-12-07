package Memento;

import java.util.ArrayList;

import rrrummy.Meld;
import rrrummy.Table;

public class TableMemento {
	private final ArrayList<Meld> melds;
	public TableMemento(Table t) {
		melds = new ArrayList<Meld>();
		for(Meld m : t.getTableMeld()) {
			Meld tempM = new Meld(m);
			melds.add(tempM);
		}
	}
	
	public ArrayList<Meld> getSaveData() {
		return melds;
	}
}
