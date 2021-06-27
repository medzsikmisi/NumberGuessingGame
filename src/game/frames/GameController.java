package game.frames;

import game.provide.CustomAlert;
import game.provide.DataLoader;
import game.provide.SceneManager;
import game.provide.User;
import game.provide.enums.Compared;
import game.provide.enums.GameMode;
import game.provide.enums.ResponseType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.SplittableRandom;

public class GameController {
    public static int boundMax = 100, boundMin = 0, currentGameTime = 0;
    private static boolean textIsVisible = false;
    private static int maxRound = 0;
    public static int THENUMBER = 0;
    public static boolean isNewGame = false;
    @FXML
    TextField usersNumber;
    @FXML
    Text response;
    @FXML
    Text numberOfTries;
    @FXML
    Button guess;
    @FXML
    Button giveUp;

    @FXML
    public void initialize() {
        usersNumber.setText(null);
        newGame();
        numberOfTries.setText(String.valueOf(currentGameTime));
        response.setText("");
        response.setVisible(false);
    }

    public static void iniT(final GameMode gameMode) {
        isNewGame = true;
        switch (gameMode) {
            case EASY -> boundMax = 50;
            case MEDIUM -> boundMax = 100;
            case HARD -> boundMax = 1000;
            case EXTREME -> {
                boundMax = 1000;
                maxRound = 15;
            }
        }
        THENUMBER = new SplittableRandom().nextInt(boundMin, boundMax);
        //System.out.println(THENUMBER);
    }

    public void newGame() {
        guess.setOnAction(actionEvent -> onGuessButtonClick());
        giveUp.setOnAction(actionEvent -> giveUp());
        currentGameTime = 0;
    }

    public void onGuessButtonClick() {
        final String userNumber = usersNumber.getText();
        if (userNumber == null) {
            setError("Write a number.", ResponseType.ERROR);
            return;
        }
        final int number;
        try {
            number = Integer.parseInt(userNumber);
        } catch (Exception e) {
            setError("Type a number.", ResponseType.ERROR);
            return;
        }
        if (number > boundMax || number < boundMin) {
            setError("Write a number between " + boundMin + " and " + boundMax + ".", ResponseType.ERROR);
            return;
        }
        numberOfTries.setText(String.valueOf(++currentGameTime));
        final Compared result = compare(number);
        switch (result) {
            case EQUAL -> {
                win();
                return;
            }
            case LESS -> {
                boundMin = number;
                setError("This number is less than my number.", ResponseType.MESSAGE);
            }
            case MORE -> {
                boundMax = number;
                setError("This number is more than my number.", ResponseType.MESSAGE);
            }
        }
        if (maxRound != 0) {
            if (currentGameTime == maxRound / 2) {
                final Alert alert = new CustomAlert(AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setContentText("You have " + (maxRound - currentGameTime) + " guesses left.");
                alert.showAndWait();
            } else if (currentGameTime == maxRound) {
                final Alert alert = new CustomAlert(AlertType.INFORMATION);
                alert.setTitle("Game over");
                alert.setContentText("You lost. My number was: " + THENUMBER);
                alert.showAndWait();
                toStart();
            }
        }

    }

    private void win() {
        Alert alert = new CustomAlert(AlertType.INFORMATION);
        alert.setTitle("You won");
        alert.setContentText("Congratulations, you won.");
        alert.showAndWait();
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.setTitle("Save your session");
        alert.setHeaderText("Do you want to save this game?");
        alert.setContentText("Note: you can see your games in the main menu(leaderboard)");
        final Optional<ButtonType> buttonType = alert.showAndWait();
        if (!buttonType.isPresent()) toStart();
        if (buttonType.get() == ButtonType.YES) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            dialog.setContentText("Enter your name: ");
            ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().setAll(new Image("/img/cubes.png"));
            final Optional<String> s = dialog.showAndWait();
            if (s.isPresent()) {
                DataLoader.load();
                DataLoader.addUser(new User(s.get(), String.valueOf(currentGameTime)));
                if (!DataLoader.save()) {
                    alert = new CustomAlert(AlertType.INFORMATION);
                    alert.setTitle("What the...");
                    alert.setContentText("I was unable to save the file.");
                }
                SceneManager.setActualScene("start");
            }
        }
        toStart();
    }

    private Compared compare(int number) {
        if (number < THENUMBER) return Compared.LESS;
        else if (number == THENUMBER) return Compared.EQUAL;
        else return Compared.MORE;
    }


    private void setError(final String message, final ResponseType type) {
        response.setText(message);
        switch (type) {
            case ERROR -> response.setFill(Color.RED);
            case MESSAGE -> response.setFill(Color.BLACK);
        }
        if (!textIsVisible) {
            textIsVisible = true;
            response.setVisible(true);
        }
    }

    public void giveUp() {
        final Alert alert = new CustomAlert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("WARNING");
        alert.setContentText("Are you sure you want to give up?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        final Optional<ButtonType> result = alert.showAndWait();
        if (result.isEmpty()) return;
        if (result.get() == ButtonType.NO) return;
        toStart();

    }

    private void toStart() {
        initialize();
        SceneManager.setActualScene("start");
    }


}
