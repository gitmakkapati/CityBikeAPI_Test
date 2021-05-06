@api
Feature: Bike API Testing

  Scenario: Successful attempt for finding the country along with Latitude and Longitude
    Given User triggered citybike endpoint and able to access
    When user entered the city as "Frankfurt"
    Then User should receive positive response
    And response contains country equals "DE"
    And response contains latitude of "50.1072" and longitude of "8.66375"

  Scenario: Successfully get the bike ids's when valid city is entered
    Given User triggered citybike endpoint and able to access
    When user entered  city as "london"
    Then User should receive positive response
    And User should get the bike_id

  Scenario: Unsuccessful attempt for finding city as empty
    Given User triggered citybike endpoint and able to access
    When user entered the city as ""
    Then user should receive negative response

  Scenario: Successfully find  number of free bikes available in the network
    Given User triggered citybike endpoint and able to access
    Then User should receive positive response
    And get number of free bikes in the "Copenhagen" station from network

