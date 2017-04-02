package logo.program.operators;

import logo.program.FunctionalBlock;

/**
 * Open bracket. Uses in reverse Polish notation.
 *
 * @see logo.Parser#getExpression(FunctionalBlock)
 */
public class Bracket implements Priority {

    public int getPriority() {
        return 0;
    }

}
