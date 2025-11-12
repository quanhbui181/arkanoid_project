package UI.GameOver;

import UI.SceneManager;
import audio.SoundManager;
import javafx.fxml.FXML;

public class GameOverScene {

    @FXML
    public void ExitToMenu(){
        SceneManager.getInstance().getSoundManager().play("click");
        SceneManager.getInstance().switchTo("Menu");
    }

    @FXML
    public void restartGame(){
        SceneManager.getInstance().getSoundManager().play("click");
        SceneManager.getInstance().switchTo("Game");
    }

}