package ru.nsu.nev.program.commands;

import ru.nsu.nev.program.FunctionalBlock;
import ru.nsu.nev.program.LogicError;
import ru.nsu.nev.program.SyntaxError;

public interface Command {

    // handler works with internal structure
    void handler(FunctionalBlock placement) throws SyntaxError;

    // drawer works only with canvas
    void drawer() throws LogicError, SyntaxError;
}
