package rrrummy_AI;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;

import rrrummy.InvalidTileException;
import rrrummy.Player;
import rrrummy.Tile;

public class AI extends Player{
	private AIstrategy aIstrategy;
	private ArrayList<Tile> tile4Run;
	private ArrayList<Tile> tile4Group;
	private ArrayList<Tile> tile2Play;
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

	public ArrayList<Tile> findInitRun() {
		// TODO Auto-generated method stub
		tile4Run = new ArrayList<Tile>();
		tile2Play = new ArrayList<Tile>();
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
							tile4Run.clear();	
							tile4Run.add(this.getHand(handIndex));
							playIndex = 0;
							handIndex++;
						}
					} else {
						tile2Play.addAll(tile4Run);
						tile4Run.clear();
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
				} else {
					countPoint = checkSum(tile4Run);
					if(countPoint >= 30) {
						tile2Play.addAll(tile4Run);
						tile4Run.clear();
						tile4Run.add(this.getHand(i));
						playIndex = 0;
						run = false;		// reach 30, break loop 
					} else {	
						tile4Run.clear();	
						tile4Run.add(this.getHand(i));
						playIndex = 0;
					}
				}
			}
		}
		return tile2Play;
	}

	public ArrayList<Tile> findInitGroup() {
		// TODO Auto-generated method stub
		tile4Group = new ArrayList<Tile>();
		tile2Play = new ArrayList<Tile>();
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
							tile4Group.clear();	
							tile4Group.add(this.getHand(handIndex));
							playIndex = 0;
							handIndex++;
						}
					} else {
						tile2Play.addAll(tile4Group);
						tile4Group.clear();
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
				} else {
					countPoint = checkSum(tile4Group);
					if(countPoint >= 30) {
						tile2Play.addAll(tile4Group);
						tile4Group.clear();
						tile4Group.add(this.getHand(i));
						playIndex = 0;
						run = false;		// reach 30, break loop 
					} else {	
						tile4Group.clear();	
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

	public int play(Tile t) {
		// TODO Auto-generated method stub
		boolean has = false;
		int index = 0 ;
		for(int i=0; i<this.getHands().size();i++) {
			if(this.getHand(i) == t) {
				has = true;
				index = i;
				break;
			}	
		}
		if(!has)
			System.out.println("can not find the Tile ---- AI.java 315");
		
		return index;
	}

	public ArrayList<Tile> findRun() {
		// TODO Auto-generated method stub
		tile4Run = new ArrayList<Tile>();
		tile2Play = new ArrayList<Tile>();
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
							tile4Run.clear();	
							tile4Run.add(this.getHand(handIndex));
							playIndex = 0;
							handIndex++;
						}
					} else {
						tile2Play.addAll(tile4Run);
						tile4Run.clear();
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
				} else {
					if(tile4Run.size() >= 3) {
						tile2Play.addAll(tile4Run);
						tile4Run.clear();
						tile4Run.add(this.getHand(i));
						playIndex = 0;
					} else {	
						tile4Run.clear();	
						tile4Run.add(this.getHand(i));
						playIndex = 0;
					}
				}
			}
		}
		return tile2Play;
	}

	public ArrayList<Tile> findGroup() {
		// TODO Auto-generated method stub
		tile4Group = new ArrayList<Tile>();
		tile2Play = new ArrayList<Tile>();
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
							tile4Group.clear();	
							tile4Group.add(this.getHand(handIndex));
							playIndex = 0;
							handIndex++;
						} else {
							tile2Play.addAll(tile4Group);
							tile4Group.clear();
							tile4Group.add(this.getHand(handIndex));
							handIndex++;
							playIndex = 0;
						}
					} else {
						tile2Play.addAll(tile4Group);
						tile4Group.clear();
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
				} else {
					if(tile4Group.size() >= 3) {
						tile2Play.addAll(tile4Group);
						tile4Group.clear();
						tile4Group.add(this.getHand(i));
						playIndex = 0;
					} else {	
						tile4Group.clear();	
						tile4Group.add(this.getHand(i));
						playIndex = 0;
					}
				}
			}
		}
		
		return tile2Play;
	}

	public ArrayList<Tile> findComb() {
		// TODO Auto-generated method stub
		ArrayList<Tile> tempArray = new ArrayList<Tile>();
		tile4Run = new ArrayList<Tile>();
		tile4Group = new ArrayList<Tile>();
		System.out.println(tempArray);
		tile4Run = this.findRun();
		tile4Group = this.findGroup();
		int runSum = this.checkSum(tile4Run);
		int groupSum = this.checkSum(tile4Group);
		System.out.println(this.getHands());
		System.out.println("---" +tempArray);
		if(runSum >= 30) {
			return tile4Run;
		}  else if(groupSum  >= 30) {
			return tile4Group;
		} else {
			if(runSum > groupSum) {
				tile4Group.clear();
				this.getHands().removeAll(tile4Run);
				tile4Group = this.findGroup();
				if(runSum + this.checkSum(tile4Group) >= 30) {
					this.getHands().addAll(tile4Run);
					tempArray.addAll(tile4Run);
					tempArray.addAll(tile4Group);
					System.out.println(tempArray);
					return tempArray;
				} 
			} else {
				tile4Run.clear();
				this.getHands().removeAll(tile4Group);
				tile4Run = this.findRun();
				if(groupSum + this.checkSum(tile4Run) >= 30) {
					this.getHands().addAll(tile4Group);
					tempArray.addAll(tile4Run);
					tempArray.addAll(tile4Group);
					System.out.println(tempArray);
					return tempArray;
				} 
			}
		}
		return null;
	}
	
}
