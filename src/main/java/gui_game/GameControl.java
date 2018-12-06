package gui_game;

import java.util.ArrayList;
import java.util.Random;

import gui.TablePane;
import javafx.scene.image.Image;

import java.util.Collections;

import players.Player;
import rrrummy.InvalidTileException;
import rrrummy.Tile;
import command.*;


@SuppressWarnings("restriction")
public class GameControl {
	private Game game;
	private ArrayList<Player> players;
	private ArrayList<Tile> tiles;
	private int currentPlayer;
	private TablePane view;
	private CommandControl commandControl;
	private int hadPlayed = 0;
	
	public GameControl(ArrayList<Player> players, int firstHumanPlayer, ArrayList<Image> red, ArrayList<Image> blue, ArrayList<Image> green, ArrayList<Image> orange, Image joker, Image back) {
		//normal game at least one human player
		this.players = players;
		commandControl= new CommandControl(this);
		view = new TablePane(this,players, firstHumanPlayer,red,blue,green,orange,joker,back);
		initTiles();
		whichPlayerPlayFirst();
		game = new Game(players, tiles);
		initHand();
		players.get(currentPlayer).getTurn(this);
	}
	
	private void initHand() {
		for (Player p: players) {
			ArrayList<Tile> tiles = new ArrayList<Tile>();
			ArrayList<Integer> order = game.initHand(p, 14, tiles);
			view.initHand(players.indexOf(p), tiles, order);
			p.printHand();
		}
		
	}
	
	private void whichPlayerPlayFirst() {
		int max = 0;
		Random random = new Random();
		ArrayList<Tile> pickTiles = new ArrayList<Tile>(tiles);
		Collections.shuffle(pickTiles);
		for (int i = 0; i < players.size(); i++) {
			int a = random.nextInt(pickTiles.size());
			int temp = pickTiles.remove(a).getNumber();
			if (temp > max) {
				max = temp;
				currentPlayer = i;
			}
		}
		//TODO:delet this line after testing!!!
		currentPlayer = 2;
	}

	private void initTiles() {
		tiles = new ArrayList<Tile>();
		for (Tile.Color c: Tile.Color.values()) {
			if (c == Tile.Color.JOKER) continue;
			for (int i = 1; i<=13; i++) {
				try {
					tiles.add(new Tile(c,i));
					tiles.add(new Tile(c,i));
				} catch (InvalidTileException e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		}
		tiles.add(new Tile());
		tiles.add(new Tile());
		Collections.shuffle(tiles);
	}



	public boolean isSet(int meldIndex) {
		return game.isSet(meldIndex);
	}
	
	public TablePane getTablePane() {return view;}

	
	//TODO:game logic command needed later!
	public boolean cut(int meldIndex, int tileIndex) {
		return commandControl.playerCommand(new CutCommand(meldIndex, tileIndex, this));
	}



	public boolean replace(int tileInHandIndex, int meldIndex, int tileIndex) {
		return commandControl.playerCommand(new ReplaceCommand(tileInHandIndex,meldIndex,tileIndex,this));
	}



	public void play(int tileInHandIndex, int meldIndex, boolean headOrTail) {
		if (headOrTail) commandControl.playerCommand(new PlayCommand(tileInHandIndex, meldIndex, 0, this));
		else commandControl.playerCommand(new PlayCommand(tileInHandIndex, meldIndex, 1, this));
	}

	public void play(int tileIndex) {
		commandControl.playerCommand(new PlayCommand(tileIndex, this));
	}

	public void move(int fromMeldIndex, int fromTileIndex, int toMeldIndex, boolean headOrTail) {
		int i;
		if (headOrTail) i = 0;
		else i = 1;
		commandControl.playerCommand(new MoveCommand(fromMeldIndex, fromTileIndex, toMeldIndex, i, this));
	}
	
	public void endTurn() {
		commandControl.playerCommand(new EndTurnCommand(this));
	}
	
	public void commandEndTurn() {
		Tile t = null;
		
		if (hadPlayed == 0) {
			t = game.playerDraw(players.get(currentPlayer));
			ArrayList<Integer> order = players.get(currentPlayer).sortHand();
			if (t != null) view.drawTile(currentPlayer, t, order);
		}
		
		currentPlayer = (currentPlayer +1) % players.size();
		hadPlayed = 0;
		players.get(currentPlayer).getTurn(this);
	}
	
	//model method
	public void humanTurn() {
		view.setHumanTurn(currentPlayer);
	}

	public void aiTurn() {
		view.setAiTurn();
		while(true) {
			String command = players.get(currentPlayer).getCommandString();
			if (command.equalsIgnoreCase("end")) break;
			commandControl.newCommand(command);
		}
		this.endTurn();
	}

	//command method
	public boolean commandPlays(int handIndex) {
		Tile t = players.get(currentPlayer).getHand(handIndex);
		boolean b = game.playerPlays(players.get(currentPlayer), handIndex);
		if (b) {
			view.playerPlayed(currentPlayer, t);
			hadPlayed++;
		}
		game.printTable();
		players.get(currentPlayer).printHand();
		return b;
		//return game.playerPlays(players.get(currentPlayer), handIndex);
	}

	public boolean commandPlays(ArrayList<Integer> arr) {
		ArrayList<Tile> tilesPlayed = new ArrayList<Tile>();
		for (int i : arr) {
			Tile t = players.get(currentPlayer).getHand(i);;
			tilesPlayed.add(t);
		}
		boolean b = game.playerPlays(players.get(currentPlayer), arr);
		if (b) {
			int index = game.tableSize()-1;
			for (Tile t : tilesPlayed) {
				view.playerPlayed(currentPlayer,index, false,t);
			}
			hadPlayed++;
		}
		return b;
	}

	public boolean commandPlays(int handIndex, int meldIndex) {
		Tile t = players.get(currentPlayer).getHand(handIndex);
		switch(game.playerPlays(players.get(currentPlayer), handIndex, meldIndex, false)) {
		case 0:
			view.playerPlayed(currentPlayer, meldIndex, true,t);
			break;
		case 1:
			view.playerPlayed(currentPlayer, meldIndex, false,t);
			break;
		default: 
			return false;
		}
		System.err.println("play");
		game.printTable();
		players.get(currentPlayer).printHand();
		hadPlayed++;
		
		//if ( game.playerPlays(players.get(currentPlayer), handIndex, meldIndex, false) >=0) return true;
		return true;
	}

	public boolean commandPlays(int handIndex, int meldIndex, boolean b) {
		Tile t = players.get(currentPlayer).getHand(handIndex);
		switch(game.playerPlays(players.get(currentPlayer), handIndex, meldIndex, b)) {
		case 0:
			view.playerPlayed(currentPlayer, meldIndex, true,t);
			break;
		case 1:
			view.playerPlayed(currentPlayer, meldIndex, false,t);
			break;
		default:
			return false;
		}
		hadPlayed++;
		System.err.println("play");
		game.printTable();
		players.get(currentPlayer).printHand();
		return true;
	}
	

	public boolean commandReplace(int tileInHandIndex, int meldIndex, int tileIndex) {
		boolean b = game.replace(currentPlayer, tileInHandIndex, meldIndex, tileIndex);
		if (b) view.replace(meldIndex, tileIndex);
		
		System.err.println("Replace"+ b);
		
		game.printTable();
		players.get(currentPlayer).printHand();
		return b;
	}

	public boolean commandMove(int fromMeldIndex, int fromMeldHoT, int toMeldIndex, boolean toHoT) {
		int i = game.move(fromMeldIndex, fromMeldHoT, toMeldIndex, toHoT);
		switch(i) {
		case 0:
			view.move(toMeldIndex, true);
			break;
		case 1:
			view.move(toMeldIndex, false);
			break;
		default:
			return false;
		}
		game.printTable();
		players.get(currentPlayer).printHand();
		return true;
	}

	public boolean commandCut(int meldIndex, int tileIndex) {
		if (game.cut(meldIndex, tileIndex)) {
			view.cut(meldIndex,tileIndex);
			return true;
		}
		
		game.printTable();
		players.get(currentPlayer).printHand();
		return false;
	}
}
