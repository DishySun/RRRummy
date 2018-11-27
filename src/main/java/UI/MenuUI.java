package UI;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenuUI extends Application{
	private Pane canvas;
	private Scene scene;
	private Button Play;
	private Button Credits;
	private Button Exit;
	private Button HPOK;
	private Button AIOK;
	private Button Ready;
	private Button ReturnMenu;
	private Label label,ailabel,hmlabel;
	public int playern, ain;
	public int hn;
	private String s;
	private ImageView bg1,r1;
	private int width = 515;
	private int height = 583;
	ArrayList<String> AllAIStrategy;
	ArrayList<String> AllPlayerName;
	//ArrayList<ChoiceBox <String>> AllAIStrategy;
	ArrayList<ChoiceBox <String>> HumanPlayerNum;
	ArrayList<ChoiceBox <String>> AIPlayerNum;
	ArrayList<ChoiceBox <String>> nStrategy;
	ChoiceBox <String> tempCB;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		initUI(primaryStage);
		GameMenu(primaryStage);
		
	}
	
	private void GameMenu(final Stage primaryStage) {
		
		Play.relocate(30, 30);
		Credits.relocate(30, height - 60);
		Exit.relocate(width - 60, height - 60);
		Play.setOnAction(new EventHandler<ActionEvent>() {
 
			@Override
			public void handle(ActionEvent event) {
				canvas.getChildren().removeAll(label,Play,Credits,Exit,bg1,r1);
				label.setText("Please select the number of Player and AI");
				label.setFont(Font.font("Serif",FontWeight.NORMAL, 20));
				label.relocate(20, 20);
				hmlabel.setText("Human Player");
				hmlabel.setFont(Font.font("Serif",FontWeight.NORMAL, 20));
				hmlabel.relocate(90, 360);
				ailabel.setText("AI Player");
				ailabel.setFont(Font.font("Serif",FontWeight.NORMAL, 20));
				ailabel.relocate(330, 360);
				HumanPlayerNum = new ArrayList<ChoiceBox<String>>();
				AIPlayerNum = new ArrayList<ChoiceBox<String>>();
				//AllAIStrategy = new ArrayList<ChoiceBox<String>>();
				Image h1 = null;
				Image a1 = null;
				try {
					h1 = new Image(new FileInputStream("src/main/resources/HM.png"));
					a1 = new Image(new FileInputStream("src/main/resources/AI.png"));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        ImageView human1 = new ImageView(h1);
		        human1.relocate(60, 120);
		        human1.setFitWidth(166);
		        human1.setFitHeight(235);        
		        ImageView ai1 = new ImageView(a1);
		        ai1.relocate(width - 230, 120);
		        ai1.setFitWidth(165);
		        ai1.setFitHeight(233);
				ChoiceBox<String> hpCB = new ChoiceBox<String>();
				for(int playern=0;playern<4;playern++) {
	            hpCB.getItems().add(""+(playern+1));
				}
	        	hpCB.setLayoutX(130);
	        	hpCB.setLayoutY(400);
	        	HumanPlayerNum.add(hpCB);
	        	canvas.getChildren().addAll(HumanPlayerNum);
	        	canvas.getChildren().addAll(label, human1,ai1,hmlabel,ailabel);
	        	canvas.getChildren().addAll(ReturnMenu);
	        	canvas.getChildren().addAll(HPOK,AIOK);
	     
	        	ChoiceBox<String> apCB = new ChoiceBox<String>();
	        	for(int ain=0;ain<4;ain++) {
		            apCB.getItems().add(""+(ain+1));
					}
		        	apCB.setLayoutX(350);
		        	apCB.setLayoutY(400);
		        	AIPlayerNum.add(apCB);
		        	canvas.getChildren().addAll(AIPlayerNum);
		        	HPOK.relocate(130, 500);
		        	HPOK.setOnAction(new EventHandler<ActionEvent>() {

		    			@Override
		    			public void handle(ActionEvent event) {
		    				String h = hpCB.getValue();
		    				hn = Integer.parseInt(h);	
		    				for (int i = 0; i< hn; i++) {
		    				TextInputDialog dialog = new TextInputDialog();
		    				dialog.setTitle("Player Name Input");
		    				dialog.setHeaderText("Player " + (i+1) + ", Could you tell me your name please?");
		    				dialog.setContentText("Please enter your name:");
		    				Optional<String> result = dialog.showAndWait();
		    				//AllPlayerName = new ArrayList<String>();
		    				//AllPlayerName.add(result);
		    				System.out.println(result);
		    				}
		    			}});
		        	AIOK.relocate(350, 500);
		        	AIOK.setOnAction(new EventHandler<ActionEvent>() {

		    			@Override
		    			public void handle(ActionEvent event) {
		    				
		    				String a = apCB.getValue();
		    				int in = Integer.parseInt(a);			
		    				nStrategy = new ArrayList<ChoiceBox<String>>();
		   	             for(int i = 0; i < in;i++)
		   	             {
		   	             	tempCB = new ChoiceBox<String>();
		   	                //AllAIStrategy.add(a);
		   	             	for(int j=0; j<4;j++)
		   	             	{
		   	                 	tempCB.getItems().add("Strategy " + (j+1));
		   	             	}
		   	             	tempCB.setValue("Strategy 1");
		   	             	tempCB.setLayoutX(100*(i+1)-50);
		   	             	tempCB.setLayoutY(450);
		   	                nStrategy.add(tempCB);
		   	             s = nStrategy.get(0).getValue();
		   	          System.out.println(s);
		   	          //AllAIStrategy = new ArrayList<String>();
		   	             //AllAIStrategy.add(s);
		   	          //System.out.println(AllAIStrategy);
		   	             	//nStrategy.add(tempCB);
		   	             //System.out.println(nStrategy);
		   	             	nStrategy.get(i).setVisible(true);
		   	             //System.out.println(nStrategy.get(0));
		   	             }
		   	               //String s = tempCB.getValue();
		   	               
	    				    //int s = Integer.parseInt(s);
		    				canvas.getChildren().addAll(nStrategy);
		    				canvas.getChildren().removeAll(AIOK,HPOK,ReturnMenu);
		    				Ready.relocate(230, 500);
		    				canvas.getChildren().addAll(Ready);
		    				Ready.setOnAction(new EventHandler<ActionEvent>() {

				    			@Override
				    			public void handle(ActionEvent event) {
				    				//AllAIStrategy.add(s);
				    				//AllAIStrategy = new ArrayList<String>();
				    				//String s = tempCB.getValue();
				    				//AllAIStrategy.add(s);
				    				System.out.println(s);
				    				System.out.println(AllAIStrategy);
				    			}});
		    			}});
		        	ReturnMenu.relocate(350,550);
		        	ReturnMenu.setOnAction(new EventHandler<ActionEvent>() {

		    			@Override
		    			public void handle(ActionEvent event) {
		    				canvas.getChildren().removeAll(label);
		    				canvas.getChildren().removeAll(AIPlayerNum);
		    				canvas.getChildren().removeAll(HumanPlayerNum);
		    				canvas.getChildren().removeAll(ReturnMenu,HPOK,AIOK,human1,ai1,ailabel,hmlabel);
		    				canvas.getChildren().add(bg1);
		    				canvas.getChildren().addAll(Play,Credits,Exit,r1);
		    			}});
			}});
		Credits.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Credits");
				alert.setHeaderText("Contributors:");
				alert.setContentText("Group 27:\nHaiyue Sun\nJingkai Lin\nDe Tao\nYuanming Xiao\n");

				alert.showAndWait();
			}});
		Exit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			    primaryStage.close();
			}});
		Image bg = null;
		Image r = null;
		try {
			bg = new Image(new FileInputStream("src/main/resources/BG.png"));
			r = new Image(new FileInputStream("src/main/resources/RY.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        bg1 = new ImageView(bg);
        r1 = new ImageView(r);
        r1.setFitWidth(80);
        r1.setFitHeight(80);
        r1.relocate(width - 110,30);
        canvas.getChildren().add(bg1);
		canvas.getChildren().addAll(Play,Credits,Exit,r1);
		
		
	}
	
	private void initUI(Stage primaryStage) throws FileNotFoundException {
		canvas = new Pane();
		scene = new Scene(canvas, width, height);
		label = new Label();
		hmlabel = new Label();
		ailabel = new Label();
		Play = new Button("Play");
		Credits = new Button("Credits");
		Exit = new Button("Exit");
		HPOK = new Button("OK");
		AIOK = new Button("OK");
		Ready = new Button("Ready?!");
		ReturnMenu = new Button("Return to Menu");
		primaryStage.setScene(scene);
		primaryStage.setTitle("COMP 3004 Game");
		primaryStage.show();
		
	}
	
	public static void main (String[] args) {
		launch(args);
	}

}
