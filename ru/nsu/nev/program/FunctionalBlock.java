package ru.nsu.nev.program;

import ru.nsu.nev.program.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class FunctionalBlock {

    public Boolean isImmediatelyExecute;
    public Boolean isFinished = false;

    private List<Command> commands = new ArrayList<>();
    private List<Variable> variables = new ArrayList<>();
    private FunctionalBlock parent = null;

    public FunctionalBlock (Boolean immediately, FunctionalBlock nparent){
        this (immediately);
        parent = nparent;
    }

    public FunctionalBlock (Boolean immediately){
        isImmediatelyExecute = immediately;
    }

    public void execute () throws LogicError, SyntaxError {
        if (!isImmediatelyExecute)
            for (Command c : commands){
                c.drawer();
            }
    }

    public void addCommand (Command cmd){
        commands.add(cmd);
    }

    public void addVariable (Variable var){
        variables.add(var);
    }

    private Variable getVarByName (String name){
        FunctionalBlock block = this;
        while (block != null) {
            for (Variable var : block.variables)
                if (var.getName().equals(name))
                    return var;
            block = block.parent;
        }
        return null;
    }

    public Integer getVariableValue (String name){
        Variable var = getVarByName(name);
        if (var == null)
            return null;
        return var.getValue();
    }

    public void setVariableValue (String name, int value) throws SyntaxError {
        Variable var = getVarByName(name);
        if (var == null)
            throw new SyntaxError("variable "+name+" doesn't exist");
        var.setValue(value);
    }
}
