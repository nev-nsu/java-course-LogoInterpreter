package ru.nsu.nev.program.operators;


import ru.nsu.nev.program.primitives.Primitive;

public class Division implements BinaryOperator, Priority {

    public int apply (Primitive left, Primitive right){
        return left.getValue() / right.getValue();
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
