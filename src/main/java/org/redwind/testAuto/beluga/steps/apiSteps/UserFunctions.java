package org.redwind.testAuto.beluga.steps.apiSteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.redwind.testAuto.beluga.configuration.RestHelper;
import org.redwind.testAuto.beluga.utils.APIHelper;

import java.net.MalformedURLException;
import java.net.URL;

public class UserFunctions
{
private APIHelper  apiHelper = new RestHelper();
private URL endpoint;
private ContentType contentType;
private Header header;
private Response response;


@Given("User is fed with the correct authentications and endpoint details")
public void collectInformation() throws MalformedURLException {
   setEndpoint(new URL("https://reqres.in/api/users"));
   setHeader(new Header("Authorization","No Auth"));
   setContentType(ContentType.JSON);
}
@Then("User performs get calls")
public void performGetCall() {
   Response response = apiHelper.get(getContentType(),getEndpoint(),getHeader());
   System.out.println("Response --->  "+ response.asString());
   setResponse(response);
}

@And("verifies the response and response code")
public void validateResponse() {
    int responseCode = response.getStatusCode();
    if(responseCode==200) {
        System.out.println("Correct Response");
    } else {
        System.out.print("not passed");
    }
}

public void setEndpoint(URL endpoint) {
      this.endpoint = endpoint;
}

public URL getEndpoint() {
      return this.endpoint;
}

public void setContentType(ContentType contentType) {
      this.contentType=contentType;
}

public ContentType getContentType() {
      return this.contentType;
}

public void setHeader(Header header) {
      this.header = header;
}
public Header getHeader() {
      return this.header;
}
public void setResponse(Response response) {
    this.response = response;
}
public Response getResponse() {
    return this.response;
}
}
