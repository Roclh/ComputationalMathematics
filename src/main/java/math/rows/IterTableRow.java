package math.rows;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class IterTableRow {
    private SimpleIntegerProperty n = new SimpleIntegerProperty();
    private SimpleDoubleProperty xprev = new SimpleDoubleProperty();
    private SimpleDoubleProperty x = new SimpleDoubleProperty();
    private SimpleDoubleProperty px = new SimpleDoubleProperty();
    private SimpleDoubleProperty fx = new SimpleDoubleProperty();
    private SimpleDoubleProperty prevx = new SimpleDoubleProperty();

    public IterTableRow(int n, double xprev, double x, double px, double fx, double prevx) {
        this.n.set(n);
        this.xprev.set(xprev);
        this.fx.set(fx);
        this.x.set(x);
        this.px.set(px);
        this.prevx.set(prevx);
    }

    public static ArrayList<TableColumn<IterTableRow, ? extends Number>> getTableColumns(){
        ArrayList<TableColumn<IterTableRow, ? extends Number>> list = new ArrayList<>();
        TableColumn<IterTableRow, Integer> nColumn = new TableColumn<>("№");
        nColumn.setCellValueFactory(new PropertyValueFactory<>("n"));
        list.add(nColumn);
        TableColumn<IterTableRow, Double> xprevColumn = new TableColumn<>("Prev");
        xprevColumn.setCellValueFactory(new PropertyValueFactory<>("xprev"));
        list.add(xprevColumn);
        TableColumn<IterTableRow, Double> xColumn = new TableColumn<>("x");
        xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        list.add(xColumn);
        TableColumn<IterTableRow, Double> pxColumn = new TableColumn<>("p(x)");
        pxColumn.setCellValueFactory(new PropertyValueFactory<>("px"));
        list.add(pxColumn);
        TableColumn<IterTableRow, Double> fxColumn = new TableColumn<>("f(x)");
        fxColumn.setCellValueFactory(new PropertyValueFactory<>("fx"));
        list.add(fxColumn);
        TableColumn<IterTableRow, Double> prevxColumn = new TableColumn<>("|prev-x|");
        prevxColumn.setCellValueFactory(new PropertyValueFactory<>("prevx"));
        list.add(prevxColumn);
        return list;
    }

    public int getN() {
        return n.get();
    }


    public void setN(int n) {
        this.n.set(n);
    }

    public double getXprev() {
        return xprev.get();
    }

    public void setXprev(double xprev) {
        this.xprev.set(xprev);
    }

    public double getX() {
        return x.get();
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public double getPx() {
        return px.get();
    }

    public void setPx(double px) {
        this.px.set(px);
    }

    public double getPrevx() {
        return prevx.get();
    }

    public void setPrevx(double prevx) {
        this.prevx.set(prevx);
    }

    public double getFx() {
        return fx.get();
    }

    public void setFx(double fx) {
        this.fx.set(fx);
    }

}
