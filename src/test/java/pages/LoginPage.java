package pages;
import testData.GlobalVars;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginPage {

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public WebDriver driver;

    @FindBy(css = "#usr")
    private WebElement usernameField;

    @FindBy(css = "#pwd")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@type='submit']")
    private WebElement submit;

    @FindBy(xpath = "//*[@id='case_login']/h3")
    public WebElement message;

    @FindBy(css = "#case_login h3")
    private WebElement result;

    public void Open(){
        driver.get(GlobalVars.baseUrl + "/login");
    }

    public void login(String username, String password) {
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        this.submit.click();
    }

    public String getResponseStatus(String username, String password) throws IOException {
        Map<String, String> map = loginRequest(username, password);
        return map.get("code");
    }
    public String getResponseMessage(String username, String password) throws IOException {
        Map<String, String> map = loginRequest(username, password);
        return map.get("message");
    }
    public Map<String, String> loginRequest(String username, String password) throws IOException {
        Connection.Response response =
                Jsoup.connect(GlobalVars.baseUrl + "/login?mode=login")
                        .method(Connection.Method.POST)
                        .data("usr", username)
                        .data("pwd", password)
                        .execute();
        //parse the document from response
        Document document = response.parse();

        int statusCode = response.statusCode();
        String code = new Integer(statusCode).toString();

        String s = document.getElementsByTag("h3").text();

        Map<String, String> map = new HashMap<String, String>();
        map.put("message", s);
        map.put("code", code);

        return map;
    }
}
