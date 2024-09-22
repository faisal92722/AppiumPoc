package com.qa.base;

import com.qa.configurationFileReader.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;

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
    @AfterTest
         public  static void quitdriver(){
            if (driver!= null){
                driver.quit();
    }
    }
}
