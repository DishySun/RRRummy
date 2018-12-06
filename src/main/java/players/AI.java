package players;

import gui_game.GameControl;

public class AI extends Player{
	//private AIStrategy strategy;
	
	public AI(AIStrategy strategy) {
		super("Computer"+idTracker + " ("+strategy.getDifficulty()+")");
		this.strategy = strategy;
		this.strategy.setMyId(this.getId());
		this.strategy.setHand(hand);
	}
	
	public String getCommandString() {
		return strategy.generateCommand();
	}
	public AIStrategy getStrategy() {return strategy;}
	
	public void getTurn(GameControl g) {
		g.aiTurn();
	}
}

