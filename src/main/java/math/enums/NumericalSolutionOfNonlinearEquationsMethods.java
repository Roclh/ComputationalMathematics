package math.enums;

public enum NumericalSolutionOfNonlinearEquationsMethods {
    HALVES,SECANT,ITERATION;


    @Override
    public String toString() {
        switch (this){
            case HALVES:
                return "Метод половинного деления";
            case SECANT:
                return "Метод секущих";
            case ITERATION:
                return "Метод простых итераций";
        }
        return null;
    }
}
