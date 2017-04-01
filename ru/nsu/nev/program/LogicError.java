package ru.nsu.nev.program;

import ru.nsu.nev.Parser;

public class LogicError extends Exception{

    public LogicError (String description){
        // TODO log
        System.err.println("Line " + Parser.getNumberOfLine() + ": Logic error \"" + description + "\". Program has been halted.");
        System.exit(0);
    }
}