package rrrummy_AI;

import java.util.ArrayList;
import java.util.HashMap;

import rrrummy.AbleToAddBothSideException;
import rrrummy.Meld;
import rrrummy.Player;
import rrrummy.Table;
import rrrummy.Tile;

public class STR3  implements Observer, AIstrategy{
	private Table table;
	private ArrayList<Meld> meldListRun;
	private ArrayList<Meld> meldListSet;
	private ArrayList<Meld> meldListTotal;
	private ArrayList<Player> players;
	private ArrayList<ArrayList<Tile>> meldListAllList;
	private HashMap<Tile,Integer> tile4PlayOnTable;
	private ArrayList<ArrayList<Tile>> tile4Run;
	private ArrayList<ArrayList<Tile>> tile4Group;
	private ArrayList<ArrayList<Tile>> tile2Play;
	private AI AI;
	public STR3(Subject data) {
		data.registerObserver(this);
		players = new ArrayList<Player>();
	}
	
	/**
	 * play as soon as possible
	 */
	@Override
	public boolean playInitial() {
		// TODO Auto-generated method stub
		AI = (AI)players.get(0);
		boolean hasPlay = true;
		tile4Run = new ArrayList<ArrayList<Tile>>();
		tile4Group = new ArrayList<ArrayList<Tile>>();
		tile2Play = new ArrayList<ArrayList<Tile>>();
		meldListRun = new ArrayList<Meld>();
		meldListSet = new ArrayList<Meld>();
		meldListTotal = new ArrayList<Meld>();
		
		AI AI = (AI)players.get(0);
		tile4Run = AI.findInitRun();
		tile4Group = AI.findInitGroup();
		if(tile4Run.size()> 0 || tile4Group.size() >  0) {
			if(tile4Run.size() > tile4Group.size()) {		//first run or first set
				meldListRun = AI.arrayList2MeldList(tile4Run);
				AI.playMeld(meldListRun,table);
			} else {
				meldListSet = AI.arrayList2MeldList(tile4Group);
				AI.playMeld(meldListSet,table);
			}
		} else {	//if no run or set reach 30\
			tile2Play =AI.findComb30();
			if(tile2Play != null) {
				meldListTotal = AI.arrayList2MeldList(tile2Play);
				AI.playMeld(meldListTotal,table);
			} else
				hasPlay = false;
				System.out.println("No melds to play");
		}
		return hasPlay;
	}

	/**
	 * if it can play all tiles(possibly using what's on table) it does;
	 * else:
	 * 		if no other players hand.size< this.hand.size - 3, only play tiles that form meld on table
	 * 		else, play all tiles it can
	 */
	@Override
	public boolean playRest() {
		AI = (AI)players.get(0);
		boolean shouldPlay = false;
		boolean canPlay = false;
		boolean hasPlay = true;
		int tileCount = 0;
		// TODO Auto-generated method stub
		tile4Run = new ArrayList<ArrayList<Tile>>();
		tile4Group = new ArrayList<ArrayList<Tile>>();
		tile2Play = new ArrayList<ArrayList<Tile>>();
		meldListAllList = new ArrayList<ArrayList<Tile>>();
		meldListRun = new ArrayList<Meld>();
		meldListSet = new ArrayList<Meld>();
		meldListTotal = new ArrayList<Meld>();
		tile4PlayOnTable = new HashMap<Tile,Integer>();
		
		for(Player p : players) {
			if(p.handSize() < AI.handSize()-3) {
				shouldPlay = true;
				break;
			}
		}
		if(shouldPlay) {				//13 3 less than P3
			meldListAllList = AI.findCombAll();			// find all possible tiles on hands
			if(meldListAllList != null) {
				meldListTotal = AI.arrayList2MeldList(meldListAllList);
				AI.playMeld(meldListTotal, table);
			}
			tile4PlayOnTable = AI.findMeldsOnTable(table);
			if(tile4PlayOnTable != null) {
				AI.play2Table(tile4PlayOnTable, table);
			}
			if(meldListAllList == null && tile4PlayOnTable == null) {	
				System.out.println("p3 could play but has not tile to play");
				hasPlay = false;
			}
		} else {
			meldListAllList = AI.findCombAll();
			for(ArrayList<Tile> arr : meldListAllList) {
				tileCount += arr.size();
			}
			if(tileCount == AI.handSize()) { 		//-12a win not using tiles on table
				meldListTotal = AI.arrayList2MeldList(meldListAllList);
				AI.playMeld(meldListTotal, table);
				canPlay = true;
			}else { // -13b win using tiles on table
				for(ArrayList<Tile> arr : meldListAllList) {
					AI.getHands().removeAll(arr);
				}
				tile4PlayOnTable = AI.findMeldsOnTable(table);
				for(ArrayList<Tile> arr : meldListAllList) {
					AI.getHands().addAll(arr);
				}
				if(tile4PlayOnTable != null && tileCount + tile4PlayOnTable.size() == AI.handSize()) {
					AI.play2Table(tile4PlayOnTable, table);
					canPlay = true;
				}
				else 
					hasPlay = false;
			}
		}
		return hasPlay;
	}

	@Override
	public void update(Table melds, ArrayList<Player> players) {
		// TODO Auto-generated method stub
		this.table = melds;
		this.players = players;
	}

}
