@regression @get
Feature: Get Items API
  As a user
  I want to be able to retrieve and list items
  So that I can view my device inventory

  @retrieve_specific_object
  Scenario: Ability to return an item
    Given I have an "x-api-key" header with value "0b78d9ba-8ee3-4362-90ae-d364bc590812"
    And I have a "Content-Type" header with value "application/json"
    And I have an existing item created with name "iPhone 15 Pro"
    When I send a GET request for the created item ID
    Then the response status code should be 200
    And the response should contain the device name "iPhone 15 Pro"
    And the response should match the Device schema

  @retrieve_list_of_objects
  Scenario: Ability to list multiple items
    Given I have a valid session
    When I send a GET request to list all items
    Then the response status code should be 200
    And the response should be a list of items
    And the response should match the Device List schema

  @retrieve_specific_objects_by_ids
  Scenario: Ability to retrieve multiple items by IDs
    Given I have a valid session
    And I have an existing item created with name "Bulk Item 1"
    And I store the current ID as "id1"
    And I have an existing item created with name "Bulk Item 2"
    And I store the current ID as "id2"
    When I send a GET request for items with stored IDs "id1" and "id2"
    Then the response status code should be 200
    And the response should be a list of items
    And the response should contain items with names "Bulk Item 1" and "Bulk Item 2"

  @get_unknown_object
  Scenario: Error case - Get non-existent item
    Given I have an "x-api-key" header with value "0b78d9ba-8ee3-4362-90ae-d364bc590812"
    And I have a "Content-Type" header with value "application/json"
    When I send a GET request for item ID "non-existent-id-12345"
    Then the response status code should be 404

  @negative @invalid_api_key
  Scenario: Error case - Get item with invalid API key
    Given I have a valid session
    And I have an existing item created with name "Auth Test"
    And I have an "x-api-key" header with value "invalid-key-123"
    When I send a GET request for the created item ID
    Then the response status code should be 403

  @negative @invalid_id_format
  Scenario: Error case - Get item with invalid ID format
    Given I have a valid session
    When I send a GET request for item ID "@#$%^&*"
    Then the response status code should be 401
