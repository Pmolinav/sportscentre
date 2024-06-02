Feature: ActivityBOConfiguration

  Background:
    Given the following users have been stored previously
      | username | password     | name     | email          | role  | creation_date | modification_date |
      | someUser | somePassword | someName | some@email.com | ADMIN | 123456        | 123456            |
    When an user with username someUser and password somePassword tries to log in
    Then received status code is 200

  Scenario: Create a new activity unauthorized
    Given invalid auth token
    When try to create a new activity with data
      | type | name             | description                          | price |
      | GYM  | New Gym activity | Some description of the GYM activity | 5.20  |
    Then received status code is 401

  Scenario: Create a new activity successfully
    When try to create a new activity with data
      | type | name             | description                          | price |
      | GYM  | New Gym activity | Some description of the GYM activity | 5.20  |
    Then received status code is 201
    Then an activity with name New Gym activity has been stored successfully

  Scenario: Create a new activity bad request
    When try to create a new activity with data
      | type | description                          | price | creation_date | modification_date |
      | GYM  | Some description of the GYM activity | 5.20  | 123456        | 123456            |
    Then received status code is 400

  Scenario: Get all activities successfully
    When try to create a new activity with data
      | type     | name                  | description                               | price |
      | GYM      | New Gym activity      | Some description of the GYM activity      | 5.20  |
      | FOOTBALL | New Football activity | Some description of the FOOTBALL activity | 10.50 |
    Then received status code is 201
    Then an activity with name New Gym activity has been stored successfully
    Then an activity with name New Football activity has been stored successfully
    When try to get all activities
    Then received status code is 200
    Then a list of activities with names New Gym activity,New Football activity are returned in response

  Scenario: Get activity by activityId successfully
    When try to create a new activity with data
      | type | name             | description                          | price |
      | GYM  | New Gym activity | Some description of the GYM activity | 5.20  |
    Then received status code is 201
    Then an activity with name New Gym activity has been stored successfully
    When try to get an activity by activityId
    Then received status code is 200
    Then an activity with name New Gym activity is returned in response

  Scenario: Delete activity by activityId successfully
    When try to create a new activity with data
      | type | name             | description                          | price |
      | GYM  | New Gym activity | Some description of the GYM activity | 5.20  |
    Then received status code is 201
    Then an activity with name New Gym activity has been stored successfully
    When try to delete an activity by activityId
    Then received status code is 200