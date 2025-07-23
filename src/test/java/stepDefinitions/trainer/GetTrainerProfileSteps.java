package stepDefinitions.trainer;

import dev.gymService.model.dto.*;
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

public class GetTrainerProfileSteps {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private Environment environment;

    private ResponseEntity<TrainerProfileResponse> response;

    private String username;

    @Given("a trainer profile with username {string} exists")
    public void a_trainer_profile_with_username_exists(String username) {
        this.username = username;
    }

    @When("the trainer sends a request to get the profile for username {string} to endpoint {string}")
    public void the_client_sends_request(String username, String endpoint) {
        TrainerProfileRequest request = new TrainerProfileRequest();
        request.setUserName(username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(environment.getProperty("test.jwt.token"));
        HttpEntity<TrainerProfileRequest> entity = new HttpEntity<>(request, headers);

        response = restTemplate.postForEntity(endpoint, entity, TrainerProfileResponse.class);
    }

    @Then("the trainer profile response status should be {int}")
    public void the_response_status_should_be(Integer statusCode) {
        assertThat(response.getStatusCodeValue()).isEqualTo(statusCode);
    }

    @Then("the trainer profile response should contain trainer first name {string}")
    public void the_response_should_contain_trainee_first_name(String firstName) {
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getFirstName()).isEqualTo(firstName);
    }
}
