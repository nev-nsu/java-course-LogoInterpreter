package logo.program.blocks.conditional;

import logo.Interpreter;
import logo.program.Expression;
import logo.program.FunctionalBlock;
import logo.program.LogicalError;
import logo.program.SyntaxError;

/**
 * One-time executed conditional block.
 * Execute if conditional evaluates into nonzero value.
 */
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
