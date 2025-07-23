package stepDefinitions.trainee;
import dev.gymService.model.dto.TraineePasswordChangeRequest;
import dev.gymService.model.dto.TraineeTrainingsRequest;
import dev.gymService.model.dto.TrainerTrainingsRequest;
import dev.gymService.model.dto.TrainingDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.*;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
public class GetTraineeTrainingsSteps {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private Environment environment;
    private ResponseEntity<TrainingDTO[]> response;

@When("the trainee sends a request to get trainings for trainee {string} from date {string} to date {string} with trainer {string} and training type {string} to endpoint {string}")
public void the_client_sends_a_request_to_get_trainings(String traineeUsername, String fromDate, String toDate, String trainerUsername, String trainingType, String endpoint) {
    TraineeTrainingsRequest  request = new TraineeTrainingsRequest();
    request.setUserName(traineeUsername);
    request.setFromDate(fromDate);
    request.setToDate(toDate);
    request.setTrainerName(trainerUsername);
    request.setTrainingType(Long.valueOf(trainingType));;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(environment.getProperty("test.jwt.token"));

    HttpEntity<TraineeTrainingsRequest> entity = new HttpEntity<>(request, headers);

    response = restTemplate.exchange(
            endpoint,
            HttpMethod.POST,
            entity,
            TrainingDTO[].class
    );

    System.out.println("@@@@@@2Trainee trainings: " + Arrays.toString(response.getBody()));
}
    @Then("the trainee training response status should be {int}")
    public void the_response_status_should_be(Integer statusCode) {
        assertThat(response.getStatusCodeValue()).isEqualTo(statusCode);
    }

    @Then("the response should contain a list of {int} trainings")
    public void the_response_should_contain_trainings(Integer trainingQuantity) {
        TrainingDTO[] body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.length).isEqualTo(trainingQuantity);
    }
}
