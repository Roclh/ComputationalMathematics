package math;

public class RootConditions {
    public static int countRoots(String function, double min, double max, double step){
        int count = 0;
        double next = FormulaInterpreter.calculate(function, min);
        boolean positive = next>0;
        for(double i = min+step; i<max; i+=step){
            next = FormulaInterpreter.calculateDerivative(function,i, step);
            if(next>0&&!positive){
                count++;
                positive = true;
            }else if(next<0&&positive){
                count++;
                positive = false;
            }
        }
        return count;
    }

    public static int countRoots(String function, double min, double max){
        double step = 0.01d;
        return countRoots(function, min, max, step);
    }

}
