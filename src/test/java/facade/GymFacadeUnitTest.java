package facade;

import dev.gymService.controller.GymFacade;
import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.model.TrainingType;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.service.interfaces.TrainingService;
import dev.gymService.utills.UserInformationUtility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GymFacadeUnitTest {
    @Mock
    TraineeService traineeService;
    @Mock
    TrainingService trainingService;

    @Mock
    TrainerService trainerService;

    @InjectMocks
    GymFacade gymFacade;

    @Test
    public void shouldCreateTrainee(){
        // Given
        Trainee trainee = new Trainee();
        trainee.setFirstName("Andrey");
        trainee.setLastName("Andreyev");
        trainee.setUserName(UserInformationUtility.generateUserName("Andrey","Andreyev",
                List.of("Ivan.Ivanov")));
        trainee.setActive(true);
        trainee.setDateOfBirth(LocalDate.parse("2000-01-01"));
        trainee.setAddress("Furmanova 2");
        trainee.setUserId(traineeService.generateTraineeId());
        trainee.setPassword(UserInformationUtility.generatePassword());

        when(traineeService.createTrainee(trainee)).thenReturn(trainee);

        // When
        Trainee createdTrainee = gymFacade.createTrainee(trainee);

        // Then
        assertNotNull(createdTrainee);
        assertEquals("Andrey.Andreyev", createdTrainee.getUserName());
        assertEquals(10, createdTrainee.getPassword().length());
        verify(traineeService, times(1)).createTrainee(trainee);
    }

    @Test
    public void shouldUpdateTrainee(){
        // Given
        Trainee trainee = new Trainee();
        trainee.setFirstName("Andrey");
        trainee.setLastName("Andreyev");
        trainee.setUserName(UserInformationUtility.generateUserName("Andrey","Andreyev", List.of("Ivan.Ivanov")));
        trainee.setActive(true);
        trainee.setDateOfBirth(LocalDate.parse("2000-01-01"));
        trainee.setAddress("Furmanova 2");
        trainee.setUserId(traineeService.generateTraineeId());
        trainee.setPassword(UserInformationUtility.generatePassword());

        when(traineeService.updateTrainee(trainee)).thenReturn(trainee);

        // When
        Trainee updatedTrainee = gymFacade.updateTrainee(trainee);

        // Then
        assertNotNull(updatedTrainee);
        assertEquals("Andrey.Andreyev", updatedTrainee.getUserName());
        assertEquals(10, updatedTrainee.getPassword().length());
        verify(traineeService, times(1)).updateTrainee(trainee);
    }

    @Test
    public void shouldDeleteTrainee(){
        // When
        gymFacade.deleteTrainee(1L);

        // Then
        verify(traineeService, times(1)).deleteTrainee(1L);
    }

    @Test
    public void shouldGetAllTrainees() {
        // Given
        Trainee trainee1 = new Trainee();
        trainee1.setFirstName("Andrey");
        trainee1.setLastName("Andreyev");
        trainee1.setUserName(UserInformationUtility.generateUserName("Andrey","Andreyev", List.of("Ivan.Ivanov")));
        trainee1.setActive(true);
        trainee1.setDateOfBirth(LocalDate.parse("2000-01-01"));
        trainee1.setAddress("Furmanova 2");
        trainee1.setUserId(traineeService.generateTraineeId());
        trainee1.setPassword(UserInformationUtility.generatePassword());

        Trainee trainee2 = new Trainee();
        trainee2.setFirstName("Ivan");
        trainee2.setLastName("Ivanov");
        trainee2.setUserName(UserInformationUtility.generateUserName("Ivan","Ivanov", List.of("Ivan.Ivanov")));
        trainee2.setActive(true);
        trainee2.setDateOfBirth(LocalDate.parse("1990-01-01"));
        trainee2.setAddress("Mailina 2");
        trainee2.setUserId(traineeService.generateTraineeId());
        trainee2.setPassword(UserInformationUtility.generatePassword());

        List<Trainee> trainees = Arrays.asList(trainee1, trainee2);
        when(traineeService.getAllTrainee()).thenReturn(trainees);

        // When
        List<Trainee> retrievedTrainees = gymFacade.getAllTrainees();

        // Then
        assertEquals(2, retrievedTrainees.size());
        verify(traineeService, times(1)).getAllTrainee();
    }

    @Test
    public void shouldGetTraineeById() {
        // Given
        Trainee trainee = new Trainee();
        trainee.setFirstName("Andrey");
        trainee.setLastName("Andreyev");
        trainee.setUserName(UserInformationUtility.generateUserName("Andrey","Andreyev", List.of("Ivan.Ivanov")));
        trainee.setActive(true);
        trainee.setDateOfBirth(LocalDate.parse("2000-01-01"));
        trainee.setAddress("Furmanova 2");
        trainee.setUserId(traineeService.generateTraineeId());
        trainee.setPassword(UserInformationUtility.generatePassword());

        when(traineeService.getTraineeById(1L)).thenReturn(trainee);

        // When
        Trainee retrievedTrainee = gymFacade.getTraineeById(1L);

        // Then
        assertNotNull(retrievedTrainee);
        verify(traineeService, times(1)).getTraineeById(1L);
    }

    // Trainer
    @Test
    public void shouldCreateNewTrainer() {
        // Given
        Trainer trainer = new Trainer();
        trainer.setUserId(trainerService.generateTrainerId());
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUserName(UserInformationUtility.generateUserName(trainer.getFirstName(), trainer.getLastName(), List.of("Ivan.Ivanov")));
        trainer.setPassword(UserInformationUtility.generatePassword());
        trainer.setActive(true);
        trainer.setSpecialization("swimming");

        when(trainerService.createTrainer(trainer)).thenReturn(trainer);

        // When
        Trainer createdTrainer = gymFacade.createTrainer(trainer);

        // Then
        assertNotNull(createdTrainer);
        assertEquals("Ivan.Ivanov", createdTrainer.getUserName());
        assertEquals(10, createdTrainer.getPassword().length());
        assertEquals("swimming", createdTrainer.getSpecialization());
        verify(trainerService, times(1)).createTrainer(trainer);
    }

    @Test
    public void shouldUpdateTrainer(){
        // Given
        Trainer trainer = new Trainer();
        trainer.setUserId(trainerService.generateTrainerId());
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUserName(UserInformationUtility.generateUserName(trainer.getFirstName(), trainer.getLastName(), List.of("Ivan.Ivanov")));
        trainer.setPassword(UserInformationUtility.generatePassword());
        trainer.setActive(true);
        trainer.setSpecialization("swimming");

        when(trainerService.updateTrainer(trainer)).thenReturn(trainer);

        // When
        Trainer updatedTrainer = gymFacade.updateTrainer(trainer);

        // Then
        assertNotNull(updatedTrainer);
        assertEquals("Ivan.Ivanov", updatedTrainer.getUserName());
        assertEquals(10, updatedTrainer.getPassword().length());
        verify(trainerService, times(1)).updateTrainer(trainer);
    }

    @Test
    public void shouldDeleteTrainer() {
        // When
        gymFacade.deleteTrainer(1L);

        // Then
        verify(trainerService, times(1)).deleteTrainer(1L);
    }

    @Test
    public void shouldGetAllTrainers() {
        // Given
        Trainer trainer = new Trainer();
        trainer.setUserId(trainerService.generateTrainerId());
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUserName(UserInformationUtility.generateUserName(trainer.getFirstName(), trainer.getLastName(), List.of("Ivan.Ivanov")));
        trainer.setPassword(UserInformationUtility.generatePassword());
        trainer.setActive(true);
        trainer.setSpecialization("swimming");

        Trainer trainer1 = new Trainer();
        trainer1.setUserId(trainerService.generateTrainerId());
        trainer1.setFirstName("Maksim");
        trainer1.setLastName("Maksimov");
        trainer1.setUserName(UserInformationUtility.generateUserName(trainer.getFirstName(), trainer.getLastName(), List.of("Ivan.Ivanov")));
        trainer1.setPassword(UserInformationUtility.generatePassword());
        trainer1.setActive(true);
        trainer1.setSpecialization("cardio");


        List<Trainer> trainees = Arrays.asList(trainer, trainer1);
        when(trainerService.getAllTrainers()).thenReturn(trainees);

        // When
        List<Trainer> retrievedTrainers = gymFacade.getAllTrainers();

        // Then
        assertEquals(2, retrievedTrainers.size());
        assertEquals(10, retrievedTrainers.get(1).getPassword().length());
        verify(trainerService, times(1)).getAllTrainers();
    }

    @Test
    public void shouldGetTrainerById() {
        // Given
        Trainer trainer = new Trainer();
        trainer.setUserId(trainerService.generateTrainerId());
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUserName(UserInformationUtility.generateUserName(trainer.getFirstName(), trainer.getLastName(), List.of("Ivan.Ivanov")));
        trainer.setPassword(UserInformationUtility.generatePassword());
        trainer.setActive(true);
        trainer.setSpecialization("swimming");

        when(trainerService.getTrainerById(1L)).thenReturn(trainer);

        // When
        Trainer retrievedTrainer = gymFacade.getTrainerById(1L);

        // Then
        assertNotNull(retrievedTrainer);
        assertEquals("Ivan.Ivanov", retrievedTrainer.getUserName());
        assertEquals(10, retrievedTrainer.getPassword().length());
        verify(trainerService, times(1)).getTrainerById(1L);
    }

    // Training
    @Test
    public void shouldCreateNewTraining() {
        // Given
        Training training = new Training();
        training.setTrainingId(trainingService.generateTrainingId());
        training.setTrainerId(1L);
        training.setTrainingName("Cardio");
        training.setTrainingType(new TrainingType("Cardio"));
        training.setTrainingDate(LocalDate.parse("2025-01-01"));
        training.setTrainingDuration(45L);

        when(trainingService.createTraining(training)).thenReturn(training);

        // When
        Training createdTraining = gymFacade.createTraining(training);

        // Then
        assertNotNull(createdTraining);
        assertNotNull(createdTraining.getTrainingId());
        assertEquals("Cardio", createdTraining.getTrainingName());

        verify(trainingService, times(1)).createTraining(training);
    }

    @Test
    public void shouldUpdateTraining(){
        // Given
        Training training = new Training();
        training.setTrainingId(trainingService.generateTrainingId());
        training.setTrainerId(1L);
        training.setTrainingName("Cardio");
        training.setTrainingType(new TrainingType("Cardio"));
        training.setTrainingDate(LocalDate.parse("2025-01-01"));
        training.setTrainingDuration(45L);

        when(trainingService.updateTraining(training)).thenReturn(training);

        // When
        Training updatedTraining = gymFacade.updateTraining(training);

        // Then
        assertNotNull(updatedTraining);
        assertNotNull(updatedTraining.getTrainingId());
        assertEquals("Cardio", updatedTraining.getTrainingName());
        verify(trainingService, times(1)).updateTraining(training);
    }

    @Test
    public void shouldDeleteTraining() {
        // When
        gymFacade.deleteTraining(1L);

        // Then
        verify(trainingService, times(1)).deleteTraining(1L);
    }

    @Test
    public void shouldGetAllTrainings() {
        // Given
        Training training = new Training();
        training.setTrainingId(trainingService.generateTrainingId());
        training.setTrainerId(1L);
        training.setTrainingName("Cardio");
        training.setTrainingType(new TrainingType("Cardio"));
        training.setTrainingDate(LocalDate.parse("2025-01-01"));
        training.setTrainingDuration(45L);

        // Given
        Training training1 = new Training();
        training1.setTrainingId(trainingService.generateTrainingId());
        training1.setTrainerId(2L);
        training1.setTrainingName("Swimming");
        training1.setTrainingType(new TrainingType("Swimming"));
        training1.setTrainingDate(LocalDate.parse("2025-02-01"));
        training1.setTrainingDuration(55L);

        List<Training> trainees = Arrays.asList(training, training1);
        when(trainingService.getAllTrainings()).thenReturn(trainees);

        // When
        List<Training> retrievedTrainers = gymFacade.getAllTrainings();

        // Then
        assertEquals(2, retrievedTrainers.size());
        verify(trainingService, times(1)).getAllTrainings();
    }

    @Test
    public void shouldGetTrainingById() {
        // Given
        Training training = new Training();
        training.setTrainingId(trainingService.generateTrainingId());
        training.setTrainerId(1L);
        training.setTrainingName("Cardio");
        training.setTrainingType(new TrainingType("Cardio"));
        training.setTrainingDate(LocalDate.parse("2025-01-01"));
        training.setTrainingDuration(45L);

        when(trainingService.getTrainingById(1L)).thenReturn(training);

        // When
        Training retrievedTraining = gymFacade.getTrainingById(1L);

        // Then
        assertNotNull(retrievedTraining);
        verify(trainingService, times(1)).getTrainingById(1L);
    }
}
