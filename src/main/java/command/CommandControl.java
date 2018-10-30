package command;

import java.util.ArrayList;

import game.GameControl;
import players.Player;

public class CommandControl {
	private ArrayList<Command> commandHistory;
	
	public CommandControl() {
		commandHistory = new ArrayList<Command>();
	}
	
	public boolean newCommand(GameControl gc, String command, Player p) {
		if (command.compareToIgnoreCase("End") == 0) {
			System.out.println(p.getName() + " decided to draw a tile and end his turn.");
			Command c = new DrawAndEndCommand(gc);
			commandHistory.add(c);
			c.excute();
			return true;
		}
		return false;
	}
}
