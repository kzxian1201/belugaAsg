package org.redwind.autotest.beluga.steps.apiSteps;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.redwind.autotest.beluga.utils.APIHelper;
import org.redwind.autotest.beluga.configuration.RestHelper;
import org.redwind.autotest.beluga.utils.GenericFunctions;
import org.redwind.autotest.beluga.utils.JsonHelper;
import org.redwind.autotest.beluga.utils.PropertyReader;
import org.testng.Assert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UserFunctions
{
    private APIHelper apiHelper = new RestHelper();
    private URL endpoint;
    private ContentType contentType;
    private Header header;
    private Response response;
    private JsonHelper jsonHelper = new JsonHelper();
    private File jsonBody ;
    int userID;
    private GenericFunctions genericFunctions = new GenericFunctions();
    private String createdUser;
    private PropertyReader propertyReader = new PropertyReader();
    public static final Logger logger = LogManager.getFormatterLogger();


    @Given("User is fed with the correct authentications and endpoint details")
    public void collectInformation() throws IOException {
        setEndpoint(new URL(propertyReader.getApiProperty("base_url")+propertyReader.getApiProperty("users_detail")));
        setHeader(new Header("Authorization",propertyReader.getApiProperty("authorization")));
        setContentType(ContentType.JSON);
    }
    @Then("User performs get calls")
    public void performGetCall() {
        response = apiHelper.get(getContentType(),getEndpoint(),getHeader());
        ExtentCucumberAdapter.addTestStepLog("Response --->  "+ response.asString());
        setResponse(response);
    }

    @And("verifies the response and response code")
    public void validateResponse() {
        int responseCode = response.getStatusCode();
        String status = jsonHelper.getJsonString(getResponse(),"status");
        Assert.assertEquals(status,"success","Status mismatch");
        Assert.assertEquals(responseCode,200,"Response code is not correct");
    }

    @Given("User is fed with the correct authentication, payload and endpoints")
    public void collectPostInformation() throws IOException {
        setEndpoint(new URL(propertyReader.getApiProperty("base_url")+propertyReader.getApiProperty("create_user")));
        setHeader(new Header("Authorization",propertyReader.getApiProperty("authorization")));
        setContentType(ContentType.JSON);
        updateCreateUserJson();
        String body = System.getProperty("user.dir") + propertyReader.getApiProperty("createUserPath");
        jsonBody = new File(body);
    }

    @When("User performs post call out")
    public void postCallOut() {
        response = apiHelper.post(getContentType(),getEndpoint(),getHeader(),jsonBody);
        ExtentCucumberAdapter.addTestStepLog("Response --->  "+ response.asString());
        setResponse(response);
        userID = jsonHelper.getIntegerFromJsonObj(response,"data","id");
        logger.info(userID);
    }

    @And("Validates the post response")
    public void validatesPostResponse() {
        int responseCode = getResponse().getStatusCode();
        String status = jsonHelper.getJsonString(getResponse(),"status");
        String message = jsonHelper.getJsonString(getResponse(),"message");
        String userName = jsonHelper.getStringFromJsonObj(getResponse(),"data","name");
        Assert.assertEquals(status,"success","Status mismatch");
        Assert.assertEquals(responseCode,200,"Response code is not correct");
        Assert.assertEquals(message,"Successfully! Record has been added.", "Create user message is incorrect");
        Assert.assertEquals(userName,getCreatedUser(),"username is not updated in json");
    }

    @And("Verify new user is added to the list")
    public void verifyCreatedUser() {
        int responseCode = getResponse().getStatusCode();
        String status = jsonHelper.getJsonString(getResponse(),"status");
        String userName = jsonHelper.getStringFromJsonObj(getResponse(),"data","employee_name");
        Assert.assertEquals(status,"Success","Status mismatch");
        Assert.assertEquals(responseCode,200,"Response code is not correct");
        Assert.assertEquals(userName,getCreatedUser(),"User is not created due to mismatch");
    }

    public void updateCreateUserJson() throws IOException {
        JSONObject jsonObject = new JSONObject(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + propertyReader.getApiProperty("createUserPath"))));
        setCreatedUser(genericFunctions.getRandomString());
        jsonObject.put("name", getCreatedUser());
        jsonObject.put("salary", genericFunctions.getRandomNumber(10,10000));
        jsonObject.put("age",genericFunctions.getRandomNumber(1,99));
        try (FileWriter file = new FileWriter(System.getProperty("user.dir") + propertyReader.getApiProperty("createUserPath"))) {
            file.write(jsonObject.toString());
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
    public String getCreatedUser() {
        return createdUser;
    }
    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }
}
