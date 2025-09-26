package soulsyncproject;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class MainController {

    private String selectedMood = null;

    @FXML
    private Button quoteButton, btnMusic, btnChat, btnTheme, btnUser;
    @FXML
    private AnchorPane navBarContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private ImageView backgroundImage;
    @FXML
    private ImageView chatIcon;

    @FXML
    public void initialize() {
        backgroundImage.setImage(new Image("/assets/bg2.jpg"));
        chatIcon.setFitWidth(28);
        chatIcon.setFitHeight(28);
    }


    @FXML
    private void onNavClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String userData = (String) clickedButton.getUserData();
        switch (userData) {
            case "0" ->
                openQuotesScreen();   // Quote Button
            case "1" ->
                openSoundScreen();    // Music Button
            case "2" ->
                goHome();             // Chat Button
            case "3" ->
                openThemeScreen();    // Theme Button
            case "4" ->
                openUserScreen();     // User Button
        }
    }

    @FXML
    public void openChatScreen() {
        String input = userInput.getText();  // Get message from input field
        openChatScreen(input);               // Call the helper method
    }

// ✅ Helper method with actual logic, not called directly from FXML
    private void openChatScreen(String message) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatScreen.fxml"));
            Parent chatRoot = loader.load();

            ChatScreenController controller = loader.getController();
            controller.initChat(message);  // Pass the typed or mood input

            Scene chatScene = new Scene(chatRoot, 400, 650);
            Stage stage = (Stage) userInput.getScene().getWindow(); //type casting

            chatRoot.setOpacity(0);
            stage.setScene(chatScene);

            javafx.animation.FadeTransition ft = new javafx.animation.FadeTransition(javafx.util.Duration.seconds(1), chatRoot);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMoodClick(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String mood = btn.getText();
        openChatScreen(mood);
    }

    private void openThemeScreen() {
        switchScreen("ThemeScreen.fxml");
    }

    private void openUserScreen() {
        switchScreen("UserScreen.fxml");
    }

    private void openSoundScreen() {
        switchScreen("SoundScreen.fxml");
    }

    
    
    private void openQuotesScreen() {
        switchScreen("QuotesScreen.fxml");
    }
    
    private void goHome() {
        // Optional: implement if needed to refresh MainScreen
    }

    // ✅ Safe switcher using backgroundImage to get Scene
    private void switchScreen(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage) backgroundImage.getScene().getWindow();
            Scene scene = new Scene(root, 400, 700);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
