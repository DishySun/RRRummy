package gui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

@SuppressWarnings("restriction")
public class GuiTest extends Application{

	private Scene scene;
	@Override
	public void start(Stage primaryStage) throws Exception {
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
