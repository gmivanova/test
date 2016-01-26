Feature: Test

  Scenario: Send a request and analyze the response
    When I get the number {10}
    Then the status code should be {200}
    And the response is valid according to the {number_schema.json} schema profile {get}

  Scenario Outline: Send some post requests
    Given I load the request {test.json} with profile {<profile>}
    When I post to test
    Then the status code should be {201}
    And the response is valid according to the {test_schema.json} schema profile {post}
    Examples:
      |profile  |
      |1 number |
      |6 numbers|
      |7 numbers|