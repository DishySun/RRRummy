package gui_game;

import java.util.ArrayList;
import java.util.Random;

import gui.TablePane;
import javafx.scene.image.Image;

import java.util.Collections;

import players.Player;
import rrrummy.InvalidTileException;
import rrrummy.Tile;


@SuppressWarnings("restriction")
public class GameControl {
	private Game game;
	private ArrayList<Player> players;
	private ArrayList<Tile> tiles;
	private int currentPlayer;
	private TablePane view;
	
	public GameControl(ArrayList<Player> players, int firstHumanPlayer, ArrayList<Image> red, ArrayList<Image> blue, ArrayList<Image> green, ArrayList<Image> orange, Image joker, Image back) {
		//normal game at least one human player
		this.players = players;
		view = new TablePane(this,players, firstHumanPlayer,red,blue,green,orange,joker,back);
		initTiles();
		whichPlayerPlayFirst();
		game = new Game(players, tiles);
		initHand();
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
	public void cut(int meldIndex, int tileIndex) {
		if (game.cut(meldIndex, tileIndex)) view.cut(meldIndex,tileIndex);
		System.out.println("cut");
		
		game.printTable();
		players.get(currentPlayer).printHand();
	}



	public boolean replace(int tileInHandIndex, int meldIndex, int tileIndex) {
		boolean b = game.replace(currentPlayer, tileInHandIndex, meldIndex, tileIndex);
		if (b) view.replace(meldIndex, tileIndex);
		
		System.err.println("Replace"+ b);
		
		game.printTable();
		players.get(currentPlayer).printHand();
		return b;
	}



	public void play(int tileInHandIndex, int meldIndex, boolean headOrTail) {
		Tile t = players.get(currentPlayer).getHand(tileInHandIndex);
		switch(game.playerPlays(players.get(currentPlayer), tileInHandIndex, meldIndex, headOrTail)) {
		case 0:
			view.playerPlayed(currentPlayer, meldIndex, true,t);
			break;
		case 1:
			view.playerPlayed(currentPlayer, meldIndex, false,t);
			break;
		default: break;
		}
		System.err.println("play");
		game.printTable();
		players.get(currentPlayer).printHand();
	}

	public void play(int tileIndex) {
		Tile t = players.get(currentPlayer).getHand(tileIndex);
		if(game.playerPlays(players.get(currentPlayer), tileIndex)) view.playerPlayed(currentPlayer, t);
		game.printTable();
		players.get(currentPlayer).printHand();
	}

	public void move(int fromMeldIndex, int fromTileIndex, int toMeldIndex, boolean headOrTail) {
		System.err.println("move");
		int i = game.move(fromMeldIndex, fromTileIndex, toMeldIndex, headOrTail);
		System.out.println("Moving result: " + i);
		switch(i) {
		case 0:
			view.move(toMeldIndex, true);
			break;
		case 1:
			view.move(toMeldIndex, false);
			break;
		default:
			break;
		}
		game.printTable();
		players.get(currentPlayer).printHand();
	}
}
