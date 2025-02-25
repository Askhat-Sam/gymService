package dev.gymService.controller;

import dev.gymService.service.interfaces.TraineeService;
import org.springframework.stereotype.Controller;

@Controller
public class TraineeController {
    TraineeService traineeService;

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }
}
