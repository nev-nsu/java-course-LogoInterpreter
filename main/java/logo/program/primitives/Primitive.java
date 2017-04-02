package logo.program.primitives;


import logo.program.SyntaxError;

public interface Primitive {

    int getValue() throws SyntaxError;
}
