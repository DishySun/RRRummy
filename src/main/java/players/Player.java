package players;

import java.util.ArrayList;

import game.View;
import gui_game.GameControl;
import rrrummy.Tile;
import observer.*;

public class Player implements Subject{
	private ArrayList<Observer> observers;
	private String name;
	protected Hand hand;
	private int playerId;
	protected static int idTracker = 0;
	private int playedScore;
	protected AIStrategy strategy;
	
	public Player(String name) {
		this.name = name;
		playerId = idTracker++;
		observers = new ArrayList<Observer>() ;
		strategy = new StrategyFour();
	}
	
	public String getName() {return name;}
	public int getId() {return playerId;}
	public boolean isHuman() {return true;}
	
	public void initHand(final ArrayList<Tile> arr) {
		this.playedScore = 0;
		hand = new Hand(arr);
		notifyObserver();
		strategy.setHand(hand);
		hand.sort();
	}
	
	public void draw(Tile t) {
		hand.add(t);
		notifyObserver();
	}
	
	public void sortHand(){
		hand.sort();
	}
	
	public Tile play(int index) {
		if (index >= handSize() || index < 0) return null;
		Tile t = hand.remove(index);
		notifyObserver();
		return t;
	}
	
	public Tile getTile(int i) {return hand.getTile(i);}
	public void printHand() {
		System.out.println(name+"'s hand: "+hand);
	}
	public String getCommandString() {
		return strategy.generateCommand();
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
	public String getHeadOrTail(String string) {
		return View.getHeadOrTail(string);
	}
	public AIStrategy getStrategy() {return strategy;}
	public void caluPlayedScore(int i) {playedScore += i;}
	public int getPlayerdSocre() {return playedScore;}

	@Override
	public void register(Observer o) {
		if (observers.contains(o)) return;
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

	public void getTurn(GameControl g) {
		g.humanTurn();
	}
}
