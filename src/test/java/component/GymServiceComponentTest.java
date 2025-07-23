package component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gymService.Application;
import dev.gymService.model.*;
import dev.gymService.model.dto.TraineeRegistrationRequest;
import dev.gymService.model.dto.TrainerRegistrationRequest;
import dev.gymService.model.dto.TrainingAddRequest;
import dev.gymService.model.dto.TrainingWorkloadRequest;
import dev.gymService.service.interfaces.WorkloadServiceClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(classes = Application.class)
@Testcontainers
public class GymServiceComponentTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private WorkloadServiceClient workloadServiceClient;
    @Container
    static final RabbitMQContainer rabbitmq = new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.11.11-alpine"));

    @DynamicPropertySource
    static void overridePropertiesInternal(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbitmq::getHost);
        registry.add("spring.rabbitmq.port", rabbitmq::getAmqpPort);
        registry.add("spring.rabbitmq.username", rabbitmq::getAdminUsername);
        registry.add("spring.rabbitmq.password", rabbitmq::getAdminPassword);
    }
    @Container
    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0.36")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("init.sql");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }
    @BeforeAll
    static void beforeAll(){
        mysqlContainer.start();
        rabbitmq.start();
    }
    @AfterAll
    static void afterAll(){
        mysqlContainer.stop();
        rabbitmq.stop();
    }
    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${rabbitmq.update.exchange-name}")
    private String exchange;
    @Value("${rabbitmq.get-summary.routing-key}")
    private String summaryRequestRoutingKey;

    @Test
    void testTraineeAndTraineeAndTrainingCreation() throws Exception {
        // Register new trainee
        TraineeRegistrationRequest traineeRegistrationRequest = new TraineeRegistrationRequest();
        traineeRegistrationRequest.setFirstName("Ivan");
        traineeRegistrationRequest.setLastName("Ivanov");
        traineeRegistrationRequest.setAddress("Mailina 35");
        traineeRegistrationRequest.setDateOfBirth(LocalDate.parse("1990-02-03"));

        String requestBodyTraineeRegistrationRequest = objectMapper.writeValueAsString(traineeRegistrationRequest);

        MvcResult createdTrainee = mockMvc.perform(post("/gym-service/trainees/registerNewTrainee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyTraineeRegistrationRequest))
                .andExpect(status().isOk())
                .andReturn();

        // Extract response content as String
        String responseContentTrainee = createdTrainee.getResponse().getContentAsString();

        ObjectMapper objectMapperTrainee = new ObjectMapper();
        JsonNode jsonNodeTrainee = objectMapperTrainee.readTree(responseContentTrainee);

        String tokenTrainee = jsonNodeTrainee.get("token").asText();
        String userNameTrainee = jsonNodeTrainee.get("userName").asText();

        // Register new trainee
        TrainerRegistrationRequest trainerRegistrationRequest = new TrainerRegistrationRequest();
        trainerRegistrationRequest.setFirstName("Vladislav");
        trainerRegistrationRequest.setLastName("Bekmeev");
        trainerRegistrationRequest.setSpecialization(2L);

        String requestBodyTrainerRegistrationRequest = objectMapper.writeValueAsString(trainerRegistrationRequest);

        MvcResult createdTrainer = mockMvc.perform(post("/gym-service/trainers/registerNewTrainer")
                        .contentType("application/json")
                        .content(requestBodyTrainerRegistrationRequest))
                .andExpect(status().isOk())
                .andReturn();;

        // Extract response content as String
        String responseContentTrainer = createdTrainer.getResponse().getContentAsString();

        ObjectMapper objectMapperTrainer = new ObjectMapper();
        JsonNode jsonNodeTrainer = objectMapperTrainer.readTree(responseContentTrainer);

        String userNameTrainer = jsonNodeTrainer.get("userName").asText();
        String tokenTrainer = jsonNodeTrainer.get("token").asText();

        // Create Training
        TrainingAddRequest trainingAddRequest = new TrainingAddRequest();
        trainingAddRequest.setTrainingName("Yoga");
        trainingAddRequest.setTraineeUserName(userNameTrainee);
        trainingAddRequest.setTrainerUsername(userNameTrainer);
        trainingAddRequest.setTrainingDate(LocalDate.parse("2025-01-01"));
        trainingAddRequest.setTrainingDuration(23L);

        String requestBodyTraining = objectMapper.writeValueAsString(trainingAddRequest);

        mockMvc.perform(post("/gym-service/trainings/addTraining")
                        .header("Authorization", "Bearer " + tokenTrainer)
                        .contentType("application/json")
                        .content(requestBodyTraining))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Training has been added successfully"))
                .andReturn();

              // Request workload for created trainer
        TrainingWorkload stubTrainignWorkload = new TrainingWorkload();
        Map<Integer, Long> monthTrainingWorkload = new HashMap<>();
        monthTrainingWorkload.put(1, 23L);
        Map<Integer, Map<Integer, Long>> trainingDuration = new HashMap<>();
        trainingDuration.put(2025,monthTrainingWorkload);

        stubTrainignWorkload.setTrainingDuration(trainingDuration);

        TrainingWorkloadRequest request = new TrainingWorkloadRequest();
        request.setTrainerUsername(userNameTrainer);

        when(rabbitTemplate.convertSendAndReceive(eq(exchange),
                eq(summaryRequestRoutingKey),
                any(TrainingWorkloadRequest.class))).thenReturn(stubTrainignWorkload);

        MvcResult returnedTrainerWorkload = mockMvc.perform(get("/gym-service/trainings/getWorkloadSummary")
                        .header("Authorization", "Bearer " + tokenTrainee)
                        .param("username", userNameTrainer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trainingDuration.2025.1").value(23))
                .andReturn();
    }
}
