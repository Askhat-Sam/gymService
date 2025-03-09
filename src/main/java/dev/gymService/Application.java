package dev.gymService;

import dev.gymService.configuration.AppConfig;
import dev.gymService.dao.interfaces.TrainerRepository;
import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.service.interfaces.TrainingService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        TraineeService traineeService = context.getBean("traineeServiceImpl", TraineeService.class);
        TrainerService trainerService = context.getBean("trainerServiceImpl", TrainerService.class);
        TrainingService trainingService = context.getBean("trainingServiceImpl", TrainingService.class);

        //############################## TRAINEE #################################################
//        // Creating new trainee
//        Trainee trainee = new Trainee();
//        trainee.setDateOfBirth(LocalDate.parse("2000-01-01"));
//        trainee.setAddress("Furmanova 2");
//        trainee.setFirstName("Andrey");
//        trainee.setLastName("Andreyev");
//        trainee.setIsActive(true);
//        trainee.setUserId(8L);
//        traineeService.createTrainee(trainee);

        // Getting trainee by id
        System.out.println("Getting trainee by id: " + traineeService.getTraineeById(3L, "Andrey.Andreyev", "j7hU76gt758"));

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

        // Get trainings list by trainee Username
//        System.out.println("Trainings list of trainee: " + traineeService.getTraineeTrainingList("Ivan.Ivanov",
//                "2025-02-27", "2025-02-28", "Vladislav.Bekmeev"));

        // Get trainer list not assigned to trainer by trainee userName
//        System.out.println("Not assigned trainers list: " + traineeService.getNotAssignedTrainers("Ivan.Ivanov"));

        // Get trainer list  trainee userName
//        System.out.println("Trainers list by traineeUserName: " + traineeService.getTraineeByUserName("Denis.Denisov").getTrainers());

        // Update trainers list by traineeUserName
//        List<Trainer> trainers = traineeService.getTraineeByUserName("Denis.Denisov").getTrainers();
//        System.out.println("Trainers list by traineeUserName BEFORE: " + trainers);
//        System.out.println();
//        trainers.remove(1);
//        System.out.println(trainers);
//        traineeService.updateTrainersList("Denis.Denisov", trainers);
//        System.out.println();
//        System.out.println("Trainers list by traineeUserName AFTER: " + trainers);




            // Delete trainers list by traineeUserName
//        traineeService.deleteTraineeByUserName("Ivan.Ivanov");



        //############################## TRAINER #################################################

        // Creating new trainer
//        Trainer trainer = new Trainer();
//        trainer.setSpecialization(1L);
//        trainer.setFirstName("Sergey");
//        trainer.setLastName("Sergushin");
//        trainer.setIsActive(true);
//        trainer.setUserId(7L);
//        trainerService.createTrainer(trainer);

        // Getting trainer by id
//        System.out.println(trainerService.getTrainerById(4L));

        // Getting trainer by userName
//        System.out.println("Trainings list: " + trainerService.getTrainerByUserName("Vladislav.Bekmeev"));

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
//        System.out.println("Trainings list of trainer: " + trainerService.getTrainerTrainingList("Maksim.Maksimov",
//                "2025-01-03", "2025-02-01", "Andrey.Andreyev"));

        // Get trainee list  trainer userName
//        System.out.println("Trainees list by trainerUserName: " + trainerService.getTrainerByUserName("Sergey.Sergeyev").getTrainees());





        //############################## TRAINING #################################################
//        Add new training
//        Trainer trainer = trainerService.getTrainerById(6L);
//        Trainee trainee = traineeService.getTraineeById(3L);
//
//
//            Training training = new Training();
//            training.setTrainingId(7L);
//            training.setTraineeId(3L);
//            training.setTrainerId(6L);
//            training.setTrainingName("Cardio");
//            training.setTrainingTypeId(3L);
//            training.setTrainingDate(LocalDate.parse("2025-03-09"));
//            training.setTrainingDuration(61L);
//            training.setTrainee(trainee);
//            training.setTrainer(trainer);
//
//        System.out.println("New training has been added: " + trainingService.addTraining(training));




    }
}
