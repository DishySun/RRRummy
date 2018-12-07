package gui;

import javafx.scene.layout.Pane;
import rrrummy.Tile;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.event.*;


@SuppressWarnings("restriction")
public class TilePickerPane extends Pane{
	private ListView<Tile> tilePicker;
	private Button addButton, removeButton;
	
	public TilePickerPane(String name, EventHandler<ActionEvent> addEvent, EventHandler<ActionEvent> removeEvent) {
		initUI(name, addEvent, removeEvent);
	}
	
	
	private void initUI(String name, EventHandler<ActionEvent> addEvent, EventHandler<ActionEvent> removeEvent) {
		Pane innerPane = new Pane();
		tilePicker = new ListView<Tile>();
		tilePicker.setMaxWidth(100);
		
		addButton = new Button("Add");
		addButton.relocate(0, 420);
		addButton.setMinWidth(100);
		addButton.setMaxWidth(100);
		addButton.setOnAction(addEvent);
		
		removeButton = new Button("Remove");
		removeButton.relocate(0, 470);
		removeButton.setMinWidth(100);
		removeButton.setMaxWidth(100);
		removeButton.setOnAction(removeEvent);
		
		innerPane.getChildren().addAll(tilePicker,addButton,removeButton);
		innerPane.relocate(0, 25);
		
		Label nameLabel = new Label(name);
		this.getChildren().addAll(nameLabel, innerPane);
	}
	
	public ListView<Tile> getList(){
		return this.tilePicker;
	}
}
