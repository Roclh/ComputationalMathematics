package math.rows;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class DifferentiationTableRow {
    private SimpleIntegerProperty n = new SimpleIntegerProperty();
    private SimpleDoubleProperty xi = new SimpleDoubleProperty();
    private SimpleDoubleProperty yi = new SimpleDoubleProperty();
    private SimpleDoubleProperty fxiyi = new SimpleDoubleProperty();
    private SimpleDoubleProperty r = new SimpleDoubleProperty();

    public DifferentiationTableRow(int n, double xi, double yi, double fxiyi, double r) {
        this.n.set(n);
        this.xi.set(xi);
        this.yi.set(yi);
        this.fxiyi.set(fxiyi);
        this.r.set(r);
    }


    public double getR() {
        return r.get();
    }

    public void setR(double r) {
        this.r.set(r);
    }

    public static ArrayList<TableColumn<DifferentiationTableRow, ? extends Number>> getTableColumns(){
        ArrayList<TableColumn<DifferentiationTableRow, ? extends Number>> list = new ArrayList<>();
        TableColumn<DifferentiationTableRow, Integer> nColumn = new TableColumn<>("№");
        nColumn.setCellValueFactory(new PropertyValueFactory<>("n"));
        list.add(nColumn);
        TableColumn<DifferentiationTableRow, Double> xiColumn = new TableColumn<>("x(i)");
        xiColumn.setCellValueFactory(new PropertyValueFactory<>("xi"));
        list.add(xiColumn);
        TableColumn<DifferentiationTableRow, Double> yiColumn = new TableColumn<>("y(i)");
        yiColumn.setCellValueFactory(new PropertyValueFactory<>("yi"));
        list.add(yiColumn);
        TableColumn<DifferentiationTableRow, Double> fxiyiColumn = new TableColumn<>("f(xi,yi)");
        fxiyiColumn.setCellValueFactory(new PropertyValueFactory<>("fxiyi"));
        list.add(fxiyiColumn);
        TableColumn<DifferentiationTableRow, Double> rColumn = new TableColumn<>("R");
        rColumn.setCellValueFactory(new PropertyValueFactory<>("r"));
        list.add(rColumn);
        return list;
    }

    public int getN() {
        return n.get();
    }

    public void setN(int n) {
        this.n.set(n);
    }

    public double getXi() {
        return xi.get();
    }

    public void setXi(double xi) {
        this.xi.set(xi);
    }

    public double getYi() {
        return yi.get();
    }

    public void setYi(double yi) {
        this.yi.set(yi);
    }

    public double getFxiyi() {
        return fxiyi.get();
    }

    public void setFxiyi(double fxiyi) {
        this.fxiyi.set(fxiyi);
    }
}
