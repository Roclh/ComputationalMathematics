package command.commands;

import command.Command;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
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
        Font font = new Font("Helvetica",18 );
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
        FlowPane flowPane = new FlowPane(10, 10, formulaInput,minValueInput, maxValueInput, accuracyInput);
        Button calculate = new Button("Рассчитать");
        calculate.setFont(font);
        calculate.setOnMouseClicked(event -> {
            flowPane.getChildren().add(NumericalSolutionOfNonlinearEquationsLogics.solve(
                    formulaInput.getText()
                    , Double.parseDouble(minValueInput.getText())
                    ,Double.parseDouble(maxValueInput.getText())
                    ,Double.parseDouble(accuracyInput.getText()),
                    NumericalSolutionOfNonlinearEquationsMethods.HALVES
                    ));
        });
        flowPane.getChildren().add(calculate);
        nodes.add(flowPane);
        return nodes;
    }
}
