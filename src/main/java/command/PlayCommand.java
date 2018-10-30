package command;

import players.Player;
import rrrummy.Tile;

public class PlayCommand implements Command{
	/* Correct Form:
	 * 		keyword		handIndex(s)	keyword		meldIndex
	 * 1. 	Play 		3				-			-							==> playerPlays(Tile t)
	 * 2.	Play		0 1 2			X			X							==> playerPlays(ArrayList<Tile> arr)
	 * 3.	Play		2				to			4							==> playerPlays(Tile t ,int toMeld)
	 * 4. 	Play		2				to 			4 				head
	 * */
	private Player player;
	
	public PlayCommand(String commandString, Player player) {
		this.player = player;
	}
	
	@Override
	public void excute() {
		// TODO Auto-generated method stub
		
	}
	

}
