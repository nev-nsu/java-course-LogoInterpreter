package ru.nsu.nev.program.commands;

import ru.nsu.nev.Interpreter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.ClassLoader.getSystemClassLoader;

public class CommandsFactory {

    private static Properties property = null;

    public static void init (String configurationFileName){
        try (InputStream input = getSystemClassLoader().getResourceAsStream(configurationFileName)) {
            property = new Properties();
            property.load(input);
        } catch (IOException e) {
            Interpreter.logger.error("IO error");
            e.printStackTrace();
            System.exit(0);
        }
        Interpreter.logger.info ("commands factory has been initialized");
    }

    public static Command create (String name){
        try {
            Class commandClass = Class.forName(property.getProperty(name), false, getSystemClassLoader());
            Command cmd = (Command) commandClass.newInstance();
            return cmd;
        } catch (Exception e) {
            return null;
        }
    }

}
