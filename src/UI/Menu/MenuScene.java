package UI.Menu;

import UI.SceneManager;
import UI.Game.GameScene;
import javafx.fxml.FXML;

public class MenuScene {

    private boolean isGameInitialized = false;

    @FXML
    public void startClicked() {
        System.out.println("Start Clicked");

        SceneManager sm = SceneManager.getInstance();

        if (!isGameInitialized) {
            System.out.println("Lần đầu bấm Start: Đang khởi tạo GameScene...");

            GameScene gameInstance = new GameScene(sm);

            sm.addGameScene("Game", gameInstance);

            isGameInitialized = true;
            System.out.println("GameScene đã sẵn sàng!");

        } else {
            System.out.println("Game đã có, chỉ cần chuyển cảnh...");
        }

        sm.switchTo("Game");
    }

    public void clickTutorial() {
        // (code...)
    }

    public void clickShop() {
        // (code...)
    }
}