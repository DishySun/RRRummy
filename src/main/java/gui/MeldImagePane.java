package gui;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

@SuppressWarnings("restriction")
public class MeldImagePane extends Pane{
	//private final boolean HEAD = true;
	private final boolean TAIL = false;
	private final int MELD_IN_EVERY_COLUMN = 6;
	private Pane innerPane;
	
	public MeldImagePane() {
		innerPane = new Pane();
		innerPane.setStyle("-fx-background-color: green; " +
				"-fx-border-color: gray; " +
				"-fx-padding: 4 4;");
		innerPane.relocate(10, 10);
		innerPane.setPrefSize(400, 300);
		
		Label titleLabel = new Label();
		titleLabel.setText("Melds");
		titleLabel.setStyle("-fx-background-color: white; \n" +
				"-fx-translate-y: -8; \n" +
				"-fx-translate-x: 10;");
		this.getChildren().addAll(titleLabel, innerPane);
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
			a.add(iv, headOrTail);
		}
	}
	
	public ImageView remove(int meldIndex, int tileIndex) {
		if (meldIndex < 0  || tileIndex < 0 || meldIndex >= innerPane.getChildren().size()) return null;
		TileImagePane a = (TileImagePane)innerPane.getChildren().get(meldIndex);
		if (tileIndex >= a.getChildren().size()) return null;
		ImageView iv =  a.remove(tileIndex);
		if (a.getChildren().size() == 0) {
			innerPane.getChildren().remove(a);
			for (int i = tileIndex; i < innerPane.getChildren().size(); i++) {
				innerPane.getChildren().get(i).relocate(i / MELD_IN_EVERY_COLUMN * 100, (i % MELD_IN_EVERY_COLUMN) * 50);
			}
		}
		return iv;
	}
	
	public ImageView cut(int meldIndex, int tileIndex) {
		if (meldIndex < 0  || tileIndex < 0 || meldIndex >= innerPane.getChildren().size()) return null;
		TileImagePane a = (TileImagePane)innerPane.getChildren().get(meldIndex);
		if (tileIndex >= a.getChildren().size()) return null;
		if (tileIndex == a.getChildren().size() - 1 || a.getChildren().size() < 3 || tileIndex == 0) return this.remove(meldIndex, tileIndex);
		int s = innerPane.getChildren().size();
		for (int i = 0; i < tileIndex; i++) {
			this.add(a.remove(0), s, TAIL);
		}
		return a.remove(0);
	}
	
	public void replace(ImageView iv, int meldIndex, int tileIndex) {
		if (meldIndex < 0  || tileIndex < 0 || meldIndex >= innerPane.getChildren().size()) return;
		TileImagePane a = (TileImagePane)innerPane.getChildren().get(meldIndex);
		if (tileIndex >= a.getChildren().size()) return;
		a.replace(iv, tileIndex);
	}
}
