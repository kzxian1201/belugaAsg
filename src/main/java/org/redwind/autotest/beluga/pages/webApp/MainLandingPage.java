package org.redwind.autotest.beluga.pages.webApp;

import org.openqa.selenium.By;

public class MainLandingPage {
    private MainLandingPage() {}
    public static final By MAIN_HEADING = By.xpath("//div[@class='app_logo']");
    public static final By FILTER_VALUE = By.xpath("//select[@class='product_sort_container']");
}
