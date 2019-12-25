package watki;

import javafx.scene.canvas.GraphicsContext;

class BricksArray {

    private Brick [] bricks;
    private int bricksLeft;
    private int bricksArea;

    BricksArray(GraphicsContext gc){
        int padding, x, y, ncol, nrow, w, h;
        x = y = padding = Constants.BORDERS;
        ncol = Constants.BRICKS_IN_ROW;
        nrow = Constants.BRICKS_IN_COL;
        bricksLeft = ncol * nrow;
        w = Constants.BRICK_WIDTH;
        h = Constants.BRICK_HEIGHT;

        bricks = new Brick[Constants.BRICKS_IN_ROW * Constants.BRICKS_IN_COL];

        for(int i=0; i<nrow; i++){
            for(int j=0; j<ncol; j++){
                bricks[ncol * i + j] = new Brick(x, y, gc);
                x = x + w+padding;
            }
            x = padding;
            y=y+padding+h;

        }

        bricksArea = bricks[bricks.length -1].getY() + Constants.BRICK_HEIGHT;
    }

    int breaksAnyBrick(Ball ball, int ballX, int ballY, int ballD){
        if (bricksLeft <= 0) return  100;
        for(Brick b : bricks){
           int result = b.breaks(ball, ballX, ballY, ballD);
            if(result != -1){
                bricksLeft --;
                return result;
            }
        }
        return  -1;
    }

    int getBricksArea(){
        return bricksArea;
    }
    int getScore(){
        return (Constants.BRICKS_IN_ROW * Constants.BRICKS_IN_COL - bricksLeft)*5;
    }
}
