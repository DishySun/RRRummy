package command;

import java.util.ArrayList;
import java.util.Arrays;

import game.Game;
import game.View;

import java.lang.NumberFormatException;
import java.lang.IndexOutOfBoundsException;

public class CommandControl {
	private ArrayList<Command> commandHistory;
	private ArrayList<String> commandStringList;
	private View view;
	private Game game;

	public CommandControl(View v, Game game) {
		commandHistory = new ArrayList<Command>();
		commandStringList = new ArrayList<String>();
		view = v;
		this.game = game;
	}

	public int newCommand(String command) {
		commandStringList.add(command);
		int i = createCommand(command);
		if (i < 1)commandStringList.remove(commandStringList.size() - 1);
		return i;
	}
	
	private int createCommand(String command) {
		// return -1 if command is not valid, 0 for execute fail, 1 for success
		command = command.toLowerCase();
		ArrayList<String> commandList = new ArrayList<String>(Arrays.asList(command.split("\\s+")));
		System.out.println("command ---" + commandList);
		commandList.remove("");
		if (commandList.size() == 0) return -1;
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
			}catch(NumberFormatException e){
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
				}catch (IndexOutOfBoundsException ee) {
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
			break;
			//return -1;
		case "move":
			int fromMeldIndex = 0;
			int fromMeldHoT = -1;
			int toMeldIndex = 0;
			int toMeldHot = -1;
			try {
				temp = commandList.remove(0);
				fromMeldIndex = Integer.valueOf(temp);
			} catch (Exception e) {
				System.out.println("int is excepted for 2nd string");
				return -1;
			}
			try {
				temp = commandList.remove(0);
				if(temp.equalsIgnoreCase("head")) fromMeldHoT = 0;
				else if (temp.equalsIgnoreCase("tail")) fromMeldHoT = 1;
				else return -1;
			}catch(Exception e) {
				System.out.println("'Head'/'Tail' is expected for 3rd string");
				return -1;
			}
			try {
				temp = commandList.remove(0);
				if(!temp.equalsIgnoreCase("to")) return -1;
			}catch(IndexOutOfBoundsException e) {
				System.out.println("'to' is excepted for 4th string");
				return -1;
			}
			try {
				temp = commandList.remove(0);
				toMeldIndex = Integer.valueOf(temp);
			}catch(Exception e) {
				System.out.println("int is excepted for 5th string");
				return -1;
			}
			try {
				temp = commandList.remove(0);
				if (temp.equalsIgnoreCase("head")) toMeldHot = 0;
				else if (temp.equalsIgnoreCase("tail"))toMeldHot = 1;
				else {
					System.out.println("'Head'/'Tail' is expected for 6rd string");
					return -1;
				}
				
			}catch(IndexOutOfBoundsException e) {
				// 3 parameters
				c = new MoveCommand(fromMeldIndex, fromMeldHoT, toMeldIndex, game, view);
			}
			//4 parameters
			c = new MoveCommand(fromMeldIndex, fromMeldHoT, toMeldIndex, toMeldHot ,game, view);
			break;
		case "cut":
			int cutFrom = -1;
			int cutAt = -1;
			try {
				temp = commandList.remove(0);
				cutFrom = Integer.valueOf(temp);
			}catch (Exception e) {
				System.out.println("int is expected for 2nd string");
				return -1;
			}
			try {
				temp = commandList.remove(0);
				if (!temp.equalsIgnoreCase("at")) return -1;
			}catch (Exception e) {
				System.out.println("'at' is expected for 3rd string");
				return -1;
			}
			try {
				temp = commandList.remove(0);
				cutAt = Integer.valueOf(temp);
			}catch (Exception e) {
				System.out.println("int is expected for 4th string");
				return -1;
			}
			c = new CutCommand(cutFrom, cutAt, game);
			break;
		case "replace":
			int replaceFrom = -1;
			int replaceTo = -1;
			int replaceHandIndex = -1;
			try {
				temp = commandList.remove(0);
				replaceFrom = Integer.valueOf(temp);
			}catch(Exception e) {
				System.out.println("int is excepted for 2nd string");
				return -1;
			}
			try {
				temp = commandList.remove(0);
				if(!temp.equalsIgnoreCase("to")) return -1;
			}catch(Exception e) {
				System.out.println("'to' is excepted for 3rd string");
				return -1;
			}
			try {
				temp = commandList.remove(0);
				replaceTo = Integer.valueOf(temp);
			}catch(Exception e) {
				System.out.println("int is excepted for 4th string");
				return -1;
			}
			try {
				temp = commandList.remove(0);
				replaceHandIndex = Integer.valueOf(temp);
			}catch(Exception e) {
				System.out.println("int is excepted for 5th string");
				return -1;
			}
			c = new ReplaceCommand(replaceFrom, replaceTo, replaceHandIndex, game);
			break;
		case "end": 
			c = new EndTurnCommand(game);
			break;
		default: 
			System.out.println("[Play, Move, Cut, Replace] for 1st string");
			return -1;
		}
		
		if (command.compareToIgnoreCase("end") == 0) {
			c = new EndTurnCommand(game);
		}
		if(c.excute()) {
			commandHistory.add(c);
			return 1;
		}
		return 0;
	}
	
	public String toString() {
		String result = "";
		for(int i = 0; i < commandStringList.size(); i++) {
			result += commandStringList.get(i);
			if (i < commandStringList.size() - 1) result+="\n";
		}
		return result;
	}
}
