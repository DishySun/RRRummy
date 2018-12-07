package rrrummy;
import java.util.ArrayList;

import Memento.HandMemento;
import Memento.TableMemento;
import observer.Observer;
import observer.Subject;
import players.Hand;

public class Table implements Subject{
	private ArrayList<Meld> table;
	private ArrayList<Observer> observers;
	private int lastModifiedMeld;
	public Table() {
		table = new ArrayList<Meld>();
		observers = new ArrayList<Observer>();
	}
	
	public int size() {return table.size();}
	
	public void add(Tile t) {
			table.add(new Meld(t));
			lastModifiedMeld = table.size() - 1;
			notifyObserver();
	}
	public boolean add(ArrayList<Tile> arr) {
		Meld m = Meld.newMeld(arr);
		if (m == null) return false;
		table.add(m);
		notifyObserver();
		lastModifiedMeld = table.size() - 1;
		return true;
	}
	public int add(Tile t, int i) throws AbleToAddBothSideException{
		if (i >= this.size()) return -1;
		int b = table.get(i).add(t);
		if(b >= 0) {
			lastModifiedMeld = i;
			notifyObserver();
		}
		return b;
	}
	
	public int addHead(Tile t, int i) {
		if (i >= this.size()) return -1;
		int b = table.get(i).addHead(t);
		if(b >= 0) {
			lastModifiedMeld = i;
			notifyObserver();
		}
		return b;
	}
	public int addTail(Tile t, int i) {
		if (i >= this.size()) return -1;
		int b = table.get(i).addTail(t);
		if(b >= 0) {
			lastModifiedMeld = i;
			notifyObserver();
		}
		return b;
	}
	
	public int move (int fromMeld, boolean removeHeadOrTail, int toMeld, boolean toHeadOrTail) {
		if(fromMeld >= table.size() || fromMeld < 0 || toMeld < 0 || toMeld >= table.size()) return -1;
		Tile t = null;
		if (removeHeadOrTail) t = table.get(fromMeld).removeHead();
		else t = table.get(fromMeld).removeTail();
		int b;
		if (toHeadOrTail) b = addHead(t, toMeld);
		else b = addTail(t, toMeld);
		if (b < 0) {
			if(removeHeadOrTail) addHead(t, fromMeld);
			else addTail(t, fromMeld);
			return -1;
		}
		lastModifiedMeld = toMeld;
		if(table.get(fromMeld).size() == 0) table.remove(fromMeld);
		return b;
	}
	public int move(int fromMeldIndex, int fromTileIndex, int toMeldIndex, boolean headOrTail) {
		Tile t = table.get(fromMeldIndex).getTile(fromTileIndex);
		//if (table.get(fromMeldIndex).size() ==0) table.remove(fromMeldIndex); 
		if (t == null) return -1;
		int i;
		if (headOrTail) i = table.get(toMeldIndex).addHead(t);
		else i = table.get(toMeldIndex).addTail(t);
		if (i >= 0) table.get(fromMeldIndex).remove(fromTileIndex);
		if (table.get(fromMeldIndex).size() == 0)table.remove(fromMeldIndex);
		return i;
	}
	public Tile removeHead(int i) {
		Tile t = table.get(i).removeHead();
		if (table.get(i).size() == 0) table.remove(i);
		if (t != null) notifyObserver();
		return t;
	}
	
	public Tile removeTail(int i) {
		Tile t = table.get(i).removeTail();
		if (table.get(i).size() == 0) table.remove(i);
		if (t != null) notifyObserver();
		return t;
	}
	
	public Tile replace(Tile t, int tableIndex, int meldIndex) {
		Tile tt = table.get(tableIndex).replace(t, meldIndex);
		if (tt != null) notifyObserver();
		return tt;
	}
	
	public boolean cut(int meldIndex, int tileIndex) {
		Meld m = table.get(meldIndex).cut(tileIndex);
		if (m == null) return false;
		table.add(m);
		lastModifiedMeld = table.size() - 1;
		notifyObserver();
		return true;
	}
	
	public String toString() {
		String result = "Table:\n";
		for (int i = 0; i < table.size(); i++) {
			result += i +". ";
			if(lastModifiedMeld == i) result += table.get(i).lastModifiedString();
			else result += table.get(i).toString();
			if (i < size() - 1) result+="\n";
		}
		return result;
	}
	
	public int lastMeldScore() {
		return table.get(size() - 1).getMeldScore();
	}
	
	public boolean isSet(int meldIndex) {
		if (meldIndex < 0 || meldIndex >= table.size()) return false;
		return table.get(meldIndex).isSet();
	}

	public void steTable(ArrayList<Meld> t) {
		table = t;
	}
	
	@Override
	public void register(Observer o) {
		observers.add(o);	
	}

	@Override
	public void unregister(Observer o) {
		int index = observers.indexOf(o);
		observers.remove(index);
	}

	@Override
	public void notifyObserver() {
		for (Observer o: observers) {
			o.update(table);
		}
	}

	public Table getTable() {return this;}
	public ArrayList<Meld> getTableMeld() { return table; }
	
	////// memento
	
	public void setState(ArrayList<Meld> m) {
		table = m;
	}
	
	public TableMemento Save() {
		return new TableMemento(this);
	}
	
	public void restoreToState(TableMemento memeto) {
		table = memeto.getSaveData();
	}
	
}
