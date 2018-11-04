package players;

import java.util.ArrayList;

import game.View;
import rrrummy.Tile;
import observer.*;

public class Player implements Subject{
	private ArrayList<Observer> observers;
	private String name;
	protected Hand hand;
	private int playerId;
	protected static int idTracker = 0;
	
	public Player(String name) {
		this.name = name;
		playerId = idTracker++;
		observers = new ArrayList<Observer>() ;
	}
	
	public String getName() {return name;}
	public int getId() {return playerId;}
	
	public void initHand(ArrayList<Tile> arr) {
		hand = new Hand(arr);
		hand.sort();
		notifyObserver();
	}
	
	public void draw(Tile t) {
		hand.add(t);
		hand.sort();
		notifyObserver();
	}
	
	public Tile play(int index) {
		if (index >= handSize() || index < 0) return null;
		notifyObserver();
		return hand.remove(index);
	}
	
	public Tile getTile(int i) {return hand.getTile(i);}
	public void printHand() {
		System.out.println(name+"'s hand: "+hand);
	}
	public String getCommandString(View v) {
		return v.getCommand();
	}
	public int handSize() {return hand.size();}
	public Tile getHand(int i) {
		if (i >= handSize() || i < 0) return null;
		return hand.getTile(i);
	}
	public int getHand(Tile t) {
		if (!hand.contaions(t)) return -1;
		return hand.indexOf(t);
	}
	public boolean handContains(Tile t) {
		return hand.contaions(t);
	}
	public String getHeadOrTail(String string, View view) {
		return view.getHeadOrTail(string);
	}
	public AIStrategy getStrategy() {return null;}

	@Override
	public void register(Observer o) {
		observers.add(o);
	}

	@Override
	public void unregister(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObserver() {
		for (Observer o: observers) {
			o.update(this.getId(), handSize());
		}
	}
}
