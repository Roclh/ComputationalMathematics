package math;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import math.rows.ApproximationRow;
import math.rows.DifferentiationTableRow;
import nodes.ResultBoxLabel;
import org.gillius.jfxutils.chart.JFXChartUtil;

import java.util.ArrayList;

public class NumericalDifferentiationLogics {
    public static FlowPane solve(String function, double x0, double y0,double end, double accuracy, double step){
        FlowPane result = new FlowPane(10,10);
        result.setPrefWidth(1500d);
        result.getChildren().addAll(rungeKutta(function,x0,y0,end,accuracy,step));
        if((end-x0)/step>4){
            ArrayList<Node> list = adams(function,x0,y0,end,accuracy,step);
            if(result.getChildren().stream().anyMatch(node -> node.getClass().equals(LineChart.class))){
                LineChart<Number,Number> chartRunge = (LineChart<Number,Number>)result.getChildren().stream().filter(node -> node.getClass().equals(LineChart.class)).findAny().get();
                LineChart<Number,Number> chartAdams = (LineChart<Number,Number>)list.stream().filter(node -> node.getClass().equals(LineChart.class)).findAny().get();
                FormulaInterpreter.populateChart(chartRunge, chartAdams.getData().get(0));
                JFXChartUtil.setupZooming( chartRunge, mouseEvent -> {
                    if ( mouseEvent.getButton() != MouseButton.PRIMARY ||
                            mouseEvent.isShortcutDown() )
                        mouseEvent.consume();
                });
                list.removeIf(node -> node.getClass().equals(LineChart.class));
            }
            result.getChildren().addAll(list);
        }else{
            result.getChildren().add(new ResultBoxLabel("Метод Адамса использовать невозможно"));
        }
        return result;
    }
    
    public static ArrayList<Node> rungeKutta(String function, double x0, double y0,double end, double accuracy, double step){
        ArrayList<Node> ans = new ArrayList<>();
        ObservableList<DifferentiationTableRow> rows = FXCollections.observableArrayList();
        javafx.scene.control.TableView<DifferentiationTableRow> tableView = new TableView<>(rows);
        tableView.setPrefWidth(500d);
        tableView.getColumns().addAll(DifferentiationTableRow.getTableColumns());
        int n = (int)Math.floor((end-x0)/step);
        ArrayList<Double> y = new ArrayList<>();
        y.add(y0);
        for(int i = 0; i<n; i++){
            double xi = x0+step*i;
            y.add(findRungeSolution(function,xi, y.get(i),step));
        }
        ArrayList<Double> y2=new ArrayList<>();
        step/=2;
        n = (int)Math.floor((end-x0)/step);
        if(n%2==0){
            n++;
        }
        y2.add(y0);
        for(int i =0; i<n;i++){
            double xi = x0+step*i;
            y2.add(findRungeSolution(function,xi, y2.get(i),step));
        }
        for(int i =0; i<y.size(); i++){
            double xi = x0+2*step*i;
            tableView.getItems().add(new DifferentiationTableRow(i, xi, y.get(i), FormulaInterpreter.calculate(function, xi, y.get(i)),(y.get(i)-y2.get(i*2))/(2*accuracy-1)));
        }
        VBox table = new VBox(20, new ResultBoxLabel("Метод Рунге-Кутта"), tableView);
        ans.add(table);
        ans.add(FormulaInterpreter.buildGraph(generateX(x0, end, step*2),y, "differentiation", "Метод Рунге-Кутта"));
        return ans;
    }

    public static ArrayList<Node> adams(String function, double x0, double y0,double end, double accuracy, double step){
        ArrayList<Node> ans = new ArrayList<>();
        ObservableList<DifferentiationTableRow> rows = FXCollections.observableArrayList();
        javafx.scene.control.TableView<DifferentiationTableRow> tableView = new TableView<>(rows);
        tableView.setPrefWidth(500d);
        tableView.getColumns().addAll(DifferentiationTableRow.getTableColumns());
        int n = (int)Math.floor((end-x0)/step);
        ArrayList<Double> y = new ArrayList<>();
        y.add(y0);
        y.add(findRungeSolution(function, x0+step*0, y.get(0), step));
        y.add(findRungeSolution(function, x0+step*1, y.get(1), step));
        y.add(findRungeSolution(function, x0+step*2, y.get(2), step));
        double fi = FormulaInterpreter.calculate(function, x0+step*3, y.get(3));
        double fi1 = FormulaInterpreter.calculate(function, x0+step*2, y.get(2));
        double fi2 = FormulaInterpreter.calculate(function, x0+step*1, y.get(1));
        double fi3 = FormulaInterpreter.calculate(function, x0+step*0, y.get(0));
        for(int i =4; i<=n; i++){
            y.add(findAdamsSolution(function, fi3, fi2, fi1, fi, y.get(i-1), step));
            fi3=fi2;
            fi2=fi1;
            fi1=fi;
            fi=FormulaInterpreter.calculate(function, x0+step*i, y.get(i));
        }
        ArrayList<Double> y2=new ArrayList<>();
        step/=2;
        n = (int)Math.floor((end-x0)/step);
        if(n%2==0){
            n++;
        }
        y2.add(y0);
        y2.add(findRungeSolution(function, x0+step*0, y2.get(0), step));
        y2.add(findRungeSolution(function, x0+step*1, y2.get(1), step));
        y2.add(findRungeSolution(function, x0+step*2, y2.get(2), step));
        fi = FormulaInterpreter.calculate(function, x0+step*3, y2.get(3));
        fi1 = FormulaInterpreter.calculate(function, x0+step*2, y2.get(2));
        fi2 = FormulaInterpreter.calculate(function, x0+step*1, y2.get(1));
        fi3 = FormulaInterpreter.calculate(function, x0+step*0, y2.get(0));
        for(int i =4; i<=n;i++){
            y2.add(findAdamsSolution(function, fi3, fi2, fi1, fi, y2.get(i-1), step));
            fi3=fi2;
            fi2=fi1;
            fi1=fi;
            fi=FormulaInterpreter.calculate(function, x0+step*i, y2.get(i));
        }
        for(int i =0; i<y.size(); i++){
            double xi = x0+2*step*i;
            tableView.getItems().add(new DifferentiationTableRow(i, xi, y.get(i), FormulaInterpreter.calculate(function, xi, y.get(i)),(y.get(i)-y2.get(i*2))/(2*accuracy-1)));
        }
        VBox table = new VBox(20, new ResultBoxLabel("Метод Адамса"), tableView);
        ans.add(table);
        ans.add(FormulaInterpreter.buildGraph(generateX(x0, end, step*2),y, "differentiation", "Метод Адамса"));
        return ans;

    }

    public static double findRungeSolution(String function, double x, double y, double h){
        double k_1 = h * FormulaInterpreter.calculate(function,x, y);
        double k_2 = h * FormulaInterpreter.calculate(function,x + h / 2, y + k_1 / 2);
        double k_3 = h * FormulaInterpreter.calculate(function,x + h / 2, y + k_2 / 2);
        double k_4 = h * FormulaInterpreter.calculate(function,x + h, y + k_3);
        return y + (k_1 + 2 * k_2 + 2 * k_3 + k_4) / 6;
    }

    public static double findAdamsSolution(String funciton, double f3, double f2, double f1, double f0,double yprev, double h){
        double dfi = f0-f1;
        double d2fi = f0 - 2 *f1 + f2;
        double d3fi = f0 - 3 *f1 + 3*f2 - f3;
        return yprev + h *f0 + Math.pow(h,2)/2*dfi + 5*Math.pow(h,3)/12*d2fi + 3*Math.pow(h,4)/8*d3fi;
    }

    private static ArrayList<Double> generateX(double min, double max, double step){
        ArrayList<Double> list = new ArrayList<>();
        int n = (int)Math.floor((max-min)/step);
        for(int i=0; i<=n; i++){
            list.add(min+i*step);
        }
        return list;
    }
}
