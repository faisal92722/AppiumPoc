package com.qa.TestCases;

import com.qa.base.AppFactory;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductPage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.devtools.idealized.log.Log;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Objects;

public class LoginTest extends AppFactory {
    LoginPage loginpage;
    ProductPage productpage;
    InputStream inputstream;
    JSONObject loginUser;

    @BeforeMethod
    public void setup(Method mehtod){

    loginpage = new LoginPage();
    util.log().info("\n\n*********"+ mehtod.getName()+"**********\n\n");
    }

    @BeforeClass
    public  void setupDataStream() throws IOException {
        try {
            String dataFilename = "data/loginuser.json";
            inputstream = getClass().getClassLoader().getResourceAsStream(dataFilename);
            JSONTokener jsonTokener = new JSONTokener(Objects.requireNonNull(inputstream));
            loginUser = new JSONObject(jsonTokener);

        }catch (Exception exception){
        exception.printStackTrace();
        } finally {
            if (inputstream != null){
                inputstream.close();
            }
        }
    }
    @Test
    public void verifyinvalidusername(){
        util.log().info("This test is used to Login with invalid username and valid password");
    loginpage.enterusername(loginUser.getJSONObject("InvalidUser").getString("username"));
    loginpage.enterpassword(loginUser.getJSONObject("InvalidUser").getString("password"));
    loginpage.clickloginbutton();

    String expectederrormessage = stringHashMap.get("error_invalid_username_and_password");
    String actualmessage = loginpage.getErrorMessage();

        util.log().info("Actual Error Message is:"+ actualmessage +"\nExpected Error Message is:"+expectederrormessage);
    Assert.assertEquals(actualmessage,expectederrormessage);

    }
    @Test
    public  void verifyInvalidpassword(){
        util.log().info("This test is used to Login with valid username and invalid password");
        loginpage.enterusername(loginUser.getJSONObject("InvalidPassword").getString("username"));
        loginpage.enterpassword(loginUser.getJSONObject("InvalidPassword").getString("password"));
        loginpage.clickloginbutton();

        String expectederrormessage = stringHashMap.get("error_invalid_username_and_password"+ "Sample");;
        String actualmessage = loginpage.getErrorMessage();

        System.out.println("Actual Error Message is:"+ actualmessage +"\nExpected Error Message is:"+expectederrormessage);
        Assert.assertEquals(actualmessage,expectederrormessage);
    }
    @Test
    public void verifyvalidLogin(){
        util.log().info("This test is used to Login with valid username and valid password");
        loginpage.enterusername(loginUser.getJSONObject("validusernameandpassword").getString("username"));
        loginpage.enterpassword(loginUser.getJSONObject("validusernameandpassword").getString("password"));
        productpage = loginpage.clickloginbutton();
        String expectedTitle = stringHashMap.get("product.title");;
        String actualTitle = productpage.getTitle();

        util.log().info("Actual Product Titel:"+expectedTitle+"Expected Title:"+actualTitle);
        Assert.assertEquals(actualTitle,expectedTitle);

    }

    @AfterTest
    public void teardown(){
        AppFactory.quitdriver();
    }
}
