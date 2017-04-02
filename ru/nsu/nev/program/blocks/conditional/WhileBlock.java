package ru.nsu.nev.program.blocks.conditional;

import ru.nsu.nev.Interpreter;
import ru.nsu.nev.program.Expression;
import ru.nsu.nev.program.FunctionalBlock;
import ru.nsu.nev.program.LogicalError;
import ru.nsu.nev.program.SyntaxError;

public class WhileBlock extends IfBlock {

    public WhileBlock (FunctionalBlock nparent, Expression ncondition){
        super(nparent, ncondition);
    }

    @Override
    public void execute () throws LogicalError, SyntaxError {
        if (condition == null){
            Interpreter.logger.fatal("internal error");
            System.exit(1);
        }
        while (condition.calculate() != 0)
            super.execute();
    }
}
