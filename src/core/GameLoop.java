package core;

import UI.Shop.CreditsManager;
import UI.Game.GameScene;
import UI.SceneManager;
import entities.Ball;
import entities.Brick;
import entities.BrickType;
import entities.Paddle;
import CollisionManager.BallWithPaddle;
import CollisionManager.BallWithBrick;
import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import utils.Constants;
import java.util.ArrayList;
import PowerUpSystem.PowerUpManager;
import java.util.Iterator;

/**
 * Game loop with multi-ball lives logic and time scale support.
 * UPDATED: Added timeScale for SlowMotion power-up
 */
public class GameLoop extends AnimationTimer {

    private final SceneManager sceneManager;
    private final GameScene gameScene;

    private final Ball ball;
    private final Paddle paddle;
    private final ArrayList<Brick> brickList;
    private final GraphicsContext gc;
    private final InputHandler input;
    private long lastTime = 0;
    private final IntegerProperty score = new SimpleIntegerProperty(0);

    private final PowerUpManager powerUpManager;
    private final ArrayList<Ball> ballList;

    private boolean isPaused = false;

    // === NEW: Time scale for slow motion ===
    private double timeScale = 1.0; // 1.0 = normal, 0.5 = slow motion

    public GameLoop(Ball ball, Paddle paddle, ArrayList<Brick> brickList, GraphicsContext gc,
                    InputHandler input, SceneManager sceneManager, GameScene gameScene,
                    PowerUpManager powerUpManager, ArrayList<Ball> ballList) {
        this.sceneManager = sceneManager;
        this.gameScene = gameScene;
        this.ball = ball;
        this.paddle = paddle;
        this.brickList = brickList;
        this.gc = gc;
        this.input = input;
        this.ballList = ballList;
        this.powerUpManager = powerUpManager;
        lastTime = 0;

        // === NEW: Set GameLoop reference in PowerUpManager ===
        powerUpManager.setGameLoop(this);
    }

    @Override
    public void handle(long now) {

        if (isPaused) {
            lastTime = now;
            isPaused = false;
            return;
        }

        if (lastTime == 0) {
            lastTime = now;
            return;
        }

        gc.clearRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        double secondStandard = 1000000000.0;
        double deltaTime = (now - lastTime) / secondStandard;
        lastTime = now;

        // === NEW: Apply time scale (for slow motion) ===
        double scaledDeltaTime = deltaTime * timeScale;

        gc.setStroke(Color.CRIMSON);
        gc.setLineWidth(2);
        gc.strokeRect(Constants.MARGIN_WIDTH, Constants.MARGIN_HEIGHT, Constants.PLAY_SCREEN_WIDTH, Constants.PLAY_SCREEN_HEIGHT);

        gameScene.updateLivesUI();

        update(scaledDeltaTime);

        // GAME OVER: Main ball out of lives AND no extra balls
        if (ball.getLives() <= 0 && ballList.isEmpty()) {
            sceneManager.switchTo("GameOver");
            this.stop();
            gameScene.updateHighScores();
            return;
        }

        // Check win condition
        boolean nextLevel = true;
        for (Brick brick : brickList) {
            if (!brick.getIsDestroyed()) {
                nextLevel = false;
                break;
            }
        }
        if (nextLevel) {
            gameScene.goToNextLevel();
        }

        render();
    }

    @Override
    public void stop() {
        super.stop();
        isPaused = true;
    }

    private void update(double deltaTime) {
        paddle.setKeyPressed(KeyCode.LEFT, input.isLeftPressed());
        paddle.setKeyPressed(KeyCode.RIGHT, input.isRightPressed());

        // SPACE launches main ball if it has lives and not playing
        if (input.isSpacePressed() && ball.getLives() > 0 && !ball.play) {
            ball.play = true;
        }

        paddle.update(deltaTime);

        // Update bricks and spawn power-ups
        for (Brick brick : brickList) {
            if (!brick.getIsDestroyed()) {
                brick.update(deltaTime);
                if (brick.getIsDestroyed()) {
                    int newScore = score.get() + 1;
                    CreditsManager.getInstance().addCredits(10);
                    if (brick.getCurrentBrickType() == BrickType.HALF_MEDIUM) {
                        newScore += 1;
                        CreditsManager.getInstance().addCredits(5);
                    } else if (brick.getCurrentBrickType() == BrickType.MEDIUM) {
                        newScore += 2;
                        CreditsManager.getInstance().addCredits(10);
                    } else if (brick.getCurrentBrickType() == BrickType.HALF_HARD) {
                        newScore += 3;
                        CreditsManager.getInstance().addCredits(20);
                    }   else if (brick.getCurrentBrickType() == BrickType.HARD) {
                        newScore += 4;
                        CreditsManager.getInstance().addCredits(30);
                    }
                    score.set(newScore);
                }
            }

            if (brick.shouldSpawnPowerUp()) {
                powerUpManager.trySpawnPowerUp(brick);
            }
        }

        // Update main ball (if has lives)
        if (ball.getLives() > 0) {
            ball.update(deltaTime);
        }

        // Update extra balls and remove dead ones
        Iterator<Ball> ballIterator = ballList.iterator();
        while (ballIterator.hasNext()) {
            Ball extraBall = ballIterator.next();
            extraBall.update(deltaTime);

            // Remove extra ball when it dies
            if (extraBall.getLives() <= 0) {
                System.out.println("⚽ Extra ball removed from game");
                ballIterator.remove();
            }
        }

        // === Check if ALL balls have fallen ===
        boolean mainBallFell = ball.getLives() > 0 && !ball.play &&
                ball.getPositionY() > Constants.SCREEN_HEIGHT - 200;
        boolean noExtraBalls = ballList.isEmpty();

        if (mainBallFell && noExtraBalls) {
            // ALL balls fell → Decrement player lives
            ball.setLives(ball.getLives() - 1);
            System.out.println("💔 All balls fell! Lives remaining: " + ball.getLives());

            if (ball.getLives() > 0) {
                // Respawn main ball
                ball.setPositionX(Constants.BALL_START_POSITION_X);
                ball.setPositionY(Constants.BALL_START_POSITION_Y);
                ball.setDirectionX(0.0);
                ball.setDirectionY(1.0);
                System.out.println("⚽ Ball respawned! Lives remaining: " + ball.getLives());
            } else {
                System.out.println("💀 Game Over!");
                return;
            }
        }

        // Update power-ups
        powerUpManager.update(deltaTime);

        checkCollision();
    }

    private void checkCollision() {
        // Main ball collisions (only if has lives)
        if (ball.getLives() > 0) {
            BallWithPaddle.checkCollision(ball, paddle);
            for (Brick brick : brickList) {
                BallWithBrick.checkCollision(ball, brick);
            }
        }

        // Extra balls collisions
        for (Ball extraBall : ballList) {
            BallWithPaddle.checkCollision(extraBall, paddle);
            for (Brick brick : brickList) {
                BallWithBrick.checkCollision(extraBall, brick);
            }
        }

        // Power-up collision with paddle
        powerUpManager.checkCollision(paddle);
    }

    private void render() {
        // Render main ball (only if has lives)
        if (ball.getLives() > 0) {
            ball.render(gc);
        }
        for (Brick brick : brickList) {
            brick.render(gc);
        }

        // Render extra balls
        for (Ball extraBall : ballList) {
            extraBall.render(gc);
        }
        paddle.render(gc);
        // Render power-ups
        powerUpManager.render(gc);
    }

    // === Getters and Setters ===

    public void setScore(int value) {
        score.set(value);
    }

    public int getScore() {
        return score.get();
    }

    public IntegerProperty getScoreProperty() {
        return score;
    }

    public PowerUpManager getPowerUpManager() {
        return powerUpManager;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    // === NEW: Time scale control (for slow motion) ===
    public double getTimeScale() {
        return timeScale;
    }

    public void setTimeScale(double timeScale) {
        this.timeScale = Math.max(0.1, Math.min(2.0, timeScale)); // Clamp 0.1-2.0
        System.out.println("⏱️ Time scale changed: " + this.timeScale);
    }

    public void reset() {
        input.reset();
        ballList.clear();
        powerUpManager.reset();
        lastTime = 0;
        isPaused = false;
    }
}