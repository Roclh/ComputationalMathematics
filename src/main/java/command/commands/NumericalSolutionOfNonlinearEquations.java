package command.commands;

import command.Command;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class NumericalSolutionOfNonlinearEquations extends Command {
    public NumericalSolutionOfNonlinearEquations() {
        super("Численное решение нелинейных уравнений");
    }

    @Override
    public ArrayList<Node> getNodes() {
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(new Label("Пососи"));
        return nodes;
    }
}
