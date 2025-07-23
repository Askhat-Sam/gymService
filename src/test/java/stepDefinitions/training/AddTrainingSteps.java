package stepDefinitions.training;

import dev.gymService.model.ActionType;
import dev.gymService.model.dto.TrainingAddRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.*;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class AddTrainingSteps {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private Environment environment;
    private ResponseEntity<String> response;
    private TrainingAddRequest trainingAddRequest;

    @Given("a valid training add request with trainee {string} and trainer {string} and trainingName {string} and trainingDate {string} and trainingDuration {string} and actionType {string}")
    public void a_valid_training_add_request(String traineeUsername, String trainerUsername, String trainingName, String trainingDate, String trainingDuration, String actionType) {
        trainingAddRequest = new TrainingAddRequest();
        trainingAddRequest.setTraineeUserName(traineeUsername);
        trainingAddRequest.setTrainerUsername(trainerUsername);
        trainingAddRequest.setTrainingName(trainingName);
        trainingAddRequest.setTrainingDate(LocalDate.parse(trainingDate));
        trainingAddRequest.setTrainingDuration(Long.parseLong(trainingDuration));
        trainingAddRequest.setActionType(ActionType.valueOf(actionType));
    }

    @Given("a invalid training add request with trainee {string} and trainer {string} and trainingName {string} and trainingDate {string} and trainingDuration {string} and actionType {string}")
    public void a_invalid_training_add_request(String traineeUsername, String trainerUsername, String trainingName, String trainingDate, String trainingDuration, String actionType) {
        trainingAddRequest = new TrainingAddRequest();
        trainingAddRequest.setTraineeUserName(traineeUsername);
        trainingAddRequest.setTrainerUsername(trainerUsername);
        trainingAddRequest.setTrainingName(trainingName);
        trainingAddRequest.setTrainingDate(LocalDate.parse(trainingDate));
        trainingAddRequest.setTrainingDuration(Long.parseLong(trainingDuration));
        trainingAddRequest.setActionType(ActionType.valueOf(actionType));
    }

    @When("the client sends a request to add a training to endpoint {string}")
    public void the_client_sends_the_request(String endpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(environment.getProperty("test.jwt.token"));

        HttpEntity<TrainingAddRequest> entity = new HttpEntity<>(trainingAddRequest, headers);

        response = restTemplate.exchange(
                endpoint,
                HttpMethod.POST,
                entity,
                String.class
        );
    }

    @Then("the training add response status should be {int}")
    public void the_response_status_should_be(Integer statusCode) {
        assertThat(response.getStatusCodeValue()).isEqualTo(statusCode);
    }

    @Then("the training add response message should be {string}")
    public void the_response_message_should_be(String expectedMessage) {
        assertThat(response.getBody()).contains(expectedMessage);
    }

    @Then("the training add response should contain error message {string}")
    public void the_response_should_contain_error_message(String errorMessage) {
        assertThat(response.getBody()).contains(errorMessage);
    }
}
