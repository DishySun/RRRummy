package UI;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.Pane; 
import javafx.scene.control.TextField;

public class RiggingPane extends Pane{

	//public static Parent root;
	//private Group canvas;
	//private Scene scene;
	private NewGameControlPane n;
	//private Button Confirm;
	private int pn,an;
	ListView<String> cards;
	private String oldText,newText;
	ObservableList<String> data;
	List<String> Hands;

	//public void fruitChanged(ObservableValue<? extends String> observable,String newValue)
	    //{
	        //oldText = oldValue == null ? "null" : oldValue.toString();
		       //newText = newValue.toString(); 
	        //System.out.println(oldText);
	        //System.out.println(newText);
	    //}


	public RiggingPane(){
		//pn = 4;
		//canvas = new Group();
		//scene = new Scene(canvas, w, h);
		//Stage stage = new Stage();
		//stage.setTitle("Rigging");
		//stage.setScene(scene);
		
		n = new NewGameControlPane();
	    
		//pn = n.getPNumber();
		//System.out.println(pn);
		for(int i=0;i<4;i++) {
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
        	cards.setItems(data);
            cards.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> ov, String old_val, 
                    String new_val) -> {
                        System.out.println(new_val);
                        oldText = new_val == null ? "null" : new_val.toString();
                        
            });
            
            //playerhands.getItems().add(oldText);
            while(oldText != null) {
            playerhands.getItems().add(oldText);
            //Hands.add(oldText);
            //System.out.println(Hands);
            }
            //ObservableList selectedIndices = cards.getSelectionModel().getSelectedIndices();
            //for(int k =0; k<selectedIndices.size();k++) {
            //((List) cards).get(k).getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Number>()        	
            		        //{   
								//@Override
								//public void changed(ObservableValue<? extends Number> ov, Number o, Number v) {
									// TODO Auto-generated method stub
									//System.out.println(ov);
								//}
            		        //});
              //for(int k =0; k<selectedIndices.size();k++) {
							//newText = cards.getItems().get(ov).toString();	
              //}
                //playerhands.getItems().add(a);
            //});
          //playerhands.getItems().add(newText);
        });
        Button remove = new Button("Remove");
        remove.relocate(30+i*200, 600);
        remove.setOnAction(event -> {
        	playerhands.getSelectionModel().selectedItemProperty().addListener(
                    (ObservableValue<? extends String> ov, String old_val, 
                        String new_val) -> {
                            System.out.println(new_val);
                            oldText = new_val == null ? "null" : new_val.toString();
                            
                });
        	while(oldText != null) {
        	playerhands.getItems().remove(oldText);
        	cards.getItems().add(oldText);
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
        
        //final Button addButton = new Button("Add item");
        //addButton.setOnAction(new EventHandler<ActionEvent>() {
            //@Override
           // public void handle(ActionEvent event) {
                //counter.set(counter.get()+1);
                //listView.getItems().add("Item "+counter.get());
            //}
        //});
        /*Button Add = new Button("Add");

        button.setOnAction(event -> {
            ObservableList selectedIndices = cards.getSelectionModel().getSelectedIndices();

            for(Object o : selectedIndices){
                System.out.println("o = " + o + " (" + o.getClass() + ")");
            }});
        */
        
        
        //VBox vBox = new VBox(listView, button);
        getChildren().addAll(cards);
        //getChildren().add(Confirm1);
        //listView.getItems().add("Item 2");
        //listView.getItems().add("Item 3");


        Button Confirm1 = new Button("Confirm");
        Confirm1.relocate(500, 650);
        getChildren().add(Confirm1);
		//listview
		//Play = new Button("Play");	
		//canvas.getChildren().addAll(listView, button);
		
		//stage.show();
	}
}
