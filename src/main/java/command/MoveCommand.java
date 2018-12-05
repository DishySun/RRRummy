package command;

import gui_game.GameControl;

public class MoveCommand implements Command{
	
	private int fromMeldIndex, fromMeldHoT, toMeldIndex, toMeldHoT;
	private GameControl game;
	
	
	public MoveCommand(int fromMeldIndex, int fromMeldHoT, int toMeldIndex, GameControl game) {
		//3 parameters
		this.fromMeldIndex = fromMeldIndex;
		this.fromMeldHoT = fromMeldHoT;
		this.toMeldIndex = toMeldIndex;
		this.toMeldHoT = -1;
		this.game = game;
	}
	
	public MoveCommand(int fromMeldIndex, int fromMeldHoT, int toMeldIndex, int toMeldHoT, GameControl game) {
		// 4 parameters
		this.fromMeldIndex = fromMeldIndex;
		this.fromMeldHoT = fromMeldHoT;
		this.toMeldIndex = toMeldIndex;
		this.toMeldHoT = toMeldHoT;
		this.game = game;
	}
	@Override
	public boolean excute() {
		boolean fromHoT = false; //HearOrTail
		switch (fromMeldHoT) {
		case 0: 
			fromHoT = true;
			break;
		case 14:
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
		
		return game.commandMove(fromMeldIndex, fromMeldHoT, toMeldIndex, toHoT);
	}
	
}
