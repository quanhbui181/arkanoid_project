package UI.Game;

import RenderManager.RenderMap;
import UI.SceneManager;
import core.GameLoop;
import core.InputHandler;
import entities.Ball;
import entities.Brick;
import entities.Paddle;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import utils.Constants;
import java.util.ArrayList;

public class GameScene {

    private final SceneManager sceneManager;

    private final Scene scene;
    private final GameLoop gameLoop;

    private final Ball ball;
    private final Paddle paddle;
    private final ArrayList<Brick> brickList;
    private GraphicsContext gc;
    private int currentLevel = 2;

    public GameScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        Canvas canvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        RenderMap renderMap = new RenderMap();
        brickList = new ArrayList<>();
        paddle = new Paddle();
        ball = new Ball();
        InputHandler input = new InputHandler();
        Pane root = new Pane(canvas);
        scene = new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        input.getKey(scene);
        gameLoop = new GameLoop(ball, paddle, brickList, gc, input, sceneManager, this);
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
        ball.reset();
        paddle.reset();
        brickList.clear();
        RenderMap rendermap = new RenderMap();
        rendermap.render(currentLevel, brickList);
    }

    public void goToNextLevel() {
        gameLoop.stop();
        currentLevel ++;
        final int MAX_LEVEL = 15;
        if (currentLevel > MAX_LEVEL) {
            System.out.println("thang me may roi");
            sceneManager.switchTo("Win");
        } else {
            start();
        }
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

}
