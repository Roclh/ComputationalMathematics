package nodes;

import javafx.scene.control.TextField;


public class ValueInput extends TextField {

    public ValueInput(String promptText){
        this.setStyle("-fx-font: \"Helvetica\";" +
                "-fx-font-size: 18px");
        this.setPromptText(promptText);
        this.setMinWidth(50d);
        this.setMaxWidth(100d);
    }

    public ValueInput(String promptText, double minWidth, double maxWidth){
        this.setStyle("-fx-font: \"Helvetica\";" +
                "-fx-font-size: 18px");
        this.setPromptText(promptText);
        this.setMinWidth(minWidth);
        this.setMaxWidth(maxWidth);
    }
}
