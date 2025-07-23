package stepDefinitions.training;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

public class GetTrainingTypesSteps {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private Environment environment;

    private ResponseEntity<String> response;

    private HttpHeaders headers;

    @Given("the client is authenticated")
    public void the_client_is_authenticated() {
        headers = new HttpHeaders();
        headers.setBearerAuth(environment.getProperty("test.jwt.token"));
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @When("the client sends a request to get training types to endpoint {string}")
    public void the_client_sends_request_to_get_training_types(String endpoint) {
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        response = restTemplate.exchange(endpoint, HttpMethod.GET, entity, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        String body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body).contains("Swimming", "Yoga", "Cardio");
    }

    @When("the unauthenticated client sends a request to get training types to endpoint {string}")
    public void unauthenticated_client_sends_request(String endpoint) {
        response = restTemplate.exchange(endpoint, HttpMethod.GET, null, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNull();
    }

    @Then("the get training types response status should be {int}")
    public void the_response_status_should_be(int expectedStatus) {
        assertThat(response.getStatusCodeValue()).isEqualTo(expectedStatus);
    }

    @Then("the get training types response should contain a list of training types {string}")
    public void the_response_should_contain_training_types(String trainingType) {
        assertThat(response.getBody()).contains(trainingType);
    }
}