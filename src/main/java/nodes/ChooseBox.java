package nodes;

import javafx.scene.control.ComboBox;

public class ChooseBox<T> extends ComboBox<T> {
    public ChooseBox(T[] values){
        this.getItems().addAll(values);
        this.setValue(this.getItems().stream().findAny().get());
    }
}
