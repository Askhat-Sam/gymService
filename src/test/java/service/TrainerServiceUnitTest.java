package service;

import dev.gymService.dao.TrainerDAO;
import dev.gymService.model.Trainer;
import dev.gymService.model.TrainingType;
import dev.gymService.service.implementations.TrainerServiceImpl;
import dev.gymService.utills.UserInformationUtility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrainerServiceUnitTest {
    @Mock
    TrainerDAO trainerDAO;
    @InjectMocks
    TrainerServiceImpl trainerServiceImpl;

    @Test
    public void shouldCreateNewTrainer() {
        // Given
        Trainer trainer = new Trainer();
        TrainingType trainingType = new TrainingType("Swimming");
        trainer.setUserId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setActive(true);
        trainer.setSpecialization(trainingType);

        when(trainerDAO.create(trainer)).thenReturn(trainer);

        // When
        Trainer createdTrainer = trainerServiceImpl.createTrainer(trainer);

        // Then
        assertNotNull(createdTrainer);
        assertEquals("Ivan.Ivanov", createdTrainer.getUserName());
        assertEquals(10, createdTrainer.getPassword().length());
        assertEquals(trainingType, createdTrainer.getSpecialization());
        verify(trainerDAO, times(1)).create(trainer);
    }

    @Test
    public void shouldUpdateTrainer(){
        // Given
        Trainer trainer = new Trainer();
        trainer.setUserId(1L);
        trainer.setFirstName("Denis");
        trainer.setLastName("Ivanov");
        trainer.setUserName("Denis.Ivanov");
        trainer.setPassword(UserInformationUtility.generatePassword());
        trainer.setActive(true);
        trainer.setSpecialization(new TrainingType("swimming"));

        when(trainerDAO.update(trainer)).thenReturn(trainer);

        // When
        Trainer updatedTrainer = trainerServiceImpl.updateTrainer(trainer);

        // Then
        assertNotNull(updatedTrainer);
        assertEquals("Denis.Ivanov", updatedTrainer.getUserName());
        assertEquals(10, updatedTrainer.getPassword().length());
        verify(trainerDAO, times(1)).update(trainer);
    }

    @Test
    public void shouldDeleteTrainer() {
        // When
        trainerServiceImpl.deleteTrainer(1L);

        // Then
        verify(trainerDAO, times(1)).delete(1L);
    }

    @Test
    public void shouldGetAllTrainers() {
        // Given
        Trainer trainer = new Trainer();
        trainer.setUserId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setPassword(UserInformationUtility.generatePassword());
        trainer.setActive(true);
        trainer.setSpecialization(new TrainingType("swimming"));

        Trainer trainer1 = new Trainer();
        trainer.setUserId(1L);
        trainer1.setFirstName("Maksim");
        trainer1.setLastName("Maksimov");
        trainer1.setPassword(UserInformationUtility.generatePassword());
        trainer1.setActive(true);
        trainer1.setSpecialization(new TrainingType("cardio"));


        List<Trainer> trainees = Arrays.asList(trainer, trainer1);
        when(trainerDAO.getAll()).thenReturn(trainees);

        // When
        List<Trainer> retrievedTrainers = trainerServiceImpl.getAllTrainers();

        // Then
        assertEquals(2, retrievedTrainers.size());
        verify(trainerDAO, times(1)).getAll();
    }

    @Test
    public void shouldGetTrainerById() {
        // Given
        Trainer trainer = new Trainer();
        trainer.setUserId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUserName("Ivan.Ivanov1");
        trainer.setPassword(UserInformationUtility.generatePassword());
        trainer.setActive(true);
        trainer.setSpecialization(new TrainingType("swimming"));

        System.out.println("ID: " + trainer.getUserId());

        when(trainerDAO.getById(0L)).thenReturn(trainer);

        // When
        Trainer retrievedTrainer = trainerServiceImpl.getTrainerById(0L);

        // Then
        assertNotNull(retrievedTrainer);
        assertEquals("Ivan.Ivanov1", retrievedTrainer.getUserName());
        assertEquals(10, retrievedTrainer.getPassword().length());
        verify(trainerDAO, times(1)).getById(0L);
    }
}
