package gui;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

@SuppressWarnings("restriction")
public class MeldImagePane extends Pane{
	//private final boolean HEAD = true;
	private final int MELD_IN_EVERY_COLUMN = 6;
	private Pane innerPane;
	
	public MeldImagePane(EventHandler<MouseEvent> tableOnMouseClicked) {
		innerPane = new Pane();
		
		innerPane.relocate(10, 10);
		//innerPane.setPrefSize(600, 350);
		
		Pane clickPane = new Pane();
		clickPane.setPrefSize(600, 350);
		clickPane.relocate(10, 10);
		clickPane.setOnMouseClicked(tableOnMouseClicked);
		clickPane.setStyle("-fx-background-color: green; " +
				"-fx-border-color: gray; " +
				"-fx-padding: 4 4;");
		
		Label titleLabel = new Label();
		titleLabel.setText("Melds");
		titleLabel.setStyle("-fx-background-color: white; \n" +
				"-fx-translate-y: -8; \n" +
				"-fx-translate-x: 10;");
		this.getChildren().addAll(titleLabel,clickPane, innerPane);
	}

	
	public void add(ImageView iv, int index, boolean headOrTail) {
		if (index < 0 || index >= innerPane.getChildren().size()) {
			TileImagePane newMeld = new TileImagePane();
			newMeld.add(iv, TAIL);
			newMeld.setStyle("-fx-background-color: green; " +
					"-fx-border-color: red; " +
					"-fx-padding: 0 0;");
			newMeld.setPrefSize(160, 30);
			int s = innerPane.getChildren().size();
			newMeld.relocate(s / MELD_IN_EVERY_COLUMN * 100, (s % MELD_IN_EVERY_COLUMN) * 50);
			innerPane.getChildren().add(newMeld);
		}else {
			TileImagePane a = (TileImagePane)innerPane.getChildren().get(index);
			a.addTile(iv, headOrTail);
		}
	}
	
	public void add(ImageView iv) {
		TileImagePane newMeld = new TileImagePane();
		newMeld.addTile(iv, TileImagePane.TAIL);
		newMeld.setStyle("-fx-background-color: green; " +
				"-fx-border-color: red; " +
				"-fx-padding: 0 0;");
		newMeld.setPrefSize(260, 30);
		//int s = innerPane.getChildren().size();
		//newMeld.relocate(s / MELD_IN_EVERY_COLUMN * 270, (s % MELD_IN_EVERY_COLUMN) * 50);
		innerPane.getChildren().add(newMeld);
		this.relocateAll();
	}
	
	public void relocateAll() {
		for (int i = 0; i < innerPane.getChildren().size(); i++) {
			if(((TileImagePane)innerPane.getChildren().get(i)).getChildren().size() == 0) {
				innerPane.getChildren().remove(i);
				i--;
			}
		}
		for (int i = 0; i < innerPane.getChildren().size(); i++) {
			TileImagePane temp = (TileImagePane)innerPane.getChildren().get(i);
			temp.relocate(i / MELD_IN_EVERY_COLUMN * 270, (i % MELD_IN_EVERY_COLUMN) * 50);
			temp.relocateAll();
		}
	}
	
	public ImageView cut(int meldIndex, int tileIndex) {
		if (meldIndex < 0  || tileIndex < 0 || meldIndex >= innerPane.getChildren().size()) return null;
		TileImagePane a = (TileImagePane)innerPane.getChildren().get(meldIndex);
		if (tileIndex >= a.getChildren().size()) return null;
		int s = innerPane.getChildren().size();
		for (int i = 0; i <= tileIndex; i++) {
			this.add(a.removeTile(0), s, TileImagePane.TAIL);
		}
		TileImagePane p = (TileImagePane)innerPane.getChildren().get(s);
		return (ImageView)p.getChildren().get(p.getChildren().size()-1);
	}
	
	
	
	public void replace(ImageView iv, int meldIndex, int tileIndex) {
		if (meldIndex < 0  || tileIndex < 0 || meldIndex >= innerPane.getChildren().size()) return;
		TileImagePane a = (TileImagePane)innerPane.getChildren().get(meldIndex);
		if (tileIndex >= a.getChildren().size()) return;
		a.replace(iv, tileIndex);
	}
	
	public void setOnClickedHandler(EventHandler<MouseEvent> t) {
		for (int i = 0; i < innerPane.getChildren().size(); i++) {
			((TileImagePane)innerPane.getChildren().get(i)).setOnClickedHandler(t);
		}
	}
	
	public void clearEventHandler() {
		for (int i = 0; i < innerPane.getChildren().size(); i++) {
			((TileImagePane)innerPane.getChildren().get(i)).clearEventHandler();
		}
	}
}
