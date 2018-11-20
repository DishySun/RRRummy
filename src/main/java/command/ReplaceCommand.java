package command;

import game.Game;

public class ReplaceCommand implements Command{

	private int handIndex, tableIndex, meldIndex;
	private Game game;
	
	public ReplaceCommand(int handIndex, int tableIndex, int meldIndex, Game game) {
		this.handIndex = handIndex;
		this.tableIndex = tableIndex;
		this.meldIndex = meldIndex;
		this.game = game;
	}
	
	@Override
	public boolean excute() {
		return game.replace(handIndex, tableIndex, meldIndex);
	}
}
