package ru.nsu.nev.program.commands;

import ru.nsu.nev.Interpreter;
import ru.nsu.nev.Parser;
import ru.nsu.nev.program.*;

public class Assignment implements Command {

    String variableName;
    Expression newValue;

    @Override
    public void onRead(FunctionalBlock placement) throws SyntaxError {
        variableName = Parser.getName();
        if (variableName == null)
            throw new SyntaxError("variable name was expected");
        newValue = Parser.getExpression(placement);
    }

    @Override
    public void onExecute() throws LogicalError, SyntaxError {
        FunctionalBlock block = Interpreter.mainBlock;
        if (block.getVariableValue(variableName) == null)
            throw new LogicalError("variable name was expected");
        block.setVariableValue(variableName, newValue.calculate());
    }
}
