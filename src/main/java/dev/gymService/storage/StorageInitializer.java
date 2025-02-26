package dev.gymService.storage;

import dev.gymService.dao.TraineeDAO;
import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.model.TrainingType;
import dev.gymService.utills.FileLogger;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class StorageInitializer {
    private final InMemoryStorage storage;
    private static final Logger logger = FileLogger.getLogger(TraineeDAO.class);

    public StorageInitializer(InMemoryStorage storage) {
        this.storage = storage;
    }

    @PostConstruct
    public void initialize() {
        Properties prop = new Properties();
        try (InputStream input = Files.newInputStream(Paths.get("src/main/resources/application.properties"))) {
            prop.load(input);
            String storageFile = prop.getProperty("storage.file.path");
            BufferedReader reader = new BufferedReader(new FileReader(storageFile));
            logger.log(Level.INFO, storageFile + " file has been accessed");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields[0].equals("trainee")) {
                    storage.getTraineeStorage().put(Long.parseLong(fields[1]), new Trainee(Long.parseLong(fields[1]),
                            fields[2], fields[3], fields[4], fields[5], Boolean.valueOf(fields[6]),
                            LocalDate.parse(fields[7]), fields[8]));
                    logger.log(Level.INFO, "New trainee with id [" + fields[1] + "] has been added to trainees DB");
                } else if (fields[0].equals("trainer")) {
                    storage.getTrainerStorage().put(Long.parseLong(fields[1]), new Trainer(fields[2], fields[3],
                            fields[4], fields[5], fields[6], Boolean.valueOf(fields[7]), fields[8]));
                    logger.log(Level.INFO, "New trainer with id [" + fields[1] + "] has been added to trainers DB");
                } else if (fields[0].equals("training")) {
                    storage.getTrainingStorage().put(Long.parseLong(fields[1]), new Training(Long.parseLong(fields[2]),
                            Long.parseLong(fields[3]), fields[4], new TrainingType(fields[5]), LocalDate.parse(fields[6]),
                            Long.parseLong(fields[7])));
                    logger.log(Level.INFO, "New training with id [" + fields[1] + "] has been added to trainings DB");
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException has been thrown while working with initialData.txt");
        }
    }
}
