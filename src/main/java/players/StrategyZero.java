package players;

import java.util.ArrayList;
import java.util.HashMap;
import rrrummy.Meld;
import rrrummy.Tile;

public class StrategyZero implements AIStrategy{
	private Hand myHand;
	private int myId;
	private ArrayList<Meld> table;
	private HashMap<Integer, Integer> playerHandSizes;
	private ArrayList<Tile> run;
	private ArrayList<Tile> group;
	private int countInitial;
	private String returnString;
	private AILogic logic;
	
	public StrategyZero(){
		playerHandSizes = new HashMap<Integer, Integer>();
		logic = new AILogic(myHand,table);
	}
	
	@Override
	public String generateCommand() {
		logic = new AILogic(myHand,table);
		returnString = "";
		run = new ArrayList<Tile>();
		group = new ArrayList<Tile>();
		if(countInitial < 30) {	//play initial
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
					else
						return "Something wrong";
				}
			}	else
				return "END";
		}else {
			returnString = logic.AI1Command(myHand,table);
			return returnString;
			/*run = logic.findRun();
			if(run != null)	{
				myHand.sort();
				returnString = "Play";
				for(int i=0; i<run.size();i++) {
					returnString += " " + myHand.handIndexOf(run.get(i)); 
				}
				return returnString;
			} else {
				group  = logic.findSet();
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
			}	*/
		}
	}
	
	public void setMyId(int id) { myId = id;}
	
	
	@Override
	public String getDifficulty() {
		return "Retard";
	}
	@Override
	public void update(ArrayList<Meld> table) {
		this.table = table;
	}
	@Override
	public void setHand(Hand hand) {
		myHand = hand;
	}


	@Override
	public void update(int playerId, int handSize) {
		// do nothing coz this Strategy doesn't need to know other player's hand size
		playerHandSizes.put(playerId, handSize);
	}
	
}
