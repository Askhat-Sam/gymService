@change_password
Feature: Change Trainee Password

  # First scenario for changing trainee's password when valid new password is provided
  Scenario: Change password successfully
    Given a trainee with username "Ivan.Ivanov" exists
    When the client sends a change password request with username "Ivan.Ivanov" and new password "newStrongPassword123" to endpoint "/gym-service/trainees/changePassword"
    Then the password change response status should be 200
    And the response should contain "Password has been successfully changed"

  # First scenario for changing trainee's password when invalid new password is provided
  Scenario: Failure to change password due to invalid input
    Given a trainee with username "Ivan.Ivanov" exists
    # The new password length is more than 128 character
    When the client sends a change password request with username "Ivan.Ivanov" and new password "ksjdhfksuywebmertberutheroithkerjtkerbtmertnbekrhtierthkerjthemrntbmerbtertiheorityuoeriteortnerjthkejrhtkerhterityeertkejrhtkehr" to endpoint "/gym-service/trainees/changePassword"
    Then the password change response status should be 500
    And the response should contain "password cannot be more than 72 bytes"