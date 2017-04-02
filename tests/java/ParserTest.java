import logo.Interpreter;
import logo.Parser;
import logo.program.Expression;
import logo.program.SyntaxError;
import logo.program.commands.CommandsFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void skipTest() throws SyntaxError {
        Parser.init("src/tests/logo/expr_1");
        String[] a = {"4", "+", "4", "/", "4", "+", "4", "*", "4", "-", "4", "*", "4", "-", "4", "=", "1", "."};
        for (String s : a)
            assertTrue(Parser.skipWord(s, false));
    }

    @Test
    public void expressionTest() throws SyntaxError {
        for (int i = 0; i < 3; i++) {
            Parser.init("src/tests/logo/expr_" + i);
            Expression expr = Parser.getExpression(Interpreter.mainBlock);
            Parser.skipWord("=");
            Expression expr2 = Parser.getExpression(Interpreter.mainBlock);
            assertEquals(expr2.calculate(), expr.calculate());
        }
    }

    @Test
    public void commandTest() throws SyntaxError {
        CommandsFactory.init("test_commands.properties");
        assertNull(CommandsFactory.create("__not_a_command__"));
        assertNotNull(CommandsFactory.create("acommand"));
        Parser.init("src/tests/logo/command_0");
        assertNull(Parser.getCommand(false));
        Parser.init("src/tests/logo/command_1");
        assertNotNull(Parser.getCommand(false));
    }

    @Test
    public void nameTest() throws SyntaxError {
        CommandsFactory.init("test_commands.properties");
        Parser.init("src/tests/logo/command_0");
        assertNotNull(Parser.getName());
        Parser.init("src/tests/logo/command_1");
        assertNull(Parser.getName());
    }


}
