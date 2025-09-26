package soulsyncproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class QuotesController implements Initializable {

    @FXML
    private Button quoteButton, btnMusic, btnChat, btnTheme, btnUser;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox quoteContainer;

    private final List<String> quotes = List.of(
            "Breathe. You’re going to be okay. You’ve got this.",
            "Every day may not be good, but there’s something good in every day.",
            "You are stronger than you think.",
            "Let go of what you can’t control. Embrace the present moment.",
            "Your soul shines the brightest when you’re calm.",
            "Progress, not perfection.",
            "Inhale peace, exhale stress.",
            "Even the darkest night will end and the sun will rise.",
            "Healing takes time, but every step counts.",
            "Be gentle with yourself—you’re doing the best you can."
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadQuotes();
        setupSmoothScroll();
    }

    @FXML
    public void onNavClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String userData = (String) clickedButton.getUserData();

        switch (userData) {
            case "0" ->
                openQuotesScreen();   // Quote Button
            case "1" ->
                openSoundScreen();    // Music Button
            case "2" ->
                openMainScreen();     // Chat Button
            case "3" ->
                openThemeScreen();    // Theme Button
            case "4" ->
                openUserScreen();     // User Button
        }
    }

    private void openQuotesScreen() {
        switchScreen("QuotesScreen.fxml");
    }

    private void openMainScreen() {
        switchScreen("main.fxml");
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

    private void switchScreen(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) quoteButton.getScene().getWindow();
            Scene scene = new Scene(root);

            // Apply stylesheet
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

            // Set scene without forcing width/height; let FXML handle it
            stage.setScene(scene);

            // Optional: make sure it's centered and not resizable
            stage.centerOnScreen();
            stage.setResizable(false);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadQuotes() {
        for (String quote : quotes) {
            StackPane quoteCard = new StackPane();
            quoteCard.getStyleClass().add("quote-card");
            quoteCard.setMinHeight(450);
            quoteCard.setMaxWidth(290);

            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(20);

            Label quoteSymbol = new Label("❝");
            quoteSymbol.getStyleClass().add("quote-symbol");

            Label quoteLabel = new Label(quote);
            quoteLabel.getStyleClass().add("quote-label");
            quoteLabel.setWrapText(true);
            quoteLabel.setAlignment(Pos.CENTER);
            quoteLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
            quoteLabel.setMaxWidth(280);

            vbox.getChildren().addAll(quoteSymbol, quoteLabel);
            quoteCard.getChildren().add(vbox);
            VBox.setMargin(quoteCard, new Insets(20, 0, 20, 0));
            quoteContainer.getChildren().add(quoteCard);
        }
    }

    private void setupSmoothScroll() {
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        scrollPane.setOnScroll(event -> {
            double deltaY = event.getDeltaY();
            double contentHeight = quoteContainer.getHeight();
            double viewportHeight = scrollPane.getViewportBounds().getHeight();
            double scrollValue = scrollPane.getVvalue();
            double scrollAmount = deltaY / contentHeight * 3;

            scrollPane.setVvalue(clamp(scrollValue - scrollAmount, 0, 1));
            event.consume();
        });
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
