package dev.gymService;

import dev.gymService.configuration.AppConfig;
import dev.gymService.model.Trainee;
import dev.gymService.model.User;
import dev.gymService.service.interfaces.TraineeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        TraineeService traineeService = context.getBean("traineeServiceImpl", TraineeService.class);


        // Creating new trainee
        Trainee trainee = new Trainee();
        trainee.setDateOfBirth(LocalDate.parse("2000-01-01"));
        trainee.setAddress("Furmanova 2");

        trainee.setFirstName("Andrey");
        trainee.setLastName("Andreyev");
        trainee.setUserName("Andrey.Andreyev6");
        trainee.setPassword("1234567890");
        trainee.setIsActive(true);
//        traineeService.createTrainee(trainee);

        // Getting trainee by id
        System.out.println(traineeService.getTraineeById(2L));
    }
}
