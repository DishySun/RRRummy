package game;

import java.util.Scanner;

public class View {
	private Scanner in;
	
	public View () {
		in = new Scanner(System.in);
	}
	public String getCommand() {
		System.out.print("What would you like to do: ");
		String str = in.next();
		return str;
	}
}
