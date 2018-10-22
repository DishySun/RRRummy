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
		int playIndex = 0;	//point to tile4Run position;
		int tileIndex = 0; // point to first valid tile in hand; ps: used when apply joker
		int handIndex;  //used when looping hand cards
		int jokerNum = 0;
		int countPoint = 0;
		boolean hasJoker;
		this.getHands().sort();
		//check if has joker
		if(this.getHand(this.handSize()-1).getColor() == Tile.Color.JOKER) 
			hasJoker = true;
		else
			hasJoker = false;
		
		
		if(hasJoker) {/*
			//count size of joker;
			for(int i = 0; i<this.handSize();i++) {
				if(this.getHand(i).getColor() == Tile.Color.JOKER) 
					jokerNum++;
			}
			tile4Run.add(this.getHand(0));
			handIndex = 1;
			while(handIndex != this.handSize()-jokerNum-1){
				if(tile4Run.get(playIndex).getColor() == this.getHand(handIndex).getColor() 			//1 2 3 4 5
						&& this.getHand(handIndex).getNumber()-1 == tile4Run.get(playIndex).getNumber()
						|| tile4Run.get(playIndex).getColor() == Tile.Color.JOKER)
				{
					tile4Run.add(this.getHand(handIndex));
					playIndex++;		
					handIndex++;
				} else if(tile4Run.get(playIndex).getColor() == this.getHand(handIndex).getColor()	//1 2 3 _ 5 6 && J || JJ
						&& this.getHand(handIndex).getNumber()- 2 == tile4Run.get(playIndex).getNumber() && jokerNum >= 1)
				{
					tileIndex = handIndex;
					tile4Run.add(this.getHand(this.handSize()-1));			//add joker
					playIndex++;		
					//handIndex++;
					jokerNum--;
								
				} else if(tile4Run.get(playIndex).getColor() == this.getHand(handIndex).getColor()		//1 2 _ _ 5 6 7 & J J
										&& this.getHand(handIndex).getNumber()- 3 == tile4Run.get(playIndex).getNumber() && jokerNum == 2)
				{
					tileIndex = handIndex;
					tile4Run.add(this.getHand(this.handSize()-1));			//add joker
					tile4Run.add(this.getHand(this.handSize()-2));			//add joker
					playIndex+=2;		
					//handIndex+=2;
					jokerNum-=2;
				}
				else {		//check tile array if reach 30
					for(Tile tile : tile4Run) {
						countPoint += tile.getNumber();
					}
					if(countPoint >= 30) {
						tile2Play.addAll(tile4Run);
						tile4Run.clear();
						tile4Run.add(this.getHand(handIndex));
						playIndex = 0;
					} else if (countPoint < 30 && tile4Run.size()+jokerNum >= 3 && jokerNum > 0){	//if R12 J J || R11 R12 J 
						int tempCount = 0;
						for(Tile t : tile4Run) {
							tempCount += t.getNumber();
						}
						if(jokerNum == 1) {	
							if(tempCount + tile4Run.get(playIndex).getNumber()+1 >= 30 
									&& tile4Run.get(playIndex).getNumber() != 13) {	// if total number + next number > 30 && not  13 J J 
								tile4Run.add(this.getHand(this.handSize()-1));
								tile2Play.addAll(tile4Run);
								tile4Run.clear();
								tile4Run.add(this.getHand(handIndex));
								playIndex = 0;
								jokerNum--;
							}
						}
						else if(jokerNum == 2) {
							if(tile4Run.get(tile4Run.size()-1).getNumber() < 9) {		//8 J J = 27, 9 10 J = 30
								tile4Run.clear();	
								tile4Run.add(this.getHand(handIndex));
								playIndex = 0;
							} 
						}
					} else if (countPoint < 30 && jokerNum == 0){
						tile4Run.clear();	
						tile4Run.add(this.getHand(handIndex));
						playIndex = 0;
					}
						
				}
			}
			*/
		}
		else {
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
