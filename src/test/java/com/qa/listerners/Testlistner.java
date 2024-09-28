package com.qa.listerners;

import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Testlistner implements ITestListener {

    public  void onTestFailure(ITestResult result){
        if(result.getThrowable() !=null){
            StringWriter stringwriter = new StringWriter();
            PrintWriter printwriter = new PrintWriter(stringwriter);
            result.getThrowable().printStackTrace();
            System.out.println(stringwriter.toString());
        }
    }
}
