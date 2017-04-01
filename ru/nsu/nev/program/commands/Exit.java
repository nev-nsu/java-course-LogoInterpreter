package ru.nsu.nev.program.commands;

import ru.nsu.nev.Interpreter;
import ru.nsu.nev.program.FunctionalBlock;
import ru.nsu.nev.program.LogicalError;
import ru.nsu.nev.program.SyntaxError;

public class Exit implements Command {

    @Override
    public void onRead(FunctionalBlock placement) throws SyntaxError {

    }

    @Override
    public void onExecute() throws LogicalError, SyntaxError {
        Interpreter.getMainCanvas().show();
        System.exit(0);
    }
}
