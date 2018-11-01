package command;
import rrrummy.Game;

public class EndTurnCommand implements Command{

	private Game game;
	public EndTurnCommand(Game game) {
		this.game = game;
	}
	@Override
	public void excute() {
		game.endTurn();
	}

}
