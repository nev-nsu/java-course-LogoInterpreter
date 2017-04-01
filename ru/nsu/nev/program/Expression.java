package ru.nsu.nev.program;

import ru.nsu.nev.program.operators.BinaryOperator;
import ru.nsu.nev.program.primitives.Number;
import ru.nsu.nev.program.primitives.Primitive;

import java.util.ArrayList;
import java.util.Iterator;

public class Expression {

    private Primitive[] objects;
    private ArrayList<BinaryOperator> subjects;

    public Expression (ArrayList<Primitive> nobjects, ArrayList<BinaryOperator> nsubjects){
        objects = nobjects.toArray(new Primitive[0]);
        subjects = nsubjects;
    }

    public int calculate(){
        int res = objects[0].getValue();
        Iterator<BinaryOperator> it = subjects.iterator();
        for (int i = 0; i < objects.length; i++)
            if (objects[i] == null){
                res = it.next().apply(objects[i-2], objects[i-1]);
                objects[i] = new Number(res);
            }
        return res;
    }
}
