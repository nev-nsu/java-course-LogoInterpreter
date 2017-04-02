package ru.nsu.nev.program;

import ru.nsu.nev.program.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class FunctionalBlock {

    public final boolean isImmediatelyExecute;
    public boolean isFinished = false;

    private final List<Command> commands = new ArrayList<>();
    private List<Variable> variables = new ArrayList<>();
    private final FunctionalBlock parent;

    public FunctionalBlock(boolean immediately, FunctionalBlock nparent) {
        isImmediatelyExecute = immediately;
        parent = nparent;
    }

    public void execute() throws LogicalError, SyntaxError {
        for (Command c : commands) {
            c.onExecute();
        }
        variables = new ArrayList<>();
    }

    public void addCommand(Command cmd) {
        commands.add(cmd);
    }

    public void addVariable(Variable var) throws LogicalError {
        for (Variable v : variables)
            if (v.getName().equals(var.getName()))
                throw new LogicalError("variable \"" + var.getValue() + "\" already exists");
        variables.add(var);
    }

    private Variable getVarByName(String name) {
        FunctionalBlock block = this;
        while (block != null) {
            for (Variable var : block.variables)
                if (var.getName().equals(name))
                    return var;
            block = block.parent;
        }
        return null;
    }

    public Integer getVariableValue(String name) {
        Variable var = getVarByName(name);
        if (var == null)
            return null;
        return var.getValue();
    }

    public void setVariableValue(String name, int value) throws SyntaxError {
        Variable var = getVarByName(name);
        if (var == null)
            throw new SyntaxError("variable " + name + " doesn't exist");
        var.setValue(value);
    }
}
