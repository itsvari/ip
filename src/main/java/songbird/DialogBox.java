package songbird;

import java.io.IOException;
import java.util.Collections;

import atlantafx.base.controls.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;
    @FXML
    private Card card;

    private DialogBox(String text, Image img, boolean isUser) {
        assert text != null : "Dialog text should not be null";
        assert img != null : "Dialog image should not be null";

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);

        // make the ImageView circular
        makeImageViewCircular(displayPicture);

        // Apply different styles based on whether it's a user or Songbird message
        if (isUser) {
            card.getStyleClass().add("user-message");
        } else {
            card.getStyleClass().add("songbird-message");
        }

        // Bind the Label's preferred width to the Card's width
        dialog.prefWidthProperty().bind(card.widthProperty().subtract(10));
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Makes the ImageView circular.
     * @param imageView The ImageView to make circular.
     */
    private void makeImageViewCircular(ImageView imageView) {
        Circle clip = new Circle(25, 25, 25);
        imageView.setClip(clip);
    }

    public static DialogBox getUserDialog(String s, Image i) {
        return new DialogBox(s, i, true);
    }

    public static DialogBox getSongbirdDialog(String s, Image i) {
        var db = new DialogBox(s, i, false);
        db.flip();
        return db;
    }
}
