package stepDefinitions.trainee;

import dev.gymService.model.dto.TraineeProfileRequest;
import dev.gymService.model.dto.TraineeProfileResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetTraineeProfileSteps {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private Environment environment;
    private ResponseEntity<TraineeProfileResponse> response;

    private String username;

    @Given("a trainee profile with username {string} exists")
    public void a_trainee_profile_with_username_exists(String username) {
        this.username = username;
    }

    @Given("a trainee with username {string} does not exist in DB")
    public void a_trainee_profile_with_username_does_not_exists(String username) {
        this.username = username;
    }

    @When("the client sends a request to get the profile for username {string} to endpoint {string}")
    public void the_client_sends_request(String username, String endpoint) {
        TraineeProfileRequest request = new TraineeProfileRequest();
        request.setUserName(username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(environment.getProperty("test.jwt.token"));
        HttpEntity<TraineeProfileRequest> entity = new HttpEntity<>(request, headers);

        response = restTemplate.postForEntity(endpoint, entity, TraineeProfileResponse.class);
    }

    @Then("the trainee profile response status should be {int}")
    public void the_response_status_should_be(Integer statusCode) {
        assertThat(response.getStatusCodeValue()).isEqualTo(statusCode);
    }

    @Then("the response should contain trainee first name {string}")
    public void the_response_should_contain_trainee_first_name(String firstName) {
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getFirstName()).isEqualTo(firstName);
    }
}
