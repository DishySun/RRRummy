package UI;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

import game.GameControl;
import game.View;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import rrrummy.Game;
import javafx.scene.control.TextField;

//@SuppressWarnings("restriction")
public class NewGameControlPane extends Pane{

	private RiggingPane r;
	private Button Play,Rigging,HPOK,AIOK;
	ArrayList<ChoiceBox <String>> HumanPlayerNum;
	ArrayList<ChoiceBox <String>> AIPlayerNum;
	private Label label,ailabel,hmlabel;
	private int hn,in;
	ArrayList<ChoiceBox <String>> nStrategy;
	ChoiceBox <String> tempCB,hpCB;
	private String Stra;
	Optional<String> playerNames;
	ArrayList<String> playersNameList;
	ArrayList<String> StrategyList;
	
	public NewGameControlPane (){
		initUI();
		playersNameList = new ArrayList<String>();
		StrategyList = new ArrayList<String>();
				
		Play.relocate(800, 400);
		getChildren().addAll(Rigging,Play);
		Rigging.relocate(800, 600);		
		label.setText("Please select 1-4 Player or 1-4 AI");
		label.setFont(Font.font("Serif",FontWeight.NORMAL, 20));
		label.relocate(20, 20);
		hmlabel.setText("Human Player");
		hmlabel.setFont(Font.font("Serif",FontWeight.NORMAL, 20));
		hmlabel.relocate(90, 360);
		ailabel.setText("AI Player");
		ailabel.setFont(Font.font("Serif",FontWeight.NORMAL, 20));
		ailabel.relocate(330, 360);
		getChildren().addAll(label,hmlabel,ailabel);
		HumanPlayerNum = new ArrayList<ChoiceBox<String>>();
		AIPlayerNum = new ArrayList<ChoiceBox<String>>();
		
		hpCB = new ChoiceBox<String>();
		for(int i=0;i<4;i++) {
        hpCB.getItems().add(""+(i+1));
		}
    	hpCB.setLayoutX(130);
    	hpCB.setLayoutY(400);
    	HumanPlayerNum.add(hpCB);
    	getChildren().addAll(HumanPlayerNum);
    	//public int getHn() {return hn;}
    	ChoiceBox<String> apCB = new ChoiceBox<String>();
    	for(int i=0;i<4;i++) {
            apCB.getItems().add(""+(i+1));
			}
        	apCB.setLayoutX(350);
        	apCB.setLayoutY(400);
        	AIPlayerNum.add(apCB);
        	getChildren().addAll(AIPlayerNum);
        	getChildren().addAll(HPOK,AIOK);
        	HPOK.relocate(130, 500);
        	HPOK.setOnAction(new EventHandler<ActionEvent>() {

    			@Override
    			public void handle(ActionEvent event) {
    				String h = hpCB.getValue();
    				hn = Integer.parseInt(h);
    				 //System.out.println(hn);
    				for (int i = 0; i< hn; i++) {
    				TextInputDialog dialog = new TextInputDialog();
    				dialog.setTitle("Player Name Input");
    				dialog.setHeaderText("Player " + (i+1) + ", Could you tell me your name please?");
    				dialog.setContentText("Please enter your name:");
    				playerNames = dialog.showAndWait();
    				playersNameList.add(playerNames.get());
    				//System.out.println(playerNames.get());
    				//System.out.println(playerNames);
    				}
    			}});
        	AIOK.relocate(350, 500);
        	AIOK.setOnAction(new EventHandler<ActionEvent>() {
    			@Override
    			public void handle(ActionEvent event) {
            		if(nStrategy != null)
            			getChildren().removeAll(nStrategy);
    				String a = apCB.getValue();
    			    in = Integer.parseInt(a);
    				nStrategy = new ArrayList<ChoiceBox<String>>();
   	                String[] Stra = new String[] { "Strategy 1", "Strategy 2", "Strategy 3", "Strategy 4" };
   	             for(int item = 0; item < in;item++)
   	             {
   	             	tempCB = new ChoiceBox<String>(FXCollections.observableArrayList("Strategy 1", "Strategy 2", "Strategy 3", "Strategy 4"));
   	             	tempCB.setValue("Strategy 1");
   	             	tempCB.setLayoutX(100*(item+1)-50);
   	             	tempCB.setLayoutY(450);
   	                nStrategy.add(tempCB);

   	                nStrategy.get(item).getSelectionModel().selectedIndexProperty()
		   	        	.addListener(new ChangeListener<Number>() {
			   	          public void changed(ObservableValue ov, Number value, Number new_value) {
			   	        	System.out.println(Stra[new_value.intValue()].toString());
			   	        	StrategyList.add(Stra[new_value.intValue()].toString());
					}});
   	             }			   	    
    				getChildren().addAll(nStrategy);	
    			}});
		
		Play.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(hn+in <=4 && hn+in >=2) {
					playGame();
				}
			}});
		Rigging.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(hn+in <=4 && hn+in >=2) {
					rigging();
				}
			}});
	}
	
	private void initUI() {
		label = new Label();
		hmlabel = new Label();
		ailabel = new Label();
		HPOK = new Button("OK");
		AIOK = new Button("OK");
		Play = new Button("Play");	
		Rigging = new Button("Rigging");
	}
	
	public int getPNumber() {
		System.out.print("How many AI players would you like to have (1-3): ");
		while (true) {
			String str = hpCB.getValue();
			try {
				int aiNumber = Integer.valueOf(str);
				return aiNumber;
			}catch (Exception e) {
				System.out.print("err: An integer input is expected: ");
			}
		}
	}

	public void playGame() {
		getChildren().clear();
		//System.out.println("play");
		//System.out.println("NewGame "+playersNameList);
		GameControl game = new GameControl(playersNameList,StrategyList);
		//game.newGame();
		//game.startGame();
		
	};
	
	public void rigging() {
		r =  new RiggingPane(hn,in,playersNameList,StrategyList);
		getChildren().removeAll(Rigging,Play,HPOK,AIOK);
		getChildren().removeAll(label,hmlabel,ailabel);
		getChildren().removeAll(AIPlayerNum);
		getChildren().removeAll(HumanPlayerNum);
		if(nStrategy != null)
			getChildren().removeAll(nStrategy);
		getChildren().clear();
		getChildren().add(r);
	}
}
