package com.qa.base;

import com.qa.utlis.ConfigReader;
import com.qa.utlis.utilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import javax.swing.text.Utilities;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class AppFactory {

    public  static  AppiumDriver driver;
    public  static ConfigReader configreader;
    @BeforeTest
    @Parameters({"platformName","platformVersion","deviceName"})
    public void initializer(String platformName, String platformVersion, String deviceName) throws MalformedURLException {
        try{
            configreader = new ConfigReader();
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("appium:platformName", platformName);
            capabilities.setCapability("appium:platformVersion", platformVersion);
            capabilities.setCapability("appium:deviceName", deviceName);
            capabilities.setCapability("appium:automationName",configreader.getAutomationName());
            capabilities.setCapability("appium:app", System.getProperty("user.dir") + configreader.getApkPath());
            capabilities.setCapability("appium:appPackage", configreader.getAppPackage());
            capabilities.setCapability("appium:appActivity", configreader.getAppActivity());
            capabilities.setCapability("appium:noReset",configreader.getNoReset());

            driver = new AndroidDriver(new URL(configreader.getAppiumServerEndpointURL()), capabilities);
            AppDriver.setDriver(driver);

        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }

    }

    public  void waitforVisibility (WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(utilities.WAIT));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public  void clickElement(WebElement element){
        waitforVisibility(element);
        element.click();
    }
    public  void sendKeys(WebElement element,String text){
    this.waitforVisibility(element);
    element.sendKeys(text);
    }

    public String getAttribute(WebElement element, String attribute){
    this.waitforVisibility(element);
   return element.getAttribute(attribute);

    }
    @AfterTest
         public  static void quitdriver(){
            if (driver!= null){
                driver.quit();
    }
    }
}
