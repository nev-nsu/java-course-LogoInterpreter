package logo.program.commands;

import logo.Parser;
import logo.program.Expression;
import logo.program.FunctionalBlock;
import logo.program.LogicalError;
import logo.program.SyntaxError;

public class Assignment implements Command {

    private String variableName;
    private Expression newValue;
    private FunctionalBlock block;

    @Override
    public void onRead(FunctionalBlock placement) throws SyntaxError {
        variableName = Parser.getName();
        if (variableName == null)
            throw new SyntaxError("variable name was expected");
        newValue = Parser.getExpression(placement);
        block = placement;
    }

    @Override
    public void onExecute() throws LogicalError, SyntaxError {
        if (block.getVariableValue(variableName) == null)
            throw new LogicalError("variable name was expected");
        block.setVariableValue(variableName, newValue.calculate());
    }
}
