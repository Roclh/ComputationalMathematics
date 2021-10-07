package command.commands;

import command.Command;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import math.FunctionInterpolationLogics;
import nodes.*;
import services.AlertEjector;
import services.History;

import java.util.ArrayList;

public class FunctionInterpolation extends Command {
    public FunctionInterpolation() {
        super("Интерполяция функции");
    }

    @Override
    public ArrayList<Node> getNodes() {
        ArrayList<Node> nodes = new ArrayList<>();
        Label header = new Label("Интерполяция функции");
        header.setId("h1");
        FormulaInput formulaInput = new FormulaInput(new History("FunctionInterpolation.txt"));
        ValueInput<Double> minValueInput = new ValueInput<>("Min");
        ValueInput<Double> maxValueInput = new ValueInput<>("Max");
        ValueInput<Integer> nodesKolInput = new ValueInput<>("Nodes");
        nodesKolInput.setZero(true);
        nodesKolInput.setNotNegative(true);
        nodesKolInput.setBounds(0, 30);
        FlowPane inputPane = new FlowPane(10, 10, formulaInput, minValueInput, maxValueInput, nodesKolInput);
        MainTextArea dataInput = new MainTextArea("Введите данные");
        FileInputButton fileInput = new FileInputButton("Выберите файл", dataInput);
        FlowPane fileInputPane = new FlowPane(10, 10, dataInput, fileInput);
        ValueInput<Double> interpolationValueInput = new ValueInput<>("Point");
        MainButton calculate = new MainButton("Рассчитать: ");
        VBox flow = new VBox(20, header, inputPane, fileInputPane, interpolationValueInput, calculate);
        FlowPane mainPane = new FlowPane(10, 10, flow);
        calculate.setOnMouseClicked(event -> Platform.runLater(() -> {
            try {
                if (formulaInput.getValue() != null || dataInput.getText() != null) {
                    if (mainPane.getChildren().stream().anyMatch(node -> node.getClass().equals(LineChart.class))) {
                        mainPane.getChildren().removeIf(node -> node.getClass().equals(LineChart.class));
                    }
                    if (mainPane.getChildren().stream().anyMatch(node -> node.getClass().equals(FlowPane.class))) {
                        mainPane.getChildren().removeIf(node -> node.getClass().equals(FlowPane.class));
                    }
                    if (formulaInput.getValue() != null) {
                        System.out.println("1");
                        if (!formulaInput.getValue().isEmpty() && dataInput.getText().isEmpty()) {
                            if (!formulaInput.getValue().isEmpty()) {
                                interpolationValueInput.setBounds(minValueInput.getDouble(), maxValueInput.getDouble());
                                mainPane.getChildren().add(FunctionInterpolationLogics.calculate(
                                        formulaInput.getValue(),
                                        minValueInput.getDouble(),
                                        maxValueInput.getDouble(),
                                        nodesKolInput.getInteger(),
                                        interpolationValueInput.getDouble()
                                ));
                                if (formulaInput.add()) {
                                    formulaInput.save();
                                }
                            } else {
                                throw new Exception("Способ ввода данных неопределенный, выберите либо данные, либо функцию");
                            }
                        }
                    } else if (dataInput.getText() != null) {
                        System.out.println("2");
                        if (formulaInput.getValue() != null) {
                            System.out.println("3");
                            if (formulaInput.getValue().isEmpty() && !dataInput.getText().isEmpty()) {
                                mainPane.getChildren().add(FunctionInterpolationLogics.calculate(
                                        dataInput.getText(),
                                        interpolationValueInput.getDouble()
                                ));
                            } else {
                                throw new Exception("Способ ввода данных неопределенный, выберите либо данные, либо функцию");
                            }
                        } else {
                            System.out.println("4");
                            if (!dataInput.getText().isEmpty()) {
                                mainPane.getChildren().add(FunctionInterpolationLogics.calculate(
                                        dataInput.getText(),
                                        interpolationValueInput.getDouble()
                                ));
                            } else {
                                throw new Exception("Способ ввода данных неопределенный, выберите либо данные, либо функцию");
                            }
                        }

                    }
                } else {
                    throw new Exception("Способ ввода данных неопределенный, введите функцию или данные");
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
