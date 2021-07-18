package command;

import javafx.scene.Node;

import java.util.ArrayList;

public abstract class Command {
    private String name;

    public Command(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract ArrayList<Node> getNodes();
}
