package gui;

import javafx.scene.layout.Pane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@SuppressWarnings("restriction")
public class BackImagePane extends Pane{
	private Image backImg;
	public BackImagePane(Image backImage) {
		this.backImg = backImage;
	}
	
	public void add() {
		ImageView iv = new ImageView(backImg);
		iv.relocate((this.getChildren().size())*20, 0);
		this.getChildren().add(iv);
		
	}
	
	public void remove() {
		if (this.getChildren().size() <= 0) return;
		this.getChildren().remove(this.getChildren().size() -1);
	}
	
	public int size() {
		return this.getChildren().size();
	}
}
