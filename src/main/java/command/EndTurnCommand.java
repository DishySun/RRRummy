package command;
import rrrummy.Game;

public class EndTurnCommand implements Command{

	private Game game;
	public EndTurnCommand(Game game) {
		this.game = game;
	}
	@Override
	public boolean excute() {
		game.endTurn();
		return true;
	}

}
