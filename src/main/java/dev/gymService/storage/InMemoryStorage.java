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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class InMemoryStorage {
    private final Map<Long, Trainee> traineeStorage = new HashMap<>();
    private final Map<Long, Trainer> trainerStorage = new HashMap<>();
    private final Map<Long, Training> trainingStorage = new HashMap<>();
    private static final Logger logger = FileLogger.getLogger(TraineeDAO.class);

    public Map<Long, Trainee> getTraineeStorage() {
        return traineeStorage;
    }

    public Map<Long, Trainer> getTrainerStorage() {
        return trainerStorage;
    }

    public Map<Long, Training> getTrainingStorage() {
        return trainingStorage;
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
                    Trainee trainee = new Trainee();
                    trainee.setUserId(Long.valueOf(fields[1]));
                    trainee.setFirstName(fields[2]);
                    trainee.setLastName(fields[3]);
                    trainee.setLastName(fields[4]);
                    trainee.setPassword(fields[5]);
                    trainee.setActive(Boolean.valueOf(fields[6]));
                    trainee.setDateOfBirth(LocalDate.parse(fields[7]));
                    trainee.setAddress(fields[8]);
                    traineeStorage.put(trainee.getUserId(), trainee);
                } else if (fields[0].equals("trainer")) {
                    Trainer trainer = new Trainer();
                    trainer.setUserId(Long.valueOf(fields[1]));
                    trainer.setFirstName(fields[2]);
                    trainer.setLastName(fields[3]);
                    trainer.setUserName(fields[4]);
                    trainer.setPassword(fields[5]);
                    trainer.setActive(Boolean.valueOf(fields[6]));
                    trainer.setSpecialization(new TrainingType(fields[7]));
                    trainerStorage.put(trainer.getUserId(), trainer);
                } else if (fields[0].equals("training")) {
                    Training training = new Training();
                    training.setTrainerId(Long.parseLong(fields[1]));
                    training.setTrainerId(Long.parseLong(fields[2]));
                    training.setTrainingName(fields[3]);
                    training.setTrainingType(new TrainingType(fields[4]));
                    training.setTrainingDate(LocalDate.parse(fields[5]));
                    training.setTrainingDuration(Long.parseLong(fields[6]));
                    trainingStorage.put(training.getTrainingId(), training);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException has been thrown while working with file initialData.txt");
            throw new RuntimeException(e.getMessage());
        }
    }
}
