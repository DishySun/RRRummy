package UI;

import gui.Timer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TestScene extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("test scence");
        Timer timer = new Timer();
//        btn.setText("Hello World");

        StackPane root = new StackPane();
        root.getChildren().add(timer);
        primaryStage.setScene(new Scene(root, 300, 250));
        
   
        primaryStage.show();
    }
}
