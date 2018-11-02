package players;

import java.util.ArrayList;
import java.util.HashMap;
import rrrummy.Meld;

public class StrategyZero implements AIStrategy{
	private Hand myHand;
	private int myId;
	private ArrayList<Meld> table;
	private HashMap<Integer, Integer> playerHandSizes;
	
	public StrategyZero(){
		playerHandSizes = new HashMap<Integer, Integer>();
	}
	
	@Override
	public String generateCommand() {
		return "END";
	}
	
	public void setMyId(int id) { myId = id;}
	
	
	@Override
	public String getDifficulty() {
		return "Retard";
	}
	@Override
	public void update(ArrayList<Meld> table) {
		this.table = table;
	}
	@Override
	public void setHand(Hand hand) {
		myHand = hand;
	}


	@Override
	public void update(int playerId, int handSize) {
		// do nothing coz this Strategy doesn't need to know other player's hand size
		playerHandSizes.put(playerId, handSize);
	}
	
}
