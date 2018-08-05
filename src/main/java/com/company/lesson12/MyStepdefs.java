package com.company.lesson12;

import com.company.lesson10.Lesson10;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

/**
 * #Summary:
 * #Author: Andrii_Marchenko1
 * #Author’s Email:
 * #Creation Date: 5/3/2018
 * #Comments:
 */
public class MyStepdefs {

    @Given("^I have file \"([^\"]*)\" with string content$")
    public void iHaveFileWithStringContent(String fileName) throws Throwable {
        List<String> fileLines = new Lesson10().readFileAsList(fileName);
    }

    @When("^I read file content to List$")
    public void iReadFileContentToList() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I change place of \"([^\"]*)\" word with \"([^\"]*)\" word$")
    public void iChangePlaceOfWordWithWord(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I write changed list to file \"([^\"]*)\"$")
    public void iWriteChangedListToFile(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I print changed list to console$")
    public void iPrintChangedListToConsole() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
