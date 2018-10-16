package AI;

import rrrummy.Player;

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
}
