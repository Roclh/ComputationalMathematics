package command.commands;

import command.Command;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import math.NumericalIntegrationLogics;
import math.enums.NumericalIntegrationMethods;
import math.excpetions.ZeroOrNegativeAccuracyException;
import nodes.*;
import services.AlertEjector;
import services.History;

import java.util.ArrayList;

public class NumericalIntegration extends Command {
    public NumericalIntegration() {
        super("Численное интегрирование");
    }

    @Override
    public ArrayList<Node> getNodes() {
        ArrayList<Node> nodes = new ArrayList<>();
        Label header = new Label("Численное интегрирование");
        header.setId("h1");
        FormulaInput formulaInput = new FormulaInput(new History("NumericalIntegration.txt"));
        ValueInput<Double> minValueInput = new ValueInput<>("Min");
        ValueInput<Double> maxValueInput = new ValueInput<>("Max");
        ValueInput<Double> accuracyInput = new ValueInput<>("Accuracy", 100d, 150d);
        ChooseBox<NumericalIntegrationMethods> cbxMethods = new ChooseBox<>(NumericalIntegrationMethods.values());
        MainPane flowPane = new MainPane(10, 10, header, formulaInput, minValueInput, maxValueInput, accuracyInput, cbxMethods);
        MainButton calculate = new MainButton("Рассчитать");
        calculate.setOnMouseClicked(event -> {
            Platform.runLater(() -> {
                try {
                    if (flowPane.getChildren().stream().anyMatch(node -> node.getClass().equals(FlowPane.class))) {
                        flowPane.getChildren().remove(
                                flowPane.getChildren().stream()
                                        .filter(node -> node.getClass().equals(FlowPane.class))
                                        .findFirst()
                                        .get()
                        );
                    }
                    if (!accuracyInput.getText().isEmpty()) {
                        try {
                            flowPane.getChildren().add(NumericalIntegrationLogics.solve(
                                    formulaInput.getValue(),
                                    minValueInput.getDouble(),
                                    maxValueInput.getDouble(),
                                    accuracyInput.getAccuracyDouble(),
                                    cbxMethods.getValue()
                            ));
                        } catch (ZeroOrNegativeAccuracyException e) {
                            e.printStackTrace();
                            Alert alert = AlertEjector.ejectError("Введены некорректные данные", e.getMessage());
                            alert.showAndWait();
                        }
                    } else {
                        flowPane.getChildren().add(NumericalIntegrationLogics.solve(
                                formulaInput.getValue(),
                                minValueInput.getDouble(),
                                maxValueInput.getDouble(),
                                cbxMethods.getValue()
                        ));
                    }
                    if (formulaInput.add()) {
                        formulaInput.save();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alert = AlertEjector.ejectError("Введены некорректные данные", e.getMessage());
                    alert.showAndWait();
                }
            });
        });
        flowPane.getChildren().

                add(calculate);
        nodes.add(flowPane);
        return nodes;
    }
}
