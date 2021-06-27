package game.frames;

import game.provide.CustomAlert;
import game.provide.DataLoader;
import game.provide.SceneManager;
import game.provide.User;
import game.provide.enums.GameMode;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Optional;

public class Start {
    @FXML
    RadioButton hard;
    @FXML
    RadioButton medium;
    @FXML
    RadioButton easy;
    @FXML
    RadioButton extreme;

    public void start() {
        if (hard.isSelected()) GameController.iniT(GameMode.HARD);
        else if (medium.isSelected()) GameController.iniT(GameMode.MEDIUM);
        else if (easy.isSelected()) GameController.iniT(GameMode.EASY);
        else if (extreme.isSelected()) GameController.iniT(GameMode.EXTREME);
        SceneManager.setActualScene("game");
    }

    public void leaderboard() {
        Alert alert = new CustomAlert(Alert.AlertType.INFORMATION);
        alert.setTitle("Window of fame");
        if (!DataLoader.load()) {
            alert.setContentText("There's no data or the data file has been corrupted.\n" +
                    " If you already saved one or more session \nand still having this error, try to clear data.");
            alert.showAndWait();
            return;
        }

        final List<User> data = DataLoader.getData();
        final TextArea wall = new TextArea();
        for (User user : data) {
            wall.appendText(user.getName() + " - " + user.getRounds() + "\n");
        }
        wall.setWrapText(true);
        wall.setEditable(false);
        wall.setMaxWidth(Double.MAX_VALUE);
        wall.setMaxHeight(Double.MAX_VALUE);
        alert.getDialogPane().setExpanded(true);
        alert.getDialogPane().setExpandableContent(wall);
        alert.getButtonTypes().setAll(ButtonType.OK,new ButtonType("Delete data", ButtonBar.ButtonData.OTHER));
        final Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.isPresent()){
            if (buttonType.get()!=ButtonType.OK){
                DataLoader.delete();
            }}

    }

    public void rules() {
        Alert alert = new CustomAlert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rules");
        alert.setContentText("""
                The game generates a number and you can guess it over and over or give up. After each guess, the game tells you that your number is more or less than the original number.
                There are 3 levels:
                Easy - the number is between 0 and 50
                Medium - the number is between 0 and 100
                Hard - the number is between 0 and 1000
                Extreme - the number is between 0 and 1000. You have 50 tries
                Enjoy the game.""");

        alert.showAndWait();
    }
}
