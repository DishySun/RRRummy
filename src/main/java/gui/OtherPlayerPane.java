package gui;

import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

@SuppressWarnings("restriction")
public class OtherPlayerPane extends Pane{
	private BackImagePane handImages;
	private Label handSize;
	private final int HAND_LOCATION_X = 10;
	private final int HAND_LOCATION_Y = 10; 
	
	public OtherPlayerPane(String playerName, int playerNumber, Image backImage) {
		Pane innerPane = new Pane();
		innerPane.setStyle("-fx-background-color: white; " +
				"-fx-border-color: gray; " +
				"-fx-padding: 4 4;");
		handImages = new BackImagePane(backImage);
		handImages.relocate(HAND_LOCATION_X, HAND_LOCATION_Y);
		Label size = new Label("Hand Size: ");
		size.relocate(HAND_LOCATION_X+50, HAND_LOCATION_Y+40);
		handSize = new Label(Integer.toString(handImages.size()));
		handSize.relocate(HAND_LOCATION_X+120, HAND_LOCATION_Y+40);
		
		
		innerPane.getChildren().addAll(size,handSize,handImages);
		if (playerNumber % 2 != 0) {
			innerPane.setRotate(playerNumber*90);
			innerPane.relocate(-155, 160);
			innerPane.setPrefSize(400,80);
		}else innerPane.setPrefSize(500, 80);
		
		
		Label titleLabel = new Label();
		titleLabel.setText(playerName);
		titleLabel.setStyle("-fx-background-color: white; \n" +
				"-fx-translate-y: -8; \n" +
				"-fx-translate-x: 10;");
		this.getChildren().addAll(innerPane, titleLabel);
		
	}
	
	public void add() {
		handImages.add();
		handSize.setText(Integer.toString(handImages.size()));
	}
	
	public void remove() {
		handImages.remove();
		handSize.setText(Integer.toString(handImages.size()));
	}
}
