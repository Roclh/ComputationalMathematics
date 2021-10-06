package math.rows;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class ApproximationRow {
    private SimpleIntegerProperty n = new SimpleIntegerProperty();
    private SimpleDoubleProperty x = new SimpleDoubleProperty();
    private SimpleDoubleProperty fx = new SimpleDoubleProperty();
    private SimpleDoubleProperty lin = new SimpleDoubleProperty();
    private SimpleDoubleProperty sqr = new SimpleDoubleProperty();
    private SimpleDoubleProperty exp = new SimpleDoubleProperty();
    private SimpleDoubleProperty deg = new SimpleDoubleProperty();
    private SimpleDoubleProperty log = new SimpleDoubleProperty();

    public ApproximationRow(int n, double x, double fx, double lin, double sqr, double exp, double deg, double log) {
        this.n.set(n);
        this.x.set(x);
        this.fx.set(fx);
        this.lin.set(lin);
        this.sqr.set(sqr);
        this.exp.set(exp);
        this.deg.set(deg);
        this.log.set(log);
    }

    public static ArrayList<TableColumn<ApproximationRow, ? extends Number>> getTableColumns(){
        ArrayList<TableColumn<ApproximationRow, ? extends Number>> list = new ArrayList<>();
        TableColumn<ApproximationRow, Integer> nColumn = new TableColumn<>("№");
        nColumn.setCellValueFactory(new PropertyValueFactory<>("n"));
        list.add(nColumn);
        TableColumn<ApproximationRow, Double> aColumn = new TableColumn<>("x");
        aColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        list.add(aColumn);
        TableColumn<ApproximationRow, Double> bColumn = new TableColumn<>("f(x)");
        bColumn.setCellValueFactory(new PropertyValueFactory<>("fx"));
        list.add(bColumn);
        TableColumn<ApproximationRow, Double> xColumn = new TableColumn<>("LIN");
        xColumn.setCellValueFactory(new PropertyValueFactory<>("lin"));
        list.add(xColumn);
        TableColumn<ApproximationRow, Double> faColumn = new TableColumn<>("SQR");
        faColumn.setCellValueFactory(new PropertyValueFactory<>("sqr"));
        list.add(faColumn);
        TableColumn<ApproximationRow, Double> fbColumn = new TableColumn<>("EXP");
        fbColumn.setCellValueFactory(new PropertyValueFactory<>("exp"));
        list.add(fbColumn);
        TableColumn<ApproximationRow, Double> fxColumn = new TableColumn<>("DEG");
        fxColumn.setCellValueFactory(new PropertyValueFactory<>("deg"));
        list.add(fxColumn);
        TableColumn<ApproximationRow, Double> aMinusBColumn = new TableColumn<>("LOG");
        aMinusBColumn.setCellValueFactory(new PropertyValueFactory<>("log"));
        list.add(aMinusBColumn);
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

    public double getFx() {
        return fx.get();
    }


    public void setFx(double fx) {
        this.fx.set(fx);
    }

    public double getLin() {
        return lin.get();
    }


    public void setLin(double lin) {
        this.lin.set(lin);
    }

    public double getSqr() {
        return sqr.get();
    }


    public void setSqr(double sqr) {
        this.sqr.set(sqr);
    }

    public double getExp() {
        return exp.get();
    }


    public void setExp(double exp) {
        this.exp.set(exp);
    }

    public double getDeg() {
        return deg.get();
    }


    public void setDeg(double deg) {
        this.deg.set(deg);
    }

    public double getLog() {
        return log.get();
    }


    public void setLog(double log) {
        this.log.set(log);
    }
}
