package config;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RegisterTraineeTestRunner.class,
        ToggleTraineeStatusTestRunner.class,
        GetTraineeProfileTestRunner.class,
        ChangePasswordTestRunner.class,
        GetTraineeTrainingsTestRunner.class,
        UpdateTraineeProfileTestRunner.class,
        UpdateTraineeTrainersTestRunner.class,
        RegisterTrainerTestRunner.class,
        GetTrainerProfileTestRunner.class,
        ChangePasswordTrainerTestRunner.class,
        GetTrainerTrainingsTestRunner.class,
        ToggleTrainerStatusTestRunner.class,
        UpdateTrainerProfileTestRunner.class,
        AddTrainingTestRunner.class,
        GetTrainingTypesTestRunner.class
})
public class OrderedTestSuite {
}
