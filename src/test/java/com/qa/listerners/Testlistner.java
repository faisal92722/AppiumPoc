package com.qa.listerners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.observer.entity.MediaEntity;
import com.qa.base.AppDriver;
import com.qa.base.AppFactory;
import com.qa.reports.ExtendReport;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;

import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
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
        byte[] encoded = null;
        try {
            encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        ExtendReport.getTest().fail("Test Fail", MediaEntityBuilder.createScreenCaptureFromPath(completePath).build());
        System.out.println(completePath);
        ExtendReport.getTest().fail("Test Fail",MediaEntityBuilder.createScreenCaptureFromBase64String(new String(encoded, StandardCharsets.US_ASCII)).build());
        ExtendReport.getTest().fail(result.getThrowable());
    }
            @Override
            public void onTestStart(ITestResult result) {
                ExtendReport.startTest(result.getName(), result.getMethod().getDescription()).assignCategory(AppDriver.getPlatformName() + "_" + AppDriver.getDeviceName());
            }

            @Override
            public void onTestSuccess(ITestResult result) {
                ExtendReport.getTest().log(Status.PASS, "Test Passed");
            }

            @Override
            public void onTestSkipped(ITestResult result) {
                ExtendReport.getTest().log(Status.SKIP, "Test Skipped");
            }

            @Override
            public void onFinish(ITestContext context){
                ExtendReport.getExtentReports().flush();
            }

}
