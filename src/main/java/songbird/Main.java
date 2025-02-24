package songbird;

import java.util.Objects;

import atlantafx.base.theme.CupertinoDark;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import songbird.ui.Ui;

/**
 * A GUI for Songbird using FXML and AtlantaFX.
 */
public class Main extends Application {

    private final Songbird songbird = new Songbird();

    @Override
    public void start(Stage stage) {
        Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            Scene scene = new Scene(ap);

            scene.getStylesheets().add(Objects.requireNonNull(
                    getClass().getResource("/css/styles.css")).toExternalForm());

            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            stage.setMaxWidth(600);
            stage.setTitle("Songbird");

            MainWindow mainWindow = fxmlLoader.getController();
            mainWindow.setSongbird(songbird);
            Ui.setMainWindow(mainWindow);

            stage.show();
            songbird.sendInitialResponses();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
