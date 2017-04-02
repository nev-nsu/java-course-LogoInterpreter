package ru.nsu.nev.program.commands;

import ru.nsu.nev.Canvas;
import ru.nsu.nev.Interpreter;
import ru.nsu.nev.Parser;
import ru.nsu.nev.program.Expression;
import ru.nsu.nev.program.FunctionalBlock;
import ru.nsu.nev.program.LogicalError;
import ru.nsu.nev.program.SyntaxError;

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
        Canvas.drawPoint(oldX, oldY, Canvas.freeSpace);
        Canvas.drawPoint(newX, newY, '@');
    }
}
