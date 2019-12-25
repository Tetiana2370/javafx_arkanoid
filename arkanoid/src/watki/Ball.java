package watki;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Random;

public class Ball implements Runnable{

    private double dirX = 1, dirY = -1;
    private int x, y, d;
    private BricksArray bricks;
    private Plank plank;
    private int bricksArea;
    private Image image;
    private GraphicsContext gc;
    private int speed = Constants.BALL_SPEED;

    Ball(GraphicsContext gc, BricksArray bricks, Plank plank){
        Random rand = new Random();
        this.x = rand.nextInt(Constants.WIDTH-Constants.BORDERS);
        this.y = Constants.HEIGHT - 60;
        this.gc = gc;
        this.d =  Constants.BALL_DIAM;
        this.bricks = bricks;
        this.plank = plank;
        bricksArea = bricks.getBricksArea();
        try{
            image = new Image(getClass().getResourceAsStream("graphics/ball.png"));
            gc.drawImage(image, x, y, d, d);
        }catch (Exception e){
            System.out.println("Can't load ball.png");
        }
    }

    private boolean move(){
        // dirX( -1 = left, +1 = right) dirY(-1=to top, +1 = to bottom)
        gc.setFill(Constants.WALL_COL);
        gc.setStroke(Constants.WALL_COL);
        gc.fillRect(x, y, d, d);

        if((x + dirX*speed) >= (Constants.WIDTH - Constants.BORDERS) ||  (x + dirX*speed) <= Constants.BORDERS ){
            // reqouchets from walls
            dirX *= (-1);
        }
        else if((y + dirY) < Constants.BORDERS){
            // reqouchets from top
            dirY *= (-1);

        }else if((y + dirY) >= (Constants.HEIGHT - Constants.BORDERS*2 - Constants.PLANK_HEIGHT)){
            // reqouchets from plank if there is so
            if (plank.savesBall(this)) {
                dirY *= (-1);
            }
            else{
                // game over . score. interrupt;
                try {
                    Thread.sleep(300);
                    return false;
                }catch (Exception ignored){ }
                System.exit(0);
            }

        }

        if (y <= bricksArea) {
            int res = bricks.breaksAnyBrick(this, x, y, d);
                if(res == -1 ) {}
                else if(res == 100) {
                    Thread.currentThread().interrupt();
                }
                else {
                    if (res == 1) {
                        //if ball breaks brick from top/bottom - reverse y direction
                        dirY *= (-1);
                    } else {
                        //if ball breaks brick from left/right - reverse x direction
                        dirX *= (-1);
                    }
                }
        }
        x += dirX * speed;
        y += dirY * speed;

        synchronized (gc){
            gc.drawImage(image, x, y);
        }

        return true;
    }
    int getX(){
        return this.x;
    }
    int getY(){
        return this.y;
    }
    int getD(){
        return this.d;
    }

    double getDirX(){
        return this.dirX;
    }
    double getDirY(){
        return  this.dirY;
    }

    void setDirY(double dirY){
        this.dirY = dirY;
    }
    void setDirX(double dirX){
        this.dirX = dirX;
    }

    void setSpeed(int speed){
        this.speed = speed;

    }
    @Override
    public void run() {
        long threadId = Thread.currentThread().getId();
        System.out.println("Thread # " + threadId + " is doing BALL");

        while (move()){
            try{
                move();
                Thread.sleep(50);
            }catch (Exception e){
                System.out.println("Problem with Thread.sleep() in ball occured");
            }

        }
        System.out.println("BALL : game over");

    }
}
