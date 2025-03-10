package facade;

import dev.gymService.facade.GymFacade;
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
import java.util.ArrayList;
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
        trainee.setUserName("Andrey.Andreyev");
        trainee.setIsActive(true);
        trainee.setDateOfBirth(LocalDate.parse("2000-01-01"));
        trainee.setAddress("Furmanova 2");
        trainee.setUserId(1L);
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
        trainee.setUserName("Andrey.Andreyev");
        trainee.setIsActive(true);
        trainee.setDateOfBirth(LocalDate.parse("2000-01-01"));
        trainee.setAddress("Furmanova 2");
        trainee.setUserId(1L);
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
    public void shouldDeleteTraineeByUserName(){
        // When
        gymFacade.deleteTraineeByUserName("Andre.Andreyev", "12345678");

        // Then
        verify(traineeService, times(1)).deleteTraineeByUserName("Andre.Andreyev", "12345678");
    }

    @Test
    public void shouldGetTraineeById() {
        // Given
        Trainee trainee = new Trainee();
        trainee.setFirstName("Andrey");
        trainee.setLastName("Andreyev");
        trainee.setIsActive(true);
        trainee.setDateOfBirth(LocalDate.parse("2000-01-01"));
        trainee.setAddress("Furmanova 2");
        trainee.setUserId(1L);
        trainee.setPassword(UserInformationUtility.generatePassword());

        when(traineeService.getTraineeById(1L, "Andrey.Andreyev", "12345678")).thenReturn(trainee);

        // When
        Trainee retrievedTrainee = gymFacade.getTraineeById(1L, "Andrey.Andreyev", "12345678");

        // Then
        assertNotNull(retrievedTrainee);
        verify(traineeService, times(1)).getTraineeById(1L, "Andrey.Andreyev", "12345678");
    }

    @Test
    public void shouldGetTraineeByUserName() {
        // Given
        Trainee trainee = new Trainee();
        trainee.setFirstName("Andrey");
        trainee.setLastName("Andreyev");
        trainee.setIsActive(true);
        trainee.setDateOfBirth(LocalDate.parse("2000-01-01"));
        trainee.setAddress("Furmanova 2");
        trainee.setUserId(1L);
        trainee.setPassword(UserInformationUtility.generatePassword());

        when(traineeService.getTraineeByUserName("Andrey.Andreyev", "12345678")).thenReturn(trainee);

        // When
        Trainee retrievedTrainee = gymFacade.getTraineeByUserName( "Andrey.Andreyev", "12345678");

        // Then
        assertNotNull(retrievedTrainee);
        verify(traineeService, times(1)).getTraineeByUserName("Andrey.Andreyev", "12345678");
    }

    @Test
    public void shouldChangeTraineePassword(){
        // When
        gymFacade.changeTraineePassword( "Andrey.Andreyev", "12345678", "09876543");

        // Then
        verify(traineeService, times(1)).changeTraineePassword("Andrey.Andreyev", "12345678", "09876543");
    }

    @Test
    public void shouldChangeTraineeStatus(){
        // When
        gymFacade.changeTraineeStatus("Andre.Andreyev", "12345678");

        // Then
        verify(traineeService, times(1)).changeTraineeStatus("Andre.Andreyev", "12345678");
    }

    @Test
    public void shouldGetTraineeTrainingList(){
        // When
        gymFacade.getTraineeTrainingList("Andre.Andreyev", "12345678", "2025-01-01","2025-01-01", "Denis.Denisov");

        // Then
        verify(traineeService, times(1)).getTraineeTrainingList("Andre.Andreyev", "12345678", "2025-01-01","2025-01-01", "Denis.Denisov");
    }

    @Test
    public void shouldGetNotAssignedTrainers(){

        // When
        gymFacade.getNotAssignedTrainers("Andre.Andreyev", "12345678");

        // Then
        verify(traineeService, times(1)).getNotAssignedTrainers("Andre.Andreyev", "12345678");
    }

    @Test
    public void shouldUpdateTrainersList(){

        // When
        gymFacade.updateTrainersList("Andre.Andreyev", "12345678", new ArrayList<>());

        // Then
        verify(traineeService, times(1)).updateTrainersList("Andre.Andreyev", "12345678", new ArrayList<>());
    }




    // Trainer
    @Test
    public void shouldCreateTrainer(){
        // Given
        Trainer trainer = new Trainer();
        trainer.setFirstName("Andrey");
        trainer.setLastName("Andreyev");
        trainer.setUserName("Andrey.Andreyev");
        trainer.setIsActive(true);
        trainer.setUserId(1L);
        trainer.setPassword(UserInformationUtility.generatePassword());

        when(trainerService.createTrainer(trainer)).thenReturn(trainer);

        // When
        Trainer createdTrainer = gymFacade.createTrainer(trainer);

        // Then
        assertNotNull(createdTrainer);
        assertEquals("Andrey.Andreyev", createdTrainer.getUserName());
        assertEquals(10, createdTrainer.getPassword().length());
        verify(trainerService, times(1)).createTrainer(trainer);
    }

    @Test
    public void shouldUpdateTrainer(){
        // Given
        Trainer trainer = new Trainer();
        trainer.setFirstName("Andrey");
        trainer.setLastName("Andreyev");
        trainer.setUserName("Andrey.Andreyev");
        trainer.setIsActive(true);
        trainer.setUserId(1L);
        trainer.setPassword(UserInformationUtility.generatePassword());

        when(trainerService.updateTrainer(trainer)).thenReturn(trainer);

        // When
        Trainer updatedTrainer = gymFacade.updateTrainer(trainer);

        // Then
        assertNotNull(updatedTrainer);
        assertEquals("Andrey.Andreyev", updatedTrainer.getUserName());
        assertEquals(10, updatedTrainer.getPassword().length());
        verify(trainerService, times(1)).updateTrainer(trainer);
    }


    @Test
    public void shouldGetTrainerById() {
        // Given
        Trainer trainer = new Trainer();
        trainer.setFirstName("Andrey");
        trainer.setLastName("Andreyev");
        trainer.setUserName("Andrey.Andreyev");
        trainer.setIsActive(true);
        trainer.setUserId(1L);
        trainer.setPassword(UserInformationUtility.generatePassword());

        when(trainerService.getTrainerById(1L, "Andrey.Andreyev", "12345678")).thenReturn(trainer);

        // When
        Trainer retrievedTrainer = gymFacade.getTrainerById(1L, "Andrey.Andreyev", "12345678");

        // Then
        assertNotNull(retrievedTrainer);
        verify(trainerService, times(1)).getTrainerById(1L, "Andrey.Andreyev", "12345678");
    }

    @Test
    public void shouldGetTrainerByUserName() {
        // Given
        Trainer trainer = new Trainer();
        trainer.setFirstName("Andrey");
        trainer.setLastName("Andreyev");
        trainer.setUserName("Andrey.Andreyev");
        trainer.setIsActive(true);
        trainer.setUserId(1L);
        trainer.setPassword(UserInformationUtility.generatePassword());

        when(trainerService.getTrainerByUserName("Andrey.Andreyev", "12345678")).thenReturn(trainer);

        // When
        Trainer retrievedTrainer = gymFacade.getTrainerByUserName( "Andrey.Andreyev", "12345678");

        // Then
        assertNotNull(retrievedTrainer);
        verify(trainerService, times(1)).getTrainerByUserName("Andrey.Andreyev", "12345678");
    }

    @Test
    public void shouldChangeTrainerPassword(){
        // When
        gymFacade.changeTrainerPassword( "Andrey.Andreyev", "12345678", "09876543");

        // Then
        verify(trainerService, times(1)).changeTrainerPassword("Andrey.Andreyev", "12345678", "09876543");
    }

    @Test
    public void shouldChangeTrainerStatus(){
        // When
        gymFacade.changeTrainerStatus("Andre.Andreyev", "12345678");

        // Then
        verify(trainerService, times(1)).changeTrainerStatus("Andre.Andreyev", "12345678");
    }

    @Test
    public void shouldGetTrainerTrainingList(){
        // When
        gymFacade.getTrainerTrainingList("Andre.Andreyev", "12345678", "2025-01-01","2025-01-01", "Denis.Denisov");

        // Then
        verify(trainerService, times(1)).getTrainerTrainingList("Andre.Andreyev", "12345678", "2025-01-01","2025-01-01", "Denis.Denisov");
    }



    // Training
    @Test
    public void shouldCreateNewTraining() {
        // Given
        Training training = new Training();
        training.setTrainingId(1L);
        training.setTrainerId(1L);
        training.setTrainingName("Cardio");
        training.setTrainingTypeId(1L);
        training.setTrainingDate(LocalDate.parse("2025-01-01"));
        training.setTrainingDuration(45L);

        when(trainingService.addTraining(training)).thenReturn(training);

        // When
        Training createdTraining = gymFacade.addTraining(training);

        // Then
        assertNotNull(createdTraining);
        assertNotNull(createdTraining.getTrainingId());
        assertEquals("Cardio", createdTraining.getTrainingName());

        verify(trainingService, times(1)).addTraining(training);
    }
}
