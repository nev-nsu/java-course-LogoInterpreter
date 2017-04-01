package ru.nsu.nev.program.commands;

import ru.nsu.nev.Interpreter;
import ru.nsu.nev.Parser;
import ru.nsu.nev.program.Expression;
import ru.nsu.nev.program.FunctionalBlock;
import ru.nsu.nev.program.LogicalError;
import ru.nsu.nev.program.SyntaxError;
import ru.nsu.nev.program.blocks.conditional.IfBlock;

public class If implements Command {

    FunctionalBlock newBlock;

    @Override
    public void onRead(FunctionalBlock placement) throws SyntaxError, LogicalError {
        Expression condition = Parser.getExpression(placement);
        if (Parser.getCommand(true).getClass() != Then.class)
            throw new SyntaxError("condition expression isn't finished");
        newBlock = new IfBlock(placement, condition);
        Interpreter.readFunctionalBlock(newBlock);
    }

    @Override
    public void onExecute() throws LogicalError, SyntaxError {
        newBlock.execute();
    }
}
