package ru.nsu.nev.program.blocks.conditional;

import ru.nsu.nev.program.Expression;
import ru.nsu.nev.program.FunctionalBlock;
import ru.nsu.nev.program.LogicError;
import ru.nsu.nev.program.SyntaxError;

public class WhileBlock extends IfBlock {

    public WhileBlock (Boolean immediately, FunctionalBlock nparent, Expression ncondition){
        super(immediately, nparent, ncondition);
    }

    @Override
    public void execute () throws LogicError, SyntaxError {
        // TODO : if (condition == null)
        while (condition.calculate() != 0)
            super.execute();
    }
}
