package stepDefinitions.trainee;

import dev.gymService.model.dto.TraineeDeleteRequest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DeleteTraineeSteps {
    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<String> response;
    @Autowired
    private Environment environment;

    @When("the client sends a delete trainee request for username {string} to endpoint {string}")
    public void the_client_sends_delete_request(String username, String endpoint) {
        TraineeDeleteRequest request = new TraineeDeleteRequest();
        request.setUserName(username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(environment.getProperty("test.jwt.token"));

        HttpEntity<TraineeDeleteRequest> entity = new HttpEntity<>(request, headers);

        response = restTemplate.exchange(
                endpoint,
                HttpMethod.DELETE,
                entity,
                String.class
        );
    }

    @Then("the delete response should contain {string}")
    public void the_delete_response_should_contain(String expectedMessage) {
        assertThat(response.getBody()).contains(expectedMessage);
    }

    @Then("the delete response status should be {int}")
    public void the_response_status_should_be(Integer statusCode) {
        assertThat(response.getStatusCodeValue()).isEqualTo(statusCode);
    }
}
