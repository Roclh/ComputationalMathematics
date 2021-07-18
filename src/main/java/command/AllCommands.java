package command;

import command.commands.Calculator;
import command.commands.NumericalSolutionOfNonlinearEquations;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AllCommands {
    ArrayList<Command> commands = new ArrayList<>();

    public AllCommands() {
        init();
    }

    private void init(){
        commands.add(new Calculator());
        commands.add(new NumericalSolutionOfNonlinearEquations());
    }

    public Command getCommandByName(String name){
        return commands.stream().filter(command -> command.getName().equals(name)).findFirst().get();
    }

    public ArrayList<String> getCommands(){
        return (ArrayList<String>) commands.stream().map(Command::getName).collect(Collectors.toList());
    }
}
