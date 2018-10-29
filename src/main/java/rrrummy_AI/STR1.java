package rrrummy_AI;

import java.util.ArrayList;

import rrrummy.AbleToAddBothSideException;
import rrrummy.Meld;
import rrrummy.Player;
import rrrummy.Table;
import rrrummy.Tile;

public class STR1 implements Observer, AIstrategy{
	private Table table;
	private ArrayList<Meld> meldListRun;
	private ArrayList<Meld> meldListSet;
	private ArrayList<Meld> meldListTotal;
	private ArrayList<Player> players;
	private ArrayList<ArrayList<Tile>> tile4Run;
	private ArrayList<ArrayList<Tile>> tile4Group;
	private ArrayList<ArrayList<Tile>> tile2Play;
	public STR1(Subject data) {
		data.registerObserver(this);
		players = new ArrayList<Player>();
	}
	
	/**
	 * play as soon as possible
	 */
	@Override
	public void playInitial() {
		// TODO Auto-generated method stub
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
				if(tile2Play.size() > 0) {
					meldListTotal = AI.arrayList2MeldList(tile2Play);
					AI.playMeld(meldListTotal,table);
				} else
					System.out.println("No melds to play");
			}
		} catch (AbleToAddBothSideException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * plays all the tiles it can.
	 */
	@Override
	public void playRest() {
		// TODO Auto-generated method stub
		tile4Run = new ArrayList<ArrayList<Tile>>();
		tile4Group = new ArrayList<ArrayList<Tile>>();
		tile2Play = new ArrayList<ArrayList<Tile>>();
		meldListRun = new ArrayList<Meld>();
		meldListSet = new ArrayList<Meld>();
		meldListTotal = new ArrayList<Meld>();
		
		AI AI = (AI)players.get(0);
		tile4Run = AI.findRun();
		tile4Group = AI.findGroup();
		try{
			if(tile4Run.size() > tile4Group.size()) { //first run
				meldListRun = AI.arrayList2MeldList(tile4Run);
				AI.playMeld(meldListRun,table);
				tile4Group = AI.findGroup();
				meldListSet = AI.arrayList2MeldList(tile4Group);
				AI.playMeld(meldListSet,table);
			} else {		//first group
				meldListSet = AI.arrayList2MeldList(tile4Group);
				AI.playMeld(meldListSet,table);
				tile4Run = AI.findRun();
				meldListRun = AI.arrayList2MeldList(tile4Run);
				AI.playMeld(meldListRun,table);
			}
			}catch (AbleToAddBothSideException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	@Override
	public void update(Table melds, ArrayList<Player> players) {
		// TODO Auto-generated method stub
		this.table = melds;
		this.players = players;
	}
}
