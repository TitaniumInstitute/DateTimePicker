package com.ti.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static com.ti.TransformDataHelper.getMonth;

public class AttendancePage {

    @FindBy(id = "AttendanceDate")
    private WebElement dtpAttendanceDate;

    @FindBy(xpath = "(//a/span[contains(text(),'Attendance')])[1]")
    private WebElement navAttendance;

    @FindBy(linkText = "Students Attendance")
    private WebElement navStudentsAttendance;

    //********************************************************************
    @FindBy(className = "datepicker-switch")
    private WebElement dtpMain;

    @FindBy(className = "datepicker-months")
    private WebElement dtpMonth;

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

    public AttendancePage(WebDriver driver) {
        PageFactory.initElements(driver,this);
    }

    public AttendancePage goToStudentsAttendance(WebDriver driver){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(navAttendance));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click()", navAttendance);
        return this;
    }

    public AttendancePage selectDate(WebDriver driver,String date){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(dtpAttendanceDate)).clear();
        dtpAttendanceDate.sendKeys(date);
        return this;
    }

    public AttendancePage selectFromCalendar(WebDriver driver,String date) throws InterruptedException {
        boolean yearFind = true;

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
                .executeScript("arguments[0].click();", driver.findElement(By.xpath("//table/thead/tr[1]/th[2]")));
            }

            yearFind = clickOneItem(year,lstYears);

            if ((Integer.parseInt(appYear)<Integer.parseInt(year))&&!yearFind){
                while (!yearFind){
                    icnNext.click();
                    yearFind = selectYear(year);
                }
            }

            if ((Integer.parseInt(appYear)>Integer.parseInt(year))&&!yearFind){
                while (!yearFind){
                    icnPrevious.click();
                    yearFind = selectYear(year);
                }
            }
        }

        //***** Select Month ***********
        if (!appMonth.equals(month)){
            if (dtpMain.isDisplayed()){
                dtpMain.click();
            }

            clickOneItem(month.substring(0,3),lstMonths);
            /*for (WebElement sMonth:lstMonths) {
                if ((month.substring(0,3)).equals(sMonth.getText())){
                    sMonth.click();
                    break;
                }
            }*/

        }

        //***** Select Day ***********
        /*for (WebElement sday:lstDays) {
            if (day.equals(sday.getText())){
                sday.click();
                break;
            }
        }*/
        clickOneItem(day,lstDays);
        return this;
    }

    //**********************
    private boolean selectYear(String year){
        for (WebElement syear: lstYears) {
            if (year.equals(syear.getText())) {
                syear.click();
                return true;
            }
        }
        return false;
    }

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
