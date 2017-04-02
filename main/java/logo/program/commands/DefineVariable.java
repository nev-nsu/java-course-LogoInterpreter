package logo.program.commands;

import logo.Parser;
import logo.program.FunctionalBlock;
import logo.program.LogicalError;
import logo.program.SyntaxError;
import logo.program.Variable;

public class DefineVariable implements Command {

    private String variableName;
    private FunctionalBlock block;

    @Override
    public void onRead(FunctionalBlock placement) throws SyntaxError {
        variableName = Parser.getName();
        if (variableName == null)
            throw new SyntaxError("variable name was expected");
        block = placement;
    }

    @Override
    public void onExecute() throws LogicalError, SyntaxError {
        block.addVariable(new Variable(variableName));
    }
}
