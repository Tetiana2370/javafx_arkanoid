package watki;

import javafx.scene.paint.Color;

public interface Constants {
    int WIDTH = 600;
    int HEIGHT = 700;
    int BORDERS = 10;

    int PLANK_HEIGHT = 40;
    int PLANK_WIDTH = 140;
    int PLANK_X = WIDTH/2 - PLANK_WIDTH/2;
    int PLANK_Y = HEIGHT - PLANK_HEIGHT + 5;
    int PLANK_SPEED = 12;
    int BRICKS_IN_ROW = 5;
    int BRICKS_IN_COL = 4;
    int BRICK_WIDTH = (WIDTH - BORDERS * (BRICKS_IN_ROW+1)) / BRICKS_IN_ROW;
    int BRICK_HEIGHT = (int) ((0.3*HEIGHT) / BRICKS_IN_COL);

    int BALL_SPEED = 10;
    int BALL_DIAM = 25;
    Color WALL_COL = Color.WHITE;
    Color PLANK_COL = Color.DARKGREY;
    Color BRICK_COL = Color.DARKSALMON;

}
