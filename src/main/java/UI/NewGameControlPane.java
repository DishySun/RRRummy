package UI;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

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
import javafx.scene.control.TextField;

public class NewGameControlPane extends Pane{

	private RiggingPane r;
	private Button Play,Rigging,HPOK,AIOK;
	ArrayList<ChoiceBox <String>> HumanPlayerNum;
	ArrayList<ChoiceBox <String>> AIPlayerNum;
	private Label label,ailabel,hmlabel;
	private int hn,in;
	ArrayList<ChoiceBox <String>> nStrategy;
	ArrayList<String> PlayerName;
	ArrayList<String> nStra;
	ChoiceBox <String> tempCB,hpCB;
	private String Stra;
	

	public NewGameControlPane (){
		
		initUI();
		
		
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
    				Optional<String> result = dialog.showAndWait();
    				System.out.println(result);
    				}
    			}});
        	AIOK.relocate(350, 500);
        	AIOK.setOnAction(new EventHandler<ActionEvent>() {

    			@Override
    			public void handle(ActionEvent event) {
    				
    				String a = apCB.getValue();
    			    in = Integer.parseInt(a);
    			    //System.out.println(in);
    				nStrategy = new ArrayList<ChoiceBox<String>>();
    				ArrayList<String> Stra1 = new ArrayList<String>();
    				for(int i = 0; i < 4;i++) {
    				Stra1.add("Strategy " + i);
    				//System.out.println(Stra1);
    				}
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
		   	           Stra = Stra1.get(new_value.intValue()+1);
		   	        	System.out.println(Stra);
					}});
   	             }			   	    
    				getChildren().addAll(nStrategy);	
    			}});
		
		Play.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
			}});
		Rigging.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				r =  new RiggingPane(hn+in,PlayerName,nStra);
				getChildren().removeAll(Rigging,Play,HPOK,AIOK);
				getChildren().removeAll(label,hmlabel,ailabel);
				getChildren().removeAll(AIPlayerNum);
				getChildren().removeAll(HumanPlayerNum);
				getChildren().removeAll(nStrategy);
				getChildren().add(r);
				
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

}
