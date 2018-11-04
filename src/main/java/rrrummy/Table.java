package rrrummy;
import java.util.ArrayList;

import observer.Observer;
import observer.Subject;

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
	public boolean add(Tile t, int i) throws AbleToAddBothSideException{
		if (i >= this.size()) return false;
		boolean b = table.get(i).add(t);
		if(b) {
			lastModifiedMeld = i;
			notifyObserver();
		}
		return b;
	}
	
	public boolean addHead(Tile t, int i) {
		if (i >= this.size()) return false;
		boolean b = table.get(i).addHead(t);
		if(b) {
			lastModifiedMeld = i;
			notifyObserver();
		}
		return b;
	}
	public boolean addTail(Tile t, int i) {
		if (i >= this.size()) return false;
		boolean b = table.get(i).addTail(t);
		if(b) {
			lastModifiedMeld = i;
			notifyObserver();
		}
		return b;
	}
	public boolean move (int fromMeld, boolean removeHeadOrTail, int toMeld) throws AbleToAddBothSideException{
		if(fromMeld >= table.size() || fromMeld < 0 || toMeld < 0 || toMeld >= table.size()) return false;
		Tile t = null;
		if (removeHeadOrTail) t = table.get(fromMeld).removeHead();
		else t = table.get(fromMeld).removeTail();
		boolean b = false;
		try {
			b = add(t, toMeld);
		}catch (AbleToAddBothSideException e) {
			if (removeHeadOrTail)  addHead(t, fromMeld);
			else addTail(t, fromMeld);
			throw new AbleToAddBothSideException(null, null);
		}
		if(!b) {
			if (removeHeadOrTail)  addHead(t, fromMeld);
			else addTail(t, fromMeld);
			return false;
		}
		if(table.get(fromMeld).size() == 0) table.remove(fromMeld);
		lastModifiedMeld = toMeld;
		return true;
	}
	
	public boolean move (int fromMeld, boolean removeHeadOrTail, int toMeld, boolean toHeadOrTail) {
		if(fromMeld >= table.size() || fromMeld < 0 || toMeld < 0 || toMeld >= table.size()) return false;
		Tile t = null;
		if (removeHeadOrTail) t = table.get(fromMeld).removeHead();
		else t = table.get(fromMeld).removeTail();
		boolean b = false;
		if (toHeadOrTail) b = addHead(t, toMeld);
		else b = addTail(t, toMeld);
		if (!b) {
			if(removeHeadOrTail) addHead(t, fromMeld);
			else addTail(t, fromMeld);
			return false;
		}
		lastModifiedMeld = toMeld;
		if(table.get(fromMeld).size() == 0) table.remove(fromMeld);
		return b;
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
}
