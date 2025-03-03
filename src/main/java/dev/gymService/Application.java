package dev.gymService;

import dev.gymService.configuration.AppConfig;
import dev.gymService.dao.HibernateConfig;
import dev.gymService.model.Trainee;
import dev.gymService.service.interfaces.TraineeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class, HibernateConfig.class);
        TraineeService traineeService = context.getBean("traineeServiceImpl", TraineeService.class);

        //Creating trainee by ID
//        User user = new User();
//        user.setFirstName("a");
//        user.setLastName("s");
//        user.setUserName("a.s1");
//        user.setPassword("1234567890");  // Set a password or encode it if needed
//        user.setIsActive(true);
//        Trainee trainee = new Trainee();
//        trainee.setDateOfBirth(LocalDate.parse("2000-01-01"));
//        trainee.setAddress("Furmanova 2");
//        trainee.setUser(user); // Link Trainee to User
//        traineeService.createTrainee(trainee);



        // Getting trainee by ID
        Trainee trainee1 = traineeService.getTraineeById(3L);
        System.out.println("Getting trainee by ID: " + trainee1);

        // Getting trainee by userName
        Trainee trainee2 = traineeService.getTraineeByUserName("Ivan.Ivanov");
        System.out.println("Getting trainee by userName: " + trainee2);


    }
}
