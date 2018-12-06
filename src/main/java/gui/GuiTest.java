package gui;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

@SuppressWarnings("restriction")
public class GuiTest extends Application{

	private Scene scene;
	@Override
	public void start(Stage primaryStage) throws Exception {
		PlayerCreator playerCreator = new PlayerCreator();
		scene = new Scene(playerCreator,800, 600);
		primaryStage.setTitle("Test Screen");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}

}
