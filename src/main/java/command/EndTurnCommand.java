package command;

import game.GameControl;

public class EndTurnCommand implements Command{

	private GameControl gameControl;
	public EndTurnCommand(GameControl gc) {
		gameControl = gc;
	}
	@Override
	public void excute() {
		gameControl.playerEndTurn();
	}

}
