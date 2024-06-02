Feature: UserBOConfiguration

  Background:
    Given the following users have been stored previously
      | username | password     | name     | email          | role  | creation_date | modification_date |
      | someUser | somePassword | someName | some@email.com | ADMIN | 123456        | 123456            |
    When an user with username someUser and password somePassword tries to log in
    Then received status code is 200

  Scenario: Create a new user unauthorized
    Given invalid auth token
    When try to create a new user with data
      | username | password    | name     | email         | role  |
      | newUser  | newPassword | someName | new@email.com | ADMIN |
    Then received status code is 401

  Scenario: Create a new user successfully
    When try to create a new user with data
      | username | password    | name     | email         | role  |
      | newUser  | newPassword | someName | new@email.com | ADMIN |
    Then received status code is 201
    Then an user with username newUser has been stored successfully

  Scenario: Create a new user bad request
    When try to create a new user with data
      | username | password    | email         | role  | creation_date | modification_date |
      | newUser  | newPassword | new@email.com | ADMIN | 123456        | 123456            |
    Then received status code is 400

  Scenario: Get all users successfully
    When try to create a new user with data
      | username  | password      | name      | email           | role  |
      | newUser   | newPassword   | someName  | new@email.com   | ADMIN |
      | otherUser | otherPassword | otherName | other@email.com | ADMIN |
    Then received status code is 201
    Then an user with username newUser has been stored successfully
    Then an user with username otherUser has been stored successfully
    When try to get all users
    Then received status code is 200
    Then a list of users with usernames someUser,newUser,otherUser are returned in response

  Scenario: Get user by userId successfully
    When try to create a new user with data
      | username | password    | name     | email          | role  |
      | newUser  | newPassword | someName | some@email.com | ADMIN |
    Then received status code is 201
    Then an user with username newUser has been stored successfully
    When try to get an user by userId
    Then received status code is 200
    Then an user with username newUser is returned in response

  Scenario: Delete user by userId successfully
    When try to create a new user with data
      | username | password    | name     | email          | role  |
      | newUser  | newPassword | someName | some@email.com | ADMIN |
    Then received status code is 201
    Then an user with username newUser has been stored successfully
    When try to delete an user by userId
    Then received status code is 200