package UI; 
 
import javafx.application.Application;
import javafx.scene.Scene; 
import javafx.scene.layout.StackPane; 
import javafx.stage.Stage; 
 
public class TestScene extends Application { 
	
	private MenuPane m;	
	
    public static void main(String[] args) { 
        launch(args); 
    } 
    @Override 
    public void start(Stage primaryStage) { 
        primaryStage.setTitle("test scene"); 
        StackPane root = new StackPane();        
        m =  new MenuPane();
        root.getChildren().add(m); 
        primaryStage.setScene(new Scene(root, 1280, 720)); 
        primaryStage.show(); 
    }
    
    public void loadscene1() {
    	
    }
} 

