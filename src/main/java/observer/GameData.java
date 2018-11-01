package observer;

import java.util.ArrayList;
import java.util.HashMap;

import rrrummy.Meld;

public class GameData implements Subject{
	private ArrayList<Meld> table;
	private ArrayList<Observer> observers;
	HashMap<Integer, Integer> handSizes;
	
	public GameData() {
		observers = new ArrayList<Observer>();
		
	}
	
	@Override
	public void register(Observer o) {
		// TODO Auto-generated method stub
		//System.out.println(o.getClass());
		observers.add(o);
		//System.out.println(observers.size());
	}

	@Override
	public void unregister(Observer o) {
		// TODO Auto-generated method stub
		int i = observers.indexOf(o);
		if (i >= 0) {
			observers.remove(i);
		}
	}

	@Override
	public void notifyObserver() {
		// TODO Auto-generated method stub
		for (Observer o: observers) {
			o.update(handSizes);
			o.update(table);
		}
	}
	
	public void dataChanged() {
		notifyObserver();
	}
	
	public void setValue(ArrayList<Meld> t, HashMap<Integer, Integer> handS) {
		table = t;
		handSizes = handS;
		notifyObserver();
	}
}
