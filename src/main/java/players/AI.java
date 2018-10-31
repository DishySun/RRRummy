package players;

import java.util.ArrayList;

import game.View;
import rrrummy.Tile;

public class AI extends Player{
	private AIStrategy strategy;
	
	public AI(AIStrategy strategy) {
		super(strategy.name());
		this.strategy = strategy;
	}
	
	public String getCommandString(View v) {
		return strategy.generateCommand();
	}

	public AIStrategy getSrategy() {
		return strategy;
	}
}
