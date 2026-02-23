package com.example.api.client;

import com.example.api.config.Config;
import com.example.api.model.DeviceObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ApiClient {

    public ApiClient() {
        RestAssured.baseURI = Config.BASE_URL;
    }

    public Response createObject(DeviceObject device) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(device)
                .post(Config.OBJECTS_ENDPOINT);
    }

    public Response getObject(String id) {
        return RestAssured.get(Config.OBJECTS_ENDPOINT + "/" + id);
    }

    public Response listObjects() {
        return RestAssured.get(Config.OBJECTS_ENDPOINT);
    }

    public Response deleteObject(String id) {
        return RestAssured.delete(Config.OBJECTS_ENDPOINT + "/" + id);
    }
}
