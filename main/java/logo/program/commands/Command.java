package logo.program.commands;

import logo.program.FunctionalBlock;
import logo.program.LogicalError;
import logo.program.SyntaxError;


/**
 * Unit of execution
 */
public interface Command {

    /**
     * Require necessary arguments, change functional block state.
     *
     * @param placement in which context the command will be executed
     */
    void onRead(FunctionalBlock placement) throws SyntaxError, LogicalError;

    void onExecute() throws LogicalError, SyntaxError;
}
