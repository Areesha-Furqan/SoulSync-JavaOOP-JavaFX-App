package soulsyncproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
import java.net.URL;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SoundController implements Initializable {

    @FXML
    private Button btnPlay;

    @FXML
    private ToggleButton btnRepeat;

    @FXML
    private HBox soundThumbContainer;

    @FXML
    private void goToQuotes() {
        switchScreen("QuotesScreen.fxml");
    }

    @FXML
    private void goToMain() {
        switchScreen("main.fxml");
    }

    @FXML
    private void goToTheme() {
        switchScreen("ThemeScreen.fxml");
    }

    @FXML
    private void goToUser() {
        switchScreen("UserScreen.fxml");
    }

    @FXML
    private void stayHere() {
        // Already on sound screen
    }

//    private MediaPlayer mediaPlayer;
//
//    @FXML
//    private void handlePlayButtonClick(ActionEvent event) {
//        try {
//            URL soundURL = getClass().getResource("/soulsyncproject/assets/click.mp3"); // Use your filename
//            if (soundURL == null) {
//                System.out.println("Sound file not found!");
//                return;
//            }
//            Media sound = new Media(soundURL.toExternalForm());
//            mediaPlayer = new MediaPlayer(sound);
//            mediaPlayer.play();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void switchScreen(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) btnPlay.getScene().getWindow();
            Scene scene = new Scene(root, 400, 650);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load sample sound thumbnails
        for (int i = 1; i <= 5; i++) {
            Button thumb = new Button("Sound " + i);
            thumb.setStyle("-fx-background-radius: 20; -fx-padding: 10 15; -fx-background-color: #e1bee7;");
            thumb.setOnAction(e -> System.out.println("Playing: " + thumb.getText()));
            soundThumbContainer.getChildren().add(thumb);
        }
    }

    @FXML
    public void onPlayClick(ActionEvent event) {
        ImageView icon = (ImageView) btnPlay.getGraphic();
        String url = icon.getImage().getUrl();
        if (url.endsWith("play.png")) {
            icon.setImage(new Image("icons/pause.png"));
        } else {
            icon.setImage(new Image("icons/play.png"));
        }
    }

    @FXML
    public void onRepeatToggle(ActionEvent event) {
        System.out.println("Repeat is now " + (btnRepeat.isSelected() ? "ON" : "OFF"));
    }
}
