package math;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import math.enums.NumericalSolutionOfNonlinearEquationsMethods;
import math.rows.HalvesTableRow;
import org.apache.commons.math3.util.FastMath;

//-2.7x^3-1.48x^2+19.23x+6.35
// 2.74x^3-1.93x^2-15.28x-3.72


public class NumericalSolutionOfNonlinearEquationsLogics {
    public static FlowPane solve(String function, double min, double max, double accuracy, NumericalSolutionOfNonlinearEquationsMethods method) {
        FlowPane result = new FlowPane(10,10);
        switch (method){
            case HALVES:
                result.getChildren().add(solveHalves(function,min,max,accuracy));
                break;
            case SECANT:
                break;
            case ITERATION:
                break;
        }
        return result;
    }

    public static FlowPane solve(String function, double min, double max, NumericalSolutionOfNonlinearEquationsMethods method) {
        double accuracy = 0.001;
        FlowPane result = new FlowPane(10,10);
        switch (method){
            case HALVES:
                result.getChildren().add(solveHalves(function,min,max,accuracy));
                break;
            case SECANT:
                break;
            case ITERATION:
                break;
        }
        return result;
    }

    public static TableView<HalvesTableRow> solveHalves(String function, double min, double max, double accuracy){
        ObservableList<HalvesTableRow> rows = FXCollections.observableArrayList();
        TableView<HalvesTableRow> halvesTableView = new TableView<>(rows);
        halvesTableView.getColumns().addAll(HalvesTableRow.getTableColumns());
        double a = min;
        double b = max;
        double x =(a+b)/2d;
        int n = 1;
        halvesTableView.getItems().add(new HalvesTableRow(n, a, b, x, FormulaInterpreter.calculate(function, a), FormulaInterpreter.calculate(function, b)
                , FormulaInterpreter.calculate(function, x), FastMath.abs(b-a)));
        while (FastMath.abs(a - b) >= accuracy && FastMath.abs(FormulaInterpreter.calculate(function, x)) >= accuracy) {
            if (FormulaInterpreter.calculate(function, a) * FormulaInterpreter.calculate(function, x) <= 0d) {
                b = x;
            } else {
                a = x;
            }
            x = (a + b) / 2d;
            n++;
            halvesTableView.getItems().add(new HalvesTableRow(n, a, b, x, FormulaInterpreter.calculate(function, a), FormulaInterpreter.calculate(function, b)
                    , FormulaInterpreter.calculate(function, x), FastMath.abs(a-b)));

        }
        return halvesTableView;
    }
}

