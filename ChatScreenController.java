package soulsyncproject;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.AnchorPane;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ChatScreenController {

    @FXML
    private TextField userInput;
    @FXML
    private VBox chatContainer;
    @FXML
    private ScrollPane chatScrollPane;
    @FXML
    private Button sendButton;
    @FXML
    private Button quoteButton, btnMusic, btnChat, btnTheme, btnUser;
    @FXML
    private ImageView quoteIcon, musicIcon, chatIcon, themeIcon, userIcon;

    private final Random rng = new Random();
    private boolean sessionStarted = false;
    private String currentMood = "neutral";

    //THIS BELOW IS THE RESPONSE FOR THE USER'S INPUT
    //no 1 responses types for each mood
    private final Map<String, List<String>> moodsEmpathy = Map.of(
            "happy", Arrays.asList("That's fantastic to hear!", "Sounds like joy is in the air!", "Yay! 😊", "That’s wonderful to hear!", "Your joy shines today!", "Yay! Happiness suits you!"),
            "sad", Arrays.asList("I'm sorry you're feeling down.", "I wish I could give you a hug right now.", "I hear you—this sounds tough.", "I’m really sorry you’re feeling this.", "It can feel heavy—I see you.", "I’m here with you in this."),
            "anger", Arrays.asList("I understand you're upset.", "Anger can feel overwhelming—I'm here.", "Taking a deep breath helps.", "I can hear how upset you are.", "Anger is valid—I'm with you.", "That frustration makes sense."),
            "anxiety", Arrays.asList("Nerves are natural—you're not alone.", "Anxiety can be heavy, I'm here to support.", "Let's slow down together.", "Anxiety can feel overwhelming—I'm here.", "Your worries matter—thank you for sharing.", "That tension is real—I’m listening."),
            "normal", Arrays.asList("I'm all ears—tell me more about what's going on.", "I'm all ears to whatever you feel.", "Feel free to share anything.", "What’s on your mind today?", "Hi there! I'm SoulSync 😊", "Feel free to share how you're feeling or what's on your mind."),
            "fear", Arrays.asList("Fear is powerful—I'm here with you.", "It's okay to be scared.", "That sounds frightening, but you're not alone.", "Thanks for sharing something so vulnerable.", "I see the fear you're carrying.", "You're brave for talking about it."),
            "guilt", Arrays.asList("I hear that you're feeling guilty.", "Regret is tough—I’m with you.", "You’re human—we all make mistakes.", "I understand this is weighing on you.", "You’re being honest with yourself, and that matters.", "That sounds like a heavy feeling."),
            "tired", Arrays.asList("Sounds like you’re exhausted.", "I can tell you need rest.", "That level of tiredness is real.", "I’m here with you through the fatigue.", "It’s okay to feel worn out.", "Even strong people get tired."),
            "confused", Arrays.asList("Feeling lost can be overwhelming.", "It’s okay to not have answers.", "I hear that uncertainty in your words.", "That sounds really unclear—let's explore it together.", "It’s okay to feel stuck sometimes.", "I understand that this feels confusing."),
            "lonely", Arrays.asList("I’m here with you now.", "Loneliness can be painful—I hear you.", "You’re not invisible to me.", "Thank you for sharing that.", "You matter, even when it doesn’t feel like it.", "I see your loneliness, and I’m staying.")
    );
    //no 2 responses types for each mood
    private final Map<String, List<String>> moodsComfort = Map.of(
            "happy", Arrays.asList("Keep riding that positive wave!", "Your happiness is contagious.", "May this moment last.", "Keep celebrating those moments!", "Let that brightness linger.", "Hold onto this positivity."),
            "sad", Arrays.asList("It's okay to feel. I'll stay with you.", "Your feelings matter—take your time.", "Here when you're ready to share more.", "It’s okay to feel upset—take your time.", "You don’t need to rush through this.", "Let’s take things gently, together."),
            "anger", Arrays.asList("Maybe a moment of calm could help.", "You're stronger than this moment.", "We can work through this together.", "Breathing can calm the storm.", "Taking space is okay.", "You're strong beyond anger."),
            "anxiety", Arrays.asList("Let's take a slow, deep breath together.", "One step at a time—you've got this.", "Focusing on now can ease worry.", "Let's slow our breath together.", "Step by step, you've got this.", "Focus on the present moment with me."),
            "normal", Arrays.asList("It helps to talk things through.", "How does that feel for you?", "What would help most right now?", "Talking helps process things.", "Together, we can explore it.", "It's okay to just say what's real."),
            "fear", Arrays.asList("You're not alone in this fear.", "Let’s create safety in this space.", "Take a moment to breathe—you’re safe here.", "We can move through fear together.", "You’re not facing this alone.", "It’s okay to feel vulnerable here."),
            "guilt", Arrays.asList("You don’t have to carry this alone.", "Forgiveness starts with gentleness.", "You’re more than one moment.", "Let’s release some of that weight.", "Be kind to yourself—you’re learning.", "Let’s walk through this with care."),
            "tired", Arrays.asList("It’s okay to rest—you deserve it.", "Give yourself permission to pause.", "Let the world slow down for a bit.", "You’ve done enough today.", "Rest is powerful and healing.", "Let’s breathe together slowly."),
            "confused", Arrays.asList("We can make sense of it together.", "Start where you are—it’s okay.", "We’ll figure things out slowly.", "You’re not alone in the fog.", "Let’s walk through this haze gently.", "You don’t need to have all the answers now."),
            "lonely", Arrays.asList("I’m right here, just for you.", "You’re not alone anymore.", "You are seen and valued here.", "Let’s share this space together.", "Connection matters—you have it here.", "I’m staying with you in this moment.")
    );

    //no 3 responses types for each mood
    private final Map<String, List<String>> moodsInvite = Map.of(
            "happy", Arrays.asList("Want to share what's made you so joyful?", "Tell me more about your good day.", "Keep this moment alive by talking about it.", "Wanna share more of what made you smile?", "Tell me about this best part!", "Your joy inspires—tell more!"),
            "sad", Arrays.asList("Would sharing what's on your mind help?", "I'm here if you want to open up.", "Talking often eases the heart.", "Do you want to share what's weighing on you?", "I'm listening—what happened?", "Would talking about it help?"),
            "anger", Arrays.asList("Do you want to explore why you're feeling this way?", "What triggered this anger?", "Would it help to talk it out?", "Tell me what's triggered you.", "Would discussing it help?", "What would help you feel lighter?"),
            "anxiety", Arrays.asList("Can you tell me more about what's worrying you?", "Let's unpack what's causing stress.", "What’s on your mind right now?", "Want to say what's worrying you?", "Let’s explore what's causing that stress.", "You can talk about it as long as you need."),
            "normal", Arrays.asList("What would you like to share today?", "Feel free to tell me anything.", "I'm listening to you.", "What's catching your attention lately?", "How are you doing right now—any thoughts?", "Ready to talk about anything on your mind?"),
            "fear", Arrays.asList("Would you like to talk about what’s making you scared?", "What’s making you feel unsafe?", "Tell me what’s on your heart right now.", "What is making you feel uneasy?", "Want to open up about what’s frightening you?", "What’s feeding that fear inside you?"),
            "guilt", Arrays.asList("Do you want to talk about what happened?", "What are you feeling regretful about?", "Let’s explore that weight together.", "Want to walk through that moment?", "I’m ready when you are to talk through it.", "What’s been sitting on your heart?"),
            "tired", Arrays.asList("Want to talk about what’s draining you?", "Tell me what’s been exhausting lately.", "What’s made you feel so worn down?", "Let’s unpack this tiredness.", "Want to share what’s been too much?", "Where is the fatigue coming from?"),
            "confused", Arrays.asList("What’s been feeling unclear?", "Want to talk it through slowly?", "Tell me what you’re unsure about.", "Let’s walk through the fog together.", "You can talk through your thoughts here.", "What’s been leaving you puzzled?"),
            "lonely", Arrays.asList("Want to talk about the loneliness?", "Tell me what it feels like right now.", "What’s making you feel alone?", "Want to share how that solitude feels?", "Tell me how I can be here for you.", "Let’s connect right now—what’s in your heart?")
    );

    
    
    
    private boolean isOffTopic(String input) {
        String lower = input.toLowerCase();
        return lower.matches(".*\\b(what is|who is|how does|when is|why is|tell me about|capital of|define|explain|invented|how many|how long|science|planet|universe|math|animal|formula|game|movie|ai|openai|chatgpt|news|what do you think|curious|honest clarity)\\b.*");
    }

// Updated to use real API
    private List<String> getOffTopicResponse(String input) {
        String geminiResponse = ApiHelper.getShortGeminiReply(input);

        String redirection = pickRandom(List.of(
                "Anyway, I’d love to know how *you* are doing. 🌼",
                "Let’s come back to you — how are you really feeling today?",
                "That’s interesting, but how’s your heart doing lately?",
                "I’m here for your thoughts and feelings — want to talk about those?",
                "Now, let’s get back to what’s truly on your mind. 😊"
        ));

        return List.of(geminiResponse, redirection);
    }

    
    
    
    @FXML
    public void initialize() {
        chatScrollPane.vvalueProperty().bind(chatContainer.heightProperty());
    }

    public void initChat(String inputMessage) {
        // Welcome greeting when chat screen opens
        showBotMessages(Arrays.asList(
                "Hi there! I'm SoulSync 😊",
                "Feel free to share how you're feeling or what's on your mind.",
                "Whenever you're ready, just type whatever you're thinking or feeling."
        ));
        sessionStarted = true;

        if (inputMessage != null && !inputMessage.isBlank()) {
            userInput.setText(inputMessage);
            sendMessage();
        }
    }

    @FXML
    public void sendMessage() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) {
            return;
        }

        // Display user's message
        Label userLabel = createLabel("🙎‍♂️  " + input, "-fx-background-color: rgba(219, 186, 255, 0.8);");
        chatContainer.getChildren().add(userLabel);
        userInput.clear();

        // Detect mood first
        currentMood = detectMood(input.toLowerCase());

        // If a mood is detected (not neutral), respond emotionally
        if (!currentMood.equals("neutral")) {
            List<String> replySet = Arrays.asList(
                    pickRandom(moodsEmpathy.get(currentMood)),
                    pickRandom(moodsComfort.get(currentMood)),
                    pickRandom(moodsInvite.get(currentMood))
            );

            for (String msg : replySet) {
                showBotMessages(Collections.singletonList(msg));
            }
        } // If neutral, check if it's off-topic
        else if (isOffTopic(input)) {
            List<String> replySet = getOffTopicResponse(input);
            for (String msg : replySet) {
                showBotMessages(Collections.singletonList(msg));
            }
        } // If it's neither emotional nor off-topic (random or empty/meaningless)
        else {
            showBotMessages(Collections.singletonList("🐾 I'm here to help however I can. Feel free to share how you're truly feeling right now. 💬"));
        }
    }

    //MOOD DETECTION LOGIC FOE REAL-TIME USER'S INPUT/DESCRIPTION
    //mood detection, by the user's description, the possible below keyword match to the user's mood 
    private String detectMood(String text) {
        text = text.toLowerCase(); // normalize input

        // Happy / Joyful / Positive mood
        if (text.matches(".*\\b(happy|joy|joyful|cheerful|excited|grateful|hopeful|thankful|blessed|relaxed|calm|at peace|content|peaceful|everything's good|i'm fine|i feel light|things are going well|i’m okay|feeling great|feeling positive|life is good|i am fine|good|i am okay|i am good|awesome|nice|amazing|happy)\\b.*")) {
            return "happy";
        } // Sad / Down / Depressed mood
        else if (text.matches(".*\\b(sad|depressed|unhappy|crying|tearful|hopeless|lonely|miserable|worthless|empty|broken|blue|heartbroken|i want to cry|no one cares|feeling down|nothing feels right|can’t do this anymore|life is heavy|i give up|tired of pretending|dark place|low|disconnected|not myself)\\b.*")) {
            return "sad";
        } // Angry / Frustrated / Irritated mood
        else if (text.matches(".*\\b(angry|mad|furious|upset|frustrated|annoyed|pissed off|irritated|rage|boiling inside|can’t handle this|i hate this|they made me so mad|i want to scream|fed up|lost my temper|outburst|feel like exploding)\\b.*")) {
            return "anger";
        } // Anxious / Stressed / Nervous mood
        else if (text.matches(".*\\b(anxious|nervous|stressed|panicked|worried|overthinking|can't breathe|racing heart|on edge|tension|paranoid|something bad will happen|shaky|uneasy|butterflies in my stomach|i can’t calm down|sweaty palms|spiraling|overwhelmed|pressure|mental overload|trouble|disturb|disturbed)\\b.*")) {
            return "anxiety";
        } // Fear / Scared / Insecure mood
        else if (text.matches(".*\\b(scared|afraid|fearful|insecure|terrified|i feel small|i feel weak|paralyzed|vulnerable|hiding|don’t feel safe|i’m shaking|frightened|i’m unsure|i fear the worst|i feel threatened|no control|intimidated|panicking)\\b.*")) {
            return "fear";
        } // Guilt / Regret
        else if (text.matches(".*\\b(guilty|regret|i messed up|i feel bad|shame|ashamed|it’s my fault|i let them down|i made a mistake|can’t forgive myself|i wish i could go back|sorry for everything)\\b.*")) {
            return "guilt";
        } // Tired / Burned out / Exhausted mood
        else if (text.matches(".*\\b(tired|exhausted|drained|burned out|no energy|can’t keep going|sleepy|fatigued|worn out|sluggish|done with everything|can’t focus|don’t care anymore|mentally tired|emotionally tired|can’t do this today|just want to rest)\\b.*")) {
            return "tired";
        } // Confused / Lost / Directionless mood
        else if (text.matches(".*\\b(confused|lost|don’t know|no direction|what should i do|don’t understand|can’t decide|stuck|uncertain|unsure|no clarity|messed up|blank|scattered|wandering|nowhere to go)\\b.*")) {
            return "confused";
        } // Lonely / Isolated
        else if (text.matches(".*\\b(lonely|alone|isolated|left out|no one’s there|no friends|invisible|unseen|unloved|unwanted|no one understands|can’t talk to anyone|i’m by myself|emotionally abandoned)\\b.*")) {
            return "lonely";
        } // neutral / Isolated
        else if (text.matches(".*\\b(normal|fine|nothing|calm|meh|done|free|neutral|Okay|ok|Not much|Nothing special|Just another day|same old|alright|i don't know|can't tell|not sad|numb|whatever|chilling|all good|good|normal|nothing|passing time|nothing going on|bored|hi|hey|hello|bye)\\b.*")) {
            return "normal";
        } // Default mood if no strong keyword is found
        else {
            return "neutral";
        }
    }

    // Utility: picks a random entry from a list
    private String pickRandom(List<String> list) {
        return list.get(rng.nextInt(list.size()));
    }

    // Display bot message(s) in chat container
    private void showBotMessages(List<String> messages) {
        for (String text : messages) {
            Label botLabel = createLabel("🐾  " + text, "-fx-background-color: rgba(215, 225, 255, 0.8);");
            chatContainer.getChildren().add(botLabel);
        }
    }

    // Shared label creation helper
    private Label createLabel(String text, String baseStyle) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.setStyle(baseStyle + " -fx-padding: 8 12; -fx-background-radius: 15;");
        VBox.setMargin(label, new Insets(4));
        return label;
    }

    // Navigation bar buttons to switch screens
    @FXML
    private void onNavClick(ActionEvent event) {
        Button clicked = (Button) event.getSource();
        String ud = (String) clicked.getUserData();
        switch (ud) {
            case "0" ->
                switchScreen("QuotesScreen.fxml");
            case "1" ->
                switchScreen("SoundScreen.fxml");
            case "2" ->
                switchScreen("Main.fxml");
            case "3" ->
                switchScreen("ThemeScreen.fxml");
            case "4" ->
                switchScreen("UserScreen.fxml");
        }
    }

    // Handles actual screen loading with fade effect
    private void switchScreen(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) userInput.getScene().getWindow();
            Scene scene = new Scene(root, 400, 650);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            root.setOpacity(0);
            stage.setScene(scene);
            FadeTransition ft = new FadeTransition(Duration.seconds(0.6), root);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
