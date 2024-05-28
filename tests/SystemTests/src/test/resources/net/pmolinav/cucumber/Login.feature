Feature: Login

  Background:
    Given the following users have been stored previously
      | username | password     | name     | email          | role  | creation_date | modification_date |
      | someUser | somePassword | someName | some@email.com | ADMIN | 123456        | 123456            |

  Scenario: An user logs in successfully
    When an user with username someUser and password somePassword tries to log in
    Then received status code is 200

  Scenario: An user logs in successfully
    When an user with username otherUser and password otherPassword tries to log in
    Then received status code is 401