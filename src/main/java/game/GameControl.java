package game;

import java.util.ArrayList;
import players.*;
import rrrummy.Game;


public class GameControl {
	private ArrayList<Player> players;
	private Game game;
	private View view;
	
	
	public GameControl() {
		players = new ArrayList<Player>();
		view = new View();
		players = new ArrayList<Player>();
		String playerName = view.getPlayerName();
		players.add(new Player(playerName));
		initAiPlayers();
	}
	
	private void launch() {
		newGame();
	}
	
	
	
	private void newGame() {
		game = new Game(players, view);
		game.startGame();
	}
	
	private void initAiPlayers() {
		int aiNumber = view.getAINumber();
		if (aiNumber > 3 || aiNumber < 1) aiNumber = 3;
		for (int i = 1; i <= aiNumber; i++) {
			int difficulityLevel = view.getAiDifficulty(i);
			switch(difficulityLevel) {
			case 1:
				players.add(new AI(new StrategyZero()));
				break;
			case 2:
				players.add(new AI(new StrategyTwo()));
				break;
			case 3:
				players.add(new AI(new StrategyThree()));
			default:
				players.add(new AI(new StrategyThree()));
			}
		}
	}
	
	
	
	public static void main(String[] arg) {
		GameControl gc = new GameControl();
		gc.launch();
	}
	
}
