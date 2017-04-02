package logo.program.commands;

import logo.Canvas;
import logo.Interpreter;
import logo.Parser;
import logo.program.Expression;
import logo.program.FunctionalBlock;
import logo.program.LogicalError;
import logo.program.SyntaxError;

public class Teleport implements Command {

    private Expression X, Y;

    @Override
    public void onRead(FunctionalBlock placement) throws SyntaxError {
        X = Parser.getExpression(placement);
        Parser.skipWord(",", true);
        Y = Parser.getExpression(placement);
    }

    @Override
    public void onExecute() throws LogicalError, SyntaxError {
        FunctionalBlock block = Interpreter.mainBlock;
        if (block.getVariableValue("__initialized__") == null)
            throw new LogicalError("field isn't initialized");
        int newX = X.calculate();
        int newY = Y.calculate();
        int oldX = block.getVariableValue("__x_position__");
        int oldY = block.getVariableValue("__y_position__");
        block.setVariableValue("__x_position__", newX);
        block.setVariableValue("__y_position__", newY);
        boolean draw_mode = block.getVariableValue("__draw_mode__") == 1;
        if (draw_mode)
            Canvas.drawPoint(oldX, oldY, '*');
        else
            Canvas.drawPoint(oldX, oldY, Canvas.freeSpace);
        Canvas.drawPoint(newX, newY, '@');
    }
}
