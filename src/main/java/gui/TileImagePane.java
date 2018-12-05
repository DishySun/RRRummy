package gui;

import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.event.*;

import java.util.ArrayList;

@SuppressWarnings("restriction")
public class TileImagePane extends Pane{

	public final static boolean TAIL = false;
	public final static boolean HEAD = true;
	
	public void addTile(ImageView iv, boolean headOrTail) {
		//true for head, false for tail
		if (headOrTail) {
			iv.relocate(0, 0);
			this.getChildren().add(0, iv);
			for (int i = 1; i < this.getChildren().size(); i++) {
				this.getChildren().get(i).relocate(i * 20, 0);
			}
		}
		else {
			this.move(iv,(this.getChildren().size() * 20) , 0);
			this.getChildren().add(iv);
		}
	}
	
	public void addTile(ImageView iv, int index) {
		if (index <= 0) this.addTile(iv, HEAD);
		else if (index >= this.getChildren().size()) this.addTile(iv, TAIL);
		else {
			this.getChildren().add(index,iv);
			for (int i = index; i < this.getChildren().size(); i++) {
				this.move(iv,(this.getChildren().size() * 20) , 0);
			}
		}
	}
	
	public ImageView removeTile(int i) {
		if (i < 0 || i >= this.getChildren().size() || this.getChildren().size() <= 0) return null;
		ImageView iv = (ImageView) this.getChildren().remove(i);
		this.relocateAll();
		return iv;
	}
	
	public void remove(ImageView iv) {
		this.getChildren().remove(iv);
		this.relocateAll();
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
	
	public void relocateAll() {
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
	
	public void setOnClickedHandler(EventHandler<MouseEvent> t) {
		for (Node n : this.getChildren()) {
			n.setOnMouseClicked(t);
		}
	}
	
	public void clearEventHandler() {
		for (Node n : this.getChildren()) {
			n.setOnMouseClicked(null);
		}
	}
	
	public boolean isFromHand() {
		if (this.getParent().getParent().getClass().getSimpleName().equals("CurrentPlayerPane") )return true;
		return false;
	}
	
	public int getMeldIndex() {
		//-1 for player hand
		if (this.getParent().getParent().getClass().getSimpleName().equals("CurrentPlayerPane") ) return -1;
		else return this.getParent().getChildrenUnmodifiable().indexOf(this);
	}
	
	public int getTileIndex(Node o) {
		return this.getChildrenUnmodifiable().indexOf(o);
	}
}
