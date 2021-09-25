package nodes;

import javafx.scene.control.TextField;
import math.excpetions.ZeroOrNegativeAccuracyException;


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

    public double getDouble() throws NumberFormatException{
        return Double.parseDouble(getText());
    }

    public double getAccuracyDouble() throws ZeroOrNegativeAccuracyException, NumberFormatException {
        if(Double.parseDouble(getText())==0){
            throw new ZeroOrNegativeAccuracyException("Вы ввели 0 или отрицательное значение в точность вычислений, данная точность невозможна");
        }
        return Double.parseDouble(getText());
    }
}
