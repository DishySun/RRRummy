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
	private ArrayList<Tile> tile2Play;
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
		tile2Play = new ArrayList<Tile>();
		AI AI = (AI)players.get(0);
		tile4Run = AI.findInitRun();
		tile4Group = AI.findInitGroup();
		if(tile4Run.size()> 0 || tile4Group.size() >  0) {
			if(tile4Run.size() > tile4Group.size()) {
				for(Tile t: tile4Run) {
					AI.play(AI.play(t));
				}
			} else {
				for(Tile t: tile4Group) {
					AI.play(AI.play(t));
				}
			}
		} else {
			tile2Play =AI.findComb30();
			if(tile2Play.size() > 0) {
				for(Tile t: tile2Play) {
					AI.play(AI.play(t));
				}
			} else
				System.out.println("No melds to play");
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
		
	}

	@Override
	public void update(Table melds, ArrayList<Player> players) {
		// TODO Auto-generated method stub
		this.table = melds;
		this.players = players;
	}

}
