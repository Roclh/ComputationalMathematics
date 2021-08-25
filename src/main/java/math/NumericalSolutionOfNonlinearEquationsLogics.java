package math;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import math.enums.NumericalSolutionOfNonlinearEquationsMethods;
import math.rows.HalvesTableRow;
import math.rows.SecantTableRow;
import org.apache.commons.math3.exception.ZeroException;
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
                result.getChildren().add(solveSecant(function,min,max,accuracy));
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
                result.getChildren().add(solveSecant(function,min,max,accuracy));
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

    public static TableView<SecantTableRow> solveSecant(String function, double min, double max, double accuracy){
        ObservableList<SecantTableRow> rows = FXCollections.observableArrayList();
        TableView<SecantTableRow> secantTableView = new TableView<>(rows);
        secantTableView.getColumns().addAll(SecantTableRow.getTableColumns());
        int n = 1;
        double x0 = min;
        double x1 = max;
        if(Math.abs(x1-x0)>accuracy){
            while(true){
                try{
                    double fx1 = FormulaInterpreter.calculate(function, x1);
                    double fx0 = FormulaInterpreter.calculate(function, x0);
                    double prev = x1;
                    x1 = x1-(x1-x0)*fx1/(fx1-fx0);
                    if(x1 == Double.POSITIVE_INFINITY || x1 == Double.NEGATIVE_INFINITY)
                        throw new ArithmeticException("Not finite");
                    secantTableView.getItems().add(new SecantTableRow(n, x0, prev, x1, fx1, Math.abs(x1-prev)));
                    n++;
                    x0=prev;
                    if(Math.abs(x1 - x0) <= accuracy)break;
                }catch (ArithmeticException e){
                    double fx1 = FormulaInterpreter.calculate(function, x1);
                    double fx0 = FormulaInterpreter.calculate(function, x0);
                    System.out.println("Mem "+n);
                    x1 = x1-(x1-x0)*fx1/(fx1-fx0)+1e-8;
                    break;
                }
            }
        }else{
            System.out.println("Невозможно вычислить");
        }
        return secantTableView;
    }
}

