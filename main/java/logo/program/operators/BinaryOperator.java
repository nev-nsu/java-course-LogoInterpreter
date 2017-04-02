package logo.program.operators;

import logo.program.SyntaxError;
import logo.program.primitives.Primitive;

public interface BinaryOperator extends Priority {

    int apply(Primitive left, Primitive right) throws SyntaxError;

}
