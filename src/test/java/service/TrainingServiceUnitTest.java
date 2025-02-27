package service;

import dev.gymService.dao.TrainerDAO;
import dev.gymService.dao.TrainingDAO;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.model.TrainingType;
import dev.gymService.service.implementations.TrainerServiceImpl;
import dev.gymService.service.implementations.TrainingServiceImpl;
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
public class TrainingServiceUnitTest {
    @Mock
    TrainingDAO trainingDAO;
    @InjectMocks
    TrainingServiceImpl trainingServiceImpl;

    @Test
    public void shouldCreateNewTraining() {
        // Given
        Training training = new Training(1L, 1L, "Cardio", new TrainingType("Cardio"),
                LocalDate.parse("2025-01-02"), 45L);

        when(trainingDAO.create(training)).thenReturn(training);

        // When
        Training createdTraining = trainingServiceImpl.createTraining(training);

        // Then
        assertNotNull(createdTraining);
        assertEquals("Cardio", createdTraining.getTrainingName());
        verify(trainingDAO, times(1)).create(training);
    }

    @Test
    public void shouldUpdateTrainee(){
        // Given
        Training training = new Training(1L, 1L, "Cardio", new TrainingType("Cardio"),
                LocalDate.parse("2025-01-02"), 45L);

        when(trainingDAO.update(training)).thenReturn(training);

        // When
        Training updatedTraining = trainingServiceImpl.updateTraining(training);

        // Then
        assertNotNull(updatedTraining);
        assertEquals("Cardio", updatedTraining.getTrainingName());
        verify(trainingDAO, times(1)).update(training);
    }

    @Test
    public void shouldDeleteTrainee() {
        // When
        trainingServiceImpl.deleteTraining(1L);

        // Then
        verify(trainingDAO, times(1)).delete(1L);
    }

    @Test
    public void shouldGetAllTrainees() {
        // Given
        Training training1= new Training(1L, 1L, "Cardio", new TrainingType("Cardio"),
                LocalDate.parse("2025-01-02"), 45L);
        Training training2 = new Training(2L, 2L, "Swimming", new TrainingType("Swimming"),
                LocalDate.parse("2025-01-06"), 55L);

        List<Training> trainees = Arrays.asList(training1, training2);
        when(trainingDAO.getAll()).thenReturn(trainees);

        // When
        List<Training> retrievedTrainers = trainingServiceImpl.getAllTrainings();

        // Then
        assertEquals(2, retrievedTrainers.size());
        verify(trainingDAO, times(1)).getAll();
    }

    @Test
    public void shouldGetTraineeById() {
        // Given
        Training training = new Training(1L, 1L, "Cardio", new TrainingType("Cardio"),
                LocalDate.parse("2025-01-02"), 45L);
        when(trainingDAO.getById(1L)).thenReturn(training);

        // When
        Training retrievedTraining = trainingDAO.getById(1L);

        // Then
        assertNotNull(retrievedTraining);
        verify(trainingDAO, times(1)).getById(1L);
    }
}
