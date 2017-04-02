package logo.program.commands;

import logo.program.FunctionalBlock;
import logo.program.LogicalError;
import logo.program.SyntaxError;

public class End implements Command {
    @Override
    public void onRead(FunctionalBlock placement) throws SyntaxError, LogicalError {
        placement.isFinished = true;
    }

    @Override
    public void onExecute() throws LogicalError, SyntaxError {
    }
}
