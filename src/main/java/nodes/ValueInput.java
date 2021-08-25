package nodes;

import javafx.scene.control.TextField;


public class ValueInput extends TextField {

    public ValueInput(String promptText){
        this.setPromptText(promptText);
        this.setMinWidth(50d);
        this.setMaxWidth(100d);
    }

    public ValueInput(String promptText, double minWidth, double maxWidth){
        this.setPromptText(promptText);
        this.setMinWidth(minWidth);
        this.setMaxWidth(maxWidth);
    }
}
