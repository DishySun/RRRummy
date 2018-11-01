package players;

import java.util.ArrayList;
import java.util.HashMap;

import observer.GameData;
import observer.Observer;
import observer.Subject;
import rrrummy.Meld;
import rrrummy.Tile;

public class StrategyZero implements AIStrategy, Observer{
	private Hand myHand;
	private ArrayList<Meld> table;
	private HashMap<Integer, Integer> playerHandSizes;
	private ArrayList<Tile> run;
	private ArrayList<Tile> group;
	private int countInitial;
	private boolean hasPlayInit;
	private boolean hasPlayRest;

	
	public StrategyZero(Subject data) {
		data.register(this);
		hasPlayInit = false;
		hasPlayRest = false;
		countInitial = 0;
	}
	
	@Override
	public String generateCommand() {
		hasPlayRest = false;
		run = new ArrayList<Tile>();
		group = new ArrayList<Tile>();
		if(countInitial < 30) {	//play initial
			if(myHand.checkInitialSum() >= 30-countInitial) {
				run = myHand.findRun();
				if(run != null) {
					countInitial += myHand.checkSum(run);
					hasPlayInit = true;
					return "Play" + run;
				}
				else {
					group = myHand.findGroup();
					if(group != null) {
						countInitial += myHand.checkSum(group);
						hasPlayInit = true;
						return "Play" + group;
					}	
					else
						return "Something wrong";
				}
			}	else
					return "DRAW";
		}else {
			run = myHand.findRun();
			if(run != null)	{
				hasPlayRest = true;
				return "Play" + run;
			} else {
				group  = myHand.findGroup();
				if(group != null) {
					hasPlayRest = true;
					return "Play" + group;
				}
			}
			if(hasPlayInit) {
				hasPlayInit  = false;
				return "END";
			} else {
				if(hasPlayRest)
					return "End";
				else
					return "DRAW";
			}	
		}
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
