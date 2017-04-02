import logo.Interpreter;
import logo.Parser;
import logo.program.FunctionalBlock;
import logo.program.commands.CommandsFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CrashFunctionalTest {

    static {
        CommandsFactory.init("test_standart_commands.properties");
    }

    private boolean isFailed(String filename) {
        boolean fail = false;
        try {
            Parser.init(filename);
            FunctionalBlock block = new FunctionalBlock(true, Interpreter.mainBlock);
            Interpreter.readFunctionalBlock(block);
        } catch (Exception e) {
            fail = true;
        }
        return fail;
    }

// 0. invalid expression
// 1. unknownCommand
// 2. wrongSyntaxOfConditionalBlocks
// 3. noInitialization
// 4. noExit
// 5. invalidFiledSize
// 6. fieldOverstepping
// 7. emptyFile

    @Test
    public void invalidSyntax() {
        for (int i = 0; i < 8; i++)
            assertTrue(isFailed("src/tests/logo/crash_" + i));
    }


}
