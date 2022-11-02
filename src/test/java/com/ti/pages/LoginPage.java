package com.ti.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(id = "user_login")
    private WebElement txtUser;

    @FindBy(name = "pwd")
    private WebElement txtPassword;

    @FindBy(css = "#rememberme")
    private WebElement chkRememberMe;

    @FindBy(xpath = "//input[contains(@value, 'Log')]")
    private WebElement btnLogin;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public LoginPage loginAs(String userName){
        txtUser.clear();
        txtUser.sendKeys(userName);
        return this;
    }

    public LoginPage withPassword(String password){
        txtPassword.clear();
        txtPassword.sendKeys(password);
        return this;
    }

    public LoginPage andRememberMe(boolean checked){
        if (checked){
            chkRememberMe.click();
        }
        return this;
    }

    public LoginPage login(){
        btnLogin.click();
        return this;
    }
}
