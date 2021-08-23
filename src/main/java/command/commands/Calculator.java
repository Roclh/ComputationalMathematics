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
import nodes.FormulaInput;
import nodes.MainButton;
import nodes.MainPane;
import nodes.ValueInput;
import services.AlertEjector;
import services.History;

import java.util.ArrayList;

public class Calculator extends Command {
    public Calculator() {
        super("Калькулятор функций");
    }

    @Override
    public ArrayList<Node> getNodes() {
        ArrayList<Node> items = new ArrayList<>();
        Font font = new Font("Helvetica",18 );
        FormulaInput formulaInput = new FormulaInput(new History("Calculator.txt"));
        ValueInput valueInput = new ValueInput("X");
        Label result = new Label("");
        result.setFont(font);
        LineChart<Number, Number> chart = FormulaInterpreter.getChart();
        MainButton calculate = new MainButton("Рассчитать");
        calculate.setOnMouseClicked(event -> {
            double x = Double.parseDouble(valueInput.getText());
            try{
                result.setText(FormulaInterpreter.calculate(formulaInput.getValue(), x) + " " + String.valueOf(FormulaInterpreter.calculateDerivative(formulaInput.getValue(), x, 0.000001d)));
                formulaInput.add();
                formulaInput.save();
                chart.getData().removeAll(chart.getData());
                chart.getData().add(FormulaInterpreter.getChartData(formulaInput.getValue(), -10d, 10d, 0.1));
            }catch (Exception e){
                Alert alert = AlertEjector.ejectError("Введена неверная формула", "Проверьте написание формулы и повторите попытку");
                alert.showAndWait();
            }
        });

        MainPane flowPane = new MainPane(10, 10, formulaInput, valueInput, calculate, result, chart);
        flowPane.setPadding(new Insets(10d));
        items.add(flowPane);
        return items;
    }
}
