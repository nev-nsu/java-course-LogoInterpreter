package logo.program.operators;


import logo.Interpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * Create an operator by it's one-symbol signature.
 */
public class OperatorsFactory {
    // well, maybe i should do interface "operator" and create realization "binaryOperator" and "unitaryOperator"
    // but i don't think thar unitary operators are really necessary

    private static final Map<String, Class> table = new HashMap<String, Class>() {
        {
            put("+", Addition.class);
            put("-", Subtraction.class);
            put("*", Multiplication.class);
            put("/", Division.class);
            Interpreter.logger.info("operators factory has been initialized");
        }
    };

    /**
     * @return Command, if the command is exist, else 'null'
     */
    public static BinaryOperator create(String signature) {
        try {
            Class operatorClass = table.get(signature);
            return (BinaryOperator) operatorClass.newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
