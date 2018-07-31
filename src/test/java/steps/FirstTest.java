package steps;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.LoginPage;
import testData.GlobalVars;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@Epic("Allure examples")
@Feature("Junit 4 support")
public class FirstTest {

    private static WebDriver driver;
    private static LoginPage loginPage;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "C:/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    @Story("Base support for bdd annotations")
    @Issue("ISSUE-1")
    @DisplayName("First case test")
    public void userLoginValidCredentials() {
        /**
         *  The User should be able to login with valid credentials */
        loginPage.Open();
        loginPage.login(GlobalVars.validUsername, GlobalVars.validPassword);
        assertEquals(loginPage.message.getText(), GlobalVars.welcomeMessage);


    }

    @Test
    @DisplayName("Second case test")
    @Description("Some detailed test description")
    public void userLoginInvalidUsername() {
        /**
         * The User should be unable to login with invalid credentials */
        loginPage.Open();
        loginPage.login(GlobalVars.invalidUsername, GlobalVars.validPassword);
        assertEquals(loginPage.message.getText(), GlobalVars.accessDeniedMessage);
    }

    @Test
    @DisplayName("Third case test")
    @Description("Some detailed test description")
    public void userLoginInvalidPassword() {
        /**
         *  The User should be unable to login with invalid credentials */
        loginPage.Open();
        loginPage.login(GlobalVars.validUsername, GlobalVars.invalidPassword);
        assertEquals(loginPage.message.getText(), GlobalVars.accessDeniedMessage);
    }

    @Test
    @DisplayName("Fourth case test")
    @Description("Some detailed test description")
    public void requestLoginCheckMessageValidCredentials() throws IOException {
        /**
         * Get message on page through http request with valid credentials */
        String test = loginPage.getResponseMessage(GlobalVars.validUsername, GlobalVars.validPassword);
        assertEquals(test, GlobalVars.welcomeMessage);
        /**
         *  Get status code of server response through http request with valid credentials */
        String test2 = loginPage.getResponseStatus(GlobalVars.validUsername, GlobalVars.validPassword);
        assertEquals(test2, "200");
    }

    @Test
    public void requestLoginCheckMessageInvalidUsername() throws IOException {
        /**
         *  Get message on page through http request with invalid credentials */
        String test = loginPage.getResponseMessage(GlobalVars.invalidUsername, GlobalVars.validPassword);
        assertEquals(test, GlobalVars.accessDeniedMessage);
    }

    @Test
    public void requestLoginCheckMessageInvalidPassword() throws IOException {
        /**
         * Get message on page through http request with invalid credentials */
        String test = loginPage.getResponseMessage(GlobalVars.validUsername, GlobalVars.invalidPassword);
        assertEquals(test, GlobalVars.accessDeniedMessage);
    }

    @Test
    public void userLoginWithoutCookie(){
        /**
         *  User should not be logged in without properly stored cookie */
        loginPage.Open();
        loginPage.login(GlobalVars.validUsername, GlobalVars.validPassword);
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        assertEquals(loginPage.message.getText(), GlobalVars.cookieMissingMessage);

    }

    @Rule
    public ScreenShotRule screenShootRule = new ScreenShotRule(driver);

//    @TestRule
//    public JUnitExecutionListener listener = new JUnitExecutionListener();

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}

