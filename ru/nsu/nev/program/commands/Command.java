package ru.nsu.nev.program.commands;

import ru.nsu.nev.program.FunctionalBlock;
import ru.nsu.nev.program.LogicalError;
import ru.nsu.nev.program.SyntaxError;

public interface Command {

    // onRead works with internal structure
    void onRead(FunctionalBlock placement) throws SyntaxError, LogicalError;

    // onExecute works only with canvas
    void onExecute() throws LogicalError, SyntaxError;
}
