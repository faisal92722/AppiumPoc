package com.qa.pages;

import com.qa.base.AppDriver;
import com.qa.base.AppFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends AppFactory {
    public LoginPage(){
        PageFactory.initElements(new AppiumFieldDecorator(AppDriver.getDriver()), this);

    }
    @AndroidFindBy(accessibility = "test-Username")
    public WebElement usernametextbox;
    @AndroidFindBy(accessibility = "test-Password")
    public WebElement userpasswordtextbox;
    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"LOGIN\"]")
    public WebElement LoginBtn;

   By swaglabheader = By.xpath("//android.widget.ScrollView[@content-desc=\"test-Login\"]/android.view.ViewGroup/android.widget.ImageView[1]");

    public void enterusername(String username){
      new WebDriverWait(AppDriver.getDriver(), Duration.ofSeconds(20)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(swaglabheader));
        usernametextbox.sendKeys(username);
    }

    public void enterpassword(String password){
        userpasswordtextbox.sendKeys(password);
    }
    public void clickloginbutton(){
        LoginBtn.click();

    }
}
