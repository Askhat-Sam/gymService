package stepDefinitions.trainer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gymService.model.dto.TrainerRegistrationRequest;
import dev.gymService.utills.JWTTokenHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RegisterTrainerSteps {
    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<String> response;
    private TrainerRegistrationRequest request;
    @Autowired
    private JWTTokenHelper jwtTokenHelper;
    @Given("a new trainer with firstName {string} and lastName {string} and specialization {string}")
    public void a_valid_trainer_registration_request(String firstName, String lastName, String specialization) {
        request = new TrainerRegistrationRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setSpecialization(Long.parseLong(specialization));
    }

    @Given("a new trainer without any parameters")
    public void invalid_trainee_registration() {
        request = new TrainerRegistrationRequest();
    }
    @When("the client sends POST request to register trainer {string}")
    public void the_client_sends_post_request_to_gym_service_trainers_register_new_trainee(String endpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TrainerRegistrationRequest> entity = new HttpEntity<>(request, headers);
        response = restTemplate.postForEntity(endpoint, request, String.class);
    }
    @Then("the trainer registration response status should be {int}")
    public void the_trainer_registration_response_status_should_be(Integer statusCode) {
        assertThat(response.getStatusCodeValue()).isEqualTo(statusCode);
    }
    @Then("the trainer registration response should contain a JWT token")
    public void the_response_should_contain_a_jwt_token() {
        String responseBody = response.getBody();
        assertThat(responseBody).contains("token");

        // Extract token
        String jwtToken = jwtTokenHelper.extractJwtToken(responseBody);

        // Save token to application-test.properties
        jwtTokenHelper.saveJwtTokenToProperties(jwtToken);
    }
}
