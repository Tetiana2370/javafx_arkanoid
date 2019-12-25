package watki;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

class Brick{

    private int x, y, width, height;
    private boolean destroyed = false;
    private GraphicsContext gc;

    Brick(int x, int y, GraphicsContext gc){
        this.x = x;
        this.y = y;
        this.width = Constants.BRICK_WIDTH;
        this.height = Constants.BRICK_HEIGHT;
        this.gc = gc;

        draw();
    }
    private void draw(){
        Image image = new Image(getClass().getResourceAsStream("graphics/brick.png"));
        gc.drawImage(image, x, y, width, height);
        gc.setFill(Constants.BRICK_COL);
//        gc.fillRect(x, y, width, height);
    }

    int breaks(Ball ball, int ballX, int ballY, int ballD){
        if(destroyed) return  -1;
        int res = -1;
        if(ballY >= this.y && ballY <= (this.y+height)){
            if((ballX+ballD/2) <= (this.x + width) && (ballX + ballD) >= this.x){
                //touches bottom
                res = 1;
            }else if(ballX <= (this.x+width) && ballX >= (this.x + width - 3)){
                //touches right side
                res = 2;
            }

        }else if((ballX + ballD) <= (this.x + width) && (ballX + ballD) >= this.x ){
            if((ballX + ballD/2) <= this.x && ballY <= (this.y + height) && (ballY + ballD) >= this.y ){
                //touches left side
                res = 2;
            }else if((ballY + ballD) <= this.y && ballY >= this.y){
                //touches top
                res = 1;
            }
        }
        if(res != -1){
            //erase brick
            destroyed = true;
            gc.setFill(Constants.WALL_COL);
            gc.fillRect(x, y, width, height);
        }

        //return 1 - breaks from top/ bottom, 2 - from right/left
        return  res;
    }

    int getY(){
        return y;
    }
}
