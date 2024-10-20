package com.qa.base;

import com.aventstack.extentreports.Status;
import com.qa.reports.ExtendReport;
import com.qa.utlis.ConfigReader;
import com.qa.utlis.utilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class AppFactory {

    public  static  AppiumDriver driver;
    public  static ConfigReader configreader;
    protected HashMap<String, String> stringHashMap = new HashMap<>();
    InputStream stringIs;

    public static utilities util = new utilities();

    public  static  String dateTime;

    private static AppiumDriverLocalService server;
    @BeforeSuite
    public void upAndRunningAppiumServer() {
        server = getAppiumServerDefault();
        if (!util.checkIfAppiumServerIsRunning(4723)) {
            server.start();
            server.clearOutPutStreams();
        } else {
            util.log().info("Appium Server is already up and running");
        }
    }

    @AfterSuite
    public void shutdownServer() {
        server.stop();
    }

    public AppiumDriverLocalService getAppiumServerDefault() {
        return AppiumDriverLocalService.buildDefaultService();
    }
//    utilities util;
//    static Logger log = LogManager.getLogger(AppFactory.class.getName());
    @BeforeTest
    @Parameters({"platformName","platformVersion","deviceName"})
    public void initializer(String platformName, String platformVersion, String deviceName) throws IOException, ParserConfigurationException, SAXException {
        try{
            configreader = new ConfigReader();
            String xmlFileName = "strings/string.xml";
            stringIs = getClass().getClassLoader().getResourceAsStream(xmlFileName);
            stringHashMap = util.parseStringXML(stringIs);

            dateTime = util.getDateTime();


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
            util.log().info("App URL is {}",configreader.getAppiumServerEndpointURL());
            AppDriver.setDriver(driver);
            AppDriver.setPlatformName(platformName);
            AppDriver.setDeviceName(deviceName);
            util.log().info("Log Driver is set");

        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }

    }

    public  void waitforVisibility (WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(utilities.WAIT));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public  void clickElement(WebElement element,String message){
        waitforVisibility(element);
        util.log().info(message);
        ExtendReport.getTest().log(Status.INFO,message);
        element.click();
    }
    public  void sendKeys(WebElement element,String text,String message){
    this.waitforVisibility(element);
    util.log().info(message);
    ExtendReport.getTest().log(Status.INFO,message);
    element.sendKeys(text);
    }

    public String getAttribute(WebElement element, String attribute){
    this.waitforVisibility(element);
   return element.getAttribute(attribute);
    }
    public  static  String getDateTime(){
        return  dateTime;
    }
    @AfterTest
         public  static void quitdriver(){
            if (driver!= null){
                driver.quit();
    }
    }
}
