package players;

import java.util.ArrayList;

import rrrummy.Meld;

public class StrategyFile implements AIStrategy{

	@Override
	public void update(ArrayList<Meld> table) {
	}

	@Override
	public void update(int playerId, int handSize) {
	}

	@Override
	public String getDifficulty() {
		return "Replay";
	}

	@Override
	public void setHand(Hand hand) {
		
	}

	@Override
	public void setMyId(int id) {
		
	}

	@Override
	public String generateCommand() {
		return null;
	}

}
