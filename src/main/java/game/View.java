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
}
