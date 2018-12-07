package gui;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import players.*;

@SuppressWarnings("restriction")
public class GuiTest extends Application{

	private Scene scene;
	@Override
	public void start(Stage primaryStage) throws Exception {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player("A"));
		players.add(new Player("B"));
		players.add(new Player("C"));
		players.add(new Player("D"));
		//RiggingPane playerCreator = new RiggingPane(players, null);
		PlayerCreator pc = new PlayerCreator();
		scene = new Scene(pc,800, 700);
		primaryStage.setTitle("Test Screen");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}

}
