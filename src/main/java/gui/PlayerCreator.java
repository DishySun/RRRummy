package gui;
import javafx.scene.layout.Pane;
import players.*;
import javafx.event.*;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import gui_game.GameControl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

@SuppressWarnings("restriction")
public class PlayerCreator extends Pane{
	
	private Button playButton, riggingButton;
	private ArrayList<ChoiceBox<String>> boxes;
	private ArrayList<TextField> textes;
	private ArrayList<Label> labels;
	private final int MAX_PLAYER_NUMBER = 4;
	
	public PlayerCreator() {
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
				players.add(new AI(st));
				break;
			}
		}
		if(players.size() > 1) return players;
		return null;
	}
	
	private EventHandler<ActionEvent> playButtonEventHandler = new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent event) {
			ArrayList<Player> players = initPlayers();
			//TODO: normal GameContorl Contraster
			System.out.println(players);
		}
	};
	
	private EventHandler<ActionEvent> riggingButtonEventHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			ArrayList<Player> players = initPlayers();
			//TODO: rigging Pane
			System.out.println(players);
		}
	};
}
