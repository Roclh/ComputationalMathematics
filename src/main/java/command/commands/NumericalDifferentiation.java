package command.commands;

import command.Command;
import javafx.scene.Node;

import java.util.ArrayList;

public class NumericalDifferentiation extends Command {
    public NumericalDifferentiation() {
        super("Численное дифференцирование");
    }

    @Override
    public ArrayList<Node> getNodes() {
        return null;
    }
}
