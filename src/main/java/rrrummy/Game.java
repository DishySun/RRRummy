package rrrummy;

import java.util.ArrayList;

import command.CommandControl;
import game.View;
import players.Player;

public class Game {
	private static final int INIT_HAND_SIZE = 14;
	private ArrayList<Player> players;
	private int currentPlayer;
	private int hasPlayed;
	private Table table;
	private Stock stock;
	private View view;
	private CommandControl commandControl;
	
	public Game(ArrayList<Player> ps, View v) throws InvalidTileException{
		this.table = new Table();
		this.stock = new Stock();
		players = ps;
		view = v;
		commandControl = new CommandControl();
	}
	private void initPlayersHand() {
		for (Player p: players) {
			ArrayList<Tile> h = new ArrayList<Tile>();
			for(int i = 0; i < INIT_HAND_SIZE; i++) {
				h.add(stock.draw());
			}
			p.initHand(h);
		}
	}
	
	public boolean playerDraw(int playerIndex) {
		Tile t = stock.draw();
		if (t == null) return false;
		players.get(playerIndex).draw(t);
		return true;
	}
	
	public boolean playerPlays(int playerIndex, int playerHandIndex) {
		//play a single Tile to new meld
		if(players.get(playerIndex).getHand(playerHandIndex) == null) return false;
		table.add(players.get(playerIndex).play(playerHandIndex));
		hasPlayed++;
		return true;
	}
	
	public boolean playerPlays(int playerIndex,ArrayList<Integer> playerHandIndexs) {
		//play an ArrayList of Tiles to new meld
		ArrayList<Tile> arr = new ArrayList<Tile>();
		for (Integer i : playerHandIndexs) {
			if(players.get(playerIndex).getHand(i) == null) return false;
		}
		for (Integer i : playerHandIndexs) {
			arr.add(players.get(playerIndex).play(i));
		}
		if(!table.add(arr)) return false;
		hasPlayed++;
		return true;
		
	}
	public boolean playerPlays(int playerIndex, int playerHandIndex ,int toMeldIndex, int headOrTail) throws AbleToAddBothSideException {
		//play a Tile to specific meld
		/*
		 * headOrTail; -1 => add(); 0 => addHead;  other positive number => add(Tail)
		 * */
		Tile t = players.get(playerIndex).getHand(playerHandIndex);
		if (t == null) return false;
		boolean b = false;
		switch(headOrTail) {
		case -1: 	b =  table.add(t, toMeldIndex);
					break;
		case 0:		b = table.addHead(t, toMeldIndex);
					break;
		default: 	b =  table.addTail(t, toMeldIndex);
					break;
		}
		if (b) hasPlayed++;
		return b;
	}
	
	public boolean move (int fromMeld, int removeHeadOrTail, int toMeld, int headOrTail) throws AbleToAddBothSideException{
		//removeHeadOrTail 0 for head, others for tail
		/*
		 * headOrTail; -1 => add(); 0 => addHead;  other positive number => add(Tail)
		 * */
		if(fromMeld >= table.size() || fromMeld < 0 || toMeld < 0 || toMeld >= table.size()) return false;
		Tile t = null;
		if (removeHeadOrTail == 0) t = table.removeHead(fromMeld);
		else t = table.removeTail(fromMeld);
		switch(headOrTail) {
		case -1: 	return table.add(t, toMeld);
		case 0:		return table.addHead(t, toMeld);
		default: 	return table.addTail(t, toMeld);
		}
	}
	public boolean cut(int meldIndex, int at) {
		if (meldIndex < 0 || meldIndex >= table.size()) return false;
		hasPlayed++;
		return table.cut(meldIndex, at);
	}
	public boolean replace(int playerIndex, int playerHandIndex, int tableIndex, int meldIndex) {
		Tile t = players.get(playerIndex).getHand(playerHandIndex);
		if(t == null) return false;
		Tile t2 =  table.replace(t, tableIndex, meldIndex);
		if (t2 == null)return false;
		table.add(t2);
		return true;
	}
	public void endTurn(int playerIndex) {
		if(hasPlayed == 0) this.playerDraw(playerIndex);
		currentPlayer = (currentPlayer + 1) % players.size();
		hasPlayed = 0;
	}
	
	private Player determineWinner() {
		Player winner = players.get(currentPlayer);
		int i = (currentPlayer+1) % players.size();
		while (i != currentPlayer) {
			if (players.get(i).handSize() == 0) return players.get(i);
			// for case of running out of stock
			if (players.get(i).handSize() < winner.handSize()) winner = players.get(i);
			i = (i+1) % players.size();
		}
		if (stock.size() == 0) return winner;
		return null;
	}
	
	public Player startGame() {
		//the winner will be returned
		initPlayersHand();
		currentPlayer = 0; 
		hasPlayed = 0;
		Player winner = null;
		while (winner == null) {
			String str = players.get(currentPlayer).getCommandString(view);
			commandControl.newCommand(this, str, players.get(currentPlayer));
			winner = determineWinner();
		}
		return winner;
	}
	
	public void printTable() {
		System.out.println(table);
	}
}
