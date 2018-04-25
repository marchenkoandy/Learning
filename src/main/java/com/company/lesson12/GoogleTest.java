package com.company.lesson12;

import org.openqa.selenium.WebDriver;

public class GoogleTest {

    public static void main(String[] args) {
        WebDriver driver = new Driver().driver();
        String url = "https://www.google.com";
        driver.get(url);
        System.out.println(String.format("Successfully opened the website %s", url));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }
}