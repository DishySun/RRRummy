package gui;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Button;
import javafx.event.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

@SuppressWarnings("restriction")
public class HomeScreen extends Application{
	
	private CurrentPlayerPane currentPlayer;
	private OtherPlayerPane otherPlayer;
	private MeldImagePane melds;
	private Scene scene;
	protected double orgSceneX;
	protected double orgSceneY;
	protected double orgTranslateX;
	protected double orgTranslateY;
	protected ImageView imageViewBeingMoved;
	private Pane p;
	
	public void init() {
		currentPlayer = new CurrentPlayerPane("Bill");
		currentPlayer.relocate(150, 500);
		try {
			otherPlayer = new OtherPlayerPane("Computer hard", 3, new Image(new FileInputStream("src/main/resources/Tiles/Back.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("no Back.png found");
			System.exit(-1);
		}
		otherPlayer.relocate(10, 100);
		melds = new MeldImagePane();
		melds.relocate(200, 150);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		p = new Pane();
		Button b = new Button("add");
		b.relocate(200, 200);
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					ImageView temp = new ImageView(new Image(new FileInputStream("src/main/resources/Tiles/Back.png")));
					temp.setOnMouseClicked(imageViewOnMouseClickedEventHandler);
					currentPlayer.addTile(temp);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
			}
		});
		p.getChildren().addAll(currentPlayer, otherPlayer, melds, b);
		scene = new Scene(p, 800, 600);
		primaryStage.setTitle("My Window");
	    primaryStage.setScene(scene);
	    primaryStage.show();	
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private EventHandler<MouseEvent> imageViewOnMouseClickedEventHandler = new EventHandler<MouseEvent>() {
		@Override
        public void handle(MouseEvent t) {
			if (imageViewBeingMoved == null) {
				imageViewBeingMoved = (ImageView)(t.getSource());
	            orgSceneX = t.getSceneX();
	            orgSceneY = t.getSceneY();
	            orgTranslateX = imageViewBeingMoved.getTranslateX();
	            orgTranslateY = imageViewBeingMoved.getTranslateY();
	            
	            
	            //((TileImagePane)((ImageView)t.getSource()).getParent()).remove(imageViewBeingMoved);
	            p.getChildren().add(imageViewBeingMoved);
	            imageViewBeingMoved.relocate(orgSceneX-10, orgSceneY-15);
	            imageViewBeingMoved.getScene().setOnMouseMoved(imageViewOnMouseMovedEventHandler);
			}else {
				imageViewBeingMoved.getScene().setOnMouseMoved(null);
				melds.add(imageViewBeingMoved, 0, true);
				imageViewBeingMoved = null;
				System.out.println("ggggg");
			}
        }
	};
	
	private EventHandler<MouseEvent> imageViewOnMouseMovedEventHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			double offsetX = t.getSceneX();
            double offsetY = t.getSceneY();

            imageViewBeingMoved.relocate(offsetX-10, offsetY-15);
		}
	};

}
