package gui;

import javafx.scene.layout.Pane;
import rrrummy.Tile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.*;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings({ "restriction", "unused" })
public class CurrentPlayerPane extends Pane{
	private Button endButton;
	private Button hintButton;
	private TileImagePane handImages;
	private final int HAND_LOCATION_X = 10;
	private final int HAND_LOCATION_Y = 10; 
	
	public CurrentPlayerPane(String playerName, EventHandler<ActionEvent> endTurnEventHandler, EventHandler<ActionEvent> hintEventHandler) {
		Pane innerPane = new Pane();
		innerPane.setStyle("-fx-background-color: white; " +
				"-fx-border-color: gray; " +
				"-fx-padding: 4 4;");
		handImages = new TileImagePane();
		handImages.relocate(HAND_LOCATION_X, HAND_LOCATION_Y);
		endButton = new Button("End Turn");
		endButton.setDisable(true);
		endButton.relocate(30, 50);
		endButton.setOnAction(endTurnEventHandler);
		hintButton = new Button("Hint");
		hintButton.setDisable(true);
		hintButton.relocate(150, 50);
		hintButton.setOnAction(hintEventHandler);
		
		innerPane.setPrefSize(500, 80);
		innerPane.getChildren().addAll(endButton, hintButton, handImages);
		
		Label titleLabel = new Label();
		titleLabel.setText(playerName);
		titleLabel.setStyle("-fx-background-color: white; \n" +
				"-fx-translate-y: -8; \n" +
				"-fx-translate-x: 10;");
		this.getChildren().addAll(innerPane, titleLabel);
	}
	
	public void addTile(ImageView img) {
		handImages.addTile(img,false);
	}
	
	public void addTail(int index, ImageView img) {
		handImages.addTile(img, index);
	}
	
	public ImageView removeTile(int i) {
		return handImages.removeTile(i);
	}

	public void removeTile(ImageView imageViewBeingMoved) {
		handImages.remove(imageViewBeingMoved);
	}
	
	public void sortedImages(HashMap<ImageView, Tile> imgMap) {
		handImages.sort(imgMap);
	}
	
	public void getTurnEventHandlers(EventHandler<MouseEvent> ivEvent) {
		handImages.setOnClickedHandler(ivEvent);
		endButton.setDisable(false);
		hintButton.setDisable(false);
	}
	
	public void endTurnEventHandlers() {
		handImages.clearEventHandler();
		endButton.setDisable(true);
		hintButton.setDisable(true);
	}
	
	public void relocateAll() {
		handImages.relocateAll();
	}

	public ImageView getImageView(int handIndex) {
		return (ImageView)handImages.getChildren().get(handIndex);
	}
	
	public Button getEndButton() {return endButton;}
}
