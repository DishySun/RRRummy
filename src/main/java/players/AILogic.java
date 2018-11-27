package players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import rrrummy.*;

public class AILogic {
	private ArrayList<Meld> table;
	private ArrayList<Tile> run;
	private ArrayList<Tile> group;
	private ArrayList<Tile> runCut;
	private HashMap<Tile,Integer> meldOnTable;
	private HashMap<ArrayList<Tile>,Integer> moveRunToTable;
	private HashMap<ArrayList<Tile>,Integer> moveGroupToTable;
	private HashMap<ArrayList<Tile>,Integer> cutRunToTable;
	private HashMap<Tile,Integer>replace;
	private ArrayList<Meld> tempMeldList;
	boolean hasLess;
	boolean moveRun2Table;
	boolean moveSet2Table;
	boolean cutRun2Table;
	boolean InRestProg;
	int moveRunIndex;
	int moveSetIndex;
	int cutRunIndex;
	private Meld tempMeld;
	boolean moveGroup2Table;
	Hand hand;
	ArrayList<Meld> meldList; 
	public AILogic(Hand h, ArrayList<Meld> m) {
		hand = h;
		meldList = m;
		
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
	
	public int checkSum(ArrayList<Tile> tileArray) {
		// TODO Auto-generated method stub
		boolean isRun = true;
		if(tileArray == null || tileArray.size() < 3) return 0;
		int count = 0;
		int tilePosition = 0;
		for(int i=0; i<tileArray.size();i++) {
			if(tileArray.get(i).getColor() != Tile.Color.JOKER){
				tilePosition = i;
				break;
			}
		}
		if(!hasSameColor(tileArray,tileArray.get(tilePosition))) {
			isRun = false;
		}
			
		if(isRun) {//run
			if(tilePosition == 0) { // _ _ J _ J || _ _ J J _ || _ _ J J
				for(int i=tilePosition; i<tileArray.size();i++) {
					if(tileArray.get(i).getColor() != Tile.Color.JOKER) {
						count += tileArray.get(i).getNumber();
					} else if(tileArray.get(i).getColor() == Tile.Color.JOKER){
						if(tileArray.get(i-1).getColor() != Tile.Color.JOKER)
						{
							count += tileArray.get(i-1).getNumber() + 1; 		// 3 J 5 == 3 3+1 5
						} else {
							count += tileArray.get(i-2).getNumber() + 2; 		// 3 J J  == 3 3+1 3+2
						}
					}
				}
			} else if(tilePosition == 1){ // J _ _ J _
				count += tileArray.get(tilePosition).getNumber() - 1; // J 3 _ _ == 3-1 3
				for(int i=tilePosition; i<tileArray.size();i++) {
					if(tileArray.get(i).getColor() != Tile.Color.JOKER) {
						count += tileArray.get(i).getNumber();
					} else {
						count += tileArray.get(i-1).getNumber() + 1; 		// 3 J 5 == 3 3+1 5
					}
				}
			} else if(tilePosition ==2) { // J J _ _
				count += tileArray.get(tilePosition).getNumber()*2 - 3; // J J 3 == 3-1 3-2 3
				for(int i=tilePosition; i<tileArray.size();i++) {
					count += tileArray.get(i).getNumber();
				}
			}
		}else {	//set
			count = tileArray.get(tilePosition).getNumber() * tileArray.size();
		}
		return count;
	}
	
	public boolean hasSameColor(ArrayList<Tile> tile, Tile compareTile) {
		// TODO Auto-generated method stub
		for (Tile t : tile) {
			if(t.getColor() == compareTile.getColor()) return true;
		}
		return false;	
	}
	
	public boolean hasSameColor2(ArrayList<Tile> tile, Meld comptile) {
		// TODO Auto-generated method stub
		for (Tile t : tile) {
			for(int m = 0; m<comptile.size();m++) {
				if(t.getColor() == comptile.getTile(m).getColor()) 
					return true;
			}
		}
		return false;	
	}
	
	public boolean hasSameColor3(Meld meld, Tile comptile) {
		// TODO Auto-generated method stub
		for(int i=0; i<meld.size();i++) {
			if(meld.getTile(i).getColor() == comptile.getColor())
				return true;
		}
		return false;	
	}
	
	public ArrayList<Tile> findRun() {
		// TODO Auto-generated method stub
		if(hand.size() == 0)
			return null;
		ArrayList<Tile> tile4Run = new ArrayList<Tile>();
		ArrayList<Tile> LargestRun = new ArrayList<Tile>();
		LargestRun= null;
		boolean disconnect = true;	// check if next tile number & color is connecte
		boolean run = true;		// used to stop loop when reach 30 points
		int playIndex = 0;	//point to tile4Run position;
		int handIndex;  //used when looping hand cards
		int jokerNum = 0;	// joker number
		int jokerorg = 0;	// joker number (final)
		boolean hasJoker = false;
		hand.sort();		//sort
		//System.out.println("-----" + hand) ;
		//check if has joker
		for(int i = 0; i<hand.size();i++) {			//count size of joker;
			if(hand.getTile(i).getColor() == Tile.Color.JOKER) {
				jokerNum++;
				jokerorg++;
				hasJoker = true;
			}
		}
		
		tile4Run.add(hand.getTile(0));
		for(int i=1; i<hand.size()-jokerNum;i++) {
			if(tile4Run.get(playIndex).getColor() == hand.getTile(i).getColor() 
					&& hand.getTile(i).getNumber()-1 == tile4Run.get(playIndex).getNumber()) {
				tile4Run.add(hand.getTile(i));
				playIndex++;
			} else {
				if(tile4Run.get(playIndex).getColor() == hand.getTile(i).getColor() 
						&& hand.getTile(i).getNumber() == tile4Run.get(playIndex).getNumber()) {//R1 R1 R2 R2
					continue;
				}else {
					if(tile4Run.size() < 3) {
						tile4Run = new ArrayList<Tile>();
						tile4Run.add(hand.getTile(i));
						playIndex = 0;
					} else {
						if(checkSum(tile4Run) >= checkSum(LargestRun))
							LargestRun = tile4Run;
						else	{
							tile4Run = new ArrayList<Tile>();
							tile4Run.add(hand.getTile(i));
							playIndex = 0;
						}
							
					}
				}
			}
		}
		if(checkSum(tile4Run) >= checkSum(LargestRun) && tile4Run.size() >= 3)
			LargestRun = tile4Run;
		//if not return above, check if has joker, if has,run, else return null
		tile4Run = new ArrayList<Tile>();
		playIndex = 0;
		int count = jokerorg;
		if(jokerorg > 1) jokerNum--;
		//while(count != 0) {
			if(hasJoker) {
				if(hand.size() < 2)
					return null;
				tile4Run.add(hand.getTile(0));
				handIndex = 1;
				while(handIndex < hand.size()-jokerorg && run) {
					if(tile4Run.get(playIndex).getColor() != Tile.Color.JOKER) { // last tile in tile4Run is not joker
						if(tile4Run.get(playIndex).getColor() == hand.getTile(handIndex).getColor() 			//continuously 1 2 3 4 5
								&& hand.getTile(handIndex).getNumber()-1 == tile4Run.get(playIndex).getNumber())
						{
							tile4Run.add(hand.getTile(handIndex));
							playIndex++;		
							disconnect = false;
							handIndex++;
							if(handIndex == hand.size()-jokerorg)
								disconnect = true;
						} else if(tile4Run.get(playIndex).getColor() == hand.getTile(handIndex).getColor()
								&& hand.getTile(handIndex).getNumber() == tile4Run.get(playIndex).getNumber()){	//duplicate 1 1 2 2 
							handIndex++;	
							disconnect = false;
						} else
							disconnect = true;
					}
					else {	// last tile in tile4Run is joker _ _ J
						if(tile4Run.get(playIndex-1).getColor() == Tile.Color.JOKER  // _ J J 
								&& hand.getTile(handIndex).getNumber() - 3 !=  tile4Run.get(playIndex-2).getNumber()) {
							disconnect = true;
						} else if (tile4Run.get(playIndex-1).getColor() == Tile.Color.JOKER // _ J J
								&& hand.getTile(handIndex).getNumber() - 3 ==  tile4Run.get(playIndex-2).getNumber()
								&& hand.getTile(handIndex).getColor() == tile4Run.get(playIndex-2).getColor()) {
							tile4Run.add(hand.getTile(handIndex));
							playIndex++;		
							handIndex++;
							disconnect = false;
						} else if (tile4Run.get(playIndex-1).getColor() != Tile.Color.JOKER // _ _ J
								&& hand.getTile(handIndex).getNumber() - 2 !=  tile4Run.get(playIndex-1).getNumber()) {
							disconnect = true;
						} else if (tile4Run.get(playIndex-1).getColor() != Tile.Color.JOKER // _ _ J
								&& hand.getTile(handIndex).getNumber() - 2 ==  tile4Run.get(playIndex-1).getNumber()
								&& hand.getTile(handIndex).getColor() == tile4Run.get(playIndex-1).getColor()) {
							tile4Run.add(hand.getTile(handIndex));
							playIndex++;	
							handIndex++;
							disconnect = false;
						}
						if(handIndex == hand.size()-jokerorg) {
							disconnect = true;
						}
					}

					if (disconnect){ // disconnect
						disconnect = false;
						if(tile4Run.size() < 3) {
							if(jokerNum > 0) {
								if((tile4Run.size() >= 2 && tile4Run.get(tile4Run.size()-2).getNumber() == 12 
										&& tile4Run.get(tile4Run.size()-1).getColor() == Tile.Color.JOKER )
										|| tile4Run.get(tile4Run.size()-1).getNumber() == 13
										|| (tile4Run.size() >= 3 && tile4Run.get(tile4Run.size()-3).getNumber() == 11 
										&& tile4Run.get(tile4Run.size()-1).getColor() == Tile.Color.JOKER))
								{	
											tile4Run.add(0, hand.getTile(hand.size()-1)); // add joker
											jokerNum--;
											playIndex++;
											
											if(tile4Run.size() >= 3 && checkSum(tile4Run) > checkSum(LargestRun))
												LargestRun = tile4Run;
									
								} else {
									tile4Run.add(hand.getTile(hand.size()-1)); // add joker								
									jokerNum--;
									playIndex++;
									if(tile4Run.size() >= 3 && handIndex == hand.size()-jokerorg) {
											if( checkSum(tile4Run) >= checkSum(LargestRun))
												LargestRun = tile4Run;
									}	
								}
							} else {
								if(handIndex == hand.size()-jokerorg)
									break;
								for(int i = 0; i<tile4Run.size();i++) {
									if(tile4Run.get(i).getColor() == Tile.Color.JOKER) 
										jokerNum++;
								}
								tile4Run = new ArrayList<Tile>();
								tile4Run.add(hand.getTile(handIndex));
								playIndex = 0;
								handIndex++;
							}
						} else {
							if(checkSum(tile4Run) >= checkSum(LargestRun)) {
								LargestRun =  tile4Run;
							}
							if(handIndex == hand.size()-jokerorg)
								break;
							tile4Run = new ArrayList<Tile>();
							tile4Run.add(hand.getTile(handIndex));
							playIndex = 0;
							handIndex++;
							jokerNum = 1;
						}
					}
				}
			}
		/*	jokerNum = 0;
			for(int i = 0; i<size();i++) {			//count size of joker;
				if(hand.get(i).getColor() == Tile.Color.JOKER) {
					jokerNum++;
				}	
			}
			playIndex = 0;
			tile4Run.clear();	
			//if(jokerorg > 1) jokerNum++;		//if 2 joker, first only 1 joker first, than 2
			count--;
		}*/
		//nothing to return
		return LargestRun;
	}
	
	public ArrayList<Tile> findSet() {
		// TODO Auto-generated method stub
		if(hand.size() == 0)
			return null;
		ArrayList<Tile> tile4Set = new ArrayList<Tile>();
		ArrayList<Tile> LargestSet = new ArrayList<Tile>();
		LargestSet= null;
		boolean disconnect = true;	// check if next tile number & color is connecte
		boolean run = true;		// used to stop loop when reach 30 points
		int playIndex = 0;	//point to tile4Run position;
		int handIndex;  //used when looping hand cards
		int jokerNum = 0;	// joker number
		int jokerorg = 0;	// joker number (final)
		boolean hasJoker = false;
		hand.sortByNum();		//sort
		for(int i = 0; i<hand.size();i++) {			//count size of joker;
			if(hand.getTile(i).getColor() == Tile.Color.JOKER) {
				jokerNum++;
				jokerorg++;
				hasJoker = true;
			}
		}
		
		tile4Set.add(hand.getTile(0));
		for(int i=1; i<hand.size()-jokerNum;i++) {		// R1 B1
			if(!hasSameColor(tile4Set, hand.getTile(i))
					&& hand.getTile(i).getNumber() == tile4Set.get(playIndex).getNumber()) {
				tile4Set.add(hand.getTile(i));
				playIndex++;
			} else {
				if(tile4Set.size() < 3) {
					tile4Set= new ArrayList<Tile>();	
					tile4Set.add(hand.getTile(i));
					playIndex = 0;
				} else {
					if( checkSum(tile4Set) >= checkSum(LargestSet)){
						LargestSet = tile4Set;
					}else	{
						tile4Set = new ArrayList<Tile>();
						tile4Set.add(hand.getTile(i));
						playIndex = 0;
					}
				}
			}
		}

		if( checkSum(tile4Set) >= checkSum(LargestSet) && tile4Set.size() >= 3)
			LargestSet = tile4Set;
		//if not return above, check if has joker, if has,run, else return null
		if(hand.size() == 0)
			return null;
		tile4Set = new ArrayList<Tile>();
		playIndex = 0;
		int count = jokerorg;
		if(jokerorg > 1) jokerNum--;
		while(count != 0) {		
			if(hasJoker) {
				if(hand.size() < 2)
					return null;
				tile4Set.add(hand.getTile(jokerorg));	// J 1 5 5 ....
				handIndex = jokerorg;
				while(handIndex < hand.size()) {	
					if(!hasSameColor(tile4Set, hand.getTile(handIndex)) 			//no same color && same # && tile4Set # < 4
							&& hand.getTile(handIndex).getNumber() == tile4Set.get(playIndex).getNumber()
							&& tile4Set.size() < 4)
					{
						tile4Set.add(hand.getTile(handIndex));
						playIndex++;		
						disconnect = false;
						handIndex++;
						if(handIndex == hand.size())
							disconnect = true;
					} else
						disconnect = true;
					
					if (disconnect){ // disconnect
						disconnect = false;
						if(tile4Set.size() <= 3) {
							if(jokerNum > 0) {
								tile4Set.add( hand.getTile(0)); // add joker
								jokerNum--;
								playIndex++;
								if(tile4Set.size() >= 3) {
									if( checkSum(tile4Set) >= checkSum(LargestSet))
										LargestSet = tile4Set;
								}
								if(handIndex == hand.size())
									break;
							} else if(tile4Set.size() < 3){
								for(int i = 0; i<tile4Set.size();i++) {
									if(tile4Set.get(i).getColor() == Tile.Color.JOKER) 
										jokerNum++;
								}
								if(handIndex == hand.size())
									break;
								tile4Set= new ArrayList<Tile>();	
								tile4Set.add(hand.getTile(handIndex));
								playIndex = 0;
								handIndex++;
							} else {
								if( checkSum(tile4Set) > checkSum(LargestSet))
									LargestSet = tile4Set;
								if(handIndex == hand.size())
									break;
								for(int i = 0; i<tile4Set.size();i++) {
									if(tile4Set.get(i).getColor() == Tile.Color.JOKER) 
										jokerNum++;
								}
								tile4Set= new ArrayList<Tile>();	
								tile4Set.add(hand.getTile(handIndex));
								playIndex = 0;
								handIndex++;
							}
						} else {
							if( checkSum(tile4Set) >= checkSum(LargestSet)) {
								LargestSet = tile4Set;
							}	
							if(handIndex == hand.size())
								break;
							disconnect = false;
							for(int i = 0; i<tile4Set.size();i++) {
								if(tile4Set.get(i).getColor() == Tile.Color.JOKER) 
									jokerNum++;
							}
							tile4Set= new ArrayList<Tile>();	
							tile4Set.add(hand.getTile(handIndex));
							playIndex = 0;
							handIndex++;
						}
					}
				}
			}	else
					break;
			playIndex = 0;
			tile4Set= new ArrayList<Tile>();	
			if(jokerorg > 1) {//if 2 joker, first only 1 joker first, than 2
				jokerNum = 2;		
			}
			count--;
		}									
		return LargestSet;
	}
	
	public int checkInitialSum() {
		// TODO Auto-generated method stub
		//check from run to Set sum num, return total sum
		ArrayList<Tile> run = new ArrayList<Tile>();
		ArrayList<Tile> Set = new ArrayList<Tile>();
		//ArrayList<Tile>tempHandArray = new  ArrayList<Tile>(hand);
		Hand temphand = new Hand(hand);
		Hand orghand = new Hand(hand);
		hand = temphand;
		int count = 0;
		while(true) {
			run = findRun();	
			//System.out.println("RUN: " + run);
			if(run != null) {
				count += checkSum(run);
				for(int i = run.size()-1; i>=0;i--) {		//remove joker fisrt, beacuse of the order
					if(run.get(i).isJoker()) {
						temphand.remove(temphand.size()-1);
						run.remove(i);
					}
				}
				for(int i = run.size()-1; i>=0;i--) {
						temphand.remove(temphand.indexOf(run.get(i)));
				}
			}	else
					break;
		}
		if(temphand.size() ==0) {
			hand = orghand;
			return count;
		}
		if(count < 30) {
			while(true) {
				Set = findSet();
				if(Set != null) {
					count += checkSum(Set);
					for(int i = Set.size()-1; i>=0;i--) {		//remove joker fisrt, beacuse of the order
						if(Set.get(i).isJoker()) {
							temphand.remove(0);
							Set.remove(i);
						}
					}
					for(int i = 0; i<Set.size();i++) {
						temphand.sort();
						temphand.remove(temphand.indexOf(Set.get(i)));
					}
				}	else
						break;
			}
		}
		hand = orghand;
		return count;
	}

	public HashMap<Tile, Integer> findMeldsOnTable() {
		// TODO Auto-generated method stub
		HashMap<Tile, Integer> mdlesMap = new HashMap<Tile,Integer>();
		ArrayList<Meld> tempMeldList = new ArrayList<Meld>();
		Hand temphand = new Hand(hand);
		hand = temphand;
		//System.out.println(temphand);
		//System.out.println(tempMeldList);
		for(Meld m : meldList) {
			Meld tempMeld = new Meld(m);
			tempMeldList.add(tempMeld);
		}
		meldList = tempMeldList;
		
		if(temphand.size() == 0 || tempMeldList.size()==0)
			return null;
		for(int j=0; j<temphand.size();j++) {	
			for(int i=0; i<tempMeldList.size();i++) {
				if(tempMeldList.get(i).size() != 1 && tempMeldList.get(i).getHead().getColor() != Tile.Color.JOKER) {
					if(tempMeldList.get(i).getMap().containsKey(temphand.getTile(j).toString())) {	//if can add and not used
						mdlesMap.put(temphand.getTile(j),i);
						return mdlesMap;
					}
				}
			}
		}
		return null;
	}
	
	public HashMap<ArrayList<Tile>, Integer> findRunMove(Meld meld) {
		// TODO Auto-generated method stub
		if(hand.size() == 0)
			return null;
		HashMap<ArrayList<Tile>, Integer> runCanMove = new HashMap<ArrayList<Tile>,Integer>();
		ArrayList<Tile> tile4Run = new ArrayList<Tile>();
		boolean disconnect = true;	// check if next tile number & color is connecte
		boolean run = true;		// used to stop loop when reach 30 points
		int playIndex = 0;	//point to tile4Run position;
		int handIndex;  //used when looping hand cards
		int jokerNum = 0;	// joker number
		int jokerorg = 0;	// joker number (final)
		boolean hasJoker = false;
		Meld tempmeld = new Meld(meld);
		hand.sort();		//sort
		//check if has joker
		Tile firstTile = null;
		firstTile = tempmeld.getTile(0);
		
		for(int i = 0; i<hand.size();i++) {			//count size of joker;
			if(hand.getTile(i).getColor() == Tile.Color.JOKER) {
				jokerNum++;
				jokerorg++;
				hasJoker = true;
			}
		}
		tile4Run.add(hand.getTile(0));
		for(int i=1; i<hand.size()-jokerNum;i++) {
			if(tile4Run.get(playIndex).getColor() == hand.getTile(i).getColor() 
					&& hand.getTile(i).getNumber()-1 == tile4Run.get(playIndex).getNumber()) {
				tile4Run.add(hand.getTile(i));
				playIndex++;
				disconnect  = false;
				if(i == hand.size()-jokerNum-1) 
					disconnect = true;
			}else
				disconnect = true;
		
			if(disconnect) {
				disconnect  = false;
				if(tempmeld.size() == 1) {	// Jk from replace
					if(tile4Run.size() == 2) {
						runCanMove.put(tile4Run, 0);
						return runCanMove;
					}
				}
				if(tempmeld.isRun()) {
					if(tile4Run.size() == 2) {
						Tile removeTail = tempmeld.removeTail();
						if(tempmeld.isValid()) {	// if can move
							if(removeTail.isJoker()) {	// if is joker, can move for sure
								tempmeld.addTail(removeTail);
								runCanMove.put(tile4Run, tempmeld.size()-1);
								return runCanMove;
							}else {	//check if can move to add 
								if((tile4Run.get(0).getColor() == removeTail.getColor() 
										&& tile4Run.get(0).getNumber() -1 == removeTail.getNumber())
										|| (tile4Run.get(0).getColor() == removeTail.getColor()  && tile4Run.get(1).getNumber() + 1 == removeTail.getNumber())) {
									tempmeld.addTail(removeTail);
									runCanMove.put(tile4Run, tempmeld.size()-1);
									return runCanMove;
								}
								tempmeld.addTail(removeTail);
							}
						}else {	//cannot move, add back
							tempmeld.addTail(removeTail);
						}
						//tail cannot move, check head
						Tile removeHead = tempmeld.removeHead();
						if(tempmeld.isValid()) {	// if can move
							if(removeHead.isJoker()) {	// if is joker, can move for sure
								tempmeld.addHead(removeHead);
								runCanMove.put(tile4Run, 0);
								return runCanMove;
							}else {	//check if can move to add 
								if((tile4Run.get(0).getColor() == removeHead.getColor() 
										&& tile4Run.get(0).getNumber() -1 == removeHead.getNumber())
										|| (tile4Run.get(0).getColor() == removeHead.getColor()  && tile4Run.get(1).getNumber() + 1 == removeHead.getNumber())) {
											tempmeld.addHead(removeHead);
											//System.out.println("----------: " + tempmeld);
											runCanMove.put(tile4Run, 0);
											return runCanMove;
									}
								tempmeld.addHead(removeHead);
								}
							}else {	//cannot move, add back
								tempmeld.addHead(removeHead);
							}
						}
						//both cannot be movied, check next
						tile4Run = new ArrayList<Tile>();
						tile4Run.add(hand.getTile(i));
						playIndex = 0;
				} else {		//is set
					if(tile4Run.size() == 2) {
						Tile removeTail = tempmeld.removeTail();
						if(tempmeld.isValid()) {	// if can move
							//System.out.println("------- set meld: " + tempmeld + " --- tail: " + removeTail);
							if(removeTail.isJoker()) {	// if is joker, can move for sure
								tempmeld.addTail(removeTail);
								runCanMove.put(tile4Run, tempmeld.size()-1);
								return runCanMove;
							}else {	//check if can move to add 
								if((tile4Run.get(0).getColor() == removeTail.getColor() 
										&& tile4Run.get(0).getNumber() -1 == removeTail.getNumber())
										|| (tile4Run.get(0).getColor() == removeTail.getColor()  && tile4Run.get(1).getNumber() + 1 == removeTail.getNumber())) {
									tempmeld.addTail(removeTail);
									runCanMove.put(tile4Run, tempmeld.size()-1);
									return runCanMove;
								}
								tempmeld.addTail(removeTail);
								while(tempmeld.getTile(0) != firstTile) {
									removeTail = tempmeld.removeHead();
									tempmeld.addTail(removeTail);
								}
							}
						}else {	//cannot move, add back
							tempmeld.addTail(removeTail);
							while(tempmeld.getTile(0) != firstTile) {
								removeTail = tempmeld.removeHead();
								tempmeld.addTail(removeTail);
							}
						}
						//tail cannot move, check head
						//re sort tempmeld

						while(tempmeld.getTile(0) != firstTile) {
							removeTail = tempmeld.removeHead();
							tempmeld.addTail(removeTail);
						}
						Tile removeHead = tempmeld.removeHead();
						if(tempmeld.isValid()) {	// if can move
							//System.out.println("------- set meld: " + tempmeld + " --- head: " + removeHead);
							if(removeHead.isJoker()) {	// if is joker, can move for sure
								tempmeld.addHead(removeHead);
								runCanMove.put(tile4Run, 0);
								return runCanMove;
							}else {	//check if can move to add 
								if((tile4Run.get(0).getColor() == removeHead.getColor() 
										&& tile4Run.get(0).getNumber() -1 == removeHead.getNumber())
										|| (tile4Run.get(0).getColor() == removeHead.getColor()  && tile4Run.get(1).getNumber() + 1 == removeHead.getNumber())) {
											tempmeld.addHead(removeHead);
											runCanMove.put(tile4Run, 0);
											return runCanMove;
									}
								tempmeld.addHead(removeHead);
								while(tempmeld.getTile(0) != firstTile) {
									removeTail = tempmeld.removeHead();
									tempmeld.addTail(removeTail);
								}
								}
							}else {	//cannot move, add head
								tempmeld.addHead(removeHead);
								while(tempmeld.getTile(0) != firstTile) {
									removeTail = tempmeld.removeHead();
									tempmeld.addTail(removeTail);
								}
							}
						}
						//both cannot be movied, check next
						tile4Run = new ArrayList<Tile>();
						tile4Run.add(hand.getTile(i));
						playIndex = 0;
				}
			}
		}
		return null;
	}

	public HashMap<ArrayList<Tile>, Integer> findSetMove(Meld meld) {
		// TODO Auto-generated method stub
		if(hand.size() == 0)
			return null;
		HashMap<ArrayList<Tile>, Integer> setCanMove = new HashMap<ArrayList<Tile>,Integer>();
		ArrayList<Tile> tile4Set = new ArrayList<Tile>();
		boolean disconnect = true;	// check if next tile number & color is connecte
		boolean run = true;		// used to stop loop when reach 30 points
		int playIndex = 0;	//point to tile4Run position;
		int handIndex;  //used when looping hand cards
		int jokerNum = 0;	// joker number
		int jokerorg = 0;	// joker number (final)
		boolean hasJoker = false;
		Meld tempmeld = new Meld(meld);
		hand.sortByNum();		//sort
		Tile firstTile = null;
		firstTile = tempmeld.getTile(0);
		for(int i = 0; i<hand.size();i++) {			//count size of joker;
			if(hand.getTile(i).getColor() == Tile.Color.JOKER) {
				jokerNum++;
				jokerorg++;
				hasJoker = true;
			}
		}
		
		tile4Set.add(hand.getTile(0));
		for(int i=1; i<hand.size()-jokerNum;i++) {		// R1 B1
			if(!hasSameColor(tile4Set, hand.getTile(i))
					&& hand.getTile(i).getNumber() == tile4Set.get(playIndex).getNumber()) {
				tile4Set.add(hand.getTile(i));
				playIndex++;
				disconnect = false;
				if(i == hand.size()-jokerNum-1) 
					disconnect = true;
			} else
				disconnect = true;
			
			if(disconnect){
				disconnect = false;
				if(tempmeld.size() == 1) {	// Jk from replace
					if(tile4Set.size() == 2) {
						setCanMove.put(tile4Set, 0);
						return setCanMove;
					}
				}
				if(tempmeld.isSet()) {
						if(tile4Set.size() == 2 && tempmeld.size() >= 4) {
							for(int m=0;m<tempmeld.size();m++) {
								Tile removeTail = tempmeld.removeTail();
								if(tempmeld.isValid()) {	// if can move
									if(removeTail.isJoker()) {	// if is joker, can move for sure
										tempmeld.addTail(removeTail);
										setCanMove.put(tile4Set, tempmeld.size()-1);
										return setCanMove;
									}else {	//check if can move to add 
										if(!hasSameColor(tile4Set, removeTail) 			//no same color && same # && tile4Set # < 4
												&& removeTail.getNumber() == tile4Set.get(playIndex).getNumber()) {
													tempmeld.addTail(removeTail);
													setCanMove.put(tile4Set, tempmeld.size()-1);
													return setCanMove;
											}
											tempmeld.addTail(removeTail);
											while(tempmeld.getTile(0) != firstTile) {
												removeTail = tempmeld.removeHead();
												tempmeld.addTail(removeTail);
											}
										}
								} else {	// cannot move, added back
									tempmeld.addTail(removeTail);
									while(tempmeld.getTile(0) != firstTile) {
										removeTail = tempmeld.removeHead();
										tempmeld.addTail(removeTail);
									}
								}
								//tail cannot move, check head
								//re sort tempmeld
								while(tempmeld.getTile(0) != firstTile) {
									removeTail = tempmeld.removeHead();
									tempmeld.addTail(removeTail);
								}
								
								Tile removeHead = tempmeld.removeHead();
								if(tempmeld.isValid()) {	// if can move
									if(removeHead.isJoker()) {	// if is joker, can move for sure
										tempmeld.addHead(removeHead);
										setCanMove.put(tile4Set, 0);
										return setCanMove;
									}else {	//check if can move to add 
										if(!hasSameColor(tile4Set, removeHead) 			//no same color && same # && tile4Set # < 4
												&& removeHead.getNumber() == tile4Set.get(playIndex).getNumber()) {
													tempmeld.addHead(removeHead);
													setCanMove.put(tile4Set, 0);
													return setCanMove;
											}
											tempmeld.addHead(removeHead);
											while(tempmeld.getTile(0) != firstTile) {
												removeTail = tempmeld.removeHead();
												tempmeld.addTail(removeTail);
											}
										}
								} else {	// cannot move, added back
									tempmeld.addHead(removeHead);
									while(tempmeld.getTile(0) != firstTile) {
										removeTail = tempmeld.removeHead();
										tempmeld.addTail(removeTail);
									}
								}
							}		
					}
						//both cannot be movied, check next
						tile4Set.clear();	
						tile4Set.add(hand.getTile(i));
						playIndex = 0;
				}else {	// is run
					if(tile4Set.size() == 2 && tempmeld.size() >= 4) {
						for(int m=0;m<tempmeld.size();m++) {
							Tile removeTail = tempmeld.removeTail();
							if(tempmeld.isValid()) {	// if can move
								if(removeTail.isJoker()) {	// if is joker, can move for sure
									tempmeld.addTail(removeTail);
									setCanMove.put(tile4Set, tempmeld.size()-1);
									return setCanMove;
								}else {	//check if can move to add 
									if(!hasSameColor(tile4Set, removeTail) 			//no same color && same # && tile4Set # < 4
											&& removeTail.getNumber() == tile4Set.get(playIndex).getNumber()) {
												tempmeld.addTail(removeTail);
												//System.out.println("----------: " + tempmeld);
												setCanMove.put(tile4Set, tempmeld.size()-1);
												return setCanMove;
										}
										tempmeld.addTail(removeTail);
									}
							} else {	// cannot move, added back
								tempmeld.addTail(removeTail);
							}
							//tail cannot move, check head		
							
							Tile removeHead = tempmeld.removeHead();
							if(tempmeld.isValid()) {	// if can move
								if(removeHead.isJoker()) {	// if is joker, can move for sure
									tempmeld.addHead(removeHead);
									setCanMove.put(tile4Set, 0);
									return setCanMove;
								}else {	//check if can move to add 
									if(!hasSameColor(tile4Set, removeHead) 			//no same color && same # && tile4Set # < 4
											&& removeHead.getNumber() == tile4Set.get(playIndex).getNumber()) {
										tempmeld.addHead(removeHead);
												setCanMove.put(tile4Set, 0);
												return setCanMove;
										}
										tempmeld.addHead(removeHead);
									}
							} else {	// cannot move, added back
								tempmeld.addHead(removeHead);
							}
						}		
				}
					//both cannot be movied, check next
					
					tile4Set.clear();	
					tile4Set.add(hand.getTile(i));
					playIndex = 0;
				}
			}
		}
		return null;
	}

	public boolean canPlayAll() {
		// TODO Auto-generated method stub
		if(hand.size() == 0)
			return false;
		HashMap<Tile, Integer> mdlesMap = new HashMap<Tile,Integer>();
		Hand temphand = new Hand(hand);
		Hand orghand = new Hand(hand);
		ArrayList<Meld> tempMeldList = new ArrayList<Meld>();
		ArrayList<Meld> orgMeldList = new ArrayList<Meld>();
		ArrayList<Tile> run = new ArrayList<Tile>();
		ArrayList<Tile> group = new ArrayList<Tile>();
		HashMap<Tile, Integer> meld2Table = new HashMap<Tile,Integer>();
		HashMap<ArrayList<Tile>, Integer> moveRun2Table = new HashMap<ArrayList<Tile>,Integer>();
		HashMap<ArrayList<Tile>, Integer> moveSet2Table = new HashMap<ArrayList<Tile>,Integer>();
		HashMap<ArrayList<Tile>,Integer> cutRunToTable = new HashMap<ArrayList<Tile>,Integer>();
		HashMap<Tile,Integer>replace = new HashMap<Tile,Integer>();
		
		for(Meld m : meldList) {
			Meld tempMeld = new Meld(m);
			tempMeldList.add(tempMeld);
			Meld orgMeld = new Meld(m);
			orgMeldList.add(orgMeld);
		}
		meldList = tempMeldList;
		hand = temphand;
		int count = 0;
		int size = hand.size();
		
		while(true) {
			//System.out.println(temphand);
				run = findRun();
				if(run != null) {
					//System.out.println("RUN:" + run);
					for(int i = run.size()-1; i>=0;i--) {		//remove joker fisrt, beacuse of the order
						if(run.get(i).isJoker()) {
							temphand.remove(temphand.size()-1);
							run.remove(i);
							count++;
						}
					}
					for(int i = run.size()-1; i>=0;i--) {
							temphand.remove(temphand.indexOf(run.get(i)));
							count++;
					}
				}	else
						break;
		}

		while(true) {
			//System.out.println(temphand);
				group = findSet();
				if(group != null) {
					//System.out.println("Set: " + group);
					for(int i = group.size()-1; i>=0;i--) {		//remove joker fisrt, beacuse of the order
						if(group.get(i).isJoker()) {
							temphand.remove(0);
							group.remove(i);
							count++;
						}
					}
					for(int i = 0; i<group.size();i++) {
						temphand.sort();
						temphand.remove(temphand.indexOf(group.get(i)));
						count++;
					}
				}else
						break;
		}
		//System.out.println(hand);
		while(true) {
			//System.out.println(meldList);
			meld2Table = findMeldsOnTable();
			if(meld2Table != null) {
				//System.out.println("M2T: " + meld2Table);
				for(Entry<Tile, Integer> entry : meld2Table.entrySet()) {
					Tile tile = entry.getKey();
					int index = entry.getValue();
					temphand.remove(temphand.indexOf(tile));
					tempMeldList.get(index).addTail(tile);
					meldList = tempMeldList;
					hand = temphand;
				}
				count++;
			}else
					break;
		}

		while(true) {
			int listSize = tempMeldList.size();
			temphand = hand;
			for(Meld meld : tempMeldList) {
				if(meld.size() == 0)
					continue;
				moveRun2Table = findRunMove(meld);
				if(moveRun2Table != null) {
					for(Entry<ArrayList<Tile>, Integer> entry : moveRun2Table.entrySet()) {
						ArrayList<Tile> runMove = entry.getKey();
						int index = entry.getValue();	// 
						Meld m = new Meld();
						m.addTail(runMove.get(0));
						m.addTail(runMove.get(1));
						m.addTail(meld.getTile(index));
						tempMeldList.add(m);
						temphand.remove(temphand.indexOf(runMove.get(1)));
						temphand.remove(temphand.indexOf(runMove.get(0)));
						if(index == 0)
							meld.removeHead();
						else
							meld.removeTail();
						count+=2;
					}
					break;
				}
			}
			if(listSize == tempMeldList.size())
				break;
		}
		
		while(true) {
			int listSize = tempMeldList.size();
			temphand = hand;
			for(Meld meld : tempMeldList) {
				if(meld.size() == 0)
					continue;
				moveSet2Table = findSetMove(meld);
				if(moveSet2Table != null) {

					for(Entry<ArrayList<Tile>, Integer> entry : moveSet2Table.entrySet()) {
						ArrayList<Tile> setMove = entry.getKey();
						int index = entry.getValue();	// 
						Meld m = new Meld();
						m.addTail(setMove.get(0));
						m.addTail(setMove.get(1));
						m.addTail(meld.getTile(index));
						tempMeldList.add(m);
						temphand.remove(temphand.indexOf(setMove.get(1)));
						temphand.remove(temphand.indexOf(setMove.get(0)));
						if(index == 0)
							meld.removeHead();
						else
							meld.removeTail();
						count+=2;
					}
					break;
				}
			}
			if(listSize == tempMeldList.size())
				break;
		}
		
		while(true) {
			int listSize = tempMeldList.size();
			temphand = hand;
			for(Meld meld : tempMeldList) {
				if(meld.size() == 0)
					continue;
				cutRunToTable = findRunCut(meld);
				if(cutRunToTable != null) {	// can cut
					for(Entry<ArrayList<Tile>, Integer> Entry : cutRunToTable.entrySet()) {
						ArrayList<Tile> runCut = Entry.getKey();
						int index = Entry.getValue();	// 
						Meld m = new Meld();
						while(meld.size()-1 != index) {
							m.addHead(meld.removeTail());
						}
						tempMeldList.add(m);
						for(Tile tile : runCut) {
							meld.addTail(temphand.remove(temphand.indexOf(tile)));
						}
						count+=runCut.size();
					}
					break;
				}
			}
			if(listSize == tempMeldList.size())
				break;
		}
		while(true) {
			int listSize = tempMeldList.size();
			temphand = hand;
			for(Meld meld : tempMeldList) {
				replace = findReplace(meld);
				if(replace != null) {
					for(Entry<Tile, Integer>Entry : replace.entrySet()) {
						Tile tile = Entry.getKey();
						int index = Entry.getValue();
						Tile joker =  meld.replace(tile, index);
						Meld m = new Meld();
						m.addTail(joker);
						tempMeldList.add(m);
						temphand.remove(temphand.indexOf(tile));
					}
					count++;
					break;
				}
			}
			if(listSize == tempMeldList.size())
				break;
		}
		meldList = orgMeldList;
		hand = orghand;
		//System.out.println(size);
		//System.out.println(count);
		return count == size;
	}

	/*
	 * possible situation 1 and 2: 
	 * 1: meld: a s d f g, hand d f // f g // a s
	 * get rid of f g and a s
	 * cut one before f
	 * 2: meld a s d f g, hand d // a // g
	 * get rid of a and g
	 * cut one before d
	 * anytime, cut left, and add to the original meld
	 * 
	 */
	public HashMap<ArrayList<Tile>, Integer> findRunCut(Meld meld) {
		// TODO Auto-generated method stub
		if(hand.size() == 0)
			return null;
		HashMap<ArrayList<Tile>, Integer> runCanSet = new HashMap<ArrayList<Tile>,Integer>(); //<tile to play, index>
		ArrayList<Tile> tile4Run = new ArrayList<Tile>();
		boolean disconnect = true;	// check if next tile number & color is connecte
		boolean run = true;		// used to stop loop when reach 30 points
		int playIndex = 0;	//point to tile4Run position;
		int handIndex;  //used when looping hand cards
		int jokerNum = 0;	// joker number
		int jokerorg = 0;	// joker number (final)
		boolean hasJoker = false;
		Meld tempmeld = new Meld(meld);
		hand.sort();		//sort
		//check if has joker
		for(int i = 0; i<hand.size();i++) {			//count size of joker;
			if(hand.getTile(i).getColor() == Tile.Color.JOKER) {
				jokerNum++;
				jokerorg++;
				hasJoker = true;
			}
		}
		tile4Run.add(hand.getTile(0));
		for(int i=1; i<hand.size()-jokerNum;i++) {
			if(tile4Run.get(playIndex).getColor() == hand.getTile(i).getColor() 
					&& hand.getTile(i).getNumber()-1 == tile4Run.get(playIndex).getNumber()) {
				tile4Run.add(hand.getTile(i));
				playIndex++;
				disconnect  = false;
				if(i == hand.size()-jokerNum-1) 
					disconnect = true;
			}else
				disconnect = true;
		
			if(disconnect) {
				disconnect  = false;
				if(tempmeld.isRun()) {
					if(tile4Run.size() == 2) {
						if(tempmeld.size() >= 4) {	// 1 a b 4
							for(int t=0; t<tempmeld.size();t++) {
								if(tile4Run.get(0).toString().equals(tempmeld.getTile(t).toString())) {
									if(t != 0 && t + 1 != tempmeld.size()-1) {	// can cut 0 | a b 4
										if(t == 1 && tempmeld.getTile(t).getColor() == Tile.Color.JOKER) {
											continue;
										} else if(t +1 == tempmeld.size()-2 &&  tempmeld.getTile(t+1).getColor() == Tile.Color.JOKER) {
											continue;
										}else {
											runCanSet.put(tile4Run, t-1);
											return runCanSet;
										}
									} else
										break;
								}  
							}
						}
					} else if (tile4Run.size() == 1) {
						if(tempmeld.size() >= 5) {	// 1 2 a 4 5 or 1 2 3 a 5 or 1 2 3 4 a or 1 a 3 4 5
							for(int t=0; t<tempmeld.size();t++) {
								if(tile4Run.get(0).toString().equals(tempmeld.getTile(t).toString())) {
									if(t >= 2 && t  < tempmeld.size()-2) {	// can cut 0 1 | a 3 4
										runCanSet.put(tile4Run, t-1);
										return runCanSet;
									}else
										break;
								}
							}
						}
					}
					//cannot be cutted, check next
					tile4Run = new ArrayList<Tile>();
					tile4Run.add(hand.getTile(i));
					playIndex = 0;
				} else {	//meld is set
					return null;
				}
			}
		}
		
		if(hand.size() == 1) {
			if(tempmeld.size() >= 5) {	// 1 2 a 4 5
				for(int t=0; t<tempmeld.size();t++) {
					if(tile4Run.get(0).toString().equals(tempmeld.getTile(t).toString())) {
						if(t >= 2 && t + 1 != tempmeld.size()-1) {	// can cut 0 1 | a 3 4
							runCanSet.put(tile4Run, t-1);
							return runCanSet;
						}else
							break;
					}
				}
			}
		}
		return null;
	}

	public HashMap<Tile, Integer> findReplace(Meld meld) {
		// TODO Auto-generated method stub
		if(hand.size() == 0 || meld.size() < 3)
			return null;
		
		HashMap<Tile, Integer> replaceTile = new HashMap<Tile,Integer>(); //<tile to replace, meld index>
		boolean hasJoker = false;
		//System.out.println(meld.size());
		for(int t=0; t<meld.size();t++) {			//count size of joker;
			if(meld.getTile(t).getColor() == Tile.Color.JOKER) 
				hasJoker = true;
		}

		if(hasJoker) {
			if(meld.isRun()) {
				for(int t=0; t<meld.size();t++) {
					if(meld.getTile(t).isJoker()) {	// is joker
						for(int ht=0; ht<hand.size();ht++) {
							if(t == 0) {	//joker at first tile
								if(meld.getTile(t+1).isJoker()) {			// continuously joker 
									if(hand.getTile(ht).getColor() == meld.getTile(t+2).getColor()
											&& hand.getTile(ht).getNumber()+2 == meld.getTile(t+2).getNumber()) {
										replaceTile.put(hand.getTile(ht), t);
										return replaceTile;
									}
								}else {
									if(hand.getTile(ht).getColor() == meld.getTile(t+1).getColor()
											&& hand.getTile(ht).getNumber()+1 == meld.getTile(t+1).getNumber()) {
										replaceTile.put(hand.getTile(ht), t);
										return replaceTile;
									}
								}
							} else if(t == meld.size()-1) {	// joker at last tile
								if(meld.getTile(t - 1).isJoker()) {			// continuously joker 
									if(hand.getTile(ht).getColor() == meld.getTile(t - 2).getColor()
											&& hand.getTile(ht).getNumber() - 2 == meld.getTile(t-2).getNumber()) {
										replaceTile.put(hand.getTile(ht), t);
										return replaceTile;
									}
								}else {
									if(hand.getTile(ht).getColor() == meld.getTile(t -1).getColor()
											&& hand.getTile(ht).getNumber() - 1 == meld.getTile(t -1).getNumber()) {
										replaceTile.put(hand.getTile(ht), t);
										return replaceTile;
									}
								}
							} else {
								if(meld.getTile(t - 1).isJoker()) {			// 1 J t 4
									if(hand.getTile(ht).getColor() == meld.getTile(t + 1).getColor()	
											&& hand.getTile(ht).getNumber() + 1 == meld.getTile(t + 1).getNumber()) {
										replaceTile.put(hand.getTile(ht), t);
										return replaceTile;
									}
								}else {	// 1 t J 4 or 1 t 4
									if(hand.getTile(ht).getColor() == meld.getTile(t -1).getColor()
											&& hand.getTile(ht).getNumber() - 1 == meld.getTile(t -1).getNumber()) {
										replaceTile.put(hand.getTile(ht), t);
										return replaceTile;
									}
								}
							}
						}
					}
				}
			} else {
				for(int t=0; t<meld.size();t++) {
					if(meld.getTile(t).isJoker()) {	// is joker
						for(int ht=0; ht<hand.size();ht++) {
							if(t == 0) {		//joker is first tile
								if(meld.getTile(t+1).isJoker()) {			// continuously joker 
									if(hand.getTile(ht).getNumber() == meld.getTile(t+2).getNumber()
											&& !hasSameColor3(meld,hand.getTile(ht))) {
										replaceTile.put(hand.getTile(ht), t);
										return replaceTile;
									}
								} else {
									if(hand.getTile(ht).getNumber() == meld.getTile(t+1).getNumber()
											&& !hasSameColor3(meld,hand.getTile(ht))) {
										replaceTile.put(hand.getTile(ht), t);
										return replaceTile;
									}
								}
							} else if(t == meld.size()-1) {		// joker is last tile
								if(meld.getTile(t-1).isJoker()) {			// continuously joker 
									if(hand.getTile(ht).getNumber() == meld.getTile(t-2).getNumber()
											&& !hasSameColor3(meld,hand.getTile(ht))) {
										replaceTile.put(hand.getTile(ht), t);
										return replaceTile;
									}
								} else {
									if(hand.getTile(ht).getNumber() == meld.getTile(t-1).getNumber()
											&& !hasSameColor3(meld,hand.getTile(ht))) {
										replaceTile.put(hand.getTile(ht), t);
										return replaceTile;
									}
								}
							} else {
								if(meld.getTile(t-1).isJoker()) {			// a J _ b
									if(hand.getTile(ht).getNumber() == meld.getTile(t+1).getNumber()
											&& !hasSameColor3(meld,hand.getTile(ht))) {
										replaceTile.put(hand.getTile(ht), t);
										return replaceTile;
									}
								} else {	// a _ J b or a _ b
									if(hand.getTile(ht).getNumber() == meld.getTile(t-1).getNumber()
											&& !hasSameColor3(meld,hand.getTile(ht))) {
										replaceTile.put(hand.getTile(ht), t);
										return replaceTile;
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	public boolean hasMapfromTable(ArrayList<Tile> tile, ArrayList<Meld> meldList) {
		// TODO Auto-generated method stub
		int countMep = 0;
		if(tile.size() == 1) 
			return true;
		ArrayList<Tile> tempTile  = new ArrayList<Tile>(tile);
		Meld meld = new Meld(tempTile);
		if(meld.isSet())
			return true;
		for(Meld m : meldList) {
			if(m.size() == 1)
				continue;
			else {
				for(int i=0; i<m.size();i++) {
					if(meld.getMap().containsKey(m.getTile(i).toString())) {
						if(!m.getTile(i).isJoker())
							countMep++;
						else {
							if(matchJoker(meld, m, i))
								countMep++;
						}
					}
				}
			}
		}
		return countMep >= 2;
	}
	
	public boolean matchJoker(Meld playMeld, Meld tableMeld, int index) {		
		/*
		 * index is joker position on table
		 * this function used to check if joker from play map is fixed into table 
		 */
		boolean hasJoker = false;
		for(int i=0; i<playMeld.size();i++)
		{
			if(playMeld.getTile(i).isJoker())
				hasJoker = true;
		}
		
		if(playMeld.isRun() && tableMeld.isRun()) {
			if(index == 0 || index == tableMeld.size()-1) {			// joker on table  at head or tails
				if(index == 0) {	//head
					if(hasJoker) {	// playMeld has joker
						if(playMeld.getHead().isJoker()) {	//play first tile is joker
							if(tableMeld.getTile(index+1).getColor() == playMeld.getRunColor()		//at back
									&&tableMeld.getTile(index+1).getNumber() == playMeld.getTail().getNumber()+2) {
								return true;
							} 
							if(tableMeld.getTile(index+2).getColor() == playMeld.getRunColor()		//at front
									&&tableMeld.getTile(index+2).getNumber() == playMeld.getTile(1).getNumber()+1) {
								return true;
							}
						}else if(playMeld.getTail().isJoker()){	//play last tile is joker
							if(tableMeld.getTile(index+1).getColor() == playMeld.getRunColor()		//at front
									&&tableMeld.getTile(index+1).getNumber() == playMeld.getHead().getNumber()) {
								return true;
							}
							if(tableMeld.getTile(index+1).getColor() == playMeld.getRunColor()		//at back
									&&tableMeld.getTile(index+1).getNumber() == playMeld.getTile(playMeld.size()-2).getNumber()+3) {
								return true;
							} 
						} else {
							if(tableMeld.getTile(index+1).getColor() == playMeld.getRunColor()		//at front
									&&tableMeld.getTile(index+1).getNumber() == playMeld.getHead().getNumber()) {
								return true;
							}
							if(tableMeld.getTile(index+1).getColor() == playMeld.getRunColor()		//at back
									&&tableMeld.getTile(index+1).getNumber() == playMeld.getTail().getNumber()+2) {
								return true;
							} 
						}
					}else {	// playMeld has no joker
						if(tableMeld.getTile(index+1).isJoker()) {		// next is joker too,  J J _ , check next
							if(tableMeld.getTile(index+2).getColor() == playMeld.getRunColor()
									&&tableMeld.getTile(index+2).getNumber() == playMeld.getHead().getNumber()+1) {
								return true;
							}
							if(tableMeld.getTile(index+2).getColor() == playMeld.getRunColor()
									&&tableMeld.getTile(index+2).getNumber() == playMeld.getTail().getNumber()+3) {
								return true;
							}
						}else {	// next is not joker,  J _ _ 
							if(tableMeld.getTile(index+1).getColor() == playMeld.getRunColor()		//at front
									&&tableMeld.getTile(index+1).getNumber() == playMeld.getHead().getNumber()) {
								return true;
							} 
							if(tableMeld.getTile(index+1).getColor() == playMeld.getRunColor()		//at back
									&&tableMeld.getTile(index+1).getNumber() == playMeld.getTail().getNumber()+2) {
								return true;
							} 
						}
					}
				} else {	//tail
					if(hasJoker) {	// playMeld has joker
						if(playMeld.getHead().isJoker()) {	//play head  is joker
							if(tableMeld.getTile(index-1).getColor() == playMeld.getRunColor()		//at front
									&&tableMeld.getTile(index-1).getNumber() == playMeld.getTile(1).getNumber()-3) {
								return true;
							} 
							if(tableMeld.getTile(index-1).getColor() == playMeld.getRunColor()		//at back
									&& tableMeld.getTile(index-1).getNumber() == playMeld.getTail().getNumber()) {
								return true;
							} 
						}else if(playMeld.getHead().isJoker()) {	//play Tail  is joker
							if(tableMeld.getTile(index-1).getColor() == playMeld.getRunColor()		//at front
									&&tableMeld.getTile(index-1).getNumber() == playMeld.getHead().getNumber()-2) {
								return true;
							} 
							if(tableMeld.getTile(index-1).getColor() == playMeld.getRunColor()		//at back
									&& tableMeld.getTile(index-1).getNumber() == playMeld.getTile(playMeld.size()-2).getNumber()+1) {
								return true;
							} 
						}else {
							if(tableMeld.getTile(index-1).getColor() == playMeld.getRunColor()		//at front
									&&tableMeld.getTile(index-1).getNumber() == playMeld.getHead().getNumber()-2) {
								return true;
							} 
							if(tableMeld.getTile(index-1).getColor() == playMeld.getRunColor()		//at back
									&& tableMeld.getTile(index-1).getNumber() == playMeld.getTail().getNumber()) {
								return true;
							} 
						}
					}else {	// play has no joker
						if(tableMeld.getTile(index-1).isJoker()) {		// pres is joker too,  _ J J , check next
							if(tableMeld.getTile(index-2).getColor() == playMeld.getRunColor()		//at front
									&&tableMeld.getTile(index-2).getNumber() == playMeld.getHead().getNumber()-3) {
								return true;
							}
							if(tableMeld.getTile(index-2).getColor() == playMeld.getRunColor()		//at back
									&&tableMeld.getTile(index-2).getNumber() == playMeld.getTail().getNumber()-1) {
								return true;
							}
						}else {	// next is not joker,  J _ _ 
							if(tableMeld.getTile(index-1).getColor() == playMeld.getRunColor()		//at front
									&&tableMeld.getTile(index-1).getNumber() == playMeld.getHead().getNumber()-2) {
								return true;
							} 
							if(tableMeld.getTile(index-1).getColor() == playMeld.getRunColor()		//at back
									&& tableMeld.getTile(index-1).getNumber() == playMeld.getTail().getNumber()) {
								return true;
							} 
						}
					}
				}
			} else { 	//joker not at head and tail
				if(hasJoker) {// playMeld has joker
					if(playMeld.getHead().isJoker()) {	//play head tile is joker
						if(tableMeld.getTile(index+1).getColor() == playMeld.getRunColor()		//at front
								&&tableMeld.getTile(index+1).getNumber() == playMeld.getTile(1).getNumber()-1) {
							return true;
						} 
						if(tableMeld.getTile(index-1).getColor() == playMeld.getRunColor()		//at back
								&& tableMeld.getTile(index-1).getNumber() == playMeld.getTail().getNumber()) {
							return true;
						} 
					}else if(playMeld.getTail().isJoker()) {	//play Tail tile is joker
						if(tableMeld.getTile(index+1).getColor() == playMeld.getRunColor()		//at front
								&&tableMeld.getTile(index+1).getNumber() == playMeld.getHead().getNumber()) {
							return true;
						} 
						if(tableMeld.getTile(index-1).getColor() == playMeld.getRunColor()		//at back
								&& tableMeld.getTile(index-1).getNumber() == playMeld.getTile(playMeld.size()-2).getNumber()+1) {
							return true;
						} 
					}else {	//play both head and tail is not joker
						if(tableMeld.getTile(index+1).getColor() == playMeld.getRunColor()		//at front
								&&tableMeld.getTile(index+1).getNumber() == playMeld.getHead().getNumber()) {
							return true;
						} 
						if(tableMeld.getTile(index-1).getColor() == playMeld.getRunColor()		//at back
								&& tableMeld.getTile(index-1).getNumber() == playMeld.getTail().getNumber()) {
							return true;
						} 
					}
				}else {// playMeld has no joker
					if(tableMeld.getTile(index-1).isJoker()) {		//prev is joker
						if(tableMeld.getTile(index+1).getColor() == playMeld.getRunColor()		//at front
								&&tableMeld.getTile(index+1).getNumber() == playMeld.getHead().getNumber()) {
							return true;
						} 
						if(tableMeld.getTile(index+1).getColor() == playMeld.getRunColor()		//at back
								&& tableMeld.getTile(index+1).getNumber() == playMeld.getTail().getNumber()+2) {
							return true;
						} 
					} else if(tableMeld.getTile(index+1).isJoker()) {		//next is joker
						if(tableMeld.getTile(index-1).getColor() == playMeld.getRunColor()		//at front
								&&tableMeld.getTile(index-1).getNumber() == playMeld.getHead().getNumber()-2) {
							return true;
						} 
						if(tableMeld.getTile(index-1).getColor() == playMeld.getRunColor()		//at back
								&& tableMeld.getTile(index-1).getNumber() == playMeld.getTail().getNumber()) {
							return true;
						} 
					} else {	// both not joker
						if(tableMeld.getTile(index+1).getColor() == playMeld.getRunColor()		//at front
								&&tableMeld.getTile(index+1).getNumber() == playMeld.getHead().getNumber()) {
							return true;
						} 
						if(tableMeld.getTile(index-1).getColor() == playMeld.getRunColor()		//at back
								&& tableMeld.getTile(index-1).getNumber() == playMeld.getTail().getNumber()) {
							return true;
						} 
					}
				}
			}
		}
		return false;
	}

	public String AI1Command(Hand myHand, ArrayList<Meld> t) {
		hand = myHand;
		meldList = t;
		String returnString = "";
		run = new ArrayList<Tile>();
		group = new ArrayList<Tile>();
		
		run = findRun();
		if(run != null)	{
			myHand.sort();
			returnString = "Play";
			for(int i=0; i<run.size();i++) {
				returnString += " " + myHand.handIndexOf(run.get(i)); 
			}
			return returnString;
		} else {
			group  = findSet();
			myHand.sort();
			if(group != null) {
				myHand.sort();
				returnString = "Play";
				for(int i=0; i<group.size();i++) {
					returnString += " " + myHand.handIndexOf(group.get(i)); 
				}
				return returnString;
			}
			return "END";
		}	
	}
	
	/*public String AI4CommandInitial(Hand myHand, ArrayList<Meld> t, int countInitial) {
		hand = myHand;
		table = t;
		String returnString = "";
		if(checkInitialSum() >= 30-countInitial) {
			run = findRun();
			if(run != null) {
				countInitial += checkSum(run);
				myHand.sort();
				returnString = "Play";
				for(int i=0; i<run.size();i++) {
					returnString += " " + myHand.handIndexOf(run.get(i)); 
				}
				return returnString;
			}
			else {
				group = findSet();
				myHand.sort();
				if(group != null) {
					countInitial += checkSum(group);
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
	} */
	
	public String AI4Command(Hand myHand, ArrayList<Meld> t) {
		hand = myHand;
		meldList = t;
		run = new ArrayList<Tile>();
		group = new ArrayList<Tile>();
		String returnString = "";
		if(canPlayAll() && !InRestProg) {	//if can play all, request use of meldList
//System.out.println("can play all");
			run = findRun();
			if(run != null) {
				myHand.sort();
				returnString = "Play";
				for(int i=0; i<run.size();i++) {
					returnString += " " + myHand.handIndexOf(run.get(i));
				}
				return returnString;
			} 
			group = findSet();
			myHand.sort();
			if(group != null) {
				myHand.sort();
				returnString = "Play";
				for(int i=0; i<group.size();i++) {
						returnString += " " + myHand.handIndexOf(group.get(i));
				}
				return returnString;
			}	
			meldOnTable = findMeldsOnTable();
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
					for(Meld m : meldList) {
						moveRunToTable = findRunMove(m);
							if(moveRunToTable != null) {
								moveRun2Table = true;
								tempMeld = m;
								tempMeldList = new ArrayList<Meld>(meldList);
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
					//	System.out.println("move +" +  meldList.indexOf(tempMeld));
					returnString = "Move";
					returnString += " " + tempMeldList.indexOf(tempMeld);
					if(moveRunIndex == 0)
						returnString += " head to ";
					else
						returnString += " tail to ";
					returnString +=  meldList.size()-1;
					if(tempMeld.getTile(moveRunIndex).isJoker())
						returnString += " tail";
					//System.out.println(returnString);
					return returnString;
			}	
			
			if(!moveSet2Table) {		//if not in move set progress
				if(!moveRun2Table) {
					for(Meld m : meldList) {
						moveGroupToTable = findSetMove(m);
						myHand.sort();
						if(moveGroupToTable != null) {
							moveSet2Table = true;
							tempMeld = m;
							tempMeldList = new ArrayList<Meld>(meldList);
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
					//	System.out.println("move +" +  meldList.indexOf(tempMeld));
					returnString = "Move";
					returnString += " " + tempMeldList.indexOf(tempMeld);
					if(moveSetIndex == 0)
						returnString += " head to ";
					else
						returnString += " tail to ";
					returnString +=  meldList.size()-1;
					if(tempMeld.getTile(moveSetIndex).isJoker())
						returnString += " tail";
					return returnString;
			}	

			if(!cutRun2Table) {	//if not in cut prog
				for(Meld m : meldList) {
					cutRunToTable = findRunCut(m);
					myHand.sort();
					if(cutRunToTable != null) {	// can cut
						cutRun2Table = true;
						tempMeld = m;
						for(Entry<ArrayList<Tile>, Integer> Entry : cutRunToTable.entrySet()) {
							runCut = Entry.getKey();
							cutRunIndex = Entry.getValue();	// 
							myHand.sort();
							returnString = "Cut " + meldList.indexOf(m) + " at " +cutRunIndex ;
							return returnString;
						}
					}
				}
			} else {	// in cut prog
				meldOnTable = findMeldsOnTable();
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
			for(Meld m : meldList) {
				//System.out.println("Check replace");
				replace = findReplace(m);
				if(replace != null) {
					for(Entry<Tile, Integer>Entry : replace.entrySet()) {
						Tile tile = Entry.getKey();
						int index = Entry.getValue();
						myHand.sort();
						//Replace int(handIndex) to int(tableIndex) int(meldIndex)
						returnString = "Replace " + myHand.indexOf(tile) + " to ";
						returnString += meldList.indexOf(m) + " " +  index;
						return returnString;
					}
				}
			}
			return "END";
		}		
		//if stock left is 1, stock - table total tile + all player hand tiles number 
		
		else {																// if cannot play all, play meld base on meldList
//System.out.println("can not play all");
			InRestProg = true;
			run = findRun();
			if(run != null && hasMapfromTable(run, meldList)) {
				myHand.sort();
				returnString = "Play";
				for(int i=0; i<run.size();i++) {
					returnString += " " + myHand.handIndexOf(run.get(i));
				}
				return returnString;
			} 
			group = findSet();
			myHand.sort();
			if(group != null && hasMapfromTable(group, meldList)) {
				myHand.sort();
				returnString = "Play";
				for(int i=0; i<group.size();i++) {
						returnString += " " + myHand.handIndexOf(group.get(i));
				}
				return returnString;
			}	
			meldOnTable = findMeldsOnTable();
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
					for(Meld m : meldList) {
						moveRunToTable = findRunMove(m);
							if(moveRunToTable != null) {
								moveRun2Table = true;
								tempMeld = m;
								tempMeldList = new ArrayList<Meld>(meldList);
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
					//	System.out.println("move +" +  tempMeldList.indexOf(tempMeld));
					returnString = "Move";
					returnString += " " + tempMeldList.indexOf(tempMeld);
					if(moveRunIndex == 0)
						returnString += " head to ";
					else
						returnString += " tail to ";
					returnString +=  meldList.size()-1;
					if(tempMeld.getTile(moveRunIndex).isJoker())
						returnString += " tail";
					//System.out.println(returnString);
					return returnString;
			}	
			
			if(!moveSet2Table) {		//if not in move set progress
				if(!moveRun2Table) {
					for(Meld m : meldList) {
						moveGroupToTable = findSetMove(m);
						myHand.sort();
						if(moveGroupToTable != null) {
							moveSet2Table = true;
							tempMeld = m;
							tempMeldList = new ArrayList<Meld>(meldList);
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
					//System.out.println("move +" +  tempMeldList.indexOf(tempMeld));
					returnString = "Move";
					returnString += " " + tempMeldList.indexOf(tempMeld);
					if(moveSetIndex == 0)
						returnString += " head to ";
					else
						returnString += " tail to ";
					returnString +=  meldList.size()-1;
					if(tempMeld.getTile(moveSetIndex).isJoker())
						returnString += " tail";
					return returnString;
			}	
			
			if(!cutRun2Table) {	//if not in cut prog
				for(Meld m : meldList) {
					cutRunToTable = findRunCut(m);
					myHand.sort();
					if(cutRunToTable != null) {	// can cut
						cutRun2Table = true;
						tempMeld = m;
						for(Entry<ArrayList<Tile>, Integer> Entry : cutRunToTable.entrySet()) {
							runCut = Entry.getKey();
							cutRunIndex = Entry.getValue();	// 
							myHand.sort();
							returnString = "Cut " + meldList.indexOf(m) + " at " +cutRunIndex ;
							return returnString;
						}
					}
				}
			} else {	// in cut prog
				meldOnTable = findMeldsOnTable();
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
			for(Meld m : meldList) {
				//System.out.println("Check replace");
				replace = findReplace(m);
				if(replace != null) {
					for(Entry<Tile, Integer>Entry : replace.entrySet()) {
						Tile tile = Entry.getKey();
						int index = Entry.getValue();
						myHand.sort();
						//Replace int(handIndex) to int(tableIndex) int(meldIndex)
						returnString = "Replace " + myHand.indexOf(tile) + " to ";
						returnString += meldList.indexOf(m) + " " +  index;
						return returnString;
					}
				}
			}
			InRestProg = false;
			return "END";
		}
	}
}
