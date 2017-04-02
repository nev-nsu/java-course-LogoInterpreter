package ru.nsu.nev;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import ru.nsu.nev.program.LogicalError;
import ru.nsu.nev.program.SyntaxError;
import ru.nsu.nev.program.commands.Command;
import ru.nsu.nev.program.FunctionalBlock;
import ru.nsu.nev.program.commands.CommandsFactory;

import java.io.*;
import java.util.Properties;

import static java.lang.ClassLoader.getSystemClassLoader;


public class Interpreter {

    public static final FunctionalBlock mainBlock = new FunctionalBlock(true);

    public static final Logger logger = Logger.getLogger(Interpreter.class);

    private static void loggerInit(String inputFileName) {
        Properties p = new Properties();
        try (InputStream input = getSystemClassLoader().getResourceAsStream(inputFileName)) {
            p.load(input);
            PropertyConfigurator.configure(p);
        } catch (IOException e) {
            System.err.println("Logger wasn't configured: IO error");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SyntaxError, LogicalError {
        loggerInit("resources/logger.properties");
        logger.info("PROGRAM STARTED");

        if (args.length != 1) {
            logger.error("wrong number of arguments: " + args.length);
            System.out.println("Usage: interpreter FILENAME");
            System.exit(0);
        }
        Parser.init(args[0]);

        CommandsFactory.init("resources/commands.properties");
        logger.info("EXECUTION STARTED");
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
