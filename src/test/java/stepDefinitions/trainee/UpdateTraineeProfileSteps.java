package stepDefinitions.trainee;
import dev.gymService.model.dto.TraineeProfileUpdateRequest;
import dev.gymService.model.dto.TraineeProfileUpdateResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.*;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UpdateTraineeProfileSteps {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private Environment environment;
    private ResponseEntity<TraineeProfileUpdateResponse> response;
    @When("the client sends a request to update trainee {string} with firstName {string}, lastName {string}, dateOfBirth {string}, address {string}, isActive {string} and password {string}")
    public void the_client_sends_update_request(String username, String firstName, String lastName, String dateOfBirth, String address, String isActive, String password) {
        TraineeProfileUpdateRequest request = new TraineeProfileUpdateRequest();
        request.setUserName(username);
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setDateOfBirth(LocalDate.parse(dateOfBirth));
        request.setAddress(address);
        request.setIsActive(Boolean.parseBoolean(isActive));
        request.setPassword(password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(environment.getProperty("test.jwt.token"));

        HttpEntity<TraineeProfileUpdateRequest> entity = new HttpEntity<>(request, headers);
        response = restTemplate.exchange(
                "/gym-service/trainees/updateTrainee",
                HttpMethod.PUT,
                entity,
                TraineeProfileUpdateResponse.class
        );
    }

    @Then("the update trainee response status should be {int}")
    public void the_update_response_status_should_be(Integer statusCode) {
        assertThat(response.getStatusCodeValue()).isEqualTo(statusCode);
    }

    @Then("the update response should contain {string}")
    public void the_update_response_should_contain(String expectedName) {
        assertThat(response.getBody().getFirstName()).contains(expectedName);
    }
}
