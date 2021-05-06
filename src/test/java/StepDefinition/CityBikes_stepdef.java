package StepDefinition;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;


public class CityBikes_stepdef {


    String BaseURI = "http://api.citybik.es/v2/networks";
    Response response;

    @Given("^User triggered citybike endpoint and able to access$")
    public void userTriggeredCitybikeEndpointAndAbleToAccess() {

        response = RestAssured.get(BaseURI);
    }

    @When("^user entered the city as \"([^\"]*)\"$")
    public void userEnteredTheCityAs(String cityName) {

        response = getNetworkIdResponse(getNetworkIdResource(cityName));

        if (response == null) {
            System.out.println("city not in network");
        } else if (response.getStatusCode() == 200) {
            System.out.println("city matches the network");
        }

    }

    /**
     * @param cityName Method to choose the endpoint based on the city for the BaseURI
     */

    public String getNetworkIdResource(String cityName) {

        String result;
        switch (cityName.toLowerCase()) {
            case "frankfurt":
                result = "/visa-frankfurt";
                break;
            case "london":
                result = "/nextbike-london";
                break;
            case "copenhagen":
                result = "/bycyklen";
                break;
            default:
                result = "/null";
        }
        return result;
    }

    /**
     * Method returning the endpoint to trigger based on the cityName
     */

    public Response getNetworkIdResponse(String result) {

        response = RestAssured.get(BaseURI + result);

        return response;
    }

    /**
     * @param result_country
     */

    @And("^response contains country equals \"([^\"]*)\"$")
    public void responseContainsCountryEquals(String result_country) {

        String actual_country = response.then().extract().path("network.location.country");

        Assert.assertEquals(result_country, actual_country);
    }

    /**
     * This method is to verify the response is 200
     */

    @Then("^User should receive positive response$")
    public void userShouldReceivePositiveResponse() {
        Assert.assertEquals(200, response.getStatusCode());
        System.out.println("city exist in network");

    }

    /**
     * This method is to retrieve the latitude and longitude from response body
     */
    @And("^response contains latitude of \"([^\"]*)\" and longitude of \"([^\"]*)\"$")
    public void responseContainsLatitudeOfAndLongitudeOf(float lat_exp, float lon_exp) {
        float lat_act = response.then().extract().path("network.location.latitude");
        float lon_act = response.then().extract().path("network.location.longitude");

        Assert.assertNotNull(lon_act);
        Assert.assertNotNull(lat_act);

    }

    @When("^user entered  city as \"([^\"]*)\"$")
    public void userEnteredCityAs(String cityName) {

        response = getNetworkIdResponse(getNetworkIdResource(cityName));


    }

    @And("^User should get the bike_id$")
    public void userShouldGetTheBike_id() {
        String bike_uid1_act = response.then().extract().path("network.stations[0].extra.bike_uids[0]");

        Assert.assertNotNull(bike_uid1_act);
        System.out.println("Bike id in london :" + bike_uid1_act);
    }

    /**
     * This method shows negative response with status code 404
     */

    @Then("^user should receive negative response$")
    public void userShouldReceiveNegativeResponse() {

        Assert.assertEquals(404, response.getStatusCode());
        System.out.println("city not found in network");

    }

    /**
     * Ths method will show the number of free_bikes
     */

    @And("^get number of free bikes in the \"([^\"]*)\" station from network$")
    public void getNumberOfFreeBikesInTheStationFromNetwork(String cityName) {

        response = getNetworkIdResponse(getNetworkIdResource(cityName));
        int free_bikes = response.then().extract().path("network.stations[2].free_bikes");
        Assert.assertNotNull(free_bikes);
        System.out.println("Number of bikes in chosen station :" + free_bikes);

    }
}























