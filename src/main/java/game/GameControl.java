package game;

import java.util.ArrayList;
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
	
	public GameControl(ArrayList<String> playersNameList, ArrayList<String> strategyList) {
		// TODO Auto-generated constructor stub
		players = new ArrayList<Player>();
		for(int i=0;i<playersNameList.size();i++) {
			String tempName = playersNameList.get(i);
			Player player = new Player(tempName);
			players.add(player);
		}
		if(strategyList != null && strategyList.size() != 0) {
			for(String s : strategyList) {
				switch(s) {
				case "1":
					players.add(new AI(new StrategyZero()));
					break;
				case "2":
					players.add(new AI(new StrategyTwo()));
					break;
				case "3":
					players.add(new AI(new StrategyThree()));
					break;
				case "4":
					//players.add(new AI(new StrategyFour()));
					break;
				default:
					players.add(new AI(new StrategyThree()));
				}
			}
		}
		view = new View();
	}

	public GameControl(ArrayList<String> playersNameList, ArrayList<String> strategyList,
				ArrayList<ArrayList<Tile>> handsTile) {
		// TODO Auto-generated constructor stub
		for(int i=0;i<playersNameList.size();i++) {
			Player player = new Player(playersNameList.get(i));
			players.add(player);
		}
		if(strategyList != null && strategyList.size() != 0) {
			for(String s : strategyList) {
				switch(s) {
				case "1":
					players.add(new AI(new StrategyZero()));
					break;
				case "2":
					players.add(new AI(new StrategyTwo()));
					break;
				case "3":
					players.add(new AI(new StrategyThree()));
					break;
				case "4":
					//players.add(new AI(new StrategyFour()));
					break;
				default:
					players.add(new AI(new StrategyThree()));
				}
			}
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
