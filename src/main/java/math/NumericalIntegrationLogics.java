package math;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import math.enums.NumericalIntegrationMethods;
import math.rows.HalvesTableRow;
import math.rows.IntegralTableRow;
import nodes.ResultBoxLabel;

import java.util.ArrayList;

public class NumericalIntegrationLogics {
    private static final int limit = 100000;
    private static final double defaultAccuracy = 0.001d;

    public static FlowPane solve(String function, double min, double max, NumericalIntegrationMethods method){
        return solve(function,min,max,defaultAccuracy, method);
    }

    public static FlowPane solve(String function, double min, double max, double accuracy, NumericalIntegrationMethods method) {
        FlowPane result = new FlowPane(10, 10);
        switch (method) {
            case RECTANGLES:
                result.getChildren().addAll(solveRectangles(function,min,max,accuracy));
                break;
            case TRAPEZOID:
                result.getChildren().addAll(solveTrapezoid(function,min,max,accuracy));
                break;
            case SIMPSON:
                result.getChildren().addAll(solveSimpson(function,min,max,accuracy));
                break;
        }
        return result;
    }

    private static ArrayList<Node> solveRectangles(String function, double min, double max, double accuracy){
        ArrayList<Node> result = new ArrayList<>();
        ObservableList<IntegralTableRow> rows = FXCollections.observableArrayList();
        TableView<IntegralTableRow> integralTableView = new TableView<>(rows);
        integralTableView.setPrefWidth(800d);
        integralTableView.getColumns().addAll(IntegralTableRow.getTableColumns());
        double averageX = 0;
        int n = chooseNRectangles(function, min,max, accuracy);
        double h = (max-min)/n;
        double fLeft = 0;
        double fRight=0;
        double fMiddle=0;
        for(int i = 1; i<n; i++) {
            integralTableView.getItems().add(new IntegralTableRow(i,min+i*h, FormulaInterpreter.calculate(function,min+h*i)));
            fLeft += FormulaInterpreter.calculate(function, min+h * (i - 1));
            fRight += FormulaInterpreter.calculate(function, min+h * i);
            fMiddle += FormulaInterpreter.calculate(function, min+h * (i - 0.5d));
        }
        result.add(integralTableView);
        result.add(new ResultBoxLabel("Результаты метода прямоугольников:"+
                "\r\nf'I(left): "+h*fLeft+
                "\r\nf'I(middle): "+h*fMiddle+
                "\r\nf'I(right): "+h*fRight+
                "\r\nn: "+n)
        );

        return result;
    }

    private static int chooseNRectangles(String function,double min, double max, double accuracy){
        int n = (int)Math.abs(
                Math.sqrt(RootConditions.maxValueSecondDerivative(function, min, max, accuracy)*Math.pow(max-min, 3)/24/accuracy)
        );
        if(n%2==1){
            n+=1;
        }else{
            n+=2;
        }
        return Math.max(n, 4);
    }

    private static ArrayList<Node> solveTrapezoid(String function, double min, double max, double accuracy){
        ArrayList<Node> answer = new ArrayList<>();
        return answer;
    }
    private static ArrayList<Node> solveSimpson(String function, double min, double max, double accuracy){
        ArrayList<Node> answer = new ArrayList<>();
        return answer;
    }

}
