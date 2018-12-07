package gui;

import players.*;
import gui_game.GameControl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

@SuppressWarnings("restriction")
public class PlayerCreator extends Pane{
	
	private Button playButton, riggingButton;
	private ArrayList<ChoiceBox<String>> boxes;
	private ArrayList<TextField> textes;
	private ArrayList<Label> labels;
	private final int MAX_PLAYER_NUMBER = 4;
	private final ArrayList<Image> red;
	private final ArrayList<Image> blue;
	private final ArrayList<Image> green;
	private final ArrayList<Image> orange;
	private final Image back;
	private final Image joker;
	
	public PlayerCreator() {
		String path = "src/main/resources/Tiles/";
		red = new ArrayList<Image>();
		blue = new ArrayList<Image>();
		green = new ArrayList<Image>();
		orange = new ArrayList<Image>();
		FileInputStream jk = null;
		FileInputStream bk = null;
		try {
			for (int i = 1; i <= 13; i++) {
				red.add(new Image(new FileInputStream(path+"R"+Integer.toString(i)+".png")));
				blue.add(new Image(new FileInputStream(path+"B"+Integer.toString(i)+".png")));
				green.add(new Image(new FileInputStream(path+"G"+Integer.toString(i)+".png")));
				orange.add(new Image(new FileInputStream(path+"O"+Integer.toString(i)+".png")));
			}
			jk =new FileInputStream(path + "Joker.png");
			bk =new FileInputStream(path + "Back.png");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		joker = new Image(jk);
		back = new Image(bk);
		initUI();
	}
	
	private void initUI() {
		playButton = new Button("Play");
		playButton.relocate(600, 150);
		playButton.setOnAction(playButtonEventHandler);
		
		riggingButton = new Button("Rigging");
		riggingButton.relocate(600, 300);
		riggingButton.setOnAction(riggingButtonEventHandler);
		
		boxes = new ArrayList<ChoiceBox<String>>(4);
		textes = new ArrayList<TextField>(4);
		labels = new ArrayList<Label>(4);
		
		for(int i = 0; i < MAX_PLAYER_NUMBER; i++ ) {
			TextField text = new TextField();
			text.setEditable(false);
			text.relocate(300, (i+1) * 100);
			textes.add(text);
			
			Label label = new Label();
			label.relocate(300, (i+1)*100 -20);
			labels.add(label);
			
			ChoiceBox<String> box = new ChoiceBox<String>();
			box.getItems().addAll("None","Human Player", "Easy Computer", "Normal Computer","Hard Computer","Crazy Computer");
			box.relocate(80, (i+1) * 100);
			box.getSelectionModel().selectFirst();
			boxes.add(box);

		    box.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					String s = box.getItems().get(newValue.intValue());
					if (s.equalsIgnoreCase("Human Player")) {
						text.setEditable(true);
						label.setText("Please enter a name:");
					}else {
						label.setText(null);
						text.setEditable(false);
					}
				}
		    });
		}
		
		this.getChildren().addAll(playButton,riggingButton);
		this.getChildren().addAll(boxes);
		this.getChildren().addAll(textes);
		this.getChildren().addAll(labels);
	}
	
	private ArrayList<Player> initPlayers(){
		ArrayList<Player> players = new ArrayList<Player>();
		for (int i = 0; i < MAX_PLAYER_NUMBER; i++) {
			int index = boxes.get(i).getSelectionModel().getSelectedIndex();
			AIStrategy st = null;
			switch (index) {
			case 0:
				//none
				break;
			case 1:
				//human player
				String string = textes.get(i).getText();
				if (string == null || string.equals("")) {
					System.err.println((i+1) +"th Human player doesn't have a name.");
					return null;
				}
				players.add(new Player(string));
				break;
			case 2:
				st = new StrategyZero();
			case 3:
				st = new StrategyTwo();
			case 4:
				st = new StrategyThree();
			case 5:
				st = new StrategyFour();
			default:
				if (st != null) players.add(new AI(st));
				break;
			}
		}
		if(players.size() > 1) return players;
		System.err.println("Player size < 2");
		return null;
	}
	
	private EventHandler<ActionEvent> playButtonEventHandler = new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent event) {
			ArrayList<Player> players = initPlayers();
			if (players == null) return;
			int firstPlayer = 0;
			for (Player p : players) {
				if (p.getClass().getSimpleName().equals("Player")) {
					firstPlayer = players.indexOf(p);
					break;
				}
			}
			GameControl g = new GameControl(players, firstPlayer,red,blue,green,orange,joker, back);
			Scene newScene = new Scene(g.getTablePane(), 800, 700);
			Stage newGame = new Stage();
			newGame.setTitle("RRRumy");
			newGame.setScene(newScene);
			newGame.show();
			g.normalGame();
		}
	};
	
	private EventHandler<ActionEvent> riggingButtonEventHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			ArrayList<Player> players = initPlayers();
			if (players == null) return;
			int firstHumanPlayer = 0;
			for (Player p : players) {
				if (p.getClass().getSimpleName().equals("Player")) {
					firstHumanPlayer = players.indexOf(p);
					break;
				}
			}
			GameControl g = new GameControl(players, firstHumanPlayer,red,blue,green,orange,joker, back);
			RiggingPane riggingPane = new RiggingPane(players, g);
			getScene().setRoot(riggingPane);
		}
	};
}