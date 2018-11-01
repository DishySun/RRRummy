package players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import observer.Observer;
import observer.Subject;
import rrrummy.Meld;
import rrrummy.Tile;

public class StrategyTwo implements AIStrategy, Observer{
	private Hand myHand;
	private ArrayList<Meld> table;
	private HashMap<Integer, Integer> playerHandSizes;
	private ArrayList<Tile> run;
	private ArrayList<Tile> group;
	private HashMap<Tile,Integer> meldOnTable;;
	private int countInitial;
	private boolean hasPlayInit;
	private boolean hasPlayRest;
	
	
	public StrategyTwo (Subject data) {
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
		meldOnTable = new HashMap<Tile,Integer>();
		boolean hasInitial = false;
		boolean hasLess = false;
		
		if(countInitial < 30) {	//play initial
			for(Entry<Integer, Integer> entry : playerHandSizes.entrySet()) {
				//int id = entry.getKey();
				int handsize = entry.getValue();
				if(handsize < 14) {
					hasInitial = true;
					break;
				} 
			}
			if(hasInitial) {
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
							return "Something went wrong";
					}
				}	else {
					System.out.print("Someone has played initial, but P2 can't play 30+points");
					return "DRAW";
				}
						
			} else
				return "DRAW";	
		}else {
			if(myHand.canPlayAll(null)) {	//if can play all, not request use of table
				run = myHand.findRun();
				if(run != null) {
					countInitial += myHand.checkSum(run);
					hasPlayRest = true;
					return "Play" + run;
				}
				else {
					group = myHand.findGroup();
					if(group != null) {
						countInitial += myHand.checkSum(group);
						hasPlayRest = true;
						return "Play" + group;
					}	
					else
						return "Something wrong";
				}
			} else {		//cannot win, play ---------not done
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
