package command;

import gui_game.GameControl;

public class ReplaceCommand implements Command{

	private int handIndex, tableIndex, meldIndex;
	private GameControl game;
	
	public ReplaceCommand(int handIndex, int tableIndex, int meldIndex, GameControl game) {
		this.handIndex = handIndex;
		this.tableIndex = tableIndex;
		this.meldIndex = meldIndex;
		this.game = game;
	}
	
	@Override
	public boolean excute() {
		return game.commandReplace(handIndex, tableIndex, meldIndex);
	}
}
