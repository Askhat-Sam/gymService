package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gymService.Application;
import dev.gymService.model.Role;
import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.model.dto.*;
import dev.gymService.service.interfaces.TrainerService;
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
public class TrainerControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    TrainerService trainerService;
    @Autowired
    ObjectMapper objectMapper;
    private Trainer trainer;
    private Trainee trainee;
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
        trainer.setTrainees(List.of(new Trainee()));
        trainer.setRole(Role.TRAINER);
        List<Trainer> trainers = new ArrayList<>();
        trainers.add(trainer);

        // Create trainings
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
        List<Training> trainings = new ArrayList<>();
        trainings.add(training);

        trainee = new Trainee();
        trainee.setFirstName("Ivan");
        trainee.setLastName("Ivanov");
        trainee.setUserName("Ivan.Ivanov");
        trainee.setPassword("1234567890");
        trainee.setTrainers(trainers);
        trainee.setTrainings(List.of(training));
        trainee.setIsActive(false);
        trainee.setRole(Role.TRAINEE);
        trainee.setTrainings(trainings);
    }

    @Test
    public void shouldCreateTrainer() throws Exception {
        // Given
        TrainerRegistrationRequest trainerRegistrationRequest = new TrainerRegistrationRequest();
        trainerRegistrationRequest.setFirstName("Vladislav");
        trainerRegistrationRequest.setLastName("Bekmeev");
        trainerRegistrationRequest.setSpecialization(2L);

        String requestBody = objectMapper.writeValueAsString(trainerRegistrationRequest);
        when(trainerService.createTrainer(any(Trainer.class))).thenReturn(trainer);

        // When and then
        mockMvc.perform(post("/gym-service/trainers/registerNewTrainer")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Vladislav.Bekmeev"));
    }

    @Test
    public void shouldLoginTrainer() throws Exception {
        // Given
        UserLoginRequest trainerLoginRequest = new UserLoginRequest("Vladislav.Bekmeev", "1234567890");

        String requestBody = objectMapper.writeValueAsString(trainerLoginRequest);

        when(trainerService.getTrainerByUserName(any())).thenReturn(trainer);

        // When and then
        mockMvc.perform(post("/gym-service/authentication/login")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Vladislav.Bekmeev"))
                .andExpect(jsonPath("$.role").value("TRAINER"))
                .andExpect(jsonPath("$.token").exists())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Vladislav.Bekmeev", roles = "TRAINER")
    public void shouldChangePassword() throws Exception {
        // Given
        TrainerPasswordChangeRequest trainerPasswordChangeRequest = new TrainerPasswordChangeRequest();
        trainerPasswordChangeRequest.setUserName("Vladislav.Bekmeev");
        trainerPasswordChangeRequest.setNewPassword("0989837495");


        String requestBody = objectMapper.writeValueAsString(trainerPasswordChangeRequest);

        when(trainerService.changeTrainerPassword(any(), any())).thenReturn(true);

        // When and then
        mockMvc.perform(MockMvcRequestBuilders.put("/gym-service/trainers/changePassword")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Password has been successfully changed"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Vladislav.Bekmeev", roles = "TRAINER")
    public void shouldGetTrainerProfile() throws Exception {
        // Given
        TrainerProfileRequest trainerProfileRequest = new TrainerProfileRequest();
        trainerProfileRequest.setUserName("Vladislav.Bekmeev");

        String requestBody = objectMapper.writeValueAsString(trainerProfileRequest);

        when(trainerService.getTrainerByUserName(any())).thenReturn(trainer);

        // When and then
        mockMvc.perform(get("/gym-service/trainers/getTrainerByUsername")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Vladislav"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Vladislav.Bekmeev", roles = "TRAINER")
    public void shouldToggleTrainerStatus() throws Exception {
        // Given
        TrainerStatusToggleRequest trainerStatusToggleRequest = new TrainerStatusToggleRequest();
        trainerStatusToggleRequest.setUserName("Vladislav.Bekmeev");
        trainerStatusToggleRequest.setIsActive(true);

        String requestBody = objectMapper.writeValueAsString(trainerStatusToggleRequest);

        when(trainerService.changeTrainerStatus(any())).thenReturn(true);

        // When and then
        mockMvc.perform(patch("/gym-service/trainers/toggleTrainerStatus")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User status has been successfully changed"))
                .andDo(print());
    }


    @Test
    @WithMockUser(username = "Vladislav.Bekmeev", roles = "TRAINER")
    public void shouldGetTrainerTrainings() throws Exception {
        // Given
        TraineeTrainingsRequest traineeTrainingsRequest = new TraineeTrainingsRequest();
        traineeTrainingsRequest.setUserName("Ivan.Ivanov");
        traineeTrainingsRequest.setFromDate("2025-01-01");
        traineeTrainingsRequest.setToDate("2025-02-01");
        traineeTrainingsRequest.setTrainerName("Vladislav.Bekmeev");
        traineeTrainingsRequest.setTrainingType(1L);


        String requestBody = objectMapper.writeValueAsString(traineeTrainingsRequest);

        // Mock the service method to return the list of trainings
        when(trainerService.getTrainerTrainingList(
                any(), any(), any(), any(), any()))
                .thenReturn(trainee.getTrainings());

        // When and Then
        mockMvc.perform(get("/gym-service/trainers/getTrainerTrainings")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].trainingName").value("Yoga"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Vladislav.Bekmeev", roles = "TRAINER")
    public void shouldUpdateTrainer() throws Exception {
        // Given
        TrainerProfileUpdateRequest trainerProfileUpdateRequest = new TrainerProfileUpdateRequest();
        trainerProfileUpdateRequest.setUserName("Ivan.Ivanov");
        trainerProfileUpdateRequest.setPassword("1234567890");
        trainerProfileUpdateRequest.setFirstName("Ivan");
        trainerProfileUpdateRequest.setSpecialization(2L);
        trainerProfileUpdateRequest.setIsActive(true);
        trainerProfileUpdateRequest.setLastName("Ivanov");

        String requestBody = objectMapper.writeValueAsString(trainerProfileUpdateRequest);

        // Mock the service method to return the list of trainings
        when(trainerService.getTrainerByUserName(any())).thenReturn(trainer);
        when(trainerService.updateTrainer(any(), any())).thenReturn(trainer);

        // When and Then
        mockMvc.perform(put("/gym-service/trainers/updateTrainer")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Vladislav.Bekmeev"))
                .andDo(print());
    }

}

