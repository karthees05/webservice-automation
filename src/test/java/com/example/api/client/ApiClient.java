package com.example.api.client;

import com.example.api.config.Config;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiClient {

    public ApiClient() {
        RestAssured.baseURI = Config.BASE_URL;
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

    public Response listObjectsWithIds(java.util.List<String> ids, String apiKey, String contentType) {
        var request = RestAssured.given();

        if (contentType != null && !contentType.isEmpty()) {
            request.contentType(contentType);
        }

        if (apiKey != null && !apiKey.isEmpty()) {
            request.header("x-api-key", apiKey);
        }

        if (ids != null && !ids.isEmpty()) {
            for (String id : ids) {
                request.queryParam("id", id);
            }
        }

        return request.get(Config.OBJECTS_ENDPOINT);
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

    public Response updateObject(String id, Object body, String apiKey, String contentType) {
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

        return request.put(Config.OBJECTS_ENDPOINT + "/" + id);
    }

    public Response partiallyUpdateObject(String id, Object body, String apiKey, String contentType) {
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

        return request.patch(Config.OBJECTS_ENDPOINT + "/" + id);
    }
}
