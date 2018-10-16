package rrrummy_AI;

import java.util.ArrayList;

import rrrummy.*;

public class GameData implements Subject{
	private ArrayList<Observer> observers;
	private Table table;
	private Player players;
	
	public GameData() {
		observers = new ArrayList<Observer>();
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
	
	public int getObserversSize() {
		return observers.size();
	}
}
