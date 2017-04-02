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

/**
 * Initialization and main loop of interpretation
 */

public class Interpreter {

    /**
     * Implicit functional block, which contains all logo-programs.
     * Contains also all internal variables of a logo-program.
     */
    public static final FunctionalBlock mainBlock = new FunctionalBlock(true, null);

    public static final Logger logger = Logger.getLogger(Interpreter.class);

    /**
     * Logger initialization with .properties file.
     * The file must me included in sources.
     *
     * @param inputFileName filename of resource.
     */
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

    /**
     * Initialize logger and parser with default settings and given filename
     *
     * @param args must be one argument: name of a file with logo-program
     * @throws SyntaxError in case of empty file
     */
    public static void init(String[] args) throws SyntaxError {
        loggerInit("logger.properties");
        logger.info("PROGRAM STARTED");

        if (args.length != 1) {
            logger.error("wrong number of arguments: " + args.length);
            System.out.println("Usage: interpreter FILENAME");
            System.exit(0);
        }
        Parser.init(args[0]);
    }

    /**
     * Initilization and start reading and executing main functional block of a logo-program
     */
    public static void main(String[] args) {
        try {
            init(args);
            CommandsFactory.init("commands.properties");
            logger.info("EXECUTION STARTED");
            readFunctionalBlock(mainBlock);
        } catch (Exception e) {
            System.exit(0);
        }
    }

    /**
     * Read functional block, and, in case of immediately execution, execute it
     * @param block An initialized FunctionalBlock in which context commands will be store/executed
     * @see logo.program.FunctionalBlock
     * @see logo.program.FunctionalBlock#isImmediatelyExecute
     */
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
