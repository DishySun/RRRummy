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
	private HashMap<Tile,Integer> meldOnTable;;
	private int countInitial;
	private String returnString;
	
	public StrategyTwo (Subject data) {
		data.register(this);
		countInitial = 0;
	}
	
	@Override
	public String generateCommand() {
		returnString = "";
		run = new ArrayList<Tile>();
		group = new ArrayList<Tile>();
		meldOnTable = new HashMap<Tile,Integer>();
		boolean hasInitial = false;
		
		if(countInitial < 30) {	//play initial
			System.out.println(playerHandSizes);
			for(Entry<Integer, Integer> entry : playerHandSizes.entrySet()) {
				//int id = entry.getKey();
				int handsize = entry.getValue();
				if(handsize < 14) {
					hasInitial = true;
					break;
				} 
			}
			if(hasInitial) {
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
							System.out.println( "Something went wrong");
							return "END";
						}
					}
				}	else {
					System.out.print("Someone has played initial, but P2 can't play 30+points");
					return "END";
				}
						
			} else {
				return "END";}
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
								returnString = "Play "  + myHand.handIndexOf(tile) + " to " + index /*+ " 1"*/;
								return returnString;
							}
						}else {
									return "Something Wrong";
						}
					}
				}
			}else {		// can not win, play tile that match meld on table
				meldOnTable = myHand.findMeldsOnTable(table);
				if(meldOnTable != null) {
					for(Entry<Tile, Integer>Entry : meldOnTable.entrySet()) {
						Tile tile = Entry.getKey();
						int index = Entry.getValue();
						myHand.sort();
						returnString = "Play "  + myHand.handIndexOf(tile) + " to " + index/* + " 0"*/;
						return returnString;
					}
				} else{
					return "END";
				}
					
			}	
			System.out.println( "Something went wrong");
			return "END";
		}
	}

	@Override
	public void update(ArrayList<Meld> table) {
		this.table = table;
	}
	@Override
	public void update(HashMap<Integer, Integer> handSizes) {
		this.playerHandSizes = handSizes;
		
	}
	@Override
	public void setHand(Hand hand) {
		myHand = hand;
	}

	@Override
	public String getDifficulty() {
		// TODO Auto-generated method stub
		return null;
	}
}
