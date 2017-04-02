import logo.Interpreter;
import logo.program.Expression;
import logo.program.LogicalError;
import logo.program.SyntaxError;
import logo.program.Variable;
import logo.program.commands.CommandsFactory;
import logo.program.operators.BinaryOperator;
import logo.program.operators.OperatorsFactory;
import logo.program.primitives.Number;
import logo.program.primitives.Primitive;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpressionTest {

    static {
        CommandsFactory.init("test_standart_commands.properties");
    }

    @Test
    public void expressionTest() throws SyntaxError {
        Number two = new Number(2);
        List<Primitive> a = Arrays.asList(two, two, null);
        List<BinaryOperator> b = Arrays.asList(OperatorsFactory.create("+"));
        Expression expr = new Expression(a, b);
        assertEquals(4, expr.calculate());
    }

    @Test
    public void operatorsTest() throws SyntaxError {
        // (4/2+1)*5-100
        List<Primitive> a = Arrays.asList(new Number(4), new Number(2), null,
                new Number(1), null, new Number(5), null, new Number(100), null);
        List<BinaryOperator> b = Arrays.asList(OperatorsFactory.create("/"),
                OperatorsFactory.create("+"), OperatorsFactory.create("*"), OperatorsFactory.create("-"));
        Expression expr = new Expression(a, b);
        assertEquals(-85, expr.calculate());
    }

    @Test
    public void variablesTest() throws SyntaxError, LogicalError {
        Interpreter.mainBlock.addVariable(new Variable("__var__"));
        Interpreter.mainBlock.setVariableValue("__var__", 5);
        logo.program.primitives.Variable link = new logo.program.primitives.Variable("__var__", Interpreter.mainBlock);
        List<Primitive> a = Arrays.asList(link, new Number(5), null);
        List<BinaryOperator> b = Arrays.asList(OperatorsFactory.create("*"));
        Expression expr = new Expression(a, b);
        assertEquals(25, expr.calculate());
        Interpreter.mainBlock.setVariableValue("__var__", 0);
        assertEquals(0, expr.calculate());
    }

}
