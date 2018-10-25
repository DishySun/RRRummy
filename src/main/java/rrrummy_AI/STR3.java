package rrrummy_AI;

import java.util.ArrayList;

import rrrummy.Player;
import rrrummy.Table;
import rrrummy.Tile;

public class STR3  implements Observer, AIstrategy{
	private Table table;
	private ArrayList<Player> players;
	private ArrayList<Tile> tile4Run;
	private ArrayList<Tile> tile4Group;
	private ArrayList<Tile> tile4All;
	private ArrayList<Tile> tile4MeldT;
	private AI AI;
	public STR3(Subject data) {
		data.registerObserver(this);
		players = new ArrayList<Player>();
		AI = (AI)players.get(0);
	}
	
	/**
	 * play as soon as possible
	 */
	@Override
	public void playInitial() {
		// TODO Auto-generated method stub
		tile4Run = new ArrayList<Tile>();
		tile4Group = new ArrayList<Tile>();
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
	 * if it can play all tiles(possibly using what's on table) it does;
	 * else:
	 * 		if no other players hand.size< this.hand.size - 3, only play tiles that form meld on table
	 * 		else, play all tiles it can
	 */
	@Override
	public void playRest() {
		// TODO Auto-generated method stub
		tile4Run = new ArrayList<Tile>();
		tile4Group = new ArrayList<Tile>();
		tile4All = new ArrayList<Tile>();
		tile4Group = new ArrayList<Tile>();
		boolean handsless3 = false;
		for(Player p : players) {
			if(p.handSize() < AI.handSize()-3) {
				handsless3 = true;
				break;
			}
		}
		
		tile4All = AI.findBest2Play(table);
		if(tile4All.size() == AI.handSize()) {
			for(Tile t: tile4All) {
				AI.play(AI.play(t));/////
			}
		} else {
			if(handsless3) {
				tile4MeldT = AI.findMeldsOnTable(table);
				for(Tile t: tile4All) {
					AI.play(AI.play(t));////////
				}
			} else {
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
		}
	}

	@Override
	public void update(Table melds, ArrayList<Player> players) {
		// TODO Auto-generated method stub
		this.table = melds;
		this.players = players;
	}

}
