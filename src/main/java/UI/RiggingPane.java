package UI;

import java.util.List;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Pane; 
import javafx.scene.control.TextField;

public class RiggingPane extends Pane{

	//private NewGameControlPane n;
	//private int pn,an;
	ListView<String> cards;
	private String selectTile;
	ObservableList<String> data;
	List<String> Hands;


	public RiggingPane(int totalPlayer, ArrayList<String> Stra, ArrayList<String> Pname){
		
		//n = new NewGameControlPane();
	    
		for(int i=0;i<totalPlayer;i++) {
		TextField pname = new TextField();
		pname.setLayoutX(30+i*180);
		pname.setLayoutY(100);
		pname.setDisable(true);
        ListView<String> playerhands = new ListView<String>();
        
        playerhands.setLayoutX(30+i*180);
        playerhands.setLayoutY(150);
        playerhands.setPrefSize(160, 270);
        
      
        Button add = new Button("Add");
        add.relocate(30+i*200, 500);

        add.setOnAction(event -> {
        	
        	selectTile = cards.getSelectionModel().getSelectedItem();
            if(selectTile != null) {
            playerhands.getItems().add(selectTile);
            }
        });
        Button remove = new Button("Remove");
        remove.relocate(30+i*200, 600);
        remove.setOnAction(event -> {
        selectTile = cards.getSelectionModel().getSelectedItem();
        if(selectTile != null) {
            playerhands.getItems().remove(selectTile);
        	cards.getItems().add(selectTile);
        	}
            });
		getChildren().addAll(pname,playerhands,add,remove);
		}
		data = FXCollections.observableArrayList();
		cards = new ListView<String>(data);
        
		cards.setLayoutX(800);
		cards.setLayoutY(150);
		cards.setPrefSize(160, 270);
		cards.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        for(int i=0;i<13;i++) {
        data.add("R"+ (i+1));
        data.add("G"+ (i+1));
        data.add("B"+ (i+1));
        data.add("O"+ (i+1));
        }
        for(int i=0;i<2;i++) {
        data.add("JOKER"+ (i+1));
        }      
       
        getChildren().addAll(cards);


        Button Confirm1 = new Button("Confirm");
        Confirm1.relocate(500, 650);
        getChildren().add(Confirm1);
		
	}
}
