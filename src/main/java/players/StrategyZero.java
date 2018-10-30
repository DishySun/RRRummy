package players;

import java.util.ArrayList;
import java.util.HashMap;

import observer.Observer;
import rrrummy.Meld;

public class StrategyZero implements AIStrategy, Observer{
	private Hand myHand;
	private ArrayList<Meld> table;
	private HashMap<Integer, Integer> playerHandSizes;
	
	@Override
	public String generateCommand() {
		return "END";
	}
	
	
	@Override
	public String name() {
		return "Computer (Very Easy)";
	}
	@Override
	public void update(ArrayList<Meld> table) {
		this.table = table;
	}
	@Override
	public void update(HashMap<Integer, Integer> handSizes) {
		this.playerHandSizes = handSizes;
		
	}
	@Override
	public void setHand(Hand hand) {
		myHand = hand;
	}
	
}
