package command.commands;

import command.Command;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import math.FormulaInterpreter;
import math.NumericalSolutionOfNonlinearEquationsLogics;
import math.enums.NumericalSolutionOfNonlinearEquationsMethods;
import nodes.*;
import services.AlertEjector;
import services.History;

import java.util.ArrayList;

public class NumericalSolutionOfNonlinearEquations extends Command {
    public NumericalSolutionOfNonlinearEquations() {
        super("Численное решение нелинейных уравнений");
    }

    @Override
    public ArrayList<Node> getNodes() {
        ArrayList<Node> nodes = new ArrayList<>();
        Label header = new Label("Численное решение нелинейных уравнений");
        header.setId("h1");
        FormulaInput formulaInput = new FormulaInput(new History("NumericalSolution.txt"));
        ValueInput minValueInput = new ValueInput("Min");
        ValueInput maxValueInput = new ValueInput("Max");
        ValueInput accuracyInput = new ValueInput("Accuracy", 100d, 150d);
        LineChart<Number, Number> chart = FormulaInterpreter.getChart();
        ChooseBox<NumericalSolutionOfNonlinearEquationsMethods> cbxMethods = new ChooseBox<>(NumericalSolutionOfNonlinearEquationsMethods.values());
        MainPane flowPane = new MainPane(10, 10,header, formulaInput, minValueInput, maxValueInput, accuracyInput, cbxMethods, chart);
        MainButton calculate = new MainButton("Рассчитать");
        calculate.setOnMouseClicked(event -> {
            try {
                chart.getData().removeAll(chart.getData());
                if (flowPane.getChildren().stream().anyMatch(node -> node.getClass().equals(FlowPane.class))) {
                    flowPane.getChildren().remove(flowPane.getChildren().stream().filter(node -> node.getClass().equals(FlowPane.class)).findFirst().get());
                }
                if (accuracyInput.getText().isEmpty()) {
                    flowPane.getChildren().add(NumericalSolutionOfNonlinearEquationsLogics.solve(
                            formulaInput.getValue(),
                            Double.parseDouble(minValueInput.getText()),
                            Double.parseDouble(maxValueInput.getText()),
                            cbxMethods.getValue()
                    ));
                } else {
                    flowPane.getChildren().add(NumericalSolutionOfNonlinearEquationsLogics.solve(
                            formulaInput.getValue(),
                            Double.parseDouble(minValueInput.getText()),
                            Double.parseDouble(maxValueInput.getText()),
                            Double.parseDouble(accuracyInput.getText()),
                            cbxMethods.getValue()
                    ));
                }
                chart.getData().add(FormulaInterpreter.getChartData(formulaInput.getValue(), Double.parseDouble(minValueInput.getText()), Double.parseDouble(maxValueInput.getText())));
                if (formulaInput.add()) {
                    formulaInput.save();
                }
            }catch (Exception e){
                e.printStackTrace();
                Alert alert = AlertEjector.ejectError("Введены некорректные данные", "Проверьте правильность введенных вами данных и повторите попытку");
                alert.showAndWait();
            }
        });
        flowPane.getChildren().add(calculate);
        nodes.add(flowPane);
        return nodes;
    }
}
