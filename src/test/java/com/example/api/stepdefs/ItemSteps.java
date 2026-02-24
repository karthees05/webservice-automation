package com.example.api.stepdefs;

import com.example.api.client.ApiClient;
import com.example.api.model.DeviceObject;
import com.example.api.utils.TestContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
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

    @Given("I have an/a {string} header with value {string}")
    public void iHaveAnHeaderWithValue(String key, String value) {
        context.set("header_" + key, value);
    }

    @Given("I have a valid session")
    public void iHaveAValidSession() {
        context.set("header_x-api-key", "0b78d9ba-8ee3-4362-90ae-d364bc590812");
        context.set("header_Content-Type", "application/json");
    }

    @When("I send a POST request to create the item")
    public void iSendAPOSTRequestToCreateTheItem() throws JsonProcessingException {
        String apiKey = (String) context.get("header_x-api-key");
        String contentType = (String) context.get("header_Content-Type");
        
        if (contentType == null) {
            contentType = "application/json";
        }
        
        Object body = deviceObject;
        if (!contentType.contains("json") && deviceObject != null) {
            // Manually serialize if not JSON to avoid RestAssured serialization error
            body = new ObjectMapper().writeValueAsString(deviceObject);
        }
        
        response = apiClient.createObject(body, apiKey, contentType);
        
        if (response.statusCode() == 200) {
            String id = response.jsonPath().getString("id");
            context.set("lastCreatedId", id);
        }
    }

    @When("I send a POST request to create the item with a malformed body")
    public void iSendAPOSTRequestToCreateTheItemWithAMalformedBody() {
        String apiKey = (String) context.get("header_x-api-key");
        String contentType = (String) context.get("header_Content-Type");

        if (contentType == null) {
            contentType = "application/json";
        }

        String malformedJson = "{ \"name\": \"Malformed\", \"data\": { \"year\": 2019 "; // Missing closing braces
        response = apiClient.createObject(malformedJson, apiKey, contentType);
    }

    @When("I send a POST request to create the item with an empty body")
    public void iSendAPOSTRequestToCreateTheItemWithAnEmptyBody() {
        String apiKey = (String) context.get("header_x-api-key");
        String contentType = (String) context.get("header_Content-Type");
        
        if (contentType == null) {
            contentType = "application/json";
        }
        
        response = apiClient.createObject("", apiKey, contentType);
    }

    @When("I send a POST request to create the item with missing name")
    public void iSendAPOSTRequestToCreateTheItemWithMissingName() {
        String apiKey = (String) context.get("header_x-api-key");
        String contentType = (String) context.get("header_Content-Type");
        
        if (contentType == null) {
            contentType = "application/json";
        }
        
        DeviceObject device = DeviceObject.builder()
                .data(new HashMap<>())
                .build();
        
        response = apiClient.createObject(device, apiKey, contentType);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        response.then().assertThat().statusCode(statusCode);
    }

    @And("the response should contain the device name {string}")
    public void theResponseShouldContainTheDeviceName(String name) {
        response.then().assertThat().body("name", equalTo(name));
    }

    @And("the response should contain a valid ID")
    public void theResponseShouldContainAValidID() {
        response.then().assertThat().body("id", notNullValue());
    }

    @And("I store the current ID as {string}")
    public void iStoreTheCurrentIDAs(String key) {
        String id = response.jsonPath().getString("id");
        context.set(key, id);
    }

    @Given("I have an existing item created with name {string}")
    public void iHaveAnExistingItemCreatedWithName(String name) throws JsonProcessingException {
        iHaveDeviceDataWithName(name);
        iSendAPOSTRequestToCreateTheItem();
        response.then().statusCode(200);
    }

    @When("I send a GET request for the created item ID")
    public void iSendAGETRequestForTheCreatedItemID() {
        String id = (String) context.get("lastCreatedId");
        String apiKey = (String) context.get("header_x-api-key");
        String contentType = (String) context.get("header_Content-Type");
        response = apiClient.getObject(id, apiKey, contentType);
    }

    @When("I send a GET request for items with stored IDs {string} and {string}")
    public void iSendAGETRequestForItemsWithStoredIDs(String key1, String key2) {
        String id1 = (String) context.get(key1);
        String id2 = (String) context.get(key2);
        String apiKey = (String) context.get("header_x-api-key");
        String contentType = (String) context.get("header_Content-Type");
        response = apiClient.listObjectsWithIds(java.util.List.of(id1, id2), apiKey, contentType);
    }

    @When("I send a GET request to list all items")
    public void iSendAGETRequestToListAllItems() {
        String apiKey = (String) context.get("header_x-api-key");
        String contentType = (String) context.get("header_Content-Type");
        response = apiClient.listObjects(apiKey, contentType);
    }

    @Then("the response should be a list of items")
    public void theResponseShouldBeAListOfItems() {
        response.then().assertThat().body("$", Matchers.instanceOf(java.util.List.class));
    }

    @And("the response should contain items with names {string} and {string}")
    public void theResponseShouldContainItemsWithNames(String name1, String name2) {
        response.then().assertThat().body("name", hasItems(name1, name2));
    }

    @When("I send a DELETE request for the created item ID")
    public void iSendADELETERequestForTheCreatedItemID() {
        String id = (String) context.get("lastCreatedId");
        String apiKey = (String) context.get("header_x-api-key");
        String contentType = (String) context.get("header_Content-Type");
        response = apiClient.deleteObject(id, apiKey, contentType);
    }

    @When("I send a DELETE request for item ID {string}")
    public void iSendADELETERequestForItemID(String id) {
        String apiKey = (String) context.get("header_x-api-key");
        String contentType = (String) context.get("header_Content-Type");
        response = apiClient.deleteObject(id, apiKey, contentType);
    }

    @And("the response message should confirm deletion")
    public void theResponseMessageShouldConfirmDeletion() {
        response.then().assertThat().body("message", containsStringIgnoringCase("deleted"));
    }

    @When("I send a GET request for the deleted item ID")
    public void iSendAGETRequestForTheDeletedItemID() {
        iSendAGETRequestForTheCreatedItemID();
    }

    @When("I send a GET request for item ID {string}")
    public void iSendAGETRequestForItemID(String id) {
        String apiKey = (String) context.get("header_x-api-key");
        String contentType = (String) context.get("header_Content-Type");
        response = apiClient.getObject(id, apiKey, contentType);
    }

    @When("I send a PUT request to update the created item")
    public void iSendAPUTRequestToUpdateTheCreatedItem() {
        String id = (String) context.get("lastCreatedId");
        String apiKey = (String) context.get("header_x-api-key");
        String contentType = (String) context.get("header_Content-Type");
        response = apiClient.updateObject(id, deviceObject, apiKey, contentType);
    }

    @When("I send a PATCH request to update the created item")
    public void iSendAPATCHRequestToUpdateTheCreatedItem() {
        String id = (String) context.get("lastCreatedId");
        String apiKey = (String) context.get("header_x-api-key");
        String contentType = (String) context.get("header_Content-Type");
        response = apiClient.partiallyUpdateObject(id, deviceObject, apiKey, contentType);
    }

    @When("I send a PUT request to update item ID {string}")
    public void iSendAPUTRequestToUpdateItemID(String id) {
        String apiKey = (String) context.get("header_x-api-key");
        String contentType = (String) context.get("header_Content-Type");
        response = apiClient.updateObject(id, deviceObject, apiKey, contentType);
    }

    @And("the response should match the Device schema")
    public void theResponseShouldMatchTheDeviceSchema() {
        response.then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/device_schema.json"));
    }

    @And("the response should match the Device List schema")
    public void theResponseShouldMatchTheDeviceListSchema() {
        response.then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/device_list_schema.json"));
    }
}
