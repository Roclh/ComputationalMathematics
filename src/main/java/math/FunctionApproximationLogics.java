package math;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import math.excpetions.ImpossibleMethodException;
import math.excpetions.WrongMatrixException;
import math.rows.ApproximationRow;
import nodes.ResultBoxLabel;
import services.AlertEjector;
import services.DoubleStringBuilder;
import services.Matrix;

import java.util.ArrayList;
import java.util.Arrays;

public class FunctionApproximationLogics {
    private static String[] functions = new String[5];
    private static double[] accuracy = new double[5];

    public static FlowPane calculate(String data) throws NumberFormatException {
        functions = new String[5];
        accuracy = new double[5];
        FlowPane answer = new FlowPane(10, 10);
        if (data.contains("\n")) {
            String xStringValues = data.split("\n")[0];
            String yStringValues = data.split("\n")[1];
            ArrayList<String> xStringArray = new ArrayList<String>(Arrays.asList(xStringValues.split(" ")));
            ArrayList<String> yStringArray = new ArrayList<String>(Arrays.asList(yStringValues.split(" ")));
            if (xStringArray.size() != yStringArray.size()) {
                throw new NumberFormatException("Введена матрица неверного вида, количество значений X отличается от количества значений Y");
            }
            ArrayList<Double> xArray = new ArrayList<>();
            xStringArray.forEach(s -> xArray.add(Double.parseDouble(s)));
            ArrayList<Double> yArray = new ArrayList<>();
            yStringArray.forEach(s -> yArray.add(Double.parseDouble(s)));
            try {
                answer.getChildren().add(linear(xArray, yArray));
                answer.getChildren().add(square(xArray, yArray));
                try {
                    answer.getChildren().add(exponent(xArray, yArray));
                } catch (ImpossibleMethodException e) {
                    answer.getChildren().add(new ResultBoxLabel(e.getMessage()));
                }
                try {
                    answer.getChildren().add(degree(xArray, yArray));
                } catch (ImpossibleMethodException e) {
                    answer.getChildren().add(new ResultBoxLabel(e.getMessage()));
                }
                try {
                    answer.getChildren().add(logarifm(xArray, yArray));
                } catch (ImpossibleMethodException e) {
                    answer.getChildren().add(new ResultBoxLabel(e.getMessage()));
                }
                ObservableList<ApproximationRow> rows = FXCollections.observableArrayList();
                TableView<ApproximationRow> tableView = new TableView<>(rows);
                tableView.setPrefWidth(800d);
                tableView.getColumns().addAll(ApproximationRow.getTableColumns());
                System.out.println(functions[0]);
                for(int i=0; i<xArray.size(); i++){
                    tableView.getItems().add(
                            new ApproximationRow(
                                    i,
                                    xArray.get(i),
                                    yArray.get(i),
                                    FormulaInterpreter.calculate(functions[0],xArray.get(i)),
                                    FormulaInterpreter.calculate(functions[1],xArray.get(i)),
                                    FormulaInterpreter.calculate(functions[2],xArray.get(i)),
                                    FormulaInterpreter.calculate(functions[3],xArray.get(i)),
                                    FormulaInterpreter.calculate(functions[4],xArray.get(i))
                            )
                    );
                }
                answer.getChildren().add(tableView);
                int min = 0;
                for(int i =1; i<accuracy.length; i++){
                    if(Math.abs(accuracy[min])>Math.abs(accuracy[i])){
                        min = i;
                    }
                }
                answer.getChildren().addAll(FunctionApproximationLogics.getCharts(xArray, yArray));
                ResultBoxLabel result = new ResultBoxLabel("Лучшая аппрокисмация: " + functions[min]);
                answer.getChildren().add(result);
                answer.setPrefWidth(1300d);
                if(functions[2].equals("10000")||functions[3].equals("10000")||functions[4].equals("10000")){
                    Alert alert = AlertEjector.ejectConfirmation("Внимание!", "В данном наборе значений невозможно использовать некоторые методы." +
                            " Для продолжения вычисления функции данных методов изменены на y=10000." +
                            " Данные методы отображают неверные значения");
                    alert.showAndWait();
                }

            } catch (WrongMatrixException e) {
                throw new NumberFormatException(e.getMessage());
            }
            return answer;

        } else {
            throw new NumberFormatException("В вводе недостаточное количество строчек, матрицу невозможно построить");
        }
    }

    public static ArrayList<LineChart<Number, Number>> getCharts(ArrayList<Double> xArray, ArrayList<Double> yArray){
        XYChart.Series<Number, Number> dots = new XYChart.Series<>(getList(xArray, yArray));
        dots.setName("Start values");
        ArrayList<LineChart<Number, Number>> result = new ArrayList<>();
        LineChart<Number, Number> lineChart = FormulaInterpreter.getChart();
        lineChart.setId("approximation");
        lineChart.getData().add(FormulaInterpreter.getChartData(functions[0],xArray.stream().min(Double::compareTo).get(), xArray.stream().max(Double::compareTo).get()));
        lineChart.setCreateSymbols(true);
        lineChart.getData().add(dots);
        dots = new XYChart.Series<>(getList(xArray, yArray));
        dots.setName("Start values");
        LineChart<Number, Number> squareChart = FormulaInterpreter.getChart();
        squareChart.getData().add(FormulaInterpreter.getChartData(functions[1], xArray.stream().min(Double::compareTo).get(), xArray.stream().max(Double::compareTo).get()));
        squareChart.setId("approximation");
        squareChart.setCreateSymbols(true);
        squareChart.getData().add(dots);
        dots = new XYChart.Series<>(getList(xArray, yArray));
        dots.setName("Start values");
        LineChart<Number, Number> exponentChart = FormulaInterpreter.getChart();
        exponentChart.getData().add(FormulaInterpreter.getChartData(functions[2], xArray.stream().min(Double::compareTo).get(), xArray.stream().max(Double::compareTo).get()));
        exponentChart.setId("approximation");
        exponentChart.setCreateSymbols(true);
        exponentChart.getData().add(dots);
        dots = new XYChart.Series<>(getList(xArray, yArray));
        dots.setName("Start values");
        LineChart<Number, Number> degreeChart = FormulaInterpreter.getChart();
        degreeChart.getData().add(FormulaInterpreter.getChartData(functions[3], xArray.stream().min(Double::compareTo).get(), xArray.stream().max(Double::compareTo).get()));
        degreeChart.setId("approximation");
        degreeChart.setCreateSymbols(true);
        degreeChart.getData().add(dots);
        dots = new XYChart.Series<>(getList(xArray, yArray));
        dots.setName("Start values");
        LineChart<Number, Number> logarifmChart = FormulaInterpreter.getChart();
        logarifmChart.getData().add(FormulaInterpreter.getPosChartData(functions[4], xArray.stream().min(Double::compareTo).get(), xArray.stream().max(Double::compareTo).get()));
        logarifmChart.setId("approximation");
        logarifmChart.setCreateSymbols(true);
        logarifmChart.getData().add(dots);
        result.add(lineChart);
        result.add(squareChart);
        result.add(exponentChart);
        result.add(degreeChart);
        result.add(logarifmChart);
        return result;
    }

    private static ObservableList<XYChart.Data<Number, Number>> getList(ArrayList<Double> xArray, ArrayList<Double> yArray){
        ObservableList<XYChart.Data<Number, Number>> list = FXCollections.observableArrayList();
        for(int i =0; i< xArray.size(); i++){
            list.add(new XYChart.Data<>(xArray.get(i), yArray.get(i)));
        }
        return list;
    }

    private static ResultBoxLabel linear(ArrayList<Double> xArray, ArrayList<Double> yArray) throws WrongMatrixException {
        double sx = 0;
        double sxx = 0;
        double sy = 0;
        double sxy = 0;
        for (int i = 0; i < xArray.size(); i++) {
            sx += xArray.get(i);
            sxx += xArray.get(i) * xArray.get(i);
            sy += yArray.get(i);
            sxy += xArray.get(i) * yArray.get(i);
        }
        ArrayList<Double> result = Gaussian.solve(new Matrix(new double[]{sxx, sx, sxy}, new double[]{sx, xArray.size(), sy}));
        DoubleStringBuilder answer = new DoubleStringBuilder(new StringBuilder());
        answer.append("Линейный: y = ").append(result.get(0)).append(" * x + ").append(result.get(1)).append("\r\n");
        DoubleStringBuilder func= new DoubleStringBuilder(new StringBuilder());
        functions[0] = func.append(result.get(0)).append("*x+").append(result.get(1)).toString();
        double s = 0;
        for (int i = 0; i < xArray.size(); i++) {
            s += Math.pow(result.get(0) * xArray.get(i) + result.get(1) - yArray.get(i), 2);
        }
        answer.append("Мера отклонения: ").append(s).append("\r\n");
        answer.append("Среднеквадратичное отклонение: ").append(Math.sqrt(s / xArray.size())).append("\r\n");
        double up = 0;
        double sumOfLinealFunction = 0;
        double squares = 0;
        for (int i = 0; i < xArray.size(); i++) {
            up += Math.pow(yArray.get(i) - (result.get(0) * xArray.get(i) + result.get(1)), 2);
            sumOfLinealFunction += result.get(0) * xArray.get(i) + result.get(1);
            squares += Math.pow(result.get(0) * xArray.get(i) + result.get(1), 2);
        }
        answer.append("Достоверность аппроксимации: ").append(1 - (up / (squares - Math.pow(sumOfLinealFunction, 2) / xArray.size()))).append("\r\n");

        accuracy[0] = 1 - (up / (Math.sqrt(s / xArray.size())));
        double meanX = 0;
        double meanY = 0;
        for (int i = 0; i < xArray.size(); i++) {
            meanX += xArray.get(i);
            meanY += yArray.get(i);
        }
        meanX /= xArray.size();
        meanY /= yArray.size();
        double upper = 0;
        double underX = 0;
        double underY = 0;
        for (int i = 0; i < xArray.size(); i++) {
            upper += (xArray.get(i) - meanX) * (yArray.get(i) - meanY);
            underX += Math.pow(xArray.get(i) - meanX, 2);
            underY += Math.pow(yArray.get(i) - meanY, 2);
        }
        answer.append("Коэффициент Пирсона: ").append(upper / Math.sqrt(underX * underY)).endl();
        return new ResultBoxLabel(answer.toString());
    }


    private static ResultBoxLabel square(ArrayList<Double> xArray, ArrayList<Double> yArray) throws WrongMatrixException {
        double sx = 0;
        double sxx = 0;
        double sxxx = 0;
        double sxxxx = 0;
        double sy = 0;
        double sxy = 0;
        double sxxy = 0;
        for (int i = 0; i < xArray.size(); i++) {
            sx += xArray.get(i);
            sxx += Math.pow(xArray.get(i), 2);
            sxxx += Math.pow(xArray.get(i), 3);
            sxxxx += Math.pow(xArray.get(i), 4);
            sy += yArray.get(i);
            sxy += xArray.get(i) * yArray.get(i);
            sxxy += Math.pow(xArray.get(i), 2) * yArray.get(i);
        }
        ArrayList<Double> result = Gaussian.solve(
                new Matrix(
                        new double[]{xArray.size(), sx, sxx, sy},
                        new double[]{sx, sxx, sxxx, sxy},
                        new double[]{sxx, sxxx, sxxxx, sxxy}
                ));
        DoubleStringBuilder answer = new DoubleStringBuilder(new StringBuilder());
        answer.append("Квадратный: y = ").append(result.get(0)).append(" + ").append(result.get(1)).append(" * x + ").append(result.get(2)).append(" * x ^ 2").endl();
        DoubleStringBuilder func= new DoubleStringBuilder(new StringBuilder());
        functions[1] = func.append(result.get(0)).append(" + ").append(result.get(1)).append(" * x + ").append(result.get(2)).append(" * x ^ 2").toString();
        double s = 0;
        for (int i = 0; i < xArray.size(); i++) {
            s += Math.pow(result.get(0) + result.get(1) * xArray.get(i) + result.get(2) * xArray.get(i) * xArray.get(i) - yArray.get(i), 2);
        }
        answer.append("Мера отклонения: ").append(s).endl();
        answer.append("Среднеквадратичное отклонение: ").append(Math.sqrt(s / xArray.size())).endl();
        double up = 0;
        double sumOfLinealFunction = 0;
        double squares = 0;
        for (int i = 0; i < xArray.size(); i++) {
            up += Math.pow(yArray.get(i) - (result.get(0) + result.get(1) * xArray.get(i) + result.get(2) * xArray.get(i) * xArray.get(i)), 2);
            sumOfLinealFunction += result.get(0) + result.get(1) * xArray.get(i) + result.get(2) * xArray.get(i) * xArray.get(i);
            squares += Math.pow(result.get(0) + result.get(1) * xArray.get(i) + result.get(2) * xArray.get(i) * xArray.get(i), 2);
        }
        answer.append("Достоверность аппроксимации: ").append(1 - (up / (squares - Math.pow(sumOfLinealFunction, 2) / xArray.size()))).endl();
        accuracy[1] = 1 - (up / (Math.sqrt(s / xArray.size())));
        return new ResultBoxLabel(answer.toString());
    }


    private static ResultBoxLabel exponent(ArrayList<Double> xArray, ArrayList<Double> yArray) throws ImpossibleMethodException, WrongMatrixException {
        double sx = 0;
        double sxx = 0;
        double sy = 0;
        double sxy = 0;
        for (int i = 0; i < xArray.size(); i++) {
            if (yArray.get(i) > 0) {
                sx += xArray.get(i);
                sxx += Math.pow(xArray.get(i), 2);
                sy += Math.log(yArray.get(i));
                sxy += xArray.get(i) * Math.log(yArray.get(i));
            } else {
                functions[2] = "10000";
                accuracy[2] = 10000d;
                throw new ImpossibleMethodException("Метод экспонент не может быть использован");
            }
        }
        ArrayList<Double> result = Gaussian.solve(new Matrix(
                new double[]{xArray.size(), sx, sy},
                new double[]{sx, sxx, sxy}
        ));
        DoubleStringBuilder answer = new DoubleStringBuilder(new StringBuilder());
        answer.append("Экспонент: y = ").append(Math.pow(Math.E, result.get(0))).append(" * e ^ (").append(result.get(1)).append(" * x )").endl();
        DoubleStringBuilder func= new DoubleStringBuilder(new StringBuilder());
        functions[2] = func.append(Math.pow(Math.E, result.get(0))).append(" * e ^ (").append(result.get(1)).append(" * x )").toString();
        double s = 0;
        for (int i = 0; i < xArray.size(); i++) {
            //Math.pow((Math.pow(Math.E, result.get(0) * math.e ** (answer_matrix[1] * x[i])) - y[i], 2)
            s += Math.pow((Math.pow(Math.E, result.get(0)) * Math.pow(Math.E, result.get(1) * xArray.get(i))) - yArray.get(i), 2);
        }
        answer.append("Мера отклонения: ").append(s).endl();
        answer.append("Среднеквадратичное отклонение :").append(Math.sqrt(s / xArray.size())).endl();
        double up = 0;
        double sumOfLinealFunction = 0;
        double squares = 0;
        for (int i = 0; i < xArray.size(); i++) {
            up += Math.pow(yArray.get(i) - (Math.pow(Math.E, result.get(0)) * Math.pow(Math.E, result.get(1) * xArray.get(i))), 2);
            sumOfLinealFunction += (Math.pow(Math.E, result.get(0)) * Math.pow(Math.E, result.get(1) * xArray.get(i)));
            squares += Math.pow((Math.pow(Math.E, result.get(0)) * Math.pow(Math.E, result.get(1) * xArray.get(i))), 2);
        }
        answer.append("Достоверность аппроксимации: ").append(1 - (up / (squares - Math.pow(sumOfLinealFunction, 2) / xArray.size()))).endl();
        accuracy[2] = 1 - (up / (Math.sqrt(s / xArray.size())));
        return new ResultBoxLabel(answer.toString());
    }


    private static ResultBoxLabel degree(ArrayList<Double> xArray, ArrayList<Double> yArray) throws ImpossibleMethodException, WrongMatrixException {
        double sx = 0;
        double sxx = 0;
        double sy = 0;
        double sxy = 0;
        for (int i = 0; i < xArray.size(); i++) {
            if (yArray.get(i) > 0 && xArray.get(i) > 0) {
                sx += Math.log(xArray.get(i));
                sxx += Math.pow(Math.log(xArray.get(i)), 2);
                sy += Math.log(yArray.get(i));
                sxy += Math.log(xArray.get(i)) * Math.log(yArray.get(i));
            } else {
                functions[3] = "10000";
                accuracy[3] = 10000d;
                throw new ImpossibleMethodException("Степенной метод не может быть использован");
            }
        }
        ArrayList<Double> result = Gaussian.solve(new Matrix(
                new double[]{xArray.size(), sx, sy},
                new double[]{sx, sxx, sxy}
        ));
        DoubleStringBuilder answer = new DoubleStringBuilder(new StringBuilder());
        answer.append("Степенной: y = ").append(Math.pow(Math.E, result.get(0))).append(" * x ^(").append(result.get(1)).append(")").endl();
        DoubleStringBuilder func= new DoubleStringBuilder(new StringBuilder());
        functions[3] = func.append(Math.pow(Math.E, result.get(0))).append(" * x ^(").append(result.get(1)).append(")").toString();
        double s = 0;
        for (int i = 0; i < xArray.size(); i++) {
            s += Math.pow(Math.pow(Math.E, result.get(0)) * Math.pow(xArray.get(i), result.get(1)) - yArray.get(i), 2);
        }
        answer.append("Мера отклонения: ").append(s).endl();
        answer.append("Среднеквадратичное отклонение :").append(Math.sqrt(s / xArray.size())).endl();
        double up = 0;
        double sumOfLinealFunction = 0;
        double squares = 0;
        for (int i = 0; i < xArray.size(); i++) {
            up += Math.pow(yArray.get(i) - Math.pow(Math.E, result.get(0)) * Math.pow(xArray.get(i), result.get(1)), 2);
            sumOfLinealFunction += (Math.pow(Math.E, result.get(0)) * Math.pow(xArray.get(i), result.get(1)));
            squares += Math.pow(Math.pow(Math.E, result.get(0)) * Math.pow(xArray.get(i), result.get(1)), 2);
        }
        answer.append("Достоверность аппроксимации: ").append(1 - (up / (squares - Math.pow(sumOfLinealFunction, 2) / xArray.size()))).endl();
        accuracy[3] = 1 - (up / (Math.sqrt(s / xArray.size())));
        return new ResultBoxLabel(answer.toString());
    }


    private static ResultBoxLabel logarifm(ArrayList<Double> xArray, ArrayList<Double> yArray) throws ImpossibleMethodException, WrongMatrixException {
        double sx = 0;
        double sxx = 0;
        double sy = 0;
        double sxy = 0;
        for (int i = 0; i < xArray.size(); i++) {
            if (xArray.get(i) > 0) {
                sx += Math.log(xArray.get(i));
                sxx += Math.pow(Math.log(xArray.get(i)), 2);
                sy += yArray.get(i);
                sxy += Math.log(xArray.get(i)) * yArray.get(i);
            } else {
                functions[4] = "10000";
                accuracy[4] = 10000d;
                throw new ImpossibleMethodException("Логарифмический метод не может быть использован");
            }
        }
        ArrayList<Double> result = Gaussian.solve(new Matrix(
                new double[]{xArray.size(), sx, sy},
                new double[]{sx, sxx, sxy}
        ));
        DoubleStringBuilder answer = new DoubleStringBuilder(new StringBuilder());
        answer.append("Логарифм: y = ").append(result.get(1)).append(" * log(x) + ").append(result.get(0)).endl();
        DoubleStringBuilder func= new DoubleStringBuilder(new StringBuilder());
        functions[4] = func.append(result.get(1)).append(" * log(x) + ").append(result.get(0)).toString();
        double s = 0;
        for (int i = 0; i < xArray.size(); i++) {
            //Math.pow((Math.pow(Math.E, result.get(0) * math.e ** (answer_matrix[1] * x[i])) - y[i], 2)
            s += Math.pow(result.get(1) * Math.log(xArray.get(i)) + result.get(0) - yArray.get(i), 2);
        }
        answer.append("Мера отклонения: ").append(s).endl();
        answer.append("Среднеквадратичное отклонение :").append(Math.sqrt(s / xArray.size())).endl();
        double up = 0;
        double sumOfLinealFunction = 0;
        double squares = 0;
        for (int i = 0; i < xArray.size(); i++) {
            up += Math.pow(yArray.get(i) - (result.get(1) * Math.log(xArray.get(i)) + result.get(0)), 2);
            sumOfLinealFunction += (result.get(1) * Math.log(xArray.get(i)) + result.get(0));
            squares += Math.pow(result.get(1) * Math.log(xArray.get(i)) + result.get(0), 2);
        }
        answer.append("Достоверность аппроксимации: ").append(1 - (up / (squares - Math.pow(sumOfLinealFunction, 2) / xArray.size()))).endl();
        accuracy[4] = 1 - (up / (Math.sqrt(s / xArray.size())));
        return new ResultBoxLabel(answer.toString());
    }
}
