package rrrummy_AI;

import java.util.ArrayList;

import rrrummy.Player;
import rrrummy.Table;
import rrrummy.Tile;

public class STR2 implements Observer, AIstrategy {
	private Table table;
	private ArrayList<Player> players;
	private ArrayList<Tile> tile4Run;
	private ArrayList<Tile> tile4Group;
	public STR2(Subject data) {
		data.registerObserver(this);
		players = new ArrayList<Player>();
	}
	
	/**
	 * play only if another players has already played tiles on the table
	 */
	@Override
	public void playInitial() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * case 1: if can play all tiles on hand(possibly using tiles on table), play all;
	 * case 2: else, only play tiles that can form melds on the table && 
	 */
	@Override
	public void playRest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Table melds, ArrayList<Player> players) {
		// TODO Auto-generated method stub
		this.table = melds;
		this.players = players;
	}

}
