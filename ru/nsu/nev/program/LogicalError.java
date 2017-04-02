package ru.nsu.nev.program;

import ru.nsu.nev.Interpreter;
import ru.nsu.nev.Parser;

public class LogicalError extends Exception {

    public LogicalError(String description) {
        String message = "Line " + Parser.getNumberOfLine() + ": Logical error \"" + description + "\". Program has been halted.";
        Interpreter.logger.error(message);
        System.err.println(message);
        System.exit(0);
    }
}