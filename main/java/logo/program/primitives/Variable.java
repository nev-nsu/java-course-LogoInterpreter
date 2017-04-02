package logo.program.primitives;

import logo.program.FunctionalBlock;
import logo.program.SyntaxError;

/**
 * Mask object for deferred execution, which can get value of real Variable.
 */
public class Variable implements Primitive {

    private final String name;
    private final FunctionalBlock placement;

    public Variable(String nname, FunctionalBlock block) {
        name = nname;
        placement = block;
    }

    public int getValue() throws SyntaxError {
        Integer value = placement.getVariableValue(name);
        if (value == null)
            throw new SyntaxError ("variable \""+name+"\" doesn't found");
        return value;
    }
}
