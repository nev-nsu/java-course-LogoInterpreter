package logo.program.operators;

import logo.program.SyntaxError;
import logo.program.primitives.Primitive;

class Subtraction implements BinaryOperator, Priority {

    public int apply(Primitive left, Primitive right) throws SyntaxError {
        return left.getValue() - right.getValue();
    }

    @Override
    public int getPriority() {
        return 1;
    }
}