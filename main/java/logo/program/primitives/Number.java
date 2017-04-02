package logo.program.primitives;

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
