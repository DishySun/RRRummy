package command;

import java.util.ArrayList;

import game.View;
import rrrummy.AbleToAddBothSideException;
import rrrummy.Game;

public class PlayCommand implements Command{
	/* Correct Form:
	 * 		keyword		handIndex(s)	keyword		meldIndex
	 * 1. 	Play 		3				-			-							==> playerPlays(Tile t)
	 * 2.	Play		0 1 2			X			X							==> playerPlays(ArrayList<Tile> arr)
	 * 3.	Play		2				to			4							==> playerPlays(Tile t ,int toMeld)
	 * 4. 	Play		2				to 			4 				head
	 * */
	private Game game;
	private View view;
	private int handIndex;
	private int meldIndex;
	private int headOrTail; // -1 for null, 0 for head, else for tail
	private ArrayList<Integer> arr;
	
	public PlayCommand(int h, Game g) {
		//play int
		handIndex = h;
		meldIndex = -1;
		headOrTail = -1;
		game = g;
		arr = null;
	}
	
	public PlayCommand(int h, int m, Game g , View v) {
		//play int to int
		handIndex = h;
		meldIndex = m;
		game = g;
		headOrTail  = -1;
		arr = null;
		view = v;
	}
	
	public PlayCommand(int h, int m, int b, Game g) {
		//play int to int HorT
		handIndex = h;
		meldIndex = m;
		headOrTail = b;
		game = g;
		arr = null;
	}
	
	public PlayCommand(ArrayList<Integer> a, Game g) {
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
			//game.playerPlays(int) /game.playerPlays(ArrayList)
			if (arr == null) return game.playerPlays(handIndex);
			else return game.playerPlays(arr);
		}else {
			//game.playerPlays(int,int) / game.playerPlays(int, int, boolean)
			switch (headOrTail) {
			case -1:	try{
							return game.playerPlays(handIndex, meldIndex);
						}catch(AbleToAddBothSideException e) {
							String str = view.getHeadOrTail("play");
							if(str.equalsIgnoreCase("head")) {
								headOrTail = 0;
								return game.playerPlays(handIndex, meldIndex, true);
							}
							else {
								headOrTail = 1;
								return game.playerPlays(handIndex, meldIndex, false);
							}
						}
			case 0:		return game.playerPlays(handIndex, meldIndex, true);
			default:	return game.playerPlays(handIndex, meldIndex, false);
			}
		}
	}
}
