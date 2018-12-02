package gui;

import javafx.scene.layout.Pane;
import players.Player;
import rrrummy.Tile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.*;

import java.util.ArrayList;

@SuppressWarnings("restriction")
public class TablePane extends Pane{
	private CurrentPlayerPane currentPlayer;
	private ArrayList<OtherPlayerPane> otherPlayers;
	private MeldImagePane melds;
	private ArrayList<Image> red;
	private ArrayList<Image> blue;
	private ArrayList<Image> green;
	private ArrayList<Image> orange;
	private Image joker;
	private Image back;
	private double orgSceneX, orgSceneY;
	private double orgTranslateX, orgTranslateY;
	private ImageView imageViewBeingMoved;
	private boolean isFromHand;
	
	public TablePane(ArrayList<Player> players, ArrayList<Image> red, ArrayList<Image> blue, ArrayList<Image> green, ArrayList<Image> orange, Image joker, Image back) {
		this.red = red;
		this.blue = blue;
		this.green = green;
		this.orange = orange;
		this.joker = joker;
		this.back = back;
		currentPlayer = new CurrentPlayerPane(players.get(0).getName());
		for (int i = 1; i < players.size(); i++) {
			otherPlayers.add(new OtherPlayerPane(players.get(i).getName(),i, back));
			switch (i) {
			case 2:
				otherPlayers.get(i).relocate(150, 10);
				break;
			case 1:
				otherPlayers.get(i).relocate(10, 100);
				break;
			case 3:
				otherPlayers.get(i).relocate(750, 100);
				break;
			}
		}
	}
	
	public void playerDrawTile(Tile t) {
		switch (t.getColor()) {
		case RED:
			currentPlayer.addTile(new ImageView(red.get(t.getNumber()-1)));
			break;
		case BLUE:
			currentPlayer.addTile(new ImageView(blue.get(t.getNumber()-1)));
			break;
		case GREEN:
			currentPlayer.addTile(new ImageView(green.get(t.getNumber()-1)));
			break;
		case ORANGE:
			currentPlayer.addTile(new ImageView(orange.get(t.getNumber()-1)));
			break;
		case JOKER:
			currentPlayer.addTile(new ImageView(joker));
			break;
		}
	}
	
	public void otherDrawTile(int playerNumber) {
		otherPlayers.get(playerNumber).add();
	}
	public void otherPlayTileTo(int playerNumber, Tile playedTile, int meldIndex, boolean headOrTail) {
		otherPlayers.get(playerNumber).remove();
		this.addTileTo(this.newImageView(playedTile.getColor(), playedTile.getNumber()), meldIndex, headOrTail);
	}
	public void addTileTo(ImageView iv, int meldIndex, boolean headOrTail) {
		melds.add(iv, meldIndex, headOrTail);
	}
	
	
	private ImageView newImageView(Tile.Color c, int tileNumber) {
		ImageView iv = new ImageView();
		iv.setOnMouseClicked(imageViewOnMouseClickedEventHandler);
		switch (c) {
		case RED:
			iv.setImage(red.get(tileNumber -1));
			break;
		case BLUE:
			iv.setImage(blue.get(tileNumber -1));
			break;
		case GREEN:
			iv.setImage(green.get(tileNumber -1));
			break;
		case ORANGE:
			iv.setImage(orange.get(tileNumber -1));
			break;
		case JOKER:
			iv.setImage(joker);
			break;
		}
		return iv;
	}
	
	//events handlers
	private EventHandler<MouseEvent> imageViewOnMouseClickedEventHandler = new EventHandler<MouseEvent>() {
		@Override
        public void handle(MouseEvent t) {
			if (imageViewBeingMoved == null) {
				imageViewBeingMoved = (ImageView)(t.getSource());
	            orgSceneX = t.getSceneX();
	            orgSceneY = t.getSceneY();
	            orgTranslateX = imageViewBeingMoved.getTranslateX();
	            orgTranslateY = imageViewBeingMoved.getTranslateY();
	            imageViewBeingMoved.getScene().setOnMouseMoved(imageViewOnMouseMovedEventHandler);
			}
        }
	};
	
	private EventHandler<MouseEvent> imageViewOnMouseMovedEventHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            imageViewBeingMoved.setTranslateX(newTranslateX);
            imageViewBeingMoved.setTranslateY(newTranslateY);
		}
	};
}
