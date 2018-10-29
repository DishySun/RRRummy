package rrrummy_AI;

import java.util.ArrayList;

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
		try {
			if(tile4Run.size()> 0 || tile4Group.size() >  0) {
				if(tile4Run.size() > tile4Group.size()) {		//first run or first set
					meldListRun = AI.arrayList2MeldList(tile4Run);
					AI.playMeld(meldListRun,table);
				} else {
					meldListSet = AI.arrayList2MeldList(tile4Group);
					AI.playMeld(meldListSet,table);
				}
			} else {	//if no run or set reach 30
				tile2Play =AI.findComb30();
				if(tile2Play != null) {
					meldListTotal = AI.arrayList2MeldList(tile2Play);
					AI.playMeld(meldListTotal,table);
				} else
					hasPlay = false;
					System.out.println("No melds to play");
			}
		} catch (AbleToAddBothSideException e) {
			// TODO Auto-generated catch block
			hasPlay = false;
			e.printStackTrace();
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
		// TODO Auto-generated method stub
		tile4Run = new ArrayList<Tile>();
		tile4Group = new ArrayList<Tile>();
		tile4All = new ArrayList<Tile>();
		
	}

	@Override
	public void update(Table melds, ArrayList<Player> players) {
		// TODO Auto-generated method stub
		this.table = melds;
		this.players = players;
	}

}
