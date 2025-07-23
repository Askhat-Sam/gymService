@change_password_trainer
Feature: Change Trainer Password

  # First scenario for changing trainer's password when valid new password is provided
  Scenario: Change password successfully
    Given a trainer with username "Sergey.Sergeyev" exists
    When the trainer sends a change password request with username "Sergey.Sergeyev" and new password "newStrongPassword123" to endpoint "/gym-service/trainers/changePassword"
    Then the trainer password change response status should be 200
    And the trainer password change response should contain "Password has been successfully changed"

  # First scenario for changing trainer's password when invalid new password is provided
  Scenario: Failure to change password due to invalid input
    Given a trainer with username "Vladislav.Bekmeev" exists
    # The new password length is more than 128 character
    When the trainer sends a change password request with username "Vladislav.Bekmeev" and new password "ksjdhfksuywebmertberutheroithkerjtkerbtmertnbekrhtierthkerjthemrntbmerbtertiheorityuoeriteortnerjthkejrhtkerhterityeertkejrhtkehr" to endpoint "/gym-service/trainers/changePassword"
    Then the trainer password change response status should be 500
    And the trainer password change response should contain "password cannot be more than 72 bytes"