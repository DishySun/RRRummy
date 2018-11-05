package rrrummy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import command.CommandControl;
import game.View;
import players.AI;
import players.Player;
import players.StrategyFile;

public class Game {
	private final int INIT_HAND_SIZE = 14;
	private final String ROOT_PATH = "src/main/replays/";
	private ArrayList<Player> players;
	private int currentPlayer;
	private int hasPlayed;
	private Table table;
	private Stock stock;
	private View view;
	private CommandControl commandControl;
	private String initStock;
	private Player winner;
	
	public Game(ArrayList<Player> ps, View v){
		this.table = new Table();
		this.stock = new Stock();
		initStock = stock.replay();
		players = ps;
		view = v;
		commandControl = new CommandControl(view,this);
		//set players to random sits
		
		currentPlayer = 0;
		registerObservers();
		//Collections.shuffle(players);
	}
	
	public Game(ArrayList<Player> ps, ArrayList<Tile> fileStock, View v){
		this.table = new Table();
		this.stock = new Stock(fileStock);
		players = ps;
		view = v;
		commandControl = new CommandControl(view,this);
		currentPlayer = 0;
	}
	
	public Game() {
		players = new ArrayList<Player>();
		table = new Table();
		currentPlayer = 0;
		commandControl = new CommandControl(view,this);
		view = new View();
		playReplay();
		
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
		System.out.println(players.get(currentPlayer).getName() +" has drawn "+t.toString());
		players.get(currentPlayer).draw(t);
		return true;
	}
	
	public boolean playerPlays(int playerHandIndex) {
		//play a single Tile to new meld
		if(players.get(currentPlayer).getHand(playerHandIndex) == null) return false;
		System.out.println(players.get(currentPlayer).getName() +" has played "+players.get(currentPlayer).getHand(playerHandIndex).toString() +" as a new meld.");
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
		System.out.println(players.get(currentPlayer).getName() +" has played "+arr.toString()+" as a new meld.");
		players.get(currentPlayer).caluPlayedScore(table.lastMeldScore());
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
		System.out.println(players.get(currentPlayer).getName()+ " has played "+players.get(currentPlayer).play(playerHandIndex) + "to meld" +toMeldIndex);
		hasPlayed++;
		return true;
	}
	
	public boolean playerPlays(int playerHandIndex ,int toMeldIndex, boolean headOrTail) {
		//headOrTail: 	true for head
		//				false for tail
		Tile t = players.get(currentPlayer).getHand(playerHandIndex);
		if (t == null) {
			return false;
		}
		boolean b = false;
		if (headOrTail) {
			b = table.addHead(t, toMeldIndex);
		}
		else {
			b = table.addTail(t, toMeldIndex);
		}
		if (b) {
			System.out.print(players.get(currentPlayer).getName()+ " has played "+players.get(currentPlayer).play(playerHandIndex));
			if (headOrTail) System.out.println("to the head of meld" +toMeldIndex);
			else System.out.println("to the tail of meld" +toMeldIndex);
		}
		return b;
	}
	
	public boolean move (int fromMeld, boolean removeHeadOrTail, int toMeld) throws AbleToAddBothSideException{
		return table.move(fromMeld, removeHeadOrTail, toMeld);
	}
	
	public boolean move (int fromMeld, boolean removeHeadOrTail, int toMeld, boolean toHeadOrTail) {
		return table.move (fromMeld, removeHeadOrTail, toMeld, toHeadOrTail);
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
		if (players.get(currentPlayer).getPlayerdSocre() < 30 && players.get(currentPlayer).getPlayerdSocre() > 0) {
			System.out.println("The total score of melds you have played for first turn is less than 30. You can not end you turn.");
			return;
		}
		view.endTurnAlert(players.get(currentPlayer).getName());
		if(hasPlayed == 0) this.playerDraw();
		currentPlayer = (currentPlayer + 1) % players.size();
		hasPlayed = 0;
		determineWinner();
		if (winner != null) return;
		view.startTurnAlert(players.get(currentPlayer).getName());
	}
	
	private void determineWinner() {
		winner = players.get(currentPlayer);
		int i = (currentPlayer+1) % players.size();
		if (players.get(currentPlayer).handSize() == 0) {
			winner = players.get(currentPlayer);
			return;
		}
		while (i != currentPlayer) {
			if (players.get(i).handSize() == 0) {
				
				winner = players.get(i);
				return;
			}
			// for case of running out of stock
			if (players.get(i).handSize() < winner.handSize()) winner = players.get(i);
			i = (i+1) % players.size();
		}
		if (stock.size() == 0) return;
		winner = null;
	}
	
	public Player startGame() {
		//the winner will be returned
		initPlayersHand();
		currentPlayer = 0; 
		hasPlayed = 0;
		view.startGameAnnounce(players.get(currentPlayer).getName());
		while (winner == null) {
			System.out.println(table);
			System.out.println("Stock Left: "+stock.size());
			players.get(currentPlayer).printHand();
			String str = players.get(currentPlayer).getCommandString(view);
			view.stateCommandResult(commandControl.newCommand(str));
			System.out.println();
		}
		view.announceWinner(winner.getName());
		generateReplayFile();
		return winner;
	}
	
	private void generateReplayFile() {
		if(view.saveReplay()) {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(ROOT_PATH +"stock.txt"));
				writer.write(initStock);
			    writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(ROOT_PATH +"command.txt"));
				writer.write(players.size()+"\n"+commandControl.toString());
			    writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void playReplay() {
		String stockString = null;
		System.out.println("Please enter stock file name");
		Scanner s = new Scanner(System.in);
		String stockFile = s.nextLine();
		try {
			FileReader fileReader = new FileReader(ROOT_PATH+stockFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			stockString = bufferedReader.readLine();
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Replay file for stock is not found.");
			System.exit(-1);
		}catch (IOException e) {
			e.printStackTrace();
			System.out.println("Fail to read replay file for stock.");
			System.exit(-1);
		}
		ArrayList<String> stockStringArray = new ArrayList<String>(Arrays.asList(stockString.split("\\s+")));
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		for (String str: stockStringArray ) {
			try {
				tiles.add(new Tile(str));
			}catch (InvalidTileException e) {
				e.printStackTrace();
				System.out.println("Invalid Tile detected.");
				System.exit(-1);
			}
		}
		stock = new Stock(tiles);
		System.out.println("Please enter command file name");
		Scanner c = new Scanner(System.in);
		String commandFile = c.nextLine();
		ArrayList<String> commandStringArrar = new ArrayList<String>();
		int playerNumber = -1;
		try {
			FileReader fileReader = new FileReader(ROOT_PATH+commandFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			playerNumber = Integer.valueOf(bufferedReader.readLine());
			if (playerNumber < 2 || playerNumber > 4) {
				System.out.println("Player number is not valid.");
				System.exit(-1);
			}
			String line;
			while((line = bufferedReader.readLine()) != null) {
				commandStringArrar.add(line);
        	}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Replay file for commands is not found.");
			System.exit(-1);
		}catch (IOException e) {
			e.printStackTrace();
			System.out.println("Fail to read replay file for commands.");
			System.exit(-1);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("Fail to read correct player number.");
			System.exit(-1);
		}
		for (int i = 0; i < playerNumber; i++) {
			players.add(new AI(new StrategyFile()));
		}
		initPlayersHand();
		currentPlayer = 0; 
		hasPlayed = 0;
		for  (int i = 0; i < commandStringArrar.size(); i++) {
			
			System.out.println(table);
			System.out.println("Stock Left: "+stock.size());
			players.get(currentPlayer).printHand();
			String str = commandStringArrar.get(i);
			commandControl.newCommand(str);
			System.out.println();
			if (winner != null) {
				view.announceWinner(winner.getName());
				return;
			}
		}
	}
	
	public Player getWinner() {return winner;}
	
	public void printTable() {
		System.out.println(table);
	}
	public int stockSize() {return stock.size();}
	public int tableSize() {return table.size();}
}
