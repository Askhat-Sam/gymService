package stepDefinitions.trainee;

import dev.gymService.model.Trainer;
import dev.gymService.model.dto.TraineeTrainersListUpdateRequest;
import dev.gymService.model.dto.TraineeTrainersListUpdateResponse;
import dev.gymService.model.dto.TrainerDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UpdateTraineeTrainersSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<TraineeTrainersListUpdateResponse> response;
    private List<Long> trainerIds;
    private String trainerUserName;
    @Autowired
    private Environment environment;

    @Given("a the trainee with username {string} exists")
    public void a_trainer_exists(String trainerUserName) {
        this.trainerUserName = trainerUserName;
    }
    @When("the client sends a request to update trainee trainers for username {string} to add trainer with username {string}")
    public void the_client_sends_update_trainer_list_request(String username, String trainerUsername) {
        TraineeTrainersListUpdateRequest request = new TraineeTrainersListUpdateRequest();
        request.setUserName(username);

        List<Trainer> trainers = new ArrayList<>();
        Trainer trainer = new Trainer();
        trainer.setUserName("Sergey.Sergeyev");
        trainer.setSpecialization(2L); // Optional depending on controller logic

        trainers.add(trainer);

        request.setTrainers(trainers);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(environment.getProperty("test.jwt.token"));
        System.out.println("TraineeTrainersListUpdateRequest " + request);

        HttpEntity<TraineeTrainersListUpdateRequest> entity = new HttpEntity<>(request, headers);
        response = restTemplate.exchange(
                "/gym-service/trainees/updateTraineeTrainers",
                HttpMethod.PUT,
                entity,
                TraineeTrainersListUpdateResponse.class
        );
    }

    @Then("the update trainee trainers response status should be {int}")
    public void the_response_status_should_be(Integer statusCode) {
        assertThat(response.getStatusCodeValue()).isEqualTo(statusCode);
    }

    @Then("the update trainee trainers response should contain trainer with username {string}")
    public void the_response_should_contain_trainer(String trainerUserName) {
        List<TrainerDTO> trainers = response.getBody().getTrainers();
        boolean contains = trainers.stream().anyMatch(t -> t.getUserName().equals(trainerUserName));
        assertThat(contains).isTrue();
    }
}
