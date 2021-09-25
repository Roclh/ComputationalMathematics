package math.rows;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class IntegralTableRow {
    private SimpleIntegerProperty n = new SimpleIntegerProperty();
    private SimpleDoubleProperty x = new SimpleDoubleProperty();
    private SimpleDoubleProperty fx = new SimpleDoubleProperty();

    public IntegralTableRow(int n, double x, double fx) {
        this.n.set(n);
        this.x.set(x);
        this.fx.set(fx);
    }

    public static ArrayList<TableColumn<IntegralTableRow, ? extends Number>> getTableColumns(){
        ArrayList<TableColumn<IntegralTableRow, ? extends Number>> list = new ArrayList<>();
        TableColumn<IntegralTableRow, Integer> nColumn = new TableColumn<>("№");
        nColumn.setCellValueFactory(new PropertyValueFactory<>("n"));
        list.add(nColumn);
        TableColumn<IntegralTableRow, Double> xColumn = new TableColumn<>("x");
        xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        list.add(xColumn);
        TableColumn<IntegralTableRow, Double> fxColumn = new TableColumn<>("f(x)");
        fxColumn.setCellValueFactory(new PropertyValueFactory<>("fx"));
        list.add(fxColumn);
        return list;
    }

    public int getN() {
        return n.get();
    }

    public SimpleIntegerProperty nProperty() {
        return n;
    }

    public void setN(int n) {
        this.n.set(n);
    }

    public double getX() {
        return x.get();
    }

    public SimpleDoubleProperty xProperty() {
        return x;
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public double getFx() {
        return fx.get();
    }

    public SimpleDoubleProperty fxProperty() {
        return fx;
    }

    public void setFx(double fx) {
        this.fx.set(fx);
    }
}
