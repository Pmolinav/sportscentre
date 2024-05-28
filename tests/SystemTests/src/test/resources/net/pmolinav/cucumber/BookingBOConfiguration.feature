Feature: BookingBOConfiguration

  Background:
    Given the following users have been stored previously
      | username | password     | name     | email          | role  | creation_date | modification_date |
      | someUser | somePassword | someName | some@email.com | ADMIN | 123456        | 123456            |
    When an user with username someUser and password somePassword tries to log in
    Then received status code is 200
    # Create new user previously to store a valid userId
    When try to create a new user with data
      | username | password    | name     | email          | role  |
      | newUser  | newPassword | someName | some@email.com | ADMIN |
    Then received status code is 200
    Then an user with username newUser has been stored successfully
    # Create new user previously to store a valid activityId
    When try to create a new activity with data
      | type | name             | description                          | prize |
      | GYM  | New Gym activity | Some description of the GYM activity | 5.20  |
    Then received status code is 200
    Then an activity with name New Gym activity has been stored successfully

  Scenario: Create a new booking unauthorized
    Given invalid auth token
    When try to create a new booking with data
      | start_time | end_time | status   |
      | 123456     | 1234567  | ACCEPTED |
    Then received status code is 401

  Scenario: Create a new booking successfully
    When try to create a new booking with data
      | start_time | end_time | status |
      | 123456     | 1234567  | OPEN   |
    Then received status code is 200
    Then a booking with status OPEN has been stored successfully

  Scenario: Create a new booking request
    When try to create a new booking with data
      | start_time | end_time | status    | creation_date | modification_date |
      | 123456     | 1234567  | CANCELLED | 123456        | 123456            |
    Then received status code is 400

  Scenario: Get all bookings successfully
    When try to create a new booking with data
      | start_time | end_time | status   |
      | 123456     | 1234567  | FINISHED |
      | 1234567    | 12345678 | OPEN     |
    Then received status code is 200
    Then a booking with status OPEN has been stored successfully
    Then a booking with status FINISHED has been stored successfully
    When try to get all bookings
    Then received status code is 200
    Then a list of bookings with statuses FINISHED,OPEN are returned in response

  Scenario: Get booking by bookingId successfully
    When try to create a new user with data
      | start_time | end_time | status |
      | 1234567    | 12345678 | OPEN   |
    Then received status code is 200
    Then a booking with status OPEN has been stored successfully
    When try to get a booking by bookingId
    Then received status code is 200
    Then a booking with status OPEN is returned in response

  Scenario: Delete booking by bookingId successfully
    When try to create a new user with data
      | start_time | end_time   | status    |
      | 123456789  | 1234567890 | CANCELLED |
    Then received status code is 200
    Then a booking with status CANCELLED has been stored successfully
    When try to delete a booking by bookingId
    Then received status code is 200