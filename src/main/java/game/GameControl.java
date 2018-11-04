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
	}
	
	private void launch() {
		int gameOrReplay = view.getGameOrReplay();
		if (gameOrReplay == 1)newGame();
		else if (gameOrReplay == 2)gameReplay();
	}
	
	
	
	private void newGame() {
		initAiPlayers();
		game = new Game(players, view);
		game.startGame();
	}
	
	private void gameReplay() {
		game = new Game();
	}
	
	private void initAiPlayers() {
		String playerName = view.getPlayerName();
		players.add(new Player(playerName));
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
				break;
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
