package logo.program.commands;

import logo.Canvas;
import logo.Interpreter;
import logo.Parser;
import logo.program.*;

public class Init implements Command {

    private Expression width = null;
    private Expression height = null;
    private Expression x = null;
    private Expression y = null;

    @Override
    public void onRead(FunctionalBlock placement) throws SyntaxError {
        width = Parser.getExpression(placement);
        Parser.skipWord(",", true);
        height = Parser.getExpression(placement);
        Parser.skipWord(",", true);
        x = Parser.getExpression(placement);
        Parser.skipWord(",", true);
        y = Parser.getExpression(placement);
    }


    @Override
    public void onExecute() throws SyntaxError, LogicalError {
        FunctionalBlock block = Interpreter.mainBlock;
        int X = x.calculate();
        int Y = y.calculate();
        if (block.getVariableValue("__initialized__") == null) {
            block.addVariable(new Variable("__initialized__"));
            block.addVariable(new Variable("__draw_mode__"));
            block.addVariable(new Variable("__x_position__"));
            block.addVariable(new Variable("__y_position__"));
        }
        Canvas.setSize(width.calculate(), height.calculate());
        Canvas.setMinSize(X + 1, Y + 1);
        block.setVariableValue("__x_position__", X);
        block.setVariableValue("__y_position__", Y);
        Canvas.drawPoint(X, Y, '@');
    }

}
