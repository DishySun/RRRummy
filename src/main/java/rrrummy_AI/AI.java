package rrrummy_AI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import rrrummy.*;

public class AI extends Player{
	private AIstrategy aIstrategy;
	private ArrayList<Tile> tile4Run;
	private ArrayList<Tile> tile4Group;
	private ArrayList<ArrayList<Tile>> tile2Play;
	
	public AI(String n) {
		super(n);
		// TODO Auto-generated constructor stub
	}

	public void setSTY(AIstrategy AISTY) {
		// TODO Auto-generated method stub
		this.aIstrategy = AISTY;
	}

	public AIstrategy getSTY() {
		return aIstrategy;
	}

	public ArrayList<ArrayList<Tile>> findInitRun() {
		// TODO Auto-generated method stub
		tile4Run = new ArrayList<Tile>();
		tile2Play = new ArrayList<ArrayList<Tile>>();
		boolean run = true;		// used to stop loop when reach 30 points
		boolean disconnect = true;	// check if next tile number & color is connecte
		int playIndex = 0;	//point to tile4Run position;
		int handIndex;  //used when looping hand cards
		int jokerNum = 0;	// joker number
		int jokerorg = 0;	// joker number (final)
		int countPoint = 0;	
		boolean hasJoker;
		this.getHands().sort();		//sort
		//check if has joker
		if(this.getHand(this.handSize()-1).getColor() == Tile.Color.JOKER) 
			hasJoker = true;
		else
			hasJoker = false;
		
		if(hasJoker) {	
			for(int i = 0; i<this.handSize();i++) {			//count size of joker;
				if(this.getHand(i).getColor() == Tile.Color.JOKER) 
					jokerNum++;
			}
			jokerorg = jokerNum;
			tile4Run.add(this.getHand(0));
			handIndex = 1;
			while(handIndex < this.handSize()-jokerorg && run) {
				if(tile4Run.get(playIndex).getColor() != Tile.Color.JOKER) { // last tile in tile4Run is not joker
					if(tile4Run.get(playIndex).getColor() == this.getHand(handIndex).getColor() 			//continuously 1 2 3 4 5
							&& this.getHand(handIndex).getNumber()-1 == tile4Run.get(playIndex).getNumber())
					{
						tile4Run.add(this.getHand(handIndex));
						playIndex++;		
						disconnect = false;
						handIndex++;
					} else
						disconnect = true;
				}
				else {	// last tile in tile4Run is joker _ _ J
					if(tile4Run.get(playIndex-1).getColor() == Tile.Color.JOKER  // _ J J 
							&& this.getHand(handIndex).getNumber() - 3 !=  tile4Run.get(playIndex-2).getNumber()) {
						disconnect = true;
					} else if (tile4Run.get(playIndex-1).getColor() == Tile.Color.JOKER // _ J J
							&& this.getHand(handIndex).getNumber() - 3 ==  tile4Run.get(playIndex-2).getNumber()) {
						tile4Run.add(this.getHand(handIndex));
						playIndex++;		
						handIndex++;
						disconnect = false;
					} else if (tile4Run.get(playIndex-1).getColor() != Tile.Color.JOKER // _ _ J
							&& this.getHand(handIndex).getNumber() - 2 !=  tile4Run.get(playIndex-1).getNumber()) {
						disconnect = true;
					} else if (tile4Run.get(playIndex-1).getColor() != Tile.Color.JOKER // _ _ J
							&& this.getHand(handIndex).getNumber() - 2 ==  tile4Run.get(playIndex-1).getNumber()) {
						tile4Run.add(this.getHand(handIndex));
						playIndex++;	
						handIndex++;
						disconnect = false;
					}
				}

				if (disconnect){ // disconnect
					disconnect = false;
					countPoint = checkSum(tile4Run);
					if(countPoint < 30) {
						if(jokerNum > 0) {
							if(tile4Run.size() >= 2 && tile4Run.get(tile4Run.size()-2).getNumber() == 12 
									&& tile4Run.get(tile4Run.size()-1).getColor() == Tile.Color.JOKER 
									|| tile4Run.get(tile4Run.size()-1).getNumber() == 13)
							{	
										tile4Run.add(0, this.getHand(this.handSize()-1)); // add joker
										jokerNum--;
										playIndex++;
								
							} else {
								tile4Run.add(this.getHand(this.handSize()-1)); // add joker
								jokerNum--;
								playIndex++;
							}
						} else {
							for(int i = 0; i<tile4Run.size();i++) {
								if(tile4Run.get(i).getColor() == Tile.Color.JOKER) 
									jokerNum++;
							}
							tile4Run = new ArrayList<Tile>();
							tile4Run.add(this.getHand(handIndex));
							playIndex = 0;
							handIndex++;
						}
					} else {
						tile2Play.add(tile4Run);
						tile4Run = new ArrayList<Tile>();
						tile4Run.add(this.getHand(handIndex));
						handIndex++;
						playIndex = 0;
						run = false;	// reach 30, break loop 
					}
				}
			}
		}
		else {
			//no joker
			tile4Run.add(this.getHand(0));
			for(int i=1; i<this.handSize();i++) {
				if(tile4Run.get(playIndex).getColor() == this.getHand(i).getColor() 
						&& this.getHand(i).getNumber()-1 == tile4Run.get(playIndex).getNumber()) {
					tile4Run.add(this.getHand(i));
					playIndex++;
					disconnect = false;
					if(this.handSize()-1 == i)
						disconnect = true;
				} else
					disconnect = true;
				if(disconnect) {
					countPoint = checkSum(tile4Run);
					if(countPoint >= 30) {
						tile2Play.add(tile4Run);
						tile4Run = new ArrayList<Tile>();
						tile4Run.add(this.getHand(i));
						playIndex = 0;
						run = false;		// reach 30, break loop 
					} else {	
						tile4Run = new ArrayList<Tile>();
						tile4Run.add(this.getHand(i));
						playIndex = 0;
					}
				}
			}
		}
		
		return tile2Play;
	}

	public ArrayList<ArrayList<Tile>> findInitGroup() {
		// TODO Auto-generated method stub
		tile4Group = new ArrayList<Tile>();
		tile2Play = new ArrayList<ArrayList<Tile>>();
		boolean run = true;		// used to stop loop when reach 30 points
		boolean disconnect = true;	// check if next tile number & color is connecte
		int playIndex = 0;	//point to tile4Run position;
		int handIndex;  //used when looping hand cards
		int jokerNum = 0;	// joker number
		int jokerorg = 0;	// joker number (final)
		int countPoint = 0;	
		boolean hasJoker;
		this.getHands().sortByNum();;		//sort by number, same number will put together, order base on Hand
		//check if has joker
		if(this.getHand(0).getColor() == Tile.Color.JOKER) 		// joker is first one
			hasJoker = true;
		else
			hasJoker = false;
		
		if(hasJoker) {
			for(int i = 0; i<this.handSize();i++) {			//count size of joker;
				if(this.getHand(i).getColor() == Tile.Color.JOKER) 
					jokerNum++;
			}
			jokerorg = jokerNum;
			tile4Group.add(this.getHand(jokerorg));	// J 1 5 5 ....
			handIndex = jokerorg+1;
			
			while(handIndex < this.handSize() && run) {	
				if(!hasSameColor(tile4Group, this.getHand(handIndex)) 			//no same color && same # && tile4Group # < 4
						&& this.getHand(handIndex).getNumber() == tile4Group.get(playIndex).getNumber()
						&& tile4Group.size() < 4)
				{
					tile4Group.add(this.getHand(handIndex));
					playIndex++;		
					disconnect = false;
					handIndex++;
				} else
					disconnect = true;
				
				if (disconnect){ // disconnect
					disconnect = false;
					countPoint = checkSum(tile4Group);
					if(countPoint < 30) {
						if(jokerNum > 0 && tile4Group.size()<4) {
							tile4Group.add( this.getHand(0)); // add joker
							jokerNum--;
							playIndex++;
						} else {
							for(int i = 0; i<tile4Group.size();i++) {
								if(tile4Group.get(i).getColor() == Tile.Color.JOKER) 
									jokerNum++;
							}
							tile4Group = new ArrayList<Tile>();
							tile4Group.add(this.getHand(handIndex));
							playIndex = 0;
							handIndex++;
						}
					} else {
						tile2Play.add(tile4Group);
						tile4Group = new ArrayList<Tile>();
						tile4Group.add(this.getHand(handIndex));
						handIndex++;
						playIndex = 0;
						run = false;	// reach 30, break loop 
					}
				}
			}
			
		} else {	
			//no joker
			tile4Group.add(this.getHand(0));
			for(int i=1; i<this.handSize();i++) {		// R1 B1
				if(!hasSameColor(tile4Group, this.getHand(i))
						&& this.getHand(i).getNumber() == tile4Group.get(playIndex).getNumber()) {
					tile4Group.add(this.getHand(i));
					playIndex++;
					disconnect = false;
					if(i == this.handSize()-1)
						disconnect = true;
				} else
					disconnect = true;
				
				if(disconnect){
					countPoint = checkSum(tile4Group);
					if(countPoint >= 30) {
						tile2Play.add(tile4Group);
						tile4Group = new ArrayList<Tile>();
						tile4Group.add(this.getHand(i));
						playIndex = 0;
						run = false;		// reach 30, break loop 
					} else {	
						tile4Group = new ArrayList<Tile>();
						tile4Group.add(this.getHand(i));
						playIndex = 0;
					}
				}
			}
		}
		
		return tile2Play;
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
	
	public int checkSum2arr(ArrayList<ArrayList<Tile>> arr) {
		int count = 0;
		for(ArrayList<Tile> a : arr) {
			count += checkSum(a);
		}
		return count;
	}
	
	public boolean hasSameColor(ArrayList<Tile> tile, Tile compareTile) {
		for (Tile t : tile) {
			if(t.getColor() == compareTile.getColor()) return true;
		}
		return false;	
	}

	public ArrayList<ArrayList<Tile>> findRun() {
		// TODO Auto-generated method stub
		tile4Run = new ArrayList<Tile>();
		tile2Play = new ArrayList<ArrayList<Tile>>();
		boolean run = true;		// used to stop loop when reach 30 points
		boolean disconnect = true;	// check if next tile number & color is connecte
		int playIndex = 0;	//point to tile4Run position;
		int handIndex;  //used when looping hand cards
		int jokerNum = 0;	// joker number
		int jokerorg = 0;	// joker number (final)
		boolean hasJoker;
		this.getHands().sort();		//sort
		//check if has joker
		if(this.getHand(this.handSize()-1).getColor() == Tile.Color.JOKER) 
			hasJoker = true;
		else
			hasJoker = false;
		
		if(hasJoker) {	
			for(int i = 0; i<this.handSize();i++) {			//count size of joker;
				if(this.getHand(i).getColor() == Tile.Color.JOKER) 
					jokerNum++;
			}
			jokerorg = jokerNum;
			tile4Run.add(this.getHand(0));
			handIndex = 1;
			while(handIndex < this.handSize()-jokerorg) {
				
				if(tile4Run.get(playIndex).getColor() != Tile.Color.JOKER) { // last tile in tile4Run is not joker
					if(tile4Run.get(playIndex).getColor() == this.getHand(handIndex).getColor() 			//continuously 1 2 3 4 5
							&& this.getHand(handIndex).getNumber()-1 == tile4Run.get(playIndex).getNumber())
					{
						tile4Run.add(this.getHand(handIndex));
						playIndex++;		
						disconnect = false;
						handIndex++;
					} else
						disconnect = true;
				}
				else {	// last tile in tile4Run is joker _ _ J
					if(tile4Run.get(playIndex-1).getColor() == Tile.Color.JOKER  // _ J J 
							&& this.getHand(handIndex).getNumber() - 3 !=  tile4Run.get(playIndex-2).getNumber()) {
						disconnect = true;
					} else if (tile4Run.get(playIndex-1).getColor() == Tile.Color.JOKER // _ J J
							&& this.getHand(handIndex).getNumber() - 3 ==  tile4Run.get(playIndex-2).getNumber()) {
						tile4Run.add(this.getHand(handIndex));
						playIndex++;		
						handIndex++;
						disconnect = false;
					} else if (tile4Run.get(playIndex-1).getColor() != Tile.Color.JOKER // _ _ J
							&& this.getHand(handIndex).getNumber() - 2 !=  tile4Run.get(playIndex-1).getNumber()) {
						disconnect = true;
					} else if (tile4Run.get(playIndex-1).getColor() != Tile.Color.JOKER // _ _ J
							&& this.getHand(handIndex).getNumber() - 2 ==  tile4Run.get(playIndex-1).getNumber()) {
						tile4Run.add(this.getHand(handIndex));
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
										tile4Run.add(0, this.getHand(this.handSize()-1)); // add joker
										jokerNum--;
										playIndex++;
								
							} else {
								tile4Run.add(this.getHand(this.handSize()-1)); // add joker
								
								jokerNum--;
								playIndex++;
							}
						} else {
							for(int i = 0; i<tile4Run.size();i++) {
								if(tile4Run.get(i).getColor() == Tile.Color.JOKER) 
									jokerNum++;
							}
							tile4Run = new ArrayList<Tile>();
							tile4Run.add(this.getHand(handIndex));
							playIndex = 0;
							handIndex++;
						}
					} else {
						tile2Play.add(tile4Run);
						tile4Run = new ArrayList<Tile>();
						tile4Run.add(this.getHand(handIndex));
						playIndex = 0;
						handIndex++;
					}
				}
			}
		}
		else {
			//no joker
			tile4Run.add(this.getHand(0));
			for(int i=1; i<this.handSize();i++) {
				if(tile4Run.get(playIndex).getColor() == this.getHand(i).getColor() 
						&& this.getHand(i).getNumber()-1 == tile4Run.get(playIndex).getNumber()) {
					tile4Run.add(this.getHand(i));
					playIndex++;
					disconnect = false;
					if(i == this.handSize()-1)
						disconnect = true;
				} else
					disconnect = true;
				
				if(disconnect) {
					if(tile4Run.size() >= 3) {
						tile2Play.add(tile4Run);
						tile4Run = new ArrayList<Tile>();
						tile4Run.add(this.getHand(i));
						playIndex = 0;
					} else {	
						tile4Run = new ArrayList<Tile>();
						tile4Run.add(this.getHand(i));
						playIndex = 0;
					}
				}
			}
		}
		return tile2Play;
	}

	public ArrayList<ArrayList<Tile>> findGroup() {
		// TODO Auto-generated method stub
		if(this.handSize() == 0) return null;
		
		tile4Group = new ArrayList<Tile>();
		tile2Play = new ArrayList<ArrayList<Tile>>();
		boolean run = true;		// used to stop loop when reach 30 points
		boolean disconnect = true;	// check if next tile number & color is connecte
		int playIndex = 0;	//point to tile4Run position;
		int handIndex;  //used when looping hand cards
		int jokerNum = 0;	// joker number
		int jokerorg = 0;	// joker number (final)
		boolean hasJoker;
		this.getHands().sortByNum();;		//sort by number, same number will put together, order base on Hand
		//check if has joker
		if(this.getHand(0).getColor() == Tile.Color.JOKER) 		// joker is first one
			hasJoker = true;
		else
			hasJoker = false;
		
		if(hasJoker) {
			for(int i = 0; i<this.handSize();i++) {			//count size of joker;
				if(this.getHand(i).getColor() == Tile.Color.JOKER) 
					jokerNum++;
			}
			jokerorg = jokerNum;
			tile4Group.add(this.getHand(jokerorg));	// J 1 5 5 ....
			handIndex = jokerorg + 1;
			while(handIndex < this.handSize()) {	
				if(!hasSameColor(tile4Group, this.getHand(handIndex)) 			//no same color && same # && tile4Group # < 4
						&& this.getHand(handIndex).getNumber() == tile4Group.get(playIndex).getNumber()
						&& tile4Group.size() < 4)
				{
					tile4Group.add(this.getHand(handIndex));
					playIndex++;		
					disconnect = false;
					handIndex++;
				} else
					disconnect = true;
				
				if (disconnect){ // disconnect
					disconnect = false;
					if(tile4Group.size() <= 3) {
						if(jokerNum > 0 && tile4Group.size() < 3) {
							tile4Group.add( this.getHand(0)); // add joker
							jokerNum--;
							playIndex++;
						} else if(jokerNum == 0 && tile4Group.size() < 3){
							for(int i = 0; i<tile4Group.size();i++) {
								if(tile4Group.get(i).getColor() == Tile.Color.JOKER) 
									jokerNum++;
							}
							tile4Group = new ArrayList<Tile>();
							tile4Group.add(this.getHand(handIndex));
							playIndex = 0;
							handIndex++;
						} else {
							tile2Play.add(tile4Group);
							tile4Group = new ArrayList<Tile>();
							tile4Group.add(this.getHand(handIndex));
							handIndex++;
							playIndex = 0;
						}
					} else {
						tile2Play.add(tile4Group);
						tile4Group = new ArrayList<Tile>();
						tile4Group.add(this.getHand(handIndex));
						handIndex++;
						playIndex = 0;
					}
				}
			}
			
		} else {	
			//no joker
			tile4Group.add(this.getHand(0));
			for(int i=1; i<this.handSize();i++) {		// R1 B1
				if(!hasSameColor(tile4Group, this.getHand(i))
						&& this.getHand(i).getNumber() == tile4Group.get(playIndex).getNumber()) {
					tile4Group.add(this.getHand(i));
					playIndex++;
					disconnect = false;
					if(i == this.handSize()-1)
						disconnect = true;
				} else 
					disconnect = true;
				
				if(disconnect) {
					if(tile4Group.size() >= 3) {
						tile2Play.add(tile4Group);
						tile4Group = new ArrayList<Tile>();
						tile4Group.add(this.getHand(i));
						playIndex = 0;
					} else {	
						tile4Group = new ArrayList<Tile>();
						tile4Group.add(this.getHand(i));
						playIndex = 0;
					}
				}
			}
		}
		return tile2Play;
	}

	public ArrayList<ArrayList<Tile>> findComb30() {
		// TODO Auto-generated method stub
		//find all runs, if < 30, + find all sets
		int runSum = 0;
		int groupSum = 0;
		ArrayList<ArrayList<Tile>> tempArray = new ArrayList<ArrayList<Tile>>();
		ArrayList<ArrayList<Tile>> tile4RunList = new ArrayList<ArrayList<Tile>>();
		ArrayList<ArrayList<Tile>> tile4GroupList = new ArrayList<ArrayList<Tile>>();
		tile4RunList = this.findRun();
		tile4GroupList = this.findGroup();
		
		runSum = this.checkSum2arr(tile4RunList);
		groupSum = this.checkSum2arr(tile4GroupList);
		
		//System.out.println(tile4RunList);
		//System.out.println(tile4GroupList);
		
		if(runSum >= 30) {
			return tile4RunList;
		}  else if(groupSum  >= 30) {
			return tile4GroupList;
		} else {
			if(runSum > groupSum) {
				tile4GroupList.clear();
				for(ArrayList<Tile> arr : tile4RunList) {
					this.getHands().removeAll(arr);
				}
				tile4GroupList = this.findGroup();
				if(runSum + this.checkSum2arr(tile4GroupList) >= 30) {
					for(ArrayList<Tile> arr : tile4RunList) {
						this.getHands().addAll(arr);
					}
					tempArray.addAll(tile4RunList);
					tempArray.addAll(tile4GroupList);
					return tempArray;
				} 
			} else {
				tile4RunList.clear();
				for(ArrayList<Tile> arr : tile4GroupList) {
					this.getHands().removeAll(arr);
				}
				tile4RunList = this.findRun();
				if(groupSum + this.checkSum2arr(tile4RunList) >= 30) {
					for(ArrayList<Tile> arr : tile4GroupList) {
						this.getHands().addAll(arr);
					}
					tempArray.addAll(tile4RunList);
					tempArray.addAll(tile4GroupList);
					return tempArray;
				} 
			}
		}
		return null;
	}

	public ArrayList<ArrayList<Tile>> findCombAll() {
		// TODO Auto-generated method stub
		//find all runs, and sets
		ArrayList<ArrayList<Tile>> tempArray = new ArrayList<ArrayList<Tile>>();
		ArrayList<ArrayList<Tile>> tile4RunList = new ArrayList<ArrayList<Tile>>();
		ArrayList<ArrayList<Tile>> tile4GroupList = new ArrayList<ArrayList<Tile>>();
		tile4RunList = this.findRun();
		tile4GroupList = this.findGroup();
		if(tile4RunList.size() != 0 || tile4Group.size() !=0) {
			if(tile4RunList.size() > tile4GroupList.size()) {
				tile4GroupList.clear();
				tempArray.addAll(tile4RunList);
				for(ArrayList<Tile> arr : tile4RunList) {
					this.getHands().removeAll(arr);
				}
				tile4GroupList = this.findGroup();
				tempArray.addAll(tile4GroupList);
				for(ArrayList<Tile> arr : tile4RunList) {
					this.getHands().addAll(arr);
				}	
				return tempArray;
			} else {
				tile4RunList.clear();
				tempArray.addAll(tile4GroupList);
				for(ArrayList<Tile> arr : tile4GroupList) {
					this.getHands().removeAll(arr);
				}
				tile4RunList = this.findRun();
				tempArray.addAll(tile4RunList);
				for(ArrayList<Tile> arr : tile4GroupList) {
					this.getHands().addAll(arr);	
				} 
				return tempArray;
			}
		} else
			return null;
	}

	public HashMap<Tile,Integer> findMeldsOnTable(Table table) throws AbleToAddBothSideException {
		// TODO Auto-generated method stub
		// find if any hand tile can form a mile from table
		HashMap<Tile, Integer> mdlesMap = new HashMap<Tile,Integer>();
		Table tableClone = new Table();
		tableClone = table;
		this.getHands().sort();
		//System.out.println(this.getHands());
		while(true) {	// find all possible combinatino without joker
			int size = mdlesMap.size();
			for(int i=0; i<tableClone.size();i++) {
				for(int j=0; j<this.getHands().size();j++) {	
					if(this.getHand(j).getColor() == Tile.Color.JOKER) continue;
					else if(tableClone.getMeld(i).add(this.getHand(j))) {	//if can add and not used
						mdlesMap.put(this.getHand(j),i);
					}
				}
			}
			if (mdlesMap.size() == size)
				break;
		}
		
		while(true) {
			int size = mdlesMap.size();
			for(int i=0; i<tableClone.size();i++) {
				for(int j=0; j<this.getHands().size();j++) {	
					if(this.getHand(j).getColor() == Tile.Color.JOKER) {
						if(tableClone.getMeld(i).addHead(this.getHand(j))) {
							mdlesMap.put(this.getHand(j),i);
						}
					}	
				}
			}
			if (mdlesMap.size() == size)
				break;
		}
		
		if(mdlesMap.size() == 0)
			return null;
		return mdlesMap;
	}

	public ArrayList<Meld> arrayList2MeldList(ArrayList<ArrayList<Tile>> tileArray) throws AbleToAddBothSideException {
		// TODO Auto-generated method stub
		ArrayList<Meld> meldList = new ArrayList<Meld>();
		Meld tempMeld;
		for(ArrayList<Tile> arr : tileArray) {
			tempMeld = new Meld();
			for(Tile t : arr) {
				tempMeld.addTail(t);
			}
			meldList.add(tempMeld);
		}
		return meldList;
	}

	public void playMeld(ArrayList<Meld> meldList, Table table) {
		// TODO Auto-generated method stub
		int handsize = this.handSize();
		for(Meld meld : meldList) {
			for(int m = 0; m < meld.size(); m++) {
				for(int h=0;h<this.getHands().size();h++) {
					if(this.getHand(h) == meld.get(m)) {
						this.getHands().remove(h);
						break;
					}
				}
			}
			table.add(meld);
		}
	}

	
	
}
