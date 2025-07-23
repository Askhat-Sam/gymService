package stepDefinitions.trainee;

import io.cucumber.java.en.Given;

public class CommonTraineeSteps {
    public static String username;
    @Given("a trainee with username {string} exists")
    public void a_trainee_with_username_exists(String username) {
        CommonTraineeSteps.username = username;
    }
}
