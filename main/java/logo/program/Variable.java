package logo.program;


/**
 * A pair (name, value). Only integer type is supported.
 */
public class Variable {
    private final String name;
    // We have only 'int' variables, so nor field 'type', neither universal field 'value'
    private int value;

    /**
     * Initialize (name = nname, value = 0).
     * Variable name should be from letters only.
     */
    public Variable(String nname) {
        name = nname;
        value = 0;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int nvalue) {
        value = nvalue;
    }
}
