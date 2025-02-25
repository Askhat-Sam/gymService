package dev.gymService;

import dev.gymService.configuration.AppConfig;
import dev.gymService.service.interfaces.TraineeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        TraineeService traineeService = context.getBean("traineeServiceImpl", TraineeService.class);
        System.out.println(traineeService.selectTrainee(3L));
    }
}
