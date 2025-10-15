package RenderManager;

import entities.Brick;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class RenderMap {

    private final String startPath = "AKARNOID_PROJECT/src/RenderManager/MapList/";
    private final String endPath = ".txt";


    private void screenStandardlize() {

    }

    public void render(int level, ArrayList<Brick> brickList) {
        brickList.clear(); // Remove all past level list of bricks.
        StringBuilder path = new StringBuilder(startPath);
        path.append(level);
        path.append(endPath);
        try (BufferedReader readFile = new BufferedReader(new FileReader(String.valueOf(path)))) {
            String line;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
