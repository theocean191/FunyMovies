Feature: Share a movies

  Scenario: Share a valid youtube movies
    Given I am logged in
    And I am at share a movie page
    When When I enter url "https://www.youtube.com/watch?v=-s5lDRYUCeg"
    Then The url should be shared successfully

  Scenario: Share a invalid youtube movies
    Given I am logged in
    And I am at share a movie page
    When When I enter url "http://youtube.com1///asdsadsadsad"
    Then I should be failed to share the movie