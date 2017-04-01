package ru.nsu.nev.program.commands;

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
            e.printStackTrace();
        }
    }

    public static Command create (String name){
        try {
            Class commandClass = Class.forName(property.getProperty(name), false, getSystemClassLoader());
            return (Command) commandClass.newInstance();
        } catch (Exception e) {
            return null;
        }
    }

}
