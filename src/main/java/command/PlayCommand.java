package command;

import java.util.ArrayList;

import gui_game.GameControl;

public class PlayCommand implements Command{
	/* Correct Form:
	 * 		keyword		handIndex(s)	keyword		meldIndex
	 * 1. 	Play 		3				-			-							==> aiPlays(Tile t)
	 * 2.	Play		0 1 2			X			X							==> aiPlays(ArrayList<Tile> arr)
	 * 3.	Play		2				to			4							==> aiPlays(Tile t ,int toMeld)
	 * 4. 	Play		2				to 			4 				head
	 * */
	private GameControl game;
	private int handIndex;
	private int meldIndex;
	private int headOrTail; // -1 for null, 0 for head, else for tail
	private ArrayList<Integer> arr;
	
	public PlayCommand(int h, GameControl g) {
		//play int
		handIndex = h;
		meldIndex = -1;
		headOrTail = -1;
		game = g;
		arr = null;
	}
	
	public PlayCommand(int h, int m, GameControl g) {
		//play int to int
		handIndex = h;
		meldIndex = m;
		game = g;
		headOrTail  = -1;
		arr = null;
	}
	
	public PlayCommand(int h, int m, int b, GameControl g) {
		//play int to int HorT
		handIndex = h;
		meldIndex = m;
		headOrTail = b;
		game = g;
		arr = null;
	}
	
	public PlayCommand(ArrayList<Integer> a, GameControl g) {
		//play arraylist
		handIndex = -1;
		meldIndex = -1;
		headOrTail = -1;
		arr = a;
		game = g;
	}
	
	@Override
	public boolean excute(){
		if (meldIndex == -1) {
			//game.commandPlayes(int) /game.commandPlayes(ArrayList)
			if (arr == null) return game.commandPlays(handIndex);
			else return game.commandPlays(arr);
		}else {
			//game.commandPlayes(int,int) / game.commandPlayes(int, int, boolean)
			switch (headOrTail) {
			case -1:	return game.commandPlays(handIndex, meldIndex);
			case 0:		return game.commandPlays(handIndex, meldIndex, true);
			default:	return game.commandPlays(handIndex, meldIndex, false);
			}
		}
	}
}
