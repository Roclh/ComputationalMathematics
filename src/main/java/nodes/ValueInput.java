package nodes;

import javafx.scene.control.TextField;
import math.excpetions.ZeroOrNegativeAccuracyException;


public class ValueInput <T extends Number> extends TextField {
    private T min = null;
    private T max = null;
    private boolean notZero = false;
    private boolean notNegative = false;


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
        checkBounds();
        return Double.parseDouble(getText());
    }

    public void setZero(boolean notZero){
        this.notZero = notZero;
    }

    public void setNotNegative(boolean notNegative){
        this.notNegative = notNegative;
    }

    public void setMinValue(T value){
        this.min = value;
    }

    public void setMaxnValue(T value){
        this.max = value;
    }

    public int getInteger() throws NumberFormatException{
        checkBounds();
        return Integer.parseInt(getText());
    }

    public double getAccuracyDouble() throws ZeroOrNegativeAccuracyException, NumberFormatException {
        checkBounds();
        if(Double.parseDouble(getText())==0){
            throw new ZeroOrNegativeAccuracyException("Вы ввели 0 или отрицательное значение в точность вычислений, данная точность невозможна");
        }
        return Double.parseDouble(getText());
    }

    private void checkBounds() throws NumberFormatException{
        if(this.notZero){
            if(Double.parseDouble(this.getText())==0)throw new NumberFormatException("Поле не может быть нулем");
        }
        if(this.notNegative){
            if(Double.parseDouble(this.getText())<0)throw new NumberFormatException("Поле не может быть отрицательным");
        }
        if(this.min!=null){
            if (this.min.doubleValue() >= Double.parseDouble(this.getText())) {
                throw new NumberFormatException("Заданное число вышло за границы доступных чисел");
            }
        }
        if(this.max!=null){
            if(Double.parseDouble(this.getText()) >= this.max.doubleValue()){
                throw new NumberFormatException("Заданное число вышло за границы доступных чисел");
            }
        }
    }

    public void setBounds(T min, T max){
        this.min=min;
        this.max=max;
    }
}
