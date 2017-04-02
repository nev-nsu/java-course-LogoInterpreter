package logo.program;

import logo.Interpreter;
import logo.Parser;

/**
 * Calls as a result of parsing failure.
 */
public class SyntaxError extends Exception {

    public SyntaxError(String description) {
        String message = "Line " + Parser.getNumberOfLine() + ": Syntax error \"" + description + "\". Program has been halted.";
        Interpreter.logger.error(message);
        System.err.println(message);
//        System.exit(0);
    }
}
