package players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import rrrummy.*;

public class AILogic {
	Hand hand;
	ArrayList<Meld> meldList; 
	public AILogic(Hand h, ArrayList<Meld> m) {
		hand = h;
		meldList = m;
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
		for(Meld m : meldList) {
			Meld tempMeld = new Meld(m);
			tempMeldList.add(tempMeld);
		}
		meldList = tempMeldList;
		
		if(temphand.size() == 0 || tempMeldList.size()==0)
			return null;
		for(int j=0; j<temphand.size();j++) {	
			for(int i=0; i<tempMeldList.size();i++) {
					if(tempMeldList.get(i).getMap().containsKey(temphand.getTile(j).toString())) {	//if can add and not used
						mdlesMap.put(temphand.getTile(j),i);
						return mdlesMap;
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
						//re sort tempmeld
						tempmeld = new Meld(meld);
						
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
											runCanMove.put(tile4Run, 0);
											return runCanMove;
									}
								tempmeld.addHead(removeHead);
								}
							}else {	//cannot move, add head
								tempmeld.addHead(removeHead);
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
										}
								} else {	// cannot move, added back
									tempmeld.addTail(removeTail);
								}
								//tail cannot move, check head
								//re sort tempmeld
								tempmeld = new Meld(meld);
								
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
						}
					}
					for(int i = run.size()-1; i>=0;i--) {
							temphand.remove(temphand.indexOf(run.get(i)));
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
						}
					}
					for(int i = 0; i<group.size();i++) {
						temphand.sort();
						temphand.remove(temphand.indexOf(group.get(i)));
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
		
		/*while(true) {
			for(Meld meld : meldList) {
				moveRun2Table = temphand.findRunMove(meld);
				if(moveRun2Table != null) {
					for(Entry<ArrayList<Tile>, Integer> entry : moveRun2Table.entrySet()) {
						ArrayList<Tile> arr = entry.getKey();
					}
				}
			}
			break;
		}*/
		meldList = orgMeldList;
		hand = orghand;
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
							for(int t=0; t<tempmeld.size()-1;t++) {
								if(tile4Run.get(0).toString().equals(tempmeld.getTile(t).toString())) {
									if(t != 0 && t + 1 != tempmeld.size()-1) {	// can cut 0 | a b 4
										runCanSet.put(tile4Run, t-1);
										return runCanSet;
									} else
										break;
								}  
							}
						}
					} else if (tile4Run.size() == 1) {
						if(tempmeld.size() >= 5) {	// 1 2 a 4 5
							for(int t=0; t<tempmeld.size()-1;t++) {
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
				for(int t=0; t<tempmeld.size()-1;t++) {
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
	
	
}
