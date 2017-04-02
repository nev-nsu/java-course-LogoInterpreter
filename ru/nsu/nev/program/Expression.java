package ru.nsu.nev.program;

import ru.nsu.nev.program.operators.BinaryOperator;
import ru.nsu.nev.program.primitives.Number;
import ru.nsu.nev.program.primitives.Primitive;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Expression {

    private final List<Primitive> objects;
    private final List<BinaryOperator> subjects;

    public Expression(ArrayList<Primitive> nobjects, ArrayList<BinaryOperator> nsubjects) {
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
