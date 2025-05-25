package Selectors;

import org.openqa.selenium.By;

public class LoginSelectors {
    public static final By LOGIN_MODAL_BUTTON = By.id("login2");
    public static final By LOGIN_USERNAME_INPUT = By.id("loginusername");
    public static final By LOGIN_PASSWORD_INPUT = By.id("loginpassword");
    public static final By LOGIN_BUTTON = By.xpath("//button[text()='Log in']");
    public static final By LOGOUT_BUTTON = By.id("logout2");
    public static final By WELCOME_USER = By.id("nameofuser");
}
