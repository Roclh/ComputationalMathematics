package math;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import math.rows.XYRow;
import nodes.ResultBoxLabel;
import services.DoubleStringBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class FunctionInterpolationLogics {
    public static FlowPane calculate(String formula, double min, double max, int kol, double num){
        FlowPane answer = new FlowPane(10, 10);
        double h = (max-min)/(kol-1);
        ArrayList<Double> xArray = new ArrayList<>();
        ArrayList<Double> yArray = new ArrayList<>();
        for(double i = min; i<=max; i+=h){
            try{
                yArray.add(FormulaInterpreter.calculate(formula, i));
                xArray.add(i);
            }catch (NumberFormatException ignored){
            }
        }
        return calculate(xArray, yArray, num);
    }

    public static FlowPane calculate(String data, double num) throws NumberFormatException{
        FlowPane answer = new FlowPane(10, 10);
        if (data.contains("\n")) {
            String xStringValues = data.split("\n")[0];
            String yStringValues = data.split("\n")[1];
            ArrayList<String> xStringArray = new ArrayList<>(Arrays.asList(xStringValues.split(" ")));
            ArrayList<String> yStringArray = new ArrayList<>(Arrays.asList(yStringValues.split(" ")));
            if (xStringArray.size() != yStringArray.size()) {
                throw new NumberFormatException("Введена матрица неверного вида, количество значений X отличается от количества значений Y");
            }
            ArrayList<Double> xArray = new ArrayList<>();
            xStringArray.forEach(s -> xArray.add(Double.parseDouble(s)));
            ArrayList<Double> yArray = new ArrayList<>();
            yStringArray.forEach(s -> yArray.add(Double.parseDouble(s)));
            return calculate(xArray, yArray, num);
        }else{
            throw new NumberFormatException("Введена матрица неверного вида");
        }
    }

    private static LineChart<Number, Number> getLagrangeChart(ArrayList<Double> xValues,ArrayList<Double> yValues, double num){
        XYChart.Series<Number, Number> function = new XYChart.Series<>();
        for(double i = xValues.stream().min(Double::compareTo).get()-0.01d; i<= xValues.stream().max(Double::compareTo).get()+0.01d; i+=0.01d){
            function.getData().add(new XYChart.Data<>(i, lagrange(xValues, yValues, i)));
        }
        function.setName("Lagrange Interpolation");
        XYChart.Series<Number, Number> points = new XYChart.Series<>();
        for(int i =0; i<xValues.size(); i++){
            points.getData().add(new XYChart.Data<>(xValues.get(i), yValues.get(i)));
        }
        points.setName("Data");
        XYChart.Series<Number, Number> result = new XYChart.Series<>();
        result.getData().add(new XYChart.Data<>(num, lagrange(xValues,yValues,num)));
        result.setName("Result");
        LineChart<Number, Number> chart = FormulaInterpreter.getChart();
        chart.setId("interpolation");
        chart.setCreateSymbols(true);
        chart.getData().add(function);
        chart.getData().add(points);
        chart.getData().add(result);
        return chart;
    }

    private static LineChart<Number, Number> getNewtonChart(ArrayList<Double> xValues,ArrayList<Double> yValues, double num){
        XYChart.Series<Number, Number> function = new XYChart.Series<>();
        for(double i = xValues.stream().min(Double::compareTo).get()-0.01d; i<= xValues.stream().max(Double::compareTo).get()+0.01d; i+=0.01d){
            function.getData().add(new XYChart.Data<>(i, newton(xValues, yValues, i)));
        }
        function.setName("Newton Interpolation");
        XYChart.Series<Number, Number> points = new XYChart.Series<>();
        for(int i =0; i<xValues.size(); i++){
            points.getData().add(new XYChart.Data<>(xValues.get(i), yValues.get(i)));
        }
        points.setName("Data");
        XYChart.Series<Number, Number> result = new XYChart.Series<>();
        result.getData().add(new XYChart.Data<>(num, newton(xValues,yValues,num)));
        result.setName("Result");
        LineChart<Number, Number> chart = FormulaInterpreter.getChart();
        chart.setId("interpolation");
        chart.setCreateSymbols(true);
        chart.getData().add(function);
        chart.getData().add(points);
        chart.getData().add(result);
        return chart;
    }


    private static FlowPane calculate(ArrayList<Double> xValues,ArrayList<Double> yValues, double num){
        FlowPane answer = new FlowPane(10,10);
        ObservableList<XYRow> list = FXCollections.observableArrayList();
        TableView<XYRow> tableView = new TableView<>(list);
        tableView.getColumns().addAll(XYRow.getTableColumns());
        for (int i =0; i<xValues.size(); i++) {
            tableView.getItems().add(new XYRow(i,xValues.get(i),yValues.get(i)));
        }
        DoubleStringBuilder ans = new DoubleStringBuilder(new StringBuilder());
        ans.append("Лагранж: ").append(lagrange(xValues,yValues,num)).endl();
        ans.append("Ньютон: ").append(newton(xValues,yValues,num)).endl();
        ResultBoxLabel result = new ResultBoxLabel(ans.toString());
        answer.getChildren().addAll(tableView, result, getLagrangeChart(xValues,yValues,num), getNewtonChart(xValues,yValues,num));
        return answer;
    }

    private static double lagrange(ArrayList<Double> xValues, ArrayList<Double> yValues, double num){
        double lag = 0;
        for(int i =0; i<yValues.size(); i++){
            double multiplying = 1;
            for(int j =0; j<xValues.size(); j++){
                if(i!=j) {
                    multiplying *= (num - xValues.get(j)) / (xValues.get(i) - xValues.get(j));
                }
            }
            lag += yValues.get(i)*multiplying;
        }
        return lag;
    }

    private static double newton(ArrayList<Double> xValues, ArrayList<Double> yValues, double num){
        ArrayList<Double> Y = new ArrayList<>(yValues);
        ArrayList<Double> X = new ArrayList<>(xValues);
        //Ньютон
        for(int i =1; i<xValues.size();i++){
            for(int j=i; j<xValues.size(); j++){
                Y.set(j, (Y.get(j)-Y.get(i-1))/(X.get(j)-X.get(i-1)));
            }
        }
        int n = Y.size()-1;
        double degree = Y.get(n);
        for(int i =1; i<n+1;i++){
            degree = Y.get(n-i)+(num - X.get(n-i))*degree;
        }
        return degree;
    }

}
