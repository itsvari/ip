package songbird;

import java.io.IOException;

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
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            MainWindow mainWindow = fxmlLoader.getController();
            mainWindow.setSongbird(songbird);
            Ui.setMainWindow(mainWindow);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
