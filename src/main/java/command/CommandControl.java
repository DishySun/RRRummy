package command;

import java.util.ArrayList;
import java.util.Arrays;

import game.View;
import rrrummy.Game;

public class CommandControl {
	private ArrayList<Command> commandHistory;
	private View view;
	
	
	public CommandControl(View v) {
		commandHistory = new ArrayList<Command>();
		view = v;
	}
	
	
	public int newCommand(Game game, String command) {
		// return -1 if command is not valid, 0 for excute fail, 1 for success
		command = command.toLowerCase();
		ArrayList<String> commandList = new ArrayList<String>(Arrays.asList(command.split("\\s+")));
		commandList.remove("");
		Command c = null;
		switch (commandList.remove(0)) {
		case "play":
			ArrayList<Integer> intList = new ArrayList<Integer>();
			String temp = "";
			int meldIndex = -1;
			try{
				while(true){
					temp = commandList.remove(0);
					intList.add(Integer.valueOf(temp));
				}
			}catch(java.lang.NumberFormatException e){
			    //to
				if (!temp.equalsIgnoreCase("to")) return -1;
				//TODO:adding a List of indexes to a specific meld feature will be implemented 
				if (intList.size() != 1) return -1;
				try {
					temp = commandList.remove(0);
					meldIndex = Integer.valueOf(temp);
				}catch(Exception ee) {
					return -1;
				}
				try {
					temp = commandList.remove(0);
				}catch (java.lang.IndexOutOfBoundsException ee) {
					//play int to int
					c = new PlayCommand(intList.get(0), meldIndex, game, view);
					break;
				}
				switch (temp) {
				// play int to int head/tail
				case "head":
					c = new PlayCommand(intList.get(0), meldIndex, 0, game);
					break;
				case "tail":
					c = new PlayCommand(intList.get(0), meldIndex, 1, game);
					break;
				default: return -1;
				}
		    }catch(java.lang.IndexOutOfBoundsException e){
		    	//no "to"
		    	if (intList.size() == 0) return -1;
		    	if (intList.size() == 1) {
		    		//play int
		    		c = new PlayCommand(intList.get(0), game);
		    	}else {
		    		//play arraylist
		    		c = new PlayCommand(intList, game);
		    	}
		    }
			//return -1;
		case "move":break;
		case "cut":break;
		case "replace":break;
		case "end": 
			c = new EndTurnCommand(game);
			break;
		default: return -1;
		}
		
		if (command.compareToIgnoreCase("end") == 0) {
			c = new EndTurnCommand(game);
		}
		commandHistory.add(c);
		if(c.excute()) return 1;
		return 0;
		
	}
}
