package logo.program.primitives;


import logo.program.SyntaxError;

/**
 * An object can be used as number in expressions.
 */
public interface Primitive {

    int getValue() throws SyntaxError;
}
