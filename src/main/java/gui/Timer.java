package gui;
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
    private String s = "";
    private final int TIMER = 120; 
    private int seconds;

    private Label label = new Label("time left:" + seconds+ "s");

    public Timer(EventHandler<ActionEvent> endTurnEventHandler) {
        label.setFont(javafx.scene.text.Font.font(20));
        getChildren().add(label);
        animation = new Timeline(new KeyFrame(Duration.millis(1000), e -> timelabel()));
        animation.setOnFinished(endTurnEventHandler);
    }
    
    public void timelabel() {
    	seconds--;
        s = "time left:" + seconds + "s";
        label.setText(s);
    }
    
    public void restart() {
    	seconds = TIMER;
    	animation.setCycleCount(seconds);
    	//animation.setOnFinished(b);
    	animation.play();
    }
    
    public void stop() {
    	animation.stop();
    }
}
