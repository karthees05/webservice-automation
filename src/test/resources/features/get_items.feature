@regression @get
Feature: Get Items API
  As a user
  I want to be able to retrieve and list items
  So that I can view my device inventory

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

  @get_unknown_object
  Scenario: Error case - Get non-existent item
    When I send a GET request for item ID "non-existent-id-12345"
    Then the response status code should be 404
