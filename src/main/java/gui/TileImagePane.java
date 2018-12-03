package gui;

import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

@SuppressWarnings("restriction")
public class TileImagePane extends Pane{
	
	
	public void add(ImageView iv, boolean headOrTail) {
		//true for head, false for tail
		if (headOrTail) {
			iv.relocate(0, 0);
			this.getChildren().add(0, iv);
			for (int i = 1; i < this.getChildren().size(); i++) {
				this.getChildren().get(i).relocate(i * 20, 0);
			}
		}
		else {
			iv.relocate(this.getChildren().size() * 20, 0);
			this.getChildren().add(iv);
		}
	}
	
	public ImageView remove(int i) {
		if (i < 0 || i >= this.getChildren().size() || this.getChildren().size() <= 0) return null;
		ImageView iv = (ImageView) this.getChildren().remove(i);
		this.relocateAll();
		return iv;
	}
	
	public void remove(ImageView iv) {
		this.getChildren().remove(iv);
		this.relocateAll();
	}
	
	public ImageView removeLast() {
		if (this.getChildren().size() <= 0) return null;
		return (ImageView) this.getChildren().remove(this.getChildren().size() -1);
	}
	
	public void sort(ArrayList<Integer> indexes) {
		if (indexes.size() != this.getChildren().size()) {
			System.err.println("Error size when sorting in TileImagePane.java");
			System.err.println("indexes siez: "+indexes.size());
			System.err.println("Children size: "+ this.getChildren().size());
			System.exit(-1);
		}
		ArrayList<ImageView> list = new ArrayList<ImageView>();
		for (int i: indexes) {
			list.add((ImageView) this.getChildren().get(i));
		}
		this.getChildren().setAll(list);
		this.relocateAll();
	}
	
	private void relocateAll() {
		for (int i = 0; i < this.getChildren().size(); i++) {
			this.move((ImageView) this.getChildren().get(i), i * 20, 0);
		}
		
	}

	public void replace(ImageView iv, int at) {
		if (at < 0 || at >= this.getChildren().size()) return;
		ImageView a = (ImageView)this.getChildren().get(at);
		Image img = iv.getImage();
		iv.setImage(a.getImage());
		a.setImage(img);
	}

	private void move(ImageView imageView, int i, int j) {
		//Animation may applied
		imageView.relocate(i, j);
	}
	
}
