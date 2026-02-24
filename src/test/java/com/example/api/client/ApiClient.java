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
        return createObject(device, null);
    }

    public Response createObject(DeviceObject device, String apiKey) {
        return createObject(device, apiKey, "application/json");
    }

    public Response createObject(Object body, String apiKey, String contentType) {
        var request = RestAssured.given();
        
        if (contentType != null && !contentType.isEmpty()) {
            request.contentType(contentType);
        }
        
        if (apiKey != null && !apiKey.isEmpty()) {
            request.header("x-api-key", apiKey);
        }
        
    if (body instanceof String) {
            request.body((String) body);
        } else {
            request.body(body);
        }
        
        return request.post(Config.OBJECTS_ENDPOINT);
    }

    public Response getObject(String id) {
        return getObject(id, null, "application/json");
    }

    public Response getObject(String id, String apiKey, String contentType) {
        var request = RestAssured.given();
        
        if (contentType != null && !contentType.isEmpty()) {
            request.contentType(contentType);
        }
        
        if (apiKey != null && !apiKey.isEmpty()) {
            request.header("x-api-key", apiKey);
        }
        
        return request.get(Config.OBJECTS_ENDPOINT + "/" + id);
    }

    public Response listObjects() {
        return listObjects(null, "application/json");
    }

    public Response listObjects(String apiKey, String contentType) {
        var request = RestAssured.given();
        
        if (contentType != null && !contentType.isEmpty()) {
            request.contentType(contentType);
        }
        
        if (apiKey != null && !apiKey.isEmpty()) {
            request.header("x-api-key", apiKey);
        }
        
        return request.get(Config.OBJECTS_ENDPOINT);
    }

    public Response deleteObject(String id) {
        return deleteObject(id, null, "application/json");
    }

    public Response deleteObject(String id, String apiKey, String contentType) {
        var request = RestAssured.given();
        
        if (contentType != null && !contentType.isEmpty()) {
            request.contentType(contentType);
        }
        
        if (apiKey != null && !apiKey.isEmpty()) {
            request.header("x-api-key", apiKey);
        }
        
        return request.delete(Config.OBJECTS_ENDPOINT + "/" + id);
    }
}
