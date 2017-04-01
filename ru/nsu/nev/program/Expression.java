package ru.nsu.nev.program;

import ru.nsu.nev.program.operators.BinaryOperator;
import ru.nsu.nev.program.primitives.Number;
import ru.nsu.nev.program.primitives.Primitive;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class Expression {

    private Primitive[] objects;
    private ArrayList<BinaryOperator> subjects;

    public Expression (ArrayList<Primitive> nobjects, ArrayList<BinaryOperator> nsubjects){
        objects = nobjects.toArray(new Primitive[0]);
        subjects = nsubjects;
    }

    public int calculate(){
        Stack<Primitive> VMStack = new Stack<>();
        int res = objects[0].getValue();
        Iterator<BinaryOperator> it = subjects.iterator();
        for (int i = 0; i < objects.length; i++)
            if (objects[i] == null){
                Primitive right = VMStack.pop();
                Primitive left = VMStack.pop();
                res = it.next().apply(left, right);
                VMStack.push(new Number(res));
            }
            else
                VMStack.push(objects[i]);
        return res;
    }
}
