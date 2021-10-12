package command.commands;

import command.Command;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import math.NumericalDifferentiationLogics;
import nodes.*;
import services.AlertEjector;
import services.History;

import java.util.ArrayList;

public class NumericalDifferentiation extends Command {
    ScrollPane labScroll;

    public NumericalDifferentiation(ScrollPane labScroll) {
        super("Численное дифференцирование");
        this.labScroll = labScroll;
    }

    @Override
    public ArrayList<Node> getNodes() {
        ArrayList<Node> nodes = new ArrayList<>();
        Label header = new Label("Численное дифференцирование");
        header.setId("h1");
        FormulaInput formulaInput = new FormulaInput(new History("NumericalDifferentiation.txt"));
        formulaInput.setPromptText("y'(x,y)=");
        ValueInput<Double> aValueInput = new ValueInput<>("a");
        ValueInput<Double> bValueInput = new ValueInput<>("b");
        ValueInput<Double> endOfCalculationInput = new ValueInput<>("End");
        ValueInput<Integer> accuracyInput = new ValueInput<>("Accuracy");
        ValueInput<Double> stepInput = new ValueInput<>("Step");
        accuracyInput.setZero(true);
        stepInput.setZero(true);
        accuracyInput.setNotNegative(true);
        stepInput.setNotNegative(true);
        accuracyInput.setBounds(0, 1);
        FlowPane inputPane = new FlowPane(10, 10, formulaInput, aValueInput, bValueInput, endOfCalculationInput, accuracyInput, stepInput);
        MainButton calculate = new MainButton("Рассчитать: ");
        VBox flow = new VBox(20, header, inputPane, calculate);
        FlowPane mainPane = new FlowPane(10, 10, flow);
        mainPane.setPrefWidth(1500d);
        calculate.setOnMouseClicked(event -> Platform.runLater(() -> {
            try {
                if (mainPane.getChildren().stream().anyMatch(node -> node.getClass().equals(FlowPane.class))) {
                    mainPane.getChildren().removeIf(node -> node.getClass().equals(FlowPane.class));
                }//
                endOfCalculationInput.setMinValue(aValueInput.getDouble());
                mainPane.getChildren().add(NumericalDifferentiationLogics.solve(
                        formulaInput.getValue(),
                        aValueInput.getDouble(),
                        bValueInput.getDouble(),
                        endOfCalculationInput.getDouble(),
                        accuracyInput.getAccuracyDouble(),
                        stepInput.getDouble()
                ));
                if (formulaInput.add()) {
                    formulaInput.save();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = AlertEjector.ejectError("Введены некорректные данные", e.getMessage());
                alert.showAndWait();
            }
        }));
        mainPane.setPadding(new Insets(10d));
        nodes.add(mainPane);
        return nodes;
    }
}
