package ru.nsu.nev.program;

import ru.nsu.nev.Parser;

public class SyntaxError extends Exception{

    public SyntaxError (String description){
        // TODO log
        System.err.println("Line " + Parser.getNumberOfLine() + ": Syntax error \"" + description + "\". Program has been halted.");
        System.exit(0);
    }
}
