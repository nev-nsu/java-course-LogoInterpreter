package logo.program;

import logo.program.commands.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * An unit of deferred execution and a scope.
 */
public class FunctionalBlock {

    /**
     * Is commands executed right after it's reading
     */
    public final boolean isImmediatelyExecute;

    private final List<Command> commands = new ArrayList<>();
    private final FunctionalBlock parent;

    /**
     * Is reading of the block already finished.
     */
    public boolean isFinished = false;
    private List<Variable> variables = new ArrayList<>();

    public FunctionalBlock(boolean immediately, FunctionalBlock nparent) {
        isImmediatelyExecute = immediately;
        parent = nparent;
    }

    /**
     * Execute commands from the queue of deferred execution.
     */
    public void execute() throws LogicalError, SyntaxError {
        for (Command c : commands) {
            c.onExecute();
        }
        variables = new ArrayList<>();
    }

    /**
     * Add command to the queue of deferred execution.
     */
    public void addCommand(Command cmd) {
        commands.add(cmd);
    }

    /**
     * Add variable to the scope.
     *
     * @throws LogicalError if variable already already exist in this block
     */
    public void addVariable(Variable newVariable) throws LogicalError {
        for (Variable v : variables)
            if (v.getName().equals(newVariable.getName()))
                throw new LogicalError("variable '" + newVariable.getName() + "' already exists");
        variables.add(newVariable);
    }

    private Variable getVariableByName(String name) {
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
        Variable var = getVariableByName(name);
        if (var == null)
            return null;
        return var.getValue();
    }

    public void setVariableValue(String name, int value) throws SyntaxError {
        Variable var = getVariableByName(name);
        if (var == null)
            throw new SyntaxError("variable " + name + " doesn't exist");
        var.setValue(value);
    }
}
