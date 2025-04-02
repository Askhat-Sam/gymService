package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gymService.Application;
import dev.gymService.controller.TraineeController;
import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.model.dto.*;
import dev.gymService.service.interfaces.TraineeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TraineeController.class)
@ContextConfiguration(classes = Application.class)
public class TraineeControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    TraineeService traineeService;
    @Autowired
    ObjectMapper objectMapper;
    private Trainee trainee;
    private Trainer trainer;
    @BeforeEach
    public void setUp(){
        // Create trainers
       trainer = new Trainer();
        trainer.setFirstName("Vladislav");
        trainer.setLastName("Bikmeev");
        trainer.setUserName("Vladislav.Bekmeev");
        trainer.setSpecialization(2L);
        trainer.setPassword("123456789");
        trainer.setTrainings(List.of(new Training()));
        trainer.setUserId(2L);
        trainer.setIsActive(true);
        List<Trainer> trainers = new ArrayList<>();
        trainers.add(trainer);

        Training training = new Training();
        training.setTrainingId(1L);
        training.setTrainingName("Yoga");
        training.setTrainer(trainer);
        training.setTrainingTypeId(1L);
        training.setTrainee(trainee);
        training.setTraineeId(1L);
        training.setTrainerId(2L);
        training.setTrainingDuration(45L);
        training.setTrainingDate(LocalDate.parse("2025-04-03"));

        trainee = new Trainee();
        trainee.setFirstName("Ivan");
        trainee.setLastName("Ivanov");
        trainee.setUserName("Ivan.Ivanov");
        trainee.setPassword("1234567890");
        trainee.setTrainers(trainers);
        trainee.setTrainings(List.of(training));
        trainee.setIsActive(false);

        // Create trainings
        List<Training> trainings = new ArrayList<>();
        trainings.add(training);
    }

    @Test
    public void shouldCreateTrainee() throws Exception {
        // Given
        TraineeRegistrationRequest traineeRegistrationRequest = new TraineeRegistrationRequest();
        traineeRegistrationRequest.setFirstName("Ivan");
        traineeRegistrationRequest.setLastName("Ivanov");
        traineeRegistrationRequest.setAddress("Mailina 35");
        traineeRegistrationRequest.setDateOfBirth(LocalDate.parse("1990-02-03"));

        String requestBody = objectMapper.writeValueAsString(traineeRegistrationRequest);
        when(traineeService.createTrainee(any(Trainee.class))).thenReturn(trainee);

        // When and then
        mockMvc.perform(post("/gym-service/trainees/registerNewTrainee")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Ivan.Ivanov"));
    }

    @Test
    public void shouldLoginTrainee() throws Exception {
        // Given
        TraineeLoginRequest traineeLoginRequest = new TraineeLoginRequest("Ivan.Ivanov", "1234567890");

        String requestBody = objectMapper.writeValueAsString(traineeLoginRequest);

        when(traineeService.getTraineeByUserName("Ivan.Ivanov", "1234567890")).thenReturn(trainee);

        // When and then
        mockMvc.perform(get("/gym-service/trainees/loginTrainee")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andDo(print());
    }

    @Test
    public void shouldChangePassword() throws Exception {
        // Given
        TraineePasswordChangeRequest traineePasswordChangeRequest = new TraineePasswordChangeRequest();
        traineePasswordChangeRequest.setUserName("Ivan.Ivanov");
        traineePasswordChangeRequest.setOldPassword("1234567890");
        traineePasswordChangeRequest.setNewPassword("0989837495");


        String requestBody = objectMapper.writeValueAsString(traineePasswordChangeRequest);

        when(traineeService.changeTraineePassword("Ivan.Ivanov", "1234567890", "0989837495")).thenReturn(true);

        // When and then
        mockMvc.perform(MockMvcRequestBuilders.put("/gym-service/trainees/changePassword")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Password has been successfully changed"))
                .andDo(print());
    }

    @Test
    public void shouldGetTraineeProfile() throws Exception {
        // Given
        TraineeProfileRequest traineeProfileRequest = new TraineeProfileRequest();
        traineeProfileRequest.setUserName("Ivan.Ivanov");
        traineeProfileRequest.setPassword("1234567890");

        String requestBody = objectMapper.writeValueAsString(traineeProfileRequest);

        when(traineeService.getTraineeByUserName("Ivan.Ivanov", "1234567890")).thenReturn(trainee);

        // When and then
        mockMvc.perform(get("/gym-service/trainees/getTraineeProfile")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andDo(print());
    }

    @Test
    public void shouldToggleTraineeStatus() throws Exception {
        // Given
        TraineeStatusToggleRequest traineeStatusToggleRequest = new TraineeStatusToggleRequest();
        traineeStatusToggleRequest.setUserName("Ivan.Ivanov");
        traineeStatusToggleRequest.setPassword("1234567890");
        traineeStatusToggleRequest.setIsActive(true);

        String requestBody = objectMapper.writeValueAsString(traineeStatusToggleRequest);

        when(traineeService.changeTraineeStatus("Ivan.Ivanov", "1234567890")).thenReturn(true);

        // When and then
        mockMvc.perform(patch("/gym-service/trainees/toggleTraineeStatus")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User status has been successfully changed"))
                .andDo(print());
    }

    @Test
    public void shouldDeleteTrainee() throws Exception {
        // Given
        TraineeDeleteRequest traineeDeleteRequest = new TraineeDeleteRequest();
        traineeDeleteRequest.setUserName("Ivan.Ivanov");
        traineeDeleteRequest.setPassword("1234567890");

        String requestBody = objectMapper.writeValueAsString(traineeDeleteRequest);

        when(traineeService.deleteTraineeByUserName("Ivan.Ivanov", "1234567890")).thenReturn(true);

        // When and then
        mockMvc.perform(delete("/gym-service/trainees/deleteTrainee")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User has been successfully deleted"))
                .andDo(print());
    }

    @Test
    public void shouldGetTraineeTrainings() throws Exception {
        // Given
        TraineeTrainingsRequest traineeTrainingsRequest = new TraineeTrainingsRequest();
        traineeTrainingsRequest.setUserName("Ivan.Ivanov");
        traineeTrainingsRequest.setPassword("1234567890");
        traineeTrainingsRequest.setFromDate("2025-01-01");
        traineeTrainingsRequest.setToDate("2025-02-01");
        traineeTrainingsRequest.setTrainerName("Vladislav.Bekmeev");
        traineeTrainingsRequest.setTrainingType(1L);

        Training training = new Training();
        training.setTrainingId(1L);
        training.setTrainingName("Yoga");
        training.setTrainingDate(LocalDate.parse("2025-01-02"));
        training.setTrainingDuration(23L);
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingTypeId(1L);

        List<Training> trainings = new ArrayList<>();
        trainings.add(training);

        String requestBody = objectMapper.writeValueAsString(traineeTrainingsRequest);

        // Mock the service method to return the list of trainings
        when(traineeService.getTraineeTrainingList(
                any(), any(), any(), any(), any(), any()))
                .thenReturn(trainings);

        // Then
        mockMvc.perform(get("/gym-service/trainees/getTraineeTrainings")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].trainingName").value("Yoga"))
                .andDo(print());
    }

    @Test
    public void shouldUpdateTrainee() throws Exception {
        // Given
        TraineeTrainingsRequest traineeTrainingsRequest = new TraineeTrainingsRequest();
        traineeTrainingsRequest.setUserName("Ivan.Ivanov");
        traineeTrainingsRequest.setPassword("1234567890");
        traineeTrainingsRequest.setFromDate("2025-01-01");
        traineeTrainingsRequest.setToDate("2025-02-01");
        traineeTrainingsRequest.setTrainerName("Vladislav.Bekmeev");
        traineeTrainingsRequest.setTrainingType(1L);



        String requestBody = objectMapper.writeValueAsString(traineeTrainingsRequest);

        // Mock the service method to return the list of trainings
        when(traineeService.getTraineeTrainingList(
                any(), any(), any(), any(), any(), any()))
                .thenReturn(trainee.getTrainings());

        // When and Then
        mockMvc.perform(get("/gym-service/trainees/getTraineeTrainings")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].trainingName").value("Yoga"))
                .andDo(print());
    }

    @Test
    public void shouldUpdateTraineeTrainers() throws Exception {
        // Given
        TraineeTrainersListUpdateRequest traineeTrainersListUpdateRequest = new TraineeTrainersListUpdateRequest();
        traineeTrainersListUpdateRequest.setUserName("Ivan.Ivanov");
        traineeTrainersListUpdateRequest.setPassword("1234567890");
        traineeTrainersListUpdateRequest.setTrainers(trainee.getTrainers());

        String requestBody = objectMapper.writeValueAsString(traineeTrainersListUpdateRequest);

        // Add trainers to the trainee request
        traineeTrainersListUpdateRequest.setTrainers(trainee.getTrainers());

        // Mock the service methods
        when(traineeService.getTraineeByUserName(any(), any())).thenReturn(trainee);
        when(traineeService.updateTrainersList(any(), any(), any())).thenReturn(trainee.getTrainers());


        // When and Then
        mockMvc.perform(MockMvcRequestBuilders.put("/gym-service/trainees/updateTraineeTrainers")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trainers[0].userName").value("Vladislav.Bekmeev"))
                .andDo(print());
    }

    @Test
    public void shouldGetNotAssignedOnTraineeTrainers() throws Exception {
        // Given
        TraineeNotAssignedTrainersRequest traineeNotAssignedTrainersRequest = new TraineeNotAssignedTrainersRequest();
        traineeNotAssignedTrainersRequest.setUserName("Ivan.Ivanov");
        traineeNotAssignedTrainersRequest.setPassword("1234567890");

        // Convert to JSON
        String requestBody = objectMapper.writeValueAsString(traineeNotAssignedTrainersRequest);

        // Mock the service methods
        when(traineeService.getNotAssignedTrainers(any(), any())).thenReturn(trainee.getTrainers());

        // When and Then
        mockMvc.perform(get("/gym-service/trainees/getNotAssignedOnTraineeTrainers")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userName").value("Vladislav.Bekmeev"))
                .andDo(print());
    }
}

