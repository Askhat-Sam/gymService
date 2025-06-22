package dev.gymService.service.implementations;

import dev.gymService.model.*;
import dev.gymService.model.dto.TrainingWorkloadRequest;
import dev.gymService.service.interfaces.WorkloadServiceClient;
import dev.gymService.utills.TrainingMapper;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("dev")
@Service
public class MessagingWorkloadServiceClient implements WorkloadServiceClient {
    private final RabbitTemplate rabbitTemplate;
    private final TrainingMapper trainingMapper;
    @Value("${rabbitmq.update.exchange-name}")
    private String exchange;
    @Value("${rabbitmq.update.routing-key}")
    private String updateRoutingKey;
    @Value("${rabbitmq.get-summary.routing-key}")
    private String summaryRequestRoutingKey;

    public MessagingWorkloadServiceClient(RabbitTemplate rabbitTemplate, TrainingMapper trainingMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.trainingMapper = trainingMapper;
    }

    @Override
    public String updateMicroservice(Training training, ActionType actionType, Trainee trainee, Trainer trainer) {
        TrainingWorkloadRequest request = trainingMapper.trainingToWorkloadRequest(training);
        request.setTrainerUsername(trainer.getUsername());
        request.setActionType(actionType);
        request.setTrainerFirstName(trainer.getFirstName());
        request.setTrainerLastName(trainer.getLastName());
        request.setActive(trainer.getIsActive());

        rabbitTemplate.convertAndSend(exchange, updateRoutingKey, request);
        return "Message sent via RabbitMQ";
    }

    @Override
    public TrainingWorkload getWorkloadSummary(String username) {
        // Create a TrainingWorkloadRequest object
        TrainingWorkloadRequest request = new TrainingWorkloadRequest();
        request.setTrainerUsername(username);

        // Send request and receive response using convertSendAndReceive
        Object response = rabbitTemplate.convertSendAndReceive(exchange, summaryRequestRoutingKey, request);

        if (response instanceof TrainingWorkload) {
            return (TrainingWorkload) response;
        } else {
            throw new RuntimeException("Invalid response from workload microservice via RabbitMQ.");
        }
    }
}
