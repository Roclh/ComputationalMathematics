package math.enums;

public enum NumericalSolutionOfNonlinearEquationsMethods {
    HALVES("Метод половинного деления"),SECANT("Метод секущих"),ITERATION("Итерационный метод");


    private String label;

    NumericalSolutionOfNonlinearEquationsMethods(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
