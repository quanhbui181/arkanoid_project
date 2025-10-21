import UI.Game.GameScene;
import UI.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 *
 * @author admin
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        SceneManager sm = SceneManager.getInstance();
        sm.setPrimaryStage(primaryStage);

        // tai menu (fxml)
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/UI/Menu/MenuScene.fxml"));
        Pane menuRoot = menuLoader.load();
        sm.addScene("Menu", new Scene(menuRoot));

        // chinh ti'
        primaryStage.setTitle("Anh Jack oi em yeu anh nhieu lam ❤❤❤");
        primaryStage.setResizable(false);
        Image appIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.jpg")));
        primaryStage.getIcons().add(appIcon);

        // bat dau ne
        sm.switchTo("Menu");
        primaryStage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}