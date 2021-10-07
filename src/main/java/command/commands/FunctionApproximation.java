package command.commands;

import command.Command;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import math.FunctionApproximationLogics;
import nodes.FileInputButton;
import nodes.MainButton;
import nodes.MainTextArea;
import services.AlertEjector;

import java.util.ArrayList;

public class FunctionApproximation extends Command {
    public FunctionApproximation() {
        super("Аппроксимация функции");
    }

    @Override
    public ArrayList<Node> getNodes() {
        ArrayList<Node> items = new ArrayList<>();
        Label header = new Label("Аппроксимация функции");
        header.setId("h1");
        MainTextArea textInput = new MainTextArea("Выберите точки");
        FileInputButton fileButton = new FileInputButton("Ввод из файла", textInput);
        MainButton calculate = new MainButton("Рассчитать");
        VBox box = new VBox(10,header, textInput, fileButton, calculate);
        FlowPane flowPane = new FlowPane(10, 10,box);
        calculate.setOnMouseClicked(event -> Platform.runLater(()->{
            try {
                if (flowPane.getChildren().stream().anyMatch(node -> node.getClass().equals(LineChart.class))) {
                    flowPane.getChildren().removeIf(node -> node.getClass().equals(LineChart.class));
                }
                if (flowPane.getChildren().stream().anyMatch(node -> node.getClass().equals(FlowPane.class))) {
                    flowPane.getChildren().remove(
                            flowPane.getChildren().stream()
                                    .filter(node -> node.getClass().equals(FlowPane.class))
                                    .findFirst()
                                    .get()
                    );
                }
                if (textInput.getText().isEmpty()) {
                        Alert alert = AlertEjector.ejectError("Введены некорректные данные", "Повторите попытку");
                        alert.showAndWait();
                } else {
                    flowPane.getChildren().addAll(FunctionApproximationLogics.calculate(textInput.getText()));
                }

            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = AlertEjector.ejectError("Введены некорректные данные", e.getMessage());
                alert.showAndWait();
            }
        }));
        flowPane.setPadding(new Insets(20d));
        items.add(flowPane);
        return items;
    }
}
