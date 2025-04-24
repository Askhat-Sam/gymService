package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gymService.Application;
import dev.gymService.model.Role;
import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.model.dto.*;
import dev.gymService.service.interfaces.TraineeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
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

@SpringBootTest
@AutoConfigureMockMvc
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
        trainer.setRole(Role.TRAINER);
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
        trainee.setRole(Role.TRAINEE);

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

        // When
        when(traineeService.createTrainee(any(Trainee.class))).thenReturn(trainee);

        // When and then
        mockMvc.perform(post("/gym-service/trainees/registerNewTrainee")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Ivan.Ivanov"))
                .andExpect(jsonPath("$.token").exists())
                .andDo(print());
    }

    @Test
    public void shouldLoginTrainee() throws Exception {
        // Given
        UserLoginRequest traineeLoginRequest = new UserLoginRequest("Ivan.Ivanov", "1234567890");

        String requestBody = objectMapper.writeValueAsString(traineeLoginRequest);

        when(traineeService.getTraineeByUserName("Ivan.Ivanov")).thenReturn(trainee);

        // When and then
        mockMvc.perform(post("/gym-service/authentication/login")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Ivan.Ivanov"))
                .andExpect(jsonPath("$.role").value("TRAINEE"))
                .andExpect(jsonPath("$.token").exists())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Ivan.Ivanov", roles = "TRAINEE")
    public void shouldChangePassword() throws Exception {
        // Given
        TraineePasswordChangeRequest traineePasswordChangeRequest = new TraineePasswordChangeRequest();
        traineePasswordChangeRequest.setUserName("Ivan.Ivanov");
        traineePasswordChangeRequest.setNewPassword("0989837495");


        String requestBody = objectMapper.writeValueAsString(traineePasswordChangeRequest);

        when(traineeService.changeTraineePassword("Ivan.Ivanov", "1234567890")).thenReturn(true);

        // When and then
        mockMvc.perform(MockMvcRequestBuilders.put("/gym-service/trainees/changePassword")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Password has been successfully changed"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Ivan.Ivanov", roles = "TRAINEE")
    public void shouldGetTraineeProfile() throws Exception {
        // Given
        TraineeProfileRequest traineeProfileRequest = new TraineeProfileRequest();
        traineeProfileRequest.setUserName("Ivan.Ivanov");

        String requestBody = objectMapper.writeValueAsString(traineeProfileRequest);

        when(traineeService.getTraineeByUserName("Ivan.Ivanov")).thenReturn(trainee);

        // When and then
        mockMvc.perform(get("/gym-service/trainees/getTraineeProfile")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Ivan.Ivanov", roles = "TRAINEE")
    public void shouldToggleTraineeStatus() throws Exception {
        // Given
        TraineeStatusToggleRequest traineeStatusToggleRequest = new TraineeStatusToggleRequest();
        traineeStatusToggleRequest.setUserName("Ivan.Ivanov");
        traineeStatusToggleRequest.setIsActive(true);

        String requestBody = objectMapper.writeValueAsString(traineeStatusToggleRequest);

        when(traineeService.changeTraineeStatus("Ivan.Ivanov")).thenReturn(true);

        // When and then
        mockMvc.perform(patch("/gym-service/trainees/toggleTraineeStatus")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User status has been successfully changed"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Ivan.Ivanov", roles = "TRAINEE")
    public void shouldDeleteTrainee() throws Exception {
        // Given
        TraineeDeleteRequest traineeDeleteRequest = new TraineeDeleteRequest();
        traineeDeleteRequest.setUserName("Ivan.Ivanov");

        String requestBody = objectMapper.writeValueAsString(traineeDeleteRequest);

        when(traineeService.deleteTraineeByUserName("Ivan.Ivanov")).thenReturn(true);

        // When and then
        mockMvc.perform(delete("/gym-service/trainees/deleteTrainee")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User has been successfully deleted"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Ivan.Ivanov", roles = "TRAINEE")
    public void shouldGetTraineeTrainings() throws Exception {
        // Given
        TraineeTrainingsRequest traineeTrainingsRequest = new TraineeTrainingsRequest();
        traineeTrainingsRequest.setUserName("Ivan.Ivanov");
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
                any(), any(), any(), any(), any()))
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
    @WithMockUser(username = "Ivan.Ivanov", roles = "TRAINEE")
    public void shouldUpdateTrainee() throws Exception {
        // Given
        TraineeTrainingsRequest traineeTrainingsRequest = new TraineeTrainingsRequest();
        traineeTrainingsRequest.setUserName("Ivan.Ivanov");
        traineeTrainingsRequest.setFromDate("2025-01-01");
        traineeTrainingsRequest.setToDate("2025-02-01");
        traineeTrainingsRequest.setTrainerName("Vladislav.Bekmeev");
        traineeTrainingsRequest.setTrainingType(1L);



        String requestBody = objectMapper.writeValueAsString(traineeTrainingsRequest);

        // Mock the service method to return the list of trainings
        when(traineeService.getTraineeTrainingList(
                any(), any(), any(), any(), any()))
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
    @WithMockUser(username = "Ivan.Ivanov", roles = "TRAINEE")
    public void shouldUpdateTraineeTrainers() throws Exception {
        // Given

        TraineeTrainersListUpdateRequest traineeTrainersListUpdateRequest = new TraineeTrainersListUpdateRequest();
        traineeTrainersListUpdateRequest.setUserName("Ivan.Ivanov");
        traineeTrainersListUpdateRequest.setTrainers(trainee.getTrainers());

        String requestBody = objectMapper.writeValueAsString(traineeTrainersListUpdateRequest);

        // Add trainers to the trainee request
        traineeTrainersListUpdateRequest.setTrainers(trainee.getTrainers());

        // Mock the service methods
        when(traineeService.getTraineeByUserName(any())).thenReturn(trainee);
        when(traineeService.updateTrainersList(any(), any())).thenReturn(trainee.getTrainers());


        // When and Then
        mockMvc.perform(MockMvcRequestBuilders.put("/gym-service/trainees/updateTraineeTrainers")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trainers[0].userName").value("Vladislav.Bekmeev"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Ivan.Ivanov", roles = "TRAINEE")
    public void shouldGetNotAssignedOnTraineeTrainers() throws Exception {
        // Given
        TraineeNotAssignedTrainersRequest traineeNotAssignedTrainersRequest = new TraineeNotAssignedTrainersRequest();
        traineeNotAssignedTrainersRequest.setUserName("Ivan.Ivanov");

        // Convert to JSON
        String requestBody = objectMapper.writeValueAsString(traineeNotAssignedTrainersRequest);

        // Mock the service methods
        when(traineeService.getNotAssignedTrainers(any())).thenReturn(trainee.getTrainers());

        // When and Then
        mockMvc.perform(get("/gym-service/trainees/getNotAssignedOnTraineeTrainers")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userName").value("Vladislav.Bekmeev"))
                .andDo(print());
    }
}

