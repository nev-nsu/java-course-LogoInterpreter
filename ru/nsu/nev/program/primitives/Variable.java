package ru.nsu.nev.program.primitives;

import ru.nsu.nev.program.FunctionalBlock;
import ru.nsu.nev.program.SyntaxError;

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
