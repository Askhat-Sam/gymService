package dev.gymService.storage;

import dev.gymService.dao.TraineeDAO;
import dev.gymService.model.*;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.service.interfaces.TrainingService;
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
import java.util.stream.Collectors;

@Component
public class StorageInitializer {
    private TraineeService traineeService;
    private TrainerService trainerService;
    private TrainingService trainingService;
    private static final Logger logger = FileLogger.getLogger(TraineeDAO.class);

    @Autowired
    public void setTraineeService(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @Autowired
    public void setTrainerService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Autowired
    public void setTrainingService(TrainingService trainingService) {
        this.trainingService = trainingService;
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
                    trainee.setFirstName(fields[1]);
                    trainee.setLastName(fields[2]);
                    trainee.setUserName(UserInformationUtility.generateUserName(fields[1], fields[2],
                            traineeService.getAllTrainee().stream().map(User::getUserName).collect(Collectors.toList())));
                    trainee.setPassword(UserInformationUtility.generatePassword());
                    trainee.setActive(Boolean.valueOf(fields[3]));
                    trainee.setDateOfBirth(LocalDate.parse(fields[4]));
                    trainee.setAddress(fields[5]);
                    traineeService.createTrainee(trainee);
                } else if (fields[0].equals("trainer")) {
                    Trainer trainer = new Trainer();
                    trainer.setFirstName(fields[1]);
                    trainer.setLastName(fields[2]);
                    trainer.setUserName(UserInformationUtility.generateUserName(fields[1], fields[2],
                            trainerService.getAllTrainers().stream().map(User::getUserName).collect(Collectors.toList())));
                    trainer.setPassword(UserInformationUtility.generatePassword());
                    trainer.setActive(Boolean.valueOf(fields[3]));
                    trainer.setSpecialization(fields[4]);
                    trainerService.createTrainer(trainer);
                } else if (fields[0].equals("training")) {
                    Training training = new Training();
                    training.setTrainerId(Long.parseLong(fields[1]));
                    training.setTrainingName(fields[2]);
                    training.setTrainingType(new TrainingType(fields[3]));
                    training.setTrainingDate(LocalDate.parse(fields[4]));
                    training.setTrainingDuration(Long.parseLong(fields[5]));
                    trainingService.createTraining(training);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException has been thrown while working with file initialData.txt");
            throw new RuntimeException(e.getMessage());
        }
    }
}
