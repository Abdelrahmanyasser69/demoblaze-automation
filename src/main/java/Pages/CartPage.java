package Pages;

import Selectors.CartSelectors;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(CartSelectors.CART_LINK)).click();
    }

    public List<String> getProductNames() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(CartSelectors.CART_PRODUCT_NAMES))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<Integer> getProductPrices() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(CartSelectors.CART_PRODUCT_PRICES))
                .stream()
                .map(e -> Integer.parseInt(e.getText()))
                .collect(Collectors.toList());
    }

    public int getCartTotal() {
        WebElement total = wait.until(ExpectedConditions.visibilityOfElementLocated(CartSelectors.CART_TOTAL));
        return Integer.parseInt(total.getText());
    }

    public void clearCart() {
        while (true) {
            try {
                WebElement deleteButton = wait.until(ExpectedConditions.visibilityOfElementLocated(CartSelectors.DELETE_BUTTON));
                deleteButton.click();
                Thread.sleep(1000); // Allow DOM update
            } catch (TimeoutException e) {
                break;
            } catch (StaleElementReferenceException e) {
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean isProductInCart(String productName) {
        return getProductNames().contains(productName);
    }
}
