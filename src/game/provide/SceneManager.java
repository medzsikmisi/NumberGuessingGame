package game.provide;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class SceneManager {

    private static Stage mainWindow;

    private static Scene actualScene;

    private static Map<String, Scene> scenes = new HashMap<>();

    public static void addScene(String sceneId, Scene scene){
        scenes.put(sceneId, scene);
    }
    public static void removeScene(String sceneId){
        scenes.remove(sceneId);
    }
    public static Map<String, Scene> getScenes() {
        return scenes;
    }

    public static void setActualScene(String sceneId){

        SceneManager.actualScene = scenes.get(sceneId);
        SceneManager.mainWindow.setScene(SceneManager.actualScene);
        SceneManager.mainWindow.setTitle("Number guessing Game");
        mainWindow.getIcons().setAll(new Image("/img/cubes.png"));
    }

    public static Scene getActualScene() {
        return actualScene;
    }

    public static void init(Stage mainWindow){
        SceneManager.mainWindow = mainWindow;
    }

    public static Stage getMainWindow() {
        return mainWindow;
    }
}
