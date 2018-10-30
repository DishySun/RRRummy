package game;

import java.util.ArrayList;
import java.util.Scanner;

import command.CommandControl;
import players.AI;
import players.Player;
import players.StrategyZero;
import rrrummy.Game;
import rrrummy.InvalidTileException;
import rrrummy.Tile;

public class GameControl {
	final static int INIT_HAND_SIZE = 14;
	private ArrayList<Player> players;
	private int currentPlayer;
	private Game game;
	private CommandControl commandControl;
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
		currentPlayer = 0;
		try {
			game = new Game();
		}catch (InvalidTileException e) {
			e.getStackTrace();
			System.exit(-1);
		}
		commandControl = new CommandControl();
		initPlayerHand();
		while(true) {
			String str = players.get(currentPlayer).getCommandString(view);
			commandControl.newCommand(this, str, players.get(currentPlayer));
		}
	}
	
	private void initPlayerHand() {
		for (Player p : players) {
			ArrayList<Tile> hand = new ArrayList<Tile>();
			for (int i = 0; i < INIT_HAND_SIZE; i++) {
				hand.add(game.playerDraw());
			}
			p.initHand(hand);
		}
	}
	
	/*public boolean playerPlays(int playNumber, int handIndex, int toMeld) {
		
	}*/
	
	public void drawAndEndTurn() {
		players.get(currentPlayer).draw(game.playerDraw());
		players.get(currentPlayer).printHand();
		currentPlayer = (currentPlayer+1) % players.size();
		game.printTable();
	}
	
	public void playerEndTurn() {
		currentPlayer = (currentPlayer+1) % players.size();
	}
	
	public static void main(String[] arg) {
		GameControl gc = new GameControl(3);
		gc.launch();
	}
	
}
