package players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import rrrummy.Meld;
import rrrummy.Tile;

public class AI4Logic {
	private AILogic logic;
	private int countInitial;
	boolean hasLess;
	boolean moveRun2Table;
	boolean moveSet2Table;
	boolean cutRun2Table;
	boolean InRestProg;
	int moveRunIndex;
	int moveSetIndex;
	int cutRunIndex;
	private HashMap<Tile,Integer> meldOnTable;
	private HashMap<ArrayList<Tile>,Integer> moveRunToTable;
	private HashMap<ArrayList<Tile>,Integer> moveGroupToTable;
	private HashMap<ArrayList<Tile>,Integer> cutRunToTable;
	private HashMap<Tile,Integer>replace;
	private ArrayList<Tile> runCut;
	private ArrayList<Meld> temptable;
	private Meld tempMeld;
	boolean moveGroup2Table;
	
	//constructor
	public AI4Logic(AILogic lg) {
		logic = lg;
		countInitial = 0;
		moveRunIndex = 0;
		moveSetIndex = 0;
		cutRunIndex = 0;
		moveRun2Table = false;
		moveSet2Table = false;
		InRestProg = false;
		cutRun2Table = false;
		hasLess = false;
		tempMeld = new Meld();
	}
	
	public String AI4Command(Hand myHand, ArrayList<Meld> table, HashMap<Integer, Integer> playerHandSizes,  int stockLeft) {
		logic = new AILogic(myHand, table);
		ArrayList<Tile> run = new ArrayList<Tile>();
		ArrayList<Tile> group = new ArrayList<Tile>();
		String returnString = "";
		
		boolean hasInitial = false;
		if(countInitial < 30) {	//play initial when someone has played initial
			for(Entry<Integer, Integer> entry : playerHandSizes.entrySet()) {
				//int id = entry.getKey();
				int handsize = entry.getValue();
				if(handsize < 14) {
					hasInitial = true;
					break;
				} 
			}
			if(hasInitial) {
				//returnString = logic.AI4CommandInitial(myHand,table,countInitial);
				//return returnString;
				logic = new AILogic(myHand,table);
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
				return "END";
			}
		} else {
			if(logic.canPlayAll() && !InRestProg) {	//if can play all, request use of table
				//System.out.println("can play all");
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
												temptable = new ArrayList<Meld>(table);
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
									returnString += " " + temptable.indexOf(tempMeld);
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
											temptable = new ArrayList<Meld>(table);
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
									returnString += " " + temptable.indexOf(tempMeld);
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
						} else if(stockLeft < 4) {	//if stock less than 3 tile, play all
							InRestProg = true;
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
												temptable = new ArrayList<Meld>(table);
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
									//	System.out.println("move +" +  temptable.indexOf(tempMeld));
									returnString = "Move";
									returnString += " " + temptable.indexOf(tempMeld);
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
											temptable = new ArrayList<Meld>(table);
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
									//System.out.println("move +" +  temptable.indexOf(tempMeld));
									returnString = "Move";
									returnString += " " + temptable.indexOf(tempMeld);
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
							InRestProg = false;
							return "END";
						} else {																// if cannot play all, play meld base on table
				//System.out.println("can not play all");
							InRestProg = true;
							run = logic.findRun();
							if(run != null && logic.hasMapfromTable(run, table)) {
								myHand.sort();
								returnString = "Play";
								for(int i=0; i<run.size();i++) {
									returnString += " " + myHand.handIndexOf(run.get(i));
								}
								return returnString;
							} 
							group = logic.findSet();
							myHand.sort();
							if(group != null && logic.hasMapfromTable(group, table)) {
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
												temptable = new ArrayList<Meld>(table);
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
									//	System.out.println("move +" +  temptable.indexOf(tempMeld));
									returnString = "Move";
									returnString += " " + temptable.indexOf(tempMeld);
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
											temptable = new ArrayList<Meld>(table);
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
									//System.out.println("move +" +  temptable.indexOf(tempMeld));
									returnString = "Move";
									returnString += " " + temptable.indexOf(tempMeld);
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
							InRestProg = false;
							return "END";
						}
		}
	}
}
