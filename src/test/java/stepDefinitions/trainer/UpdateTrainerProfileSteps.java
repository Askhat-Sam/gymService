package stepDefinitions.trainer;

import dev.gymService.model.dto.TraineeProfileUpdateResponse;
import dev.gymService.model.dto.TrainerProfileUpdateRequest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UpdateTrainerProfileSteps {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private Environment environment;
    private ResponseEntity<TraineeProfileUpdateResponse> response;
    @When("the client sends a request to update trainer {string} with firstName {string}, lastName {string}, specialization {string}, isActive {string} and password {string} to endpoint {string}")
    public void the_client_sends_update_request(String username, String firstName, String lastName, String specialization, String isActive, String password, String endpoint) {
        TrainerProfileUpdateRequest request = new TrainerProfileUpdateRequest();
        request.setUserName(username);
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setSpecialization(Long.parseLong(specialization));
        request.setIsActive(Boolean.parseBoolean(isActive));
        request.setPassword(password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(environment.getProperty("test.jwt.token"));

        HttpEntity<TrainerProfileUpdateRequest> entity = new HttpEntity<>(request, headers);
        response = restTemplate.exchange(
                endpoint,
                HttpMethod.PUT,
                entity,
                TraineeProfileUpdateResponse.class
        );
    }

    @Then("the update trainer response status should be {int}")
    public void the_update_response_status_should_be(Integer statusCode) {
        assertThat(response.getStatusCodeValue()).isEqualTo(statusCode);
    }

    @Then("the update trainer response should contain {string}")
    public void the_update_response_should_contain(String expectedName) {
        assertThat(response.getBody().getFirstName()).contains(expectedName);
    }
}
