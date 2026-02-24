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

  @negative @delete_non_existent
  Scenario: Error case - Delete non-existent item
    Given I have an "x-api-key" header with value "0b78d9ba-8ee3-4362-90ae-d364bc590812"
    And I have a "Content-Type" header with value "application/json"
    When I send a DELETE request for item ID "non-existent-id-999"
    Then the response status code should be 404

  @negative @invalid_api_key
  Scenario: Error case - Delete item with invalid API key
    Given I have a valid session
    And I have an existing item created with name "Delete Auth Test"
    And I have an "x-api-key" header with value "invalid-key-123"
    When I send a DELETE request for the created item ID
    Then the response status code should be 403

  @negative @delete_already_deleted
  Scenario: Error case - Delete already deleted item
    Given I have an "x-api-key" header with value "0b78d9ba-8ee3-4362-90ae-d364bc590812"
    And I have a "Content-Type" header with value "application/json"
    And I have an existing item created with name "Double Delete"
    When I send a DELETE request for the created item ID
    Then the response status code should be 200
    When I send a DELETE request for the created item ID
    Then the response status code should be 404
