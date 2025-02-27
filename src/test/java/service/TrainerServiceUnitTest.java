package service;

import dev.gymService.dao.TrainerDAO;
import dev.gymService.model.Trainer;
import dev.gymService.service.implementations.TrainerServiceImpl;
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
        Trainer trainer = new Trainer(1L, "Ivan", "Ivanov", "Ivan.Ivanov",
                "653654654", true, "swimming");

        when(trainerDAO.create(trainer)).thenReturn(trainer);

        // When
        Trainer createdTrainer = trainerServiceImpl.createTrainer(trainer);

        // Then
        assertNotNull(createdTrainer);
        assertEquals("swimming", createdTrainer.getSpecialization());
        verify(trainerDAO, times(1)).create(trainer);
    }

    @Test
    public void shouldUpdateTrainee(){
        // Given
        Trainer trainer = new Trainer(1L, "Ivan", "Ivanov", "Ivan.Ivanov",
                "653654654", true, "swimming");

        when(trainerDAO.update(trainer)).thenReturn(trainer);

        // When
        Trainer updatedTrainer = trainerServiceImpl.updateTrainer(trainer);

        // Then
        assertNotNull(updatedTrainer);
        assertEquals("653654654", updatedTrainer.getPassword());
        verify(trainerDAO, times(1)).update(trainer);
    }

    @Test
    public void shouldDeleteTrainee() {
        // When
        trainerServiceImpl.deleteTrainer(1L);

        // Then
        verify(trainerDAO, times(1)).delete(1L);
    }

    @Test
    public void shouldGetAllTrainees() {
        // Given
        Trainer trainer1 = new Trainer(1L, "Ivan", "Ivanov", "Ivan.Ivanov",
                "653654654", true, "swimming");

        Trainer trainer2 = new Trainer(2L, "Maksim", "Maksimov", "Maksim.Maksimov",
                "1233243", true, "cardio");

        List<Trainer> trainees = Arrays.asList(trainer1, trainer2);
        when(trainerDAO.getAll()).thenReturn(trainees);

        // When
        List<Trainer> retrievedTrainers = trainerServiceImpl.getAllTrainers();

        // Then
        assertEquals(2, retrievedTrainers.size());
        verify(trainerDAO, times(1)).getAll();
    }

    @Test
    public void shouldGetTraineeById() {
        // Given
        Trainer trainer = new Trainer(1L, "Ivan", "Ivanov", "Ivan.Ivanov",
                "653654654", true, "swimming");
        when(trainerDAO.getById(1L)).thenReturn(trainer);

        // When
        Trainer retrievedTrainer = trainerServiceImpl.getTrainerById(1L);

        // Then
        assertNotNull(retrievedTrainer);
        verify(trainerDAO, times(1)).getById(1L);
    }
}
