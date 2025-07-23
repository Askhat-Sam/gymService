@get_training_types
Feature: Retrieve available training types

  Scenario: Successfully retrieve list of training types
    Given the client is authenticated
    When the client sends a request to get training types to endpoint "/gym-service/trainings/getTrainingTypes"
    Then the get training types response status should be 200
    And the get training types response should contain a list of training types "1"

  Scenario: Fail to retrieve training types when unauthorized
    When the unauthenticated client sends a request to get training types to endpoint "/gym-service/trainings/getTrainingTypes"
    Then the get training types response status should be 403