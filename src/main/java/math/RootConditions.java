package math;

import math.excpetions.SecantMethodException;

public class RootConditions {

    public static double maxValueSecondDerivative(String function, double min, double max){
        double step = 0.01d;
        return maxValueSecondDerivative(function, min, max, step);
    }

    public static double maxValueSecondDerivative(String function, double min, double max, double step){
        double maxRoot = 0;
        for(double i = min; i< max; i+=step){
            double root = FormulaInterpreter.calculateSecondDerivative(function, i, step);
            if(root>maxRoot){
                maxRoot = root;
            }
        }
        return maxRoot;
    }

    public static int countRoots(String function, double min, double max) {
        double step = 0.01d;
        return countRoots(function, min, max, step);
    }
    public static int countRoots(String function, double min, double max, double step) {
        int count = 0;
        double next = FormulaInterpreter.calculate(function, min);
        boolean positive = next > 0;
        for (double i = min + step; i < max; i += step) {
            next = FormulaInterpreter.calculateDerivative(function, i, step);
            if (next > 0 && !positive) {
                count++;
                positive = true;
            } else if (next < 0 && positive) {
                count++;
                positive = false;
            }
        }
        return count;
    }


    public static void checkSecantMethod(String function, double min, double max) throws SecantMethodException {
        if (FormulaInterpreter.calculate(function, min) == FormulaInterpreter.calculate(function, max)) {
            throw new SecantMethodException("Условие сходимости не выполнено");
        }

    }

}
