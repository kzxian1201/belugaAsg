package org.redwind.autotest.beluga.steps.apiSteps;

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
import org.apache.http.HttpStatus;
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
    /**
     *
     *Method help to generate access token for all process
     */
    @Given("Generate an access token")
    public void generateAccessToken() throws MalformedURLException {
        URL endpoint = new URL(propertyReader.getApiProperty("takeAway_base_url")+propertyReader.getApiProperty("auth"));
        File body = new File(System.getProperty("user.dir") + propertyReader.getApiProperty("takeAwayAuth"));
        Header header = new Header("Authorization",propertyReader.getApiProperty("authorization"));
        response = apiHelper.post(ContentType.JSON,endpoint,header,body);
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, "Assess token is not created since response code do not match");
        accessToken += jsonHelper.getJsonString(response,"token");
    }
    /**
     *
     *Method help to read all the booking details
     */
    @And("Read all booking details")
    public void readAllBookingDetails() throws MalformedURLException {
        URL endpoint = new URL(propertyReader.getApiProperty("takeAway_base_url")+propertyReader.getApiProperty("booking"));
        Header header = new Header("Authorization",propertyReader.getApiProperty("authorization"));
        response = apiHelper.get(ContentType.JSON,endpoint,header,new Header("Cookie",accessToken));
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, "booking details are not fetched since response code do not match");
    }
    /**
     *
     *Method help to create new booking
     */
    @When("Create a new booking details")
    public void createNewBooking() throws IOException {
        URL endpoint = new URL(propertyReader.getApiProperty("takeAway_base_url")+propertyReader.getApiProperty("booking"));
        Header header = new Header("Authorization",propertyReader.getApiProperty("authorization"));
        File body = new File(System.getProperty("user.dir") + propertyReader.getApiProperty("createBooking"));
        updateBookingDetailsJSON(createBookingJsonPath);
        response = apiHelper.post(ContentType.JSON,endpoint,header,new Header("Cookie",accessToken),body);
    }
    /**
     *
     *Method helps to validate newly created booking
     */
    @And("Validate new booking is created")
    public void validateNewBookingIsCreated() {
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "New booking might not be created since status code do not match");
        generatedID = jsonHelper.getJsonInteger(response,"bookingid");
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createLabel("------> Generated booking ID is "+generatedID+"<------",ExtentColor.BLUE));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createLabel("------> Newly created booking record <------",ExtentColor.BLUE));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createCodeBlock(response.asString(), CodeLanguage.JSON));
        Assert.assertFalse(jsonHelper.getJsonInteger(response,"bookingid").describeConstable().isEmpty(),"Booking ID is not generated");
    }
    /**
     *
     *Method helps to validate newly created booking is present in the list by getting all records
     */
    @And("Validate newly created booking details present in the list")
    public void validateNewBookingIsPresent() {
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "created is not present since status code do not match");
        JSONArray jsonArray = new JSONArray(response.asString());
        ArrayList<Integer> listOfBookingID = new ArrayList<>();
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            listOfBookingID.add(jsonObject.getInt("bookingid"));
        }
        Assert.assertTrue(listOfBookingID.contains(generatedID),"Newly generated booking is not present in the list");
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createLabel("------> Newly created booking Id "+generatedID+" is present in the database<------",ExtentColor.BLUE));
    }
    /**
     *
     *Method help to get the newly created record
     */
    @And("Read booking details of newly created record")
    public void readSpecificBooking() throws MalformedURLException {
        URL endpoint = new URL(propertyReader.getApiProperty("takeAway_base_url")+propertyReader.getApiProperty("booking")+generatedID);
        Header header = new Header("Authorization",propertyReader.getApiProperty("authorization"));
        response = apiHelper.get(ContentType.JSON,endpoint,header,new Header("Cookie",accessToken));
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "details for booking id is not fetched since status code do not match");
    }
    /**
     *
     *Method helps to update newly created record
     */
    @And("Update the details in newly created booking record")
    public void updateExistingBooking() throws IOException {
        updateBookingDetailsJSON(updateBookingJsonPath);
        URL endpoint = new URL(propertyReader.getApiProperty("takeAway_base_url")+propertyReader.getApiProperty("booking")+generatedID);
        Header header = new Header("Authorization",propertyReader.getApiProperty("authorization"));
        File body = new File(System.getProperty("user.dir") + propertyReader.getApiProperty("updateBooking"));
        response = apiHelper.patch(endpoint,header,new Header("Cookie",accessToken),ContentType.JSON,body);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Response code does not match");
        Assert.assertEquals(jsonHelper.getJsonString(response,"firstname"),firstName,"First Name is not updated");
        Assert.assertEquals(jsonHelper.getJsonString(response,"lastname"),lastName,"Last Name is not updated");
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createLabel("------> First Name is modified to "+firstName+"<------",ExtentColor.BLUE));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createLabel("------> Last Name is modified to "+lastName+"<------",ExtentColor.BLUE));
    }
    /**
     *
     *Method helps to Validate booking details are updated accordingly
     */
    @And("Validate details are modified in new record")
    public void validateUpdatedRecord() {
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Response code does not match");
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createLabel("------> Modified booking record <------",ExtentColor.BLUE));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createCodeBlock(response.asString(), CodeLanguage.JSON));
        Assert.assertEquals(jsonHelper.getJsonString(response,"firstname"),firstName,"First Name is not updated");
        Assert.assertEquals(jsonHelper.getJsonString(response,"lastname"),lastName,"First Name is not updated");
    }
    /**
     *
     *Method helps to delete newly created record
     */
    @Then("Delete newly created record")
    public void deleteCreatedBooking() throws MalformedURLException {
        URL endpoint = new URL(propertyReader.getApiProperty("takeAway_base_url")+propertyReader.getApiProperty("booking")+generatedID);
        Header header = new Header("Authorization",propertyReader.getApiProperty("authorization"));
        response = apiHelper.delete(endpoint,header,new Header("Cookie",accessToken));
        Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_CREATED,"status code doesn't match and record is not deleted");

    }
    /**
     *
     *Method helps to Validate newly created record is deleted
     */
    @And("Validate newly created booking details are deleted from list")
    public void validateBookingDeleted() throws MalformedURLException {
        URL endpoint = new URL(propertyReader.getApiProperty("takeAway_base_url")+propertyReader.getApiProperty("booking")+generatedID);
        Header header = new Header("Authorization",propertyReader.getApiProperty("authorization"));
        response = apiHelper.get(endpoint,header,new Header("Cookie",accessToken));
        Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_NOT_FOUND,"Booking details are not deleted");
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createLabel("------> Response while trying to fetch deleted record is "+response.asString()+"<------",ExtentColor.BLUE));
        Assert.assertEquals(response.asString(),"Not Found", "Booking details are not deleted");
    }
    /**
     *
     *Method helps to update the json file for the body (create and update records)
     */
    public void updateBookingDetailsJSON(String filePath) throws IOException {
        String suffix = genericFunctions.getRandomNumber(299,5600);
        firstName = "Deepak"+suffix;
        lastName = "Mahalingam"+suffix;
        JsonParser jsonParser = new JsonParser();
        JsonObject root = jsonParser.parse(new FileReader(filePath)).getAsJsonObject();
        root.addProperty("firstname",firstName);
        root.addProperty("lastname",lastName);
        try(FileWriter writer = new FileWriter(filePath)){
        writer.write(root.toString());
        }
    }

}
