package math.rows;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class SecantTableRow {
    private SimpleIntegerProperty n = new SimpleIntegerProperty();
    private SimpleDoubleProperty x0 = new SimpleDoubleProperty();
    private SimpleDoubleProperty prev = new SimpleDoubleProperty();
    private SimpleDoubleProperty x1 = new SimpleDoubleProperty();
    private SimpleDoubleProperty fx1 = new SimpleDoubleProperty();
    private SimpleDoubleProperty x1prev = new SimpleDoubleProperty();

    public SecantTableRow(int n, double x0, double prev, double x1, double fx1, double x1prev) {
        this.n.set(n);
        this.x0.set(x0);
        this.prev.set(prev);
        this.x1.set(x1);
        this.fx1.set(fx1);
        this.x1prev.set(x1prev);
    }

    public static ArrayList<TableColumn<SecantTableRow, ? extends Number>> getTableColumns(){
        ArrayList<TableColumn<SecantTableRow, ? extends Number>> list = new ArrayList<>();
        TableColumn<SecantTableRow, Integer> nColumn = new TableColumn<>("№");
        nColumn.setCellValueFactory(new PropertyValueFactory<>("n"));
        list.add(nColumn);
        TableColumn<SecantTableRow, Double> x0Column = new TableColumn<>("x0");
        x0Column.setCellValueFactory(new PropertyValueFactory<>("x0"));
        list.add(x0Column);
        TableColumn<SecantTableRow, Double> prevColumn = new TableColumn<>("Prev");
        prevColumn.setCellValueFactory(new PropertyValueFactory<>("prev"));
        list.add(prevColumn);
        TableColumn<SecantTableRow, Double> x1Column = new TableColumn<>("x1");
        x1Column.setCellValueFactory(new PropertyValueFactory<>("x1"));
        list.add(x1Column);
        TableColumn<SecantTableRow, Double> fx1Column = new TableColumn<>("f(x1)");
        fx1Column.setCellValueFactory(new PropertyValueFactory<>("fx1"));
        list.add(fx1Column);
        TableColumn<SecantTableRow, Double> x1prevColumn = new TableColumn<>("|x1-Prev|");
        x1prevColumn.setCellValueFactory(new PropertyValueFactory<>("x1prev"));
        list.add(x1prevColumn);
        return list;
    }

    public int getN() {
        return n.get();
    }

    public void setN(int n) {
        this.n.set(n);
    }

    public double getX0() {
        return x0.get();
    }

    public void setX0(double x0) {
        this.x0.set(x0);
    }

    public double getPrev() {
        return prev.get();
    }

    public void setPrev(double prev) {
        this.prev.set(prev);
    }

    public double getX1() {
        return x1.get();
    }

    public void setX1(double x1) {
        this.x1.set(x1);
    }

    public double getFx1() {
        return fx1.get();
    }

    public void setFx1(double fx1) {
        this.fx1.set(fx1);
    }

    public double getX1prev() {
        return x1prev.get();
    }

    public void setX1prev(double x1prev) {
        this.x1prev.set(x1prev);
    }
}
