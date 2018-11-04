package players;

import java.util.ArrayList;

import game.View;
import rrrummy.Tile;

public class AI extends Player{
	private AIStrategy strategy;
	
	public AI(AIStrategy strategy) {
		super("Computer"+idTracker + " ("+strategy.getDifficulty()+")");
		this.strategy = strategy;
		this.strategy.setMyId(this.getId());
		this.strategy.setHand(hand);
	}
	
	public String getCommandString(View v) {
		return strategy.generateCommand();
	}
	public AIStrategy getStrategy() {return strategy;}
	public void initHand(ArrayList<Tile> arr) {
		hand = new Hand(arr);
		hand.sort();
		this.strategy.setHand(hand);
		notifyObserver();
	}
}

