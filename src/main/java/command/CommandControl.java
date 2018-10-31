package command;

import java.util.ArrayList;

import players.Player;
import rrrummy.Game;

public class CommandControl {
	private ArrayList<Command> commandHistory;
	
	public CommandControl() {
		commandHistory = new ArrayList<Command>();
	}
	
	public void newCommand(Game game, String command, Player p) {
		if (command.compareToIgnoreCase("end") == 0) {
			Command c = new EndTurnCommand(game, p);
			commandHistory.add(c);
			c.excute();
		}
	}
}
