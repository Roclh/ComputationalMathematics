package command.commands;

import command.Command;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import math.FormulaInterpreter;

import java.util.ArrayList;

public class Calculator extends Command {
    public Calculator() {
        super("Калькулятор функций");
    }

    @Override
    public ArrayList<Node> getNodes() {
        ArrayList<Node> items = new ArrayList<>();
        Font font = new Font("Helvetica",18 );
        TextField formulaInput = new TextField();
        formulaInput.setFont(font);
        formulaInput.setMinWidth(500d);
        formulaInput.setPromptText("Введите функцию, которую необходимо рассчитать");
        TextField valueInput = new TextField();
        valueInput.setFont(font);
        valueInput.setMaxWidth(50d);
        valueInput.setPromptText("X");
        Label result = new Label("");
        result.setFont(font);
        LineChart<Number, Number> chart = FormulaInterpreter.getChart();
        Button calculate = new Button("Рассчитать");
        calculate.setFont(font);
        calculate.setOnMouseClicked(event -> {
            double x = Double.parseDouble(valueInput.getText());
            try{
                result.setText(String.valueOf(FormulaInterpreter.calculate(formulaInput.getText(), x)) + " " + String.valueOf(FormulaInterpreter.calculateDerivative(formulaInput.getText(), x, 0.000001d)));
                chart.getData().removeAll(chart.getData());
                chart.getData().add(FormulaInterpreter.getChartData(formulaInput.getText(), -10d, 10d, 0.1));
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Введена неверная формула");
                alert.setContentText("Проверьте правильность написания формулы и повторите попытку");
                alert.showAndWait();
            }
        });

        FlowPane flowPane = new FlowPane(10, 10, formulaInput, valueInput, calculate, result, chart);
        flowPane.setPadding(new Insets(10d));
        items.add(flowPane);
        return items;
    }
}
