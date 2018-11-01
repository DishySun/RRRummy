package players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import observer.Observer;
import observer.Subject;
import rrrummy.Meld;
import rrrummy.Tile;

public class StrategyThree implements AIStrategy, Observer {
	private Hand myHand;
	private ArrayList<Meld> table;
	private HashMap<Integer, Integer> playerHandSizes;
	private ArrayList<Tile> run;
	private ArrayList<Tile> group;
	private HashMap<Tile,Integer> meldOnTable;;
	private int countInitial;
	private boolean hasPlayInit;
	private boolean hasPlayRest;
	
	public StrategyThree (Subject data) {
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
		boolean hasLess = false;
		
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
						return "Something went wrong";
				}
			}	else
					return "DRAW";
		}else {
			for(Entry<Integer, Integer> entry : playerHandSizes.entrySet()) {
				//int id = entry.getKey();
				int handsize = entry.getValue();
				if(handsize+3 < myHand.size()) {
					hasLess = true;
					break;
				} 
			}
			if(hasLess) {		//has 3 fewer tiles than p3, play all request use of table
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
					else {
						meldOnTable = myHand.findMeldsOnTable(table);
						if(meldOnTable != null) {
							for(Entry<Tile, Integer>Entry : meldOnTable.entrySet()) {
								Tile tile = Entry.getKey();
								int index = Entry.getValue();
								hasPlayRest = true;
								return "Play" + tile + "to" + table.get(index) ;
							}
						}else {
							if(hasPlayInit) {
								hasPlayInit  = false;
								return "END";
							} else {
								if(hasPlayRest) {
									hasPlayRest = false;
									return "End";
								}
								else {
									System.out.println("p3 could play but has not tile to play,p3 would reuse table");
									return "DRAW";
								}
									
							}	
						}
					}	
				}
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
				}else {
					if(hasPlayInit) {
						hasPlayInit  = false;
						return "END";
					} else {
						if(hasPlayRest) {
							hasPlayRest = false;
							return "End";
						}
						else {
							return "DRAW";
						}
					}
				}			
			}
			return "??";
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
