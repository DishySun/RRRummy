package command;

import rrrummy.Game;

public class CutCommand implements Command{

	private int meldIndex, tileIndex;
	private Game game;
	
	public CutCommand(int meldIndex, int tileIndex, Game game) {
		this.meldIndex = meldIndex;
		this.tileIndex = tileIndex;
		this.game = game;
	}
	
	@Override
	public boolean excute() {
		return game.cut(meldIndex, tileIndex);
	}

}
