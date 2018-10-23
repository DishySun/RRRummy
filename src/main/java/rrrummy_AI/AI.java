package rrrummy_AI;

import java.util.ArrayList;

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
		boolean run = true;
		boolean disconnect = true;
		boolean pass = false;
		int playIndex = 0;	//point to tile4Run position;
		int handIndex;  //used when looping hand cards
		int jokerNum = 0;
		int jokerorg = 0;
		int countPoint = 0;
		boolean hasJoker;
		this.getHands().sort();
		//check if has joker
		if(this.getHand(this.handSize()-1).getColor() == Tile.Color.JOKER) 
			hasJoker = true;
		else
			hasJoker = false;
		if(hasJoker) {		// has joker
			//count size of joker;
			for(int i = 0; i<this.handSize();i++) {
				if(this.getHand(i).getColor() == Tile.Color.JOKER) 
					jokerNum++;
			}
			jokerorg = jokerNum;
			tile4Run.add(this.getHand(0));
			handIndex = 1;
			while(handIndex <= this.handSize()-jokerorg && run) {
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
							if(tile4Run.size() >= 2 && tile4Run.get(tile4Run.size()-1).getNumber() == 12 
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
						run = false;
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
						for(Tile tile : tile4Run) {
							countPoint += tile.getNumber();
						}
						if(countPoint >= 30) {
							tile2Play.addAll(tile4Run);
							tile4Run.clear();
							tile4Run.add(this.getHand(i));
							playIndex = 0;
							run = false;
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
		return null;
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
}
