package gui;

import javafx.scene.image.ImageView;

@SuppressWarnings("restriction")
public class MeldImagePane extends TileImagePane{
	//private final boolean HEAD = true;
	private final boolean TAIL = false;
	private final int MELD_IN_EVERY_COLUMN = 6;

	
	public void add(ImageView iv, int index, boolean headOrTail) {
		if (index < 0 || index >= this.getChildren().size()) {
			TileImagePane newMeld = new TileImagePane();
			newMeld.add(iv, TAIL);
			int s = this.getChildren().size();
			newMeld.relocate(s / MELD_IN_EVERY_COLUMN * 100, (s % MELD_IN_EVERY_COLUMN) * 50);
			this.getChildren().add(newMeld);
		}else {
			TileImagePane a = (TileImagePane)this.getChildren().get(index);
			a.add(iv, headOrTail);
		}
	}
	
	public ImageView remove(int meldIndex, int tileIndex) {
		if (meldIndex < 0  || tileIndex < 0 || meldIndex >= this.getChildren().size()) return null;
		TileImagePane a = (TileImagePane)this.getChildren().get(meldIndex);
		if (tileIndex >= a.getChildren().size()) return null;
		ImageView iv =  a.remove(tileIndex);
		if (a.getChildren().size() == 0) {
			this.getChildren().remove(a);
			for (int i = tileIndex; i < this.getChildren().size(); i++) {
				this.getChildren().get(i).relocate(i / MELD_IN_EVERY_COLUMN * 100, (i % MELD_IN_EVERY_COLUMN) * 50);
			}
		}
		return iv;
	}
	
	public ImageView cut(int meldIndex, int tileIndex) {
		if (meldIndex < 0  || tileIndex < 0 || meldIndex >= this.getChildren().size()) return null;
		TileImagePane a = (TileImagePane)this.getChildren().get(meldIndex);
		if (tileIndex >= a.getChildren().size()) return null;
		if (tileIndex == a.getChildren().size() - 1 || a.getChildren().size() < 3 || tileIndex == 0) return this.remove(meldIndex, tileIndex);
		int s = this.getChildren().size();
		for (int i = 0; i < tileIndex; i++) {
			this.add(a.remove(0), s, TAIL);
		}
		return a.remove(0);
	}
	
	public void replace(ImageView iv, int meldIndex, int tileIndex) {
		if (meldIndex < 0  || tileIndex < 0 || meldIndex >= this.getChildren().size()) return;
		TileImagePane a = (TileImagePane)this.getChildren().get(meldIndex);
		if (tileIndex >= a.getChildren().size()) return;
		a.replace(iv, tileIndex);
	}
}
