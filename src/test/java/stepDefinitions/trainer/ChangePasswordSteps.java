package stepDefinitions.trainer;

import dev.gymService.model.dto.TrainerPasswordChangeRequest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ChangePasswordSteps {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private Environment environment;
    private ResponseEntity<String> response;

    @When("the trainer sends a change password request with username {string} and new password {string} to endpoint {string}")
    public void the_client_sends_a_change_password_request(String username, String newPassword, String endpoint) {
        TrainerPasswordChangeRequest request = new TrainerPasswordChangeRequest();
        request.setUserName(username);
        request.setNewPassword(newPassword);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(environment.getProperty("test.jwt.token"));
        HttpEntity<TrainerPasswordChangeRequest> entity = new HttpEntity<>(request, headers);
        response = restTemplate.exchange(endpoint, HttpMethod.PUT, entity, String.class);
    }

    @Then("the trainer password change response status should be {int}")
    public void the_password_change_response_status_should_be(Integer statusCode) {
        assertThat(response.getStatusCodeValue()).isEqualTo(statusCode);
    }

    @Then("the trainer password change response should contain {string}")
    public void the_response_should_contain(String expectedMessage) {
        assertThat(response.getBody()).contains(expectedMessage);
    }
}
