package rrrummy;
import java.util.ArrayList;

import observer.Observer;
import observer.Subject;

public class Table implements Subject{
	private ArrayList<Meld> table;
	private ArrayList<Observer> observers;
	public Table() {
		table = new ArrayList<Meld>();
		observers = new ArrayList<Observer>();
	}
	
	public int size() {return table.size();}
	public void add(Tile t) {
			table.add(new Meld(t));
			notifyObserver();
	}
	public boolean add(ArrayList<Tile> arr) {
		Meld m = Meld.newMeld(arr);
		if (m == null) return false;
		table.add(m);
		notifyObserver();
		return true;
	}
	public boolean add(Tile t, int i) throws AbleToAddBothSideException{
		if (i >= this.size()) return false;
		boolean b = table.get(i).add(t);
		if(b) notifyObserver();
		return b;
	}
	
	public boolean addHead(Tile t, int i) {
		if (i >= this.size()) return false;
		boolean b = table.get(i).addHead(t);
		if(b) notifyObserver();
		return b;
	}
	public boolean addTail(Tile t, int i) {
		if (i >= this.size()) return false;
		boolean b = table.get(i).addTail(t);
		if(b) notifyObserver();
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
		notifyObserver();
		return true;
	}
	
	public String toString() {
		String result = "Table:\n";
		for (int i = 0; i < table.size(); i++) {
			result += i +". "+ table.get(i).toString();
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
