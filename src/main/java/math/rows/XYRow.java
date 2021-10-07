package math.rows;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class XYRow {
    private SimpleIntegerProperty n = new SimpleIntegerProperty();
    private SimpleDoubleProperty x = new SimpleDoubleProperty();
    private SimpleDoubleProperty y = new SimpleDoubleProperty();

    public XYRow(int n, double x, double y) {
        this.n.set(n);
        this.x.set(x);
        this.y.set(y);
    }

    public static ArrayList<TableColumn<XYRow, ? extends Number>> getTableColumns(){
        ArrayList<TableColumn<XYRow, ? extends Number>> list = new ArrayList<>();
        TableColumn<XYRow, Integer> nColumn = new TableColumn<>("№");
        nColumn.setCellValueFactory(new PropertyValueFactory<>("n"));
        list.add(nColumn);
        TableColumn<XYRow, Double> xColumn = new TableColumn<>("x");
        xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        list.add(xColumn);
        TableColumn<XYRow, Double> yColumn = new TableColumn<>("y");
        yColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
        list.add(yColumn);
        return list;
    }

    public int getN() {
        return n.get();
    }

    public void setN(int n) {
        this.n.set(n);
    }

    public double getX() {
        return x.get();
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public double getY() {
        return y.get();
    }

    public void setY(double y) {
        this.y.set(y);
    }
}
