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
	private String returnString;
	
	public StrategyThree (Subject data) {
		data.register(this);
		hasPlayInit = false;
		hasPlayRest = false;
		countInitial = 0;
	}
	
	@Override
	public String generateCommand() {
		returnString = "";
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
					myHand.sort();
					returnString = "Play ";
					for(int i=0; i<run.size();i++) {
						returnString += myHand.handIndexOf(run.get(i)) + " ";
					}
					return returnString;
				}
				else {
					group = myHand.findGroup();
					if(group != null) {
						countInitial += myHand.checkSum(group);
						myHand.sort();
						returnString = "Play ";
						for(int i=0; i<group.size();i++) {
							returnString += myHand.handIndexOf(group.get(i)) + " ";
						}
						return returnString;
					}	
					else {
						System.out.println("Something went wrong");
						return "END";
					}
						
						
				}
			}	else
				return "END";
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
					myHand.sort();
					returnString = "Play ";
					for(int i=0; i<run.size();i++) {
						returnString += myHand.handIndexOf(run.get(i)) + " ";
					}
					return returnString;
				}
				else {
					group = myHand.findGroup();
					if(group != null) {
						countInitial += myHand.checkSum(group);
						myHand.sort();
						returnString = "Play ";
						for(int i=0; i<group.size();i++) {
							returnString += myHand.handIndexOf(group.get(i)) + " ";
						}
						return returnString;
					}	
					else {
						meldOnTable = myHand.findMeldsOnTable(table);
						if(meldOnTable != null) {
							for(Entry<Tile, Integer>Entry : meldOnTable.entrySet()) {
								Tile tile = Entry.getKey();
								int index = Entry.getValue();
								myHand.sort();
								returnString = "Play "  + myHand.handIndexOf(tile) + " to " + index;
								return returnString;
							}
						}else {
									System.out.println("p3 could play but has not tile to play,p3 tried reuse table");
									return "END";
						}
					}	
				}
			}else {
				if(myHand.canPlayAll(table)) {	//if can play all, request use of table
					run = myHand.findRun();
					if(run != null) {
						countInitial += myHand.checkSum(run);
						myHand.sort();
						returnString = "Play ";
						for(int i=0; i<run.size();i++) {
							returnString += myHand.handIndexOf(run.get(i)) + " ";
						}
						return returnString;
					} else {
						group = myHand.findGroup();
						if(group != null) {
							countInitial += myHand.checkSum(group);
							myHand.sort();
							returnString = "Play ";
							for(int i=0; i<run.size();i++) {
								returnString += myHand.handIndexOf(group.get(i)) + " ";
							}
							return returnString;
						}	else {
							meldOnTable = myHand.findMeldsOnTable(table);
							if(meldOnTable != null) {
								for(Entry<Tile, Integer>Entry : meldOnTable.entrySet()) {
									Tile tile = Entry.getKey();
									int index = Entry.getValue();
									myHand.sort();
									returnString = "Play "  + myHand.handIndexOf(tile) + " to " + index;
									return returnString;
								}
							}else {
								System.out.println("Something went wrong");
								return "END";
							}
						}
					}
				}else {
					return "END";
				}			
			}
			System.out.println("Something went wrong");
			return "END";
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
