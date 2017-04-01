package ru.nsu.nev.program.primitives;

public class Number implements Primitive{

    private int num;

    public Number (int number){
        num = number;
    }

    public int getValue (){
        return num;
    }
}
