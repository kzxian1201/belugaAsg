package org.redwind.autotest.beluga.pages.mobileApp.android;

import org.openqa.selenium.By;

public class CottonTraderMainPage {

    private CottonTraderMainPage() {

    }
    public static final By  SKIP_ONBOARDING_BUTTON = By.id("com.cottontradersltd.cottontraders:id/onboarding_page_skip_button");
    public static final By SEARCH = By.id("com.cottontradersltd.cottontraders:id/action_search");
    public static final By SEARCH_INPUT_FIELD = By.id("com.cottontradersltd.cottontraders:id/search_toolbar_edit_text");
    public static final By RESULT_LIST = By.xpath("//*[contains(@class,'RecyclerView')]/android.widget.Button");
    public static final By COLOR = By.xpath("//*[contains(@class,'RecyclerView')]/android.widget.Button");
    public static final By ADD_TO_CART=By.xpath("//*[contains(@class,'android.widget.Button') and contains(@text,'ADD TO BAG')]");
    public static final By SIZE_MEDIUM = By.xpath("//*[@resource-id='com.cottontradersltd.cottontraders:id/item_picker_container' and @index='3']");
    public static final By CART=By.id("com.cottontradersltd.cottontraders:id/action_bag");
    public static final By TEXT_CART = By.xpath("//*[@resource-id='com.cottontradersltd.cottontraders:id/brand_title_container']/android.widget.TextView");


}
