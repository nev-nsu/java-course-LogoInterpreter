package ru.nsu.nev.program.blocks.conditional;

import ru.nsu.nev.Interpreter;
import ru.nsu.nev.program.Expression;
import ru.nsu.nev.program.FunctionalBlock;
import ru.nsu.nev.program.LogicalError;
import ru.nsu.nev.program.SyntaxError;

public class IfBlock extends FunctionalBlock {
    protected final Expression condition;

    public IfBlock(FunctionalBlock nparent, Expression ncondition) {
        super(false, nparent);
        condition = ncondition;
    }

    @Override
    public void execute() throws LogicalError, SyntaxError {
        if (condition == null) {
            Interpreter.logger.fatal("internal error");
            System.exit(1);
        }
        if (condition.calculate() != 0)
            super.execute();
    }
}
