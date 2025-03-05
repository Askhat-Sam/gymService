//package service;
//
//import dev.gymService.dao.TrainerDAO;
//import dev.gymService.dao.TrainingDAO;
//import dev.gymService.model.Trainer;
//import dev.gymService.model.Training;
//import dev.gymService.model.TrainingType;
//import dev.gymService.service.implementations.TrainerServiceImpl;
//import dev.gymService.service.implementations.TrainingServiceImpl;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class TrainingServiceUnitTest {
//    @Mock
//    TrainingDAO trainingDAO;
//    @InjectMocks
//    TrainingServiceImpl trainingServiceImpl;
//
//    @Test
//    public void shouldCreateNewTraining() {
//        // Given
//        Training training = new Training();
//        training.setTrainingId(1L);
//        training.setTrainerId(1L);
//        training.setTrainingName("Cardio");
//        training.setTrainingType(new TrainingType("Cardio"));
//        training.setTrainingDate(LocalDate.parse("2025-01-01"));
//        training.setTrainingDuration(45L);
//
//        when(trainingDAO.create(training)).thenReturn(training);
//
//        // When
//        Training createdTraining = trainingServiceImpl.createTraining(training);
//
//        // Then
//        assertNotNull(createdTraining);
//        assertNotNull(createdTraining.getTrainingId());
//        assertEquals("Cardio", createdTraining.getTrainingName());
//
//        verify(trainingDAO, times(1)).create(training);
//    }
//
//
//
//
//
//    @Test
//    public void shouldGetAllTrainings() {
//        // Given
//        Training training = new Training();
//        training.setTrainingId(1L);
//        training.setTrainerId(1L);
//        training.setTrainingName("Cardio");
//        training.setTrainingType(new TrainingType("Cardio"));
//        training.setTrainingDate(LocalDate.parse("2025-01-01"));
//        training.setTrainingDuration(45L);
//
//        // Given
//        Training training1 = new Training();
//        training.setTrainingId(1L);
//        training1.setTrainerId(2L);
//        training1.setTrainingName("Swimming");
//        training1.setTrainingType(new TrainingType("Swimming"));
//        training1.setTrainingDate(LocalDate.parse("2025-02-01"));
//        training1.setTrainingDuration(55L);
//
//        List<Training> trainees = Arrays.asList(training, training1);
//        when(trainingDAO.getAll()).thenReturn(trainees);
//
//        // When
//        List<Training> retrievedTrainers = trainingServiceImpl.getAllTrainings();
//
//        // Then
//        assertEquals(2, retrievedTrainers.size());
//        verify(trainingDAO, times(1)).getAll();
//    }
//
//    @Test
//    public void shouldGetTrainingById() {
//        // Given
//        Training training = new Training();
//        training.setTrainingId(1L);
//        training.setTrainerId(1L);
//        training.setTrainingName("Cardio");
//        training.setTrainingType(new TrainingType("Cardio"));
//        training.setTrainingDate(LocalDate.parse("2025-01-01"));
//        training.setTrainingDuration(45L);
//
//        when(trainingDAO.getById(1L)).thenReturn(training);
//
//        // When
//        Training retrievedTraining = trainingDAO.getById(1L);
//
//        // Then
//        assertNotNull(retrievedTraining);
//        verify(trainingDAO, times(1)).getById(1L);
//    }
//}
