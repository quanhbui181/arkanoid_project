package UI.Menu;

import UI.SceneManager;
import UI.Game.GameScene;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class MenuScene {

  @FXML
  private ImageView startButton;
  @FXML
  private ImageView tutorialButton;
  @FXML
  private ImageView shopButton;
  @FXML
  private ImageView exitButton;



  @FXML
  private void onStartClicked(MouseEvent e) {
    System.out.println("Start clicked!");
    SceneManager.getInstance().switchTo("Game");
  }

  @FXML
  private void onTutorialClicked(MouseEvent e) {
    System.out.println("Tutorial clicked!");
    SceneManager.getInstance().switchTo("Tutorial");
  }

  @FXML
  private void onShopClicked(MouseEvent e) {
    System.out.println("Shop clicked!");
    SceneManager.getInstance().switchTo("Shop");
  }

  @FXML
  private void onExitClicked(MouseEvent e) {
    System.out.println("Exit clicked!");
  }
}
