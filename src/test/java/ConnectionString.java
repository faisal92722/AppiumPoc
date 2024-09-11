import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class ConnectionString {

    AppiumDriver driver;

    @BeforeTest
    public void DriverInitializer() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:platformName", "Android");
        capabilities.setCapability("appium:deviceName", "emulator-5554");
        capabilities.setCapability("appium:automationName", "uiautomator2");
        capabilities.setCapability("appium:app", System.getProperty("user.dir") + "/Application/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");
        capabilities.setCapability("appium:appPackage", "com.swaglabsmobileapp");
        capabilities.setCapability("appium:appActivity", "com.swaglabsmobileapp.SplashActivity");

        driver = new AndroidDriver(new URL("http://192.168.100.101:4723"), capabilities);
        System.out.println("Starting Andrion Appium Server");

    }

    @Test
    public void FirstTest() {
        System.out.println("This is Frist Sample Test");
    }

    @AfterTest
    public void CloseDriver() {
        driver.quit();
    }
}
