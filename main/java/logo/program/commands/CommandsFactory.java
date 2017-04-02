package logo.program.commands;

import logo.Interpreter;

import java.io.InputStream;
import java.util.Properties;

import static java.lang.ClassLoader.getSystemClassLoader;

/**
 * Create a command by name.
 */
public class CommandsFactory {

    private static Properties property = null;

    /**
     * Load hashTable of (command_name, Command.class).
     * Command name should be from letters only.
     *
     * @param configurationFileName name of .property file
     */
    public static void init(String configurationFileName) {
        try (InputStream input = getSystemClassLoader().getResourceAsStream(configurationFileName)) {
            property = new Properties();
            property.load(input);
        } catch (Exception e) {
            Interpreter.logger.error("IO error");
            e.printStackTrace();
            System.exit(0);
        }
        Interpreter.logger.info("commands factory has been initialized");
    }

    /**
     * @return Command, if the command is exist, else 'null'
     */
    public static Command create(String name) {
        try {
            Class commandClass = Class.forName(property.getProperty(name), false, getSystemClassLoader());
            return (Command) commandClass.newInstance();
        } catch (Exception e) {
            return null;
        }
    }

}
