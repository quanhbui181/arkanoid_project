package UI.Pause;

import UI.SceneManager;
import javafx.fxml.FXML;

public class PauseScene {

  @FXML
  public void onContinueClicked() {
      System.out.println("Continue clicked!");
      SceneManager.getInstance().getSoundManager().play("click");
      SceneManager.getInstance().switchTo("Game");
  }

  @FXML
  public void onQuitClicked() {
      SceneManager.getInstance().getGameSceneInstance().updateHighScores();
      System.out.println("Quit clicked!");
      SceneManager.getInstance().getSoundManager().play("click");
      SceneManager.getInstance().switchTo("Menu");
  }
}
