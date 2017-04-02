package ru.nsu.nev.program.operators;

import ru.nsu.nev.program.SyntaxError;
import ru.nsu.nev.program.primitives.Primitive;

public interface BinaryOperator extends Priority {

    int apply(Primitive left, Primitive right) throws SyntaxError;

}
