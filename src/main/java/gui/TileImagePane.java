package gui;

import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

import javafx.animation.TranslateTransition; 
import javafx.util.Duration;
import rrrummy.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

@SuppressWarnings("restriction")
public class TileImagePane extends Pane{

	public final static boolean TAIL = false;
	public final static boolean HEAD = true;
	
	
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
		this.relocateAll();
	}
	
	public void addTile(ImageView iv, boolean headOrTail) {
		if (headOrTail) this.getChildren().add(0, iv);
		else this.getChildren().add(iv);
		this.relocateAll();
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
	
	public void sort(HashMap<ImageView, Tile> imgMap) {
		ArrayList<ImageView> list = new ArrayList<ImageView>();
		for (int i = 0; i < this.getChildren().size(); i++) {
			list.add((ImageView)this.getChildren().get(i));
		}
		Collections.sort(list, new Comparator<ImageView>(){

			@Override
			public int compare(ImageView iv1, ImageView iv2) {
				Tile t1 = imgMap.get(iv1);
				Tile t2 = imgMap.get(iv2);
				if (t1.isGreaterThan(t2)) {
					return 1;
				}else return -1;
			}
		});
		this.getChildren().setAll(list);
		this.relocateAll();
	}
	
	public void relocateAll() {
		for (int i = 0; i < this.getChildren().size(); i++) {
			this.move((ImageView) this.getChildren().get(i), i * 20, 0);
		}
		
	}

	private void move(ImageView imageView, int i, int j) {
		//Animation may applied
		TranslateTransition translateTransition = new TranslateTransition();
		translateTransition.setDuration(Duration.millis(800)); 
		translateTransition.setNode(imageView); 
		translateTransition.setToX(i);
		//translateTransition.setByY(30);
		translateTransition.setCycleCount(1);
		translateTransition.play();
		//imageView.relocate(i, j);
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
	
	public void clearAllNode() {
		this.getChildren().removeAll();
		this.relocateAll();
	}
	
	public int getMeldIndex() {
		//-1 for player hand
		if (this.getParent().getParent().getClass().getSimpleName().equals("CurrentPlayerPane") ) return -1;
		else return this.getParent().getChildrenUnmodifiable().indexOf(this);
	}
	
	public int getTileIndex(Node o) {
		return this.getChildrenUnmodifiable().indexOf(o);
	}
	
	public int size() {return this.getChildren().size();}
}
