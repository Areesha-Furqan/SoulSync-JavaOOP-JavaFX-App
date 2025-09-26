package soulsyncproject;


import javafx.application.Application;
import javafx.fxml.FXMLLoader; //TO BUILD THE UI 
import javafx.scene.Parent;
import javafx.scene.Scene; //UI OF THE WINDOW
import javafx.stage.Stage; //STAGE TO SHOW A WINDOW


public class Main extends Application { 
    
    @Override
    public void start(Stage stage) {
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/soulsyncproject/Splash.fxml")); // Splash is your first screen
            Scene scene = new Scene(root, 400, 650); // Standard Android-like size
            stage.setTitle("SoulSync Application");
            stage.setScene(scene);
            stage.setResizable(true); // Makes it fixed-size like mobile
            stage.show();
        }
        
        catch (Exception e) {
            e.printStackTrace();
        }
    
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
