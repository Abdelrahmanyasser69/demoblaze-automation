package tests;

import Pages.LoginPage;
import utils.TestDataReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.json.simple.JSONObject;
import java.time.Duration;


public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.demoblaze.com");
        loginPage = new LoginPage(driver);
    }

    @Test(priority = 1)
    public void testValidLogin() {
        JSONObject loginData = TestDataReader.getTestData("LoginData.validData");
        String username = loginData.get("validUsername").toString();
        String password = loginData.get("validPassword").toString();
        String expectedWelcome = loginData.get("expectedWelcome").toString();

        loginPage.openLoginModal();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.submitLogin();

        Assert.assertTrue(
                loginPage.isLoginSuccessful(expectedWelcome),
                "Login failed or welcome message not displayed correctly"
        );

        loginPage.clickLogout();
    }
    @Test(priority = 2)
    public void testInvalidLogin() {
        JSONObject loginData = TestDataReader.getTestData("LoginData.invalidData");
        String username = loginData.get("invalidUsername").toString();
        String password = loginData.get("invalidPassword").toString();

        loginPage.openLoginModal();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.submitLogin();

        String alertMessage = loginPage.getLoginAlertMessage();
        loginPage.acceptAlert();

        Assert.assertEquals(alertMessage, "Wrong password.", "Unexpected alert message for invalid login.");
    }

    @Test(priority = 3)
    public void testLoginWithEmptyFields() {
        loginPage.openLoginModal();
        loginPage.submitLogin();

        String alertMessage = loginPage.getLoginAlertMessage();
        loginPage.acceptAlert();

        Assert.assertEquals(alertMessage, "Please fill out Username and Password.", "Unexpected alert message for empty fields.");
    }



    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
