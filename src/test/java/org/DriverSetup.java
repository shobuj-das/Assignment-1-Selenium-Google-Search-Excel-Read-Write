package org;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class DriverSetup {
    public WebDriver driver;

    @BeforeSuite
    public void getDriver(){
        driver = new ChromeDriver();
    }

    @AfterSuite
    public void quitDriver(){
        driver.quit();
    }
}
