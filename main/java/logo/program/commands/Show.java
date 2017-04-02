package logo.program.commands;

import logo.Canvas;
import logo.program.FunctionalBlock;
import logo.program.LogicalError;
import logo.program.SyntaxError;

public class Show implements Command {
    @Override
    public void onRead(FunctionalBlock placement) throws SyntaxError, LogicalError {

    }

    @Override
    public void onExecute() throws LogicalError, SyntaxError {
        Canvas.show();
    }
}
