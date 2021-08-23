package nodes;

import javafx.scene.control.ComboBox;
import services.History;

public class FormulaInput extends ComboBox<String>{
    History history;

    public FormulaInput(History history){
        this.setStyle("-fx-font: \"Helvetica\";" +
                "-fx-font-size: 18px");
        this.setPromptText("Введите функцию, которую необходимо рассчитать");
        this.setMinWidth(500d);
        this.setMaxWidth(1000d);
        this.history = history;
        this.getItems().addAll(history.getFunctions());
        this.setEditable(true);
    }

    public boolean save(){
        return history.save();
    }

    public boolean add(){
        if(history.addToHistory(this.getValue())){
            this.getItems().clear();
            this.getItems().addAll(history.getFunctions());
            return true;
        }else{
            return false;
        }
    }
}
