package com.example.api.stepdefs;

import com.example.api.client.ApiClient;
import com.example.api.model.DeviceObject;
import com.example.api.utils.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import java.util.HashMap;

import static org.hamcrest.Matchers.*;

public class ItemSteps {

    private final TestContext context;
    private final ApiClient apiClient;
    private DeviceObject deviceObject;
    private Response response;

    public ItemSteps() {
        this.context = new TestContext();
        this.apiClient = new ApiClient();
    }

    @Given("I have device data with name {string}")
    public void iHaveDeviceDataWithName(String name) {
        deviceObject = DeviceObject.builder()
                .name(name)
                .data(new HashMap<>())
                .build();
    }

    @And("the device has {string} as string value {string}")
    public void theDeviceHasAsStringValue(String key, String value) {
        deviceObject.getData().put(key, value);
    }

    @And("the device has {string} as int value {int}")
    public void theDeviceHasAsIntValue(String key, Integer value) {
        deviceObject.getData().put(key, value);
    }

    @And("the device has {string} as double value {double}")
    public void theDeviceHasAsDoubleValue(String key, Double value) {
        deviceObject.getData().put(key, value);
    }

    @When("I send a POST request to create the item")
    public void iSendAPOSTRequestToCreateTheItem() {
        response = apiClient.createObject(deviceObject);
        
        if (response.statusCode() == 200) {
            String id = response.jsonPath().getString("id");
            context.set("lastCreatedId", id);
        }
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @And("the response should contain the device name {string}")
    public void theResponseShouldContainTheDeviceName(String name) {
        response.then().body("name", equalTo(name));
    }

    @And("the response should contain a valid ID")
    public void theResponseShouldContainAValidID() {
        response.then().body("id", notNullValue());
    }

    @Given("I have an existing item created with name {string}")
    public void iHaveAnExistingItemCreatedWithName(String name) {
        iHaveDeviceDataWithName(name);
        iSendAPOSTRequestToCreateTheItem();
        response.then().statusCode(200);
    }

    @When("I send a GET request for the created item ID")
    public void iSendAGETRequestForTheCreatedItemID() {
        String id = (String) context.get("lastCreatedId");
        response = apiClient.getObject(id);
    }

    @When("I send a GET request to list all items")
    public void iSendAGETRequestToListAllItems() {
        response = apiClient.listObjects();
    }

    @Then("the response should be a list of items")
    public void theResponseShouldBeAListOfItems() {
        response.then().body("$", Matchers.instanceOf(java.util.List.class));
    }

    @When("I send a DELETE request for the created item ID")
    public void iSendADELETERequestForTheCreatedItemID() {
        String id = (String) context.get("lastCreatedId");
        response = apiClient.deleteObject(id);
    }

    @And("the response message should confirm deletion")
    public void theResponseMessageShouldConfirmDeletion() {
        response.then().body("message", containsStringIgnoringCase("deleted"));
    }

    @When("I send a GET request for the deleted item ID")
    public void iSendAGETRequestForTheDeletedItemID() {
        iSendAGETRequestForTheCreatedItemID();
    }

    @When("I send a GET request for item ID {string}")
    public void iSendAGETRequestForItemID(String id) {
        response = apiClient.getObject(id);
    }
}
