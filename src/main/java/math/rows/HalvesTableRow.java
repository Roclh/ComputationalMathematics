package math.rows;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class HalvesTableRow {
    private SimpleIntegerProperty n = new SimpleIntegerProperty();
    private SimpleDoubleProperty a = new SimpleDoubleProperty();
    private SimpleDoubleProperty b = new SimpleDoubleProperty();
    private SimpleDoubleProperty x = new SimpleDoubleProperty();
    private SimpleDoubleProperty fa = new SimpleDoubleProperty();
    private SimpleDoubleProperty fb = new SimpleDoubleProperty();
    private SimpleDoubleProperty fx = new SimpleDoubleProperty();
    private SimpleDoubleProperty ab = new SimpleDoubleProperty();

    public HalvesTableRow(int n, double a, double b, double x, double fa, double fb, double fx, double aMinusB) {
        this.n.set(n);
        this.a.set(a);
        this.b.set(b);
        this.x.set(x);
        this.fa.set(fa);
        this.fb.set(fb);
        this.fx.set(fx);
        this.ab.set(aMinusB);
    }

    public static ArrayList<TableColumn<HalvesTableRow, ? extends Number>> getTableColumns(){
        ArrayList<TableColumn<HalvesTableRow, ? extends Number>> list = new ArrayList<>();
        TableColumn<HalvesTableRow, Integer> nColumn = new TableColumn<>("№");
        nColumn.setCellValueFactory(new PropertyValueFactory<>("n"));
        list.add(nColumn);
        TableColumn<HalvesTableRow, Double> aColumn = new TableColumn<>("a");
        aColumn.setCellValueFactory(new PropertyValueFactory<>("a"));
        list.add(aColumn);
        TableColumn<HalvesTableRow, Double> bColumn = new TableColumn<>("b");
        bColumn.setCellValueFactory(new PropertyValueFactory<>("b"));
        list.add(bColumn);
        TableColumn<HalvesTableRow, Double> xColumn = new TableColumn<>("x");
        xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        list.add(xColumn);
        TableColumn<HalvesTableRow, Double> faColumn = new TableColumn<>("f(a)");
        faColumn.setCellValueFactory(new PropertyValueFactory<>("fa"));
        list.add(faColumn);
        TableColumn<HalvesTableRow, Double> fbColumn = new TableColumn<>("f(b)");
        fbColumn.setCellValueFactory(new PropertyValueFactory<>("fb"));
        list.add(fbColumn);
        TableColumn<HalvesTableRow, Double> fxColumn = new TableColumn<>("f(x)");
        fxColumn.setCellValueFactory(new PropertyValueFactory<>("fx"));
        list.add(fxColumn);
        TableColumn<HalvesTableRow, Double> aMinusBColumn = new TableColumn<>("|a-b|");
        aMinusBColumn.setCellValueFactory(new PropertyValueFactory<>("ab"));
        list.add(aMinusBColumn);
        return list;
    }

    public int getN() {
        return n.get();
    }

    public void setN(int n) {
        this.n.set(n);
    }

    public double getA() {
        return a.get();
    }

    public void setA(double a) {
        this.a.set(a);
    }

    public double getB() {
        return b.get();
    }

    public void setB(double b) {
        this.b.set(b);
    }

    public double getX() {
        return x.get();
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public double getFa() {
        return fa.get();
    }

    public void setFa(double fa) {
        this.fa.set(fa);
    }

    public double getFb() {
        return fb.get();
    }

    public void setFb(double fb) {
        this.fb.set(fb);
    }

    public double getFx() {
        return fx.get();
    }

    public void setFx(double fx) {
        this.fx.set(fx);
    }

    public double getaMinusB() {
        return ab.get();
    }

    public void setaMinusB(double aMinusB) {
        this.ab.set(aMinusB);
    }
}
