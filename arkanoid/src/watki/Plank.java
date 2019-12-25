package watki;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;


class Plank implements Runnable{

    private GraphicsContext gc;
    private Pane root;
    private int x, y, width, height, speed;
    private Image image;

    Plank(GraphicsContext gc){
        this.gc = gc;
        x = Constants.PLANK_X;
        y = Constants.PLANK_Y;
        width = Constants.PLANK_WIDTH;
        height = Constants.PLANK_HEIGHT ;
        speed = Constants.PLANK_SPEED;
        image = new Image(getClass().getResourceAsStream("graphics/plank.png"));
    }

    // 0 - move left , 1 - move right
    void move(int direction){
        if(direction == 0){
            if (x > Constants.BORDERS) {
                //erase rigth piece of plank
                gc.setFill(Constants.WALL_COL);
                gc.fillRect(x, y, width, height);
                //draw  plank
                gc.drawImage(image, x-speed, y, width, height );
                //set new position of plank
                x = x-speed;
            }

        }
        else{
            if (x + Constants.BORDERS + width < Constants.WIDTH) {
                //erase left piece of plank
                gc.setFill(Constants.WALL_COL);
                gc.fillRect(x, y, speed, height);
                //draw  plank
                gc.drawImage(image, x+speed, y, width, height );
                //set new position of plank
                x = x+speed;
            } else { /* ball has free way*/ }

        }
    }

    boolean savesBall(Ball ball){
        if (ball.getX() <= (this.x + width) && ball.getX() >= (this.x + this.width - 2*ball.getD())){

            if(ball.getDirX() == -1) {
                ball.setDirX(1);
            }
        }else if((ball.getX() + ball.getD()) >= this.x  && (ball.getX() + ball.getD() <= (this.x +  2*ball.getD()))){

            if(ball.getDirX() == 1){
                ball.setDirX(-1);
            }
        }else if(ball.getX() >= this.x && ball.getX() <= (this.x + this.width )){
            ball.setSpeed(10);
        }
        else return false;

        return true;
    }

    @Override
    public void run(){
        gc.drawImage(image, x, y, width, height);
        try{
            Thread.sleep(1000);
        }catch (Exception ignored){ }
    }
}
