@regression
Feature: Item Management API
  As a user
  I want to be able to create, retrieve, list and delete items or objects
  So that I can manage my device inventory
@create_object
  Scenario: Verify an item can be created
    Given I have device data with name "Apple MacBook Pro 16"
    And the device has "year" as int value 2019
    And the device has "price" as double value 1849.99
    And the device has "CPU model" as string value "Intel Core i9"
    And the device has "Hard disk size" as string value "1 TB"
    When I send a POST request to create the item
    Then the response status code should be 200
    And the response should contain the device name "Apple MacBook Pro 16"
    And the response should contain a valid ID


@retrieve_specific_object
  Scenario: Ability to return an item
    Given I have an existing item created with name "iPhone 15 Pro"
    When I send a GET request for the created item ID
    Then the response status code should be 200
    And the response should contain the device name "iPhone 15 Pro"
@retrieve_lost_of_objects
  Scenario: Ability to list multiple items
    When I send a GET request to list all items
    Then the response status code should be 200
    And the response should be a list of items

@delete_an_object
  Scenario: Ability to delete an item
    Given I have an existing item created with name "To be deleted"
    When I send a DELETE request for the created item ID
    Then the response status code should be 200
    And the response message should confirm deletion
    When I send a GET request for the deleted item ID
    Then the response status code should be 404

@get_unknown_object
  Scenario: Error case - Get non-existent item
    When I send a GET request for item ID "non-existent-id-12345"
    Then the response status code should be 404
