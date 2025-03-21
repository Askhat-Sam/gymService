package dev.gymService;

import dev.gymService.configuration.AppConfig;
import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.service.interfaces.TrainerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        TraineeService traineeService = context.getBean("traineeServiceImpl", TraineeService.class);
        TrainerService trainerService = context.getBean("trainerServiceImpl", TrainerService.class);

        // Getting trainee by id
        System.out.println("----Getting trainee by id----");
        System.out.println("Getting trainee by id: " + traineeService.getTraineeById(1L, "Ivan.Ivanov", "12hU76gt5("));

        // Create new trainee
        System.out.println("----Creating new trainee----");
        Trainee trainee = new Trainee();
        trainee.setFirstName("Andrey");
        trainee.setLastName("Oliya");
        trainee.setIsActive(false);
        trainee.setDateOfBirth(LocalDate.parse("2000-09-09"));
        trainee.setAddress("Panfilova 139");
        traineeService.createTrainee(trainee);

        // Create new trainer
        System.out.println("----Create new trainer----");
        Trainer trainer = new Trainer();
        trainer.setFirstName("Maksim");
        trainer.setLastName("Avdeev");
        trainer.setIsActive(false);
        trainer.setSpecialization(3L);
        trainerService.createTrainer(trainer);

        // Create new trainee with suffix 1
        System.out.println("----Creating new trainee----");
        Trainee trainee1 = new Trainee();
        trainee1.setFirstName("Andrey");
        trainee1.setLastName("Oliya");
        trainee1.setIsActive(false);
        trainee1.setDateOfBirth(LocalDate.parse("2000-09-09"));
        trainee1.setAddress("Panfilova 139");
        traineeService.createTrainee(trainee1);

        // Create new trainer with suffix 1
        System.out.println("----Create new trainer----");
        Trainer trainer1 = new Trainer();
        trainer1.setFirstName("Maksim");
        trainer1.setLastName("Avdeev");
        trainer1.setIsActive(false);
        trainer1.setSpecialization(3L);
        trainerService.createTrainer(trainer1);
    }
}