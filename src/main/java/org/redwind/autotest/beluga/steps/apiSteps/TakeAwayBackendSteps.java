package org.redwind.autotest.beluga.steps.apiSteps;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.redwind.autotest.beluga.configuration.RestHelper;
import org.redwind.autotest.beluga.utils.APIHelper;
import org.redwind.autotest.beluga.utils.GenericFunctions;
import org.redwind.autotest.beluga.utils.JsonHelper;
import org.redwind.autotest.beluga.utils.PropertyReader;
import org.testng.Assert;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TakeAwayBackendSteps {
    private APIHelper apiHelper = new RestHelper();
    public static final Logger logger = LogManager.getFormatterLogger();
    private PropertyReader propertyReader = new PropertyReader();
    private GenericFunctions genericFunctions = new GenericFunctions();
    private JsonHelper jsonHelper = new JsonHelper();
    private String accessToken = "token=";
    private Response response;
    private int generatedID;
    private String firstName;
    private String lastName;
    private String createBookingJsonPath=System.getProperty("user.dir") + propertyReader.getApiProperty("createBooking");
    private String updateBookingJsonPath=System.getProperty("user.dir") + propertyReader.getApiProperty("updateBooking");

    ExtentTest test;
    @Given("Generate an access token")
    public void generateAccessToken() throws MalformedURLException {
        URL endpoint = new URL(propertyReader.getApiProperty("takeAway_base_url")+propertyReader.getApiProperty("auth"));
        File body = new File(System.getProperty("user.dir") + propertyReader.getApiProperty("takeAwayAuth"));
        Header header = new Header("Authorization",propertyReader.getApiProperty("authorization"));
        response = apiHelper.post(ContentType.JSON,endpoint,header,body);
        accessToken += jsonHelper.getJsonString(response,"token");
        logger.info(response.asString());
    }
    @And("Read all booking details")
    public void readAllBookingDetails() throws MalformedURLException {
        URL endpoint = new URL(propertyReader.getApiProperty("takeAway_base_url")+propertyReader.getApiProperty("booking"));
        Header header = new Header("Authorization",propertyReader.getApiProperty("authorization"));
        response = apiHelper.get(ContentType.JSON,endpoint,header,new Header("Cookie",accessToken));
    }
    @When("Create a new booking details")
    public void createNewBooking() throws IOException {
        URL endpoint = new URL(propertyReader.getApiProperty("takeAway_base_url")+propertyReader.getApiProperty("booking"));
        Header header = new Header("Authorization",propertyReader.getApiProperty("authorization"));
        File body = new File(System.getProperty("user.dir") + propertyReader.getApiProperty("createBooking"));
        updateBookingDetailsJSON(createBookingJsonPath);
        response = apiHelper.post(ContentType.JSON,endpoint,header,new Header("Cookie",accessToken),body);
    }
    @And("Validate new booking is created")
    public void validateNewBookingIsCreated() {
        int statusCode = 200;
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createLabel("------> Status code is "+response.getStatusCode()+"<------",ExtentColor.BLUE));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createCodeBlock(response.asString(), CodeLanguage.JSON));
        Assert.assertEquals(response.getStatusCode(), statusCode, "Response code does not match");
        generatedID = jsonHelper.getJsonInteger(response,"bookingid");
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createLabel("------> Generated booking ID "+generatedID+"<------",ExtentColor.BLUE));
        Assert.assertFalse(jsonHelper.getJsonInteger(response,"bookingid").describeConstable().isEmpty(),"Booking ID is not generated");
    }
    @And("Validate newly created booking details present in the list")
    public void validateNewBookingIsPresent() {
        int statusCode = 200;
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createLabel("------> Status code is "+response.getStatusCode()+"<------",ExtentColor.BLUE));
        Assert.assertEquals(response.getStatusCode(), statusCode, "Response code does not match");
        JSONArray jsonArray = new JSONArray(response.asString());
        ArrayList<Integer> listOfBookingID = new ArrayList<>();
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            listOfBookingID.add(jsonObject.getInt("bookingid"));
        }
        Assert.assertTrue(listOfBookingID.contains(generatedID),"Newly generated booking is not present in the list");
    }
    @And("Read booking details of newly created record")
    public void readSpecificBooking() throws MalformedURLException {
        URL endpoint = new URL(propertyReader.getApiProperty("takeAway_base_url")+propertyReader.getApiProperty("booking")+generatedID);
        Header header = new Header("Authorization",propertyReader.getApiProperty("authorization"));
        response = apiHelper.get(ContentType.JSON,endpoint,header,new Header("Cookie",accessToken));
    }
    @And("Update the details in newly created booking record")
    public void updateExistingBooking() throws IOException {
        updateBookingDetailsJSON(updateBookingJsonPath);
        URL endpoint = new URL(propertyReader.getApiProperty("takeAway_base_url")+propertyReader.getApiProperty("booking")+generatedID);
        Header header = new Header("Authorization",propertyReader.getApiProperty("authorization"));
        File body = new File(System.getProperty("user.dir") + propertyReader.getApiProperty("updateBooking"));
        response = apiHelper.patch(endpoint,header,new Header("Cookie",accessToken),ContentType.JSON,body);
        int statusCode = 200;
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createLabel("------> Status code is "+response.getStatusCode()+"<------",ExtentColor.BLUE));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createCodeBlock(response.asString(), CodeLanguage.JSON));
        Assert.assertEquals(response.getStatusCode(), statusCode, "Response code does not match");
        Assert.assertEquals(jsonHelper.getJsonString(response,"firstname"),firstName,"First Name is not updated");
        Assert.assertEquals(jsonHelper.getJsonString(response,"lastname"),lastName,"First Name is not updated");
    }
    @And("Validate details are modified in new record")
    public void validateUpdatedRecord() {
        int statusCode = 200;
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createLabel("------> Status code is "+response.getStatusCode()+"<------",ExtentColor.BLUE));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createCodeBlock(response.asString(), CodeLanguage.JSON));
        Assert.assertEquals(response.getStatusCode(), statusCode, "Response code does not match");
        Assert.assertEquals(jsonHelper.getJsonString(response,"firstname"),firstName,"First Name is not updated");
        Assert.assertEquals(jsonHelper.getJsonString(response,"lastname"),lastName,"First Name is not updated");
    }
    @Then("Delete newly created record")
    public void deleteCreatedBooking() throws MalformedURLException {
        URL endpoint = new URL(propertyReader.getApiProperty("takeAway_base_url")+propertyReader.getApiProperty("booking")+generatedID);
        Header header = new Header("Authorization",propertyReader.getApiProperty("authorization"));
        response = apiHelper.delete(endpoint,header,new Header("Cookie",accessToken));
        int responseCode = 201;
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createLabel("------> Status code is "+response.getStatusCode()+"<------",ExtentColor.BLUE));
        Assert.assertEquals(response.getStatusCode(),responseCode,"status code doesn't match and record is not deleted");
    }
    @And("Validate newly created booking details are deleted from list")
    public void validateBookingDeleted() throws MalformedURLException {
        URL endpoint = new URL(propertyReader.getApiProperty("takeAway_base_url")+propertyReader.getApiProperty("booking")+generatedID);
        Header header = new Header("Authorization",propertyReader.getApiProperty("authorization"));
        response = apiHelper.get(endpoint,header,new Header("Cookie",accessToken));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createLabel("------> Response for deleted record is "+response.asString()+"<------",ExtentColor.BLUE));
        Assert.assertEquals(response.getStatusCode(),404,"Booking details are not deleted");
        Assert.assertEquals(response.asString(),"Not Found", "Booking details are not deleted");
    }
    public void updateBookingDetailsJSON(String filePath) throws IOException {
        String sufix = genericFunctions.getRandomNumber(299,5600);
        firstName = "Deepak"+sufix;
        lastName = "Mahalingam"+sufix;
        JsonParser jsonParser = new JsonParser();
        JsonObject root = jsonParser.parse(new FileReader(filePath)).getAsJsonObject();
        root.addProperty("firstname",firstName);
        root.addProperty("lastname",lastName);
        FileWriter writer = new FileWriter(filePath);
        writer.write(root.toString());
        writer.close();
    }

}
