package UI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane; 

@SuppressWarnings("restriction")
public class MenuPane extends Pane{

	private Button Play,Replay;
	private NewGameControlPane n;
    
	

	public MenuPane(){
		
		Play = new Button("Play");	
		Replay = new Button("Watch Replay");
		Play.relocate(500, 400);
		Replay.relocate(800, 400);
		getChildren().addAll(Play,Replay);
		
		Play.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				n = new NewGameControlPane();
				getChildren().add(n);
				getChildren().removeAll(Play,Replay);
			}});
		
		Replay.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
			}});
	}
}
