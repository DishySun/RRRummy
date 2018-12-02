package gui;

import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;

@SuppressWarnings({ "restriction", "unused" })
public class CurrentPlayerPane extends Pane{
	private Button endButton;
	private Button hintButton;
	private TileImagePane handImages;
	private final int HAND_LOCATION_X = 10;
	private final int HAND_LOCATION_Y = 10; 
	
	public CurrentPlayerPane(String playerName) {
		Pane innerPane = new Pane();
		innerPane.setStyle("-fx-background-color: white; " +
				"-fx-border-color: gray; " +
				"-fx-padding: 4 4;");
		handImages = new TileImagePane();
		handImages.relocate(HAND_LOCATION_X, HAND_LOCATION_Y);
		endButton = new Button("End Turn");
		endButton.setDisable(true);
		endButton.relocate(30, 50);
		hintButton = new Button("Hint");
		hintButton.setDisable(true);
		hintButton.relocate(150, 50);
		
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
		handImages.add(img,false);
	}
	
	public ImageView removeTile(int i) {
		return handImages.remove(i);
	}
	
	public void sortedImages(ArrayList<Integer> imgs) {
		handImages.sort(imgs);
	}

	public void removeTile(ImageView imageViewBeingMoved) {
		handImages.remove(imageViewBeingMoved);
	}
}
