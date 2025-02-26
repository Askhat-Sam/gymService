package dev.gymService.storage;

import dev.gymService.dao.TraineeDAO;
import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.model.TrainingType;
import dev.gymService.utills.FileLogger;
import dev.gymService.utills.UserInformationUtility;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
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
    private InMemoryStorage storage;
    private static final Logger logger = FileLogger.getLogger(TraineeDAO.class);
    @Autowired
    public void setStorageInitializer(InMemoryStorage storage){
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
                    long traineeId = UserInformationUtility.generateTraineeId();
                    String traineePassword = UserInformationUtility.generatePassword();
                    String traineeUserName = UserInformationUtility.generateUserName(fields[1], fields[2], traineeId);
                    storage.getTraineeStorage().put(traineeId, new Trainee(traineeId,
                            fields[1], fields[2], traineeUserName, traineePassword, Boolean.valueOf(fields[3]),
                            LocalDate.parse(fields[4]), fields[5]));
                    logger.log(Level.INFO, "New trainee with id [" + traineeId + "] has been added to trainees DB");
                } else if (fields[0].equals("trainer")) {
                    long trainerId = UserInformationUtility.generateTrainerId();
                    String trainerPassword = UserInformationUtility.generatePassword();
                    String trainerUserName = UserInformationUtility.generateUserName(fields[1], fields[2], trainerId);
                    storage.getTrainerStorage().put(trainerId, new Trainer(trainerId, fields[1],fields[2],
                            trainerUserName, trainerPassword, Boolean.valueOf(fields[3]), fields[4]));
                    logger.log(Level.INFO, "New trainer with id [" + trainerId + "] has been added to trainers DB");
                } else if (fields[0].equals("training")) {
                    long trainingId = UserInformationUtility.generateTrainingId();
                    storage.getTrainingStorage().put(trainingId, new Training(trainingId,
                            Long.parseLong(fields[1]), fields[2], new TrainingType(fields[3]), LocalDate.parse(fields[4]),
                            Long.parseLong(fields[5])));
                    logger.log(Level.INFO, "New training with id [" + trainingId + "] has been added to trainings DB");
                }


            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException has been thrown while working with file initialData.txt");
            throw new RuntimeException(e.getMessage());
        }
        System.out.println("Trainees:" + storage.getTraineeStorage());
        System.out.println("Trainers:" + storage.getTrainerStorage());
        System.out.println("Trainings:" + storage.getTrainingStorage());
    }
}
