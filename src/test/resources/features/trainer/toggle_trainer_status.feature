@toggle_trainer_status
Feature: Toggle trainer status

  Scenario: Toggle status of an existing trainer
    Given a trainer with username "Sergey.Sergeyev" exists
    When trainer sends a toggle status request with username "Sergey.Sergeyev" to endpoint "/gym-service/trainers/toggleTrainerStatus"
    Then trainer toggle status response status should be 200
    And trainer toggle status response should contain "User status has been successfully changed"