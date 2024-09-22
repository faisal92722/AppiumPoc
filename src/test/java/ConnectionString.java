import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
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
        capabilities.setCapability("appium:noReset",false);
      //  capabilities.setCapability("appium:newCommandTimeout", 5000);
//        capabilities.setCapability("appium:avd","Pixel_3a_API_35_extension_level_13_x86_64");
//        capabilities.setCapability("appium:avdLaunchTimoeout","50000000");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);

        System.out.println("Starting Andriod Appium Server");

    }

    @Test
    public void FirstTest() {
        System.out.println("This is First Sample Test");
    }

    @AfterTest
    public void CloseDriver() {
        driver.quit();
////        String cmdString = "adb -s emulator-5554 env kill";
////        if(driver!=null)
////        {
////            try{
////                Runtime.getRuntime().exec("CMD /C "+ cmdString);
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//        }

    }
}
