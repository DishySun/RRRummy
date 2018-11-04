package rrrummy;

import java.util.ArrayList;
import java.util.Collections;

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
	
	public Game(ArrayList<Player> ps, View v){
		this.table = new Table();
		this.stock = new Stock();
		players = ps;
		view = v;
		commandControl = new CommandControl(view);
		//set players to random sits
		
		currentPlayer = 0;
		registerObservers();
		Collections.shuffle(players);
	}
	
	public Game(ArrayList<Player> ps, ArrayList<Tile> fileStock, View v){
		this.table = new Table();
		this.stock = new Stock(fileStock);
		players = ps;
		view = v;
		commandControl = new CommandControl(view);
		currentPlayer = 0;
	}
	
	public void registerObservers() {
		for (Player p : players) {
			if (p.getStrategy() == null) continue;
			table.register(p.getStrategy());
			for (Player pp: players) {
				pp.register(p.getStrategy());
			}
		}
		table.notifyObserver();
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
	
	public boolean playerDraw() {
		Tile t = stock.draw();
		if (t == null) return false;
		players.get(currentPlayer).draw(t);
		return true;
	}
	
	public boolean playerPlays(int playerHandIndex) {
		//play a single Tile to new meld
		if(players.get(currentPlayer).getHand(playerHandIndex) == null) return false;
		table.add(players.get(currentPlayer).play(playerHandIndex));
		hasPlayed++;
		return true;
	}
	
	public boolean playerPlays(ArrayList<Integer> playerHandIndexs) {
		//play an ArrayList of Tiles to new meld
		ArrayList<Tile> arr = new ArrayList<Tile>();
		
		for (Integer i : playerHandIndexs) {
			Tile t =players.get(currentPlayer).getHand(i);
			if(t == null) return false;
			arr.add(t);
		}
		if(!table.add(arr)) return false;
		for (Tile t : arr) {
			int i = players.get(currentPlayer).getHand(t);
			players.get(currentPlayer).play(i);
		}
		hasPlayed++;
		return true;
		
	}
	public boolean playerPlays(int playerHandIndex ,int toMeldIndex) throws AbleToAddBothSideException {
		//play a Tile to specific meld
		/*
		 * headOrTail; -1 => add(); 0 => addHead;  other positive number => add(Tail)
		 * */
		Tile t = players.get(currentPlayer).getHand(playerHandIndex);
		if (t == null) return false;
		if (!table.add(t, toMeldIndex)) return false;
		players.get(currentPlayer).play(playerHandIndex);
		hasPlayed++;
		return true;
	}
	public boolean playerPlays(int playerHandIndex ,int toMeldIndex, boolean headOrTail) {
		//headOrTail: 	true for head
		//				false for tail
		Tile t = players.get(currentPlayer).play(playerHandIndex);
		if (t == null) {
			players.get(currentPlayer).draw(t);
			return false;
		}
		boolean b = false;
		if (headOrTail) b = table.addHead(t, toMeldIndex);
		else b = table.addTail(t, toMeldIndex);
		return b;
	}
	
	public boolean move (int fromMeld, boolean removeHeadOrTail, int toMeld) throws AbleToAddBothSideException{
		//removeHeadOrTail 0 for head, others for tail
		/*
		 * headOrTail: true for head, false for tail
		 * */
		return table.move(fromMeld, removeHeadOrTail, toMeld);
		/*if(fromMeld >= table.size() || fromMeld < 0 || toMeld < 0 || toMeld >= table.size()) return false;
		Tile t = null;
		if (removeHeadOrTail) t = table.removeHead(fromMeld);
		else t = table.removeTail(fromMeld);
		boolean b = false;
		try {
			b = table.add(t, toMeld);
		}catch (AbleToAddBothSideException e) {
			if (removeHeadOrTail)  table.addHead(t, fromMeld);
			else table.addTail(t, fromMeld);
			throw new AbleToAddBothSideException(null, null);
		}
		if(!b) {
			if (removeHeadOrTail)  table.addHead(t, fromMeld);
			else table.addTail(t, fromMeld);
			return false;
		}
		return true;*/
	}
	
	public boolean move (int fromMeld, boolean removeHeadOrTail, int toMeld, boolean toHeadOrTail) {
		return table.move (fromMeld, removeHeadOrTail, toMeld, toHeadOrTail);
		/*if(fromMeld >= table.size() || fromMeld < 0 || toMeld < 0 || toMeld >= table.size()) return false;
		Tile t = null;
		if (removeHeadOrTail) t = table.removeHead(fromMeld);
		else t = table.removeTail(fromMeld);
		boolean b = false;
		if (toHeadOrTail) b = table.addHead(t, toMeld);
		else b = table.addTail(t, toMeld);
		if (!b) {
			if(removeHeadOrTail) table.addHead(t, fromMeld);
			else table.addTail(t, fromMeld);
			return false;
		}
		return b;*/
	}
	
	
	public boolean cut(int meldIndex, int at) {
		if (meldIndex < 0 || meldIndex >= table.size()) return false;
		return table.cut(meldIndex, at);
	}
	public boolean replace(int playerHandIndex, int tableIndex, int meldIndex) {
		Tile t = players.get(currentPlayer).getHand(playerHandIndex);
		if(t == null) return false;
		Tile t2 =  table.replace(t, tableIndex, meldIndex);
		if (t2 == null) {
			players.get(currentPlayer).draw(t);
			return false;
		}
		table.add(t2);
		return true;
	}
	public void endTurn() {
		view.endTurnAlert(players.get(currentPlayer).getName());
		if(hasPlayed == 0) this.playerDraw();
		currentPlayer = (currentPlayer + 1) % players.size();
		hasPlayed = 0;
		view.startTurnAlert(players.get(currentPlayer).getName());
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
		view.startGameAnnounce(players.get(currentPlayer).getName());
		Player winner = null;
		while (winner == null) {
			System.out.println(table);
			System.out.println("Stock Left: "+stock.size());
			players.get(currentPlayer).printHand();
			String str = players.get(currentPlayer).getCommandString(view);
			commandControl.newCommand(this, str);
			winner = determineWinner();
		}
		System.out.println("The winner is: "+ winner.getName());
		return winner;
	}
	
	public void printTable() {
		System.out.println(table);
	}
	public int stockSize() {return stock.size();}
	public int tableSize() {return table.size();}
}
