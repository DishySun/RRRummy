package rrrummy_AI;

import java.util.ArrayList;

import rrrummy.Player;
import rrrummy.Table;

public class STR1 implements Observer, AIstrategy{
	private Table table;
	private ArrayList<Player> players;
	public STR1(Subject data) {
		data.registerObserver(this);
		players = new ArrayList<Player>();
	}
	
	/**
	 * play as soon as possible
	 */
	@Override
	public void playInitial(Table t) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * plays all the tiles it can.
	 */
	@Override
	public void playRest(Table table, ArrayList<Player> players) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Table melds, ArrayList<Player> players) {
		// TODO Auto-generated method stub
		this.table = melds;
		this.players = players;
	}
}
