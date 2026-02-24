@regression @delete
Feature: Delete Items API
  As a user
  I want to be able to delete items
  So that I can keep my inventory up to date

  @delete_an_object
  Scenario: Ability to delete an item
    Given I have an "x-api-key" header with value "0b78d9ba-8ee3-4362-90ae-d364bc590812"
    And I have a "Content-Type" header with value "application/json"
    And I have an existing item created with name "To be deleted"
    When I send a DELETE request for the created item ID
    Then the response status code should be 200
    And the response message should confirm deletion
    When I send a GET request for the deleted item ID
    Then the response status code should be 404
