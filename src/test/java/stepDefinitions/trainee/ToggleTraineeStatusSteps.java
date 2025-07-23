package stepDefinitions.trainee;

import dev.gymService.model.dto.TraineeStatusToggleRequest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
public class ToggleTraineeStatusSteps {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private Environment environment;

    private ResponseEntity<String> response;
    private String username;

    @When("the client sends a toggle status request with username {string} to endpoint {string}")
    public void the_client_sends_toggle_status_request(String username, String endpoint) {
        TraineeStatusToggleRequest request = new TraineeStatusToggleRequest();
        request.setUserName(username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(environment.getProperty("test.jwt.token"));

        HttpEntity<TraineeStatusToggleRequest> entity = new HttpEntity<>(request, headers);
        response = restTemplate.exchange(
                endpoint,
                HttpMethod.PATCH,
                entity,
                String.class
        );
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(Integer statusCode) {
        assertThat(response.getStatusCodeValue()).isEqualTo(statusCode);
    }

    @Then("the toggle status response should contain {string}")
    public void the_response_should_contain(String expectedMessage) {
        assertThat(response.getBody()).contains(expectedMessage);
    }
}
