package stepDefinitions.trainer;

import dev.gymService.model.dto.TraineeTrainingsRequest;
import dev.gymService.model.dto.TrainerTrainingsRequest;
import dev.gymService.model.dto.TrainingDTO;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetTrainerTrainingsSteps {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private Environment environment;
    private ResponseEntity<TrainingDTO[]> response;

    @When("the trainer sends a request to get trainings for username {string} from date {string} to date {string} with trainee {string} and training type {string} to endpoint {string}")
    public void the_client_sends_a_request_to_get_trainings(String username, String fromDate, String toDate, String traineeUsername, String trainingType, String endpoint) {
        TrainerTrainingsRequest request = new TrainerTrainingsRequest();
        request.setUserName(username);
        request.setFromDate(fromDate);
        request.setToDate(toDate);
        request.setTraineeName(traineeUsername);
        request.setTrainingType(Long.valueOf(trainingType));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(environment.getProperty("test.jwt.token"));

        HttpEntity<TrainerTrainingsRequest> entity = new HttpEntity<>(request, headers);

        response = restTemplate.exchange(
                endpoint,
                HttpMethod.POST,
                entity,
                TrainingDTO[].class
        );
    }

    @Then("the trainer training response status should be {int}")
    public void the_response_status_should_be(Integer statusCode) {
        assertThat(response.getStatusCodeValue()).isEqualTo(statusCode);
    }

    @Then("the trainer training response should contain a list of trainings")
    public void the_response_should_contain_trainings() {
        TrainingDTO[] body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.length).isGreaterThanOrEqualTo(0);
    }
}
