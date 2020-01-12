Feature: Registration

  Scenario: Valid user registration
    Given I am on register page
    When I enter username "registration_user1" and password "test1234" and confirmation "test1234"
    Then user should be registered successfully

  Scenario: Exist user name registration
    Given I am on register page
    And exist username "registration_user2"
    When I enter username "registration_user1" and password "test1234" and confirmation "test1234"
    Then user should be failed to register

  Scenario: Invalid password confirmation registration
    Given I am on register page
    When I enter username "registration_user3" and password "test1234" and confirmation "notmatch"
    Then user should be failed to register

