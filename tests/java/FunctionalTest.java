import logo.Canvas;
import logo.Interpreter;
import logo.Parser;
import logo.program.FunctionalBlock;
import logo.program.commands.CommandsFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FunctionalTest {

    static {
        CommandsFactory.init("test_standart_commands.properties");
    }

    @Test
    public void isItWorks() {
        try {
            for (int i = 1; i <= 4; i++) {
                Parser.init("src/tests/logo/test_" + i + "_in.logo");
                FunctionalBlock block = new FunctionalBlock(true, Interpreter.mainBlock);
                Canvas.clean();
                Interpreter.readFunctionalBlock(block);
                String[] out = Canvas.getField();
                BufferedReader rightOut = new BufferedReader(new InputStreamReader(new FileInputStream(
                        "src/tests/logo/test_" + i + "_out.logo"), "UTF-8"));
                for (String str : out) {
                    String rightStr = rightOut.readLine();
                    assertTrue(rightStr != null && rightStr.equals(str.trim()));
                }
            }
        } catch (Exception e) {
            Assertions.fail("");
        }

        System.setOut(null);
    }


}
