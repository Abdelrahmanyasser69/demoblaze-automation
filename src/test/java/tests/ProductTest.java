package tests;

import Pages.CartPage;
import Pages.LoginPage;
import Pages.ProductPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.json.simple.JSONObject;
import utils.TestDataReader;

import java.time.Duration;
import java.util.List;

public class ProductTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductPage productPage;
    private CartPage cartPage;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.demoblaze.com");

        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);

        JSONObject loginData = TestDataReader.getTestData("LoginData.validData");
        String username = loginData.get("validUsername").toString();
        String password = loginData.get("validPassword").toString();

        loginPage.openLoginModal();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.submitLogin();

        Assert.assertTrue(loginPage.isLoginSuccessful("Welcome " + username), "Login failed");

        cartPage.goToCart();
        cartPage.clearCart();
        driver.get("https://www.demoblaze.com");
    }

    @Test(priority = 1)
    public void testAddMultipleProductsAndValidateCart() {
        JSONObject productData = TestDataReader.getTestData("ProductData");
        String alertMessageExpected = productData.get("addToCartAlertMessage").toString();

        productPage.openCategory("Laptops");
        productPage.selectProduct("MacBook Pro");
        String product1Name = productPage.getProductName();
        String product1PriceText = productPage.getProductPrice();
        int product1Price = Integer.parseInt(product1PriceText.replaceAll("[^\\d]", ""));
        productPage.clickAddToCart();
        Assert.assertEquals(productPage.handleAlertAndGetText(), alertMessageExpected);

        driver.navigate().to("https://www.demoblaze.com");

        productPage.openCategory("Laptops");
        productPage.selectProduct("Sony vaio i5");
        String product2Name = productPage.getProductName();
        String product2PriceText = productPage.getProductPrice();
        int product2Price = Integer.parseInt(product2PriceText.replaceAll("[^\\d]", ""));
        productPage.clickAddToCart();
        Assert.assertEquals(productPage.handleAlertAndGetText(), alertMessageExpected);

        cartPage.goToCart();
        List<String> cartNames = cartPage.getProductNames();
        List<Integer> cartPrices = cartPage.getProductPrices();
        int total = cartPage.getCartTotal();

        Assert.assertTrue(cartNames.contains(product1Name));
        Assert.assertTrue(cartNames.contains(product2Name));
        Assert.assertTrue(cartPrices.contains(product1Price));
        Assert.assertTrue(cartPrices.contains(product2Price));

        int expectedTotal = product1Price + product2Price;
        Assert.assertEquals(total, expectedTotal, "Cart total incorrect");
    }

    @Test(priority = 2)
    public void testSearchProductValidation() {
        productPage.openCategory("Laptops");

        boolean isFound = productPage.isProductPresentByName("macbook pro");
        Assert.assertTrue(isFound, "Product not found in the list.");
    }

    @Test(priority = 3)
    public void testNonExistentProductShouldNotAppear() {
        productPage.openCategory("Laptops");
        boolean isFound = productPage.isProductPresentByName("Nokia 3310");
        Assert.assertFalse(isFound, "Non-existent product should not be visible in category.");
    }


    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
