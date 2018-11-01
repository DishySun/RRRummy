package players;

import game.View;

public class AI extends Player{
	private AIStrategy strategy;
	
	public AI(AIStrategy strategy) {
		super("Computer"+idTracker + " ("+strategy.getDifficulty()+")");
		this.strategy = strategy;
	}
	
	public String getCommandString(View v) {
		return strategy.generateCommand();
	}
	

	public AIStrategy getSrategy() {
		return strategy;
	}
}
