package UI;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Spliterator;

import audio.SoundManager;
import core.Player;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import UI.Game.GameScene;

public class SceneManager {
    //object scenemanager duy nhat
    private static SceneManager instance;

    //main stage
    private Stage primaryStage;

    // Luu cac scenes gom ten scene va scene
    private final Map<String, Scene> scenes = new HashMap<>();

    private GameScene gameSceneInstance;
    private String currentSceneName;

    private final SoundManager soundManager = new SoundManager();
    private String currentPlayerName;

    private SceneManager() {
        askForName();

    } // k cho phep khoi tao obj ben ngoai

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void setPrimaryStage(Stage stage){
        this.primaryStage = stage;
    }

    public  void addScene(String name, Scene scene){
        scenes.put(name, scene);
    }

    public void addGameScene(String name, GameScene gameScene) {
        this.gameSceneInstance = gameScene;
        this.scenes.put(name, gameScene.getScene());
        this.gameSceneInstance.addPlayer(currentPlayerName);
    }

    public Scene getScene(String name){
        return scenes.get(name);
    }

    public void switchTo(String name){
        Scene scene = scenes.get(name);

        if(scene == null){
            System.out.println("Chưa có màn này: " + name);
            return;
        }

        if ("Game".equals(currentSceneName) && gameSceneInstance != null) {
            System.out.println("Đang rời Game !");
            gameSceneInstance.stop();
            gameSceneInstance.setCurrentLevel(1);
        }

        if (primaryStage != null) {
            primaryStage.setScene(scene);
            this.currentSceneName = name;
        }

        if ("Game".equals(name) && gameSceneInstance != null) {
            System.out.println("Đang vào Game !");
            gameSceneInstance.start();
        }

    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    private boolean isValidName(String name) {
        if (name == null) {
            return false;
        }
        String check = name.trim();
        if (check.length() > 5 || check.length() < 2) {
            return false;
        }
        for (int i = 0; i < check.length(); ++i) {
            if (!Character.isLetterOrDigit(check.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    private void askForName() {
        while (currentPlayerName == null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Welcome to Arkanoid \uD83C\uDFAE");
            dialog.setHeaderText("Are you ready ?");
            dialog.setContentText("Enter your name:");
            dialog.setGraphic(null);
            Optional<String> input = dialog.showAndWait();
            if (input.isPresent()) {
                String name = input.get();
                if (isValidName(name)) {
                    currentPlayerName = name.trim();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Invalid name \uD83D\uDEAB");
                    alert.setHeaderText("Enter your name again !");
                    alert.setContentText("Name must be 2-5 characters long and contain only letters and numbers.");
                    alert.setGraphic(null);
                    alert.showAndWait();
                }
            } else {
                Platform.exit();
                break;
            }
        }
    }
}