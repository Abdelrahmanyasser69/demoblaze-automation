package Pages;

import Selectors.CheckoutSelectors;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class CheckoutPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickPlaceOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(CheckoutSelectors.PLACE_ORDER_BUTTON)).click();
    }

    public void fillCheckoutForm(String name, String country, String city, String creditCard, String month, String year) {
        driver.findElement(CheckoutSelectors.NAME_INPUT).sendKeys(name);
        driver.findElement(CheckoutSelectors.COUNTRY_INPUT).sendKeys(country);
        driver.findElement(CheckoutSelectors.CITY_INPUT).sendKeys(city);
        driver.findElement(CheckoutSelectors.CARD_INPUT).sendKeys(creditCard);
        driver.findElement(CheckoutSelectors.MONTH_INPUT).sendKeys(month);
        driver.findElement(CheckoutSelectors.YEAR_INPUT).sendKeys(year);
    }

    public void clickPurchase() {
        wait.until(ExpectedConditions.elementToBeClickable(CheckoutSelectors.PURCHASE_BUTTON)).click();
    }

    public void clickPurchaseWithoutFilling() {
        wait.until(ExpectedConditions.elementToBeClickable(CheckoutSelectors.PURCHASE_BUTTON)).click();
    }

    public boolean isFormStillOpen() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(CheckoutSelectors.NAME_INPUT));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isConfirmationPopupVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(CheckoutSelectors.CONFIRMATION_POPUP)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getAlertTextIfPresent() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            return alert.getText();
        } catch (TimeoutException e) {
            return null;
        }
    }

    public int extractAmountFromPopup() {
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(CheckoutSelectors.CONFIRMATION_TEXT)).getText();
        System.out.println("Popup Text: " + text);
        String[] lines = text.split("\\n");

        for (String line : lines) {
            if (line.contains("Amount")) {
                return Integer.parseInt(line.replaceAll("[^0-9]", ""));
            }
        }

        throw new RuntimeException("Amount not found in confirmation popup");
    }


    public void takeScreenshotOfPopup(String filename) {
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File screenshotsDir = new File("screenshots");

        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdirs();
        }

        try {
            FileHandler.copy(screenshotFile, new File(screenshotsDir, filename));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save screenshot", e);
        }
    }


    public void confirmPopup() {
        wait.until(ExpectedConditions.elementToBeClickable(CheckoutSelectors.OK_BUTTON)).click();
    }
}
