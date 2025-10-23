package core;

import UI.Game.GameScene;
import UI.SceneManager;
import entities.Ball;
import entities.Brick;
import entities.Paddle;
import CollisionManager.BallWithPaddle;
import CollisionManager.BallWithBrick;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import utils.Constants;
import java.util.ArrayList;

// game loop(time line/ animation)
public class GameLoop extends AnimationTimer {

    private final SceneManager sceneManager;
    private final GameScene gameScene;

    private final Ball ball;
    private final Paddle paddle;
    private final ArrayList<Brick> brickList;
    private final GraphicsContext gc;
    private final InputHandler input;
    private long lastTime;
    private int score = 0;

    public GameLoop(Ball ball, Paddle paddle, ArrayList<Brick> brickList, GraphicsContext gc, InputHandler input, SceneManager sceneManager, GameScene gameScene) {
        this.sceneManager = sceneManager;
        this.gameScene = gameScene;
        this.ball = ball;
        this.paddle = paddle;
        this.brickList = brickList;
        this.gc = gc;
        this.input = input;
        lastTime = 0;
    }

    @Override
    public void handle(long now) {
        gc.clearRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        double secondStandard = 1000000000.0;
        double deltaTime = (now - lastTime) / secondStandard;
        lastTime = now;
        update(deltaTime);
        gc.setStroke(Color.CRIMSON);
        gc.setLineWidth(2);
        gc.strokeRect(Constants.MARGIN_WIDTH, Constants.MARGIN_HEIGHT, Constants.PLAY_SCREEN_WIDTH, Constants.PLAY_SCREEN_HEIGHT);
        gameScene.updateLivesUI(ball.getLives());
        if (ball.getLives() <= 0) {
            sceneManager.switchTo("GameOver");
            this.stop();
        }
        boolean nextLevel = true;
        for (Brick brick : brickList) {
            if (!brick.getIsDestroyed()) {
                nextLevel = false;
                break;
            }
        }
        if (nextLevel) gameScene.goToNextLevel();
        render();
    }

    private void update(double deltaTime) {
        paddle.setKeyPressed(KeyCode.LEFT, input.isLeftPressed());
        paddle.setKeyPressed(KeyCode.RIGHT, input.isRightPressed());
        if (input.isSpacePressed() && !ball.play) ball.play = true;
        paddle.update(deltaTime);
        for (Brick brick : brickList) {
            if (!brick.getIsDestroyed()) {
                brick.update(deltaTime);
                if (brick.getIsDestroyed()) {
                    System.out.println("\uD83C\uDFAF Score: " + (++score));
                }
            }
        }
        ball.update(deltaTime);
        checkCollision();
    }

    private void checkCollision() {
        BallWithPaddle.checkCollision(ball, paddle);
        for (Brick brick : brickList) {
            BallWithBrick.checkCollision(ball, brick);
        }
    }

    private void render() {
        ball.render(gc);
        paddle.render(gc);
        for (Brick brick : brickList) {
            brick.render(gc);
        }
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

}