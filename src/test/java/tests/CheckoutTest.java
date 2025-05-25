package tests;

import Pages.CartPage;
import Pages.CheckoutPage;
import Pages.LoginPage;
import Pages.ProductPage;
import org.json.simple.JSONObject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.TestDataReader;

import java.time.Duration;

public class CheckoutTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductPage productPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.demoblaze.com");

        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);

        JSONObject loginData = TestDataReader.getTestData("LoginData.validData");
        loginPage.openLoginModal();
        loginPage.enterUsername(loginData.get("validUsername").toString());
        loginPage.enterPassword(loginData.get("validPassword").toString());
        loginPage.submitLogin();

        Assert.assertTrue(
                loginPage.isLoginSuccessful(loginData.get("expectedWelcome").toString()),
                "Login failed"
        );

        productPage.openCategory("Laptops");
        productPage.selectProduct("MacBook Pro");
        productPage.clickAddToCart();
        productPage.handleAlertAndGetText();
        cartPage.goToCart();
        Assert.assertTrue(cartPage.isProductInCart("MacBook Pro"), "Product not found in cart");
    }

    @Test(priority = 1)
    public void testEmptyFormSubmission() {
        checkoutPage.clickPlaceOrder();
        checkoutPage.clickPurchaseWithoutFilling();
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        Assert.assertEquals(alertText, "Please fill out Name and Creditcard.");
        alert.accept();
    }

    @Test(priority = 2)
    public void testPartialFormSubmission() {
        JSONObject data = TestDataReader.getTestData("CheckoutData.partialData");
        checkoutPage.clickPlaceOrder();
        checkoutPage.fillCheckoutForm(
                data.get("name").toString(),
                data.get("country").toString(),
                data.get("city").toString(),
                data.get("creditCard").toString(),
                data.get("month").toString(),
                data.get("year").toString()
        );
        checkoutPage.clickPurchase();
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        Assert.assertEquals(alertText, "Please fill out Name and Creditcard.");
        alert.accept();
    }

    @Test(priority = 3)
    public void testInvalidCardSubmission() {
        JSONObject data = TestDataReader.getTestData("CheckoutData.invalidCardData");
        checkoutPage.clickPlaceOrder();
        checkoutPage.fillCheckoutForm(
                data.get("name").toString(),
                data.get("country").toString(),
                data.get("city").toString(),
                data.get("creditCard").toString(),
                data.get("month").toString(),
                data.get("year").toString()
        );
        checkoutPage.clickPurchase();
        Assert.assertFalse(
                checkoutPage.isConfirmationPopupVisible(),
                "Confirmation appeared for invalid card data. Expected rejection but site accepted."
        );
    }

    @Test(priority = 4)
    public void testSuccessfulCheckout() {
        JSONObject data = TestDataReader.getTestData("CheckoutData.validData");
        checkoutPage.clickPlaceOrder();
        checkoutPage.fillCheckoutForm(
                data.get("name").toString(),
                data.get("country").toString(),
                data.get("city").toString(),
                data.get("creditCard").toString(),
                data.get("month").toString(),
                data.get("year").toString()
        );
        checkoutPage.clickPurchase();

        Assert.assertTrue(checkoutPage.isConfirmationPopupVisible(), "Confirmation popup should appear");

        int amount = checkoutPage.extractAmountFromPopup();
        Assert.assertEquals(amount, 1100, "Product amount should be 1100 for MacBook Pro");

        checkoutPage.takeScreenshotOfPopup("checkout_success.png");
        checkoutPage.confirmPopup();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
