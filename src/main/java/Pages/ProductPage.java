package Pages;

import Selectors.ProductSelectors;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openCategory(String category) {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText(category))).click();
    }

    public void selectProduct(String productName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By productLocator = By.linkText(productName);
        wait.until(ExpectedConditions.presenceOfElementLocated(productLocator));
        wait.until(ExpectedConditions.elementToBeClickable(productLocator));

        WebElement productElement = wait.until(driver -> driver.findElement(productLocator));

        for (int i = 0; i < 2; i++) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", productElement);
                productElement.click();
                break;
            } catch (StaleElementReferenceException e) {
                productElement = driver.findElement(productLocator);
            }
        }
    }

    public boolean isProductPresentByName(String productName) {
        try {
            return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ProductSelectors.PRODUCT_NAMES))
                    .stream()
                    .anyMatch(p -> {
                        try {
                            return p.getText().equalsIgnoreCase(productName);
                        } catch (StaleElementReferenceException e) {
                            return false; // Skip stale elements
                        }
                    });
        } catch (StaleElementReferenceException e) {
            // Retry once
            List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ProductSelectors.PRODUCT_NAMES));
            return products.stream().anyMatch(p -> p.getText().equalsIgnoreCase(productName));
        }
    }


    public String getProductName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ProductSelectors.PRODUCT_NAME)).getText();
    }

    public String getProductPrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ProductSelectors.PRODUCT_PRICE)).getText();
    }

    public String getProductDescription() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ProductSelectors.PRODUCT_DESCRIPTION)).getText();
    }

    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(ProductSelectors.ADD_TO_CART_BUTTON)).click();
    }

    public String handleAlertAndGetText() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String text = alert.getText();
        alert.accept();
        return text;
    }
}
