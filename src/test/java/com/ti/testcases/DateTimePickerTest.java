package com.ti.testcases;

import com.ti.pages.AttendancePageRefactor;
import com.ti.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DateTimePickerTest {
    WebDriver driver;
    LoginPage loginPage;
    AttendancePageRefactor attendancePage;
    String baseURL = "https://demosite.titaniuminstitute.com.mx/wp-admin/admin.php?page=sch-dashboard";
    String userName = "admin";
    String password = "G3-ySzY%";

    @BeforeTest
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(baseURL);
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
        attendancePage = new AttendancePageRefactor(driver);
    }

    @AfterTest
    void closeBrowser() {
        driver.quit();
    }

    @Test(enabled = false)
    void DateTimePickerTest() throws InterruptedException {
        loginPage.loginAs(userName).withPassword(password).login();
        attendancePage.goToStudentsAttendance(driver).selectDateFromCalendar(driver, "10/28/2034");
        Thread.sleep(5000);
    }

    @Test
    void DateTimePickerTypeTest() throws InterruptedException {
        loginPage.loginAs(userName).withPassword(password).login();
        attendancePage.goToStudentsAttendance(driver).typeDate(driver, "10/28/2034");
        Thread.sleep(5000);
    }
}
