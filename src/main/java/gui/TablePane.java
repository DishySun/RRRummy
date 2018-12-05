package gui;

//layout
import javafx.scene.layout.Pane;
import players.Player;
import rrrummy.Tile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.*;

//utils
import java.util.ArrayList;

//effect part
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.effect.ColorAdjust;

//model
import gui_game.GameControl;

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
	private ImageView newlyPlayedTile; //pointer only
	private ImageView imageViewBeingSelected; //pointer only
	private ImageView imageViewBeingMoved; 
	private boolean isFromHand;
	private ScaleTransition selectingAnimation;
	private ColorAdjust newlyPlayedTileEffect;
	private GameControl gameControl;
	private final int playerIndexToShow;
	private double mouseX;
	private double mouseY;
	
	public TablePane(GameControl gc, final ArrayList<Player> players, int playerIndexToShow, ArrayList<Image> red, ArrayList<Image> blue, ArrayList<Image> green, ArrayList<Image> orange, Image joker, Image back) {
		this.gameControl = gc;
		this.playerIndexToShow = playerIndexToShow;
		this.red = red;
		this.blue = blue;
		this.green = green;
		this.orange = orange;
		this.joker = joker;
		this.isFromHand = false;
		this.melds = new MeldImagePane(tableOnMouseClicked);
		this.melds.relocate(120, 120);
		this.otherPlayers = new ArrayList<OtherPlayerPane>();
		//this.melds.setOnMouseClicked(tableOnMouseClicked);
		this.imageViewBeingMoved = new ImageView();
		
		this.currentPlayer = new CurrentPlayerPane(players.get(playerIndexToShow).getName(), endTurnEventHandler, hintEventHandler);
		currentPlayer.relocate(150, 500);
		for (int i = 1; i< players.size(); i++) {
			int a = (playerIndexToShow +i)%players.size();
			
			otherPlayers.add(new OtherPlayerPane(players.get(a).getName(),i, back));
		}
		for (int i = 0; i < otherPlayers.size(); i++) {
			switch (i) {
			case 1:
				otherPlayers.get(1).relocate(150, 10);
				break;
			case 0:
				otherPlayers.get(0).relocate(10, 100);
				break;
			case 2:
				otherPlayers.get(2).relocate(750, 100);
				break;
			default:
				break;
			}
			
		}
		selectingAnimation = new ScaleTransition();
		selectingAnimation.setDuration(Duration.millis(500));
		selectingAnimation.setByX(1.2);
		selectingAnimation.setByY(1.2);
		selectingAnimation.setCycleCount(Timeline.INDEFINITE);
		selectingAnimation.setAutoReverse(true);
		
		newlyPlayedTileEffect = new ColorAdjust();
		newlyPlayedTileEffect.setContrast(0.1);
		newlyPlayedTileEffect.setHue(-0.05);
		newlyPlayedTileEffect.setBrightness(0.1);
		newlyPlayedTileEffect.setSaturation(0.2);
		
		this.getChildren().addAll(otherPlayers);
		this.getChildren().addAll(melds,currentPlayer,imageViewBeingMoved);
	}
	
	public void playerDrawTile(Tile t) {
		currentPlayer.addTile(newImageView(t.getColor(),t.getNumber()));
	}
	
	public void playerPlayed(int playerIndex, int meldIndex, boolean headOrTail, Tile t) {
		if(playerIndexToShow == playerIndex) {
			melds.add(imageViewBeingSelected, meldIndex, headOrTail);
			this.setNewlyPlayed(imageViewBeingSelected);
			drop();
		}else {
			otherPlayTileTo(playerIndex, t, meldIndex, headOrTail);
		}
	}
	public void playerPlayed(int playerIndex, Tile t) {
		if(playerIndexToShow == playerIndex) {
			melds.add(imageViewBeingSelected);
			this.setNewlyPlayed(imageViewBeingSelected);
			drop();
		}else {
			int i = getOtherPlayerIndex(playerIndex);
			otherPlayers.get(i).remove();
			setNewlyPlayed(this.newImageView(t.getColor(), t.getNumber()));
		}
	}
	
	public void move(int toMeldIndex, boolean headOrTail) {
		melds.add(imageViewBeingSelected, toMeldIndex, headOrTail);
		this.setNewlyPlayed(imageViewBeingSelected);
		drop();
		melds.relocateAll();
	}
	
	public void cut(int meldIndex, int tileIndex) {
		pick(melds.cut(meldIndex, tileIndex));
		isFromHand = false;
	}
	public void replace(int meldIndex, int tileIndex) {
		melds.replace(imageViewBeingSelected, meldIndex, tileIndex);
	}
	
	public void otherDrawTile(int playerNumber) {
		otherPlayers.get(playerNumber).add();
	}
	
	private void setNewlyPlayed(ImageView iv) {
		if (newlyPlayedTile !=null) {
			newlyPlayedTile.setEffect(null);
		}
		newlyPlayedTile = iv;
		newlyPlayedTile.setEffect(newlyPlayedTileEffect);
	}
	private void otherPlayTileTo(int playerNumber, Tile playedTile, int meldIndex, boolean headOrTail) {
		int i = getOtherPlayerIndex(playerNumber);
		otherPlayers.get(i).remove();
		this.setNewlyPlayed(this.newImageView(playedTile.getColor(), playedTile.getNumber()));
		
		melds.add(newlyPlayedTile, meldIndex, headOrTail);
		drop();
	}
	
	private int getOtherPlayerIndex(int currentPlayer) {
		return (currentPlayer + otherPlayers.size() +1 - playerIndexToShow) % (otherPlayers.size() +1) -1;
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
	
	
	
	//method for event handlers
	private void pick(ImageView source) {
		imageViewBeingSelected = source;
		selectAnimationPlay();
		activeMovingImageView(imageViewBeingSelected.getImage(), mouseX+5, mouseY+5);
	}
	
	private void drop() {
		selectAnimationStop();
		disactiveMovingImageView();
		currentPlayer.relocateAll();
	}
	
	private void selectAnimationPlay() {
		selectingAnimation.setNode(imageViewBeingSelected);
		selectingAnimation.play();
	}
	
	private void selectAnimationStop() {
		selectingAnimation.stop();
		selectingAnimation.setNode(null);
		imageViewBeingSelected.setScaleX(1.0);
		imageViewBeingSelected.setScaleY(1.0);
		imageViewBeingSelected = null;
		
	}
	
	private void activeMovingImageView(Image img, double x, double y) {
		imageViewBeingMoved.setImage(img);
		imageViewBeingMoved.relocate(x, y);
		imageViewBeingMoved.getScene().setOnMouseMoved(imageViewOnMouseMovedEventHandler);
	}
	
	private void disactiveMovingImageView() {
		imageViewBeingMoved.setImage(null);
		imageViewBeingMoved.relocate(-50, -50);
		imageViewBeingMoved.getScene().setOnMouseMoved(null);
	}
	
	//events handlers
	private EventHandler<MouseEvent> imageViewOnMouseClickedEventHandler = new EventHandler<MouseEvent>() {
		@Override
        public void handle(MouseEvent t) {
			ImageView source = (ImageView) t.getSource();
			mouseX = t.getSceneX();
			mouseY = t.getSceneY();
			if(imageViewBeingSelected == null) {
				//pick
				if (((TileImagePane)source.getParent()).isFromHand()) {
					//pick for play
					pick(source);
					isFromHand = true;
				}else {
					int meldIndex = ((TileImagePane)source.getParent()).getMeldIndex();
					int tileIndex = ((TileImagePane)source.getParent()).getTileIndex(source);
					int s = ((TileImagePane)source.getParent()).getChildrenUnmodifiable().size();
					if (gameControl.isSet(meldIndex) || tileIndex == 0 || tileIndex == s-1) {
						//pick for move
						pick(source);
						isFromHand = false;
					}else {
						//cut
						gameControl.cut(meldIndex,tileIndex);
						//picking status
					}
				}
			}else {
				if (source.equals(imageViewBeingSelected)) {
					drop();
					return;
				}
				//drop
				
					int tileInHandIndex = ((TileImagePane)imageViewBeingSelected.getParent()).getTileIndex(imageViewBeingSelected);
					System.out.println("Select index: "+tileInHandIndex);
					int meldIndex = ((TileImagePane)source.getParent()).getMeldIndex();
					System.out.println("table index: " +meldIndex);
					int tileIndex = ((TileImagePane)source.getParent()).getTileIndex(source);
					System.out.println("table tile index: "+tileIndex);
					int size = source.getParent().getChildrenUnmodifiable().size();
					//System.out.println(size);
					boolean headOrTail;
					if(tileIndex > size / 2) headOrTail =false;
					else headOrTail = true;
					if(isFromHand) {
						if(!gameControl.replace(tileInHandIndex,meldIndex,tileIndex)) {
							gameControl.play(tileInHandIndex,meldIndex,headOrTail);
						}
					}else {
						int pervMeldIndex = ((TileImagePane)imageViewBeingSelected.getParent()).getMeldIndex();
						gameControl.move(pervMeldIndex, tileInHandIndex, meldIndex, headOrTail);
					}
				
			}
        }
	};
	
	private EventHandler<MouseEvent> tableOnMouseClicked = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			if(imageViewBeingSelected == null) return;
			int tileIndex = ((TileImagePane)imageViewBeingSelected.getParent()).getTileIndex(imageViewBeingSelected);
			gameControl.play(tileIndex);
		}};
	
	private EventHandler<MouseEvent> imageViewOnMouseMovedEventHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			mouseX = t.getSceneX();
			mouseY = t.getSceneY();
			imageViewBeingMoved.relocate(mouseX+5, mouseY+5);
		}
	};
	
	private EventHandler<ActionEvent> endTurnEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	private EventHandler<ActionEvent> hintEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	};

	public void initHand(int playerIndex, ArrayList<Tile> tiles, ArrayList<Integer> order) {
		if (playerIndex == playerIndexToShow) {
			for (Tile t: tiles) {
				currentPlayer.addTile(newImageView(t.getColor(),t.getNumber()));
			}
			currentPlayer.sortedImages(order);
		}else {
			int p = this.getOtherPlayerIndex(playerIndex);
			for (int i = 0; i < tiles.size(); i++) {
				if (p == -1) continue;
				otherPlayers.get(p).add();
			}
		}
	}
}
