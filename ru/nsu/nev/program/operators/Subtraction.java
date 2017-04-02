package ru.nsu.nev.program.operators;

import ru.nsu.nev.program.SyntaxError;
import ru.nsu.nev.program.primitives.Primitive;

public class Subtraction implements BinaryOperator, Priority {

    public int apply(Primitive left, Primitive right) throws SyntaxError {
        return left.getValue() - right.getValue();
    }

    @Override
    public int getPriority() {
        return 1;
    }
}