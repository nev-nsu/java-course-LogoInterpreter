package logo;

import logo.program.Expression;
import logo.program.FunctionalBlock;
import logo.program.SyntaxError;
import logo.program.commands.Command;
import logo.program.commands.CommandsFactory;
import logo.program.operators.BinaryOperator;
import logo.program.operators.Bracket;
import logo.program.operators.OperatorsFactory;
import logo.program.operators.Priority;
import logo.program.primitives.Number;
import logo.program.primitives.Primitive;
import logo.program.primitives.Variable;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Allow to works with semantic units, by realizing all low-level IO operations
 */
public class Parser {

    private static int numberOfLine = 0;
    private static BufferedReader input;
    private static String[] words;
    private static int currentWord;

    /**
     * How many lines was already read
     */
    public static int getNumberOfLine() {
        return numberOfLine;
    }

    /**
     * @param inputFileName name of the file with logo-program
     * @throws SyntaxError in case of empty file
     */
    public static void init(String inputFileName) throws SyntaxError {
        try {
            input = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName), "UTF-8"));
        } catch (Exception e) {
            Interpreter.logger.error("IO error");
            e.printStackTrace();
            System.exit(0);
        }
        numberOfLine = 0;
        newLine();
    }

    public static Command getCommand() throws SyntaxError {
        return getCommand(true);
    }

    /**
     * Try to read a command from the input
     *
     * @param strictly If 'true' then failure will cause SyntaxError. default value = 'true'
     * @return read Command if success, null in the other case
     * @throws SyntaxError if getting failed and strictly == 'true', or if end of the file was reached
     * @see logo.program.commands.Command
     */
    public static Command getCommand(boolean strictly) throws SyntaxError {
        String cmd_name = getNextWord();
        Command cmd = CommandsFactory.create(cmd_name);
        if (cmd == null && strictly)
            throw new SyntaxError("command \"" + cmd_name + "\" doesn't exist");
        return cmd;
    }

    private static void tryTransition(TOKEN_TYPE state, TOKEN_TYPE newState) throws SyntaxError {
        switch (newState) {
            case OPERATOR:
                if (state == TOKEN_TYPE.PRIMITIVE || state == TOKEN_TYPE.CLOSE_BRACKET)
                    return;
            case OPEN_BRACKET:
                if ((state == TOKEN_TYPE.OPERATOR) || (state == TOKEN_TYPE.OPEN_BRACKET))
                    return;
            case CLOSE_BRACKET:
                if ((state == TOKEN_TYPE.PRIMITIVE) || (state == TOKEN_TYPE.CLOSE_BRACKET))
                    return;
            case PRIMITIVE:
                if (state == TOKEN_TYPE.OPERATOR || state == TOKEN_TYPE.OPEN_BRACKET)
                    return;
            default:
        }
        throw new SyntaxError("incorrect expression");
    }

    /**
     * Try to read an expression using reverse Polish notation.
     * @param placement functional block in which context expression will be calculate
     * @throws SyntaxError if there no expression in input stream, or if end of the file was reached
     * @see Expression
     */
    public static Expression getExpression(FunctionalBlock placement) throws SyntaxError {
        // Reverse Polish notation
        ArrayList<Primitive> objects = new ArrayList<>();
        ArrayList<BinaryOperator> subjects = new ArrayList<>();
        Stack<Priority> stack = new Stack<>();
        TOKEN_TYPE state = TOKEN_TYPE.OPEN_BRACKET;

        while (true) {
            TOKEN_TYPE new_state = null;
            BinaryOperator operator = getOperator();
            if (operator != null) {
                new_state = TOKEN_TYPE.OPERATOR;
                if (stack.empty() || stack.lastElement().getClass() == Bracket.class)
                    stack.push(operator);
                else {
                    while (!stack.empty() && stack.lastElement().getPriority() >= operator.getPriority()) {
                        subjects.add((BinaryOperator) stack.pop());
                        objects.add(null);
                    }
                    stack.push(operator);
                }
            } else {
                Primitive primitive = null;
                Integer num = getNumber();
                if (num != null)
                    primitive = new Number(num);
                else {
                    String name = getName();
                    if (name != null)
                        primitive = new Variable(name, placement);
                    else  // if brackets
                    {
                        if (skipWord("(")) {
                            new_state = TOKEN_TYPE.OPEN_BRACKET;
                            stack.add(new Bracket());
                        } else if (skipWord(")")) {
                            new_state = TOKEN_TYPE.CLOSE_BRACKET;
                            try {
                                Priority element = stack.pop();
                                while (element.getClass() != Bracket.class) {
                                    subjects.add((BinaryOperator) element);
                                    objects.add(null);
                                    element = stack.pop();
                                }
                            } catch (EmptyStackException e) {
                                throw new SyntaxError("incorrect expression");
                            }
                        } else {
                            while (!stack.empty()) {
                                Priority element = stack.pop();
                                if (element.getClass() != Bracket.class) {
                                    subjects.add((BinaryOperator) element);
                                    objects.add(null);
                                } else
                                    throw new SyntaxError("incorrect expression");
                            }
                            if (objects.isEmpty())
                                throw new SyntaxError("incorrect expression");
                            return new Expression(objects, subjects);
                        }
                    }
                }
                // Primitive handler
                if (primitive != null) {
                    new_state = TOKEN_TYPE.PRIMITIVE;
                    objects.add(primitive);
                }
            }
            tryTransition(state, new_state);
            state = new_state;
        }
    }

    /**
     * Try to read an operator
     * @return BinaryOperator if reading was successfully, else 'null'
     * @throws SyntaxError if end of the file was reached
     */
    public static BinaryOperator getOperator() throws SyntaxError {
        BinaryOperator res = OperatorsFactory.create(getNextWord());
        if (res == null)
            currentWord--;
        return res;
    }

    /**
     * Try to read a decimal number
     * @return Integer if reading was successfully, else 'null'
     * @throws SyntaxError if end of the file was reached
     */
    private static Integer getNumber() throws SyntaxError {
        String word = getNextWord();
        Integer res;
        try {
            res = Integer.valueOf(word);
        } catch (NumberFormatException e) {
            currentWord--;
            return null;
        }
        return res;
    }

    /**
     * Try to read a name of a variable. Distinguish the name from a command.
     * @return String if reading was successfully, else 'null'
     * @throws SyntaxError if end of the file was reached
     */
    public static String getName() throws SyntaxError {
        try {
            Command test = getCommand(false);
            currentWord--;
            if (test != null) {
                return null;
            }
        } catch (Exception e) {
            Interpreter.logger.fatal("internal error");
            System.exit(1);
        }
        String word = getNextWord();
        for (int i = 0; i < word.length(); i++)
            if (!Character.isLetter(word.codePointAt(i))) {
                currentWord--;
                return null;
            }
        return word;
    }

    /**
     * @param strictly If 'true' then failure will cause SyntaxError. default value = 'false'
     * @return if skipping was successfully
     * @throws SyntaxError if skipping failed and strictly == 'true', or if end of the file was reached
     */
    public static boolean skipWord(String skipping_word, boolean strictly) throws SyntaxError {
        String word = getNextWord();
        if (skipping_word.equals(word))
            return true;
        if (strictly)
            throw new SyntaxError(skipping_word + " expected");
        currentWord--;
        return false;
    }

    public static boolean skipWord(String needed_word) throws SyntaxError {
        return skipWord(needed_word, false);
    }

    private static void newLine() throws SyntaxError {
        String line = null;
        try {
            numberOfLine++;
            line = input.readLine();
        } catch (IOException e) {
            Interpreter.logger.error("IO error");
            e.printStackTrace();
            System.exit(0);
        }
        if (line == null)
            throw new SyntaxError("unexpected end of the file");
        line = line.replaceAll("([_\\W])", " $1 ");
        line = line.trim();
        if (line.isEmpty())
            newLine();
        else {
            words = line.split("\\s+");
            currentWord = 0;
        }
    }

    private static String getNextWord() throws SyntaxError {
        if (currentWord == words.length)
            newLine();
        return words[currentWord++];
    }

    private enum TOKEN_TYPE {
        OPERATOR, OPEN_BRACKET, CLOSE_BRACKET, PRIMITIVE
    }
}
