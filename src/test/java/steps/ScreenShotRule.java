package steps;

import io.qameta.allure.Attachment;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShotRule extends TestWatcher {

    private WebDriver driver;

    public ScreenShotRule(WebDriver driver) {
        this.driver =  driver;
    }

    @Override
    protected void failed(Throwable e, Description description) {
            System.out.println("zazazazazazaza");
            screenshot();
    }
    @Attachment(value = "Page screenshot", type = "image/png")
    byte[] saveScreenshot(byte[] screenShot) {
        return screenShot;
    }
    void screenshot() {
        saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
    }
    }
