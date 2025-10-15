package utils;

public class Constants {

    public static final int SCREEN_WIDTH = 1280; // Default window width.
    public static final int SCREEN_HEIGHT = 930; // Default window height.
    public static final double PADDLE_SPEED = 650.0; // Default paddle movement speed.
    public static final double BALL_SPEED = 780.0; // Default ball movement speed.
    public static final int BALL_RADIUS = 10; // Default ball radius.
    public static final int BALL_LIVES = 100; // Default ball lives.
    public static final double START_POSITION_X = (SCREEN_WIDTH - BALL_RADIUS * 2)/ 2.0; // Default ball spawn positionX.
    public static final double START_POSITION_Y = SCREEN_HEIGHT - (BALL_RADIUS * 2) - 80; // Default ball spawn positionY.
    public static final double EPSILON = 1e-8; // Calculating errors in real number operations.

}