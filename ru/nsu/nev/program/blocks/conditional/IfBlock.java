package ru.nsu.nev.program.blocks.conditional;

import ru.nsu.nev.program.Expression;
import ru.nsu.nev.program.FunctionalBlock;
import ru.nsu.nev.program.LogicalError;
import ru.nsu.nev.program.SyntaxError;

public class IfBlock extends FunctionalBlock{
    protected Expression condition;

    public void setCondition (Expression ncondition){
        condition = ncondition;
    }

    public IfBlock (FunctionalBlock nparent, Expression ncondition){
        super(false, nparent);
        condition = ncondition;
    }

    @Override
    public void execute () throws LogicalError, SyntaxError {
        // TODO : if (condition == null)
        if (condition.calculate() != 0)
            super.execute();
    }
}
