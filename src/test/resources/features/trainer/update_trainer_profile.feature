@update_trainer_profile
Feature: Update Trainer Profile

  Scenario: Successfully update an existing trainer profile
    Given a trainer with username "Vladislav.Bekmeev" exists
    When the client sends a request to update trainer "Vladislav.Bekmeev" with firstName "Vladislav777", lastName "Bekmeev", specialization "2", isActive "true" and password "12345678910" to endpoint "/gym-service/trainers/updateTrainer"
    Then the update trainer response status should be 200
    And the update trainer response should contain "Vladislav777"