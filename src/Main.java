import UI.Game.GameScene;
import UI.SceneManager;
import UI.Shop.CreditManager;
import UI.Shop.ShopScene;
import UI.Shop.SkinManager;
import UI.Game.GameScene;
import UI.SceneManager;
import core.Player;
import javafx.application.Application;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import java.io.IOException;
import java.util.Objects;

/**
 *
 * @author admin
 */
public class Main extends Application {

  private CreditManager creditManager = new CreditManager();
  private SkinManager skinManager = new SkinManager();
  private LongProperty credits;
  private SceneManager sm = SceneManager.getInstance();


    @Override
    public void start(Stage primaryStage) throws IOException {

      // Tai credits ve tu file ngay khi game mo
      long loadedCredits = creditManager.loadCredits();
      this.credits = new SimpleLongProperty(loadedCredits);

      credits.addListener((obs,oldVal,newVal) -> {
        // Moi khi credits thay doi thi luu lai
        System.out.println("Credits thay doi, tu dong luu: " + newVal.longValue());
        creditManager.saveCredits(newVal.longValue());
      });

        sm.setPrimaryStage(primaryStage);

        // tai menu (fxml)
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/UI/Menu/MenuScene.fxml"));
        Pane menuRoot = menuLoader.load();
        sm.addScene("Menu", new Scene(menuRoot));

        FXMLLoader tutorialLoader = new FXMLLoader(getClass().getResource("/UI/Tutorial/TutorialScene.fxml"));
        Pane tutorialRoot = tutorialLoader.load();
        sm.addScene("Tutorial", new Scene(tutorialRoot));

        FXMLLoader gameOverLoader = new FXMLLoader(getClass().getResource("/UI/GameOver/GameOverScene.fxml"));
        Pane gameOverRoot = gameOverLoader.load();
        sm.addScene("GameOver", new Scene(gameOverRoot));

        FXMLLoader shopLoader = new FXMLLoader(getClass().getResource("/UI/Shop/ShopScene.fxml"));
        Pane shopRoot = shopLoader.load();

        ShopScene shopController = shopLoader.getController();
        shopController.initData(this.credits, this.creditManager, this.skinManager);

        sm.addScene("Shop", new Scene(shopRoot));


//      FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("/UI/Game/GameScene.fxml"));
//      Pane gameRoot = gameLoader.load();

//      GameScene gameController = gameLoader.getController();
//      gameController.initData(this.credits);
//      sm.addScene("Game", new Scene(gameRoot));

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