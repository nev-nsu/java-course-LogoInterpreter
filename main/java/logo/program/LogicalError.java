package logo.program;

import logo.Interpreter;
import logo.Parser;

/**
 * Calls as a result of failed parameters of logo-commands validation.
 */
public class LogicalError extends Exception {

    public LogicalError(String description) {
        String message = "Line " + Parser.getNumberOfLine() + ": Logical error \"" + description + "\". Program has been halted.";
        Interpreter.logger.error(message);
        System.err.println(message);
//        System.exit(0);
    }
}