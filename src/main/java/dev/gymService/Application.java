package dev.gymService;

import dev.gymService.configuration.AppConfig;
import dev.gymService.model.Trainee;
import dev.gymService.model.User;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.service.interfaces.TrainingService;
import dev.gymService.utills.UserInformationUtility;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        TraineeService traineeService = context.getBean("traineeServiceImpl", TraineeService.class);
        TrainerService trainerService = context.getBean("trainerServiceImpl", TrainerService.class);
        TrainingService trainingService = context.getBean("trainingServiceImpl", TrainingService.class);

        // Print the list of initial trainees, trainers, trainings
        System.out.println("The list of initialized trainees: " + traineeService.getAllTrainee());
        System.out.println("The list of initialized trainers: " +trainerService.getAllTrainers());
        System.out.println("The list of initialized trainings: " +trainingService.getAllTrainings());

        // Add new trainee
        Trainee trainee = new Trainee();
        trainee.setFirstName("Andrey");
        trainee.setLastName("Andreyev");
        trainee.setActive(true);
        trainee.setDateOfBirth(LocalDate.parse("2000-01-01"));
        trainee.setAddress("Furmanova 2");
        traineeService.createTrainee(trainee);

        // Get the created new trainee
        System.out.println("Getting trainee with id 5:" + traineeService.getTraineeById(5L));

        // Add new trainee
        Trainee trainee2 = new Trainee();
        trainee2.setFirstName("Andrey");
        trainee2.setLastName("Andreyev");
        trainee2.setActive(true);
        trainee2.setDateOfBirth(LocalDate.parse("2000-01-01"));
        trainee2.setAddress("Furmanova 2");
        traineeService.createTrainee(trainee2);

        // Add new trainee
        Trainee trainee3 = new Trainee();
        trainee3.setFirstName("Andrey");
        trainee3.setLastName("Andreyev");
        trainee3.setActive(true);
        trainee3.setDateOfBirth(LocalDate.parse("2000-01-01"));
        trainee3.setAddress("Furmanova 2");
        traineeService.createTrainee(trainee3);

        System.out.println();
        System.out.println("All Trainee: " + traineeService.getAllTrainee());

        // Update trainee with id 5
        Trainee trainee1 = traineeService.getTraineeById(5L);
        trainee1.setAddress("Furmanova 222");

        traineeService.updateTrainee(trainee1);
        System.out.println("Updated address: " + traineeService.getTraineeById(5L).getAddress());

        // Delete trainee with id 5
        traineeService.deleteTrainee(5L);
        System.out.println("Getting trainee with id 5:" + traineeService.getTraineeById(5L));
    }
}
