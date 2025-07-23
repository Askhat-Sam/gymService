package stepDefinitions.trainee;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gymService.model.dto.TraineeRegistrationRequest;
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

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
public class RegisterTraineeSteps {
    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<String> response;
    private TraineeRegistrationRequest request;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Given("a new trainee with firstName {string} and lastName {string} and dateOfBirth {string} and address {string}")
    public void a_valid_trainee_registration_request(String firstName, String lastName, String dateOfBirth, String address) {
        request = new TraineeRegistrationRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setDateOfBirth(LocalDate.parse(dateOfBirth));
        request.setAddress(address);
    }

    @Given("a new trainee without any parameters")
    public void invalid_trainee_registration() {
        request = new TraineeRegistrationRequest();
    }
    @When("the client sends POST request to {string}")
    public void the_client_sends_post_request_to_gym_service_trainees_register_new_trainee(String endpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TraineeRegistrationRequest> entity = new HttpEntity<>(request, headers);
        response = restTemplate.postForEntity(endpoint, request, String.class);
    }
    @Then("the trainee registration response status should be {int}")
    public void the_trainee_registration_response_status_should_be(Integer statusCode) {
        assertThat(response.getStatusCodeValue()).isEqualTo(statusCode);
    }
    @Then("the response should contain a JWT token")
    public void the_response_should_contain_a_jwt_token() {
        String responseBody = response.getBody();
        assertThat(responseBody).contains("token");

        // Extract token
        String jwtToken = jwtTokenHelper.extractJwtToken(responseBody);

        // Save token to application-test.properties
        jwtTokenHelper.saveJwtTokenToProperties(jwtToken);
    }

//    private String extractJwtToken(String responseBody) {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            JsonNode jsonNode = objectMapper.readTree(responseBody);
//            return jsonNode.get("token").asText();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to extract JWT token from response", e);
//        }
//    }
//
//    private void saveJwtTokenToProperties(String jwtToken) {
//        // Dynamically set the JWT token in the Spring Environment
//        ConfigurableEnvironment environment = (ConfigurableEnvironment) applicationContext.getEnvironment();
//        environment.getSystemProperties().put("test.jwt.token", jwtToken);
//    }
}
