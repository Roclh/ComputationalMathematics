package nodes;

import javafx.scene.control.ComboBox;
import math.excpetions.EmptyFormulaException;
import services.History;

public class FormulaInput extends ComboBox<String>{
    History history;

    public FormulaInput(History history){
        this.setPromptText("Введите функцию, которую необходимо рассчитать");
        this.setMinWidth(500d);
        this.setMaxWidth(1000d);
        this.history = history;
        this.getItems().addAll(history.getFunctions());
        this.setEditable(true);
    }

    public String getFormula() throws EmptyFormulaException {
        if(this.getValue().isEmpty()) throw new EmptyFormulaException("В вводе отсутствует функция");
        else return this.getValue();
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
