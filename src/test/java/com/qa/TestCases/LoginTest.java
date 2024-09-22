package com.qa.TestCases;

import com.qa.base.AppFactory;
import com.qa.pages.LoginPage;
import org.openqa.selenium.devtools.idealized.log.Log;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest extends AppFactory {
    LoginPage loginpage;
@BeforeTest
    public void setup(){
        loginpage = new LoginPage();
    }
     @Test
    public void Validlogin() throws InterruptedException {
        System.out.println("This test is used for Valid Login");
            loginpage.enterusername("standard_user");
            loginpage.enterpassword("secret_sauce");
            loginpage.clickloginbutton();
            Thread.sleep(5000);
            System.out.println("Login Successfully");

        }

    @AfterTest
    public void teardown(){
        AppFactory.quitdriver();
    }
}
