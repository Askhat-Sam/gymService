package service;


import dev.gymService.dao.interfaces.TraineeRepository;
import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.service.implementations.TraineeServiceImpl;
import dev.gymService.utills.UserInformationUtility;
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

    @Test
    public void shouldCreateNewTraineeWithUserNameWithoutSuffix() {
        // Given
        Trainee trainee = new Trainee();
        trainee.setFirstName("Andrey");
        trainee.setLastName("Andreyev");
        trainee.setActive(true);
        trainee.setDateOfBirth(LocalDate.parse("2000-01-01"));
        trainee.setAddress("Furmanova 2");
        trainee.setUserId(1L);

        // Mocking getTraineeByUserName to simulate existing user
        when(traineeRepository.getTraineeByUserName("Andrey.Andreyev", trainee.getPassword())).thenReturn(null);
        when(traineeRepository.create(trainee)).thenReturn(trainee);

        // When
        Trainee createdTrainee = traineeServiceImpl.createTrainee(trainee);

        // Then
        assertNotNull(createdTrainee);
        assertEquals("Andrey.Andreyev", createdTrainee.getUserName());
        assertEquals(10, createdTrainee.getPassword().length());
        verify(traineeRepository, times(1)).create(trainee);
    }

    @Test
    public void shouldCreateNewTraineeWithUserNameWithSuffix1() {
        // Given
        Trainee trainee = new Trainee();
        trainee.setFirstName("Andrey");
        trainee.setLastName("Andreyev");
        trainee.setActive(true);
        trainee.setDateOfBirth(LocalDate.parse("2000-01-01"));
        trainee.setAddress("Furmanova 2");
        trainee.setUserId(1L);

        // Mocking getTraineeByUserName to simulate existing user
        when(traineeRepository.getTraineeByUserName("Andrey.Andreyev", trainee.getPassword())).thenReturn(trainee);
        when(traineeRepository.create(trainee)).thenReturn(trainee);

        // When
        Trainee createdTrainee = traineeServiceImpl.createTrainee(trainee);

        // Then
        assertNotNull(createdTrainee);
        assertEquals("Andrey.Andreyev1", createdTrainee.getUserName());
        assertEquals(10, createdTrainee.getPassword().length());
        verify(traineeRepository, times(1)).create(trainee);
    }

    @Test
    public void shouldCreateNewTraineeWithUserNameWithSuffix2() {
        // Given: Existing users
        Trainee existingTrainee1 = new Trainee();
        existingTrainee1.setUserName("Andrey.Andreyev");

        Trainee existingTrainee2 = new Trainee();
        existingTrainee2.setUserName("Andrey.Andreyev1");

        // New trainee being created
        Trainee newTrainee = new Trainee();
        newTrainee.setFirstName("Andrey");
        newTrainee.setLastName("Andreyev");
        newTrainee.setActive(true);
        newTrainee.setDateOfBirth(LocalDate.parse("2000-01-01"));
        newTrainee.setAddress("Furmanova 2");
        newTrainee.setUserId(1L);

        // Simulating that "Andrey.Andreyev" and "Andrey.Andreyev1" already exist in DB
        when(traineeRepository.getTraineeByUserName("Andrey.Andreyev", newTrainee.getPassword())).thenReturn(existingTrainee1);
        when(traineeRepository.getTraineeByUserName("Andrey.Andreyev1", newTrainee.getPassword())).thenReturn(existingTrainee2);
        when(traineeRepository.getTraineeByUserName("Andrey.Andreyev2", newTrainee.getPassword())).thenReturn(null);
        when(traineeRepository.create(newTrainee)).thenReturn(newTrainee);

        // When
        Trainee createdTrainee = traineeServiceImpl.createTrainee(newTrainee);

        // Then
        assertNotNull(createdTrainee);
        assertEquals("Andrey.Andreyev2", createdTrainee.getUserName());
        assertEquals(10, createdTrainee.getPassword().length());
        verify(traineeRepository, times(1)).create(newTrainee);
    }

    @Test
    public void shouldUpdateTrainee() {
        // Given
        Trainee trainee = new Trainee();
        trainee.setFirstName("Andrey");
        trainee.setLastName("Andreyev");
        trainee.setUserName("Andrey.Andreyev");
        trainee.setActive(true);
        trainee.setDateOfBirth(LocalDate.parse("2000-01-01"));
        trainee.setAddress("Furmanova 2");
        trainee.setUserId(1L);
        trainee.setPassword(UserInformationUtility.generatePassword());

        when(traineeRepository.updateTrainee(trainee)).thenReturn(trainee);

        // When
        Trainee updatedTrainee = traineeServiceImpl.updateTrainee(trainee);

        // Then
        assertNotNull(updatedTrainee);
        assertEquals("Furmanova 2", updatedTrainee.getAddress());
        assertEquals("Andrey.Andreyev", updatedTrainee.getUserName());
        assertEquals(10, updatedTrainee.getPassword().length());
        verify(traineeRepository, times(1)).updateTrainee(trainee);
    }

    @Test
    public void shouldDeleteTrainee() {
        // When
        traineeServiceImpl.deleteTraineeByUserName("Andrey.Andreyev", "SamplePassword");

        // Then
        verify(traineeRepository, times(1)).deleteTraineeByUserName("Andrey.Andreyev", "SamplePassword");
    }

    @Test
    public void shouldChangeTraineeStatus() {
        // When
        traineeServiceImpl.changeTraineeStatus("Andrey.Andreyev", "SamplePassword");

        // Then
        verify(traineeRepository, times(1)).changeTraineeStatus("Andrey.Andreyev", "SamplePassword");
    }

    @Test
    public void shouldGetTraineeTrainingList() {
        // Given
        Trainee trainee1 = new Trainee();
        trainee1.setFirstName("Andrey");
        trainee1.setLastName("Andreyev");
        trainee1.setActive(true);
        trainee1.setDateOfBirth(LocalDate.parse("2000-01-01"));
        trainee1.setAddress("Furmanova 2");
        trainee1.setUserId(1L);
        trainee1.setPassword(UserInformationUtility.generatePassword());

        Trainer trainer = new Trainer();
        trainer.setUserName("Denis.Denisov");

        Training training1 =new Training();
        training1.setTrainingId(1L);
        training1.setTrainingName("Cardio");
        training1.setTrainingDate(LocalDate.parse("2025-01-01"));

        Training training2 =new Training();
        training2.setTrainingId(2L);
        training2.setTrainingName("Yoga");
        training2.setTrainingDate(LocalDate.parse("2025-01-02"));

        trainee1.setTrainings(new ArrayList<>());
        trainee1.getTrainings().add(training1);
        trainee1.getTrainings().add(training2);

        when(traineeRepository.getTraineeTrainingList("Andrey.Andreyev", "123456", "2025-01-01",
                "2025-01-01", "Denis.Denisov")).thenReturn(trainee1.getTrainings());

        // When
        List<Training> retrievedTraineeTrainings = traineeServiceImpl.getTraineeTrainingList("Andrey.Andreyev", "123456", "2025-01-01",
                "2025-01-01", "Denis.Denisov");

        // Then
        assertEquals(2, retrievedTraineeTrainings.size());
        verify(traineeRepository, times(1)).getTraineeTrainingList("Andrey.Andreyev", "123456", "2025-01-01",
                "2025-01-01", "Denis.Denisov");
    }

    @Test
    public void shouldChangeTraineePassword() {
        // When
        traineeServiceImpl.changeTraineePassword("Andrey.Andreyev", "123456789", "987654321");

        // Then
        verify(traineeRepository, times(1)).changeTraineePassword("Andrey.Andreyev", "123456789", "987654321");
    }

    @Test
    public void shouldGetNotAssignedTrainers() {
        // When
        traineeServiceImpl.getNotAssignedTrainers("Andrey.Andreyev", "123456789");

        // Then
        verify(traineeRepository, times(1)).getNotAssignedTrainers("Andrey.Andreyev", "123456789");
    }

}