package ru.nsu.nev.program.commands;

import ru.nsu.nev.Interpreter;
import ru.nsu.nev.program.FunctionalBlock;
import ru.nsu.nev.program.LogicalError;
import ru.nsu.nev.program.SyntaxError;

public class Draw implements Command {

    @Override
    public void onRead(FunctionalBlock placement) throws SyntaxError {
    }

    @Override
    public void onExecute() throws LogicalError, SyntaxError {
        if (Interpreter.mainBlock.getVariableValue("__initialized__") == null)
            throw new LogicalError("field isn't initialized");
        Interpreter.mainBlock.setVariableValue("__draw_mode__", 1);
    }
}
