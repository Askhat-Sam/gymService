package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gymService.Application;
import dev.gymService.controller.TrainingController;
import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.model.TrainingType;
import dev.gymService.model.dto.TrainingAddRequest;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.service.interfaces.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = Application.class)
public class TrainingControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    TrainingService trainingService;
    @MockitoBean
    TraineeService traineeService;
    @MockitoBean
    TrainerService trainerService;

    @Autowired
    ObjectMapper objectMapper;
    private Training training;
    private Trainer trainer;
    private Trainee trainee;
    private TrainingType trainingType;
    @BeforeEach
    public void setUp(){
        // Create trainee
        trainee = new Trainee();
        trainee.setFirstName("Ivan");
        trainee.setLastName("Ivanov");
        trainee.setUserName("Ivan.Ivanov");
        trainee.setPassword("1234567890");
        trainee.setIsActive(false);

        // Create trainer
         trainer = new Trainer();
        trainer.setUserName("Vladislav.Bekmeev");

        //Create TrainingType
        trainingType = new TrainingType();
        trainingType.setId(3L);

        // Create training
         training = new Training();
        training.setTraineeId(1L);
        training.setTrainerId(2L);
        training.setTrainingName("Yoga");
        training.setTrainingTypeId(1L);
        training.setTrainingDate(LocalDate.parse("2025-01-03"));
        training.setTrainingDuration(45L);
        training.setTrainee(trainee);
        training.setTrainer(trainer);
    }

    @Test
    public void shouldAddTraining() throws Exception {
        // Given
        TrainingAddRequest trainingAddRequest = new TrainingAddRequest();
        trainingAddRequest.setTrainingName("Yoga");
        trainingAddRequest.setTraineeUserName("Ivan.Ivanov");
        trainingAddRequest.setTrainerUsername("Vladislav.Bekmeev");
        trainingAddRequest.setTrainingDate(LocalDate.parse("2025-01-01"));
        trainingAddRequest.setTrainingDuration(23L);
        trainingAddRequest.setTraineePassword("123456789");
        trainingAddRequest.setTrainerPassword("123456789");

        String requestBody = objectMapper.writeValueAsString(trainingAddRequest);
        when(trainingService.addTraining(any())).thenReturn(training);
        when(traineeService.getTraineeByUserName(any(), any())).thenReturn(trainee);
        when(trainerService.getTrainerByUserName(any(), any())).thenReturn(trainer);
        when(trainingService.getTrainingTypeIdByTrainingName(any())).thenReturn(new TrainingType());

        // then
        mockMvc.perform(post("/gym-service/trainings/addTraining")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Training has been added successfully"));
    }

    @Test
    public void shouldGetTrainingTypes() throws Exception {
        // Given
        when(trainingService.getTrainingTypes()).thenReturn(List.of(trainingType));

        // then
        mockMvc.perform(get("/gym-service/trainings/getTrainingTypes")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("3"));
    }
}

