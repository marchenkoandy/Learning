package com.company.lesson12;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

public class Driver {
    public WebDriver driver() {
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "src\\main\\resources\\drivers\\chromedriver.exe");
        return new ChromeDriver();
    }
}
