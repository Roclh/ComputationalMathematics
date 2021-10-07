package math;

import javafx.scene.chart.*;
import javafx.scene.input.MouseButton;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.JFXChartUtil;


public class FormulaInterpreter {
    public static double calculate(String formula, double x) throws NumberFormatException{
        Expression calc = new ExpressionBuilder(formula)
                .variables("x")
                .build()
                .setVariable("x", x);
        double result = calc.evaluate();
        if(Double.isNaN(result) || result == Double.POSITIVE_INFINITY|| result == Double.NEGATIVE_INFINITY){
            throw new NumberFormatException("При вычислении получилось невозможное значение");
        }else {
            return result;
        }

    }

    public static double calculateDerivative(String formula, double x, double accuracy, int depth){
        if(depth>1){
            return (calculateDerivative(formula,x+accuracy, accuracy, depth-1)-
                    calculateDerivative(formula,x-accuracy,accuracy,depth-1))/(2*accuracy)-calculate(formula,x)*accuracy;
        }
        return calculateDerivative(formula,x, accuracy);
    }

    public static double calculateDerivative(String formula, double x, double accuracy){
        return (calculate(formula, x+accuracy)-calculate(formula, x-accuracy))/(2*accuracy);
    }


    public static double calculateDerivative(String formula, double x) {
        double accuracy = 0.001d;
        return calculateDerivative(formula, x, accuracy);
    }

    public static double calculateSecondDerivative(String formula, double x, double accuracy) {
        return (calculate(formula, x+accuracy) - 2*calculate(formula, x)+ calculate(formula, x-accuracy)) / (accuracy*accuracy);
    }

    public static double calculateThirdDerivative(String formula, double x, double accuracy){
        return (calculate(formula, x+2*accuracy)-2*calculate(formula,x+accuracy)+2*calculate(formula,x-accuracy)-
                calculate(formula, x-2*accuracy))/(2*Math.pow(accuracy,3));
    }

    public static XYChart.Series<Number, Number> getChartData(String formula, double min, double max, double step) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("y = "+ formula);
        for (double i = min; i <= max; i += step) {
            series.getData().add(new XYChart.Data<>(i, calculate(formula, i)));
        }
        return series;
    }

    public static XYChart.Series<Number, Number> getChartData(String formula, double min, double max) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(formula);
        for (double i = min; i <= max; i += 0.01d) {
            series.getData().add(new XYChart.Data<>(i, calculate(formula, i)));
        }
        return series;
    }

    public static XYChart.Series<Number, Number> getPosChartData(String formula, double min, double max){
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("y = " + formula);
        for(double i = min; i<=max; i+=0.1d){
            if(i<=0){
                continue;
            }
            double val = calculate(formula,i);
            if(val>0){
                series.getData().add(new XYChart.Data<>(i, val));
            }
        }
        return series;
    }

    public static BarChart<String, Number> getGist(){
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("X");
        yAxis.setLabel("f(X)");
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setStyle("-fx-stroke-width: 1px;");
        return chart;
    }

    public static LineChart<Number, Number> getChart() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("X");
        yAxis.setLabel("f(X)");
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setStyle("-fx-stroke-width: 1px;");
        chart.setCreateSymbols(false);
        ChartPanManager panner = new ChartPanManager(chart);
        panner.setMouseFilter(event -> {
            if (event.getButton() == MouseButton.SECONDARY ||
                    (event.getButton() == MouseButton.PRIMARY &&
                            event.isShortcutDown())) {
                //let it through
            } else {
                event.consume();
            }
        });
        panner.start();


        JFXChartUtil.setupZooming(chart, event -> {
            if (event.getButton() != MouseButton.PRIMARY ||
                    event.isShortcutDown()) {

            } else {
                event.consume();
            }

        });

        JFXChartUtil.addDoublePrimaryClickAutoRangeHandler(chart);

        return chart;
    }
}
