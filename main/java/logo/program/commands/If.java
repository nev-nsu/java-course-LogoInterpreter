package logo.program.commands;

import logo.Interpreter;
import logo.Parser;
import logo.program.Expression;
import logo.program.FunctionalBlock;
import logo.program.LogicalError;
import logo.program.SyntaxError;
import logo.program.blocks.conditional.IfBlock;

public class If implements Command {

    private FunctionalBlock newBlock;

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
