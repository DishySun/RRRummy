package game;

import java.util.ArrayList;
import players.AI;
import players.Player;
import players.StrategyZero;
import rrrummy.Game;


public class GameControl {
	private ArrayList<Player> players;
	private Game game;
	private View view;
	
	
	public GameControl(int i) {
		
		view = new View();
		players = new ArrayList<Player>();
		players.add(new Player("Bill"));
		while (i >0) {
			players.add(new AI(new StrategyZero()));
			i--;
		}
	}
	
	public void launch() {
		newGame();
	}
	
	
	
	private void newGame() {
		game = new Game(players, view);
		game.startGame();
	}
	
	
	/*public boolean playerPlays(int playNumber, int handIndex, int toMeld) {
		
	}*/
	
	
	
	public static void main(String[] arg) {
		GameControl gc = new GameControl(3);
		gc.launch();
	}
	
}
