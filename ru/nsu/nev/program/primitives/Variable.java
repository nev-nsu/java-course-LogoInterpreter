package ru.nsu.nev.program.primitives;

import ru.nsu.nev.program.FunctionalBlock;

public class Variable implements Primitive{

    private String name;
    private FunctionalBlock placement;

    public Variable (String nname, FunctionalBlock block){
        name = nname;
        placement = block;
    }

    public int getValue (){
        Integer value = placement.getVariableValue(name);
        if (value == null); // some logging
        return value;
    }
}
