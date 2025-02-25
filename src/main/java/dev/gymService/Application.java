package dev.gymService;

import dev.gymService.configuration.Configuration;
import dev.gymService.service.interfaces.TraineeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Configuration.class);

        TraineeService traineeService = applicationContext.getBean("traineeService", TraineeService.class);
        traineeService.deleteTrainee("1");

    }
}
