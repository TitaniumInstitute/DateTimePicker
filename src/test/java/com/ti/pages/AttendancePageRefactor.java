package com.ti.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static com.ti.TransformDataHelper.convertToInt;
import static com.ti.TransformDataHelper.getMonth;

public class AttendancePageRefactor {

    @FindBy(id = "AttendanceDate")
    private WebElement dtpAttendanceDate;

    @FindBy(xpath = "(//a/span[contains(text(),'Attendance')])[1]")
    private WebElement navAttendance;

    @FindBy(linkText = "Students Attendance")
    private WebElement navStudentsAttendance;

    //********************************************************************
    @FindBy(className = "datepicker-switch")
    private WebElement dtpMain;

    @FindBy(className = "datepicker-years")
    private WebElement dtpYear;

    @FindBy(xpath = "(//th[contains(text(),'«')])[3]")
    private WebElement icnPrevious;

    @FindBy(xpath = "(//th[contains(text(),'»')])[3]")
    private WebElement icnNext;

    @FindBy(xpath = "//div[@class='datepicker-days']/table/tbody/tr/td[@class='day' or @class='active day']")
    private List<WebElement> lstDays;

    @FindBy(xpath = "//div[@class='datepicker-months']/table/tbody/tr/td/span")
    private List<WebElement> lstMonths;

    @FindBy(xpath = "//div[@class='datepicker-years']/table/tbody/tr/td/span")
    private List<WebElement> lstYears;

    public AttendancePageRefactor(WebDriver driver) {
        PageFactory.initElements(driver,this);
    }

    public AttendancePageRefactor goToStudentsAttendance(WebDriver driver){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(navAttendance));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click()", navAttendance);
        return this;
    }

    public AttendancePageRefactor typeDate(WebDriver driver, String date){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(dtpAttendanceDate)).clear();
        dtpAttendanceDate.sendKeys(date);
        return this;
    }

    public AttendancePageRefactor selectDateFromCalendar(WebDriver driver, String date) {
        boolean yearFind;

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(dtpAttendanceDate)).click();

        String[] dateSplit = date.split("/");
        String month = getMonth(dateSplit[0]);
        String day = dateSplit[1];
        String year = dateSplit[2];
        System.out.println(String.format("Month=%s, Day=%s, Year=%s",month,day,year));

        String[] appDateSplit = dtpMain.getText().split(" ");
        String appMonth = appDateSplit[0];
        String appYear = appDateSplit[1];
        System.out.println(String.format("appMonth=%s, appYear=%s",appMonth, appYear));

        //***** Select Year ***********
        if(!appYear.equals(year)){
            while (!dtpYear.isDisplayed()){
                ((JavascriptExecutor)driver)
                .executeScript("arguments[0].click();", dtpMain);
            }

            yearFind = clickOneItem(year,lstYears);

            if ((convertToInt(appYear)<convertToInt(year))&&!yearFind)                            {
                while (!yearFind){
                    icnNext.click();
                    yearFind = clickOneItem(year,lstYears);
                }
            }

            if ((convertToInt(appYear)>convertToInt(year))&&!yearFind){
                while (!yearFind){
                    icnPrevious.click();
                    yearFind = clickOneItem(year,lstYears);
                }
            }
        }

        //***** Select Month ***********
        if (!appMonth.equals(month)){
            if (dtpMain.isDisplayed()){
                dtpMain.click();
            }

            clickOneItem(month.substring(0,3),lstMonths);
        }

        //***** Select Day ***********
        clickOneItem(day,lstDays);
        return this;
    }

    //**********************
    private boolean clickOneItem(String element, List<WebElement> lstItems){
        for (WebElement item: lstItems) {
            if (element.equals(item.getText())) {
                item.click();
                return true;
            }
        }
        return false;
    }
}
