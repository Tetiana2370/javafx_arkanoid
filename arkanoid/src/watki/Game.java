package watki;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Game extends Application{

    private Stage primaryStage;
    private Plank plank;
    private BricksArray bricks;
    private GraphicsContext gc;
    private boolean gameFinished = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // building entry stage
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Arkanoid");

        VBox root = new VBox();
        Button startButton = new Button("START");
        Button exitButton = new Button("EXIT");
        startButton.setStyle("-fx-background-color: linear-gradient(#6ED977, #A1EDA8); -fx-font-size: 20 ");
        startButton.setMinSize(100, 30);
        exitButton.setStyle(" -fx-background-color: linear-gradient(#F7B0B8, #D7ABC2);-fx-font-size: 20 ");
        exitButton.setMinSize(100, 30);
        startButton.setOnAction(e -> this.play());
        exitButton.setOnAction(e -> System.exit(0));

        root.getChildren().addAll(startButton, exitButton);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(25);

        primaryStage.setScene(new Scene(root, Constants.WIDTH, Constants.HEIGHT));
        primaryStage.show();

    }

    private void drawGameScene(){
        Pane root = new Pane();
        Scene gameScene = new Scene(root, Constants.WIDTH, Constants.HEIGHT);
        Canvas canvas = new Canvas(Constants.WIDTH, Constants.HEIGHT);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Constants.WALL_COL);
        gc.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
        root.getChildren().add(canvas);
        primaryStage.setScene(gameScene);

        //adding key handler to scene
        addKeyHandler(gameScene);
    }

    private void play() {

        drawGameScene();
        this.plank = new Plank(gc);
        this.bricks = new BricksArray(gc);
        Ball ball = new Ball(gc, bricks, plank);

        // updater will check if game is over and set end gameScene
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    public void run() {
                        if(gameFinished){
                            showEndGameScene();
                        }
                    }
                };
                while (true){
                    try{
                        Thread.sleep(100);
                    }catch (InterruptedException ignored){}
                    Platform.runLater(updater);
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

        Thread gameProcessThread = new Thread(new GameProcess(plank, ball, this));
        gameProcessThread.start();

    }


    void endGame(){
        gameFinished = true;
    }

    private void showEndGameScene(){
        VBox root = new VBox();
        root.setSpacing(30);

        Label result = new Label();
        Label score = new Label();

        if(bricks.getScore() == 100){
            result.setText("YOU WON");
            result.setStyle("-fx-font-size: 40; -fx-font-weight: bold; -fx-text-fill:green");
        }else{
            result.setText("GAME OVER");
            result.setStyle("-fx-font-size: 40; -fx-font-weight: bold; -fx-text-fill:red");
        }
        score.setText("Score: " + String.valueOf(bricks.getScore()) + " / 100");
        score.setStyle("-fx-font-size: 35");
        root.getChildren().addAll(result, score);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);

        primaryStage.setScene(new Scene(root, Constants.WIDTH, Constants.HEIGHT));

    }

    private void addKeyHandler(Scene scene){
        scene.setOnKeyPressed(ke -> {
            KeyCode keyCode = ke.getCode();
            if (keyCode.equals(KeyCode.A) || keyCode.equals(KeyCode.LEFT)) {
                plank.move(0);
            }
            else if (keyCode.equals(KeyCode.D) || keyCode.equals(KeyCode.RIGHT)) {
                plank.move(1);
            }
            else if (keyCode.equals(KeyCode.ESCAPE)) {
                System.exit(0);
            }
        });

        scene.setOnKeyReleased (ke -> {
            KeyCode keyCode = ke.getCode();
            if (keyCode.equals(KeyCode.A) || keyCode.equals(KeyCode.LEFT)) {
                plank.move(0);
            }
            else if (keyCode.equals(KeyCode.D) || keyCode.equals(KeyCode.RIGHT)) {
                plank.move(1);
            }
            else if (keyCode.equals(KeyCode.ESCAPE)) {
                System.exit(0);
            }
        });
    }
}