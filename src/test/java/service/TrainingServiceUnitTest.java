package service;

import dev.gymService.dao.interfaces.TrainingRepository;
import dev.gymService.model.Training;
import dev.gymService.service.implementations.TrainingServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrainingServiceUnitTest {
    @Mock
    TrainingRepository trainingRepository;
    @InjectMocks
    TrainingServiceImpl trainingServiceImpl;

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

        when(trainingRepository.addTraining(training)).thenReturn(training);

        // When
        Training createdTraining = trainingServiceImpl.addTraining(training);

        // Then
        assertNotNull(createdTraining);
        assertNotNull(createdTraining.getTrainingId());
        assertEquals("Cardio", createdTraining.getTrainingName());
        assertEquals(LocalDate.parse("2025-01-01"), createdTraining.getTrainingDate());

        verify(trainingRepository, times(1)).addTraining(training);
    }
}
