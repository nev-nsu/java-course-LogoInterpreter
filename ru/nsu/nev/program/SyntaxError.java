package ru.nsu.nev.program;

import ru.nsu.nev.Interpreter;
import ru.nsu.nev.Parser;

public class SyntaxError extends Exception {

    public SyntaxError(String description) {
        String message = "Line " + Parser.getNumberOfLine() + ": Syntax error \"" + description + "\". Program has been halted.";
        Interpreter.logger.error(message);
        System.err.println(message);
        System.exit(0);
    }
}
