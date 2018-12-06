package game;

import java.util.ArrayList;
import java.util.Stack;

import players.*;
import rrrummy.Game;
import rrrummy.Tile;


public class GameControl {
	private ArrayList<Player> players;
	private Game game;
	private View view;
	
	
	public GameControl() {
		players = new ArrayList<Player>();
		view = new View();
	}
	
	public GameControl(ArrayList<Player> players ) {
		// TODO Auto-generated constructor stub
		for(Player p : players)
			System.out.println(p.getName());
	}

	public GameControl(ArrayList<Player> players, ArrayList<ArrayList<Tile>> handsTile, Stack<Tile> stockRig) {
		// TODO Auto-generated constructor stub
		for(Player p : players) {
			System.out.println(p.getName());
			System.out.println(handsTile);
			System.out.println(stockRig);
		}
	}

	private void launch() {
		int gameOrReplay = view.getGameOrReplay();
		if (gameOrReplay == 1)newGame();
		else if (gameOrReplay == 2)gameReplay();
	}
	
	
	
	public void newGame() {
		initAiPlayers();
		//game = new Game(players, view);
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
