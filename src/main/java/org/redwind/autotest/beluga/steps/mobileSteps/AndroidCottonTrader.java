package org.redwind.autotest.beluga.steps.mobileSteps;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.redwind.autotest.beluga.pages.mobileApp.android.CottonTraderMainPage;
import org.redwind.autotest.beluga.utils.GenericFunctions;
import org.redwind.autotest.beluga.utils.WrapperMethods;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AndroidCottonTrader extends WrapperMethods {

    private String color;
    @Given("User verifies he is present in main landing page")
    public void validateMainLandingPage() {
        waitForPresenceOfElementLocated(CottonTraderMainPage.SKIP_ONBOARDING_BUTTON, Duration.ofSeconds(120));
        if(isElementDisplayed(CottonTraderMainPage.SKIP_ONBOARDING_BUTTON)) {
            clickOnElement(CottonTraderMainPage.SKIP_ONBOARDING_BUTTON);
        } else {
            System.out.println("not present");
        }
    }
    @And("User searches for {string} from home page search")
    public void searchKeyword(String keyword) throws InterruptedException {
        waitForPresenceOfElementLocated(CottonTraderMainPage.SEARCH,Duration.ofSeconds(120));
        clickOnElement(CottonTraderMainPage.SEARCH);
        waitForPresenceOfElementLocated(CottonTraderMainPage.SEARCH_INPUT_FIELD,Duration.ofSeconds(120));
        clickOnElement(CottonTraderMainPage.SEARCH_INPUT_FIELD);
        enterText(CottonTraderMainPage.SEARCH_INPUT_FIELD,keyword);
        enterPress();
        Thread.sleep(10000);
    }
    @And("Select the {string} item from the list")
    public void selectGivenItem(String item){
        int tileForSelection = Integer.parseInt(item);
        int iteration = tileForSelection / 2;
        for(int i=1;i<=iteration;i++) {
            List<WebElement> list = getListOfElements(CottonTraderMainPage.RESULT_LIST);
            moveToMobileElements(list.get(1).getLocation(),list.get(3).getLocation());
        }
        List<WebElement> list = getListOfElements(CottonTraderMainPage.RESULT_LIST);
        clickOnElement(list.get(0));
    }
    @And("Choose the color of the item if available")
    public void selectColorOdItem() {
        waitForPresenceOfElementLocated(CottonTraderMainPage.COLOR,Duration.ofSeconds(120));
        List<WebElement> list = getListOfElements(CottonTraderMainPage.COLOR);
        int availableColors=list.size();
        GenericFunctions genericFunctions = new GenericFunctions();
        String number=genericFunctions.getRandomNumber(0,availableColors-1);
        color = getValueFromElement(list.get(Integer.parseInt(number)),"content-desc");
        clickOnElement(list.get(Integer.parseInt(number)));
    }
    @When("User adds the item to bag")
    public void addItemToBag() {
        waitForPresenceOfElementLocated(CottonTraderMainPage.ADD_TO_CART, Duration.ofSeconds(120));
        clickOnElement(CottonTraderMainPage.ADD_TO_CART);
        waitForPresenceOfElementLocated(CottonTraderMainPage.SIZE_MEDIUM, Duration.ofSeconds(120));
        clickOnElement(CottonTraderMainPage.SIZE_MEDIUM);
    }
    @Then("Validate selected {string} item present in cart with specified color")
    public void validateItemInCart(String keyword){
        waitForPresenceOfElementLocated(CottonTraderMainPage.ADD_TO_CART,Duration.ofSeconds(120));
        clickOnElement(CottonTraderMainPage.CART);
        waitForPresenceOfElementLocated(CottonTraderMainPage.TEXT_CART,Duration.ofSeconds(120));
        List<WebElement> list = getListOfElements(CottonTraderMainPage.TEXT_CART);
        List<String> cartString = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            cartString.add(getTextFromElement(list.get(i)));
        }
        System.out.println(color);
        System.out.println(cartString.get(1));
        Assert.assertTrue(cartString.get(0).contains(keyword),"Keyword is not matched");
        Assert.assertTrue(cartString.get(1).contains(color),"color is not matched");
    }

}
