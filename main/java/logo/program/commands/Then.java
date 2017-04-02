package logo.program.commands;

import logo.program.FunctionalBlock;
import logo.program.LogicalError;
import logo.program.SyntaxError;

class Then implements Command {
    @Override
    public void onRead(FunctionalBlock placement) throws SyntaxError {
        throw new SyntaxError("condition needed");
    }

    @Override
    public void onExecute() throws LogicalError, SyntaxError {
    }
}
