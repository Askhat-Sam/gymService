package service;

import dev.gymService.dao.TraineeDAO;
import dev.gymService.model.Trainee;
import dev.gymService.service.implementations.TraineeServiceImpl;
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
public class TraineeServiceUnitTest {
    @Mock
    TraineeDAO traineeDAO;
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
        when(traineeDAO.getTraineeByUserName("Andrey.Andreyev")).thenReturn(null);
        when(traineeDAO.create(trainee)).thenReturn(trainee);

        // When
        Trainee createdTrainee = traineeServiceImpl.createTrainee(trainee);

        // Then
        assertNotNull(createdTrainee);
        assertEquals("Andrey.Andreyev", createdTrainee.getUserName());
        assertEquals(10, createdTrainee.getPassword().length());
        verify(traineeDAO, times(1)).create(trainee);
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
        when(traineeDAO.getTraineeByUserName("Andrey.Andreyev")).thenReturn(trainee);
        when(traineeDAO.create(trainee)).thenReturn(trainee);

        // When
        Trainee createdTrainee = traineeServiceImpl.createTrainee(trainee);

        // Then
        assertNotNull(createdTrainee);
        assertEquals("Andrey.Andreyev1", createdTrainee.getUserName());
        assertEquals(10, createdTrainee.getPassword().length());
        verify(traineeDAO, times(1)).create(trainee);
    }

    @Test
    public void shouldCreateNewTraineeWithUserNameWithSuffix2() {
        // Given: Existing users
        Trainee existingTrainee1 = new Trainee();
        existingTrainee1.setUserName("Andrey.Andreyev"); // Already exists

        Trainee existingTrainee2 = new Trainee();
        existingTrainee2.setUserName("Andrey.Andreyev1"); // Already exists

        // New trainee being created
        Trainee newTrainee = new Trainee();
        newTrainee.setFirstName("Andrey");
        newTrainee.setLastName("Andreyev");
        newTrainee.setActive(true);
        newTrainee.setDateOfBirth(LocalDate.parse("2000-01-01"));
        newTrainee.setAddress("Furmanova 2");
        newTrainee.setUserId(1L);

        // Simulating that "Andrey.Andreyev" and "Andrey.Andreyev1" already exist in DB
        when(traineeDAO.getTraineeByUserName("Andrey.Andreyev")).thenReturn(existingTrainee1);
        when(traineeDAO.getTraineeByUserName("Andrey.Andreyev1")).thenReturn(existingTrainee2);
        when(traineeDAO.getTraineeByUserName("Andrey.Andreyev2")).thenReturn(null);
        when(traineeDAO.create(newTrainee)).thenReturn(newTrainee);

        // When
        Trainee createdTrainee = traineeServiceImpl.createTrainee(newTrainee);

        // Then
        assertNotNull(createdTrainee);
        assertEquals("Andrey.Andreyev2", createdTrainee.getUserName());
        assertEquals(10, createdTrainee.getPassword().length());
        verify(traineeDAO, times(1)).create(newTrainee);
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

        when(traineeDAO.update(trainee)).thenReturn(trainee);

        // When
        Trainee updatedTrainee = traineeServiceImpl.updateTrainee(trainee);

        // Then
        assertNotNull(updatedTrainee);
        assertEquals("Furmanova 2", updatedTrainee.getAddress());
        assertEquals("Andrey.Andreyev", updatedTrainee.getUserName());
        assertEquals(10, updatedTrainee.getPassword().length());
        verify(traineeDAO, times(1)).update(trainee);
    }

    @Test
    public void shouldDeleteTrainee() {
        // When
        traineeServiceImpl.deleteTrainee(1L);

        // Then
        verify(traineeDAO, times(1)).delete(1L);
    }

    @Test
    public void shouldGetAllTrainees() {
        // Given
        Trainee trainee1 = new Trainee();
        trainee1.setFirstName("Andrey");
        trainee1.setLastName("Andreyev");
        trainee1.setActive(true);
        trainee1.setDateOfBirth(LocalDate.parse("2000-01-01"));
        trainee1.setAddress("Furmanova 2");
        trainee1.setUserId(1L);
        trainee1.setPassword(UserInformationUtility.generatePassword());

        Trainee trainee2 = new Trainee();
        trainee2.setFirstName("Ivan");
        trainee2.setLastName("Ivanov");
        trainee2.setActive(true);
        trainee2.setDateOfBirth(LocalDate.parse("1990-01-01"));
        trainee2.setAddress("Mailina 2");
        trainee2.setUserId(2L);
        trainee2.setPassword(UserInformationUtility.generatePassword());

        List<Trainee> trainees = Arrays.asList(trainee1, trainee2);
        when(traineeDAO.getAll()).thenReturn(trainees);

        // When
        List<Trainee> retrievedTrainees = traineeServiceImpl.getAllTrainee();

        // Then
        assertEquals(2, retrievedTrainees.size());
        verify(traineeDAO, times(1)).getAll();
    }

    @Test
    public void shouldGetTraineeById() {
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

        when(traineeDAO.getById(1L)).thenReturn(trainee);

        // When
        Trainee retrievedTrainee = traineeServiceImpl.getTraineeById(1L);

        // Then
        assertNotNull(retrievedTrainee);
        assertEquals("Andrey.Andreyev", retrievedTrainee.getUserName());
        assertEquals(10, retrievedTrainee.getPassword().length());
        verify(traineeDAO, times(1)).getById(1L);
    }
}