package Selectors;

import org.openqa.selenium.By;

public class ProductSelectors {
    public static final By LAPTOPS_CATEGORY = By.linkText("Laptops");
    public static final By PRODUCT_LINK_MACBOOK = By.linkText("MacBook Pro");
    public static final By PRODUCT_NAME = By.cssSelector(".name");
    public static final By PRODUCT_PRICE = By.cssSelector(".price-container");
    public static final By PRODUCT_DESCRIPTION = By.cssSelector("#more-information .description, .description");
    public static final By ADD_TO_CART_BUTTON = By.linkText("Add to cart");
    public static final By PRODUCT_NAMES = By.cssSelector(".card-title");

}
