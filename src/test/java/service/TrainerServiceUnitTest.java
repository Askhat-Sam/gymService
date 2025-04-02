package service;

import dev.gymService.repository.interfaces.TrainerRepository;
import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.service.implementations.TrainerServiceImpl;
import dev.gymService.utills.UserInformationUtility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrainerServiceUnitTest {
    @Mock
    TrainerRepository trainerRepository;
    @InjectMocks
    TrainerServiceImpl trainerServiceImpl;
    private Trainer trainer;

    @Before
    public void setUp() {
        trainer = new Trainer();
        trainer.setFirstName("Andrey");
        trainer.setLastName("Andreyev");
        trainer.setUserName("Andrey.Andreyev");
        trainer.setPassword("1234567890");
        trainer.setIsActive(true);
        trainer.setUserId(1L);
    }

    @Test
    public void shouldCreateNewTrainerWithUserNameWithoutSuffix() {
        // Given
        Trainer trainer = new Trainer();
        trainer.setFirstName("Andrey");
        trainer.setLastName("Andreyev");
        trainer.setUserName("Andrey.Andreyev");
        trainer.setPassword("1234567890");
        trainer.setIsActive(true);
        trainer.setUserId(1L);

        // Mocking getTraineeByUserName to simulate existing user
        when(trainerRepository.getTrainerByUserName("Andrey.Andreyev")).thenReturn(null);
        when(trainerRepository.getTrainerByUserName("Andrey.Andreyev")).thenReturn(null);
        when(trainerRepository.create(trainer)).thenReturn(trainer);

        // When
        Trainer createdTrainee = trainerServiceImpl.createTrainer(trainer);

        // Then
        assertNotNull(createdTrainee);
        assertEquals("Andrey.Andreyev", createdTrainee.getUserName());
        assertEquals(10, createdTrainee.getPassword().length());
        verify(trainerRepository, times(1)).create(trainer);
    }

    @Test
    public void shouldCreateNewTrainerWithUserNameWithSuffix1() {
        // Given
        when(trainerRepository.getTrainerByUserName("Andrey.Andreyev")).thenReturn(trainer);
        when(trainerRepository.create(trainer)).thenReturn(trainer);

        // When
        Trainer createdTrainee = trainerServiceImpl.createTrainer(trainer);

        // Then
        assertNotNull(createdTrainee);
        assertEquals("Andrey.Andreyev1", createdTrainee.getUserName());
        assertEquals(10, createdTrainee.getPassword().length());
        verify(trainerRepository, times(1)).create(trainer);
    }

    @Test
    public void shouldCreateNewTrainerWithUserNameWithSuffix2() {
        // Given: Existing users
        Trainer existingTrainer1 = new Trainer();
        existingTrainer1.setUserName("Andrey.Andreyev");
        existingTrainer1.setPassword("1234567890");

        Trainer existingTrainer2 = new Trainer();
        existingTrainer2.setUserName("Andrey.Andreyev1");
        existingTrainer2.setPassword("1234567890");

        // New trainee being created
        Trainer newTrainer = new Trainer();
        newTrainer.setFirstName("Andrey");
        newTrainer.setLastName("Andreyev");
        newTrainer.setIsActive(true);
        newTrainer.setUserId(1L);
        newTrainer.setPassword("1234567890");

        // Simulating that "Andrey.Andreyev" and "Andrey.Andreyev1" already exist in DB
        when(trainerRepository.getTrainerByUserName("Andrey.Andreyev")).thenReturn(existingTrainer1);
        when(trainerRepository.getTrainerByUserName("Andrey.Andreyev1")).thenReturn(existingTrainer2);
        when(trainerRepository.getTrainerByUserName("Andrey.Andreyev2")).thenReturn(null);
        when(trainerRepository.create(newTrainer)).thenReturn(newTrainer);

        // When
        Trainer createdTrainer = trainerServiceImpl.createTrainer(newTrainer);

        // Then
        assertNotNull(createdTrainer);
        assertEquals("Andrey.Andreyev2", createdTrainer.getUserName());
        assertEquals(10, createdTrainer.getPassword().length());
        verify(trainerRepository, times(1)).create(newTrainer);
    }

    @Test
    public void shouldUpdateTrainer() {
        // Given
        when(trainerRepository.updateTrainer(trainer)).thenReturn(trainer);
        when(trainerRepository.getTrainerByUserName(trainer.getUserName())).thenReturn(trainer);

        // When
        Trainer updatedTrainer = trainerServiceImpl.updateTrainer(trainer, "Andrey.Andreyev", "1234567890");

        // Then
        assertNotNull(updatedTrainer);
        assertEquals(true, updatedTrainer.getIsActive());
        assertEquals("Andrey.Andreyev", updatedTrainer.getUserName());
        assertEquals(10, updatedTrainer.getPassword().length());
        verify(trainerRepository, times(1)).updateTrainer(trainer);
    }


    @Test
    public void shouldChangeTrainerStatus() {
        // Given
        when(trainerRepository.getTrainerByUserName("Andrey.Andreyev")).thenReturn(trainer);

        // When
        trainerServiceImpl.changeTrainerStatus("Andrey.Andreyev", "1234567890");

        // Then
        verify(trainerRepository, times(1)).updateTrainer(any());
    }

    @Test
    public void shouldGetTrainerTrainingList() {
        // Given
        Trainee trainee = new Trainee();
        trainee.setUserName("Denis.Denisov");

        Training training1 =new Training();
        training1.setTrainingId(1L);
        training1.setTrainingName("Cardio");
        training1.setTrainingDate(LocalDate.parse("2025-01-01"));

        Training training2 =new Training();
        training2.setTrainingId(2L);
        training2.setTrainingName("Yoga");
        training2.setTrainingDate(LocalDate.parse("2025-01-02"));

        trainer.setTrainings(new ArrayList<>());
        trainer.getTrainings().add(training1);
        trainer.getTrainings().add(training2);

        when(trainerRepository.getTrainerTrainingList("Andrey.Andreyev",  "2025-01-01",
                "2025-01-01", "Denis.Denisov")).thenReturn(trainer.getTrainings());
        when(trainerRepository.getTrainerByUserName("Andrey.Andreyev")).thenReturn(trainer);

        // When
        List<Training> retrievedTrainerTrainings = trainerServiceImpl.getTrainerTrainingList("Andrey.Andreyev", "1234567890", "2025-01-01",
                "2025-01-01", "Denis.Denisov");

        // Then
        assertEquals(2, retrievedTrainerTrainings.size());
        verify(trainerRepository, times(1)).getTrainerTrainingList("Andrey.Andreyev", "2025-01-01",
                "2025-01-01", "Denis.Denisov");
    }

    @Test
    public void shouldChangeTrainerPassword() {
        // Given
        when(trainerRepository.getTrainerByUserName("Andrey.Andreyev")).thenReturn(trainer);

        // When
        trainerServiceImpl.changeTrainerPassword("Andrey.Andreyev", "1234567890", "987654321");

        // Then
        verify(trainerRepository, times(1)).updateTrainer(any());
    }
}
