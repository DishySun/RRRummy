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
	private ArrayList<Tile> runCut;
	private HashMap<Tile,Integer> meldOnTable;;
	private HashMap<ArrayList<Tile>,Integer> moveRunToTable;
	private HashMap<ArrayList<Tile>,Integer> moveGroupToTable;
	private HashMap<ArrayList<Tile>,Integer> cutRunToTable;
	private HashMap<Tile,Integer>replace;
	boolean moveRun2Table;
	boolean moveSet2Table;
	boolean cutRun2Table;
	int moveRunIndex;
	int moveSetIndex;
	int cutRunIndex;
	private Meld tempMeld;
	private int countInitial;
	private String returnString;
	private AILogic logic;
	
	public StrategyTwo () {
		playerHandSizes = new HashMap<Integer, Integer>();
		logic = new AILogic(myHand,table);
		countInitial = 0;
		moveRunIndex = 0;
		moveSetIndex = 0;
		cutRunIndex = 0;
		cutRun2Table = false;
		moveRun2Table = false;
		moveSet2Table = false;
		tempMeld = new Meld();
	}
	
	@Override
	public String generateCommand() {
		logic = new AILogic(myHand,table);
		returnString = "";
		run = new ArrayList<Tile>();
		group = new ArrayList<Tile>();
		runCut = new ArrayList<Tile>();
		meldOnTable = new HashMap<Tile,Integer>();
		boolean hasInitial = false;
		
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
				if(logic.checkInitialSum() >= 30-countInitial) {
					run = logic.findRun();
					if(run != null) {
						countInitial += logic.checkSum(run);
						myHand.sort();
						returnString = "Play";
						for(int i=0; i<run.size();i++) {
							returnString += " " + myHand.handIndexOf(run.get(i)); 
						}
						return returnString;
					}
					else {
						group = logic.findSet();
						myHand.sort();
						if(group != null) {
							countInitial += logic.checkSum(group);
							myHand.sort();
							returnString = "Play";
							for(int i=0; i<group.size();i++) {
								returnString += " " + myHand.handIndexOf(group.get(i)); 
							}
							return returnString;
						}	
						else {
							return "END";
						}
					}
				}	else {
					//System.out.print("Someone has played initial, but P2 can't play 30+points");
					return "END";
				}
						
			} else {
				return "END";}
		}else {
			if(logic.canPlayAll()) {	//if can play all, request use of table
				run = logic.findRun();
				if(run != null) {
					myHand.sort();
					returnString = "Play";
					for(int i=0; i<run.size();i++) {
						returnString += " " + myHand.handIndexOf(run.get(i));
					}
					return returnString;
				} 
				group = logic.findSet();
				myHand.sort();
				if(group != null) {
					myHand.sort();
					returnString = "Play";
					for(int i=0; i<group.size();i++) {
							returnString += " " + myHand.handIndexOf(group.get(i));
					}
					return returnString;
				}	
				meldOnTable = logic.findMeldsOnTable();
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
				} 
				
				if(!moveRun2Table) {		//if not in move run progress
					if(!moveSet2Table) {	//if not in move set progress
						for(Meld m : table) {
							moveRunToTable = logic.findRunMove(m);
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
					}
				}	else {	// if in move run progress already
					//System.out.println("move run");
						moveRun2Table = false;
						//System.out.println(tempMeld);
						//	System.out.println("move +" +  table.indexOf(tempMeld));
						returnString = "Move";
						returnString += " " + table.indexOf(tempMeld);
						if(moveRunIndex == 0)
							returnString += " head to ";
						else
							returnString += " tail to ";
						returnString +=  table.size()-1;
						if(tempMeld.getTile(moveRunIndex).isJoker())
							returnString += " tail";
						//System.out.println(returnString);
						return returnString;
				}	
				
				if(!moveSet2Table) {		//if not in move set progress
					if(!moveRun2Table) {
						for(Meld m : table) {
							moveGroupToTable = logic.findSetMove(m);
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
					}
				}	else {	// if in move set progress already
					//System.out.println("move set");
					moveSet2Table = false;
						//System.out.println(tempMeld);
						//	System.out.println("move +" +  table.indexOf(tempMeld));
						returnString = "Move";
						returnString += " " + table.indexOf(tempMeld);
						if(moveSetIndex == 0)
							returnString += " head to ";
						else
							returnString += " tail to ";
						returnString +=  table.size()-1;
						if(tempMeld.getTile(moveSetIndex).isJoker())
							returnString += " tail";
						return returnString;
				}	
				
				if(!cutRun2Table) {	//if not in cut prog
					for(Meld m : table) {
						cutRunToTable = logic.findRunCut(m);
						myHand.sort();
						if(cutRunToTable != null) {	// can cut
							cutRun2Table = true;
							tempMeld = m;
							for(Entry<ArrayList<Tile>, Integer> Entry : cutRunToTable.entrySet()) {
								runCut = Entry.getKey();
								cutRunIndex = Entry.getValue();	// 
								myHand.sort();
								returnString = "Cut " + table.indexOf(m) + " at " +cutRunIndex ;
								return returnString;
							}
						}
					}
				} else {	// in cut prog
					meldOnTable = logic.findMeldsOnTable();
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
					} else
						cutRun2Table = false;
				}
				
				//replace prog
				for(Meld m : table) {
					//System.out.println("Check replace");
					replace = logic.findReplace(m);
					if(replace != null) {
						for(Entry<Tile, Integer>Entry : replace.entrySet()) {
							Tile tile = Entry.getKey();
							int index = Entry.getValue();
							myHand.sort();
							//Replace int(handIndex) to int(tableIndex) int(meldIndex)
							returnString = "Replace " + myHand.indexOf(tile) + " to ";
							returnString += table.indexOf(m) + " " +  index;
							return returnString;
						}
					}
				}
				return "END";	
			}else {		// can not win, play tile that match meld on table
				meldOnTable = logic.findMeldsOnTable();
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
				}
				
				if(!moveRun2Table) {		//if not in move run progress
					if(!moveSet2Table) {	//if not in move set progress
						for(Meld m : table) {
							moveRunToTable = logic.findRunMove(m);
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
					}
				}	else {	// if in move run progress already
					//System.out.println("move run");
						moveRun2Table = false;
						//System.out.println(tempMeld);
						//	System.out.println("move +" +  table.indexOf(tempMeld));
						returnString = "Move";
						returnString += " " + table.indexOf(tempMeld);
						if(moveRunIndex == 0)
							returnString += " head to ";
						else
							returnString += " tail to ";
						returnString +=  table.size()-1;
						if(tempMeld.getTile(moveRunIndex).isJoker())
							returnString += " tail";
						//System.out.println(returnString);
						return returnString;
				}	
				
				if(!moveSet2Table) {		//if not in move set progress
					if(!moveRun2Table) {
						for(Meld m : table) {
							moveGroupToTable = logic.findSetMove(m);
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
					}
				}	else {	// if in move set progress already
					//System.out.println("move set");
					moveSet2Table = false;
						//System.out.println(tempMeld);
						//	System.out.println("move +" +  table.indexOf(tempMeld));
						returnString = "Move";
						returnString += " " + table.indexOf(tempMeld);
						if(moveSetIndex == 0)
							returnString += " head to ";
						else
							returnString += " tail to ";
						returnString +=  table.size()-1;
						if(tempMeld.getTile(moveSetIndex).isJoker())
							returnString += " tail";
						return returnString;
				}	
				
				if(!cutRun2Table) {	//if not in cut prog
					for(Meld m : table) {
						cutRunToTable = logic.findRunCut(m);
						myHand.sort();
						if(cutRunToTable != null) {	// can cut
							cutRun2Table = true;
							tempMeld = m;
							for(Entry<ArrayList<Tile>, Integer> Entry : cutRunToTable.entrySet()) {
								runCut = Entry.getKey();
								cutRunIndex = Entry.getValue();	// 
								myHand.sort();
								returnString = "Cut " + table.indexOf(m) + " at " +cutRunIndex ;
								return returnString;
							}
						}
					}
				} else {	// in cut prog
					meldOnTable = logic.findMeldsOnTable();
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
					} else
						cutRun2Table = false;
				}
				
				//replace prog
				for(Meld m : table) {
					//System.out.println("Check replace");
					replace = logic.findReplace(m);
					if(replace != null) {
						for(Entry<Tile, Integer>Entry : replace.entrySet()) {
							Tile tile = Entry.getKey();
							int index = Entry.getValue();
							myHand.sort();
							//Replace int(handIndex) to int(tableIndex) int(meldIndex)
							returnString = "Replace " + myHand.indexOf(tile) + " to ";
							returnString += table.indexOf(m) + " " +  index;
							return returnString;
						}
					}
				}
			}
			//System.out.println( "can not win, no tile can play that requires use of tiles of the table");
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
		return "Normal";
	}

	@Override
	public void setMyId(int id) {
		// TODO Auto-generated method stub
		
	}
}
