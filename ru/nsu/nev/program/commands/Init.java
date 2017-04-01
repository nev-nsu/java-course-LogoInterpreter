package ru.nsu.nev.program.commands;

import ru.nsu.nev.Interpreter;
import ru.nsu.nev.Parser;
import ru.nsu.nev.program.*;

public class Init implements Command {

    public static boolean isInitialized = false;

    private Expression width = null;
    private Expression height = null;
    private Expression x = null;
    private Expression y = null;

    public void handler(FunctionalBlock placement) throws SyntaxError {
        width = Parser.getExpression(placement);
        Parser.skipWord(",", true);
        height = Parser.getExpression(placement);
        Parser.skipWord(",", true);
        x = Parser.getExpression(placement);
        Parser.skipWord(",", true);
        y = Parser.getExpression(placement);
    }


    public void drawer() throws SyntaxError, LogicError {
        int X = x.calculate();
        int Y = y.calculate();
        if (!isInitialized) {
            isInitialized = true;
            Interpreter.getMainBlock().addVariable(new Variable("__x_position__"));
            Interpreter.getMainBlock().addVariable(new Variable("__y_position__"));
        }
        Interpreter.getMainCanvas().setSize(width.calculate(), height.calculate());
        Interpreter.getMainCanvas().setMinSize(X + 1, Y + 1);
        Interpreter.getMainBlock().setVariableValue("__x_position__", X);
        Interpreter.getMainBlock().setVariableValue("__y_position__", Y);
        Interpreter.getMainCanvas().drawPoint(X, Y);
    }

}
