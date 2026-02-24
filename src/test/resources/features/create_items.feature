@regression @create
Feature: Create Items API
  As a user
  I want to be able to create new items
  So that I can add them to my inventory

  @create_object
  Scenario: Verify an item can be created
    Given I have device data with name "Apple MacBook Pro 16"
    And I have an "x-api-key" header with value "0b78d9ba-8ee3-4362-90ae-d364bc590812"
    And I have a "Content-Type" header with value "application/json"
    And the device has "year" as int value 2019
    And the device has "price" as double value 1849.99
    And the device has "CPU model" as string value "Intel Core i9"
    And the device has "Hard disk size" as string value "1 TB"
    When I send a POST request to create the item
    Then the response status code should be 200
    And the response should contain the device name "Apple MacBook Pro 16"
    And the response should contain a valid ID

  @negative @invalid_content_type
  Scenario: Error case - Create item with invalid content type
    Given I have device data with name "Invalid Content Type"
    And I have an "x-api-key" header with value "0b78d9ba-8ee3-4362-90ae-d364bc590812"
    And I have a "Content-Type" header with value "text/plain"
    When I send a POST request to create the item
    Then the response status code should be 415

  @negative @invalid_api_key
  Scenario: Error case - Create item with invalid API key
    Given I have device data with name "Invalid API Key"
    And I have an "x-api-key" header with value "invalid-key-123"
    And I have a "Content-Type" header with value "application/json"
    When I send a POST request to create the item
    Then the response status code should be 403

  @negative @malformed_json
  Scenario: Error case - Create item with malformed JSON
    Given I have an "x-api-key" header with value "0b78d9ba-8ee3-4362-90ae-d364bc590812"
    And I have a "Content-Type" header with value "application/json"
    When I send a POST request to create the item with a malformed body
    Then the response status code should be 400

  @negative @empty_body
  Scenario: Error case - Create item with empty body
    Given I have an "x-api-key" header with value "0b78d9ba-8ee3-4362-90ae-d364bc590812"
    And I have a "Content-Type" header with value "application/json"
    When I send a POST request to create the item with an empty body
    Then the response status code should be 400

  @negative @missing_name
  Scenario: Error case - Create item with missing name
    Given I have an "x-api-key" header with value "0b78d9ba-8ee3-4362-90ae-d364bc590812"
    And I have a "Content-Type" header with value "application/json"
    When I send a POST request to create the item with missing name
    Then the response status code should be 200
