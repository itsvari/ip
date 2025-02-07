package songbird;

import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import songbird.ui.Ui;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    private static final Image SONGBIRD_IMAGE = new Image(Objects.requireNonNull(
            MainWindow.class.getResourceAsStream("/images/songbird_small.png")));
    private static final Image USER_IMAGE = new Image(Objects.requireNonNull(
            MainWindow.class.getResourceAsStream("/images/user_small.png")));

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private VBox dialogContainer;

    private Songbird songbird;

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setSongbird(Songbird songbird) {
        this.songbird = songbird;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        userInput.clear();

        // Store the user input in Ui before getting the response
        Ui.setCurrentUserInput(input);
        songbird.getResponse(input);
    }

    /**
     * Appends the user's input to the dialog container.
     * @param input The user's input.
     */
    public void handleUserDialog(String input) {
        dialogContainer.getChildren().addAll(DialogBox.getUserDialog(input, USER_IMAGE));
    }

    /**
     * Appends Songbird's response to the dialog container.
     * @param response The response from Songbird.
     */
    public void handleSongbirdResponse(String response) {
        dialogContainer.getChildren().addAll(DialogBox.getSongbirdDialog(response, SONGBIRD_IMAGE));
    }
}
