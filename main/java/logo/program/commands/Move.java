package logo.program.commands;

import logo.Canvas;
import logo.Interpreter;
import logo.Parser;
import logo.program.Expression;
import logo.program.FunctionalBlock;
import logo.program.LogicalError;
import logo.program.SyntaxError;

public class Move implements Command {

    private char direction = '\0';
    private Expression offset;

    @Override
    public void onRead(FunctionalBlock placement) throws SyntaxError {
        char dirs[] = {'L', 'R', 'U', 'D'};
        for (char c : dirs)
            if (Parser.skipWord(Character.toString(c)))
                direction = c;
        if (direction == '\0')
            throw new SyntaxError("the direction of movement was expected");
        offset = Parser.getExpression(placement);
    }

    @Override
    public void onExecute() throws LogicalError, SyntaxError {
        FunctionalBlock block = Interpreter.mainBlock;
        if (block.getVariableValue("__initialized__") == null)
            throw new LogicalError("field isn't initialized");
        int X = block.getVariableValue("__x_position__");
        int Y = block.getVariableValue("__y_position__");
        int i = offset.calculate();
        if (i < 0)
            throw new LogicalError("offset is negative");
        int x_limit = Canvas.getWidth() - 1;
        int y_limit = Canvas.getHeight() - 1;
        boolean draw_mode = block.getVariableValue("__draw_mode__") == 1;
        Canvas.drawPoint(X, Y, Canvas.freeSpace);
        while (i > 0) {
            i--;
            if (draw_mode) Canvas.drawPoint(X, Y, '*');
            switch (direction) {
                case 'L':
                    X--;
                    if (X < 0)
                        X = x_limit;
                    break;
                case 'R':
                    X++;
                    if (X > x_limit)
                        X = 0;
                    break;
                case 'U':
                    Y--;
                    if (Y < 0)
                        Y = y_limit;
                    break;
                case 'D':
                    Y++;
                    if (Y > y_limit)
                        Y = 0;
                    break;
            }
        }
        Canvas.drawPoint(X, Y, '@');
        block.setVariableValue("__x_position__", X);
        block.setVariableValue("__y_position__", Y);
    }

}

