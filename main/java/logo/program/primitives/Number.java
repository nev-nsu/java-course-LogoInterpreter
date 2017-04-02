package logo.program.primitives;

/**
 * Decimal number
 */
public class Number implements Primitive {

    private final int num;

    public Number(int number) {
        num = number;
    }

    @Override
    public int getValue() {
        return num;
    }
}
