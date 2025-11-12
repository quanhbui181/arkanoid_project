package UI.Tutorial;

import UI.SceneManager;
import audio.SoundManager;
import javafx.fxml.FXML;

public class TutorialScene {

    @FXML
    public void exitToMenu(){
        SceneManager.getInstance().getSoundManager().play("click");
        SceneManager.getInstance().switchTo("Menu");
    }

}