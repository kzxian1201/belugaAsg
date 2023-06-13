package org.redwind.autotest.beluga.configuration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.redwind.autotest.beluga.utils.APIHelper;

import java.io.File;
import java.net.URL;

public class RestHelper implements APIHelper
{
    @Override
    public Response get(ContentType contentType, URL endpoint, Header authorization) {
        Response response = null;
        response = RestAssured.given()
                .header(authorization)
                .when()
                .get(endpoint).then()
                .contentType(contentType)
                .extract().response();
        return response;
    }
    @Override
    public Response get(ContentType contentType, URL endpoint, Header authorization,Header cookie) {
        Response response = null;
        response = RestAssured.given()
                .header(cookie)
                .header(authorization)
                .when()
                .get(endpoint).then()
                .contentType(contentType)
                .extract().response();
        return response;
    }
    @Override
    public Response get(URL endpoint, Header authorization,Header cookie) {
        Response response = null;
        response = RestAssured.given()
                .header(cookie)
                .header(authorization)
                .when()
                .get(endpoint).then()
                .extract().response();
        return response;
    }
    @Override
    public Response post(ContentType contentType, URL endpoint, Header authorization, File filePath) {
        Response response = null;
        response = RestAssured.given()
                .header(authorization)
                .contentType(contentType)
                .body(filePath)
                .when()
                .post(endpoint)
                .then()
                .extract().response();
        return response;
    }
    public Response post(ContentType contentType, URL endpoint,Header authorization,Header cookie, File filePath){
        Response response = null;
        response = RestAssured.given()
                .header(authorization)
                .header(cookie)
                .contentType(contentType)
                .body(filePath)
                .when()
                .post(endpoint)
                .then()
                .extract().response();
        return response;
    }

    @Override
    public Response delete(URL endpoint, Header authorization) {
        Response response = null;
        response = RestAssured.given()
                .header(authorization)
                .when()
                .delete(endpoint)
                .then()
                .extract().response();
        return response;
    }
    @Override
    public Response delete(URL endpoint, Header authorization, Header cookie) {
        Response response = null;
        response = RestAssured.given()
                .header(authorization)
                .header(cookie)
                .when()
                .delete(endpoint)
                .then()
                .extract().response();
        return response;
    }

    @Override
    public Response patch(URL endpoint, Header authorization, ContentType contentType, File body) {
        Response response = null;
        response = RestAssured.given()
                .header(authorization)
                .contentType(contentType)
                .body(body)
                .when()
                .patch(endpoint)
                .then()
                .extract().response();
        return response;
    }
    @Override
    public Response patch(URL endpoint, Header authorization,Header cookies, ContentType contentType, File body) {
        Response response = null;
        response = RestAssured.given()
                .header(authorization)
                .header(cookies)
                .contentType(contentType)
                .body(body)
                .when()
                .patch(endpoint)
                .then()
                .extract().response();
        return response;
    }

    @Override
    public Response put(URL endpoint, Header authorization, ContentType contentType, File body) {
        Response response = null;
        response = RestAssured.given()
                .header(authorization)
                .contentType(contentType)
                .body(body)
                .when()
                .put(endpoint)
                .then().extract().response();
        return response;
    }
}
