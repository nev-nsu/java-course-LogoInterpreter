package logo.program;

import logo.program.operators.BinaryOperator;
import logo.program.primitives.Number;
import logo.program.primitives.Primitive;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Expression {

    private final List<Primitive> objects;
    private final List<BinaryOperator> subjects;

    public Expression(List<Primitive> nobjects, List<BinaryOperator> nsubjects) {
        objects = nobjects;
        subjects = nsubjects;
    }

    public int calculate() throws SyntaxError {
        Stack<Primitive> VMStack = new Stack<>();
        int res = objects.get(0).getValue
                ();
        Iterator<BinaryOperator> it = subjects.iterator();
        for (Primitive i : objects)
            if (i == null) {
                Primitive right = VMStack.pop();
                Primitive left = VMStack.pop();
                res = it.next().apply(left, right);
                VMStack.push(new Number(res));
            } else
                VMStack.push(i);
        return res;
    }
}
