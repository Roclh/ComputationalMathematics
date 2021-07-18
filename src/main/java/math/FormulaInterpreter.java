package math;


import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseButton;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.JFXChartUtil;

public class FormulaInterpreter {
    public static double calculate(String formula, double x){
        Expression calc = new ExpressionBuilder(formula)
                .variables("x")
                .build()
                .setVariable("x", x);
        return calc.evaluate();
    }

    public static double calculateDerivative(String formula, double x, double accuracy){
        Expression fx = new ExpressionBuilder(formula)
                .variables("x")
                .build();
        return (fx.setVariable("x", x+accuracy).evaluate()-fx.setVariable("x", x).evaluate())/(accuracy);
    }

    public static double calculateDerivative(String formula, double x){
        Expression fx = new ExpressionBuilder(formula)
                .variables("x")
                .build();
        return (fx.setVariable("x", x+0.001d).evaluate()-fx.setVariable("x", x).evaluate())/(0.001d);
    }


    public static XYChart.Series<Number, Number> getChartData(String formula, double min, double max, double step){
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(formula);
        for(double i = min; i<=max; i+=step){
            series.getData().add(new XYChart.Data<>(i, calculate(formula, i)));
        }
        return series;
    }

    public static LineChart<Number, Number> getChart(){
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("X");
        yAxis.setLabel("f(X)");
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setStyle("-fx-stroke-width: 1px;");
        chart.setCreateSymbols(false);
        ChartPanManager panner = new ChartPanManager( chart );
        panner.setMouseFilter( event -> {
            if ( event.getButton() == MouseButton.SECONDARY ||
                    ( event.getButton() == MouseButton.PRIMARY &&
                            event.isShortcutDown() ) ) {
                //let it through
            } else {
                event.consume();
            }
        } );
        panner.start();



        JFXChartUtil.setupZooming( chart, event->{
            if ( event.getButton() != MouseButton.PRIMARY ||
                    event.isShortcutDown() ){

            } else{
                event.consume();
            }

        } );

        JFXChartUtil.addDoublePrimaryClickAutoRangeHandler( chart );

        return chart;
    }
}
