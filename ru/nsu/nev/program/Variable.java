package ru.nsu.nev.program;

public class Variable {
    private String name;
    public String getName () { return name;}

    // We have only 'int' variables, so nor field 'type', neither universal field 'value'
    private int value;
    public int getValue() { return value;}
    public void setValue(int nvalue) { value = nvalue;}

    public Variable (String nname){
        name = nname;
        value = 0;
    }
}
