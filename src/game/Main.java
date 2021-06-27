package game;

import game.provide.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("frames/start.fxml"));
        primaryStage.setTitle("Number guessing game");
        SceneManager.init(primaryStage);
        SceneManager.addScene("start", new Scene(root, 600, 400));
        SceneManager.setActualScene("start");
        SceneManager.getMainWindow().show();
        root = FXMLLoader.load(getClass().getResource("frames/game.fxml"));
        SceneManager.addScene("game", new Scene(root, 600, 400));

    }


    public static void main(String[] args) {
        launch(args);
    }
}
