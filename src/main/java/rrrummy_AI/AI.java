package rrrummy_AI;

import java.util.ArrayList;

import rrrummy.Player;
import rrrummy.Tile;

public class AI extends Player{
	private AIstrategy aIstrategy;
	public AI(String n) {
		super(n);
		// TODO Auto-generated constructor stub
	}

	public void setSTY(AIstrategy AISTY) {
		// TODO Auto-generated method stub
		this.aIstrategy = AISTY;
	}

	public AIstrategy getSTY() {
		return aIstrategy;
	}

	public ArrayList<Tile> findInitRun() {
		// TODO Auto-generated method stub
		
		return null;
	}

	public ArrayList<Tile> findInitGroup() {
		// TODO Auto-generated method stub
		return null;
	}
}
