package players;

import java.util.ArrayList;

import rrrummy.Tile;

public class AI extends Player{
	private AIStrategy strategy;
	
	public AI(AIStrategy strategy) {
		super(strategy.name());
		this.strategy = strategy;
	}
	
	public void initHand(ArrayList<Tile> arr) {
		hand = new Hand(arr);
	}
	
	public String generateCommand() {
		return strategy.generateCommand();
	}

}
