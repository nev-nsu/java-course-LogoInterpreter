package logo.program.commands;

import logo.Canvas;
import logo.Interpreter;
import logo.program.FunctionalBlock;
import logo.program.LogicalError;
import logo.program.SyntaxError;

class Clean implements Command {
    @Override
    public void onRead(FunctionalBlock placement) throws SyntaxError, LogicalError {

    }

    @Override
    public void onExecute() throws LogicalError, SyntaxError {
        FunctionalBlock block = Interpreter.mainBlock;
        Canvas.cleanField();
        int X = block.getVariableValue("__x_position__");
        int Y = block.getVariableValue("__y_position__");
        Canvas.drawPoint(X, Y, '@');
    }
}
