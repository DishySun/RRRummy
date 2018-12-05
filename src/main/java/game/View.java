package game;

import java.util.Scanner;

public class View {
	private static Scanner in;
	
	public View () {
		in = new Scanner(System.in);
	}
	public static String getCommand() {
		System.out.print("What would you like to do: ");
		String str = in.nextLine();
		return str;
	}
	public static String getHeadOrTail(String string) {
		System.out.print("Do you want to "+ string + " it to head or tail:");
		while(true) {
			String str = in.nextLine();
			if(str.equalsIgnoreCase("head") || str.equalsIgnoreCase("tail")) return str;	
			System.out.print("Plaese enter either 'head' or 'tail: '");
		}
	}
	public int getAiDifficulty(int num) {
		System.out.print("How difficult would you want Computer "+ num+ " to be (1-3): ");
		while (true) {
			String str = in.nextLine();
			try {
				int diff = Integer.valueOf(str);
				return diff;
			}catch (Exception e) {
				System.out.print("err: An integer input is expected: ");
			}
		}
	}
	
	public int getAINumber() {
		System.out.print("How many AI players would you like to have (1-3): ");
		while (true) {
			String str = in.nextLine();
			try {
				int aiNumber = Integer.valueOf(str);
				return aiNumber;
			}catch (Exception e) {
				System.out.print("err: An integer input is expected: ");
			}
		}
	}
	public String getPlayerName() {
		System.out.print("Welcome to COMP 3004 RRRummy.\nHow may I call you: ");
		String name = in.nextLine();
		System.out.println("Nice to meet you, " + name +"!");
		return name;
	}
	public void startGameAnnounce(String name) {
		System.out.println("The game starts!");
		System.out.println(name +", you are the first player. Now it is your turn!");
	}
	public void endTurnAlert(String name) {
		System.out.println(name+" choose to end his turn.");
	}
	public void startTurnAlert(String name) {
		System.out.println("\n==============================");
		System.out.println("\n"+ name +", you are the next.");
	}
	public void announceWinner(String name) {
		System.out.println("The winner is "+ name+"!");
	}
	public boolean saveReplay() {
		System.out.print ("Do you want to save the replay for this game? (yes/no)");
		while(true) {
			String str = in.nextLine().toLowerCase();
			if (str.equals("yes")|| str.equals("y")) return true;
			if (str.equals("no") || str.equals("n")) return false;
		}
	}
	public int getGameOrReplay() {
		System.out.print ("Do you want to Play a new game(1) or Watch the replay(2) of pervious game? ");
		String str = null;
		while (true) {
			str = in.nextLine();
			if (str.equals("1") || str.equalsIgnoreCase("p")) return 1;
			else if (str.equals("2") || str.equalsIgnoreCase("w")) return 2;
			System.out.print("enter again: ");
		}
	}
	public void stateCommandResult(int newCommand) {
		switch (newCommand) {
		case -1: 
			System.out.print("Invalid Command entered.");
			break;
		case 0: 
			System.out.print("Fail to execute.");
			break;
		case 1:
			System.out.print("Successfully executed.");
			break;
		default:
			System.out.print("error happened.");
		}
		
	}
	
	public String getStockFile() {
		System.out.println("Please enter stock file name");
		String stockFile = in.nextLine();
		return stockFile;
	}
	
	public String getCommandFile() {
		System.out.println("Please enter command file name");
		String commandFile = in.nextLine();
		return commandFile;
	}
	
}
