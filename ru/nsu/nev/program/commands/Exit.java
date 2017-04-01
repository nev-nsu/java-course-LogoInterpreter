package ru.nsu.nev.program.commands;

import ru.nsu.nev.Interpreter;
import ru.nsu.nev.program.FunctionalBlock;
import ru.nsu.nev.program.LogicError;
import ru.nsu.nev.program.SyntaxError;

public class Exit implements Command {

    public void handler(FunctionalBlock placement) throws SyntaxError {}

    public void drawer() throws LogicError, SyntaxError {
        Interpreter.getMainCanvas().show();
        System.exit(0);
    }
}
