package ru.nsu.nev.program.operators;


import ru.nsu.nev.Interpreter;

import java.util.HashMap;
import java.util.Map;

public class OperatorsFactory {
    // well, maybe i should do interface "operator" and create realization "binaryOperator" and "unitaryOperator"
    // but i don't think thar unitary operators are really necessary

    private static Map<String, Class> table = new HashMap<String, Class>(){
        {
            put("+", Addition.class);
            put("-", Substruction.class);
            put("*", Multiplication.class);
            put("/", Division.class);
            Interpreter.logger.info ("operators factory has been initialized");
        }
    };

    public static BinaryOperator create (String signature){
        try {
            Class operatorClass = table.get(signature);
            return (BinaryOperator) operatorClass.newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
