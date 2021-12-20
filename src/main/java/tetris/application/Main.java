package application;

import application.controller.MainWindowController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.net.URL;

public class Main extends Application {

	@Override
    public void start(final Stage stage) throws Exception {
	    	URL fxmlLocation = getClass().getResource("GameBoard.fxml");
	    	FXMLLoader loader = new FXMLLoader(fxmlLocation);
		//FXMLLoader loader = new FXMLLoader(getClass().getResource("src/main/java/tetris/application/GameBoard.fxml"));
		Parent root = (Parent)loader.load();
		System.out.println(loader.toString());
		Scene scene = new Scene(root);
		scene.getStylesheets().add("application/style.css");
		
		MainWindowController controller = (MainWindowController)loader.getController();
		controller.setSceneAndSetupListeners(scene, stage);
        
		stage.setTitle("PuF - Tetris");

        stage.setScene(scene);
        stage.show();	
    }

	public static void main(final String[] args) {
		launch(args);
	}
	
}
