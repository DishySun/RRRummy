package UI;

import java.util.List;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Pane;
import rrrummy.InvalidTileException;
import rrrummy.Tile;
import javafx.scene.control.TextField;

@SuppressWarnings("restriction")
public class RiggingPane extends Pane{

	//private NewGameControlPane n;
	//private int pn,an;
	private ListView<String> cards;
	private ObservableList<String> selectTiles;
	private String selectTile;
	private ObservableList<String> data;
	private ArrayList<ArrayList<String>> hands;

	public RiggingPane(int totalPlayer, ArrayList<String> Stra, ArrayList<String> Pname){
		System.out.println("taotal" + totalPlayer);
		hands = new ArrayList<ArrayList<String>>();
		for(int i=0; i<totalPlayer;i++) {		//initialize size
			hands.add(new ArrayList<String>());
		}
		//n = new NewGameControlPane();
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
        
		for(int i=0;i<totalPlayer;i++) {
			int index = i;
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
	        	selectTiles = cards.getSelectionModel().getSelectedItems();
	            if(selectTiles != null && hands.get(index).size() < 14) {
	            	for(String s : selectTiles) {
	                    playerhands.getItems().add(s);
	                    hands.get(index).add(s);
	                    if(checkDouble(hands,s)) 
	                    	data.remove(s);
	            	}
	            }
	            
	        });
	        
	        Button remove = new Button("Remove");
	        remove.relocate(30+i*200, 600);
	        
	        remove.setOnAction(event -> {
	        selectTile = playerhands.getSelectionModel().getSelectedItem();
	        if(selectTile != null) {
	        		playerhands.getItems().remove(selectTile);
	        		hands.get(index).remove(selectTile);
	        		if(!checkHas(data,selectTile))
	        			data.add(selectTile);
	        	}
	            });
			getChildren().addAll(pname,playerhands,add,remove);
		}

        Button Confirm1 = new Button("Confirm");
        Confirm1.relocate(500, 650);
        getChildren().add(Confirm1);
        
        Confirm1.setOnAction(event ->{
        	ArrayList<ArrayList<Tile>> handsTile = new ArrayList<ArrayList<Tile>>();
        	
        	if(checkHas14or0(hands)) {
        		for(int i=0; i<hands.size();i++) {		//initialize size
        			handsTile.add(new ArrayList<Tile>());
        		}
        		for(ArrayList<String> s : hands) {
        			if(s != null) {
        				for(String str : s) {
        					try {
								Tile t = new Tile(str);
								handsTile.get(hands.indexOf(s)).add(t);
							} catch (InvalidTileException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
        				}
        			}
        		}
        		System.out.println(handsTile);
        	}	else
        		System.out.println("has to have 14 or 0 tile");
        });

	}
	
	private boolean checkDouble(ArrayList<ArrayList<String>> list, String str2check) {
		int count = 0;
		for(ArrayList<String> s : list) {
			if(s != null) {
				for(String str : s) {
					if(str.equals(str2check))
						count++;
				}
			}
		}
		return count == 2;
	}
	
	private boolean checkHas(ObservableList<String> data, String str) {
		for(String s : data) {
			if(s.equals(str))
				return true;
		}
		return false;
	}
	
	private boolean checkHas14or0(ArrayList<ArrayList<String>> list) {
		for(ArrayList<String> s : list) {
			if(s.size() < 14 && s.size()>0)
				return false;
		}
		return true;
	}
}
