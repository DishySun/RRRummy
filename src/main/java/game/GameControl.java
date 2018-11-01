package game;

import java.util.ArrayList;

import observer.GameData;
import players.AI;
import players.Player;
import players.StrategyThree;
import players.StrategyTwo;
import players.StrategyZero;
import rrrummy.Game;


public class GameControl {
	private ArrayList<Player> players;
	private Game game;
	private View view;
	private GameData data;
	
	
	public GameControl(int i) {
		data = new GameData();
		view = new View();
		players = new ArrayList<Player>();
		players.add(new Player("Bill"));
		while (i >0) {
			if(i == 1)
				 players.add(new AI(new StrategyZero(data)));
			else if (i == 2)
				players.add(new AI(new StrategyTwo(data)));
			else if(i == 3)
				players.add(new AI(new StrategyThree(data)));
			i--;
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
