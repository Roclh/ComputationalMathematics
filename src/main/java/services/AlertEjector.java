package services;

import javafx.scene.control.Alert;

public class AlertEjector {
    public static Alert ejectError(String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert;
    }

    public static Alert ejectConfirmation(String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert;
    }
}
