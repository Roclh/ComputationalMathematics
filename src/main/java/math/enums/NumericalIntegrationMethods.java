package math.enums;

public enum NumericalIntegrationMethods {
    RECTANGLES("Метод прямоугольников"), TRAPEZOID("Метод трапеций"), SIMPSON("Метод симпсона");

    private String label;

    NumericalIntegrationMethods(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
