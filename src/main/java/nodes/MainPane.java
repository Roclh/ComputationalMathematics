package nodes;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

public class MainPane extends FlowPane {
    private final double maxSize = 2560d;
    private final double minSize = 1440d;

    public MainPane() {
        this.setMaxSize(maxSize,minSize);
        this.setPadding(new Insets(10d));
    }

    public MainPane(Orientation orientation) {
        super(orientation);
        this.setMaxSize(maxSize,minSize);
        this.setPadding(new Insets(10d));
    }

    public MainPane(double hgap, double vgap) {
        super(hgap, vgap);
        this.setMaxSize(maxSize,minSize);
        this.setPadding(new Insets(10d));
    }

    public MainPane(Orientation orientation, double hgap, double vgap) {
        super(orientation, hgap, vgap);
        this.setMaxSize(maxSize,minSize);
        this.setPadding(new Insets(10d));
    }

    public MainPane(Node... children) {
        super(children);
        this.setMaxSize(maxSize,minSize);
        this.setPadding(new Insets(10d));
    }

    public MainPane(Orientation orientation, Node... children) {
        super(orientation, children);
        this.setMaxSize(maxSize,minSize);
        this.setPadding(new Insets(10d));
    }

    public MainPane(double hgap, double vgap, Node... children) {
        super(hgap, vgap, children);
        this.setMaxSize(maxSize,minSize);
        this.setPadding(new Insets(10d));
    }

    public MainPane(Orientation orientation, double hgap, double vgap, Node... children) {
        super(orientation, hgap, vgap, children);
        this.setMaxSize(maxSize,minSize);
        this.setPadding(new Insets(10d));
    }
}
