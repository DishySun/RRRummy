package gui_game;

import java.util.ArrayList;

import rrrummy.*;
import players.Player;

public class Game {
	private Table table;
	private Stock stock;
	private ArrayList<Player> players;
	private ArrayList<Tile> tiles;
	
	public Game(final ArrayList<Player> players, final ArrayList<Tile> tiles) {
		this.tiles = new ArrayList<Tile>(tiles);
		table = new Table();
		stock = new Stock(this.tiles);
		this.players = new ArrayList<Player>(players);
		this.registerObservers();
	}
	
	private void registerObservers() {
		for (Player p : players) {
			if (p.getStrategy() == null) continue;
			table.register(p.getStrategy());
			for (Player pp: players) {
				pp.register(p.getStrategy());
			}
		}
		table.notifyObserver();
	}
	
	public ArrayList<Integer> initHand(Player p, int initTileNumber, ArrayList<Tile> initHand) {
		if (stock.size() < initTileNumber - initHand.size()) {
			System.err.println("out of stock while initHand");
			System.exit(-1);
		}
		while(initHand.size() < initTileNumber) {
			initHand.add(stock.draw());
		}
		return p.initHand(initHand);
	}
	
	public boolean playerDraw(Player p, Tile drewTile) {
		drewTile = stock.draw();
		if (drewTile == null) return false;
		p.draw(drewTile);
		return true;
	}
	
	public boolean playerDraw(Player p, int number, ArrayList<Tile> tiles) {
		//draw more than one tiles
		if (tiles.size() > 0) {
			System.err.println("player draw: non-empty ArrayList was passed in");
			tiles.clear();
		}
		for (int i = 0; i < number; i++) {
			Tile t = stock.draw();
			if (t == null) {
				//put back
				while (tiles.size()>0) {
					stock.put(tiles.remove(tiles.size() - 1));
				}
				return false;
			}
			tiles.add(t);
		}
		for (Tile t : tiles) {
			p.draw(t);
		}
		return true;
	}
	
	public boolean playerPlays(Player p, int playerHandIndex) {
		//play a single Tile to new meld
		if(p.getHand(playerHandIndex) == null) return false;
		table.add(p.play(playerHandIndex));
		return true;
	}
	
	public int playerPlays(Player p, int playerHandIndex ,int toMeldIndex, boolean headOrTail) {
		//headOrTail: 	true for head
		//				false for tail
		// return: 	-1 for fail adding
		//			0 for added to head
		//			1 for added to tail
		Tile t = p.getHand(playerHandIndex);
		if (t == null) {
			return -1;
		}
		int i;
		
		if (headOrTail) {
			i = table.addHead(t, toMeldIndex);
		}else {
			i = table.addTail(t, toMeldIndex);
		}
		if (i >= 0) p.play(playerHandIndex);
		return i;
	}
	
	public int move (int fromMeld, boolean removeHeadOrTail, int toMeld, boolean toHeadOrTail) {
		int i = table.move (fromMeld, removeHeadOrTail, toMeld, toHeadOrTail);
		System.out.println("normal move: " + i);
		return i;
	}
	public int move(int fromMeldIndex, int fromTileIndex, int toMeldIndex, boolean headOrTail) {
		int i = table.move(fromMeldIndex, fromTileIndex, toMeldIndex, headOrTail);
		System.out.println("move from set: " + i);
		return i;
	}
	
	public boolean cut(int meldIndex, int at) {
		if (meldIndex < 0 || meldIndex >= table.size()) return false;
		return table.cut(meldIndex, at);
	}
	
	
	public boolean replace(int playerIndex, int playerHandIndex, int tableIndex, int meldIndex) {
		//replace from hand
		Tile t = players.get(playerIndex).getHand(playerHandIndex);
		if(t == null) return false;
		Tile t2 =  table.replace(t, tableIndex, meldIndex);
		if (t2 == null) return false;
		players.get(playerIndex).play(playerHandIndex);
		table.add(t2);
		return true;
	}
	
	public boolean isSet(int meldIndex) {
		return table.isSet(meldIndex);
	}

	public void printTable() {
		System.out.println(table);
	}
	
}