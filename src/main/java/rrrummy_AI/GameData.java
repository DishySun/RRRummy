package rrrummy_AI;

import java.util.ArrayList;

import rrrummy.*;

public class GameData implements Subject{
	private ArrayList<Observer> observers;
	private ArrayList<Player> players;
	private Table table;
	
	public GameData() {
		observers = new ArrayList<Observer>();
		players = new ArrayList<Player>();
	}
	
	@Override
	public void registerObserver(Observer o) {
		// TODO Auto-generated method stub
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		// TODO Auto-generated method stub
		int i = observers.indexOf(o);
		if (i >= 0) {
			observers.remove(i);
		}
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		for (int i = 0; i < observers.size(); i++) {
			Observer observer = (Observer)observers.get(i);
			observer.update(table, players);
		}
	}
	
	public void dataChanged() {
		notifyObservers();
	}
	
	public void setValue(Table t, ArrayList<Player> pls) {
		this.table = t;
		this.players = pls;
		dataChanged();
	}
	
	public int getObserversSize() {
		return observers.size();
	}
}
