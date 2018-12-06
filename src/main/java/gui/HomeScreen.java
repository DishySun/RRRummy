package gui;
import javafx.application.Application;
import javafx.stage.Stage;
import players.*;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import gui_game.GameControl;


@SuppressWarnings("restriction")
public class HomeScreen extends Application{
	
	ArrayList<Player> players;
	GameControl gameControl;
	Scene scene;
	ArrayList<Image> red;
	ArrayList<Image> blue;
	ArrayList<Image> green;
	ArrayList<Image> orange;
	Image joker;
	Image back;

	
	public void init() {
		try {
			this.initImages();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		players = new ArrayList<Player>();
		players.add(new AI(new StrategyZero()));
		players.add(new AI(new StrategyFour()));
		players.add(new Player("Bill"));
		players.add(new AI(new StrategyTwo()));
		gameControl = new GameControl(players, 2, red, blue,green,orange,joker,back);

	}
	
	private void initImages() throws FileNotFoundException {
		String path = "src/main/resources/Tiles/";
		red = new ArrayList<Image>();
		blue = new ArrayList<Image>();
		green = new ArrayList<Image>();
		orange = new ArrayList<Image>();
		for (int i = 1; i <= 13; i++) {
			red.add(new Image(new FileInputStream(path+"R"+Integer.toString(i)+".png")));
			blue.add(new Image(new FileInputStream(path+"B"+Integer.toString(i)+".png")));
			green.add(new Image(new FileInputStream(path+"G"+Integer.toString(i)+".png")));
			orange.add(new Image(new FileInputStream(path+"O"+Integer.toString(i)+".png")));
		}
		joker = new Image(new FileInputStream(path + "Joker.png"));
		back = new Image(new FileInputStream(path + "Back.png"));
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		scene = new Scene(gameControl.getTablePane(), 900, 600);
		primaryStage.setTitle("Home Screen Test");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String args[]){ 
	      launch(args); 
	} 

}
