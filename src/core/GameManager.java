package core;

import RenderManager.RenderMap;
import entities.Ball;
import entities.Brick;
import entities.BrickType;
import entities.Paddle;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import utils.Constants;

import java.util.ArrayList;

// to manage game, scene
public class GameManager {

    public GameManager(Scene scene) {
        Canvas canvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // System.out.println("Lives: " + ball.getLives());
        RenderMap rendermap = new RenderMap();
        ArrayList<Brick> brickList = new ArrayList<>();
        rendermap.render(2, brickList);
        Paddle paddle = new Paddle((Constants.SCREEN_WIDTH - 200) / 2.0, Constants.SCREEN_HEIGHT - 50, 200, 20);
        Ball ball = new Ball();
        InputHandler input = new InputHandler();
        input.getKey(scene);
        Pane root = new Pane(canvas);
        scene.setRoot(root);
        GameLoop gameloop = new GameLoop(ball, paddle, brickList, gc, input);
        gameloop.start();
    }
}