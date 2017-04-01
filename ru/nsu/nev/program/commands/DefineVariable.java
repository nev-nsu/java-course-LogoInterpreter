package ru.nsu.nev.program.commands;

import ru.nsu.nev.Interpreter;
import ru.nsu.nev.Parser;
import ru.nsu.nev.program.FunctionalBlock;
import ru.nsu.nev.program.LogicalError;
import ru.nsu.nev.program.SyntaxError;
import ru.nsu.nev.program.Variable;

public class DefineVariable implements Command {

    String variableName;

    @Override
    public void onRead(FunctionalBlock placement) throws SyntaxError {
        variableName = Parser.getName();
        if (variableName == null)
            throw new SyntaxError("variable name was expected");
    }

    @Override
    public void onExecute() throws LogicalError, SyntaxError {
        FunctionalBlock block = Interpreter.getMainBlock();
        block.addVariable(new Variable(variableName));
    }
}
