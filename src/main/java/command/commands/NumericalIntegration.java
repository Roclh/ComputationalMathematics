package command.commands;

import command.Command;
import javafx.scene.Node;

import java.util.ArrayList;

public class NumericalIntegration extends Command {
    public NumericalIntegration() {
        super("Численное интегрирование");
    }

    @Override
    public ArrayList<Node> getNodes() {
        return null;
    }
}
