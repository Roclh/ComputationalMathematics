package nodes;

import javafx.scene.control.TextArea;


public class MainTextArea extends TextArea {

    public MainTextArea(String text){
        this.setPromptText(text);
        this.setMinWidth(500d);
        this.setMaxWidth(1000d);
        this.setEditable(true);
    }
}
