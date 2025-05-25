package Pages;

import Selectors.LoginSelectors;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openLoginModal() {
        WebElement loginModalButton = wait.until(ExpectedConditions.elementToBeClickable(LoginSelectors.LOGIN_MODAL_BUTTON));
        loginModalButton.click();
    }

    public void enterUsername(String username) {
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(LoginSelectors.LOGIN_USERNAME_INPUT));
        usernameInput.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(LoginSelectors.LOGIN_PASSWORD_INPUT));
        passwordInput.sendKeys(password);
    }

    public void submitLogin() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(LoginSelectors.LOGIN_BUTTON));
        loginButton.click();
    }

    public boolean isLoginSuccessful(String expectedUsername) {
        WebElement welcomeUser = wait.until(ExpectedConditions.visibilityOfElementLocated(LoginSelectors.WELCOME_USER));
        return welcomeUser.getText().contains(expectedUsername);
    }

    public void clickLogout() {
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(LoginSelectors.LOGOUT_BUTTON));
        logoutButton.click();
    }

    public String getLoginAlertMessage() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        return alert.getText();
    }

    public void acceptAlert() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }
}
