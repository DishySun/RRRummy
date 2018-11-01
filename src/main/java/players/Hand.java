package players;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import rrrummy.AbleToAddBothSideException;
import rrrummy.Meld;
import rrrummy.Tile;

public class Hand {
	private ArrayList<Tile> hand;
	
	public Hand(ArrayList<Tile> initHand) {
		hand = initHand;
		this.sort();
	}
	
	public Tile getTile(int i){return hand.get(i);}
	public void add(Tile t) {
		hand.add(t);
		this.sort();
	}
	public Tile remove(int i) {return hand.remove(i);}
	public int size() {return hand.size();}
	public Tile remove() {return hand.remove(size()-1);}
	public String toString() {
		String aString = "";
		for (int i = 0; i < hand.size(); i++) {
			aString+= hand.get(i).toString();
			if (i != hand.size()-1) aString+=", ";
		}
		return aString;
	}
	public void sort() {

		Collections.sort(hand, new Comparator<Tile>(){

			@Override
			public int compare(Tile t1, Tile t2) {
					if (t1.isGreaterThan(t2)) {
					return 1;
				}else return -1;
			}
		
		});
	}
	public void sortByNum() {
		// TODO Auto-generated method stub
		Collections.sort(hand, new Comparator<Tile>(){

			@Override
			public int compare(Tile t1, Tile t2) {
				// TODO Auto-generated method stub
				if (t1.isEqualThan(t2)) {
					return 1;
				}else return -1;
			}
		});
	}
	
	public int checkSum(ArrayList<Tile> tileArray) {
		// TODO Auto-generated method stub
		if(tileArray == null) return 0;
		int count = 0;
		int tilePosition = 0;
		for(int i=0; i<tileArray.size();i++) {
			if(tileArray.get(i).getColor() != Tile.Color.JOKER){
				tilePosition = i;
				break;
			}
		}
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
		return count;
	}
	
	public boolean hasSameColor(ArrayList<Tile> tile, Tile compareTile) {
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
		boolean disconnect = true;	// check if next tile number & color is connecte
		boolean run = true;		// used to stop loop when reach 30 points
		int playIndex = 0;	//point to tile4Run position;
		int handIndex;  //used when looping hand cards
		int jokerNum = 0;	// joker number
		int jokerorg = 0;	// joker number (final)
		boolean hasJoker = false;
		sort();		//sort
		//check if has joker
		for(int i = 0; i<size();i++) {			//count size of joker;
			if(hand.get(i).getColor() == Tile.Color.JOKER) {
				jokerNum++;
				jokerorg++;
			}
				
			hasJoker = true;
		}
		
		
		tile4Run.add(hand.get(0));
		for(int i=1; i<size()-jokerNum;i++) {
			if(tile4Run.get(playIndex).getColor() == hand.get(i).getColor() 
					&& hand.get(i).getNumber()-1 == tile4Run.get(playIndex).getNumber()) {
				tile4Run.add(hand.get(i));
				playIndex++;
			} else {
				if(tile4Run.size() < 3) {
					tile4Run = new ArrayList<Tile>();
					tile4Run.add(hand.get(i));
					playIndex = 0;
				}
			}
		}
		if(tile4Run.size() >= 3)
			return tile4Run;
		//if not return above, check if has joker, if has,run, else return null
		tile4Run = new ArrayList<Tile>();
		playIndex = 0;
		int count = jokerorg;
		if(jokerorg > 1) jokerNum--;
		while(count != 0) {
			if(hasJoker) {
				if(hand.size() < 2)
					return null;
				tile4Run.add(hand.get(0));
				handIndex = 1;
				while(handIndex < size()-jokerorg && run) {
					if(tile4Run.get(playIndex).getColor() != Tile.Color.JOKER) { // last tile in tile4Run is not joker
						if(tile4Run.get(playIndex).getColor() == hand.get(handIndex).getColor() 			//continuously 1 2 3 4 5
								&& hand.get(handIndex).getNumber()-1 == tile4Run.get(playIndex).getNumber())
						{
							tile4Run.add(hand.get(handIndex));
							playIndex++;		
							disconnect = false;
							handIndex++;
							if(handIndex == size()-jokerorg)
								disconnect = true;
						} else
							disconnect = true;
					}
					else {	// last tile in tile4Run is joker _ _ J
						if(tile4Run.get(playIndex-1).getColor() == Tile.Color.JOKER  // _ J J 
								&& hand.get(handIndex).getNumber() - 3 !=  tile4Run.get(playIndex-2).getNumber()) {
							disconnect = true;
						} else if (tile4Run.get(playIndex-1).getColor() == Tile.Color.JOKER // _ J J
								&& hand.get(handIndex).getNumber() - 3 ==  tile4Run.get(playIndex-2).getNumber()) {
							tile4Run.add(hand.get(handIndex));
							playIndex++;		
							handIndex++;
							disconnect = false;
						} else if (tile4Run.get(playIndex-1).getColor() != Tile.Color.JOKER // _ _ J
								&& hand.get(handIndex).getNumber() - 2 !=  tile4Run.get(playIndex-1).getNumber()) {
							disconnect = true;
						} else if (tile4Run.get(playIndex-1).getColor() != Tile.Color.JOKER // _ _ J
								&& hand.get(handIndex).getNumber() - 2 ==  tile4Run.get(playIndex-1).getNumber()) {
							tile4Run.add(hand.get(handIndex));
							playIndex++;	
							handIndex++;
							disconnect = false;
						}
					}

					if (disconnect){ // disconnect
						disconnect = false;
						if(tile4Run.size() < 3) {
							if(jokerNum > 0) {
								if(tile4Run.size() >= 2 && tile4Run.get(tile4Run.size()-2).getNumber() == 12 
										&& tile4Run.get(tile4Run.size()-1).getColor() == Tile.Color.JOKER 
										|| tile4Run.get(tile4Run.size()-1).getNumber() == 13)
								{	
											tile4Run.add(0, hand.get(size()-1)); // add joker
											jokerNum--;
											playIndex++;
											if(tile4Run.size() >= 3 && handIndex == size()-jokerorg)
												return tile4Run;
									
								} else {
									tile4Run.add(hand.get(size()-1)); // add joker
									jokerNum--;
									playIndex++;
									if(tile4Run.size() >= 3 && handIndex == size()-jokerorg)
										return tile4Run;
								}
							} else {
								for(int i = 0; i<tile4Run.size();i++) {
									if(tile4Run.get(i).getColor() == Tile.Color.JOKER) 
										jokerNum++;
								}
								tile4Run = new ArrayList<Tile>();
								tile4Run.add(hand.get(handIndex));
								playIndex = 0;
								handIndex++;
							}
						} else {
							return tile4Run;
						}
					}
				}
			}
			
			for(int i = 0; i<tile4Run.size();i++) {
				if(tile4Run.get(i).getColor() == Tile.Color.JOKER) 
					jokerNum++;
			}
			playIndex = 0;
			tile4Run.clear();	
			if(jokerorg > 1) jokerNum++;		//if 2 joker, first only 1 joker first, than 2
			count--;
		}
		//nothing to return
		return null;
	}
	
	public ArrayList<Tile> findGroup() {
		// TODO Auto-generated method stub
		if(hand.size() == 0)
			return null;
		ArrayList<Tile> tile4Set = new ArrayList<Tile>();
		boolean disconnect = true;	// check if next tile number & color is connecte
		boolean run = true;		// used to stop loop when reach 30 points
		int playIndex = 0;	//point to tile4Run position;
		int handIndex;  //used when looping hand cards
		int jokerNum = 0;	// joker number
		int jokerorg = 0;	// joker number (final)
		boolean hasJoker = false;
		sortByNum();		//sort
		for(int i = 0; i<size();i++) {			//count size of joker;
			if(hand.get(i).getColor() == Tile.Color.JOKER) {
				jokerNum++;
				jokerorg++;
			}
			hasJoker = true;
		}
		
		tile4Set.add(hand.get(0));
		for(int i=1; i<size()-jokerNum;i++) {		// R1 B1
			if(!hasSameColor(tile4Set, hand.get(i))
					&& hand.get(i).getNumber() == tile4Set.get(playIndex).getNumber()) {
				tile4Set.add(hand.get(i));
				playIndex++;
			} else {
				if(tile4Set.size() < 3) {
					tile4Set.clear();	
					tile4Set.add(hand.get(i));
					playIndex = 0;
				}
			}
		}
		if(tile4Set.size() >= 3)
			return tile4Set;
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
				tile4Set.add(hand.get(jokerorg));	// J 1 5 5 ....
				handIndex = jokerorg;
				while(handIndex < size()) {	
					if(!hasSameColor(tile4Set, hand.get(handIndex)) 			//no same color && same # && tile4Set # < 4
							&& hand.get(handIndex).getNumber() == tile4Set.get(playIndex).getNumber()
							&& tile4Set.size() < 4)
					{
						tile4Set.add(hand.get(handIndex));
						playIndex++;		
						disconnect = false;
						handIndex++;
						if(handIndex == size())
							disconnect = true;
					} else
						disconnect = true;
					
					if (disconnect){ // disconnect
						disconnect = false;
						if(tile4Set.size() <= 3) {
							if(jokerNum > 0) {
								tile4Set.add( hand.get(0)); // add joker
								jokerNum--;
								playIndex++;
								if(tile4Set.size() >= 3 && handIndex == size()) 
									return tile4Set;
							} else if(jokerNum == 0 && tile4Set.size() < 3){
								for(int i = 0; i<tile4Set.size();i++) {
									if(tile4Set.get(i).getColor() == Tile.Color.JOKER) 
										jokerNum++;
								}
								tile4Set.clear();	
								tile4Set.add(hand.get(handIndex));
								playIndex = 0;
								handIndex++;
							} else {
								return tile4Set;
							}
						} else {
							return tile4Set;
						}
					}
				}
			}
			for(int i = 0; i<tile4Set.size();i++) {
				if(tile4Set.get(i).getColor() == Tile.Color.JOKER) 
					jokerNum++;
			}
			playIndex = 0;
			tile4Set.clear();	
			if(jokerorg > 1) jokerNum++;		//if 2 joker, first only 1 joker first, than 2
			count--;
		}
		return null;
	}
	
	public int checkInitialSum() {
		//check from run to group sum num, return total sum
		ArrayList<Tile> run = new ArrayList<Tile>();
		ArrayList<Tile> group = new ArrayList<Tile>();
		ArrayList<Tile>tempHandArray = new  ArrayList<Tile>(hand);
		Hand temphand = new Hand(tempHandArray);
		int count = 0;
		while(true) {
			run = temphand.findRun();
			if(run != null) {
				for(Tile tile : run) {
					temphand.remove(tempHandArray.indexOf(tile));
				}
				count += checkSum(run);
			}	else
					break;
		}
		if(temphand.size() ==0)
			return count;

		if(count < 30) {
			while(true) {
				//System.out.println(temphand.size());
				group = temphand.findGroup();
				if(group != null) {
					for(Tile tile : group) {
						temphand.remove(tempHandArray.indexOf(tile));
					}
					count += checkSum(group);
				}	else
						break;
			}
		}
		return count;
	}
	
	public HashMap<Tile,Integer> findMeldsOnTable(ArrayList<Meld> meldList) {
		if(hand.size() == 0)
			return null;
		HashMap<Tile, Integer> mdlesMap = new HashMap<Tile,Integer>();
		ArrayList<Tile>tempHandArray = new  ArrayList<Tile>(hand);
		Hand temphand = new Hand(tempHandArray);
		ArrayList<Meld> tempMeldList = new ArrayList<Meld>(meldList);
		
		
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
	
	public void play2Table(HashMap<Tile, Integer> tileMap, ArrayList<Meld> meldList) {
		// TODO Auto-generated method stub
		sort();
		for(Entry<Tile, Integer> entry : tileMap.entrySet()) {
			Tile tile = entry.getKey();
			int index = entry.getValue();
			
			for(int h=0;h<hand.size();h++) {
				if(hand.get(h) == tile) {
					hand.remove(h);
					try {
						meldList.get(index).add(tile);	
						break;
					} catch (AbleToAddBothSideException e) {
						// TODO Auto-generated catch block
						meldList.get(index).addTail(tile);
						break;
						//e.printStackTrace();
					}	
				}
			}
		}
	}
	
	public boolean canPlayAll(ArrayList<Meld> meldList) {
		HashMap<Tile, Integer> mdlesMap = new HashMap<Tile,Integer>();
		ArrayList<Tile>tempHandArray = new  ArrayList<Tile>(hand);
		Hand temphand = new Hand(tempHandArray);
		//ArrayList<Meld> tempMeldList = (ArrayList<Meld>) meldList.clone();
		ArrayList<Tile> run = new ArrayList<Tile>();
		ArrayList<Tile> group = new ArrayList<Tile>();
		HashMap<Tile, Integer> meld2Table = new HashMap<Tile,Integer>();
		
		int count = 0;
		
		while(true) {
				run = temphand.findRun();
				if(run != null) {
					//System.out.println(run);
					for(Tile tile : run) {
						temphand.remove(tempHandArray.indexOf(tile));
						count++;
					}
				}	else
						break;
		}
	
		while(true) {
			//System.out.println(temphand);
				group = temphand.findGroup();
				if(group != null) {
					//System.out.println(group);
					for(Tile tile : group) {
						temphand.remove(tempHandArray.indexOf(tile));
						count++;
					}
				}else
						break;
		}
		//System.out.println(hand);
		while(true) {
			meld2Table = temphand.findMeldsOnTable(meldList);
			if(meld2Table != null) {
				//temphand.play2Table(mdlesMap, meldList);
				for(Entry<Tile, Integer> entry : meld2Table.entrySet()) {
					Tile tile = entry.getKey();
					int index = entry.getValue();
					temphand.remove(tempHandArray.indexOf(tile));
				}
				count++;
			}else
					break;
		}
		//System.out.println());
		///System.out.println(size());
		//System.out.println(count);
		return count == size();
	}
	
}