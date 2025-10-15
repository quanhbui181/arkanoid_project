package entities;

import utils.Constants;
import core.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

/**
 * Ball management.
 *
 */
public class Ball extends MovableObject {

    private double speed = Constants.BALL_SPEED; // Default ball movement speed.
    private int radius = Constants.BALL_RADIUS; // Default ball radius.
    private double directionX = 0.0; // x-axis ball direction.
    private double directionY = 1.0; // y-axis ball direction.
    /* private boolean isOver = false; // Check if game is over. */
    private int lives = Constants.BALL_LIVES; // Remaining lives.
    private final Random rd = new Random(); // Ball direction randomizer.

    /**
     * Set ball direction to a random angle (30 to 150 degrees).
     *
     * @param ballCenterX PositionX of the center of the ball.
     * @param paddleCenterX PositionX of the center of the paddle.
     * @param paddleWidth The height of the paddle.
     */
    public void setDirection(double ballCenterX, double paddleCenterX, double paddleWidth) {
        // Determine the ball's position relative to the paddle (left, middle, or right).
        double relativeIntersectX = ballCenterX - paddleCenterX; // left ( < 0), middle ( = 0), right (> 0).
        // Scale the ball's relative position to a value between -1.0 and 1.0.
        double normalize = relativeIntersectX / (paddleWidth / 2.0);
        /* if (normalize > 1.0) normalize = 1.0;
        else if (normalize < -1.0) normalize = -1.0; */
        double baseAngle = Math.PI / 2;
        double maxAngleOffset = Math.PI / 3;
        double bounceAngle = baseAngle - (normalize * maxAngleOffset); // (30 - 150 degree).
        directionX = Math.cos(bounceAngle);
        directionY = -Math.sin(bounceAngle);
    }


    /**
     * Initialize object.
     *
     */
    public Ball() {
        super(Constants.START_POSITION_X, Constants.START_POSITION_Y, Constants.BALL_RADIUS * 2, Constants.BALL_RADIUS * 2);
        boolean isLeft = (rd.nextInt(2) == 0);
        this.setDeltaX(speed * directionX);
        this.setDeltaY(speed * directionY);
        System.out.println("⚽ Ball Init Success !");
        System.out.println("⚽ Remaining lives: " + lives);
    }

    @Override
    public void move(double deltaTime) {

        this.setDeltaX(directionX * speed * deltaTime);
        this.setDeltaY(directionY * speed * deltaTime);
        double newPositionX = this.getPositionX() + this.getDeltaX();
        double newPositionY = this.getPositionY() + this.getDeltaY();

        if (newPositionX + this.getWidth() > Constants.SCREEN_WIDTH) {
            newPositionX = Constants.SCREEN_WIDTH - this.getWidth();
            directionX *= -1;
        } else if (newPositionX < 0) {
            newPositionX = 0;
            directionX *= -1;
        }

        if (newPositionY < 0) {
            newPositionY = 0;
            directionY *= -1;
        } else if (newPositionY > Constants.SCREEN_HEIGHT + 10) {
            lives -= 1;
            System.out.println("⚽ Remaining lives: " + lives);
            if (lives <= 0) {
                /* isOver = true; */
                System.out.println("\uD83D\uDC80 Dead !");
                return;
            }
            newPositionX = Constants.START_POSITION_X;
            newPositionY = Constants.START_POSITION_Y;
            boolean isLeft = (rd.nextInt(2) == 0);
            directionX = 0.0; directionY = 1.0;
        }

        this.setPositionX(newPositionX);
        this.setPositionY(newPositionY);
    }

    @Override
    public void update(double deltaTime) {
        if (lives > 0) move(deltaTime);
        //Update power-up sau...
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillOval(this.getPositionX(), this.getPositionY(), radius * 2, radius * 2);
    }

    //Getter and Setter method.

    public double getSpeed() { return speed; }

    public void setSpeed(double speed) { this.speed = speed; }

    public int getRadius() { return radius; }

    public void setRadius(int radius) { this.radius = radius; }

    public double getDirectionX() { return directionX; }

    public void setDirectionX(double directionX) { this.directionX = directionX; }

    public double getDirectionY() { return directionY; }

    public void setDirectionY(double directionY) { this.directionY = directionY; }

    /* public boolean isOver() { return isOver; }

    public void setOver(boolean isOver) { this.isOver = isOver; } */

    public int getLives() { return lives; }

    public void setLives(int lives) { this.lives = lives; }
}