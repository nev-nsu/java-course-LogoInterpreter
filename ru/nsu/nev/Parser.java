package ru.nsu.nev;

import ru.nsu.nev.program.Expression;
import ru.nsu.nev.program.FunctionalBlock;
import ru.nsu.nev.program.SyntaxError;
import ru.nsu.nev.program.commands.Command;
import ru.nsu.nev.program.commands.CommandsFactory;
import ru.nsu.nev.program.operators.BinaryOperator;
import ru.nsu.nev.program.operators.Bracket;
import ru.nsu.nev.program.operators.OperatorsFactory;
import ru.nsu.nev.program.operators.Priority;
import ru.nsu.nev.program.primitives.Number;
import ru.nsu.nev.program.primitives.Primitive;
import ru.nsu.nev.program.primitives.Variable;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class Parser {

    private static int numberOfLine = 0;
    public static int getNumberOfLine(){
        return numberOfLine;
    }

    private static BufferedReader input;
    private static String[] words;
    private static int currentWord;

    public static void init(String inputFileName) {
        try {
            input = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName), "UTF-8"));
        } catch (Exception e) {
            Interpreter.logger.error("IO error");
            e.printStackTrace();
            System.exit(0);
        }
        newLine();
    }

    public static Command getCommand() throws SyntaxError {
        return getCommand(true);
    }

    public static Command getCommand(boolean strictly) throws SyntaxError {
        String cmd_name = getNextWord();
        Command cmd = CommandsFactory.create (cmd_name);
        if (cmd == null && strictly)
            throw new SyntaxError ("command \"" + cmd_name + "\" doesn't exist");
        return cmd;
    }

    private enum TOKEN_TYPE {
        OPERATOR, OPEN_BRACKET, CLOSE_BRACKET, PRIMITIVE
    }

    private static void tryTransition (TOKEN_TYPE state, TOKEN_TYPE newState) throws SyntaxError {
        switch (newState){
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

    public static Expression getExpression(FunctionalBlock placement) throws SyntaxError {
        // Reverse Polish notation :(
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
                else{
                    if (stack.lastElement().getPriority() >= operator.getPriority())
                        subjects.add((BinaryOperator) stack.pop());
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
                                while (element.getClass() != Bracket.class){
                                    subjects.add((BinaryOperator)element);
                                    objects.add(null);
                                    element = stack.pop();
                                }
                            } catch (EmptyStackException e) {
                                throw new SyntaxError("incorrect expression");
                            }
                        } else {
                            while (!stack.empty()){
                                Priority element = stack.pop();
                                if (element.getClass() != Bracket.class) {
                                    subjects.add((BinaryOperator) element);
                                    objects.add(null);
                                }
                                else
                                    throw new SyntaxError("incorrect expression");
                            }
                            if (objects.isEmpty())
                                throw new SyntaxError("incorrect expression");
                            return new Expression(objects, subjects);
                        }
                    }
                }
                // Primitive onRead
                if (primitive != null) {
                    new_state = TOKEN_TYPE.PRIMITIVE;
                    objects.add(primitive);
                }
            }
            tryTransition(state, new_state);
            state = new_state;
        }
    }

    public static BinaryOperator getOperator (){
        BinaryOperator res = OperatorsFactory.create(getNextWord());
        if (res == null)
            currentWord--;
        return res;
    }

    private static Integer getNumber(){
        String word = getNextWord();
        Integer res;
        try {
            res = Integer.valueOf(word);
        }
        catch (NumberFormatException e){
            currentWord--;
            return null;
        }
        return res;
    }

    public static String getName (){
        try {
            Command test = getCommand(false);
            currentWord--;
            if (test != null) {
                return null;
            }
        }
        catch (Exception e){
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

    public static boolean skipWord (String needed_word, boolean strictly) throws SyntaxError {
        String word = getNextWord();
        if (needed_word.equals(word))
            return true;
        if (strictly)
            throw new SyntaxError (needed_word + " expected");
        currentWord--;
        return false;
    }

    public static boolean skipWord (String needed_word) throws SyntaxError {
        return skipWord(needed_word, false);
    }

    private static void newLine () {
        String line = null;
        try {
            numberOfLine++;
            line = input.readLine();
        } catch (IOException e) {
            Interpreter.logger.error("IO error");
            e.printStackTrace();
            System.exit(0);
        }
        line = line.replaceAll("([_\\W])", " $1 ");
        line = line.trim();
        if (line.isEmpty())
            newLine();
        else {
            words = line.split("\\s+");
            currentWord = 0;
        }
    }

    private static String getNextWord(){
        if (currentWord == words.length)
            newLine();
        return words[currentWord++];
    }
}
