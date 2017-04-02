package logo;

import logo.program.FunctionalBlock;
import logo.program.LogicalError;
import logo.program.SyntaxError;
import logo.program.commands.Command;
import logo.program.commands.CommandsFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.InputStream;
import java.util.Properties;

import static java.lang.ClassLoader.getSystemClassLoader;


public class Interpreter {

    public static final FunctionalBlock mainBlock = new FunctionalBlock(true, null);

    public static final Logger logger = Logger.getLogger(Interpreter.class);

    private static void loggerInit(String inputFileName) {
        Properties p = new Properties();
        try (InputStream input = getSystemClassLoader().getResourceAsStream(inputFileName)) {
            p.load(input);
            PropertyConfigurator.configure(p);
        } catch (Exception e) {
            System.err.println("Logger wasn't configured: IO error");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void init(String[] args) throws SyntaxError {
        loggerInit("logger.properties");
        logger.info("PROGRAM STARTED");

        if (args.length != 1) {
            logger.error("wrong number of arguments: " + args.length);
            System.out.println("Usage: interpreter FILENAME");
            System.exit(0);
        }
        Parser.init(args[0]);

        CommandsFactory.init("commands.properties");
        logger.info("EXECUTION STARTED");
    }

    public static void main(String[] args) throws SyntaxError, LogicalError {
        init(args);
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
