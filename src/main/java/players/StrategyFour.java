package players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import observer.Observer;
import rrrummy.Meld;
import rrrummy.Tile;

public class StrategyFour implements AIStrategy, Observer{
	private Hand myHand;
	private ArrayList<Meld> table;
	private HashMap<Integer, Integer> playerHandSizes;
	private ArrayList<Tile> run;
	private ArrayList<Tile> group;
	private ArrayList<Tile> runCut;
	private HashMap<Tile,Integer> meldOnTable;
	private HashMap<ArrayList<Tile>,Integer> moveRunToTable;
	private HashMap<ArrayList<Tile>,Integer> moveGroupToTable;
	private HashMap<ArrayList<Tile>,Integer> cutRunToTable;
	private HashMap<Tile,Integer>replace;
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
	private int countInitial;
	private String returnString;
	private AILogic logic;
	
	public StrategyFour() {
		logic = new AILogic(myHand,table);
		countInitial = 0;
		moveRunIndex = 0;
		moveSetIndex = 0;
		cutRunIndex = 0;
		moveRun2Table = false;
		moveSet2Table = false;
		InRestProg = false;
		cutRun2Table = false;
		hasLess = false;
		tempMeld = new Meld();
		playerHandSizes = new HashMap<Integer, Integer>();
	}

	@Override
	public String generateCommand() {
		// TODO Auto-generated method stub
		boolean hasInitial = false;
		if(countInitial < 30) {	//play initial when someone has played initial
			for(Entry<Integer, Integer> entry : playerHandSizes.entrySet()) {
				//int id = entry.getKey();
				int handsize = entry.getValue();
				if(handsize < 14) {
					hasInitial = true;
					break;
				} 
			}
			if(hasInitial) {
				//returnString = logic.AI4CommandInitial(myHand,table,countInitial);
				//return returnString;
				logic = new AILogic(myHand,table);
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
						else {
							return "END";
						}
					}
				}	else {
					//System.out.print("Someone has played initial, but P2 can't play 30+points");
					return "END";
				}	
			} else {
				return "END";
			}
		} else {		//after initial
			int totalTile  = 0;
			for(Entry<Integer, Integer> entry : playerHandSizes.entrySet()) {
				//int id = entry.getKey();
				int handsize = entry.getValue();
				totalTile += handsize;;
			}
			for(Meld m : table) {
				totalTile += m.size();
			}
			int stockLeft = 106 - totalTile;
			returnString = logic.AI4Command(myHand, table, stockLeft);
			return returnString;
		}
	}
	
	@Override
	public void update(ArrayList<Meld> table) {
		// TODO Auto-generated method stub
		this.table = table;
	}

	@Override
	public void update(int playerId, int handSize) {
		// TODO Auto-generated method stub
		playerHandSizes.put(playerId, handSize);
	}

	@Override
	public String getDifficulty() {
		// TODO Auto-generated method stub
		return "Difficult";
	}

	@Override
	public void setHand(Hand hand) {
		// TODO Auto-generated method stub
		myHand = hand;
	}

	@Override
	public void setMyId(int id) {
		// TODO Auto-generated method stub
		
	}
}
