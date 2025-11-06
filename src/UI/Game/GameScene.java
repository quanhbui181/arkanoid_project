package UI.Game;

import PowerUpSystem.PowerUpManager;
import PowerUpSystem.PowerUpType;
import RenderManager.RenderMap;
import UI.SceneManager;
import audio.SoundManager;
import core.GameLoop;
import core.HighScore;
import core.InputHandler;
import core.Player;
import entities.Ball;
import entities.Brick;
import entities.Paddle;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

public class GameScene {

    private final SceneManager sceneManager = SceneManager.getInstance();

    private final Scene scene;
    private final GameLoop gameLoop;

    private final Ball ball;
    private final Paddle paddle;
    private final ArrayList<Brick> brickList;
    private final GraphicsContext gc;
    private int currentLevel = 1;


    private final PowerUpManager powerUpManager;
    private final Map<PowerUpType, Node> powerUpUIMap = new HashMap<>();
    private final HighScore highScore;
    private String currentPlayerName;

    @FXML
    private ImageView heart1;
    @FXML
    private ImageView heart2;
    @FXML
    private ImageView heart3;
    @FXML
    private Label scoreLabel;
    @FXML
    private VBox boxOfCredits;
    @FXML
    private VBox boxOfHighScores;
    @FXML
    private Label creditsGameLabel;
    @FXML
    private Button pauseButton;
    @FXML
    private Button exitButton;


    public GameScene() {
        try {
            Font.loadFont(getClass().getResourceAsStream("/fonts/AGENCYB.TTF"), 1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        Canvas canvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        RenderMap renderMap = new RenderMap();
        brickList = new ArrayList<>();
        paddle = new Paddle();
        ball = new Ball();
        InputHandler input = new InputHandler();
        ArrayList<Ball> ballList = new ArrayList<>();
        powerUpManager = new PowerUpManager(ball, paddle, ballList);
        gameLoop = new GameLoop(ball, paddle, brickList, gc, input, sceneManager, this, powerUpManager, ballList);
        highScore = new HighScore();
        AnchorPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/Game/GameScene.fxml"));
            loader.setController(this);
            root = loader.load();
            root.getChildren().add(1, canvas);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            root = new AnchorPane(canvas);
        }
        scene = new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        input.getKey(scene);
    }

    @FXML
    public void initialize() {
        scoreLabel.textProperty().bind(gameLoop.getScoreProperty().asString("%d"));
        ActivePowerUpCreditsListener apc = new ActivePowerUpCreditsListener(this);
        powerUpManager.getActivePowerUpCredits().addListener(apc);
        createHighScoreUI();
    }

    private void createHighScoreUI() {
            boxOfHighScores.getChildren().clear();
            List<Player> top = highScore.getHighScoresList();
            if (top.isEmpty()) {
                Label first = new Label("Lan dau cua em...");
                boxOfHighScores.getChildren().add(first);
                return;
            }
            for (int i = top.size() - 1; i >= 0; --i) {
                Player player = top.get(i);
                HBox row = createScoreRow(top.size() - i, player);
                boxOfHighScores.getChildren().add(row);
            }
    }

    private HBox createScoreRow(int rank, Player player) {
        Label rankLabel;
        Label info = new Label(player.toString());
        String colorStyle;
        if (rank == 1) {
            rankLabel = new Label("\uD83E\uDD47");
            colorStyle = "-fx-text-fill: gold;";
        } else if (rank == 2) {
            rankLabel = new Label("\uD83E\uDD48");
            colorStyle = "-fx-text-fill: #E5E4E2;";
        } else if (rank == 3) {
            rankLabel = new Label("\uD83E\uDD49");
            colorStyle = "-fx-text-fill: #CD7F32;";
        } else {
            rankLabel = new Label("\uD83E\uDD1D");
            colorStyle = "-fx-text-fill: silver;";
        }
        info.setStyle(colorStyle);
        rankLabel.setStyle(colorStyle);
        HBox row = new HBox(10, rankLabel, info);
        row.setAlignment(Pos.CENTER);
        return row;
    }


    protected void createPowerUpCreditsUI(PowerUpType type, DoubleProperty timer) {
        Label nameLabel = new Label(type.getName());

        Label timerLabel = new Label();
        timerLabel.setTextFill(Color.YELLOW);

        timerLabel.textProperty().bind(timer.asString("%.1fs"));

        HBox row = new HBox(20, nameLabel, timerLabel);
        row.setAlignment(Pos.CENTER);

        powerUpUIMap.put(type, row);
        boxOfCredits.getChildren().add(row);
    }

    protected void removePowerUpCreditsUI(PowerUpType type) {
        Node nodeToRemove = powerUpUIMap.remove(type);
        if (nodeToRemove != null) {
            boxOfCredits.getChildren().remove(nodeToRemove);
        }
    }

    public Scene getScene() {
        return scene;
    }

    public void start() {
        reset();
        gameLoop.start();
    }

    public void stop() {
        gameLoop.stop();
    }

    public void reset() {
        SceneManager.getInstance().getSoundManager().play("play");
        gameLoop.setScore(0);
        ball.reset();
        paddle.reset();
        brickList.clear();
        gameLoop.getPowerUpManager().reset();
        RenderMap rendermap = new RenderMap();
        rendermap.render(currentLevel, brickList);
        updateLivesUI();
    }

    public void goToNextLevel() {
        gameLoop.stop();
        currentLevel++;
        final int MAX_LEVEL = 15;
        if (currentLevel > MAX_LEVEL) {
            System.out.println("thang me may roi");
            sceneManager.switchTo("Win");
        } else {
            SceneManager.getInstance().getSoundManager().play("play");
            ball.reset();
            paddle.reset();
            brickList.clear();
            gameLoop.getPowerUpManager().reset();
            RenderMap rendermap = new RenderMap();
            rendermap.render(currentLevel, brickList);
            updateLivesUI();
            gameLoop.start();
        }
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    /**
     * Update lives UI based on current ball lives.
     * UPDATED: Now shows hearts when lives are gained back.
     */
    public void updateLivesUI() {
        int lives = ball.getLives();

        // Show/hide hearts based on current lives
        heart1.setVisible(lives >= 1);
        heart2.setVisible(lives >= 2);
        heart3.setVisible(lives >= 3);

        // Debug log
        // System.out.println("💖 Lives UI updated: " + lives + " lives");
    }

    @FXML
    public void pauseClicked() {
          SceneManager.getInstance().getSoundManager().play("click");
          gameLoop.stop();
          sceneManager.switchTo("Pause");
          System.out.println("Bấm Pause!");
    }

    @FXML
    public void exitClicked() {
        SceneManager.getInstance().getSoundManager().play("click");
        System.out.println("Bấm Exit!");
        gameLoop.stop();
        updateHighScores();
        sceneManager.switchTo("Menu");
    }

    public void updateHighScores() {
        highScore.addScore(gameLoop.getScore(), currentPlayerName);
        createHighScoreUI();
    }

    public void addPlayer(String currentPlayerName) {
        this.currentPlayerName = currentPlayerName;
    }
}