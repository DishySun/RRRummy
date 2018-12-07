package gui;

//layout
import javafx.scene.layout.Pane;
import players.Player;
import rrrummy.Tile;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.event.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

//utils
import java.util.ArrayList;
import java.util.HashMap;

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
	private HashMap<ImageView, Tile> imageMap;
	private Pane firstPane; 
	
	public TablePane(GameControl gc, final ArrayList<Player> players, int playerIndexToShow, ArrayList<Image> red, ArrayList<Image> blue, ArrayList<Image> green, ArrayList<Image> orange, Image joker, Image back) {
		this.gameControl = gc;
		imageMap = new HashMap<ImageView, Tile>();
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
		this.timer = new Timer(endTurnEventHandler);
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
		
		firstPane = new Pane();
		Button playButton = new Button("Play");
		playButton.relocate(400, 300);
		playButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				getChildren().remove(firstPane);
				gameControl.playButton();
				getChildren().remove(event.getSource());
			}});
		
		
		this.getChildren().addAll(otherPlayers);
		this.getChildren().addAll(melds,currentPlayer,imageViewBeingMoved,timer,hintLabel,firstPane,playButton);
		setAiTurn();
	}
	
	public void decideFirstDraw(Tile t, int playerNumber) {
		ImageView iv = newImageView(t);
		iv.setOnMouseClicked(null);
		iv.setScaleX(3.0);
		iv.setScaleY(3.0);
		if (playerNumber == playerIndexToShow) {
			iv.relocate(400, 400);
		}else {
			int i = this.getOtherPlayerIndex(playerNumber);
			switch(i) {
			case 0:
				iv.relocate(300, 300);
				break;
			case 1:
				iv.relocate(400, 200);
				break;
			case 2:
				iv.relocate(500, 300);
				break;
			}
		}
		firstPane.getChildren().add(iv);
	}
	
	public void anounceWhoFirst(int number) {
		Label temp = new Label("This player play first");
		temp.setTextFill(Color.web("purple"));
		temp.setStyle("-fx-background-color: pink;");
		temp.setFont(Font.font("Cambria", 30));
		if (number == playerIndexToShow) {
			temp.relocate(300, 500);
		}else {
			int i = this.getOtherPlayerIndex(number);
			switch(i) {
			case 0:
				temp.relocate(100, 300);
				break;
			case 1:
				temp.relocate(300, 50);
				break;
			case 2:
				temp.relocate(450, 300);
				break;
			}
		}
		firstPane.getChildren().add(temp);
	}
	
	public void playerDrawTile(Tile t) {
		currentPlayer.addTile(newImageView(t));
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
			ImageView temp = this.newImageView(t);
			setNewlyPlayed(temp);
			melds.add(temp);
		}
	}
	
	public void move(int fromMeldIndex, int fromTileIndex, int toMeldIndex, boolean headOrTail) {
		ImageView iv = melds.getImageView(fromMeldIndex, fromTileIndex);
		melds.add(iv, toMeldIndex, headOrTail);
		this.setNewlyPlayed(iv);
		if (imageViewBeingSelected != null) drop();
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
		this.setNewlyPlayed(this.newImageView(playedTile));
		
		melds.add(newlyPlayedTile, meldIndex, headOrTail);
		//drop();
	}
	
	private int getOtherPlayerIndex(int currentPlayer) {
		return (currentPlayer + otherPlayers.size() +1 - playerIndexToShow) % (otherPlayers.size() +1) -1;
	}
	
	
	private ImageView newImageView(Tile t) {
		ImageView iv = new ImageView();
		Tile.Color c = t.getColor();
		int tileNumber = t.getNumber();
		imageMap.put(iv, t);
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
		currentPlayer.getEndButton().setDisable(true);
	}
	
	private void drop() {
		selectAnimationStop();
		disactiveMovingImageView();
		currentPlayer.relocateAll();
		currentPlayer.getEndButton().setDisable(false);
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
				//閬告姙涓伄銉嗐兗銉��銇傘倠
				if(meldIndex == -1) {
					//鎵嬫湱銇嬨倝
					pick(source);
				}else {
					//銉°儷銉夈亱銈�
					if (size == 0) return;
					if (gameControl.isSet(meldIndex) || tileIndex == 0 || tileIndex == size-1) {
						//銉°儷銉夈伄鏈�鍒濄亱鏈�鏈��銇濄倢銇嬨倝閬告姙涓伄銇痵et銇牬鍚堛��閬告姙銇欍倠
						pick(source);
					}else {
						//銇濄亞銇с伅銇亜銇倝 cut銇欍倠
						gameControl.cut(meldIndex, tileIndex);
					}
				}
			}else {
				//閬告姙涓伄銉嗐兗銉��銇亜
				int seletcedMeldIndex = ((TileImagePane)imageViewBeingSelected.getParent()).getMeldIndex();
				int selectedTileIndex = ((TileImagePane)imageViewBeingSelected.getParent()).getTileIndex(imageViewBeingSelected);
				if (meldIndex == seletcedMeldIndex) {
					//鍏冦伄鍫存墍銇埢銇欏牬鍚�
					drop();
				}else if (seletcedMeldIndex == -1) {
					//閬告姙涓伄銉嗐兗銉伅鎵嬫湱銇亗銈嬪牬鍚�
					//鍏堛仛銇痳eplace銈掕│銇�
					if (!gameControl.replace(selectedTileIndex, meldIndex, tileIndex)) {
						//澶辨晽銇熴倝
						//鎵嬫湱銇嬨倝play銇欍倠
						boolean headOrTail = (tileIndex < size /2);
						gameControl.play(selectedTileIndex, meldIndex, headOrTail);
					}
				}else {
					//閬告姙涓伄銉嗐兗銉伅table銇亗銈�
					//move銇椼亱銇亜
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
			currentPlayer.endTurnEventHandlers();
			melds.clearEventHandler();
			gameControl.endTurn();
			//timer.stop();
		}
		
	};
	
	private EventHandler<ActionEvent> hintEventHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			hintLabel.setText(players.get(playerIndexToShow).getCommandString());
		}
		
	};

	public void initHand(int playerIndex, ArrayList<Tile> tiles) {
		if (playerIndex == playerIndexToShow) {
			for (Tile t: tiles) {
				currentPlayer.addTile(newImageView(t));
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
			currentPlayer.addTile(this.newImageView(t));
		}
		for (Player p : players) {
			if (players.indexOf(p) == this.playerIndexToShow)continue;
			int otherIndex = this.getOtherPlayerIndex(players.indexOf(p));
			otherPlayers.get(otherIndex).setTileNumber(p.handSize());
		}	
	}

	public void drawTile(int currentPlayer2, Tile t) {
		if (currentPlayer2 == playerIndexToShow) this.playerDrawTile(t);
		else this.otherDrawTile(this.getOtherPlayerIndex(currentPlayer2));
		currentPlayer.sortedImages(imageMap);
	}

	public void anaounceWinner(Player winner) {
		//this.getChildren().clear();
		currentPlayer.endTurnEventHandlers();
		melds.clearEventHandler();
		Label winnerLabel = new Label();
		winnerLabel.setText("Winner is : "+winner.getName());
		winnerLabel.relocate(400, 250);
		this.getChildren().add(winnerLabel);
	}
	
	public void updateHands(int playerIndex, ArrayList<Tile> hands) {
		currentPlayer.clearCurrentPlayerPane();
		initHand(playerIndex, hands);
	}
	
	public void updateTable(ArrayList<ArrayList<Tile>> table) {
		ArrayList<ArrayList<ImageView>> ivs = new ArrayList<ArrayList<ImageView>>();
		for (int i = 0; i < table.size(); i++) {
			ArrayList<ImageView> a = new ArrayList<ImageView>();
			for (Tile t: table.get(i)) {
				a.add(this.newImageView(t));
			}
			ivs.add(a);
		}
		melds.restore(ivs);
	}
	
}
