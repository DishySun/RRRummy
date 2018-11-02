package players;

import java.util.ArrayList;
import java.util.HashMap;

import observer.GameData;
import observer.Observer;
import observer.Subject;
import rrrummy.Meld;
import rrrummy.Tile;

public class StrategyZero implements AIStrategy, Observer{
	private Hand myHand;
	private ArrayList<Meld> table;
	private HashMap<Integer, Integer> playerHandSizes;
	private ArrayList<Tile> run;
	private ArrayList<Tile> group;
	private int countInitial;
	private String returnString;
	
	public StrategyZero(Subject data) {
		data.register(this);
		countInitial = 0;
	}
	
	@Override
	public String generateCommand() {
		returnString = "";
		run = new ArrayList<Tile>();
		group = new ArrayList<Tile>();
		if(countInitial < 30) {	//play initial
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
					else
						return "Something wrong";
				}
			}	else
				return "END";
		}else {
			run = myHand.findRun();
			if(run != null)	{
				myHand.sort();
				returnString = "Play";
				for(int i=0; i<run.size();i++) {
					returnString += " " + myHand.handIndexOf(run.get(i)); 
				}
				return returnString;
			} else {
				group  = myHand.findGroup();
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
	}

	@Override
	public String getDifficulty() {
		return "Retard";
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
}
