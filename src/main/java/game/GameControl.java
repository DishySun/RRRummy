package game;

import java.util.ArrayList;
import players.*;
import rrrummy.Game;


public class GameControl {
	private ArrayList<Player> players;
	private Game game;
	private View view;
	private GameData data;
	
	
	public GameControl(int aiNumber) {
		players = new ArrayList<Player>();
		view = new View();
		players = new ArrayList<Player>();
		players.add(new Player("Bill"));
		while (aiNumber >0) {
			players.add(new AI(new StrategyZero()));
			aiNumber--;
		}
		
	}
	
	public void launch() {
		newGame();
	}
	
	
	
	private void newGame() {
		game = new Game(players, view, data);
		game.startGame();
	}
	
	
	/*public boolean playerPlays(int playNumber, int handIndex, int toMeld) {
		
	}*/
	
	
	
	public static void main(String[] arg) {
		GameControl gc = new GameControl(3);
		gc.launch();
	}
	
}
