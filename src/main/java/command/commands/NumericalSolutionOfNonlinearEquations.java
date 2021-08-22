package command.commands;

import command.Command;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import math.FormulaInterpreter;
import math.NumericalSolutionOfNonlinearEquationsLogics;
import math.enums.NumericalSolutionOfNonlinearEquationsMethods;

import java.util.ArrayList;

public class NumericalSolutionOfNonlinearEquations extends Command {
    public NumericalSolutionOfNonlinearEquations() {
        super("Численное решение нелинейных уравнений");
    }

    @Override
    public ArrayList<Node> getNodes() {
        ArrayList<Node> nodes = new ArrayList<>();
        Font font = new Font("Helvetica", 18);
        TextField formulaInput = new TextField();
        formulaInput.setFont(font);
        formulaInput.setMinWidth(500d);
        formulaInput.setPromptText("Введите функцию, которую необходимо рассчитать");
        TextField minValueInput = new TextField();
        minValueInput.setFont(font);
        minValueInput.setMaxWidth(50d);
        minValueInput.setPromptText("Min");
        TextField maxValueInput = new TextField();
        maxValueInput.setFont(font);
        maxValueInput.setMaxWidth(50d);
        maxValueInput.setPromptText("Max");
        TextField accuracyInput = new TextField();
        accuracyInput.setFont(font);
        accuracyInput.setMaxWidth(100d);
        accuracyInput.setPromptText("Accuracy");
        LineChart<Number, Number> chart = FormulaInterpreter.getChart();
        ComboBox<NumericalSolutionOfNonlinearEquationsMethods> cbxMethods = new ComboBox<>();
        cbxMethods.getItems().addAll(NumericalSolutionOfNonlinearEquationsMethods.values());
        cbxMethods.setValue(cbxMethods.getItems().stream().findAny().get());
        FlowPane flowPane = new FlowPane(10, 10, formulaInput, minValueInput, maxValueInput, accuracyInput, cbxMethods, chart);
        Button calculate = new Button("Рассчитать");
        calculate.setFont(font);
        calculate.setOnMouseClicked(event -> {
            chart.getData().removeAll(chart.getData());
            if(flowPane.getChildren().stream().anyMatch(node -> node.getClass().equals(FlowPane.class))){
                flowPane.getChildren().remove(flowPane.getChildren().stream().filter(node -> node.getClass().equals(FlowPane.class)).findFirst().get());
            }
            if (accuracyInput.getText().isEmpty()) {
                flowPane.getChildren().add(NumericalSolutionOfNonlinearEquationsLogics.solve(
                        formulaInput.getText(),
                        Double.parseDouble(minValueInput.getText()),
                        Double.parseDouble(maxValueInput.getText()),
                        cbxMethods.getValue()
                ));
                chart.getData().add(FormulaInterpreter.getChartData(formulaInput.getText(), Double.parseDouble(minValueInput.getText()), Double.parseDouble(maxValueInput.getText())));
            } else {
                flowPane.getChildren().add(NumericalSolutionOfNonlinearEquationsLogics.solve(
                        formulaInput.getText(),
                        Double.parseDouble(minValueInput.getText()),
                        Double.parseDouble(maxValueInput.getText()),
                        Double.parseDouble(accuracyInput.getText()),
                        cbxMethods.getValue()
                ));
                chart.getData().add(FormulaInterpreter.getChartData(formulaInput.getText(), Double.parseDouble(minValueInput.getText()), Double.parseDouble(maxValueInput.getText())));
            }
        });
        flowPane.getChildren().add(calculate);
        nodes.add(flowPane);
        return nodes;
    }
}
