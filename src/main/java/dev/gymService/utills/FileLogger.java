package dev.gymService.utills;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FileLogger {
    public static void log(Logger logger, String message){
        FileHandler fileHandler;

        try {
            fileHandler = new FileHandler("log.txt");
            logger.addHandler(fileHandler);

            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.info(message);

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }
}
