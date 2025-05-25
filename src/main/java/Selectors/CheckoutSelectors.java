package Selectors;

import org.openqa.selenium.By;

public class CheckoutSelectors {
    public static final By PLACE_ORDER_BUTTON = By.xpath("//button[text()='Place Order']");
    public static final By NAME_INPUT = By.id("name");
    public static final By COUNTRY_INPUT = By.id("country");
    public static final By CITY_INPUT = By.id("city");
    public static final By CARD_INPUT = By.id("card");
    public static final By MONTH_INPUT = By.id("month");
    public static final By YEAR_INPUT = By.id("year");
    public static final By PURCHASE_BUTTON = By.xpath("//button[text()='Purchase']");
    public static final By CONFIRMATION_POPUP = By.cssSelector(".sweet-alert.showSweetAlert.visible");
    public static final By CONFIRMATION_TEXT = By.cssSelector(".sweet-alert.showSweetAlert.visible p");
    public static final By OK_BUTTON = By.xpath("//button[text()='OK']");
}
