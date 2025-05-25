package Selectors;

import org.openqa.selenium.By;

public class CartSelectors {
    public static final By CART_LINK = By.id("cartur");
    public static final By CART_PRODUCT_NAMES = By.xpath("//tr/td[2]");
    public static final By CART_PRODUCT_PRICES = By.xpath("//tr/td[3]");
    public static final By CART_TOTAL = By.id("totalp");
    public static final By DELETE_BUTTON = By.cssSelector("a[onclick^='deleteItem']");

}
