package service;


import dev.gymService.model.Trainee;
import dev.gymService.model.Training;
import dev.gymService.repository.interfaces.TraineeRepository;
import dev.gymService.service.implementations.TraineeServiceImpl;
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
public class TraineeServiceUnitTest {
    @Mock
    TraineeRepository traineeRepository;
    @InjectMocks
    TraineeServiceImpl traineeServiceImpl;
    private Trainee trainee;


    @Before
    public void setUp() {
        trainee = new Trainee();
        trainee.setFirstName("Andrey");
        trainee.setLastName("Andreyev");
        trainee.setUserName("Andrey.Andreyev");
        trainee.setPassword("1234567890");
        trainee.setIsActive(true);
        trainee.setDateOfBirth(LocalDate.parse("2000-01-01"));
        trainee.setAddress("Furmanova 2");
        trainee.setUserId(1L);
    }

    @Test
    public void shouldCreateNewTraineeWithUserNameWithoutSuffix() {
        // Given
        when(traineeRepository.getByUserName("Andrey.Andreyev")).thenReturn(null);
        when(traineeRepository.getByUserName("Andrey.Andreyev")).thenReturn(null);
        when(traineeRepository.create(trainee)).thenReturn(trainee);

        // When
        Trainee createdTrainee = traineeServiceImpl.createTrainee(trainee);

        // Then
        assertNotNull(createdTrainee);
        assertEquals("Andrey.Andreyev", createdTrainee.getUserName());
        assertEquals(60, createdTrainee.getPassword().length());
        verify(traineeRepository, times(1)).create(trainee);
    }

    @Test
    public void shouldCreateNewTraineeWithUserNameWithSuffix1() {
        // Given
        when(traineeRepository.getByUserName("Andrey.Andreyev")).thenReturn(trainee);
        when(traineeRepository.create(trainee)).thenReturn(trainee);

        // When
        Trainee createdTrainee = traineeServiceImpl.createTrainee(trainee);

        // Then
        assertNotNull(createdTrainee);
        assertEquals("Andrey.Andreyev1", createdTrainee.getUserName());
        assertEquals(60, createdTrainee.getPassword().length());
        verify(traineeRepository, times(1)).create(trainee);
    }

    @Test
    public void shouldCreateNewTraineeWithUserNameWithSuffix2() {
        // Given: Existing users
        Trainee existingTrainee1 = new Trainee();
        existingTrainee1.setUserName("Andrey.Andreyev");
        existingTrainee1.setPassword("1234567890");

        Trainee existingTrainee2 = new Trainee();
        existingTrainee2.setUserName("Andrey.Andreyev1");
        existingTrainee2.setPassword("1234567890");

        // New trainee being created
        Trainee newTrainee = new Trainee();
        newTrainee.setFirstName("Andrey");
        newTrainee.setLastName("Andreyev");
        newTrainee.setUserName("Andrey.Andreyev");
        newTrainee.setPassword("1234567890");
        newTrainee.setIsActive(true);
        newTrainee.setDateOfBirth(LocalDate.parse("2000-01-01"));
        newTrainee.setAddress("Furmanova 2");
        newTrainee.setUserId(1L);

        // Simulating that "Andrey.Andreyev" and "Andrey.Andreyev1" already exist in DB
        when(traineeRepository.getByUserName("Andrey.Andreyev")).thenReturn(existingTrainee1);
        when(traineeRepository.getByUserName("Andrey.Andreyev1")).thenReturn(existingTrainee2);
        when(traineeRepository.getByUserName("Andrey.Andreyev2")).thenReturn(null);
        when(traineeRepository.create(newTrainee)).thenReturn(newTrainee);

        // When
        Trainee createdTrainee = traineeServiceImpl.createTrainee(newTrainee);

        // Then
        assertNotNull(createdTrainee);
        assertEquals("Andrey.Andreyev2", createdTrainee.getUserName());
        assertEquals(60, createdTrainee.getPassword().length());
        verify(traineeRepository, times(1)).create(newTrainee);
    }

    @Test
    public void shouldUpdateTrainee() {
        // Given
        when(traineeRepository.update(trainee)).thenReturn(trainee);
        when(traineeRepository.getByUserName(trainee.getUserName())).thenReturn(trainee);

        // When
        Trainee updatedTrainee = traineeServiceImpl.updateTrainee(trainee, "Andrey.Andreyev");

        // Then
        assertNotNull(updatedTrainee);
        assertEquals("Furmanova 2", updatedTrainee.getAddress());
        assertEquals("Andrey.Andreyev", updatedTrainee.getUserName());
        assertEquals(10, updatedTrainee.getPassword().length());
        verify(traineeRepository, times(1)).update(trainee);
    }

    @Test
    public void shouldDeleteTrainee() {
        // Given
        when(traineeRepository.getByUserName("Andrey.Andreyev")).thenReturn(trainee);
        when(traineeRepository.getByUserName("Andrey.Andreyev")).thenReturn(trainee);

        // When
        traineeServiceImpl.deleteTraineeByUserName("Andrey.Andreyev");

        // Then
        verify(traineeRepository, times(1)).deleteByUserName("Andrey.Andreyev");
    }

    @Test
    public void shouldChangeTraineeStatus() {
        // Given
        when(traineeRepository.getByUserName("Andrey.Andreyev")).thenReturn(trainee);
        // When
        traineeServiceImpl.changeTraineeStatus("Andrey.Andreyev");

        // Then
        verify(traineeRepository, times(1)).update(any());
    }

    @Test
    public void shouldGetTraineeTrainingList() {
        // Given
        Training training1 = new Training();
        training1.setTrainingId(1L);
        training1.setTrainingName("Cardio");
        training1.setTrainingDate(LocalDate.parse("2025-01-01"));

        Training training2 = new Training();
        training2.setTrainingId(2L);
        training2.setTrainingName("Yoga");
        training2.setTrainingDate(LocalDate.parse("2025-01-02"));

        trainee.setTrainings(new ArrayList<>());
        trainee.getTrainings().add(training1);
        trainee.getTrainings().add(training2);

        when(traineeRepository.getTraineeTrainingList("Andrey.Andreyev", "2025-01-01",
                "2025-01-01", "Denis.Denisov", 1L)).thenReturn(trainee.getTrainings());
        when(traineeRepository.getByUserName("Andrey.Andreyev")).thenReturn(trainee);

        // When
        List<Training> retrievedTraineeTrainings = traineeServiceImpl.getTraineeTrainingList("Andrey.Andreyev", "2025-01-01",
                "2025-01-01", "Denis.Denisov", 1L);

        // Then
        assertEquals(2, retrievedTraineeTrainings.size());
        verify(traineeRepository, times(1)).getTraineeTrainingList("Andrey.Andreyev", "2025-01-01",
                "2025-01-01", "Denis.Denisov", 1L);
    }

    @Test
    public void shouldChangeTraineePassword() {
        // Given
        when(traineeRepository.getByUserName("Andrey.Andreyev")).thenReturn(trainee);
        // When
        traineeServiceImpl.changeTraineePassword("Andrey.Andreyev", "987654321");

        // Then
        verify(traineeRepository, times(1)).update(any());
    }

    @Test
    public void shouldGetNotAssignedTrainers() {
        when(traineeRepository.getByUserName("Andrey.Andreyev")).thenReturn(trainee);
        // When
        traineeServiceImpl.getNotAssignedTrainers("Andrey.Andreyev");

        // Then
        verify(traineeRepository, times(1)).getNotAssignedTrainers("Andrey.Andreyev");
    }

}