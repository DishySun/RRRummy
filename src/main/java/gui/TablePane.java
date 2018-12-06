package gui;

//layout
import javafx.scene.layout.Pane;
import players.Player;
import rrrummy.Tile;
import javafx.scene.control.Label;
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
	private ArrayList<Player> players;
	private MeldImagePane melds;
	private ArrayList<Image> red;
	private ArrayList<Image> blue;
	private ArrayList<Image> green;
	private ArrayList<Image> orange;
	private Image joker;
	private ImageView newlyPlayedTile; //pointer only
	private ImageView imageViewBeingSelected; //pointer only
	private ImageView imageViewBeingMoved; 
	private ScaleTransition selectingAnimation;
	private ColorAdjust newlyPlayedTileEffect;
	private GameControl gameControl;
	private int playerIndexToShow;
	private double mouseX;
	private double mouseY;
	private Timer timer;
	private Label hintLabel;
	
	public TablePane(GameControl gc, final ArrayList<Player> players, int playerIndexToShow, ArrayList<Image> red, ArrayList<Image> blue, ArrayList<Image> green, ArrayList<Image> orange, Image joker, Image back) {
		this.gameControl = gc;
		this.playerIndexToShow = playerIndexToShow;
		this.red = red;
		this.blue = blue;
		this.green = green;
		this.orange = orange;
		this.joker = joker;
		this.players = players;
		this.melds = new MeldImagePane(tableOnMouseClicked);
		this.melds.relocate(120, 120);
		this.otherPlayers = new ArrayList<OtherPlayerPane>();
		//this.melds.setOnMouseClicked(tableOnMouseClicked);
		this.imageViewBeingMoved = new ImageView();
		this.timer = new Timer(null);
		timer.relocate(20, 520);
		this.hintLabel = new Label();
		hintLabel.relocate(700, 500);
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
		this.getChildren().addAll(melds,currentPlayer,imageViewBeingMoved,timer,hintLabel);
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
			ImageView temp = this.newImageView(t.getColor(), t.getNumber());
			setNewlyPlayed(temp);
			melds.add(temp);
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
	}
	public void replace(int meldIndex, int tileIndex) {
		melds.replace(imageViewBeingSelected, meldIndex, tileIndex);
		melds.add(imageViewBeingSelected);
		drop();
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
		//drop();
	}
	
	private int getOtherPlayerIndex(int currentPlayer) {
		return (currentPlayer + otherPlayers.size() +1 - playerIndexToShow) % (otherPlayers.size() +1) -1;
	}
	
	
	private ImageView newImageView(Tile.Color c, int tileNumber) {
		ImageView iv = new ImageView();
		//iv.setOnMouseClicked(imageViewOnMouseClickedEventHandler);
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
			hintLabel.setText(null);
			ImageView source = (ImageView) t.getSource();
			mouseX = t.getSceneX();
			mouseY = t.getSceneY();
			int meldIndex = ((TileImagePane)source.getParent()).getMeldIndex();
			int tileIndex = ((TileImagePane)source.getParent()).getTileIndex(source);
			int size = ((TileImagePane)source.getParent()).size();
			if (imageViewBeingSelected == null) {
				//選択中のテール　ある
				if(meldIndex == -1) {
					//手札から
					pick(source);
				}else {
					//メルドから
					if (size == 0) return;
					if (gameControl.isSet(meldIndex) || tileIndex == 0 || tileIndex == size-1) {
						//メルドの最初か最末　それから選択中のはsetの場合　選択する
						pick(source);
					}else {
						//そうではないなら cutする
						gameControl.cut(meldIndex, tileIndex);
					}
				}
			}else {
				//選択中のテール　ない
				int seletcedMeldIndex = ((TileImagePane)imageViewBeingSelected.getParent()).getMeldIndex();
				int selectedTileIndex = ((TileImagePane)imageViewBeingSelected.getParent()).getTileIndex(imageViewBeingSelected);
				if (meldIndex == seletcedMeldIndex) {
					//元の場所に戻す場合
					drop();
				}else if (seletcedMeldIndex == -1) {
					//選択中のテールは手札にある場合
					//先ずはreplaceを試す
					if (!gameControl.replace(selectedTileIndex, meldIndex, tileIndex)) {
						//失敗たら
						//手札からplayする
						boolean headOrTail = (tileIndex < size /2);
						gameControl.play(selectedTileIndex, meldIndex, headOrTail);
					}
				}else {
					//選択中のテールはtableにある
					//moveしかない
					if (meldIndex == -1) return;
					boolean headOrTail = (tileIndex < size /2);
					gameControl.move(seletcedMeldIndex, selectedTileIndex, meldIndex, headOrTail);
				}
			}
        }
	};
	
	private EventHandler<MouseEvent> tableOnMouseClicked = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			if(imageViewBeingSelected == null) ;{
				int meldIndex = ((TileImagePane)imageViewBeingSelected.getParent()).getMeldIndex();
				int tileIndex = ((TileImagePane)imageViewBeingSelected.getParent()).getTileIndex(imageViewBeingSelected);
				int size = ((TileImagePane)imageViewBeingSelected.getParent()).size();
				if (size == 1) return;
				if (meldIndex == -1)gameControl.play(tileIndex);
				else {gameControl.cut(meldIndex, tileIndex);}
			}
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
			timer.stop();
			gameControl.endTurn();
		}
		
	};
	
	private EventHandler<ActionEvent> hintEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			hintLabel.setText(players.get(playerIndexToShow).getCommandString());
		}
		
	};

	public void initHand(int playerIndex, ArrayList<Tile> tiles, ArrayList<Integer> order) {
		if (playerIndex == playerIndexToShow) {
			for (Tile t: tiles) {
				currentPlayer.addTile(newImageView(t.getColor(),t.getNumber()));
			}
		}else {
			int p = this.getOtherPlayerIndex(playerIndex);
			for (int i = 0; i < tiles.size(); i++) {
				if (p == -1) continue;
				otherPlayers.get(p).add();
			}
		}
	}

	public void setAiTurn() {
		currentPlayer.endTurnEventHandlers();
		melds.clearEventHandler();
	}

	public void setHumanTurn(int currentPlayer2) {
		timer.restart();
		if (this.playerIndexToShow == currentPlayer2) {
			currentPlayer.getTurnEventHandlers(imageViewOnMouseClickedEventHandler);
			melds.setOnClickedHandler(imageViewOnMouseClickedEventHandler);
		}else {this.switchPlayer(currentPlayer2);}
	}

	private void switchPlayer(int currentPlayer2) {
		playerIndexToShow = currentPlayer2;
		currentPlayer = new CurrentPlayerPane(players.get(playerIndexToShow).getName(), endTurnEventHandler, hintEventHandler);
		for (int i = 0; i < players.get(playerIndexToShow).handSize(); i++) {
			Tile t = players.get(playerIndexToShow).getHand(i);
			currentPlayer.addTile(this.newImageView(t.getColor(),t.getNumber()));
		}
		for (Player p : players) {
			if (players.indexOf(p) == this.playerIndexToShow)continue;
			int otherIndex = this.getOtherPlayerIndex(players.indexOf(p));
			otherPlayers.get(otherIndex).setTileNumber(p.handSize());
		}
	}

	public void drawTile(int currentPlayer2, Tile t, ArrayList<Integer> order) {
		if (currentPlayer2 == playerIndexToShow) this.playerDrawTile(t);
		else this.otherDrawTile(this.getOtherPlayerIndex(currentPlayer2));
		currentPlayer.sortedImages(order);
	}
}
