@register_new_trainer
Feature: Register new trainer

  # First scenario for registering the trainer when valid input is provided
  Scenario: Register trainer with valid input
    Given a new trainer with firstName "Alexandr" and lastName "Egorov" and specialization "1"
    When the client sends POST request to register trainer "/gym-service/trainers/registerNewTrainer"
    Then the trainer registration response status should be 200
    And the trainer registration response should contain a JWT token

  # Second scenario for registering the trainer when invalid input is provided
  Scenario: Failure to register trainer with invalid input{}
    Given a new trainer without any parameters
    When the client sends POST request to register trainer "/gym-service/trainers/registerNewTrainer"
    Then the trainer registration response status should be 500