package command;

import game.View;
import rrrummy.Game;

public class MoveCommand implements Command{
	
	private int fromMeldIndex, fromMeldHoT, toMeldIndex, toMeldHoT;
	private Game game;
	private View view;
	
	
	public MoveCommand(int fromMeldIndex, int fromMeldHoT, int toMeldIndex, Game game, View view) {
		this.fromMeldIndex = fromMeldIndex;
		this.fromMeldHoT = fromMeldHoT;
		this.toMeldIndex = toMeldIndex;
		this.toMeldHoT = -1;
		this.game = game;
		this.view = view;
	}
	
	public MoveCommand(int fromMeldIndex, int fromMeldHoT, int toMeldIndex, int toMeldHoT, Game game) {
		this.fromMeldIndex = fromMeldIndex;
		this.fromMeldHoT = fromMeldHoT;
		this.toMeldIndex = toMeldIndex;
		this.toMeldHoT = toMeldHoT;
		this.game = game;
		this.view = null;
	}
	@Override
	public boolean excute() {
		boolean fromHoT = false; //HearOrTail
		switch (fromMeldHoT) {
		case 0: 
			fromHoT = true;
			break;
		case 1:
			fromHoT = false;
			break;
		default: return false;
		}
		
		boolean toHoT = false;
		switch (toMeldHoT) {
		case 0: 
			toHoT = true;
			break;
		case 1:
			toHoT = false;
			break;
		default:
			toHoT = false;
		}
		
		String str = "";
		if (toMeldHoT == -1) {
			//3 parameters move method
			try {
				return game.move(fromMeldIndex, fromHoT, toMeldIndex);
			}catch (rrrummy.AbleToAddBothSideException e) {
				str = view.getHeadOrTail("move");
				if (str.equalsIgnoreCase("head"))toHoT = true;
				else toHoT = false;
			}
		}
		return game.move(toMeldIndex, fromHoT, toMeldIndex, toHoT);
	}
	
}
