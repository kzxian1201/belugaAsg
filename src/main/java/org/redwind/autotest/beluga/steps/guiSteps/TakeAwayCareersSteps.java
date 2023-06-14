package org.redwind.autotest.beluga.steps.guiSteps;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.redwind.autotest.beluga.pages.webApp.TakeAwayCareersPage;
import org.redwind.autotest.beluga.utils.PropertyReader;
import org.redwind.autotest.beluga.utils.WrapperMethods;
import org.testng.Assert;

import java.time.Duration;
import java.util.HashSet;

public class TakeAwayCareersSteps extends WrapperMethods {
    private PropertyReader propertyReader = new PropertyReader();
    private String jobCount;
    private String oldWindowID;
    private String oldPageTitle;
    /**
     *
     *This method opens the career page of Just Eat Take Away in browser
     *and accept the cookies
     *
     */
    @Given("Open career page of Takeaway")
    public void openCareersPage() {
        openApplication(propertyReader.getGuiProperty("Takeaway_Career"));
        waitForElementToBeClickable(TakeAwayCareersPage.ALLOW_COOKIES,Duration.ofSeconds(120));
        clickOnElement(TakeAwayCareersPage.ALLOW_COOKIES);
    }
    /**
     *
     *Method enter the custom job in job category input field and searches it
     * @param jobTitle - Custom job title(passed from feature file)
     */
    @When("Job is searched globally for Job Title {string}")
    public void searchForCustomJob(String jobTitle) {
        waitForPresenceOfElementLocated(TakeAwayCareersPage.SEARCH_JOB_INPUT_FIELD, Duration.ofSeconds(120));
        enterText(TakeAwayCareersPage.SEARCH_JOB_INPUT_FIELD, jobTitle);
        clickOnElement(TakeAwayCareersPage.SEARCH_BUTTON);
    }
    /**
     *
     *Function used to validate heading is updated as per the job title
     * @param jobTitle - Custom job title(passed from feature file)
     */
    @Then("Verify search result matches the Job title {string}")
    public void verifySearchResultHeading(String jobTitle) {
        String expectedSearchResultHeading="Showing Search results for "+"\""+jobTitle+"\"";
        scrollToElement(TakeAwayCareersPage.SEARCH_RESULT_AREA);
        waitForPresenceOfElementLocated(TakeAwayCareersPage.SEARCH_KEYWORD_HEADING,Duration.ofSeconds(120));
        String actualHeading = getTextFromElement(TakeAwayCareersPage.SEARCH_KEYWORD_HEADING);
        Assert.assertEquals(actualHeading,expectedSearchResultHeading,"Search heading matches the custom search keyword");
    }
    /**
     *
     *Function used to validate the job search result contains jobs posted globally
     */
    @And("Verify search result contain jobs posted in different locations")
    public void verifyJobResultsDisplayedGlobally() {
        waitForPresenceOfElementLocated(TakeAwayCareersPage.SEARCH_RESULT_AREA,Duration.ofSeconds(120));
        HashSet<String> setOfCountries = new HashSet<>();
        for(int i=1;i<=getListOfElements(TakeAwayCareersPage.SEARCH_RESULT_PAGES).size();i++) {
            for(int j=1;j<=getListOfElements(TakeAwayCareersPage.LIST_OF_JOB_SEARCHED).size();j++) {
                setOfCountries.add(getCountryNameFromSearchedJob(TakeAwayCareersPage.getJobLocationFromSearchResult(j)));
            }
            if(getListOfElements(TakeAwayCareersPage.NEXT_PAGE_IN_SEARCH_RESULT).isEmpty()){
                logger.info("We are in last page of job search results");
            } else {
                clickOnElement(TakeAwayCareersPage.NEXT_PAGE_IN_SEARCH_RESULT);
            }
        }
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, MarkupHelper.createLabel("------> List of counties where jobs are posted "+setOfCountries+"<------", ExtentColor.BLUE));
        Assert.assertTrue(setOfCountries.size()>1,"Job from different countries are not shown in the result");
    }
    /**
     *
     *Function used to filter the job for specific country
     * @param country - Country to filter for search (passed from feature file)
     */
    @And("Refine the search for country {string}")
    public void filterJobForSpecificCountry(String country) {
        scrollToElement(TakeAwayCareersPage.REFINE_YOUR_SEARCH);
        waitForElementToBeClickable(TakeAwayCareersPage.FILTER_COUNTRY,Duration.ofSeconds(120));
        clickOnElement(TakeAwayCareersPage.FILTER_COUNTRY);
        scrollToElement(TakeAwayCareersPage.FILTER_BY_VALUE(country));
        waitForElementToBeClickable(TakeAwayCareersPage.FILTER_BY_VALUE(country),Duration.ofSeconds(120));
        clickOnElement(TakeAwayCareersPage.FILTER_BY_VALUE(country));
        boolean flag = isChecked(TakeAwayCareersPage.CHECKBOX_FOR_FILTER(country));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createLabel("------> "+country+" checkbox status is "+flag+"<------",ExtentColor.BLUE));
        Assert.assertTrue(flag,"Country is not selected");
    }
    /**
     *
     *Function used to validate the search result has a job posted in specific country
     * @param country - Country to filter for search (passed from feature file)
     */
    @And("Verify search result contains only jobs posted in {string}")
    public void verifyJobResultsDisplayedForSpecifiedCountry(String country) {
        waitForPresenceOfElementLocated(TakeAwayCareersPage.SEARCH_RESULT_AREA,Duration.ofSeconds(120));
        scrollToElement(TakeAwayCareersPage.SEARCH_RESULT_AREA);
        HashSet<String> setOfCountries = new HashSet<>();
        for(int i=1;i<=getListOfElements(TakeAwayCareersPage.SEARCH_RESULT_PAGES).size();i++) {
            for(int j=1;j<=getListOfElements(TakeAwayCareersPage.LIST_OF_JOB_SEARCHED).size();j++) {
                setOfCountries.add(getCountryNameFromSearchedJob(TakeAwayCareersPage.getJobLocationFromSearchResult(j)));
            }
            if(getListOfElements(TakeAwayCareersPage.NEXT_PAGE_IN_SEARCH_RESULT).isEmpty()){
                logger.info("We are in last page of job search results");
            } else {
                clickOnElement(TakeAwayCareersPage.NEXT_PAGE_IN_SEARCH_RESULT);
            }
        }
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createLabel("------> Resulted jobs are posted in "+setOfCountries+"<------",ExtentColor.BLUE));
        Assert.assertTrue(setOfCountries.size()==1 && setOfCountries.toString().contains(country) ,"Job from different countries are shown in the result");
    }
    /**
     *
     *Function used to select the specific job category
     * @param jobCategory - Job Category (passed from feature file)
     */
    @When("{string} is selected from Job Category dropdown")
    public void selectJobCategoryFromDropdown(String jobCategory) {
        waitForPresenceOfElementLocated(TakeAwayCareersPage.SEARCH_JOB_INPUT_FIELD, Duration.ofSeconds(120));
        clickOnElement(TakeAwayCareersPage.SEARCH_JOB_INPUT_FIELD);
        waitForElementToBeClickable(TakeAwayCareersPage.JOB_CATEGORY_DROPDOWN(jobCategory),Duration.ofSeconds(120));
        scrollToElement(TakeAwayCareersPage.JOB_CATEGORY_DROPDOWN(jobCategory));
        jobCount = getTextFromElement(TakeAwayCareersPage.JOB_CATEGORY_DROPDOWN_COUNT(jobCategory));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createLabel("------> Job count for the category "+jobCategory+" is "+jobCount+"<------",ExtentColor.BLUE));
        clickOnElement(TakeAwayCareersPage.JOB_CATEGORY_DROPDOWN(jobCategory));
    }
    /**
     *
     *Method used to validate the selected job is selected in refine search section
     * @param jobCategory - Job Category (passed from feature file)
     */
    @And("Verify {string} is selected automatically in Refine your search section")
    public void validateSelectionOfJobCategoryInFilterSection(String jobCategory) {
        waitForElementToBeClickable(TakeAwayCareersPage.REFINE_YOUR_SEARCH,Duration.ofSeconds(120));
        scrollToElement(TakeAwayCareersPage.REFINE_YOUR_SEARCH);
        if(getValueFromElement(TakeAwayCareersPage.FILTER_CATEGORY,"aria-expanded").contains("false")){
            clickOnElement(TakeAwayCareersPage.FILTER_CATEGORY);
        }
        scrollToElement(TakeAwayCareersPage.FILTER_BY_VALUE(jobCategory));
        boolean flag = isChecked(TakeAwayCareersPage.CHECKBOX_FOR_FILTER(jobCategory));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createLabel("------> "+jobCategory+" checkbox status is "+flag+"<------",ExtentColor.BLUE));
        Assert.assertTrue(flag,"Job category is not selected automatically in search section");
    }
    /**
     *
     *Function used verify number of search result matches the job count
     * @param jobCategory - Job Category (passed from feature file)
     */
    @And("Verify number of search result matches the number of Job count section for {string}")
    public void validateJobCountMatchesAsPerSelection(String jobCategory) {
        String actualJobCountInFilter = getTextFromElement(TakeAwayCareersPage.JOB_COUNT_IN_FILTER(jobCategory));
        Assert.assertTrue(actualJobCountInFilter.contains(jobCount),"Job count does not matched in filter");
        String actualJobCountInList = getTextFromElement(TakeAwayCareersPage.JOB_LIST_COUNT);
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createLabel("------> Job count displayed for the category "+jobCategory+" in the list is "+actualJobCountInList+"<------",ExtentColor.BLUE));
        Assert.assertTrue(actualJobCountInList.contains(jobCount),"Job count does not matched in List");
    }
    /**
     *
     *Method used to validate job count matches in the list and the filter
     * @param country - country name for filter (passed from feature file)
     */
    @And("Verify number of search result matches the job count for {string}")
    public void validateJobCountMatchesForCountry(String country) {
        scrollToElement(TakeAwayCareersPage.JOB_COUNT_IN_FILTER(country));
        String jobCountForCountry = getTextFromElement(TakeAwayCareersPage.JOB_COUNT_IN_FILTER(country));
        jobCountForCountry = jobCountForCountry.replace("(","");
        jobCountForCountry = jobCountForCountry.replace(")","");
        jobCountForCountry=jobCountForCountry.trim();
        scrollToElement(TakeAwayCareersPage.JOB_LIST_COUNT);
        String jobCountInList = getTextFromElement(TakeAwayCareersPage.JOB_LIST_COUNT);
        Assert.assertTrue(jobCountForCountry.replace("Jobs","").contains(jobCountInList.replace("Jobs","")),"Search result does not matches the country count");
    }
    /**
     *
     *Function used to validate the search result contains job posted in specific country
     * @param jobCategory - Job Category (passed from feature file)
     * @param country - country name for filter (passed from feature file)
     */
    @And("Verify search result contain only for job category as {string} in {string}")
    public void validateSearchResultHasCorrectJobCategory(String jobCategory, String country) {
        waitForPresenceOfElementLocated(TakeAwayCareersPage.SEARCH_RESULT_AREA,Duration.ofSeconds(120));
        scrollToElement(TakeAwayCareersPage.SEARCH_RESULT_AREA);
        HashSet<String> setOfJobCategories = new HashSet<>();
        HashSet<String> setOfLocation = new HashSet<>();
        for(int i=1;i<=getListOfElements(TakeAwayCareersPage.SEARCH_RESULT_PAGES).size();i++) {
            for(int j=1;j<=getListOfElements(TakeAwayCareersPage.LIST_OF_JOB_SEARCHED).size();j++) {
                setOfJobCategories.add(getJobCategoryFromSearchedJob(TakeAwayCareersPage.getJobCategoryFromSearchResult(j)));
                setOfLocation.add(getCountryNameFromSearchedJob(TakeAwayCareersPage.getJobLocationFromSearchResult(j)));
            }
            if(getListOfElements(TakeAwayCareersPage.NEXT_PAGE_IN_SEARCH_RESULT).isEmpty()){
                logger.info("We are in last page of job search results");
            } else {
                clickOnElement(TakeAwayCareersPage.NEXT_PAGE_IN_SEARCH_RESULT);
            }
        }
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createLabel("------> Job Category shown in the result is "+setOfJobCategories+"<------",ExtentColor.BLUE));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createLabel("------> Job location posted in the result is "+setOfLocation+"<------",ExtentColor.BLUE));
        Assert.assertTrue(setOfJobCategories.size()==1 && setOfJobCategories.toString().contains(jobCategory) ,"Different job Categories are shown in the result");
        Assert.assertTrue(setOfLocation.size()==1 && setOfLocation.toString().contains(country) ,"Job from different countries are shown in the result");
    }
    /**
     *
     *Function used to select job category from widget
     * @param jobCategory - Job Category (passed from feature file)
     */
    @When("{string} is selected from Job Category widget")
    public void clickJobCategoryOnWidget(String jobCategory){
        waitForPresenceOfElementLocated(TakeAwayCareersPage.JOB_CATEGORY_WIDGET_HEADING, Duration.ofSeconds(120));
        scrollToElement(TakeAwayCareersPage.JOB_CATEGORY_WIDGET_HEADING);
        waitForElementToBeClickable(TakeAwayCareersPage.JOB_CATEGORY_WIDGET(jobCategory),Duration.ofSeconds(120));
        scrollToElement(TakeAwayCareersPage.JOB_CATEGORY_WIDGET(jobCategory));
        oldPageTitle = getTitle();
        oldWindowID = getCurrentWindowID();
        clickOnElement(TakeAwayCareersPage.JOB_CATEGORY_WIDGET(jobCategory));
    }
    /**
     *
     *method validates search result opens in new tab
     */
    @Then("Verify search result is opened in new tab")
    public void validateResultIsOpenedInNewWindow() {
        switchToLastOpenedWindow();
        String newPageTitle = getTitle();
        String newWindowID = getCurrentWindowID();
        Assert.assertNotEquals(oldPageTitle,newPageTitle,"Old Page title is same as of new");
        Assert.assertNotEquals(oldWindowID,newWindowID,"Search result is not opened in new tab");
    }
    /**
     *
     *Method used to validate clear all button clears the filter
     * @param jobCategory - Job Category (passed from feature file)
     */
    @And("Validate {string} is not selected after clicking clear filter")
    public void validateClearAllFilter(String jobCategory) {
        scrollToElement(TakeAwayCareersPage.REFINE_YOUR_SEARCH);
        waitForElementToBeClickable(TakeAwayCareersPage.CLEAR_ALL, Duration.ofSeconds(120));
        clickOnElement(TakeAwayCareersPage.CLEAR_ALL);
        scrollToElement(TakeAwayCareersPage.REFINE_YOUR_SEARCH);
        waitForElementToBeClickable(TakeAwayCareersPage.FILTER_CATEGORY, Duration.ofSeconds(120));
        if(getValueFromElement(TakeAwayCareersPage.FILTER_CATEGORY,"aria-expanded").contains("false")){
            clickOnElement(TakeAwayCareersPage.FILTER_CATEGORY);
        }
        boolean flag = isChecked(TakeAwayCareersPage.CHECKBOX_FOR_FILTER(jobCategory));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,MarkupHelper.createLabel("------> "+jobCategory+" checkbox status is "+flag+"<------",ExtentColor.BLUE));
        Assert.assertFalse(flag,"Job category is selected after clicking clear all filter");
    }
    /**
     *
     *Method help to get the country from search result
     * @param locator - web element
     */
    public String getCountryNameFromSearchedJob(By locator) {
        String location = getTextFromElement(locator);
        if(location.contains("Location")){
            location = location.replace("Location","");}
        String[] country = location.split(",");
        int index= country.length-1;
        location = country[index];
        return location.trim();
    }
    /**
     *
     *Method help to get the category from search result
     * @param locator - web element
     */
    public String getJobCategoryFromSearchedJob(By locator) {
        String category = getTextFromElement(locator);
        if(category.contains("Category")){
            category = category.replace("Category","");}
        return category.trim();
    }

}
