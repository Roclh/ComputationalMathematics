package nodes;

import javafx.scene.control.Button;

public class MainButton extends Button {
    public MainButton(String text) {
        this.setStyle("-fx-font: \"Helvetica\";" +
                "-fx-font-size: 18px");
        this.setText(text);
    }
}
