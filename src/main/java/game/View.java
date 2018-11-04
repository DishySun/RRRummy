package game;

import java.util.Scanner;

public class View {
	private Scanner in;
	
	public View () {
		in = new Scanner(System.in);
	}
	public String getCommand() {
		System.out.print("What would you like to do: ");
		String str = in.nextLine();
		return str;
	}
	public String getHeadOrTail(String string) {
		System.out.println("Do you want to "+ string + " it to head or tail:");
		while(true) {
			String str = in.nextLine();
			if(str.equalsIgnoreCase("head") || str.equalsIgnoreCase("tail")) return str;	
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
		System.out.println("\n==============================");
	}
	public void startTurnAlert(String name) {
		System.out.println("\n"+ name +" , you are the next.");
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
	
}
