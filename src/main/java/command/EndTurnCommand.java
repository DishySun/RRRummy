package command;

import players.Player;
import rrrummy.Game;

public class EndTurnCommand implements Command{

	private Game game;
	private Player player;
	public EndTurnCommand(Game game, Player player) {
		this.game = game;
		this.player = player;
	}
	@Override
	public void excute() {
		game.endTurn(player.getId());
	}

}
