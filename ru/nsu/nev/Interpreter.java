package ru.nsu.nev;

import ru.nsu.nev.program.LogicalError;
import ru.nsu.nev.program.SyntaxError;
import ru.nsu.nev.program.commands.Command;
import ru.nsu.nev.program.FunctionalBlock;
import ru.nsu.nev.program.commands.CommandsFactory;

import java.io.*;


public class Interpreter {

    private static FunctionalBlock mainBlock;
    public static FunctionalBlock getMainBlock () {
        return mainBlock;
    }

    private static Canvas mainCanvas;
    public static Canvas getMainCanvas () {
        return mainCanvas;
    }

    private static void init(String inputFileName){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName), "UTF-8"));
            Parser.init(in);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(0);
            // TODO log
        }
        mainCanvas = new Canvas();
        CommandsFactory.init("resources/conf.ini");
    }

    public static void main(String[] args) throws SyntaxError, LogicalError {
	    if (args.length != 1)
        {
            // TODO log
            System.out.println("Help need be here");
        }
        init (args[0]);
        // create block
        mainBlock = new FunctionalBlock(true);
        readFunctionalBlock(mainBlock);
    }

    static public void readFunctionalBlock(FunctionalBlock block) throws SyntaxError, LogicalError {
        while (!block.isFinished) {
            Command cmd = Parser.getCommand();
            if (cmd == null) {
                continue;
            }
            cmd.onRead(block);
            if (block.isImmediatelyExecute)
                cmd.onExecute();
            else
                block.addCommand(cmd);
        }
    }

}
