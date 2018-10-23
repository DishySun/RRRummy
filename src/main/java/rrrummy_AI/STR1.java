package rrrummy_AI;

import java.util.ArrayList;

import rrrummy.Player;
import rrrummy.Table;
import rrrummy.Tile;

public class STR1 implements Observer, AIstrategy{
	private Table table;
	private ArrayList<Player> players;
	private ArrayList<Tile> tile4Run;
	private ArrayList<Tile> tile4Group;
	private ArrayList<Tile> tile2Play;
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
		tile4Run = new ArrayList<Tile>();
		tile4Group = new ArrayList<Tile>();
		tile2Play = new ArrayList<Tile>();
		AI AI = (AI)players.get(0);
		tile4Run = AI.findInitRun();
		tile4Group = AI.findInitGroup();
		if(tile4Run.size() > tile4Group.size()) {
			for(Tile t: tile4Run) {
				AI.play(AI.play(t));
			}
		} else {
			for(Tile t: tile4Group) {
				AI.play(AI.play(t));
			}
		}
	}

	/**
	 * plays all the tiles it can.
	 */
	@Override
	public void playRest() {
		// TODO Auto-generated method stub
		tile4Run = new ArrayList<Tile>();
		tile4Group = new ArrayList<Tile>();
		tile2Play = new ArrayList<Tile>();
		AI AI = (AI)players.get(0);
		tile4Run = AI.findRun();
		tile4Group = AI.findGroup();
		if(tile4Run.size() > tile4Group.size()) { //first run
			for(Tile t: tile4Run) {
				AI.play(AI.play(t));
			}
			tile4Group = AI.findGroup();
			for(Tile t: tile4Group) {
				AI.play(AI.play(t));
			}
		} else {		//first group
			for(Tile t: tile4Group) {
				AI.play(AI.play(t));
			}
			tile4Run = AI.findRun();
			for(Tile t: tile4Run) {
				AI.play(AI.play(t));
			}
		}
	}

	@Override
	public void update(Table melds, ArrayList<Player> players) {
		// TODO Auto-generated method stub
		this.table = melds;
		this.players = players;
	}
}
