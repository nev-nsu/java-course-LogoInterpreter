package ru.nsu.nev.program.commands;

import ru.nsu.nev.Canvas;
import ru.nsu.nev.Interpreter;
import ru.nsu.nev.Parser;
import ru.nsu.nev.program.Expression;
import ru.nsu.nev.program.FunctionalBlock;
import ru.nsu.nev.program.LogicalError;
import ru.nsu.nev.program.SyntaxError;

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
        FunctionalBlock block = Interpreter.getMainBlock();
        Canvas canvas = Interpreter.getMainCanvas();
        if (block.getVariableValue("__initialized__") == null)
            throw new LogicalError("field isn't initialized");
        int X = block.getVariableValue("__x_position__");
        int Y = block.getVariableValue("__y_position__");
        int i = offset.calculate();
        int x_limit = canvas.getWidth() - 1;
        int y_limit = canvas.getHeight() - 1;
        boolean draw_mode = block.getVariableValue("__draw_mode__") == 1;
        canvas.drawPoint(X, Y, canvas.freeSpace);
        while (i > 0){
            i--;
            if (draw_mode) canvas.drawPoint(X, Y, '*');
            switch (direction) {
                case 'L':
                    X--;
                    if (X < 0)
                        X = x_limit;
                    break;
                case 'R':
                    X++;
                    if (X > x_limit - 1)
                        X = 0;
                    break;
                case 'U':
                    Y--;
                    if (Y < 0)
                        Y = y_limit;
                    break;
                case 'D':
                    Y++;
                    if (Y > y_limit - 1)
                        Y = 0;
                    break;
            }
        }
        canvas.drawPoint(X, Y, '@');
        block.setVariableValue("__x_position__", X);
        block.setVariableValue("__y_position__", Y);
    }

}

