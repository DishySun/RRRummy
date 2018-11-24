package UI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;

public class MenuUI extends Application{
	private Pane canvas;
	private Scene scene;
	private Button Play;
	private Button Credits;
	private Button Exit;
	private Button HPOK;
	private Label label;
	ArrayList<ChoiceBox <String>> AllAIStrategy;
	ArrayList<ChoiceBox <String>> HumanPlayerNum;
	ArrayList<ChoiceBox <String>> AIPlayerNum;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		initUI(primaryStage);
		GameMenu(primaryStage);
		
	}
	
	private void GameMenu(final Stage primaryStage) {
		label.setText("Welcome to COMP3004 RRRummy Game!\nPlease select game options below.");
		label.setFont(Font.font("Serif",FontWeight.NORMAL, 20));
		label.relocate(20, 20);
		
		Play.relocate(200, 100);
		Credits.relocate(200, 170);
		Exit.relocate(200, 240);
		Play.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				canvas.getChildren().removeAll(label,Play,Credits,Exit);
				label.setText("Please select the number of Player and AI");
				label.setFont(Font.font("Serif",FontWeight.NORMAL, 20));
				label.relocate(20, 20);
				HumanPlayerNum = new ArrayList<ChoiceBox<String>>();
				AIPlayerNum = new ArrayList<ChoiceBox<String>>();
				AllAIStrategy = new ArrayList<ChoiceBox<String>>();
				//for(j=0)
				ChoiceBox<String> hpCB = new ChoiceBox<String>();
				for(int i=0;i<4;i++) {
	            hpCB.getItems().add(""+(i+1));
				}
	        	//hpCB.setValue("Strategy 1");
	        	hpCB.setLayoutX(600);
	        	hpCB.setLayoutY(500);
	        	HumanPlayerNum.add(hpCB);
	        	//System.out.println(hpCB);
	        	canvas.getChildren().addAll(HumanPlayerNum);
	        	canvas.getChildren().addAll(label);
	        	//allaiStrategy.get(i).setDisable(false);
	        	//HumanPlayerNum.setVisible(true);
				/*TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Text Input Dialog");
				dialog.setHeaderText("Could you tell me your name please?");
				dialog.setContentText("Please enter your name:");
				Optional<String> result = dialog.showAndWait();*/
	        	ChoiceBox<String> apCB = new ChoiceBox<String>();
	        	for(int i=0;i<4;i++) {
		            apCB.getItems().add(""+(i+1));
					}
		        	//hpCB.setValue("Strategy 1");
		        	apCB.setLayoutX(400);
		        	apCB.setLayoutY(500);
		        	AIPlayerNum.add(apCB);
		        	canvas.getChildren().addAll(AIPlayerNum);
		        	HPOK.relocate(600, 600);
		        	HPOK.setOnAction(new EventHandler<ActionEvent>() {

		    			@Override
		    			public void handle(ActionEvent event) {
		    				
		    			}});
			}});
		Credits.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Credis");
				alert.setHeaderText("Contributors:");
				alert.setContentText("Group 27:\nHaiyun Sun\nJingkai Lin\nDe Tao\nYuanming Xiao\n");

				alert.showAndWait();
			}});
		Exit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			    primaryStage.close();
			}});
		canvas.getChildren().addAll(label,Play,Credits,Exit,HPOK);
		
	}
	
	private void initUI(Stage primaryStage) throws FileNotFoundException {
		canvas = new Pane();
		scene = new Scene(canvas, 1280, 720);
		label = new Label();
		Play = new Button("Play");
		Credits = new Button("Credits");
		Exit = new Button("Exit");
		HPOK = new Button("OK");
		//createContent();
		primaryStage.setScene(scene);
		primaryStage.setTitle("COMP 3004 Game");
		primaryStage.show();
		//addContent();
		
	}
	
	public static void main (String[] args) {
		launch(args);
	}

}
