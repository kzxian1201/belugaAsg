package org.redwind.autotest.beluga.pages.webApp;

import org.openqa.selenium.By;

public class TakeAwayCareersPage {
    private TakeAwayCareersPage(){}
    public static final By ALLOW_COOKIES=By.xpath("//button[ppc-content[contains(text(),'Allow')]]");
    public static final By SEARCH_JOB_INPUT_FIELD= By.xpath("//input[@id='keywordSearch']");
    public static final By SEARCH_BUTTON=By.xpath("//button[@type='submit']");
    public static final By SEARCH_RESULT_AREA=By.xpath("//div[contains(@class,'ph-search-results-area')]");
    public static final By SEARCH_KEYWORD_HEADING=By.xpath("//h2[@show.bind='searchKeyword']");
    public static final By LIST_OF_JOB_SEARCHED=By.xpath("//div[@class='phs-jobs-list']/div[@class='content-block']/ul/li");
    public static final By SEARCH_RESULT_PAGES=By.xpath("//div[contains(@class,'search-bottom-count')]/following-sibling::ul/li/a[contains(@aria-label,'Page')]");
    public static final By NEXT_PAGE_IN_SEARCH_RESULT=By.xpath("//li[a[contains(@aria-label,'Page') and @aria-current='true']]/following-sibling::li[1]/a[contains(@aria-label,'Page')]");
    public static final By getJobLocationFromSearchResult(int index) {
        By replaceXpath = By.xpath("//li["+index+"]//span[@class='job-location']");
        return replaceXpath;
    }
    public static final By getJobCategoryFromSearchResult(int index) {
        By replaceXpath = By.xpath("//li["+index+"]//span[@class='au-target category']");
        return replaceXpath;
    }
    public static final By FILTER_COUNTRY=By.xpath("//button[contains(text(),'Country')]");
    public static final By FILTER_CATEGORY=By.xpath("//button[contains(text(),'Category')]");
    public static final By CHECKBOX_FOR_FILTER(String filterName) {
        By replaceXpath = By.xpath("//span[contains(text(),'"+filterName+"')]/preceding-sibling::input");
        return replaceXpath;
    }
    public static final By FILTER_BY_VALUE(String name) {
        By replaceXpath = By.xpath("//label[span[contains(text(),'"+name+"')]]");
        return replaceXpath;
    }
    public static final By REFINE_YOUR_SEARCH=By.xpath("//h2//*[contains(text(),'Refine your search')]");
    public static final By JOB_CATEGORY_DROPDOWN(String value) {
        By replaceXpath = By.xpath("//a[span[contains(text(),'"+value+"')]]");
        return replaceXpath;
    }
    public static final By JOB_CATEGORY_DROPDOWN_COUNT(String value) {
        By replaceXpath = By.xpath("//a[span[contains(text(),'"+value+"')]]/span[contains(@class,'count')]");
        return replaceXpath;
    }
    public static final By JOB_CATEGORY_DROPDOWN_LABEL=By.xpath("//h2//*[contains(text(),'Job Categories')]");
    public static final By JOB_COUNT_IN_FILTER(String value) {
        By replaceXpath = By.xpath("//span[contains(text(),'"+value+"')]/following-sibling::span[@class='result-jobs-count']");
        return replaceXpath;
    }
    public static final By JOB_LIST_COUNT=By.xpath("//div[contains(@class,'jobs-list-count')]");
    public static final By JOB_CATEGORY_WIDGET_HEADING=By.xpath("//h2//*[contains(text(),'Find your fit')]");
    public static final By JOB_CATEGORY_WIDGET(String value) {
        By replaceXpath = By.xpath("//div[div[div[ppc-container[span[ppc-content[div[font[span[text()='"+value+"']]]]]]]]]/preceding-sibling::a");
        return replaceXpath;
    }
    public static final By CLEAR_ALL=By.xpath("//a[contains(@class,'clearall')]");
}
