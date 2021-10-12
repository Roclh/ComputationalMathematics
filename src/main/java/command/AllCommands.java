package command;

import command.commands.*;
import javafx.scene.control.ScrollPane;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AllCommands {
    ArrayList<Command> commands = new ArrayList<>();
    ScrollPane labScroll;

    public AllCommands(ScrollPane labScroll) {
        this.labScroll = labScroll;
        init();
    }

    private void init(){
        commands.add(new Calculator());
        commands.add(new NumericalSolutionOfNonlinearEquations());
        commands.add(new NumericalIntegration());
        commands.add(new FunctionApproximation());
        commands.add(new FunctionInterpolation());
        commands.add(new NumericalDifferentiation(this.labScroll));
    }

    public Command getCommandByName(String name){
        return commands.stream().filter(command -> command.getName().equals(name)).findFirst().get();
    }

    public ArrayList<String> getCommands(){
        return (ArrayList<String>) commands.stream().map(Command::getName).collect(Collectors.toList());
    }
}
