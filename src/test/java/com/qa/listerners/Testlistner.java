package com.qa.listerners;

import com.qa.base.AppDriver;
import com.qa.base.AppFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;

import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class Testlistner implements ITestListener {

    public  void onTestFailure(ITestResult result){
        if(result.getThrowable() !=null){
            StringWriter stringwriter = new StringWriter();
            PrintWriter printwriter = new PrintWriter(stringwriter);
            result.getThrowable().printStackTrace();
            System.out.println(stringwriter.toString());
        }

        File file = ((TakesScreenshot) AppDriver.getDriver()).getScreenshotAs(OutputType.FILE);

        Map<String, String> params = new HashMap<>();
        params = result.getTestContext().getCurrentXmlTest().getAllParameters();

        String imgpath = "Screenshots" + File.separator + params.get("platformName") + "_" + params.get("platformVersion") + "_" + params.get("deviceName")
                + File.separator + AppFactory.getDateTime() + File.separator + result.getTestClass().getRealClass().getSimpleName() + File.separator + result.getName() + ".png";

        String completePath = System.getProperty("user.dir") + File.separator + imgpath;

        try {
            FileUtils.copyFile(file, new File(imgpath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
