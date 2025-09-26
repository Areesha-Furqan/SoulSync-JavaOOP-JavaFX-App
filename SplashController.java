package soulsyncproject;

import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SplashController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label titleLabel;

    @FXML
    private Label subtitleLabel;

    @FXML
    public void initialize() {
        // Fade in root
        FadeTransition bgFadeIn = new FadeTransition(Duration.seconds(1.5), rootPane);
        bgFadeIn.setFromValue(0.0);
        bgFadeIn.setToValue(1.0);

        // Fade in title
        FadeTransition titleFadeIn = new FadeTransition(Duration.seconds(1.5), titleLabel);
        titleFadeIn.setFromValue(0.0);
        titleFadeIn.setToValue(1.0);

        // Fade in subtitle
        FadeTransition subtitleFadeIn = new FadeTransition(Duration.seconds(1.5), subtitleLabel);
        subtitleFadeIn.setFromValue(0.0);
        subtitleFadeIn.setToValue(1.0);

        // Sequence of intro animations
        SequentialTransition sequence = new SequentialTransition(bgFadeIn, titleFadeIn, subtitleFadeIn);

        // After intro, wait, then load main screen with bounce
        PauseTransition wait = new PauseTransition(Duration.seconds(2.5));
        wait.setOnFinished(e -> {
            try {
                // Get screen size (no java.awt)
                double screenHeight = Screen.getPrimary().getBounds().getHeight();
                double usableHeight = screenHeight - 80;

                // Load main screen
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/soulsyncproject/Main.fxml"));
                Parent mainRoot = loader.load();
                mainRoot.setOpacity(0); // Start invisible for fade-in

                Stage stage = (Stage) rootPane.getScene().getWindow();
                Scene scene = new Scene(mainRoot, 400, 650);
                stage.setScene(scene);

                // Apply fade-in to Main.fxml
                FadeTransition fadeIn = new FadeTransition(Duration.millis(900), mainRoot);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Start animations
        sequence.setOnFinished(e -> wait.play());
        sequence.play();
    }
}
