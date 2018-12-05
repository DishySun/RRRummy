package command;
import gui_game.GameControl;

public class EndTurnCommand implements Command{

	private GameControl game;
	public EndTurnCommand(GameControl game) {
		this.game = game;
	}
	@Override
	public boolean excute() {
		game.commandEndTurn();
		return true;
	}

}
