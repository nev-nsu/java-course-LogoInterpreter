package ru.nsu.nev.program.primitives;


import ru.nsu.nev.program.SyntaxError;

public interface Primitive {

    int getValue() throws SyntaxError;
}
