package logo.program.commands;

import logo.program.FunctionalBlock;
import logo.program.LogicalError;
import logo.program.SyntaxError;

public interface Command {

    // onRead works with internal structure
    void onRead(FunctionalBlock placement) throws SyntaxError, LogicalError;

    // onExecute works only with canvas
    void onExecute() throws LogicalError, SyntaxError;
}
