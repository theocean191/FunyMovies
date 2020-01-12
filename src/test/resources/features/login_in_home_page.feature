Feature: Login in home page

  Scenario: Valid user login
    Given I am at login page
    And exist account with username "login_user1" and password "test1234"
    When I enter username "login_user1" and password "test1234"
    Then user should be login successfully

  Scenario: login with invalid username
    Given I am at login page
    When I enter username "login_user2" and password "test1234"
    Then user should be failed to login

  Scenario: login with invalid password
    Given I am at login page
    And exist account with username "login_user2" and password "test1234"
    When I enter username "login_user2" and password "incorrectpassword"
    Then user should be failed to login