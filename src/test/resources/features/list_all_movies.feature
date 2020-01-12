Feature: List a movies

  Scenario: List all videos in home page
    Given I have a list of videos in database
    And I am at home page
    Then I should see all videos listed
