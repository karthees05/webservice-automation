@regression @create
Feature: Create Items API
  As a user
  I want to be able to create new items
  So that I can add them to my inventory

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
