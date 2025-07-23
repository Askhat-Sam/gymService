package config;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        features = "src/test/resources/features",
        glue = {"config", "stepDefinitions"},
        tags = "@get_trainee_profile"
)
public class GetTraineeProfileTestRunner {
}
