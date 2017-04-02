package ru.nsu.nev.program.commands;

import ru.nsu.nev.program.FunctionalBlock;
import ru.nsu.nev.program.LogicalError;
import ru.nsu.nev.program.SyntaxError;

public class Then implements Command {
    @Override
    public void onRead(FunctionalBlock placement) throws SyntaxError {
        throw new SyntaxError("condition needed");
    }

    @Override
    public void onExecute() throws LogicalError, SyntaxError {
    }
}
