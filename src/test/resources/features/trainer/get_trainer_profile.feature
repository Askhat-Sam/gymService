@get_trainer_profile
Feature: Get trainer profile
  # First scenario for getting trainer profile by username
  Scenario: Successfully get trainer profile existing in DB by username
    Given a trainer with username "Ivan.Ivanov" exists
    When the trainer sends a request to get the profile for username "Sergey.Sergeyev" to endpoint "/gym-service/trainers/getTrainerByUsername"
    Then the trainer profile response status should be 200
    And the trainer profile response should contain trainer first name "Sergey"

  # First scenario for getting trainer profile by username
  Scenario: Failure to get trainer profile by username since user not exist in DB
    Given a trainer with username "Sergey.Sergeyev777" does not exist in DB
    When the trainer sends a request to get the profile for username "Sergey.Sergeyev777" to endpoint "/gym-service/trainers/getTrainerByUsername"
    Then the trainer profile response status should be 500
