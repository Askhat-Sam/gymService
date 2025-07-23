package stepDefinitions.trainer;

import io.cucumber.java.en.Given;

public class CommonTrainerSteps {
    public static String username;
    @Given("a trainer with username {string} exists")
    public void a_trainer_with_username_exists(String username) {
        CommonTrainerSteps.username = username;
    }

    @Given("a trainer with username {string} does not exist in DB")
    public void a_trainee_profile_with_username_does_not_exists(String username) {
        this.username = username;
    }
}
