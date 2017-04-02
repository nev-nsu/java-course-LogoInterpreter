package logo.program.commands;

import logo.Canvas;
import logo.program.FunctionalBlock;
import logo.program.LogicalError;
import logo.program.SyntaxError;

public class Exit implements Command {

    @Override
    public void onRead(FunctionalBlock placement) throws SyntaxError {
        placement.isFinished = true;
    }

    @Override
    public void onExecute() throws LogicalError, SyntaxError {
        Canvas.show();
    }
}
