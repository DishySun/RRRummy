package rrrummy_AI;

import java.util.ArrayList;

import rrrummy.Player;
import rrrummy.Table;

public class STR3  implements Observer, AIstrategy{
	private Table table;
	private ArrayList<Player> players;
	public STR3(Subject data) {
		data.registerObserver(this);
		players = new ArrayList<Player>();
	}
	
	/**
	 * play as soon as possible
	 */
	@Override
	public void playInitial(Table table) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * if no other players hand.size< this.hand.size - 3, only play tiles that form meld on table
	 * else, play all tiles it can
	 */
	@Override
	public void playRest(Table table, ArrayList<Player> players) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Table melds, ArrayList<Player> players) {
		// TODO Auto-generated method stub
		
	}

}
