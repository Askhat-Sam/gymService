package dev.gymService;

import dev.gymService.configuration.AppConfig;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.service.interfaces.TrainerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        TraineeService traineeService = context.getBean("traineeServiceImpl", TraineeService.class);
        TrainerService trainerService = context.getBean("trainerServiceImpl", TrainerService.class);

//        // Creating new trainee
//        Trainee trainee = new Trainee();
//        trainee.setDateOfBirth(LocalDate.parse("2000-01-01"));
//        trainee.setAddress("Furmanova 2");
//
//        trainee.setFirstName("Andrey");
//        trainee.setLastName("Andreyev");
//        trainee.setUserName("Andrey.Andreyev1");
//        trainee.setPassword("1234567890");
//        trainee.setIsActive(true);
//        trainee.setUserId(4L);
//        traineeService.createTrainee(trainee);

        // Getting trainee by id
//        System.out.println(traineeService.getTraineeById(4L));

        // Getting trainer by userName
//        System.out.println(traineeService.getTraineeByUserName("Andrey.Andreyev"));

        // Change trainee's password
//        traineeService.changeTraineePassword("Andrey.Andreyev", "987654321");
//        System.out.println("Trainee with changed password: " + traineeService.getTraineeByUserName("Andrey.Andreyev"));

//        // Update trainee
//        Trainee trainee = new Trainee();
//        trainee.setDateOfBirth(LocalDate.parse("9999-02-01"));
//        trainee.setAddress("Furmanova 666");
//
//        trainee.setFirstName("Denis");
//        trainee.setLastName("Denisov");
//        trainee.setUserName("Denis.Denisov");
//        trainee.setPassword("000111111111");
//        trainee.setIsActive(false);
//        traineeService.updateTrainee(trainee);

        // Getting trainer by userName
//        traineeService.deleteTraineeByUserName("Andrey.Andreyev");

        // Change trainee status
//        traineeService.changeTraineeStatus("Denis.Denisov");

        //###############################################################################

        // Creating new trainer
//        Trainer trainer = new Trainer();
//        trainer.setSpecialization(1L);
//        trainer.setFirstName("Andrey");
//        trainer.setLastName("Andreyev");
//        trainer.setUserName("Andrey.Andreyev4");
//        trainer.setPassword("1234567890");
//        trainer.setIsActive(true);
//        trainer.setUserId(4L);
//        trainerService.createTrainer(trainer);

        // Getting trainer by id
//        System.out.println(trainerService.getTrainerById(4L));

        // Getting trainer by userName
        System.out.println(trainerService.getTrainerByUserName("Maksim.Maksimov").getTrainings());

        // Change trainer's password
//        trainerService.changeTrainerPassword("Vladislav.Bekmeev", "987654321");
//        System.out.println("Trainer with changed password: " + trainerService.getTrainerByUserName("Vladislav.Bekmeev"));

        // Update trainee
//        Trainer trainer = new Trainer();
//        trainer.setSpecialization(2L);
//        trainer.setFirstName("Denis");
//        trainer.setLastName("Denisov");
//        trainer.setUserName("Sergey.Sergeyev");
//        trainer.setPassword("66666666");
//        trainer.setIsActive(false);
//        trainerService.updateTrainer(trainer);

        // Change trainer status
//        trainerService.changeTrainerStatus("Maksim.Maksimov");


        // Get trainings list by trainer Username
//        trainerService.getTrainerTrainings("Maksim.Maksimov");



    }
}
