package logo.program.commands;

import logo.Interpreter;
import logo.program.FunctionalBlock;
import logo.program.LogicalError;
import logo.program.SyntaxError;

class Ward implements Command {

    @Override
    public void onRead(FunctionalBlock placement) throws SyntaxError {
    }

    @Override
    public void onExecute() throws LogicalError, SyntaxError {
        if (Interpreter.mainBlock.getVariableValue("__initialized__") == null)
            throw new LogicalError("field isn't initialized");
        Interpreter.mainBlock.setVariableValue("__draw_mode__", 0);
    }
}