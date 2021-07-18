package math;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import math.enums.NumericalSolutionOfNonlinearEquationsMethods;
import math.rows.HalvesTableRow;
import org.apache.commons.math3.util.FastMath;

public class NumericalSolutionOfNonlinearEquationsLogics {
    public static FlowPane solve(String function, double min, double max, double accuracy, NumericalSolutionOfNonlinearEquationsMethods method) {
        ObservableList<HalvesTableRow> rows = FXCollections.observableArrayList();
        TableView<HalvesTableRow> halvesTableView = new TableView<>(rows);
        halvesTableView.getColumns().addAll(HalvesTableRow.getTableColumns());
        double a = min;
        double b = max;
        double x =(a+b)/2d;
        int n = 1;
        halvesTableView.getItems().add(new HalvesTableRow(n, a, b, x, FormulaInterpreter.calculate(function, a), FormulaInterpreter.calculate(function, b)
                , FormulaInterpreter.calculate(function, x), FastMath.abs(b-a)));
        while (FastMath.abs(a - b) >= accuracy || FastMath.abs(FormulaInterpreter.calculate(function, x)) < accuracy) {
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

        return new FlowPane(10, 10, halvesTableView);
    }
}

