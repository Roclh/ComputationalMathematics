package nodes;

import javafx.scene.control.ComboBox;

public class ChoiseBox <T> extends ComboBox<T> {
    public ChoiseBox(T[] values){
        this.setStyle("-fx-font: \"Helvetica\";" +
                "-fx-font-size: 18px");
        this.getItems().addAll(values);
        this.setValue(this.getItems().stream().findAny().get());
    }
}
