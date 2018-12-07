package gui;

import java.util.ArrayList;

import gui_game.GameControl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.collections.ObservableList;

import players.Player;
import rrrummy.InvalidTileException;
import rrrummy.Tile;


@SuppressWarnings("restriction")
public class RiggingPane extends Pane{
	private ArrayList<TilePickerPane> players;
	private TilePickerPane stock;
	private ListView<Tile> tiles;
	private ArrayList<Tile> allTiles;
	private GameControl gc;
	
	public RiggingPane(ArrayList<Player> p, GameControl g) {
		gc = g;
		initUI(p);
	}
	
	private void initUI(ArrayList<Player> pp) {
		this.initTiles();
		stock = new TilePickerPane("Stock", addEvent, removeEvent);
		stock.relocate(10, 50);
		stock.setMaxWidth(100);
		
		tiles = new ListView<Tile>();
		tiles.getItems().addAll(allTiles);
		tiles.setMaxWidth(100);
		tiles.relocate(150, 70);
		
		this.players = new ArrayList<TilePickerPane>();
		for (int i = 0 ; i < pp.size(); i++) {
			Player p = pp.get(i);
			TilePickerPane pkPane = new TilePickerPane(p.getName(), addEvent, removeEvent);
			this.players.add(pkPane);
			pkPane.relocate((i*120) + 300 , 50);
		}
		
		Button playButton = new Button("Play");
		playButton.relocate(380, 600);
		playButton.setOnAction(playEvent);
		this.getChildren().addAll(stock,tiles,playButton);
		this.getChildren().addAll(this.players);
	}

	
	private void initTiles() {
		allTiles = new ArrayList<Tile>();
		for (Tile.Color c: Tile.Color.values()) {
			if (c == Tile.Color.JOKER) continue;
			for (int i = 1; i<=13; i++) {
				try {
					allTiles.add(new Tile(c,i));
					allTiles.add(new Tile(c,i));
				} catch (InvalidTileException e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		}
		allTiles.add(new Tile());
		allTiles.add(new Tile());
	}

	EventHandler<ActionEvent> addEvent = new EventHandler<ActionEvent>(){

		@Override
		public void handle(ActionEvent event) {
			ObservableList<Tile> items = tiles.getSelectionModel().getSelectedItems();
			Button b = (Button)event.getSource();
			TilePickerPane pane = (TilePickerPane)b.getParent().getParent();
			ListView<Tile> list = pane.getList();
			list.getItems().addAll(items);
			tiles.getItems().removeAll(items);
		}};
		
	EventHandler<ActionEvent> removeEvent = new EventHandler<ActionEvent>(){

		@Override
		public void handle(ActionEvent event) {
			Button b = (Button)event.getSource();
			TilePickerPane pane = (TilePickerPane)b.getParent().getParent();
			ListView<Tile> list = pane.getList();
			ObservableList<Tile> items = list.getSelectionModel().getSelectedItems();
			tiles.getItems().addAll(items);
			list.getItems().removeAll(items);
		}};
		
	EventHandler<ActionEvent> playEvent = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			ArrayList<ArrayList<Tile>> initHands = new ArrayList<ArrayList<Tile>>(); 
			for (TilePickerPane tp : players) {
				ListView<Tile> temp = tp.getList();
				ArrayList<Tile> playerHand = new  ArrayList<Tile>();
				playerHand.addAll(temp.getItems());
				initHands.add(playerHand);
			}
			ArrayList<Tile> initStock = new ArrayList<Tile>();
			ListView<Tile> st = stock.getList();
			if (st.getItems().size() != 0) initStock.addAll(st.getItems());
			else initStock.addAll(tiles.getItems());
			Stage newGame = new Stage();
			Scene newScene = new Scene(gc.getTablePane(), 900, 700);
			newGame.setTitle("RRRumy");
			newGame.setScene(newScene);
			newGame.show();
			gc.riggingGame(initHands,initStock);
		}};
		
}
