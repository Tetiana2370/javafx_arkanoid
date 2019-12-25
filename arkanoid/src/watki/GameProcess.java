package watki;

public class GameProcess implements Runnable{

    private Plank plank;
    private Ball ball;
    private Game game;

    GameProcess(Plank plank, Ball ball, Game game){
        this.plank = plank;
        this.ball = ball;
        this.game = game;
    }

    @Override
    public void run() {

        Thread plankTread = new Thread(plank);
        Thread ballThread = new Thread(ball);
        plankTread.start();
        ballThread.start();

        try{
            ballThread.join();
            plankTread.join();
        }catch (Exception e){
            System.out.println("exception in game process join()");
        }

        game.endGame();
    }
}