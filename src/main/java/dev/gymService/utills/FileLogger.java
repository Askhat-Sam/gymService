package dev.gymService.utills;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FileLogger {
    public static Logger getLogger(Class<?> logClass){
        Logger logger = Logger.getLogger(logClass.getName());

        if (logger.getHandlers().length == 0) {
            try {
                FileHandler fileHandler = new FileHandler("logs.txt", 100 * 1024 * 1024, 5, true);
                fileHandler.setFormatter(new SimpleFormatter());
                logger.addHandler(fileHandler);
                logger.setUseParentHandlers(false); // Prevent logs from duplicating to console
            } catch (IOException e) {
                logger.log(Level.INFO, "Exception has been thrown: " + e.getMessage());
            }
        }
        return logger;
    }
}
