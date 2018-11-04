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
	private HashMap<Tile,Integer> meldOnTable;
	private HashMap<ArrayList<Tile>,Integer> moveRunToTable;
	private HashMap<ArrayList<Tile>,Integer> moveGroupToTable;
	boolean moveRun2Table;
	boolean moveSet2Table;
	int moveRunIndex;
	int moveSetIndex;
	private Meld tempMeld;
	boolean moveGroup2Table;
	private int countInitial;
	private String returnString;
	
	public StrategyThree () {
		countInitial = 0;
		moveRunIndex = 0;
		moveSetIndex = 0;
		moveRun2Table = false;
		moveSet2Table = false;
		tempMeld = new Meld();
		playerHandSizes = new HashMap<Integer, Integer>();
	}
	
	@Override
	public String generateCommand() {
		returnString = "";
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
					returnString = "Play";
					for(int i=0; i<run.size();i++) {
						returnString += " " + myHand.handIndexOf(run.get(i)); 
					}
					return returnString;
				}
				else {
					group = myHand.findGroup();
					myHand.sort();
					if(group != null) {
						countInitial += myHand.checkSum(group);
						myHand.sort();
						returnString = "Play";
						for(int i=0; i<group.size();i++) {
							returnString += " " + myHand.handIndexOf(group.get(i)); 
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
			if(hasLess || moveRun2Table || moveSet2Table) {		//has 3 fewer tiles than p3, play all request use of table
				//System.out.println("Some one has 3 fewer tiles than this AI");
				run = myHand.findRun();
				if(run != null) {
					myHand.sort();
					returnString = "Play";
					for(int i=0; i<run.size();i++) {
						returnString += " " + myHand.handIndexOf(run.get(i));
					}
					return returnString;
				}
				group = myHand.findGroup();
				myHand.sort();
				if(group != null) {
					myHand.sort();
					returnString = "Play";
					for(int i=0; i<group.size();i++) {
						returnString += " " + myHand.handIndexOf(group.get(i));
					}
					return returnString;
				}	
				meldOnTable = myHand.findMeldsOnTable(table);
				myHand.sort();
				if(meldOnTable != null) {
					for(Entry<Tile, Integer>Entry : meldOnTable.entrySet()) {
						Tile tile = Entry.getKey();
						int index = Entry.getValue();
						//System.out.println(meldOnTable);
						myHand.sort();
						if(tile.isJoker())
							returnString = "Play "  + myHand.handIndexOf(tile) + " to " + index + " " + "tail";
						else
							returnString = "Play "  + myHand.handIndexOf(tile) + " to " + index/* + " " + "1"*/;	
						return returnString;
					}
				}
						//System.out.println("--"+table);
						//System.out.println("--"+myHand +  " " +moveRun2Table);
				if(!moveRun2Table) {		//if not in move run progress
					for(Meld m : table) {
						moveRunToTable = myHand.findRunMove(m);
						if(moveRunToTable != null) {
							moveRun2Table = true;
							tempMeld = m;
							for(Entry<ArrayList<Tile>, Integer> Entry : moveRunToTable.entrySet()) {
								ArrayList<Tile> runMove = Entry.getKey();
								moveRunIndex = Entry.getValue();	// 
								myHand.sort();
								returnString = "Play";
								for(int i=0; i<runMove.size();i++) {
									returnString += " " + myHand.handIndexOf(runMove.get(i));
								}
								//System.out.println("Try using move 2 ");
								return returnString;
							}
						}
					}	
				}	else {	// if in move run progress already
					//System.out.println("move run");
						moveRun2Table = false;
						//System.out.println(tempMeld);
						//	System.out.println("move +" +  table.indexOf(tempMeld));
						returnString = "Move";
						returnString += " " + table.indexOf(tempMeld);
						if(moveRunIndex == 0)
							returnString += " 0 to ";
						else
							returnString += " 1 to ";
						returnString +=  table.size()-1;
						//System.out.println(returnString);
						return returnString;
				}	
				
				if(!moveSet2Table) {		//if not in move set progress
					for(Meld m : table) {
						moveGroupToTable = myHand.findSetMove(m);
						myHand.sort();
						if(moveGroupToTable != null) {
							moveSet2Table = true;
							tempMeld = m;
							for(Entry<ArrayList<Tile>, Integer> Entry : moveGroupToTable.entrySet()) {
								ArrayList<Tile> setMove = Entry.getKey();
								moveSetIndex = Entry.getValue();	// 
								myHand.sort();
								returnString = "Play";
								for(int i=0; i<setMove.size();i++) {
									returnString += " " + myHand.handIndexOf(setMove.get(i));
								}
								//System.out.println("Try using move 2 set");
								return returnString;
							}
						}
					}	
				}	else {	// if in move set progress already
					//System.out.println("move set");
					moveSet2Table = false;
						//System.out.println(tempMeld);
						//	System.out.println("move +" +  table.indexOf(tempMeld));
						returnString = "Move";
						returnString += " " + table.indexOf(tempMeld);
						returnString += " 1 to ";
						returnString +=  table.size()-1;
						//System.out.println(returnString);
						return returnString;
				}	
				
				System.out.println("p3 could play but has not tile to play");
				return "END";
			}else {
				if(myHand.canPlayAll(table)) {	//if can play all, request use of table
					run = myHand.findRun();
					if(run != null) {
						myHand.sort();
						returnString = "Play";
						for(int i=0; i<run.size();i++) {
							returnString += " " + myHand.handIndexOf(run.get(i));
						}
						return returnString;
					} else {
						group = myHand.findGroup();
						myHand.sort();
						if(group != null) {
							myHand.sort();
							returnString = "Play";
							for(int i=0; i<group.size();i++) {
								returnString += " " + myHand.handIndexOf(group.get(i));
							}
							return returnString;
						}	else {
							meldOnTable = myHand.findMeldsOnTable(table);
							if(meldOnTable != null) {
								for(Entry<Tile, Integer>Entry : meldOnTable.entrySet()) {
									Tile tile = Entry.getKey();
									int index = Entry.getValue();
									myHand.sort();
									if(tile.isJoker())
										returnString = "Play "  + myHand.handIndexOf(tile) + " to " + index + " " + "tail";
									else
										returnString = "Play "  + myHand.handIndexOf(tile) + " to " + index/* + " " + "1"*/;	
									return returnString;
								}
							}else {
								//System.out.println("Something might went wrong");
								return "END";
							}
						}
					}
				}else {
					return "END";
				}			
			}
			//System.out.println("Something might went wrong");
			return "END";
		}
	}

	@Override
	public void update(ArrayList<Meld> table) {
		this.table = table;
	}
	@Override
	public void update(int playerId, int handSize) {
		playerHandSizes.put(playerId, handSize);
	}
	@Override
	public void setHand(Hand hand) {
		myHand = hand;
	}

	@Override
	public String getDifficulty() {
		return "Difficult";
	}

	@Override
	public void setMyId(int id) {
		// TODO Auto-generated method stub
		
	}
}
