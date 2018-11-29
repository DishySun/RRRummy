package UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TestScore extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        Button button = new Button();
        button.setText("Show Score Board");
        button.setOnAction(e -> new ScoreBoard().display("title", "message"));

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene=new Scene(layout,300,300);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
