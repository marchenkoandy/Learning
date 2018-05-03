package com.company.lesson12;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        features = "classpath:cucumber/",
        glue = "com.company",
        tags = {"~@Ignore"},
        plugin = {"pretty", "json:target/cucumber.json"})
public class TestNGRunner extends AbstractTestNGCucumberTests {
}