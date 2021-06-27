package game.provide;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CustomAlert extends Alert {
    public CustomAlert(AlertType alertType) {
        super(alertType);
        this.setGraphic(null);
        this.setHeaderText(null);
        ((Stage) this.getDialogPane()
                .getScene()
                .getWindow())
                .getIcons()
                .add(new Image("/img/cubes.png"));
    }

    public CustomAlert(AlertType alertType, String s, ButtonType... buttonTypes) {
        super(alertType, s, buttonTypes);
        this.setGraphic(null);
        this.setHeaderText(null);
        ((Stage) this.getDialogPane()
                .getScene()
                .getWindow())
                .getIcons()
                .add(new Image("/img/cubes.png"));
    }
}
