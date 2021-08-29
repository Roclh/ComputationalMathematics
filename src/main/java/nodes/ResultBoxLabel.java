package nodes;
import javafx.scene.control.Label;

public class ResultBoxLabel extends Label {
    public ResultBoxLabel() {
    }

    public ResultBoxLabel(String text){
        super(text);
        this.setId("result");
    }
}
