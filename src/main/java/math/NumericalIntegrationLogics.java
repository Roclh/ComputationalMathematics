package math;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import math.enums.NumericalIntegrationMethods;
import math.rows.IntegralTableRow;
import nodes.ResultBoxLabel;

import java.util.ArrayList;

public class NumericalIntegrationLogics {
    private static final int limit = 100000;
    private static final double defaultAccuracy = 0.001d;

    public static FlowPane solve(String function, double min, double max, NumericalIntegrationMethods method) {
        return solve(function, min, max, defaultAccuracy, method);
    }

    public static FlowPane solve(String function, double min, double max, double accuracy, NumericalIntegrationMethods method) {
        FlowPane result = new FlowPane(10, 10);
        switch (method) {
            case RECTANGLES:
                result.getChildren().addAll(solveRectangles(function, min, max, accuracy));
                break;
            case TRAPEZOID:
                result.getChildren().addAll(solveTrapezoid(function, min, max, accuracy));
                break;
            case SIMPSON:
                result.getChildren().addAll(solveSimpson(function, min, max, accuracy));
                break;
        }
        return result;
    }

    private static ArrayList<Node> solveRectangles(String function, double min, double max, double accuracy) {
        ArrayList<Node> result = new ArrayList<>();
        ObservableList<IntegralTableRow> rows = FXCollections.observableArrayList();
        TableView<IntegralTableRow> integralTableView = new TableView<>(rows);
        integralTableView.setPrefWidth(800d);
        integralTableView.getColumns().addAll(IntegralTableRow.getTableColumns());
        int n = chooseNRectangles(function, min, max, accuracy);
        double h = (max - min) / n;
        double fLeft = 0;
        double fRight = 0;
        double fMiddle = 0;
        for (int i = 1; i <= n; i++) {
            integralTableView.getItems().add(new IntegralTableRow(i, min + i * h, FormulaInterpreter.calculate(function, min + h * i)));
            fLeft += FormulaInterpreter.calculate(function, min + h * (i - 1));
            fRight += FormulaInterpreter.calculate(function, min + h * i);
            fMiddle += FormulaInterpreter.calculate(function, min + h * (i - 0.5d));
        }
        fLeft *= h;
        fRight *= h;
        fMiddle *= h;
        double iLeft = Math.abs(RootConditions.maxValueSecondDerivative(function, min, max, accuracy) * Math.pow(max - min, 2) / (2 * n));
        double iMiddle = Math.abs(RootConditions.maxValueSecondDerivative(function, min, max, accuracy) * Math.pow(max - min, 3) / (24 * n * n));
        double iRight = Math.abs(RootConditions.maxValueSecondDerivative(function, min, max, accuracy) * Math.pow(max - min, 2) / (2 * n));
        result.add(integralTableView);
        result.add(new ResultBoxLabel("Результаты метода прямоугольников:" +
                "\r\nf'I(left): " + fLeft +
                "\r\nf'I(middle): " + fMiddle +
                "\r\nf'I(right): " + fRight +
                "\r\nf'R(n) (left): " + iLeft +
                "\r\nf'R(n) (middle): " + iMiddle +
                "\r\nf'R(n) (right): " + iRight +
                "\r\nn: " + n)
        );
        return result;
    }

    private static int chooseNRectangles(String function, double min, double max, double accuracy) {
        int n = (int) Math.sqrt(
                Math.abs(RootConditions.maxValueSecondDerivative(function, min, max, accuracy) * Math.pow(max - min, 3) / 24 / accuracy)
        );
        if (n % 2 == 1) {
            n += 1;
        } else {
            n += 2;
        }
        return Math.max(n, 4);
    }

    private static ArrayList<Node> solveTrapezoid(String function, double min, double max, double accuracy) {
        ArrayList<Node> answer = new ArrayList<>();
        ObservableList<IntegralTableRow> rows = FXCollections.observableArrayList();
        TableView<IntegralTableRow> integralTableView = new TableView<>(rows);
        integralTableView.setPrefWidth(800d);
        integralTableView.getColumns().addAll(IntegralTableRow.getTableColumns());
        int n = chooseNTrapezoid(function, min, max, accuracy);
        double result = 0;
        double h = (max - min) / n;
        for (int i = 0; i <= n; i++) {
            integralTableView.getItems().add(new IntegralTableRow(i, min + i * h, FormulaInterpreter.calculate(function, min + h * i)));
            result += (FormulaInterpreter.calculate(function, min + h * i));
        }
        result = h * ((FormulaInterpreter.calculate(function, min) + FormulaInterpreter.calculate(function, max)) / 2+result);
        double inaccuracy = Math.abs(RootConditions.maxValueSecondDerivative(function,min,max,accuracy) * Math.pow(max-min, 3) / (12 * Math.pow(n, 2)));
        answer.add(integralTableView);
        answer.add(new ResultBoxLabel("Результаты метода трапеций:" +
                "\r\nf'I: " + result +
                "\r\nf'R(n): " + inaccuracy +
                "\r\nn: " + n)
        );

        return answer;
    }

    private static int chooseNTrapezoid(String function, double min, double max, double accuracy) {
        int n = (int) Math.sqrt(
                Math.abs(RootConditions.maxValueSecondDerivative(function, min, max, accuracy) * Math.pow(max - min, 3) / 12 / accuracy)
        );
        System.out.println(RootConditions.maxValueSecondDerivative(function, min, max, accuracy));
        if (n % 2 == 1) {
            n += 1;
        } else {
            n += 2;
        }
        return Math.max(n, 4);
    }

    private static ArrayList<Node> solveSimpson(String function, double min, double max, double accuracy) {
        ArrayList<Node> answer = new ArrayList<>();
        ObservableList<IntegralTableRow> rows = FXCollections.observableArrayList();
        TableView<IntegralTableRow> integralTableView = new TableView<>(rows);
        integralTableView.setPrefWidth(800d);
        integralTableView.getColumns().addAll(IntegralTableRow.getTableColumns());
        int n = chooseNSimpson(function,min,max,accuracy);
        double result = 0;
        double h = (max-min)/n;
        double buf1= 0;
        double buf2=0;
        for(int i =0; i<=n; i++){
            if((0<i&&i<n)&& i%2==1){
                buf1+=FormulaInterpreter.calculate(function, min+h*i);
            }else if ((0<i&&i<n-1)&& i%2==0){
                buf2+=FormulaInterpreter.calculate(function, min+h*i);
            }
            integralTableView.getItems().add(new IntegralTableRow(i, min+h*i, FormulaInterpreter.calculate(function,min+h*i)));
        }
        result = h/3*(FormulaInterpreter.calculate(function,max)+FormulaInterpreter.calculate(function,min) + 4*buf1 + 2*buf2);
        double inaccuracy = Math.abs(RootConditions.maxValue(function, min, max, accuracy, 4) * Math.pow(max - min, 5) / (180 * Math.pow(n, 4)));
        answer.add(integralTableView);
        answer.add(new ResultBoxLabel("Результаты метода Симпсона:" +
                "\r\nf'I: " + result +
                "\r\nf'R(n): " + inaccuracy +
                "\r\nn: " + n)
        );
        return answer;
    }

    private static int chooseNSimpson(String function, double min, double max, double accuracy){
        int n = (int) Math.pow(
                        Math.abs(RootConditions.maxValue(function,min,max,accuracy, 4)*Math.pow(max-min, 5)/180/accuracy), 0.25d
        );
        System.out.println(RootConditions.maxValue(function,min,max,accuracy, 4)*Math.pow(max-min, 5)/180/accuracy);
        if (n % 2 == 1) {
            n += 1;
        } else {
            n += 2;
        }
        return Math.max(n, 4);
    }

}
