package command;

import gui_game.GameControl;

public class CutCommand implements Command{

	private int meldIndex, tileIndex;
	private GameControl game;
	
	public CutCommand(int meldIndex, int tileIndex, GameControl game) {
		this.meldIndex = meldIndex;
		this.tileIndex = tileIndex;
		this.game = game;
	}
	
	@Override
	public boolean excute() {
		return game.commandCut(meldIndex, tileIndex);
	}

}
