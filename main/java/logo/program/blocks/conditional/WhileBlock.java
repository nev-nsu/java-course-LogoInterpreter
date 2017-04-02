package logo.program.blocks.conditional;

import logo.Interpreter;
import logo.program.Expression;
import logo.program.FunctionalBlock;
import logo.program.LogicalError;
import logo.program.SyntaxError;

public class WhileBlock extends IfBlock {

    public WhileBlock(FunctionalBlock nparent, Expression ncondition) {
        super(nparent, ncondition);
    }

    @Override
    public void execute() throws LogicalError, SyntaxError {
        if (condition == null) {
            Interpreter.logger.fatal("internal error");
            System.exit(1);
        }
        while (condition.calculate() != 0)
            super.execute();
    }
}
