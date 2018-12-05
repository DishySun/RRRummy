package gui;
import game.GameControl;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

@SuppressWarnings("restriction")
public class Timer extends Pane {

    private Timeline animation;
    private String S = "";
    private int seconds = 120;

    Label label = new Label("time left:" + seconds+ "s");


    public Timer(EventHandler<ActionEvent> endTurnEventHandler) {
        label.setFont(javafx.scene.text.Font.font(20));
        getChildren().add(label);
        label.relocate(100, 0);
        animation = new Timeline(new KeyFrame(Duration.millis(1000), e -> timelabel()));
        animation.setCycleCount(seconds);
        animation.play();
        animation.setOnFinished(endTurnEventHandler);
    }
    
    public void timelabel() {
    	seconds--;
        S = "time left:" + seconds + "s";
        label.setText(S);
    }
   
}
